package slimeknights.tconstruct.tools.modifiers;

import net.minecraftforge.common.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.tools.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraftforge.event.entity.player.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraftforge.event.world.*;
import net.minecraft.nbt.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;

public class ModBlasting extends ModifierTrait
{
    public ModBlasting() {
        super("blasting", 16755235, 3, 0);
        final ListIterator<ModifierAspect> iter = this.aspects.listIterator();
        while (iter.hasNext()) {
            if (iter.next() == ModifierAspect.freeModifier) {
                iter.set(new ModifierAspect.FreeFirstModifierAspect(this, 1));
            }
        }
        this.addAspects(ModifierAspect.harvestOnly);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment enchantment) {
        return enchantment != Enchantments.field_185306_r && enchantment != Enchantments.field_185304_p && enchantment != Enchantments.field_185308_t;
    }
    
    @Override
    public boolean canApplyTogether(final IToolMod toolmod) {
        final String id = toolmod.getIdentifier();
        return !id.equals(TinkerModifiers.modLuck.getModifierIdentifier()) && !id.equals(TinkerModifiers.modSilktouch.getIdentifier()) && !id.equals(TinkerTraits.squeaky.getIdentifier()) && !id.equals(TinkerTraits.autosmelt.getIdentifier());
    }
    
    private int getLevel(final ItemStack tool) {
        return ModifierNBT.readInteger(TinkerUtil.getModifierTag(tool, this.getModifierIdentifier())).level;
    }
    
    @Override
    public void miningSpeed(final ItemStack tool, final PlayerEvent.BreakSpeed event) {
        final World world = event.getEntityPlayer().func_130014_f_();
        float speed = ToolHelper.getActualMiningSpeed(tool);
        final int level = this.getLevel(tool);
        final float hardness = event.getState().func_185887_b(world, event.getPos());
        if (hardness <= 0.0f) {
            return;
        }
        speed *= hardness;
        if (level > 2) {
            speed /= 1.1f;
        }
        else if (level > 1) {
            speed /= 5.0f;
        }
        else {
            speed /= 10.0f;
        }
        final float weight1 = level / (float)this.maxLevel;
        final float weight2 = 1.0f - level / (float)this.maxLevel;
        final float totalSpeed = speed * weight1 + event.getOriginalSpeed() * weight2;
        event.setNewSpeed(totalSpeed);
    }
    
    private float getBlockDestroyChange(final ItemStack tool) {
        final float level = (float)this.getLevel(tool);
        final float chancePerLevel = 1.0f / this.maxLevel;
        return level * chancePerLevel;
    }
    
    @Override
    public void afterBlockBreak(final ItemStack tool, final World world, final IBlockState state, final BlockPos pos, final EntityLivingBase player, final boolean wasEffective) {
        final EnumParticleTypes particleType = (ModBlasting.random.nextInt(20) == 0) ? EnumParticleTypes.EXPLOSION_LARGE : EnumParticleTypes.EXPLOSION_NORMAL;
        world.func_175688_a(particleType, pos.func_177958_n() + 0.5, pos.func_177956_o() + 0.5, pos.func_177952_p() + 0.5, 0.0, 0.0, 0.0, new int[0]);
    }
    
    @Override
    public void blockHarvestDrops(final ItemStack tool, final BlockEvent.HarvestDropsEvent event) {
        final float chance = 1.0f - this.getBlockDestroyChange(tool);
        event.setDropChance(event.getDropChance() * chance);
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final String loc = String.format("modifier.%s.extra", this.getIdentifier());
        final float chance = this.getBlockDestroyChange(tool);
        return (List<String>)ImmutableList.of((Object)Util.translateFormatted(loc, Util.dfPercent.format(chance)));
    }
    
    @Override
    public int getPriority() {
        return 200;
    }
}
