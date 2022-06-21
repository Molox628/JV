package slimeknights.tconstruct.library.smeltery;

import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import java.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.materials.*;

public class Cast extends Pattern implements ICast
{
    public Cast() {
        this.func_77637_a((CreativeTabs)TinkerRegistry.tabSmeltery);
    }
    
    @Override
    protected Collection<Item> getSubItemToolparts() {
        return TinkerRegistry.getCastItems();
    }
    
    @Override
    protected boolean isValidSubitemMaterial(final Material material) {
        return material.isCastable();
    }
}
