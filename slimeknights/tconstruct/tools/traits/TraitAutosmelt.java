package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.tools.*;
import net.minecraft.item.*;
import net.minecraftforge.event.world.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.item.crafting.*;
import net.minecraft.enchantment.*;
import slimeknights.tconstruct.common.config.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;

public class TraitAutosmelt extends AbstractTrait
{
    public TraitAutosmelt() {
        super("autosmelt", 16733440);
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment enchantment) {
        return enchantment != Enchantments.field_185306_r;
    }
    
    @Override
    public boolean canApplyTogether(final IToolMod toolmod) {
        return !toolmod.getIdentifier().equals(TinkerTraits.squeaky.getIdentifier()) && !toolmod.getIdentifier().equals(TinkerModifiers.modSilktouch.getIdentifier());
    }
    
    @Override
    public void blockHarvestDrops(final ItemStack tool, final BlockEvent.HarvestDropsEvent event) {
        if (ToolHelper.isToolEffective2(tool, event.getState())) {
            final ListIterator<ItemStack> iter = event.getDrops().listIterator();
            while (iter.hasNext()) {
                final ItemStack drop = iter.next();
                ItemStack smelted = FurnaceRecipes.func_77602_a().func_151395_a(drop);
                if (!smelted.func_190926_b()) {
                    smelted = smelted.func_77946_l();
                    smelted.func_190920_e(drop.func_190916_E());
                    final int fortune = EnchantmentHelper.func_77506_a(Enchantments.field_185308_t, tool);
                    if (Config.autosmeltlapis && fortune > 0) {
                        smelted.func_190920_e(smelted.func_190916_E() * TraitAutosmelt.random.nextInt(fortune + 1) + 1);
                    }
                    iter.set(smelted);
                    float xp = FurnaceRecipes.func_77602_a().func_151398_b(smelted);
                    if (xp < 1.0f && Math.random() < xp) {
                        ++xp;
                    }
                    if (xp < 1.0f) {
                        continue;
                    }
                    event.getState().func_177230_c().func_180637_b(event.getWorld(), event.getPos(), (int)xp);
                }
            }
        }
    }
    
    @Override
    public void afterBlockBreak(final ItemStack tool, final World world, final IBlockState state, final BlockPos pos, final EntityLivingBase player, final boolean wasEffective) {
        if (world.field_72995_K && wasEffective) {
            for (int i = 0; i < 3; ++i) {
                world.func_175688_a(EnumParticleTypes.FLAME, pos.func_177958_n() + TraitAutosmelt.random.nextDouble(), pos.func_177956_o() + TraitAutosmelt.random.nextDouble(), pos.func_177952_p() + TraitAutosmelt.random.nextDouble(), 0.0, 0.0, 0.0, new int[0]);
            }
        }
    }
}
