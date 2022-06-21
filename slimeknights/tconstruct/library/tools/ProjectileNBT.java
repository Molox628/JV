package slimeknights.tconstruct.library.tools;

import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.tools.ranged.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;

public class ProjectileNBT extends ToolNBT
{
    public float accuracy;
    
    public ProjectileNBT() {
        this.accuracy = 1.0f;
    }
    
    public ProjectileNBT(final NBTTagCompound tag) {
        super(tag);
    }
    
    public ProjectileNBT shafts(final ProjectileCore projectileCore, final ArrowShaftMaterialStats... shafts) {
        int dur = 0;
        float modifier = 0.0f;
        for (final ArrowShaftMaterialStats shaft : shafts) {
            if (shaft != null) {
                dur += shaft.bonusAmmo;
                modifier += shaft.modifier;
            }
        }
        dur *= projectileCore.getDurabilityPerAmmo();
        modifier /= shafts.length;
        this.durability = Math.round(this.durability * modifier);
        this.durability += Math.round(dur / (float)shafts.length);
        this.durability = Math.max(1, this.durability);
        return this;
    }
    
    public ProjectileNBT fletchings(final FletchingMaterialStats... fletchings) {
        float modifier = 0.0f;
        float accuracy = 0.0f;
        for (final FletchingMaterialStats fletching : fletchings) {
            if (fletching != null) {
                modifier += fletching.modifier;
                accuracy += fletching.accuracy;
            }
        }
        accuracy /= fletchings.length;
        modifier /= fletchings.length;
        this.accuracy = Math.min(1.0f, Math.max(0.0f, accuracy));
        this.durability = Math.round(this.durability * modifier);
        this.durability = Math.max(1, this.durability);
        return this;
    }
    
    @Override
    public void read(final NBTTagCompound tag) {
        super.read(tag);
        this.accuracy = tag.func_74760_g("Accuracy");
    }
    
    @Override
    public void write(final NBTTagCompound tag) {
        super.write(tag);
        tag.func_74776_a("Accuracy", this.accuracy);
    }
    
    public static ProjectileNBT from(final ItemStack itemStack) {
        return new ProjectileNBT(TagUtil.getToolTag(itemStack));
    }
}
