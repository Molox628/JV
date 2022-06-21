package slimeknights.tconstruct.tools.modifiers;

import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.utils.*;

public class ModLuck extends ModifierTrait
{
    protected static final int baseCount = 60;
    protected static final int maxLevel = 3;
    private final LuckAspect aspect;
    
    public ModLuck() {
        super("luck", 2970082, 3, 0);
        this.aspects.clear();
        this.aspect = new LuckAspect(this);
        this.addAspects(this.aspect, new ModifierAspect.CategoryAnyAspect(new Category[] { Category.HARVEST, Category.WEAPON, Category.PROJECTILE }));
    }
    
    public int getLuckLevel(final ItemStack itemStack) {
        return this.getLuckLevel(TinkerUtil.getModifierTag(itemStack, this.getModifierIdentifier()));
    }
    
    public int getLuckLevel(final NBTTagCompound modifierTag) {
        final ModifierNBT.IntegerNBT data = ModifierNBT.readInteger(modifierTag);
        return this.aspect.getLevel(data.current);
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment enchantment) {
        return enchantment != Enchantments.field_185306_r;
    }
    
    @Override
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        final int lvl = this.getLuckLevel(modifierTag);
        this.applyEnchantments(rootCompound, lvl);
    }
    
    @Override
    public void afterBlockBreak(final ItemStack tool, final World world, final IBlockState state, final BlockPos pos, final EntityLivingBase player, final boolean wasEffective) {
        this.rewardProgress(tool);
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
        if (player.field_70170_p.field_72995_K || !wasHit) {
            return;
        }
        for (int i = (int)(damageDealt / 2.0f); i > 0; --i) {
            this.rewardProgress(tool);
        }
    }
    
    public void rewardProgress(final ItemStack tool) {
        if (ModLuck.random.nextFloat() > 0.03f) {
            return;
        }
        try {
            if (this.canApply(tool, tool)) {
                this.apply(tool);
            }
        }
        catch (TinkerGuiException ex) {}
    }
    
    protected void applyEnchantments(final NBTTagCompound rootCompound, int lvl) {
        boolean harvest = false;
        boolean weapon = false;
        lvl = Math.min(lvl, Enchantments.field_185304_p.func_77325_b());
        for (final Category category : TagUtil.getCategories(rootCompound)) {
            if (category == Category.HARVEST) {
                harvest = true;
            }
            if (category == Category.WEAPON) {
                weapon = true;
            }
        }
        if (weapon) {
            while (lvl > ToolBuilder.getEnchantmentLevel(rootCompound, Enchantments.field_185304_p)) {
                ToolBuilder.addEnchantment(rootCompound, Enchantments.field_185304_p);
            }
        }
        if (harvest) {
            while (lvl > ToolBuilder.getEnchantmentLevel(rootCompound, Enchantments.field_185308_t)) {
                ToolBuilder.addEnchantment(rootCompound, Enchantments.field_185308_t);
            }
        }
    }
    
    @Override
    public String getTooltip(final NBTTagCompound modifierTag, final boolean detailed) {
        final int level = this.getLuckLevel(modifierTag);
        String tooltip = this.getLocalizedName();
        if (level > 0) {
            tooltip = tooltip + " " + TinkerUtil.getRomanNumeral(level);
        }
        if (detailed) {
            final ModifierNBT data = ModifierNBT.readInteger(modifierTag);
            tooltip = tooltip + " " + data.extraInfo;
        }
        return tooltip;
    }
    
    public static class LuckAspect extends MultiAspect
    {
        public LuckAspect(final IModifier parent) {
            super(parent, 5931746, 3, 60, 1);
            this.freeModifierAspect = new FreeFirstModifierAspect(parent, 1);
        }
        
        @Override
        protected int getMaxForLevel(final int level) {
            return this.countPerLevel * level * (level + 1) / 2;
        }
        
        public int getLevel(final int current) {
            int i;
            for (i = 0; current >= this.getMaxForLevel(i + 1); ++i) {}
            return i;
        }
    }
}
