package slimeknights.tconstruct.smeltery.item;

import slimeknights.mantle.item.*;
import slimeknights.tconstruct.library.smeltery.*;
import gnu.trove.map.hash.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;

public class CastCustom extends ItemMetaDynamic implements ICast
{
    protected TIntIntHashMap values;
    
    public CastCustom() {
        this.values = new TIntIntHashMap();
        this.func_77637_a((CreativeTabs)TinkerRegistry.tabSmeltery);
    }
    
    public ItemStack addMeta(final int meta, final String name, final int amount) {
        this.values.put(meta, amount);
        final ItemStack ret = this.addMeta(meta, name);
        return ret;
    }
    
    public ItemStack addMeta(final int meta, final String name) {
        if (!this.values.containsKey(meta)) {
            throw new RuntimeException("Usage of wrong function. Use the addMeta function that has an amount paired with it with this implementation");
        }
        return super.addMeta(meta, name);
    }
}
