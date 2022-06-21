package slimeknights.tconstruct.tools.common.client;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.client.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.entity.player.*;
import com.google.common.collect.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.client.renderer.texture.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.client.event.*;
import slimeknights.tconstruct.tools.ranged.*;
import net.minecraft.client.resources.*;
import javax.annotation.*;

@SideOnly(Side.CLIENT)
public class RenderEvents implements IResourceManagerReloadListener
{
    private static final ResourceLocation widgetsTexPath;
    private final TextureAtlasSprite[] destroyBlockIcons;
    
    public RenderEvents() {
        this.destroyBlockIcons = new TextureAtlasSprite[10];
    }
    
    @SubscribeEvent
    public void renderExtraBlockBreak(final RenderWorldLastEvent event) {
        final PlayerControllerMP controllerMP = Minecraft.func_71410_x().field_71442_b;
        final EntityPlayer player = (EntityPlayer)Minecraft.func_71410_x().field_71439_g;
        final World world = player.func_130014_f_();
        ItemStack tool = player.func_184614_ca();
        if (!tool.func_190926_b()) {
            final Entity renderEntity = Minecraft.func_71410_x().func_175606_aa();
            if (renderEntity != null) {
                final double distance = controllerMP.func_78757_d();
                final RayTraceResult mop = renderEntity.func_174822_a(distance, event.getPartialTicks());
                if (mop != null) {
                    tool = DualToolHarvestUtils.getItemstackToUse((EntityLivingBase)player, world.func_180495_p(mop.func_178782_a()));
                    if (tool.func_77973_b() instanceof IAoeTool) {
                        final ImmutableList<BlockPos> extraBlocks = ((IAoeTool)tool.func_77973_b()).getAOEBlocks(tool, world, player, mop.func_178782_a());
                        for (final BlockPos pos : extraBlocks) {
                            event.getContext().func_72731_b(player, new RayTraceResult(new Vec3d(0.0, 0.0, 0.0), (EnumFacing)null, pos), 0, event.getPartialTicks());
                        }
                    }
                }
            }
        }
        if (controllerMP.field_78778_j) {
            tool = DualToolHarvestUtils.getItemstackToUse((EntityLivingBase)player, world.func_180495_p(controllerMP.field_178895_c));
            if (tool.func_77973_b() instanceof IAoeTool && ((IAoeTool)tool.func_77973_b()).isAoeHarvestTool()) {
                final BlockPos pos2 = controllerMP.field_178895_c;
                this.drawBlockDamageTexture(Tessellator.func_178181_a(), Tessellator.func_178181_a().func_178180_c(), (Entity)player, event.getPartialTicks(), world, (List<BlockPos>)((IAoeTool)tool.func_77973_b()).getAOEBlocks(tool, world, player, pos2));
            }
        }
    }
    
    public void drawBlockDamageTexture(final Tessellator tessellatorIn, final BufferBuilder bufferBuilder, final Entity entityIn, final float partialTicks, final World world, final List<BlockPos> blocks) {
        final double d0 = entityIn.field_70142_S + (entityIn.field_70165_t - entityIn.field_70142_S) * partialTicks;
        final double d2 = entityIn.field_70137_T + (entityIn.field_70163_u - entityIn.field_70137_T) * partialTicks;
        final double d3 = entityIn.field_70136_U + (entityIn.field_70161_v - entityIn.field_70136_U) * partialTicks;
        final TextureManager renderEngine = Minecraft.func_71410_x().field_71446_o;
        final int progress = (int)(Minecraft.func_71410_x().field_71442_b.field_78770_f * 10.0f) - 1;
        if (progress < 0) {
            return;
        }
        renderEngine.func_110577_a(TextureMap.field_110575_b);
        GlStateManager.func_179120_a(774, 768, 1, 0);
        GlStateManager.func_179147_l();
        GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 0.5f);
        GlStateManager.func_179136_a(-3.0f, -3.0f);
        GlStateManager.func_179088_q();
        GlStateManager.func_179092_a(516, 0.1f);
        GlStateManager.func_179141_d();
        GlStateManager.func_179094_E();
        bufferBuilder.func_181668_a(7, DefaultVertexFormats.field_176600_a);
        bufferBuilder.func_178969_c(-d0, -d2, -d3);
        bufferBuilder.func_78914_f();
        for (final BlockPos blockpos : blocks) {
            final double d4 = blockpos.func_177958_n() - d0;
            final double d5 = blockpos.func_177956_o() - d2;
            final double d6 = blockpos.func_177952_p() - d3;
            final Block block = world.func_180495_p(blockpos).func_177230_c();
            final TileEntity te = world.func_175625_s(blockpos);
            boolean hasBreak = block instanceof BlockChest || block instanceof BlockEnderChest || block instanceof BlockSign || block instanceof BlockSkull;
            if (!hasBreak) {
                hasBreak = (te != null && te.canRenderBreaking());
            }
            if (!hasBreak) {
                final IBlockState iblockstate = world.func_180495_p(blockpos);
                if (iblockstate.func_177230_c().func_149688_o(iblockstate) == Material.field_151579_a) {
                    continue;
                }
                final TextureAtlasSprite textureatlassprite = this.destroyBlockIcons[progress];
                final BlockRendererDispatcher blockrendererdispatcher = Minecraft.func_71410_x().func_175602_ab();
                blockrendererdispatcher.func_175020_a(iblockstate, blockpos, textureatlassprite, (IBlockAccess)world);
            }
        }
        tessellatorIn.func_78381_a();
        bufferBuilder.func_178969_c(0.0, 0.0, 0.0);
        GlStateManager.func_179118_c();
        GlStateManager.func_179136_a(0.0f, 0.0f);
        GlStateManager.func_179113_r();
        GlStateManager.func_179141_d();
        GlStateManager.func_179132_a(true);
        GlStateManager.func_179121_F();
    }
    
    @SubscribeEvent
    public void handRenderEvent(final RenderSpecificHandEvent event) {
        final EntityPlayer player = (EntityPlayer)Minecraft.func_71410_x().field_71439_g;
        if (event.getHand() == EnumHand.OFF_HAND && player.func_184587_cr()) {
            final ItemStack stack = player.func_184607_cu();
            if (!stack.func_190926_b() && stack.func_77975_n() == EnumAction.BOW) {
                event.setCanceled(true);
            }
        }
        final ItemStack mainStack = player.func_184614_ca();
        final RayTraceResult rt = Minecraft.func_71410_x().field_71476_x;
        if (!mainStack.func_190926_b() && rt != null && rt.field_72313_a == RayTraceResult.Type.BLOCK && DualToolHarvestUtils.shouldUseOffhand((EntityLivingBase)player, rt.func_178782_a(), mainStack)) {
            event.setCanceled(true);
            EnumHand hand;
            ItemStack itemStack;
            if (event.getHand() == EnumHand.MAIN_HAND) {
                hand = EnumHand.OFF_HAND;
                itemStack = player.func_184592_cb();
            }
            else {
                hand = EnumHand.MAIN_HAND;
                itemStack = player.func_184614_ca();
            }
            final ItemRenderer itemRenderer = Minecraft.func_71410_x().func_175597_ag();
            itemRenderer.func_187457_a((AbstractClientPlayer)Minecraft.func_71410_x().field_71439_g, event.getPartialTicks(), event.getInterpolatedPitch(), hand, event.getSwingProgress(), itemStack, event.getEquipProgress());
        }
    }
    
    @SubscribeEvent
    public void onFovEvent(final FOVUpdateEvent event) {
        final ItemStack stack = event.getEntity().func_184607_cu();
        if (!stack.func_190926_b()) {
            float zoom = 0.0f;
            float progress = 0.0f;
            if (stack.func_77973_b() == TinkerRangedWeapons.longBow) {
                zoom = 0.35f;
                progress = TinkerRangedWeapons.longBow.getDrawbackProgress(stack, (EntityLivingBase)event.getEntity());
            }
            if (zoom > 0.0f) {
                event.setNewfov(1.0f - progress * progress * zoom);
            }
        }
    }
    
    public void func_110549_a(@Nonnull final IResourceManager resourceManager) {
        final TextureMap texturemap = Minecraft.func_71410_x().func_147117_R();
        for (int i = 0; i < this.destroyBlockIcons.length; ++i) {
            this.destroyBlockIcons[i] = texturemap.func_110572_b("minecraft:blocks/destroy_stage_" + i);
        }
    }
    
    static {
        widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
    }
}
