package slimeknights.tconstruct.gadgets;

import net.minecraftforge.common.*;
import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.color.*;
import slimeknights.tconstruct.gadgets.block.*;
import net.minecraft.block.properties.*;
import net.minecraftforge.client.model.*;
import net.minecraft.client.renderer.block.statemap.*;
import slimeknights.tconstruct.common.*;
import slimeknights.tconstruct.gadgets.item.*;
import java.util.*;
import net.minecraftforge.fml.client.registry.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.gadgets.entity.*;
import slimeknights.tconstruct.gadgets.client.*;
import slimeknights.tconstruct.library.client.model.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.item.*;
import javax.annotation.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;

public class GadgetClientProxy extends ClientProxy
{
    @Override
    public void preInit() {
        super.preInit();
        MinecraftForge.EVENT_BUS.register((Object)new GadgetClientEvents());
    }
    
    @Override
    public void init() {
        final Minecraft minecraft = Minecraft.func_71410_x();
        minecraft.func_184125_al().func_186722_a((@Nonnull state, access, pos, tintIndex) -> ((BlockSlime.SlimeType)state.func_177229_b((IProperty)BlockSlimeChannel.TYPE)).getColor(), new Block[] { (Block)TinkerGadgets.slimeChannel });
        final ItemColors colors = minecraft.getItemColors();
        colors.func_186731_a((@Nonnull stack, tintIndex) -> BlockSlime.SlimeType.fromMeta(stack.func_77952_i()).getColor(), new Block[] { (Block)TinkerGadgets.slimeChannel });
        colors.func_186730_a((@Nonnull stack, tintIndex) -> TinkerGadgets.slimeBoots.func_82814_b(stack), new Item[] { (Item)TinkerGadgets.slimeBoots, (Item)TinkerGadgets.slimeSling });
        super.init();
    }
    
    @Override
    public void registerModels() {
        super.registerModels();
        ModelLoader.setCustomStateMapper((Block)TinkerGadgets.slimeChannel, (IStateMapper)new PropertyStateMapper("slime_channel", (PropertyEnum<?>)BlockSlimeChannel.SIDE, (IProperty<?>[])new IProperty[] { (IProperty)BlockSlimeChannel.TYPE }));
        ModelRegisterUtil.registerItemModel(TinkerGadgets.stoneTorch);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.stoneLadder);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.punji);
        ModelRegisterUtil.registerItemModel((Block)TinkerGadgets.rack);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.woodRail);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.woodRailTrapdoor);
        ModelRegisterUtil.registerItemModel((Block)TinkerGadgets.woodenHopper);
        ModelRegisterUtil.registerItemModel((Block)TinkerGadgets.slimeChannel);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerGadgets.driedClay);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerGadgets.brownstone);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerGadgets.driedClaySlab);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerGadgets.brownstoneSlab);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerGadgets.brownstoneSlab2);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.driedClayStairs);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.driedBrickStairs);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.brownstoneStairsSmooth);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.brownstoneStairsRough);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.brownstoneStairsPaver);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.brownstoneStairsBrick);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.brownstoneStairsBrickCracked);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.brownstoneStairsBrickFancy);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.brownstoneStairsBrickSquare);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.brownstoneStairsBrickTriangle);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.brownstoneStairsBrickSmall);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.brownstoneStairsRoad);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.brownstoneStairsTile);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.brownstoneStairsCreeper);
        ModelRegisterUtil.registerItemModel((Item)TinkerGadgets.slimeSling);
        ModelRegisterUtil.registerItemModel((Item)TinkerGadgets.slimeBoots);
        ModelRegisterUtil.registerItemModel((Item)TinkerGadgets.piggybackPack);
        ModelRegisterUtil.registerItemModel(TinkerGadgets.stoneStick);
        for (final ItemThrowball.ThrowballType type : ItemThrowball.ThrowballType.values()) {
            ModelRegisterUtil.registerItemModel((Item)TinkerGadgets.throwball, type.ordinal(), type.name().toLowerCase(Locale.US));
        }
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityFancyItemFrame.class, (IRenderFactory)RenderFancyItemFrame.FACTORY);
        for (final EntityFancyItemFrame.FrameType type2 : EntityFancyItemFrame.FrameType.values()) {
            for (final boolean withMap : new boolean[] { true, false }) {
                final String variant = RenderFancyItemFrame.getVariant(type2, withMap);
                final ModelResourceLocation loc = Util.getModelResource("fancy_frame", variant);
                ModelLoader.registerItemVariants((Item)TinkerGadgets.fancyFrame, new ResourceLocation[] { (ResourceLocation)loc });
                if (!withMap) {
                    ModelLoader.setCustomModelResourceLocation((Item)TinkerGadgets.fancyFrame, type2.ordinal(), loc);
                }
            }
        }
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityThrowball.class, (IRenderFactory)RenderThrowball.FACTORY);
        TinkerGadgets.spaghetti.registerItemModels();
        ModelRegisterUtil.registerToolModel((Item)TinkerGadgets.momsSpaghetti, Util.getResource("moms_spaghetti" + ToolModelLoader.EXTENSION));
    }
    
    @Override
    public void postInit() {
        super.postInit();
    }
}
