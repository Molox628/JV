package slimeknights.tconstruct.library.client.crosshair;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import javax.annotation.*;

@SideOnly(Side.CLIENT)
public final class CrosshairRenderEvents
{
    public static final CrosshairRenderEvents INSTANCE;
    private static final Minecraft mc;
    
    private CrosshairRenderEvents() {
    }
    
    @SubscribeEvent
    public void onCrosshairRender(final RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            return;
        }
        final EntityPlayer entityPlayer = (EntityPlayer)CrosshairRenderEvents.mc.field_71439_g;
        final ItemStack itemStack = this.getItemstack(entityPlayer);
        if (itemStack.func_190926_b()) {
            return;
        }
        final ICustomCrosshairUser customCrosshairUser = (ICustomCrosshairUser)itemStack.func_77973_b();
        final ICrosshair crosshair = customCrosshairUser.getCrosshair(itemStack, entityPlayer);
        if (crosshair == ICrosshair.DEFAULT) {
            return;
        }
        final float width = (float)event.getResolution().func_78326_a();
        final float height = (float)event.getResolution().func_78328_b();
        crosshair.render(customCrosshairUser.getCrosshairState(itemStack, entityPlayer), width, height, event.getPartialTicks());
        event.setCanceled(true);
        CrosshairRenderEvents.mc.func_110434_K().func_110577_a(Gui.field_110324_m);
        if (CrosshairRenderEvents.mc.field_71474_y.field_186716_M == 1) {
            final int resW = event.getResolution().func_78326_a();
            final int resH = event.getResolution().func_78328_b();
            GlStateManager.func_179147_l();
            GlStateManager.func_187428_a(GlStateManager.SourceFactor.ONE_MINUS_DST_COLOR, GlStateManager.DestFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.func_179141_d();
            final float f = CrosshairRenderEvents.mc.field_71439_g.func_184825_o(0.0f);
            if (f < 1.0f) {
                final int i = resH / 2 - 7 + 16;
                final int j = resW / 2 - 7;
                final int k = (int)(f * 17.0f);
                CrosshairRenderEvents.mc.field_71456_v.func_73729_b(j, i, 36, 94, 16, 4);
                CrosshairRenderEvents.mc.field_71456_v.func_73729_b(j, i, 52, 94, k, 4);
            }
        }
        OpenGlHelper.func_148821_a(770, 771, 1, 0);
        GlStateManager.func_179084_k();
    }
    
    @Nonnull
    private ItemStack getItemstack(final EntityPlayer entityPlayer) {
        ItemStack itemStack = ItemStack.field_190927_a;
        if (entityPlayer.func_184587_cr() && this.isValidItem(entityPlayer.func_184607_cu())) {
            itemStack = entityPlayer.func_184607_cu();
        }
        if (itemStack.func_190926_b() && this.isValidItem(entityPlayer.func_184614_ca())) {
            itemStack = entityPlayer.func_184614_ca();
        }
        if (itemStack.func_190926_b() && this.isValidItem(entityPlayer.func_184592_cb())) {
            itemStack = entityPlayer.func_184592_cb();
        }
        return itemStack;
    }
    
    private boolean isValidItem(final ItemStack itemStack) {
        return itemStack.func_77973_b() instanceof ICustomCrosshairUser;
    }
    
    static {
        INSTANCE = new CrosshairRenderEvents();
        mc = Minecraft.func_71410_x();
    }
}
