package slimeknights.tconstruct.library.traits;

import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.*;

public abstract class AbstractTraitLeveled extends AbstractTrait
{
    protected final String name;
    protected final int levels;
    
    public AbstractTraitLeveled(final String identifier, final int color, final int maxLevels, final int levels) {
        this(identifier, String.valueOf(levels), color, maxLevels, levels);
    }
    
    public AbstractTraitLeveled(final String identifier, final String suffix, final int color, final int maxLevels, final int levels) {
        super(identifier + suffix, color);
        this.name = identifier;
        this.levels = levels;
        final IModifier modifier = TinkerRegistry.getModifier(this.name);
        if (modifier != null) {
            if (modifier instanceof AbstractTraitLeveled && ((AbstractTraitLeveled)modifier).levels > this.levels) {
                TinkerRegistry.registerModifierAlias(this, this.name);
            }
        }
        else {
            TinkerRegistry.registerModifierAlias(this, this.name);
        }
        this.aspects.clear();
        this.addAspects(new ModifierAspect.LevelAspect(this, maxLevels), new ModifierAspect.DataAspect(this, color));
    }
    
    @Override
    public void updateNBTforTrait(final NBTTagCompound modifierTag, final int newColor) {
        super.updateNBTforTrait(modifierTag, newColor);
        final ModifierNBT data = ModifierNBT.readTag(modifierTag);
        data.level = 0;
        data.write(modifierTag);
    }
    
    @Override
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        final NBTTagList tagList = TagUtil.getModifiersTagList(rootCompound);
        int index = TinkerUtil.getIndexInCompoundList(tagList, this.getModifierIdentifier());
        NBTTagCompound tag = new NBTTagCompound();
        if (index > -1) {
            tag = tagList.func_150305_b(index);
        }
        else {
            index = tagList.func_74745_c();
            tagList.func_74742_a((NBTBase)tag);
        }
        if (!tag.func_74767_n(this.identifier)) {
            final ModifierNBT tag2;
            final ModifierNBT data = tag2 = ModifierNBT.readTag(tag);
            tag2.level += this.levels;
            data.write(tag);
            tag.func_74757_a(this.identifier, true);
            tagList.func_150304_a(index, (NBTBase)tag);
            TagUtil.setModifiersTagList(rootCompound, tagList);
            this.applyModifierEffect(rootCompound);
        }
    }
    
    public void applyModifierEffect(final NBTTagCompound rootCompound) {
    }
    
    @Override
    public String getModifierIdentifier() {
        return this.name;
    }
    
    @Override
    public String getLocalizedName() {
        String locName = Util.translate("modifier.%s.name", this.name);
        if (this.levels > 1) {
            locName = locName + " " + TinkerUtil.getRomanNumeral(this.levels);
        }
        return locName;
    }
    
    @Override
    public String getLocalizedDesc() {
        return Util.translate("modifier.%s.desc", this.name);
    }
    
    @Override
    public String getTooltip(final NBTTagCompound modifierTag, final boolean detailed) {
        return this.getLeveledTooltip(modifierTag, detailed);
    }
}
