package slimeknights.tconstruct.smeltery.tileentity;

import net.minecraft.util.*;
import slimeknights.mantle.common.*;
import slimeknights.tconstruct.library.smeltery.*;
import slimeknights.tconstruct.smeltery.multiblock.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import slimeknights.tconstruct.smeltery.inventory.*;
import net.minecraft.client.gui.inventory.*;
import slimeknights.tconstruct.smeltery.client.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.text.*;
import javax.annotation.*;
import net.minecraft.util.math.*;
import java.util.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.smeltery.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import net.minecraft.nbt.*;

public class TileTinkerTank extends TileMultiblock<MultiblockTinkerTank> implements ITickable, IInventoryGui, ISmelteryTankHandler
{
    protected static final int CAPACITY_PER_BLOCK = 4000;
    protected MultiblockTinkerTank multiblock;
    protected boolean active;
    protected SmelteryTank liquids;
    protected int tick;
    
    public TileTinkerTank() {
        super("gui.tinkertank.name", 0);
        this.setMultiblock(new MultiblockTinkerTank(this));
        this.liquids = new SmelteryTank(this);
    }
    
    public void func_73660_a() {
        if (this.func_145831_w().field_72995_K) {
            return;
        }
        if (!this.isActive()) {
            if (this.tick == 0) {
                this.checkMultiblockStructure();
            }
            this.tick = (this.tick + 1) % 20;
        }
    }
    
    @Override
    protected void updateStructureInfo(final MultiblockDetection.MultiblockStructure structure) {
        final int liquidSize = (structure.xd + 2) * (structure.yd + 2) * (structure.zd + 2);
        this.liquids.setCapacity(liquidSize * 4000);
        this.markDirtyFast();
    }
    
    public SmelteryTank getTank() {
        return this.liquids;
    }
    
    public Container createContainer(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (Container)new ContainerTinkerTank(this);
    }
    
    @SideOnly(Side.CLIENT)
    public GuiContainer createGui(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return new GuiTinkerTank(this.createContainer(inventoryplayer, world, pos), this);
    }
    
    @Nonnull
    public ITextComponent func_145748_c_() {
        if (this.func_145818_k_()) {
            return (ITextComponent)new TextComponentString(this.func_70005_c_());
        }
        return (ITextComponent)new TextComponentTranslation(this.func_70005_c_(), new Object[0]);
    }
    
    @Nonnull
    public AxisAlignedBB getRenderBoundingBox() {
        if (this.minPos == null || this.maxPos == null) {
            return super.getRenderBoundingBox();
        }
        return new AxisAlignedBB((double)(this.minPos.func_177958_n() - 1), (double)Math.min(this.minPos.func_177956_o(), this.field_174879_c.func_177956_o()), (double)(this.minPos.func_177952_p() - 1), (double)(this.maxPos.func_177958_n() + 2), (double)(Math.max(this.maxPos.func_177956_o(), this.field_174879_c.func_177956_o()) + 1), (double)(this.maxPos.func_177952_p() + 2));
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
        return compound;
    }
    
    @Override
    public void func_145839_a(final NBTTagCompound compound) {
        super.func_145839_a(compound);
        this.liquids.readFromNBT(compound);
    }
}
