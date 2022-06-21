package slimeknights.tconstruct.tools.modifiers;

import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.tools.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.tools.*;

public class ModSilktouch extends ToolModifier
{
    public ModSilktouch() {
        super("silktouch", 16507531);
        this.addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect((T)this), ModifierAspect.freeModifier);
    }
    
    @Override
    public boolean canApplyTogether(final Enchantment enchantment) {
        return enchantment != Enchantments.field_185304_p && enchantment != Enchantments.field_185308_t;
    }
    
    @Override
    public boolean canApplyTogether(final IToolMod otherModifier) {
        return !otherModifier.getIdentifier().equals(TinkerTraits.squeaky.getIdentifier()) && !otherModifier.getIdentifier().equals(TinkerModifiers.modLuck.getIdentifier());
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        ToolBuilder.addEnchantment(rootCompound, Enchantments.field_185306_r);
        final ToolNBT toolData = TagUtil.getToolStats(rootCompound);
        toolData.speed = Math.max(1.0f, toolData.speed - 3.0f);
        toolData.attack = Math.max(1.0f, toolData.attack - 3.0f);
        TagUtil.setToolTag(rootCompound, toolData.get());
    }
}
