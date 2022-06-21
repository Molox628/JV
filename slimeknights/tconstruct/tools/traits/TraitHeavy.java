package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import java.util.*;
import net.minecraft.inventory.*;
import javax.annotation.*;
import net.minecraft.item.*;
import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;

public class TraitHeavy extends AbstractTrait
{
    protected static final UUID KNOCKBACK_MODIFIER;
    
    public TraitHeavy() {
        super("heavy", 16777215);
    }
    
    @Override
    public void getAttributeModifiers(@Nonnull final EntityEquipmentSlot slot, final ItemStack stack, final Multimap<String, AttributeModifier> attributeMap) {
        if (slot == EntityEquipmentSlot.MAINHAND || slot == EntityEquipmentSlot.OFFHAND) {
            attributeMap.put((Object)SharedMonsterAttributes.field_111266_c.func_111108_a(), (Object)new AttributeModifier(TraitHeavy.KNOCKBACK_MODIFIER, "Knockback modifier", 1.0, 0));
        }
    }
    
    static {
        KNOCKBACK_MODIFIER = UUID.fromString("cca17597-84ae-44fe-bf98-ca08a9047079");
    }
}
