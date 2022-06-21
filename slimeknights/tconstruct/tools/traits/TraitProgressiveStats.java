package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.*;

public abstract class TraitProgressiveStats extends AbstractTrait
{
    protected final String pool_key;
    protected final String applied_key;
    
    public TraitProgressiveStats(final String identifier, final TextFormatting color) {
        super(identifier, color);
        this.pool_key = identifier + "StatPool";
        this.applied_key = identifier + "StatBonus";
    }
    
    public TraitProgressiveStats(final String identifier, final int color) {
        super(identifier, color);
        this.pool_key = identifier + "StatPool";
        this.applied_key = identifier + "StatBonus";
    }
    
    @Override
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        final ToolNBT data = TagUtil.getToolStats(rootCompound);
        final StatNBT bonus = this.getBonus(rootCompound);
        final ToolNBT toolNBT = data;
        toolNBT.durability += bonus.durability;
        final ToolNBT toolNBT2 = data;
        toolNBT2.speed += bonus.speed;
        final ToolNBT toolNBT3 = data;
        toolNBT3.attack += bonus.attack;
        TagUtil.setToolTag(rootCompound, data.get());
    }
    
    protected boolean hasPool(final NBTTagCompound root) {
        return TagUtil.getExtraTag(root).func_74764_b(this.pool_key);
    }
    
    protected StatNBT getPool(final NBTTagCompound root) {
        return getStats(root, this.pool_key);
    }
    
    protected void setPool(final NBTTagCompound root, final StatNBT data) {
        setStats(root, data, this.pool_key);
    }
    
    protected StatNBT getBonus(final NBTTagCompound root) {
        return getStats(root, this.applied_key);
    }
    
    protected void setBonus(final NBTTagCompound root, final StatNBT data) {
        setStats(root, data, this.applied_key);
    }
    
    protected static StatNBT getStats(final NBTTagCompound root, final String key) {
        return ModifierNBT.readTag(TagUtil.getTagSafe(TagUtil.getExtraTag(root), key), StatNBT.class);
    }
    
    protected static void setStats(final NBTTagCompound root, final StatNBT data, final String key) {
        final NBTTagCompound extra = TagUtil.getExtraTag(root);
        final NBTTagCompound tag = new NBTTagCompound();
        data.write(tag);
        extra.func_74782_a(key, (NBTBase)tag);
        TagUtil.setExtraTag(root, extra);
    }
    
    protected boolean playerIsBreakingBlock(final Entity entity) {
        return false;
    }
    
    public static class StatNBT extends ModifierNBT
    {
        public int durability;
        public float attack;
        public float speed;
        
        @Override
        public void read(final NBTTagCompound tag) {
            super.read(tag);
            this.durability = tag.func_74762_e("durability");
            this.attack = tag.func_74760_g("attack");
            this.speed = tag.func_74760_g("speed");
        }
        
        @Override
        public void write(final NBTTagCompound tag) {
            super.write(tag);
            tag.func_74768_a("durability", this.durability);
            tag.func_74776_a("attack", this.attack);
            tag.func_74776_a("speed", this.speed);
        }
    }
}
