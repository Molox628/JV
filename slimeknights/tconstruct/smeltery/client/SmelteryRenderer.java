package slimeknights.tconstruct.smeltery.client;

import slimeknights.tconstruct.smeltery.tileentity.*;
import javax.annotation.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;
import net.minecraftforge.client.*;
import net.minecraft.util.math.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.tileentity.*;

public class SmelteryRenderer extends SmelteryTankRenderer<TileSmeltery>
{
    public void render(@Nonnull final TileSmeltery smeltery, final double x, final double y, final double z, final float partialTicks, final int destroyStage, final float alpha) {
        if (!smeltery.isActive()) {
            return;
        }
        final BlockPos tilePos = smeltery.func_174877_v();
        final BlockPos minPos = smeltery.getMinPos();
        final BlockPos maxPos = smeltery.getMaxPos();
        if (minPos == null || maxPos == null) {
            return;
        }
        this.renderFluids(smeltery.getTank(), tilePos, minPos, maxPos, x, y, z);
        final double x2 = minPos.func_177958_n() - tilePos.func_177958_n();
        final double y2 = minPos.func_177956_o() - tilePos.func_177956_o();
        final double z2 = minPos.func_177952_p() - tilePos.func_177952_p();
        final int xd = 1 + maxPos.func_177958_n() - minPos.func_177958_n();
        final int zd = 1 + maxPos.func_177952_p() - minPos.func_177952_p();
        final int layer = xd * zd;
        Minecraft.func_71410_x().field_71446_o.func_110577_a(TextureMap.field_110575_b);
        RenderUtil.pre(x, y, z);
        GlStateManager.func_179129_p();
        GlStateManager.func_179137_b(x2, y2, z2);
        GlStateManager.func_179109_b(0.5f, 0.5f, 0.5f);
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
        RenderHelper.func_74519_b();
        for (int i = 0; i < smeltery.func_70302_i_(); ++i) {
            if (smeltery.isStackInSlot(i)) {
                final int h = i / layer;
                final int i2 = i % layer;
                final BlockPos pos = minPos.func_177982_a(i2 % xd, h, i2 / xd);
                final int brightness = smeltery.func_145831_w().func_175626_b(pos, 0);
                final ItemStack stack = smeltery.func_70301_a(i);
                final boolean isItem = !(stack.func_77973_b() instanceof ItemBlock);
                OpenGlHelper.func_77475_a(OpenGlHelper.field_77476_b, brightness % 65536 / 1.0f, brightness / 65536 / 1.0f);
                GlStateManager.func_179109_b((float)(i2 % xd), (float)h, (float)(i2 / xd));
                if (isItem) {
                    GlStateManager.func_179114_b(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                IBakedModel model = Minecraft.func_71410_x().func_175599_af().func_184393_a(stack, smeltery.func_145831_w(), (EntityLivingBase)null);
                model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.NONE, false);
                Minecraft.func_71410_x().func_175599_af().func_180454_a(stack, model);
                if (isItem) {
                    GlStateManager.func_179114_b(90.0f, 1.0f, 0.0f, 0.0f);
                }
                GlStateManager.func_179109_b((float)(-i2 % xd), (float)(-h), (float)(-i2 / xd));
            }
        }
        RenderHelper.func_74519_b();
        GlStateManager.func_179089_o();
        RenderUtil.post();
    }
}
