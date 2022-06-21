package slimeknights.tconstruct.library.tools;

import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.materials.*;

public class ToolNBT
{
    public int durability;
    public int harvestLevel;
    public float attack;
    public float speed;
    public float attackSpeedMultiplier;
    public int modifiers;
    private final NBTTagCompound parent;
    
    public ToolNBT() {
        this.durability = 0;
        this.harvestLevel = 0;
        this.attack = 0.0f;
        this.speed = 0.0f;
        this.attackSpeedMultiplier = 1.0f;
        this.modifiers = 3;
        this.parent = new NBTTagCompound();
    }
    
    public ToolNBT(final NBTTagCompound tag) {
        this.read(tag);
        this.parent = tag;
    }
    
    public ToolNBT head(final HeadMaterialStats... heads) {
        this.durability = 0;
        this.harvestLevel = 0;
        this.attack = 0.0f;
        this.speed = 0.0f;
        for (final HeadMaterialStats head : heads) {
            if (head != null) {
                this.durability += head.durability;
                this.attack += head.attack;
                this.speed += head.miningspeed;
                if (head.harvestLevel > this.harvestLevel) {
                    this.harvestLevel = head.harvestLevel;
                }
            }
        }
        this.durability = Math.max(1, this.durability / heads.length);
        this.attack /= heads.length;
        this.speed /= heads.length;
        return this;
    }
    
    public ToolNBT extra(final ExtraMaterialStats... extras) {
        int dur = 0;
        for (final ExtraMaterialStats extra : extras) {
            if (extra != null) {
                dur += extra.extraDurability;
            }
        }
        this.durability += Math.round(dur / (float)extras.length);
        return this;
    }
    
    public ToolNBT handle(final HandleMaterialStats... handles) {
        int dur = 0;
        float modifier = 0.0f;
        for (final HandleMaterialStats handle : handles) {
            if (handle != null) {
                dur += handle.durability;
                modifier += handle.modifier;
            }
        }
        modifier /= handles.length;
        this.durability = Math.round(this.durability * modifier);
        this.durability += Math.round(dur / (float)handles.length);
        this.durability = Math.max(1, this.durability);
        return this;
    }
    
    public void read(final NBTTagCompound tag) {
        this.durability = tag.func_74762_e("Durability");
        this.harvestLevel = tag.func_74762_e("HarvestLevel");
        this.attack = tag.func_74760_g("Attack");
        this.speed = tag.func_74760_g("MiningSpeed");
        this.attackSpeedMultiplier = tag.func_74760_g("AttackSpeedMultiplier");
        this.modifiers = tag.func_74762_e("FreeModifiers");
    }
    
    public void write(final NBTTagCompound tag) {
        tag.func_74768_a("Durability", this.durability);
        tag.func_74768_a("HarvestLevel", this.harvestLevel);
        tag.func_74776_a("Attack", this.attack);
        tag.func_74776_a("MiningSpeed", this.speed);
        tag.func_74776_a("AttackSpeedMultiplier", this.attackSpeedMultiplier);
        tag.func_74768_a("FreeModifiers", this.modifiers);
    }
    
    public NBTTagCompound get() {
        final NBTTagCompound tag = this.parent.func_74737_b();
        this.write(tag);
        return tag;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ToolNBT toolNBT = (ToolNBT)o;
        return this.durability == toolNBT.durability && this.harvestLevel == toolNBT.harvestLevel && Float.compare(toolNBT.attack, this.attack) == 0 && Float.compare(toolNBT.speed, this.speed) == 0 && this.modifiers == toolNBT.modifiers;
    }
    
    @Override
    public int hashCode() {
        int result = this.durability;
        result = 31 * result + this.harvestLevel;
        result = 31 * result + ((this.attack != 0.0f) ? Float.floatToIntBits(this.attack) : 0);
        result = 31 * result + ((this.speed != 0.0f) ? Float.floatToIntBits(this.speed) : 0);
        result = 31 * result + this.modifiers;
        return result;
    }
}
