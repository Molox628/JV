package slimeknights.tconstruct.tools.tools;

import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.tools.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;
import javax.annotation.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.world.*;
import net.minecraftforge.event.*;
import net.minecraftforge.common.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.entity.item.*;
import slimeknights.tconstruct.library.tools.*;

public class Kama extends AoeToolCore
{
    public static final ImmutableSet<Material> effective_materials;
    
    public Kama(final PartMaterialType... requiredComponents) {
        super(requiredComponents);
        this.addCategory(Category.HARVEST, Category.WEAPON);
        this.setHarvestLevel("shears", 0);
    }
    
    public Kama() {
        this(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toolRod), PartMaterialType.head(TinkerTools.kamaHead), PartMaterialType.extra(TinkerTools.binding) });
    }
    
    @Override
    public float damagePotential() {
        return 1.0f;
    }
    
    @Override
    public double attackSpeed() {
        return 1.2999999523162842;
    }
    
    @Override
    public boolean isEffective(final IBlockState state) {
        return Kama.effective_materials.contains((Object)state.func_185904_a());
    }
    
    @Override
    protected boolean breakBlock(final ItemStack stack, final BlockPos pos, final EntityPlayer player) {
        return !ToolHelper.isBroken(stack) && ToolHelper.shearBlock(stack, player.func_130014_f_(), player, pos);
    }
    
    @Override
    protected void breakExtraBlock(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos pos, final BlockPos refPos) {
        ToolHelper.shearExtraBlock(stack, world, player, pos, refPos);
    }
    
    @Nonnull
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        final ItemStack itemStackIn = playerIn.func_184586_b(hand);
        if (ToolHelper.isBroken(itemStackIn)) {
            return (ActionResult<ItemStack>)ActionResult.newResult(EnumActionResult.FAIL, (Object)itemStackIn);
        }
        final RayTraceResult trace = this.func_77621_a(worldIn, playerIn, true);
        if (trace == null || trace.field_72313_a != RayTraceResult.Type.BLOCK) {
            return (ActionResult<ItemStack>)ActionResult.newResult(EnumActionResult.PASS, (Object)itemStackIn);
        }
        final int fortune = ToolHelper.getFortuneLevel(itemStackIn);
        final BlockPos origin = trace.func_178782_a();
        boolean harvestedSomething = false;
        for (final BlockPos pos : this.getAOEBlocks(itemStackIn, playerIn.func_130014_f_(), playerIn, origin)) {
            harvestedSomething |= this.harvestCrop(itemStackIn, worldIn, playerIn, pos, fortune);
        }
        harvestedSomething |= this.harvestCrop(itemStackIn, worldIn, playerIn, origin, fortune);
        if (harvestedSomething) {
            playerIn.func_130014_f_().func_184148_a((EntityPlayer)null, playerIn.field_70165_t, playerIn.field_70163_u, playerIn.field_70161_v, SoundEvents.field_187730_dW, playerIn.func_184176_by(), 1.0f, 1.0f);
            this.swingTool(playerIn, hand);
            return (ActionResult<ItemStack>)ActionResult.newResult(EnumActionResult.SUCCESS, (Object)itemStackIn);
        }
        return (ActionResult<ItemStack>)ActionResult.newResult(EnumActionResult.PASS, (Object)itemStackIn);
    }
    
    protected boolean canHarvestCrop(final IBlockState state) {
        boolean canHarvest = state.func_177230_c() instanceof BlockReed;
        if (state.func_177230_c() instanceof BlockCrops && ((BlockCrops)state.func_177230_c()).func_185525_y(state)) {
            canHarvest = true;
        }
        if (state.func_177230_c() instanceof BlockNetherWart && (int)state.func_177229_b((IProperty)BlockNetherWart.field_176486_a) == 3) {
            canHarvest = true;
        }
        return canHarvest;
    }
    
    public boolean func_111207_a(final ItemStack stack, final EntityPlayer player, final EntityLivingBase target, final EnumHand hand) {
        if (target instanceof IShearable) {
            final int fortune = EnchantmentHelper.func_77506_a(Enchantments.field_185308_t, stack);
            if (this.shearEntity(stack, player.func_130014_f_(), player, (Entity)target, fortune)) {
                this.swingTool(player, hand);
                return true;
            }
        }
        return false;
    }
    
    protected void swingTool(final EntityPlayer player, final EnumHand hand) {
        player.func_184609_a(hand);
        player.func_184810_cG();
    }
    
    public boolean harvestCrop(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos pos, final int fortune) {
        final IBlockState state = world.func_180495_p(pos);
        boolean canHarvest = this.canHarvestCrop(state);
        if (state.func_177230_c() instanceof BlockReed && !(world.func_180495_p(pos.func_177977_b()).func_177230_c() instanceof BlockReed)) {
            canHarvest = false;
        }
        final TinkerToolEvent.OnScytheHarvest event = TinkerToolEvent.OnScytheHarvest.fireEvent(stack, player, world, pos, state, canHarvest);
        if (event.isCanceled()) {
            return false;
        }
        if (event.getResult() == Event.Result.DENY) {
            return true;
        }
        if (event.getResult() == Event.Result.ALLOW) {
            canHarvest = true;
        }
        if (!canHarvest) {
            return false;
        }
        if (!world.field_72995_K) {
            this.doHarvestCrop(stack, world, player, pos, fortune, state);
        }
        return true;
    }
    
    protected void doHarvestCrop(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos pos, final int fortune, final IBlockState state) {
        float chance = 1.0f;
        final NonNullList<ItemStack> drops = (NonNullList<ItemStack>)NonNullList.func_191196_a();
        state.func_177230_c().getDrops((NonNullList)drops, (IBlockAccess)world, pos, state, fortune);
        chance = ForgeEventFactory.fireBlockHarvesting((List)drops, world, pos, state, fortune, chance, false, player);
        IPlantable seed = null;
        for (final ItemStack drop : drops) {
            if (drop != null && drop.func_77973_b() instanceof IPlantable) {
                seed = (IPlantable)drop.func_77973_b();
                drop.func_190918_g(1);
                if (drop.func_190926_b()) {
                    drops.remove((Object)drop);
                    break;
                }
                break;
            }
        }
        boolean replanted = false;
        if (seed != null) {
            final IBlockState down = world.func_180495_p(pos.func_177977_b());
            if (down.func_177230_c().canSustainPlant(down, (IBlockAccess)world, pos.func_177977_b(), EnumFacing.UP, seed)) {
                final IBlockState crop = seed.getPlant((IBlockAccess)world, pos);
                if (crop != state) {
                    world.func_175656_a(pos, seed.getPlant((IBlockAccess)world, pos));
                    ToolHelper.damageTool(stack, 1, (EntityLivingBase)player);
                }
                for (final ItemStack drop2 : drops) {
                    if (world.field_73012_v.nextFloat() <= chance) {
                        Block.func_180635_a(world, pos, drop2);
                    }
                }
                replanted = true;
            }
        }
        if (!replanted) {
            this.breakExtraBlock(stack, player.func_130014_f_(), player, pos, pos);
        }
    }
    
    public boolean shearEntity(final ItemStack stack, final World world, final EntityPlayer player, final Entity entity, final int fortune) {
        if (!(entity instanceof IShearable)) {
            return false;
        }
        final IShearable shearable = (IShearable)entity;
        if (shearable.isShearable(stack, (IBlockAccess)world, entity.func_180425_c())) {
            if (!world.field_72995_K) {
                final List<ItemStack> drops = (List<ItemStack>)shearable.onSheared(stack, (IBlockAccess)world, entity.func_180425_c(), fortune);
                final Random rand = world.field_73012_v;
                for (final ItemStack drop : drops) {
                    final EntityItem entityItem = entity.func_70099_a(drop, 1.0f);
                    if (entityItem != null) {
                        final EntityItem entityItem2 = entityItem;
                        entityItem2.field_70181_x += rand.nextFloat() * 0.05f;
                        final EntityItem entityItem3 = entityItem;
                        entityItem3.field_70159_w += (rand.nextFloat() - rand.nextFloat()) * 0.1f;
                        final EntityItem entityItem4 = entityItem;
                        entityItem4.field_70179_y += (rand.nextFloat() - rand.nextFloat()) * 0.1f;
                    }
                }
            }
            ToolHelper.damageTool(stack, 1, (EntityLivingBase)player);
            return true;
        }
        return false;
    }
    
    @Override
    protected ToolNBT buildTagData(final List<slimeknights.tconstruct.library.materials.Material> materials) {
        return this.buildDefaultTag(materials);
    }
    
    static {
        effective_materials = ImmutableSet.of((Object)Material.field_151569_G, (Object)Material.field_151584_j, (Object)Material.field_151585_k, (Object)Material.field_151582_l, (Object)Material.field_151572_C, (Object)Material.field_151570_A, (Object[])new Material[0]);
    }
}
