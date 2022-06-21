package slimeknights.tconstruct.smeltery.tileentity;

import slimeknights.mantle.common.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.smeltery.events.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.shared.*;
import net.minecraftforge.fluids.*;
import net.minecraft.util.math.*;
import java.util.*;
import slimeknights.tconstruct.library.smeltery.*;
import slimeknights.tconstruct.smeltery.multiblock.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import slimeknights.tconstruct.smeltery.inventory.*;
import net.minecraft.client.gui.inventory.*;
import slimeknights.tconstruct.smeltery.client.*;
import net.minecraftforge.fml.relauncher.*;
import javax.annotation.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import slimeknights.tconstruct.smeltery.network.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.*;

public class TileSmeltery extends TileHeatingStructureFuelTank<MultiblockSmeltery> implements ITickable, IInventoryGui, ISmelteryTankHandler
{
    public static final DamageSource smelteryDamage;
    static final Logger log;
    public static final String TAG_INSIDEPOS = "insidePos";
    protected static final int CAPACITY_PER_BLOCK = 1152;
    protected static final int ALLOYING_PER_TICK = 10;
    protected SmelteryTank liquids;
    protected int tick;
    private BlockPos insideCheck;
    private int fullCheckCounter;
    
    public TileSmeltery() {
        super("gui.smeltery.name", 0, 1);
        this.fullCheckCounter = 0;
        this.setMultiblock(new MultiblockSmeltery(this));
        this.liquids = new SmelteryTank(this);
    }
    
    public void func_73660_a() {
        if (this.isClientWorld()) {
            return;
        }
        if (!this.isActive()) {
            if (this.tick == 0) {
                this.checkMultiblockStructure();
            }
        }
        else {
            if (this.tick == 0) {
                this.interactWithEntitiesInside();
            }
            if (this.tick % 4 == 0) {
                this.heatItems();
                this.alloyAlloys();
            }
            if (this.needsFuel) {
                this.consumeFuel();
            }
            if (this.tick == 0) {
                if (++this.fullCheckCounter >= 15) {
                    this.fullCheckCounter = 0;
                    this.checkMultiblockStructure();
                }
                else {
                    this.updateInsideCheck();
                    if (!this.func_145831_w().func_175623_d(this.insideCheck)) {
                        this.setInvalid();
                        this.insideCheck = null;
                        final IBlockState state = this.func_145831_w().func_180495_p(this.field_174879_c);
                        this.func_145831_w().func_184138_a(this.func_174877_v(), state, state, 3);
                    }
                    else {
                        this.progressInsideCheck();
                    }
                }
            }
        }
        this.tick = (this.tick + 1) % 20;
    }
    
    private void updateInsideCheck() {
        if (this.insideCheck == null || this.insideCheck.func_177958_n() < this.minPos.func_177958_n() || this.insideCheck.func_177956_o() < this.minPos.func_177956_o() || this.insideCheck.func_177952_p() < this.minPos.func_177952_p() || this.insideCheck.func_177958_n() > this.maxPos.func_177958_n() || this.insideCheck.func_177956_o() > this.maxPos.func_177956_o() || this.insideCheck.func_177952_p() > this.maxPos.func_177952_p()) {
            this.insideCheck = this.minPos;
        }
    }
    
    private void progressInsideCheck() {
        this.insideCheck = this.insideCheck.func_177982_a(1, 0, 0);
        if (this.insideCheck.func_177958_n() > this.maxPos.func_177958_n()) {
            this.insideCheck = new BlockPos(this.minPos.func_177958_n(), this.insideCheck.func_177956_o(), this.insideCheck.func_177952_p() + 1);
            if (this.insideCheck.func_177952_p() > this.maxPos.func_177952_p()) {
                this.insideCheck = new BlockPos(this.minPos.func_177958_n(), this.insideCheck.func_177956_o() + 1, this.minPos.func_177952_p());
            }
        }
    }
    
    protected void updateHeatRequired(final int index) {
        final ItemStack stack = this.func_70301_a(index);
        if (!stack.func_190926_b()) {
            final MeltingRecipe melting = TinkerRegistry.getMelting(stack);
            if (melting != null) {
                this.setHeatRequiredForSlot(index, Math.max(5, melting.getUsableTemperature()));
                if (!this.hasFuel()) {
                    this.consumeFuel();
                }
                return;
            }
        }
        this.setHeatRequiredForSlot(index, 0);
    }
    
    protected boolean onItemFinishedHeating(final ItemStack stack, final int slot) {
        final MeltingRecipe recipe = TinkerRegistry.getMelting(stack);
        if (recipe == null) {
            return false;
        }
        final TinkerSmelteryEvent.OnMelting event = TinkerSmelteryEvent.OnMelting.fireEvent(this, stack, recipe.output.copy());
        final FluidStack fluidStack = FluidUtil.getValidFluidStackOrNull(event.result);
        final int filled = this.liquids.fill(fluidStack, false);
        if (filled == fluidStack.amount) {
            this.liquids.fill(fluidStack, true);
            this.func_70299_a(slot, ItemStack.field_190927_a);
            return true;
        }
        this.itemTemperatures[slot] = this.itemTempRequired[slot] * 2 + 1;
        return false;
    }
    
    protected void interactWithEntitiesInside() {
        final AxisAlignedBB bb = this.info.getBoundingBox().func_191195_a(-2.0, -1.0, -2.0).func_72317_d(-1.0, 0.0, -1.0);
        final List<Entity> entities = (List<Entity>)this.func_145831_w().func_72872_a((Class)Entity.class, bb);
        for (final Entity entity : entities) {
            if (entity instanceof EntityItem) {
                if (TinkerRegistry.getMelting(((EntityItem)entity).func_92059_d()) == null) {
                    continue;
                }
                final ItemStack stack = ((EntityItem)entity).func_92059_d();
                for (int i = 0; i < this.func_70302_i_(); ++i) {
                    if (!this.isStackInSlot(i)) {
                        final ItemStack invStack = stack.func_77946_l();
                        stack.func_190918_g(1);
                        invStack.func_190920_e(1);
                        this.func_70299_a(i, invStack);
                    }
                    if (stack.func_190926_b()) {
                        entity.func_70106_y();
                        break;
                    }
                }
            }
            else {
                if (this.liquids.getFluidAmount() <= 0) {
                    continue;
                }
                FluidStack fluid = TinkerRegistry.getMeltingForEntity(entity);
                if (fluid == null && entity instanceof EntityLivingBase && entity.func_70089_S() && !entity.field_70128_L) {
                    fluid = new FluidStack((Fluid)TinkerFluids.blood, 20);
                }
                if (fluid == null || !entity.func_70097_a(TileSmeltery.smelteryDamage, 2.0f)) {
                    continue;
                }
                this.liquids.fill(fluid.copy(), true);
            }
        }
    }
    
    protected void alloyAlloys() {
        for (final AlloyRecipe recipe : TinkerRegistry.getAlloys()) {
            if (!recipe.isValid()) {
                continue;
            }
            int matched = recipe.matches(this.liquids.getFluids());
            if (matched > 10) {
                matched = 10;
            }
            while (matched > 0) {
                for (final FluidStack liquid : recipe.getFluids()) {
                    final FluidStack toDrain = liquid.copy();
                    final FluidStack drained = this.liquids.drain(toDrain, true);
                    assert drained != null;
                    if (drained.isFluidEqual(toDrain) && drained.amount == toDrain.amount) {
                        continue;
                    }
                    TileSmeltery.log.error("Smeltery alloy creation drained incorrect amount: was %s:%d, should be %s:%d", (Object)drained.getUnlocalizedName(), (Object)drained.amount, (Object)toDrain.getUnlocalizedName(), (Object)toDrain.amount);
                }
                final FluidStack toFill = FluidUtil.getValidFluidStackOrNull(recipe.getResult().copy());
                final int filled = this.liquids.fill(toFill, true);
                if (filled != recipe.getResult().amount) {
                    TileSmeltery.log.error("Smeltery alloy creation filled incorrect amount: was %d, should be %d (%s)", (Object)filled, (Object)(recipe.getResult().amount * matched), (Object)recipe.getResult().getUnlocalizedName());
                }
                matched -= filled;
            }
        }
    }
    
    @Override
    protected void updateStructureInfo(final MultiblockDetection.MultiblockStructure structure) {
        super.updateStructureInfo(structure);
        this.liquids.setCapacity(this.func_70302_i_() * 1152);
    }
    
    protected boolean hasCeiling() {
        return false;
    }
    
    @Override
    protected int getUpdatedInventorySize(final int width, final int height, final int depth) {
        return width * height * depth;
    }
    
    public SmelteryTank getTank() {
        return this.liquids;
    }
    
    public Container createContainer(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (Container)new ContainerSmeltery(inventoryplayer, this);
    }
    
    @SideOnly(Side.CLIENT)
    public GuiContainer createGui(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (GuiContainer)new GuiSmeltery((ContainerSmeltery)this.createContainer(inventoryplayer, world, pos), this);
    }
    
    @Nonnull
    public AxisAlignedBB getRenderBoundingBox() {
        if (this.minPos == null || this.maxPos == null) {
            return super.getRenderBoundingBox();
        }
        return new AxisAlignedBB((double)this.minPos.func_177958_n(), (double)this.minPos.func_177956_o(), (double)this.minPos.func_177952_p(), (double)(this.maxPos.func_177958_n() + 1), (double)(this.maxPos.func_177956_o() + 1), (double)(this.maxPos.func_177952_p() + 1));
    }
    
    public void func_70299_a(final int slot, final ItemStack itemstack) {
        if (this.func_145831_w() != null && this.func_145831_w() instanceof WorldServer && !this.func_145831_w().field_72995_K && !ItemStack.func_77989_b(itemstack, this.func_70301_a(slot))) {
            TinkerNetwork.sendToClients((WorldServer)this.func_145831_w(), this.field_174879_c, (AbstractPacket)new SmelteryInventoryUpdatePacket(itemstack, slot, this.field_174879_c));
        }
        super.func_70299_a(slot, itemstack);
    }
    
    @SideOnly(Side.CLIENT)
    public void updateFluidsFromPacket(final List<FluidStack> fluids) {
        this.liquids.setFluids(fluids);
    }
    
    public void onTankChanged(final List<FluidStack> fluids, final FluidStack changed) {
        if (this.isServerWorld()) {
            TinkerNetwork.sendToAll((AbstractPacket)new SmelteryFluidUpdatePacket(this.field_174879_c, fluids));
        }
        this.markDirtyFast();
    }
    
    @Nonnull
    @Override
    public NBTTagCompound func_189515_b(NBTTagCompound compound) {
        compound = super.func_189515_b(compound);
        this.liquids.writeToNBT(compound);
        compound.func_74782_a("insidePos", (NBTBase)TagUtil.writePos(this.insideCheck));
        return compound;
    }
    
    @Override
    public void func_145839_a(final NBTTagCompound compound) {
        super.func_145839_a(compound);
        this.liquids.readFromNBT(compound);
        this.insideCheck = TagUtil.readPos(compound.func_74775_l("insidePos"));
    }
    
    static {
        smelteryDamage = new DamageSource("smeltery").func_76361_j();
        log = Util.getLogger("Smeltery");
    }
}
