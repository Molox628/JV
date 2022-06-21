package slimeknights.tconstruct.gadgets;

import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.fml.common.*;
import net.minecraft.util.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.library.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.gui.*;
import net.minecraft.inventory.*;
import net.minecraft.client.renderer.*;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber({ Side.CLIENT })
public class WoodenHopperGUIDrawEvent
{
    private static final ResourceLocation HOPPER_GUI_TEXTURE;
    
    @SubscribeEvent
    public static void addTooltip(final ItemTooltipEvent itemTooltipEvent) {
        if (itemTooltipEvent.getItemStack().func_77973_b() == Item.func_150898_a((Block)TinkerGadgets.woodenHopper)) {
            itemTooltipEvent.getToolTip().add(Util.translate("item.tconstruct.wooden_hopper.tooltip", new Object[0]));
        }
    }
    
    @SubscribeEvent
    public static void onWoodenHopperDrawGui(final GuiOpenEvent guiOpenEvent) {
        if (guiOpenEvent.getGui() instanceof GuiHopper) {
            final GuiHopper gui = (GuiHopper)guiOpenEvent.getGui();
            if (gui.field_147083_w.func_70302_i_() == 1) {
                guiOpenEvent.setGui((GuiScreen)new GuiWoodenHopper((InventoryPlayer)gui.field_147084_v, gui.field_147083_w));
            }
        }
    }
    
    static {
        HOPPER_GUI_TEXTURE = new ResourceLocation("tconstruct", "textures/gui/hopper.png");
    }
    
    @SideOnly(Side.CLIENT)
    private static class GuiWoodenHopper extends GuiHopper
    {
        public GuiWoodenHopper(final InventoryPlayer playerInv, final IInventory hopperInv) {
            super(playerInv, hopperInv);
            final Slot func_75139_a = this.field_147002_h.func_75139_a(0);
            func_75139_a.field_75223_e += 36;
        }
        
        protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146297_k.func_110434_K().func_110577_a(WoodenHopperGUIDrawEvent.HOPPER_GUI_TEXTURE);
            final int i = (this.field_146294_l - this.field_146999_f) / 2;
            final int j = (this.field_146295_m - this.field_147000_g) / 2;
            this.func_73729_b(i, j, 0, 0, this.field_146999_f, this.field_147000_g);
        }
    }
}
