package slimeknights.tconstruct.library.modifiers;

import net.minecraft.item.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.util.text.translation.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.util.text.*;
import slimeknights.tconstruct.library.*;

public abstract class ModifierAspect
{
    public static final ModifierAspect freeModifier;
    public static final ModifierAspect toolOnly;
    public static final ModifierAspect harvestOnly;
    public static final ModifierAspect weaponOnly;
    public static final ModifierAspect aoeOnly;
    public static final ModifierAspect projectileOnly;
    protected final IModifier parent;
    
    protected ModifierAspect() {
        this.parent = null;
    }
    
    public ModifierAspect(final IModifier parent) {
        this.parent = parent;
    }
    
    public abstract boolean canApply(final ItemStack p0, final ItemStack p1) throws TinkerGuiException;
    
    public abstract void updateNBT(final NBTTagCompound p0, final NBTTagCompound p1);
    
    static {
        freeModifier = new FreeModifierAspect(1);
        toolOnly = new CategoryAspect(new Category[] { Category.TOOL });
        harvestOnly = new CategoryAspect(new Category[] { Category.HARVEST });
        weaponOnly = new CategoryAspect(new Category[] { Category.WEAPON });
        aoeOnly = new CategoryAspect(new Category[] { Category.AOE });
        projectileOnly = new CategoryAspect(new Category[] { Category.PROJECTILE });
    }
    
    public static class FreeModifierAspect extends ModifierAspect
    {
        private final int requiredModifiers;
        
        public FreeModifierAspect(final int requiredModifiers) {
            this.requiredModifiers = requiredModifiers;
        }
        
        protected FreeModifierAspect(final IModifier parent, final int requiredModifiers) {
            super(parent);
            this.requiredModifiers = requiredModifiers;
        }
        
        @Override
        public boolean canApply(final ItemStack stack, final ItemStack original) throws TinkerGuiException {
            final NBTTagCompound toolTag = TagUtil.getToolTag(stack);
            if (ToolHelper.getFreeModifiers(stack) < this.requiredModifiers) {
                final String error = I18n.func_74837_a("gui.error.not_enough_modifiers", new Object[] { this.requiredModifiers });
                throw new TinkerGuiException(error);
            }
            return true;
        }
        
        @Override
        public void updateNBT(final NBTTagCompound root, final NBTTagCompound modifierTag) {
            final NBTTagCompound toolTag = TagUtil.getToolTag(root);
            final int modifiers = toolTag.func_74762_e("FreeModifiers") - this.requiredModifiers;
            toolTag.func_74768_a("FreeModifiers", Math.max(0, modifiers));
            int usedModifiers = TagUtil.getBaseModifiersUsed(root);
            usedModifiers += this.requiredModifiers;
            TagUtil.setBaseModifiersUsed(root, usedModifiers);
        }
    }
    
    public static class FreeFirstModifierAspect extends FreeModifierAspect
    {
        public FreeFirstModifierAspect(final IModifier parent, final int requiredModifiers) {
            super(parent, requiredModifiers);
        }
        
        @Override
        public boolean canApply(final ItemStack stack, final ItemStack original) throws TinkerGuiException {
            return TinkerUtil.hasModifier(TagUtil.getTagSafe(stack), this.parent.getIdentifier()) || super.canApply(stack, original);
        }
        
        @Override
        public void updateNBT(final NBTTagCompound root, final NBTTagCompound modifierTag) {
            if (modifierTag.func_74764_b("modifierUsed")) {
                return;
            }
            super.updateNBT(root, modifierTag);
            modifierTag.func_74757_a("modifierUsed", true);
        }
    }
    
    public static class DataAspect extends ModifierAspect
    {
        private final int color;
        
        public DataAspect(final IModifier parent, final TextFormatting color) {
            this(parent, Util.enumChatFormattingToColor(color));
        }
        
        public DataAspect(final IModifier parent, final int color) {
            super(parent);
            this.color = color;
        }
        
        public <T extends slimeknights.tconstruct.library.modifiers.IModifier> DataAspect(final T parent) {
            super((IModifier)parent);
            this.color = ((IModifierDisplay)parent).getColor();
        }
        
        @Override
        public boolean canApply(final ItemStack stack, final ItemStack original) {
            return true;
        }
        
        @Override
        public void updateNBT(final NBTTagCompound root, final NBTTagCompound modifierTag) {
            final ModifierNBT data = ModifierNBT.readTag(modifierTag);
            data.identifier = this.parent.getIdentifier();
            data.color = this.color;
            data.write(modifierTag);
        }
    }
    
    public static class MultiAspect extends ModifierAspect
    {
        protected final int countPerLevel;
        protected DataAspect dataAspect;
        protected LevelAspect levelAspect;
        protected FreeModifierAspect freeModifierAspect;
        
        public <T extends slimeknights.tconstruct.library.modifiers.IModifier> MultiAspect(final T parent, final int maxLevel, final int countPerLevel, final int modifiersNeeded) {
            this((IModifier)parent, ((IModifierDisplay)parent).getColor(), maxLevel, countPerLevel, modifiersNeeded);
        }
        
        public MultiAspect(final IModifier parent, final int color, final int maxLevel, final int countPerLevel, final int modifiersNeeded) {
            super(parent);
            this.countPerLevel = countPerLevel;
            this.dataAspect = new DataAspect(parent, color);
            this.freeModifierAspect = new FreeModifierAspect(modifiersNeeded);
            this.levelAspect = new LevelAspect(parent, maxLevel);
        }
        
        public MultiAspect(final IModifier parent, final int color, final int count) {
            this(parent, color, 1, count, 1);
        }
        
        protected int getMaxForLevel(final int level) {
            return this.countPerLevel * level;
        }
        
        @Override
        public boolean canApply(final ItemStack stack, final ItemStack original) throws TinkerGuiException {
            final NBTTagCompound modifierTag = TinkerUtil.getModifierTag(stack, this.parent.getIdentifier());
            final ModifierNBT.IntegerNBT data = this.getData(modifierTag);
            if (data.current >= this.getMaxForLevel(data.level)) {
                if (!this.levelAspect.canApply(stack, original)) {
                    return false;
                }
                if (!this.freeModifierAspect.canApply(stack, original)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public void updateNBT(final NBTTagCompound root, final NBTTagCompound modifierTag) {
            this.dataAspect.updateNBT(root, modifierTag);
            ModifierNBT.IntegerNBT data = this.getData(modifierTag);
            if (data.current >= this.getMaxForLevel(data.level)) {
                this.freeModifierAspect.updateNBT(root, modifierTag);
                this.levelAspect.updateNBT(root, modifierTag);
                data = this.getData(modifierTag);
            }
            data.max = this.getMaxForLevel(data.level);
            final ModifierNBT.IntegerNBT integerNBT = data;
            ++integerNBT.current;
            data.write(modifierTag);
        }
        
        private ModifierNBT.IntegerNBT getData(final NBTTagCompound tag) {
            final ModifierNBT.IntegerNBT data = ModifierNBT.readInteger(tag);
            if (data.max == 0) {
                data.max = this.getMaxForLevel(data.level);
            }
            return data;
        }
    }
    
    public static class CategoryAspect extends ModifierAspect
    {
        protected final Category[] category;
        
        public CategoryAspect(final Category... category) {
            this.category = category;
        }
        
        @Override
        public boolean canApply(final ItemStack stack, final ItemStack original) {
            for (final Category cat : this.category) {
                if (!ToolHelper.hasCategory(stack, cat)) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public void updateNBT(final NBTTagCompound root, final NBTTagCompound modifierTag) {
        }
    }
    
    public static class CategoryAnyAspect extends CategoryAspect
    {
        public CategoryAnyAspect(final Category... category) {
            super(category);
        }
        
        @Override
        public boolean canApply(final ItemStack stack, final ItemStack original) {
            for (final Category cat : this.category) {
                if (ToolHelper.hasCategory(stack, cat)) {
                    return true;
                }
            }
            return false;
        }
    }
    
    public static class SingleAspect extends ModifierAspect
    {
        public SingleAspect(final IModifier parent) {
            super(parent);
        }
        
        @Override
        public boolean canApply(final ItemStack stack, final ItemStack original) throws TinkerGuiException {
            if (!TinkerUtil.hasModifier(TagUtil.getTagSafe(stack), this.parent.getIdentifier())) {
                return true;
            }
            if (TinkerUtil.hasModifier(TagUtil.getTagSafe(original), this.parent.getIdentifier())) {
                throw new TinkerGuiException(I18n.func_74837_a("gui.error.single_modifier", new Object[] { this.parent.getLocalizedName() }));
            }
            return false;
        }
        
        @Override
        public void updateNBT(final NBTTagCompound root, final NBTTagCompound modifierTag) {
        }
    }
    
    public static class LevelAspect extends ModifierAspect
    {
        private final int maxLevel;
        
        public LevelAspect(final IModifier parent, final int maxLevel) {
            super(parent);
            this.maxLevel = maxLevel;
        }
        
        @Override
        public boolean canApply(final ItemStack stack, final ItemStack original) throws TinkerGuiException {
            final int levelNew = ModifierNBT.readTag(TinkerUtil.getModifierTag(stack, this.parent.getIdentifier())).level;
            final int levelOld = ModifierNBT.readTag(TinkerUtil.getModifierTag(original, this.parent.getIdentifier())).level;
            if (levelNew - levelOld > 0) {
                return false;
            }
            if (levelNew >= this.maxLevel) {
                throw new TinkerGuiException(I18n.func_74837_a("gui.error.max_level_modifier", new Object[] { this.parent.getLocalizedName() }));
            }
            return true;
        }
        
        @Override
        public void updateNBT(final NBTTagCompound root, final NBTTagCompound modifierTag) {
            final ModifierNBT tag;
            final ModifierNBT data = tag = ModifierNBT.readTag(modifierTag);
            ++tag.level;
            data.write(modifierTag);
        }
    }
}
