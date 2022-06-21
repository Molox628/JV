package slimeknights.tconstruct.tools.modifiers;

import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.*;
import java.util.*;
import net.minecraft.util.text.translation.*;
import com.google.common.collect.*;

public class ModReinforced extends ModifierTrait
{
    private static final float chancePerLevel = 0.2f;
    public static final String TAG_UNBREAKABLE = "Unbreakable";
    
    public ModReinforced() {
        super("reinforced", 5254787, 5, 0);
    }
    
    private float getReinforcedChance(final NBTTagCompound modifierTag) {
        final ModifierNBT data = ModifierNBT.readTag(modifierTag);
        return data.level * 0.2f;
    }
    
    @Override
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        if (this.getReinforcedChance(modifierTag) >= 1.0f) {
            rootCompound.func_74757_a("Unbreakable", true);
        }
    }
    
    @Override
    public int onToolDamage(final ItemStack tool, final int damage, int newDamage, final EntityLivingBase entity) {
        if (entity.func_130014_f_().field_72995_K) {
            return 0;
        }
        final NBTTagCompound tag = TinkerUtil.getModifierTag(tool, this.identifier);
        final float chance = this.getReinforcedChance(tag);
        if (chance >= ModReinforced.random.nextFloat()) {
            newDamage -= damage;
        }
        return Math.max(0, newDamage);
    }
    
    @Override
    public String getLocalizedDesc() {
        return String.format(super.getLocalizedDesc(), Util.dfPercent.format(0.20000000298023224));
    }
    
    @Override
    public String getTooltip(final NBTTagCompound modifierTag, final boolean detailed) {
        final ModifierNBT data = ModifierNBT.readTag(modifierTag);
        if (data.level == this.maxLevel) {
            return Util.translate("modifier.%s.unbreakable", this.getIdentifier());
        }
        return super.getTooltip(modifierTag, detailed);
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final String loc = String.format("modifier.%s.extra", this.getIdentifier());
        if (I18n.func_94522_b(loc)) {
            final float chance = this.getReinforcedChance(modifierTag);
            String chanceStr = Util.dfPercent.format(chance);
            if (chance >= 1.0f) {
                chanceStr = Util.translate("modifier.%s.unbreakable", this.getIdentifier());
            }
            return (List<String>)ImmutableList.of((Object)Util.translateFormatted(loc, chanceStr));
        }
        return super.getExtraInfo(tool, modifierTag);
    }
}
