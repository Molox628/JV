package slimeknights.tconstruct.smeltery.tileentity;

import slimeknights.tconstruct.smeltery.multiblock.*;
import slimeknights.mantle.tileentity.*;
import net.minecraft.util.math.*;
import slimeknights.mantle.multiblock.*;
import slimeknights.tconstruct.smeltery.block.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import com.google.common.collect.*;
import java.util.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;
import javax.annotation.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;

public abstract class TileMultiblock<T extends MultiblockDetection> extends TileInventory implements IMasterLogic
{
    public static final String TAG_ACTIVE = "active";
    public static final String TAG_MINPOS = "minPos";
    public static final String TAG_MAXPOS = "maxPos";
    protected static final int MAX_SIZE = 9;
    protected boolean active;
    protected MultiblockDetection.MultiblockStructure info;
    protected T multiblock;
    protected BlockPos minPos;
    protected BlockPos maxPos;
    
    public TileMultiblock(final String name, final int inventorySize) {
        super(name, inventorySize);
    }
    
    public TileMultiblock(final String name, final int inventorySize, final int maxStackSize) {
        super(name, inventorySize, maxStackSize);
    }
    
    protected void setMultiblock(final T multiblock) {
        this.multiblock = multiblock;
    }
    
    public BlockPos getMinPos() {
        return this.minPos;
    }
    
    public BlockPos getMaxPos() {
        return this.maxPos;
    }
    
    public void notifyChange(final IServantLogic servant, final BlockPos pos) {
        this.checkMultiblockStructure();
    }
    
    public void checkMultiblockStructure() {
        final boolean wasActive = this.active;
        final IBlockState state = this.func_145831_w().func_180495_p(this.func_174877_v());
        if (!(state.func_177230_c() instanceof BlockMultiblockController)) {
            this.active = false;
        }
        else {
            final EnumFacing in = ((EnumFacing)state.func_177229_b((IProperty)BlockMultiblockController.FACING)).func_176734_d();
            if (this.info == null || this.multiblock.checkIfMultiblockCanBeRechecked(this.field_145850_b, this.info)) {
                final MultiblockDetection.MultiblockStructure structure = this.multiblock.detectMultiblock(this.func_145831_w(), this.func_174877_v().func_177972_a(in), 9);
                if (structure == null) {
                    this.active = false;
                    this.updateStructureInfoInternal(null);
                }
                else {
                    this.active = true;
                    MultiblockDetection.assignMultiBlock(this.func_145831_w(), this.func_174877_v(), structure.blocks);
                    this.updateStructureInfoInternal(structure);
                    if (wasActive) {
                        this.func_145831_w().func_184138_a(this.func_174877_v(), state, state, 3);
                    }
                }
            }
        }
        if (wasActive != this.active) {
            this.func_145831_w().func_184138_a(this.func_174877_v(), state, state, 3);
            this.func_70296_d();
        }
    }
    
    protected final void updateStructureInfoInternal(MultiblockDetection.MultiblockStructure structure) {
        this.info = structure;
        if (structure == null) {
            structure = new MultiblockDetection.MultiblockStructure(0, 0, 0, (List<BlockPos>)ImmutableList.of((Object)this.field_174879_c));
        }
        if (this.info != null) {
            this.minPos = this.info.minPos.func_177982_a(1, 1, 1);
            this.maxPos = this.info.maxPos.func_177982_a(-1, this.hasCeiling() ? -1 : 0, -1);
        }
        else {
            final BlockPos field_174879_c = this.field_174879_c;
            this.maxPos = field_174879_c;
            this.minPos = field_174879_c;
        }
        this.updateStructureInfo(structure);
    }
    
    protected boolean hasCeiling() {
        return true;
    }
    
    protected abstract void updateStructureInfo(final MultiblockDetection.MultiblockStructure p0);
    
    public boolean isActive() {
        return this.active && (this.func_145831_w() == null || this.func_145831_w().field_72995_K || this.info != null);
    }
    
    public void setInvalid() {
        this.active = false;
        this.updateStructureInfoInternal(null);
    }
    
    public void func_145829_t() {
        super.func_145829_t();
        this.active = false;
    }
    
    @Nonnull
    public NBTTagCompound func_189515_b(NBTTagCompound compound) {
        compound = super.func_189515_b(compound);
        compound.func_74757_a("active", this.active);
        compound.func_74782_a("minPos", (NBTBase)TagUtil.writePos(this.minPos));
        compound.func_74782_a("maxPos", (NBTBase)TagUtil.writePos(this.maxPos));
        return compound;
    }
    
    public void func_145839_a(final NBTTagCompound compound) {
        super.func_145839_a(compound);
        this.active = compound.func_74767_n("active");
        this.minPos = TagUtil.readPos(compound.func_74775_l("minPos"));
        this.maxPos = TagUtil.readPos(compound.func_74775_l("maxPos"));
    }
    
    public SPacketUpdateTileEntity func_189518_D_() {
        final NBTTagCompound tag = new NBTTagCompound();
        this.func_189515_b(tag);
        return new SPacketUpdateTileEntity(this.func_174877_v(), this.func_145832_p(), tag);
    }
    
    public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
        final boolean wasActive = this.active;
        this.func_145839_a(pkt.func_148857_g());
        if (this.active != wasActive) {
            final IBlockState state = this.func_145831_w().func_180495_p(this.func_174877_v());
            this.func_145831_w().func_184138_a(this.func_174877_v(), state, state, 3);
        }
    }
    
    @Nonnull
    public NBTTagCompound func_189517_E_() {
        return this.func_189515_b(new NBTTagCompound());
    }
    
    public boolean isClientWorld() {
        return this.func_145831_w() != null && this.func_145831_w().field_72995_K;
    }
    
    public boolean isServerWorld() {
        return this.func_145831_w() != null && !this.func_145831_w().field_72995_K;
    }
}
