package slimeknights.tconstruct.tools.tools;

import net.minecraft.block.material.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.client.particle.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.library.events.*;
import net.minecraftforge.common.*;
import java.util.*;
import com.google.common.collect.*;
import gnu.trove.set.hash.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class LumberAxe extends AoeToolCore
{
    public static final ImmutableSet<Material> effective_materials;
    public static final float DURABILITY_MODIFIER = 2.0f;
    
    public LumberAxe() {
        super(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toughToolRod), PartMaterialType.head(TinkerTools.broadAxeHead), PartMaterialType.head(TinkerTools.largePlate), PartMaterialType.extra(TinkerTools.toughBinding) });
        this.addCategory(Category.HARVEST);
        this.setHarvestLevel("axe", 0);
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            this.addDefaultSubItems((List<ItemStack>)subItems, new slimeknights.tconstruct.library.materials.Material[0]);
            this.addInfiTool((List<ItemStack>)subItems, "InfiChopper");
        }
    }
    
    @Override
    public float miningSpeedModifier() {
        return 0.35f;
    }
    
    @Override
    public float damagePotential() {
        return 1.2f;
    }
    
    @Override
    public double attackSpeed() {
        return 0.800000011920929;
    }
    
    @Override
    public boolean isEffective(final IBlockState state) {
        return LumberAxe.effective_materials.contains((Object)state.func_185904_a()) || ItemAxe.field_150917_c.contains(state.func_177230_c());
    }
    
    @Override
    public float knockback() {
        return 1.5f;
    }
    
    @Override
    public boolean dealDamage(final ItemStack stack, final EntityLivingBase player, final Entity entity, final float damage) {
        final boolean hit = super.dealDamage(stack, player, entity, damage);
        if (hit && this.readyForSpecialAttack(player)) {
            TinkerTools.proxy.spawnAttackParticle(Particles.LUMBERAXE_ATTACK, (Entity)player, 0.8);
        }
        return hit;
    }
    
    @Override
    public boolean onBlockStartBreak(final ItemStack itemstack, final BlockPos pos, final EntityPlayer player) {
        if (!ToolHelper.isBroken(itemstack) && ToolHelper.isToolEffective2(itemstack, player.func_130014_f_().func_180495_p(pos)) && detectTree(player.func_130014_f_(), pos)) {
            return fellTree(itemstack, pos, player);
        }
        return super.onBlockStartBreak(itemstack, pos, player);
    }
    
    @Override
    public ImmutableList<BlockPos> getAOEBlocks(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos origin) {
        if (!ToolHelper.isToolEffective2(stack, world.func_180495_p(origin))) {
            return (ImmutableList<BlockPos>)ImmutableList.of();
        }
        return ToolHelper.calcAOEBlocks(stack, world, player, origin, 3, 3, 3);
    }
    
    @Override
    public int[] getRepairParts() {
        return new int[] { 1, 2 };
    }
    
    @Override
    public float getRepairModifierForPart(final int index) {
        return (index == 1) ? 2.0f : 1.25f;
    }
    
    public ToolNBT buildTagData(final List<slimeknights.tconstruct.library.materials.Material> materials) {
        final HandleMaterialStats handle = materials.get(0).getStatsOrUnknown("handle");
        final HeadMaterialStats head = materials.get(1).getStatsOrUnknown("head");
        final HeadMaterialStats plate = materials.get(2).getStatsOrUnknown("head");
        final ExtraMaterialStats binding = materials.get(3).getStatsOrUnknown("extra");
        final ToolNBT data = new ToolNBT();
        data.head(head, plate);
        data.extra(binding);
        data.handle(handle);
        final ToolNBT toolNBT = data;
        toolNBT.attack += 2.0f;
        final ToolNBT toolNBT2 = data;
        toolNBT2.durability *= (int)2.0f;
        return data;
    }
    
    public static boolean detectTree(final World world, final BlockPos origin) {
        BlockPos pos = null;
        final Stack<BlockPos> candidates = new Stack<BlockPos>();
        candidates.add(origin);
        while (!candidates.isEmpty()) {
            final BlockPos candidate = candidates.pop();
            if ((pos == null || candidate.func_177956_o() > pos.func_177956_o()) && isLog(world, candidate)) {
                for (pos = candidate.func_177984_a(); isLog(world, pos); pos = pos.func_177984_a()) {}
                candidates.add(pos.func_177978_c());
                candidates.add(pos.func_177974_f());
                candidates.add(pos.func_177968_d());
                candidates.add(pos.func_177976_e());
            }
        }
        if (pos == null) {
            return false;
        }
        final int d = 3;
        final int o = -1;
        int leaves = 0;
        for (int x = 0; x < d; ++x) {
            for (int y = 0; y < d; ++y) {
                for (int z = 0; z < d; ++z) {
                    final BlockPos leaf = pos.func_177982_a(o + x, o + y, o + z);
                    final IBlockState state = world.func_180495_p(leaf);
                    if (state.func_177230_c().isLeaves(state, (IBlockAccess)world, leaf) && ++leaves >= 5) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private static boolean isLog(final World world, final BlockPos pos) {
        return world.func_180495_p(pos).func_177230_c().isWood((IBlockAccess)world, pos);
    }
    
    public static boolean fellTree(final ItemStack itemstack, final BlockPos start, final EntityPlayer player) {
        if (player.func_130014_f_().field_72995_K) {
            return true;
        }
        final TinkerToolEvent.ExtraBlockBreak event = TinkerToolEvent.ExtraBlockBreak.fireEvent(itemstack, player, player.func_130014_f_().func_180495_p(start), 3, 3, 3, -1);
        int speed = Math.round(event.width * event.height * event.depth / 27.0f);
        if (event.distance > 0) {
            speed = event.distance + 1;
        }
        MinecraftForge.EVENT_BUS.register((Object)new TreeChopTask(itemstack, start, player, speed));
        return true;
    }
    
    static {
        effective_materials = ImmutableSet.of((Object)Material.field_151575_d, (Object)Material.field_151572_C, (Object)Material.field_151570_A);
    }
    
    public static class TreeChopTask
    {
        public final World world;
        public final EntityPlayer player;
        public final ItemStack tool;
        public final int blocksPerTick;
        public Queue<BlockPos> blocks;
        public Set<BlockPos> visited;
        
        public TreeChopTask(final ItemStack tool, final BlockPos start, final EntityPlayer player, final int blocksPerTick) {
            this.blocks = (Queue<BlockPos>)Lists.newLinkedList();
            this.visited = (Set<BlockPos>)new THashSet();
            this.world = player.func_130014_f_();
            this.player = player;
            this.tool = tool;
            this.blocksPerTick = blocksPerTick;
            this.blocks.add(start);
        }
        
        @SubscribeEvent
        public void chopChop(final TickEvent.WorldTickEvent event) {
            if (event.side.isClient()) {
                this.finish();
                return;
            }
            if (event.world.field_73011_w.getDimension() != this.world.field_73011_w.getDimension()) {
                return;
            }
            int left = this.blocksPerTick;
            while (left > 0) {
                if (this.blocks.isEmpty() || ToolHelper.isBroken(this.tool)) {
                    this.finish();
                    return;
                }
                final BlockPos pos = this.blocks.remove();
                if (!this.visited.add(pos)) {
                    continue;
                }
                if (!isLog(this.world, pos)) {
                    continue;
                }
                if (!ToolHelper.isToolEffective2(this.tool, this.world.func_180495_p(pos))) {
                    continue;
                }
                for (final EnumFacing facing : new EnumFacing[] { EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.SOUTH, EnumFacing.WEST }) {
                    final BlockPos pos2 = pos.func_177972_a(facing);
                    if (!this.visited.contains(pos2)) {
                        this.blocks.add(pos2);
                    }
                }
                for (int x = 0; x < 3; ++x) {
                    for (int z = 0; z < 3; ++z) {
                        final BlockPos pos3 = pos.func_177982_a(-1 + x, 1, -1 + z);
                        if (!this.visited.contains(pos3)) {
                            this.blocks.add(pos3);
                        }
                    }
                }
                ToolHelper.breakExtraBlock(this.tool, this.world, this.player, pos, pos);
                --left;
            }
        }
        
        private void finish() {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
        }
    }
}
