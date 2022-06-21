package slimeknights.tconstruct.common;

import net.minecraft.client.*;
import slimeknights.tconstruct.library.client.model.*;
import net.minecraftforge.client.model.*;
import slimeknights.tconstruct.library.client.material.*;
import slimeknights.tconstruct.library.client.material.deserializers.*;
import net.minecraftforge.common.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.materials.*;
import java.util.stream.*;
import java.util.function.*;
import slimeknights.tconstruct.tools.harvest.*;
import com.google.common.collect.*;
import java.util.*;
import slimeknights.tconstruct.tools.*;
import net.minecraft.client.resources.*;
import slimeknights.tconstruct.library.book.*;
import slimeknights.tconstruct.library.client.crosshair.*;
import net.minecraft.client.gui.*;
import slimeknights.tconstruct.library.*;
import slimeknights.mantle.network.*;
import net.minecraft.client.particle.*;
import slimeknights.tconstruct.shared.*;
import slimeknights.tconstruct.library.client.particle.*;
import slimeknights.tconstruct.tools.common.client.particle.*;
import slimeknights.tconstruct.shared.client.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.client.entity.*;
import net.minecraft.world.*;
import net.minecraftforge.event.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import javax.annotation.*;
import net.minecraft.client.renderer.block.model.*;
import slimeknights.tconstruct.library.tools.*;

public abstract class ClientProxy extends CommonProxy
{
    public static Material[] RenderMaterials;
    public static Material RenderMaterialString;
    public static final ResourceLocation BOOK_MODIFY;
    private static final Minecraft mc;
    public static CustomFontRenderer fontRenderer;
    protected static final ToolModelLoader loader;
    protected static final MaterialModelLoader materialLoader;
    protected static final ModifierModelLoader modifierLoader;
    
    public static void initClient() {
        ModelLoaderRegistry.registerLoader((ICustomModelLoader)ClientProxy.loader);
        ModelLoaderRegistry.registerLoader((ICustomModelLoader)ClientProxy.materialLoader);
        ModelLoaderRegistry.registerLoader((ICustomModelLoader)ClientProxy.modifierLoader);
        MaterialRenderInfoLoader.addRenderInfo("colored", ColoredRenderInfoDeserializer.class);
        MaterialRenderInfoLoader.addRenderInfo("multicolor", MultiColorRenderInfoDeserializer.class);
        MaterialRenderInfoLoader.addRenderInfo("inverse_multicolor", InverseMultiColorRenderInfoDeserializer.class);
        MaterialRenderInfoLoader.addRenderInfo("metal", MetalRenderInfoDeserializer.class);
        MaterialRenderInfoLoader.addRenderInfo("metal_textured", TexturedMetalRenderInfoDeserializer.class);
        MaterialRenderInfoLoader.addRenderInfo("block", BlockRenderInfoDeserializer.class);
        MinecraftForge.EVENT_BUS.register((Object)CustomTextureCreator.INSTANCE);
    }
    
    public static void initRenderMaterials() {
        ClientProxy.RenderMaterials = new Material[4];
        (ClientProxy.RenderMaterials[0] = new MaterialGUI("_internal_render1")).setRenderInfo(6835742);
        (ClientProxy.RenderMaterials[1] = new MaterialGUI("_internal_render2")).setRenderInfo(12698049);
        (ClientProxy.RenderMaterials[2] = new MaterialGUI("_internal_render3")).setRenderInfo(2324189);
        (ClientProxy.RenderMaterials[3] = new MaterialGUI("_internal_render4")).setRenderInfo(7423664);
        (ClientProxy.RenderMaterialString = new MaterialGUI("_internal_renderString")).setRenderInfo(16777215);
        Stream.concat((Stream<?>)Stream.of((T[])ClientProxy.RenderMaterials), (Stream<?>)Stream.of((T)ClientProxy.RenderMaterialString)).forEach((Consumer<? super Object>)TinkerRegistry::addMaterial);
    }
    
    public static void initRenderer() {
        if (TinkerHarvestTools.pickaxe != null) {
            TinkerRegistry.tabTools.setDisplayIcon(TinkerHarvestTools.pickaxe.buildItemForRendering((List<Material>)ImmutableList.of((Object)ClientProxy.RenderMaterials[0], (Object)ClientProxy.RenderMaterials[1], (Object)ClientProxy.RenderMaterials[2])));
        }
        if (TinkerTools.pickHead != null) {
            TinkerRegistry.tabParts.setDisplayIcon(TinkerTools.pickHead.getItemstackWithMaterial(ClientProxy.RenderMaterials[2]));
        }
        final IReloadableResourceManager resourceManager = (IReloadableResourceManager)ClientProxy.mc.func_110442_L();
        resourceManager.func_110542_a((IResourceManagerReloadListener)MaterialRenderInfoLoader.INSTANCE);
        resourceManager.func_110542_a((IResourceManagerReloadListener)CustomTextureCreator.INSTANCE);
        ClientProxy.fontRenderer = new CustomFontRenderer(ClientProxy.mc.field_71474_y, new ResourceLocation("textures/font/ascii.png"), ClientProxy.mc.field_71446_o);
        if (ClientProxy.mc.field_71474_y.field_74363_ab != null) {
            ClientProxy.fontRenderer.func_78264_a(ClientProxy.mc.func_135016_M().func_135042_a() || ClientProxy.mc.field_71474_y.field_151455_aw);
            ClientProxy.fontRenderer.func_78275_b(ClientProxy.mc.func_135016_M().func_135044_b());
        }
        resourceManager.func_110542_a((IResourceManagerReloadListener)ClientProxy.fontRenderer);
        final FontRenderer bookRenderer = new CustomFontRenderer(ClientProxy.mc.field_71474_y, new ResourceLocation("textures/font/ascii.png"), ClientProxy.mc.field_71446_o);
        bookRenderer.func_78264_a(true);
        if (ClientProxy.mc.field_71474_y.field_74363_ab != null) {
            ClientProxy.fontRenderer.func_78275_b(ClientProxy.mc.func_135016_M().func_135044_b());
        }
        TinkerBook.INSTANCE.fontRenderer = bookRenderer;
        MinecraftForge.EVENT_BUS.register((Object)CrosshairRenderEvents.INSTANCE);
    }
    
    protected void registerItemModelTiC(final ItemStack item, final String name) {
        if (item != null && !StringUtils.func_151246_b(name)) {
            ModelRegisterUtil.registerItemModel(item, Util.getResource(name));
        }
    }
    
    @Override
    public void sendPacketToServerOnly(final AbstractPacket packet) {
        TinkerNetwork.sendToServer(packet);
    }
    
    @Override
    public void spawnParticle(final Particles particleType, World world, final double x, final double y, final double z, final double xSpeed, final double ySpeed, final double zSpeed, final int... data) {
        if (world == null) {
            world = (World)ClientProxy.mc.field_71441_e;
        }
        Particle effect = createParticle(particleType, world, x, y, z, xSpeed, ySpeed, zSpeed, data);
        ClientProxy.mc.field_71452_i.func_78873_a(effect);
        if (particleType == Particles.EFFECT && data[0] > 1) {
            for (int i = 0; i < data[0] - 1; ++i) {
                effect = createParticle(particleType, world, x, y, z, xSpeed, ySpeed, zSpeed, data);
                ClientProxy.mc.field_71452_i.func_78873_a(effect);
            }
        }
    }
    
    @Override
    public void spawnSlimeParticle(final World world, final double x, final double y, final double z) {
        ClientProxy.mc.field_71452_i.func_78873_a((Particle)new EntitySlimeFx(world, x, y, z, TinkerCommons.matSlimeBallBlue.func_77973_b(), TinkerCommons.matSlimeBallBlue.func_77952_i()));
    }
    
    public static Particle createParticle(final Particles type, final World world, final double x, final double y, final double z, final double xSpeed, final double ySpeed, final double zSpeed, final int... data) {
        switch (type) {
            case BLUE_SLIME: {
                return (Particle)new EntitySlimeFx(world, x, y, z, TinkerCommons.matSlimeBallBlue.func_77973_b(), TinkerCommons.matSlimeBallBlue.func_77952_i());
            }
            case CLEAVER_ATTACK: {
                return new ParticleAttackCleaver(world, x, y, z, xSpeed, ySpeed, zSpeed, ClientProxy.mc.func_110434_K());
            }
            case LONGSWORD_ATTACK: {
                return new ParticleAttackLongsword(world, x, y, z, xSpeed, ySpeed, zSpeed, ClientProxy.mc.func_110434_K());
            }
            case RAPIER_ATTACK: {
                return new ParticleAttackRapier(world, x, y, z, xSpeed, ySpeed, zSpeed, ClientProxy.mc.func_110434_K());
            }
            case HATCHET_ATTACK: {
                return new ParticleAttackHatchet(world, x, y, z, xSpeed, ySpeed, zSpeed, ClientProxy.mc.func_110434_K());
            }
            case LUMBERAXE_ATTACK: {
                return new ParticleAttackLumberAxe(world, x, y, z, xSpeed, ySpeed, zSpeed, ClientProxy.mc.func_110434_K());
            }
            case FRYPAN_ATTACK: {
                return new ParticleAttackFrypan(world, x, y, z, xSpeed, ySpeed, zSpeed, ClientProxy.mc.func_110434_K());
            }
            case HAMMER_ATTACK: {
                return new ParticleAttackHammer(world, x, y, z, xSpeed, ySpeed, zSpeed, ClientProxy.mc.func_110434_K());
            }
            case EFFECT: {
                return (Particle)new ParticleEffect(data[1], world, x, y, z, xSpeed, ySpeed, zSpeed);
            }
            case ENDSPEED: {
                return new ParticleEndspeed(world, x, y, z, xSpeed, ySpeed, zSpeed);
            }
            default: {
                return null;
            }
        }
    }
    
    @Override
    public void preventPlayerSlowdown(final Entity player, final float originalSpeed, final Item item) {
        if (player instanceof EntityPlayerSP) {
            final EntityPlayerSP playerSP = (EntityPlayerSP)player;
            final ItemStack usingItem = playerSP.func_184607_cu();
            if (!usingItem.func_190926_b() && usingItem.func_77973_b() == item) {
                final MovementInput field_71158_b = playerSP.field_71158_b;
                field_71158_b.field_192832_b *= originalSpeed * 5.0f;
                final MovementInput field_71158_b2 = playerSP.field_71158_b;
                field_71158_b2.field_78902_a *= originalSpeed * 5.0f;
            }
        }
    }
    
    @Override
    public void customExplosion(final World world, final Explosion explosion) {
        ForgeEventFactory.onExplosionStart(world, explosion);
        explosion.func_77278_a();
        explosion.func_77279_a(true);
    }
    
    @Override
    public void updateEquippedItemForRendering(final EnumHand hand) {
        ClientProxy.mc.func_175597_ag().func_187460_a(hand);
        ClientProxy.mc.func_175597_ag().func_78441_a();
    }
    
    static {
        BOOK_MODIFY = Util.getResource("textures/gui/book/modify.png");
        mc = Minecraft.func_71410_x();
        loader = new ToolModelLoader();
        materialLoader = new MaterialModelLoader();
        modifierLoader = new ModifierModelLoader();
    }
    
    public static class PatternMeshDefinition implements ItemMeshDefinition
    {
        private final ResourceLocation baseLocation;
        
        public PatternMeshDefinition(final ResourceLocation baseLocation) {
            this.baseLocation = baseLocation;
        }
        
        @Nonnull
        public ModelResourceLocation func_178113_a(@Nonnull final ItemStack stack) {
            final Item item = Pattern.getPartFromTag(stack);
            String suffix = "";
            if (item != null) {
                suffix = Pattern.getTextureIdentifier(item);
            }
            return new ModelResourceLocation(new ResourceLocation(this.baseLocation.func_110624_b(), this.baseLocation.func_110623_a() + suffix), "inventory");
        }
    }
}
