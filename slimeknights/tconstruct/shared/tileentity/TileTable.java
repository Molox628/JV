package slimeknights.tconstruct.shared.tileentity;

import slimeknights.mantle.tileentity.*;
import slimeknights.tconstruct.library.client.model.*;
import net.minecraftforge.common.property.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.nbt.*;
import javax.annotation.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.tools.common.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import net.minecraft.client.*;
import net.minecraft.block.state.*;

public class TileTable extends TileInventory
{
    public static final String FEET_TAG = "textureBlock";
    public static final String FACE_TAG = "facing";
    protected int displaySlot;
    
    public TileTable() {
        super("", 0, 0);
        this.displaySlot = 0;
    }
    
    public TileTable(final String name, final int inventorySize) {
        super(name, inventorySize);
        this.displaySlot = 0;
    }
    
    public TileTable(final String name, final int inventorySize, final int maxStackSize) {
        super(name, inventorySize, maxStackSize);
        this.displaySlot = 0;
    }
    
    public IExtendedBlockState writeExtendedBlockState(IExtendedBlockState state) {
        String texture = this.getTileData().func_74779_i("texture");
        if (texture.isEmpty()) {
            final ItemStack stack = new ItemStack(this.getTileData().func_74775_l("textureBlock"));
            if (!stack.func_190926_b()) {
                final Block block = Block.func_149634_a(stack.func_77973_b());
                texture = ModelHelper.getTextureFromBlock(block, stack.func_77952_i()).func_94215_i();
                this.getTileData().func_74778_a("texture", texture);
            }
        }
        if (!texture.isEmpty()) {
            state = state.withProperty((IUnlistedProperty)BlockTable.TEXTURE, (Object)texture);
        }
        final EnumFacing facing = this.getFacing();
        state = state.withProperty((IUnlistedProperty)BlockTable.FACING, (Object)facing);
        state = this.setInventoryDisplay(state);
        return state;
    }
    
    protected IExtendedBlockState setInventoryDisplay(final IExtendedBlockState state) {
        final PropertyTableItem.TableItems toDisplay = new PropertyTableItem.TableItems();
        if (!this.func_70301_a(this.displaySlot).func_190926_b()) {
            final ItemStack stack = this.func_70301_a(this.displaySlot);
            final PropertyTableItem.TableItem item = getTableItem(stack, this.func_145831_w(), null);
            if (item != null) {
                toDisplay.items.add(item);
            }
        }
        return state.withProperty((IUnlistedProperty)BlockTable.INVENTORY, (Object)toDisplay);
    }
    
    public boolean isInventoryEmpty() {
        for (int i = 0; i < this.func_70302_i_(); ++i) {
            if (!this.func_70301_a(i).func_190926_b()) {
                return false;
            }
        }
        return true;
    }
    
    @SideOnly(Side.CLIENT)
    public static PropertyTableItem.TableItem getTableItem(final ItemStack stack, final World world, final EntityLivingBase entity) {
        if (stack.func_190926_b()) {
            return null;
        }
        if (!Config.renderTableItems) {
            return new PropertyTableItem.TableItem(stack, null);
        }
        final IBakedModel model = ModelHelper.getBakedModelForItem(stack, world, entity);
        final PropertyTableItem.TableItem item = new PropertyTableItem.TableItem(stack, model, 0.0f, -0.46875f, 0.0f, 0.8f, 1.5707964f);
        if (stack.func_77973_b() instanceof ItemBlock) {
            if (!(Block.func_149634_a(stack.func_77973_b()) instanceof BlockPane)) {
                item.y = -0.3125f;
                item.r = 0.0f;
            }
            item.s = 0.375f;
        }
        return item;
    }
    
    public SPacketUpdateTileEntity func_189518_D_() {
        final NBTTagCompound tag = this.getTileData().func_74737_b();
        this.func_189515_b(tag);
        return new SPacketUpdateTileEntity(this.func_174877_v(), this.func_145832_p(), tag);
    }
    
    public void onDataPacket(final NetworkManager net, final SPacketUpdateTileEntity pkt) {
        final NBTTagCompound tag = pkt.func_148857_g();
        final NBTBase feet = tag.func_74781_a("textureBlock");
        if (feet != null) {
            this.getTileData().func_74782_a("textureBlock", feet);
        }
        final NBTBase facing = tag.func_74781_a("facing");
        if (facing != null) {
            this.getTileData().func_74782_a("facing", facing);
        }
        this.func_145839_a(tag);
    }
    
    @Nonnull
    public NBTTagCompound func_189517_E_() {
        return this.func_189515_b(new NBTTagCompound());
    }
    
    public void handleUpdateTag(@Nonnull final NBTTagCompound tag) {
        this.func_145839_a(tag);
    }
    
    public void setFacing(final EnumFacing face) {
        this.getTileData().func_74768_a("facing", face.func_176745_a());
    }
    
    public EnumFacing getFacing() {
        return EnumFacing.func_82600_a(this.getTileData().func_74762_e("facing"));
    }
    
    public void updateTextureBlock(final NBTTagCompound tag) {
        this.getTileData().func_74782_a("textureBlock", (NBTBase)tag);
    }
    
    public NBTTagCompound getTextureBlock() {
        return this.getTileData().func_74775_l("textureBlock");
    }
    
    public void func_70299_a(final int slot, final ItemStack itemstack) {
        if (this.func_145831_w() != null && this.func_145831_w() instanceof WorldServer && !this.func_145831_w().field_72995_K && !ItemStack.func_77989_b(itemstack, this.func_70301_a(slot))) {
            TinkerNetwork.sendToClients((WorldServer)this.func_145831_w(), this.field_174879_c, (AbstractPacket)new InventorySlotSyncPacket(itemstack, slot, this.field_174879_c));
        }
        super.func_70299_a(slot, itemstack);
        if (this.func_145831_w() != null && this.func_145831_w().field_72995_K && Config.renderTableItems) {
            Minecraft.func_71410_x().field_71438_f.func_184376_a((World)null, this.field_174879_c, (IBlockState)null, (IBlockState)null, 0);
        }
    }
}
