package slimeknights.tconstruct.library.tools;

import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;

public class ProjectileLauncherNBT extends ToolNBT
{
    public float drawSpeed;
    public float range;
    public float bonusDamage;
    
    public ProjectileLauncherNBT() {
        this.drawSpeed = 1.0f;
        this.range = 1.0f;
        this.bonusDamage = 0.0f;
    }
    
    public ProjectileLauncherNBT(final NBTTagCompound tag) {
        super(tag);
    }
    
    public ProjectileLauncherNBT limb(final BowMaterialStats... bowlimbs) {
        this.drawSpeed = 0.0f;
        this.range = 0.0f;
        this.bonusDamage = 0.0f;
        for (final BowMaterialStats limb : bowlimbs) {
            if (limb != null) {
                this.drawSpeed += limb.drawspeed;
                this.range += limb.range;
                this.bonusDamage += limb.bonusDamage;
            }
        }
        this.drawSpeed /= bowlimbs.length;
        this.range /= bowlimbs.length;
        this.bonusDamage /= bowlimbs.length;
        this.drawSpeed = Math.max(0.001f, this.drawSpeed);
        this.range = Math.max(0.001f, this.range);
        return this;
    }
    
    public ProjectileLauncherNBT bowstring(final BowStringMaterialStats... bowstrings) {
        float modifier = 0.0f;
        for (final BowStringMaterialStats bowstring : bowstrings) {
            if (bowstring != null) {
                modifier += bowstring.modifier;
            }
        }
        modifier /= bowstrings.length;
        this.durability = Math.round(this.durability * modifier);
        this.durability = Math.max(1, this.durability);
        return this;
    }
    
    @Override
    public void read(final NBTTagCompound tag) {
        super.read(tag);
        this.drawSpeed = tag.func_74760_g("DrawSpeed");
        this.range = tag.func_74760_g("Range");
        this.bonusDamage = tag.func_74760_g("ProjectileBonusDamage");
    }
    
    @Override
    public void write(final NBTTagCompound tag) {
        super.write(tag);
        tag.func_74776_a("DrawSpeed", this.drawSpeed);
        tag.func_74776_a("Range", this.range);
        tag.func_74776_a("ProjectileBonusDamage", this.bonusDamage);
    }
    
    public static ProjectileLauncherNBT from(final ItemStack itemStack) {
        return new ProjectileLauncherNBT(TagUtil.getToolTag(itemStack));
    }
}
