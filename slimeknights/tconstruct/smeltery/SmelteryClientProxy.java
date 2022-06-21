package slimeknights.tconstruct.smeltery;

import net.minecraftforge.common.*;
import net.minecraft.block.properties.*;
import net.minecraftforge.client.model.*;
import net.minecraft.block.*;
import net.minecraft.client.renderer.block.statemap.*;
import slimeknights.tconstruct.common.*;
import net.minecraft.client.renderer.block.model.*;
import slimeknights.tconstruct.smeltery.block.*;
import net.minecraftforge.fml.client.registry.*;
import net.minecraft.client.renderer.tileentity.*;
import slimeknights.tconstruct.smeltery.client.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.client.*;
import net.minecraft.client.renderer.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.color.*;
import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraftforge.fluids.*;

public class SmelteryClientProxy extends ClientProxy
{
    @Override
    public void preInit() {
        super.preInit();
        MinecraftForge.EVENT_BUS.register((Object)new SmelteryClientEvents());
    }
    
    @Override
    public void registerModels() {
        ModelLoader.setCustomStateMapper((Block)TinkerSmeltery.searedGlass, (IStateMapper)new StateMap.Builder().func_178442_a(new IProperty[] { (IProperty)BlockSearedGlass.TYPE }).func_178441_a());
        ModelRegisterUtil.registerItemModel((Block)TinkerSmeltery.smelteryController);
        ModelRegisterUtil.registerItemModel((Block)TinkerSmeltery.faucet);
        ModelRegisterUtil.registerItemModel((Block)TinkerSmeltery.channel);
        ModelRegisterUtil.registerItemModel((Block)TinkerSmeltery.searedGlass);
        ModelRegisterUtil.registerItemModel(TinkerSmeltery.searedFurnaceController);
        ModelRegisterUtil.registerItemModel(TinkerSmeltery.tinkerTankController);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerSmeltery.searedBlock);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerSmeltery.castingBlock);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerSmeltery.searedSlab);
        ModelRegisterUtil.registerItemBlockMeta((Block)TinkerSmeltery.searedSlab2);
        ModelRegisterUtil.registerItemModel(TinkerSmeltery.searedStairsStone);
        ModelRegisterUtil.registerItemModel(TinkerSmeltery.searedStairsCobble);
        ModelRegisterUtil.registerItemModel(TinkerSmeltery.searedStairsPaver);
        ModelRegisterUtil.registerItemModel(TinkerSmeltery.searedStairsBrick);
        ModelRegisterUtil.registerItemModel(TinkerSmeltery.searedStairsBrickCracked);
        ModelRegisterUtil.registerItemModel(TinkerSmeltery.searedStairsBrickFancy);
        ModelRegisterUtil.registerItemModel(TinkerSmeltery.searedStairsBrickSquare);
        ModelRegisterUtil.registerItemModel(TinkerSmeltery.searedStairsBrickTriangle);
        ModelRegisterUtil.registerItemModel(TinkerSmeltery.searedStairsBrickSmall);
        ModelRegisterUtil.registerItemModel(TinkerSmeltery.searedStairsRoad);
        ModelRegisterUtil.registerItemModel(TinkerSmeltery.searedStairsTile);
        ModelRegisterUtil.registerItemModel(TinkerSmeltery.searedStairsCreeper);
        final Item drain = Item.func_150898_a((Block)TinkerSmeltery.smelteryIO);
        for (final BlockSmelteryIO.IOType type : BlockSmelteryIO.IOType.values()) {
            final String variant = String.format("%s=%s,%s=%s", BlockSmelteryIO.FACING.func_177701_a(), BlockSmelteryIO.FACING.func_177702_a((Enum)EnumFacing.SOUTH), BlockSmelteryIO.TYPE.func_177701_a(), BlockSmelteryIO.TYPE.func_177702_a((Enum)type));
            ModelLoader.setCustomModelResourceLocation(drain, type.meta, new ModelResourceLocation(drain.getRegistryName(), variant));
        }
        final Item tank = Item.func_150898_a((Block)TinkerSmeltery.searedTank);
        for (final BlockTank.TankType type2 : BlockTank.TankType.values()) {
            ModelLoader.setCustomModelResourceLocation(tank, type2.meta, new ModelResourceLocation(tank.getRegistryName(), type2.func_176610_l()));
        }
        ClientRegistry.bindTileEntitySpecialRenderer((Class)TileTank.class, (TileEntitySpecialRenderer)new TankRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)TileSmeltery.class, (TileEntitySpecialRenderer)new SmelteryRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)TileTinkerTank.class, (TileEntitySpecialRenderer)new TinkerTankRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)TileFaucet.class, (TileEntitySpecialRenderer)new FaucetRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)TileChannel.class, (TileEntitySpecialRenderer)new ChannelRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)TileCastingTable.class, (TileEntitySpecialRenderer)new CastingRenderer.Table());
        ClientRegistry.bindTileEntitySpecialRenderer((Class)TileCastingBasin.class, (TileEntitySpecialRenderer)new CastingRenderer.Basin());
        final ResourceLocation castLoc = SmelteryClientEvents.locBlankCast;
        CustomTextureCreator.castModelLocation = new ResourceLocation(castLoc.func_110624_b(), "item/" + castLoc.func_110623_a());
        ModelLoader.setCustomMeshDefinition((Item)TinkerSmeltery.cast, (ItemMeshDefinition)new PatternMeshDefinition(castLoc));
        if (Config.claycasts) {
            final ResourceLocation clayCastLoc = SmelteryClientEvents.locClayCast;
            CustomTextureCreator.castModelLocation = new ResourceLocation(clayCastLoc.func_110624_b(), "item/" + clayCastLoc.func_110623_a());
            ModelLoader.setCustomMeshDefinition((Item)TinkerSmeltery.clayCast, (ItemMeshDefinition)new PatternMeshDefinition(clayCastLoc));
        }
        TinkerSmeltery.castCustom.registerItemModels();
    }
    
    @Override
    public void init() {
        final Minecraft minecraft = Minecraft.func_71410_x();
        final ItemColors colors = minecraft.getItemColors();
        colors.func_186731_a((@Nonnull stack, tintIndex) -> {
            if (!stack.func_77942_o()) {
                return 16777215;
            }
            final FluidStack fluid = FluidStack.loadFluidStackFromNBT(stack.func_77978_p());
            if (fluid != null && fluid.amount > 0 && fluid.getFluid() != null) {
                return fluid.getFluid().getColor(fluid);
            }
            return 16777215;
        }, new Block[] { (Block)TinkerSmeltery.searedTank });
        super.init();
    }
}
