package slimeknights.tconstruct.smeltery.tileentity;

import slimeknights.tconstruct.shared.tileentity.*;
import net.minecraft.inventory.*;
import slimeknights.tconstruct.library.tileentity.*;
import slimeknights.tconstruct.smeltery.network.*;
import slimeknights.tconstruct.library.smeltery.*;
import slimeknights.mantle.tileentity.*;
import slimeknights.tconstruct.library.fluid.*;
import net.minecraftforge.items.wrapper.*;
import net.minecraftforge.common.capabilities.*;
import javax.annotation.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.smeltery.events.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import slimeknights.tconstruct.common.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraftforge.fluids.*;
import net.minecraft.world.*;
import slimeknights.mantle.network.*;
import net.minecraft.nbt.*;
import net.minecraftforge.items.*;

public abstract class TileCasting extends TileTable implements ITickable, ISidedInventory, IProgress, FluidUpdatePacket.IFluidPacketReceiver
{
    public FluidTankAnimated tank;
    public IFluidHandler fluidHandler;
    protected int timer;
    protected ICastingRecipe recipe;
    
    public TileCasting() {
        super("casting", 2, 1);
        this.tank = new FluidTankAnimated(0, (MantleTileEntity)this);
        this.fluidHandler = (IFluidHandler)new FluidHandlerCasting(this, this.tank);
        this.itemHandler = (IItemHandlerModifiable)new SidedInvWrapper((ISidedInventory)this, EnumFacing.DOWN);
    }
    
    public boolean hasCapability(@Nonnull final Capability<?> capability, @Nullable final EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability((Capability)capability, facing);
    }
    
    public <T> T getCapability(@Nonnull final Capability<T> capability, @Nullable final EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return (T)this.fluidHandler;
        }
        return (T)super.getCapability((Capability)capability, facing);
    }
    
    public void interact(final EntityPlayer player) {
        if (this.tank.getFluidAmount() > 0) {
            return;
        }
        if (!this.isStackInSlot(0) && !this.isStackInSlot(1)) {
            final ItemStack stack = player.field_71071_by.func_70298_a(player.field_71071_by.field_70461_c, this.stackSizeLimit);
            this.func_70299_a(0, stack);
        }
        else {
            final int slot = this.isStackInSlot(1) ? 1 : 0;
            final ItemStack stack2 = this.func_70301_a(slot);
            if (slot == 1) {
                FMLCommonHandler.instance().firePlayerSmeltedEvent(player, stack2);
            }
            ItemHandlerHelper.giveItemToPlayer(player, stack2);
            this.func_70299_a(slot, ItemStack.field_190927_a);
            if (slot == 1) {
                this.func_145831_w().func_175685_c(this.field_174879_c, this.func_145838_q(), true);
            }
        }
    }
    
    @Nonnull
    public int[] func_180463_a(@Nonnull final EnumFacing side) {
        return new int[] { 0, 1 };
    }
    
    public boolean func_180462_a(final int index, @Nonnull final ItemStack itemStackIn, @Nonnull final EnumFacing direction) {
        return index == 0 && !this.isStackInSlot(1);
    }
    
    public boolean func_180461_b(final int index, @Nonnull final ItemStack stack, @Nonnull final EnumFacing direction) {
        return index == 1;
    }
    
    public void func_73660_a() {
        if (this.recipe == null) {
            return;
        }
        if (this.tank.getFluidAmount() == this.tank.getCapacity()) {
            ++this.timer;
            if (!this.func_145831_w().field_72995_K) {
                if (this.timer >= this.recipe.getTime()) {
                    final TinkerCastingEvent.OnCasted event = TinkerCastingEvent.OnCasted.fire(this.recipe, this);
                    if (event.consumeCast) {
                        this.func_70299_a(0, ItemStack.field_190927_a);
                        for (final EntityPlayer player : this.func_145831_w().field_73010_i) {
                            if (player.func_174818_b(this.field_174879_c) < 1024.0 && player instanceof EntityPlayerMP) {
                                TinkerNetwork.sendPacket((Entity)player, (Packet<?>)new SPacketParticles(EnumParticleTypes.FLAME, false, this.field_174879_c.func_177958_n() + 0.5f, this.field_174879_c.func_177956_o() + 1.1f, this.field_174879_c.func_177952_p() + 0.5f, 0.25f, 0.0125f, 0.25f, 0.005f, 5, new int[0]));
                            }
                        }
                    }
                    if (event.switchOutputs) {
                        this.func_70299_a(1, this.func_70301_a(0));
                        this.func_70299_a(0, event.output);
                    }
                    else {
                        this.func_70299_a(1, event.output);
                    }
                    this.func_145831_w().func_184133_a((EntityPlayer)null, this.field_174879_c, SoundEvents.field_187659_cY, SoundCategory.AMBIENT, 0.07f, 4.0f);
                    this.reset();
                    this.func_145831_w().func_175685_c(this.field_174879_c, this.func_145838_q(), true);
                }
            }
            else if (this.func_145831_w().field_73012_v.nextFloat() > 0.9f) {
                this.func_145831_w().func_175688_a(EnumParticleTypes.SMOKE_NORMAL, this.field_174879_c.func_177958_n() + this.func_145831_w().field_73012_v.nextDouble(), this.field_174879_c.func_177956_o() + 1.1, this.field_174879_c.func_177952_p() + this.func_145831_w().field_73012_v.nextDouble(), 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }
    
    public float getProgress() {
        if (this.recipe == null || this.tank.getFluidAmount() == 0) {
            return 0.0f;
        }
        return Math.min(1.0f, this.timer / (float)this.recipe.getTime());
    }
    
    public ItemStack getCurrentResult() {
        if (this.recipe == null) {
            return null;
        }
        Fluid fluid = null;
        if (this.tank.getFluid() != null) {
            fluid = this.tank.getFluid().getFluid();
        }
        return this.recipe.getResult(this.func_70301_a(0), fluid);
    }
    
    protected abstract ICastingRecipe findRecipe(final ItemStack p0, final Fluid p1);
    
    protected ICastingRecipe findRecipe(final Fluid fluid) {
        final ICastingRecipe recipe = this.findRecipe(this.func_70301_a(0), fluid);
        if (TinkerCastingEvent.OnCasting.fire(recipe, this)) {
            return recipe;
        }
        return null;
    }
    
    public int initNewCasting(final Fluid fluid, final boolean setNewRecipe) {
        final ICastingRecipe recipe = this.findRecipe(fluid);
        if (recipe != null) {
            if (setNewRecipe) {
                this.recipe = recipe;
            }
            return recipe.getFluidAmount();
        }
        return 0;
    }
    
    public void reset() {
        this.timer = 0;
        this.recipe = null;
        this.tank.setCapacity(0);
        this.tank.setFluid((FluidStack)null);
        this.tank.renderOffset = 0.0f;
        if (this.func_145831_w() != null && !this.func_145831_w().field_72995_K && this.func_145831_w() instanceof WorldServer) {
            TinkerNetwork.sendToClients((WorldServer)this.func_145831_w(), this.field_174879_c, (AbstractPacket)new FluidUpdatePacket(this.field_174879_c, null));
        }
    }
    
    public void updateFluidTo(final FluidStack fluid) {
        final int oldAmount = this.tank.getFluidAmount();
        this.tank.setFluid(fluid);
        if (fluid == null) {
            this.reset();
            return;
        }
        if (this.recipe == null) {
            this.recipe = this.findRecipe(fluid.getFluid());
            if (this.recipe != null) {
                this.tank.setCapacity(this.recipe.getFluidAmount());
            }
        }
        final FluidTankAnimated tank = this.tank;
        tank.renderOffset += this.tank.getFluidAmount() - oldAmount;
    }
    
    public int comparatorStrength() {
        return this.isStackInSlot(1) ? 15 : 0;
    }
    
    @Nonnull
    public NBTTagCompound func_189515_b(NBTTagCompound tags) {
        tags = super.func_189515_b(tags);
        final NBTTagCompound tankTag = new NBTTagCompound();
        this.tank.writeToNBT(tankTag);
        tags.func_74782_a("tank", (NBTBase)tankTag);
        tags.func_74768_a("timer", this.timer);
        return tags;
    }
    
    public void func_145839_a(final NBTTagCompound tags) {
        super.func_145839_a(tags);
        this.tank.readFromNBT(tags.func_74775_l("tank"));
        this.updateFluidTo(this.tank.getFluid());
        this.timer = tags.func_74762_e("timer");
    }
}
