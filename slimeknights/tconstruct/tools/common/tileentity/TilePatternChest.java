package slimeknights.tconstruct.tools.common.tileentity;

import slimeknights.mantle.common.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.inventory.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import net.minecraft.client.gui.inventory.*;
import slimeknights.tconstruct.tools.common.client.*;
import net.minecraftforge.fml.relauncher.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.smeltery.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.item.*;

public class TilePatternChest extends TileTinkerChest implements IInventoryGui
{
    public TilePatternChest() {
        super("gui.patternchest.name", 256, 1);
    }
    
    public Container createContainer(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (Container)new ContainerPatternChest(inventoryplayer, this);
    }
    
    @SideOnly(Side.CLIENT)
    public GuiContainer createGui(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (GuiContainer)new GuiPatternChest(inventoryplayer, world, pos, this);
    }
    
    public boolean func_94041_b(final int slot, @Nonnull final ItemStack itemstack) {
        if (itemstack.func_190926_b() || (!(itemstack.func_77973_b() instanceof IPattern) && !(itemstack.func_77973_b() instanceof ICast))) {
            return false;
        }
        final Item part = Pattern.getPartFromTag(itemstack);
        boolean hasContents = false;
        for (int i = 0; i < this.func_70302_i_(); ++i) {
            if (this.isStackInSlot(i)) {
                hasContents = true;
                break;
            }
        }
        if (!hasContents) {
            return true;
        }
        final boolean castChest = this.isCastChest();
        if (castChest && !(itemstack.func_77973_b() instanceof ICast)) {
            return false;
        }
        if (!castChest && (!(itemstack.func_77973_b() instanceof IPattern) || itemstack.func_77973_b() instanceof ICast)) {
            return false;
        }
        if (part == null) {
            for (int i = 0; i < this.func_70302_i_(); ++i) {
                final ItemStack inv = this.func_70301_a(i);
                if (!inv.func_190926_b()) {
                    if (ItemStack.func_179545_c(itemstack, inv) && ItemStack.func_77970_a(itemstack, inv)) {
                        return false;
                    }
                }
            }
            return true;
        }
        for (int i = 0; i < this.func_70302_i_(); ++i) {
            final Item slotPart = Pattern.getPartFromTag(this.func_70301_a(i));
            if (slotPart != null) {
                if (this.func_70301_a(i).func_77973_b() != itemstack.func_77973_b()) {
                    return false;
                }
                if (slotPart == part) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Nonnull
    public String func_70005_c_() {
        if (this.isCastChest()) {
            return "gui.castchest.name";
        }
        return super.func_70005_c_();
    }
    
    public boolean isCastChest() {
        for (int i = 0; i < this.func_70302_i_(); ++i) {
            if (this.func_70301_a(i).func_77973_b() instanceof ICast) {
                return true;
            }
        }
        return false;
    }
}
