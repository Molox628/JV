package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.world.biome.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;

public class TraitHellish extends AbstractTrait
{
    private static final float bonusDamage = 4.0f;
    
    public TraitHellish() {
        super("hellish", 16711680);
    }
    
    @Override
    public float damage(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final float newDamage, final boolean isCritical) {
        for (final EnumCreatureType creatureType : EnumCreatureType.values()) {
            for (final Biome.SpawnListEntry spawnListEntry : ((Biome)Biome.field_185377_q.func_82594_a((Object)new ResourceLocation("hell"))).func_76747_a(creatureType)) {
                if (spawnListEntry.field_76300_b.equals(target.getClass())) {
                    return super.damage(tool, player, target, damage, newDamage, isCritical);
                }
            }
        }
        return super.damage(tool, player, target, damage, newDamage + 4.0f, isCritical);
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final String loc = String.format("modifier.%s.extra", this.getModifierIdentifier());
        return (List<String>)ImmutableList.of((Object)Util.translateFormatted(loc, Util.df.format(4.0)));
    }
}
