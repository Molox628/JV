package slimeknights.tconstruct.tools.common.item;

import net.minecraft.item.*;

public class ItemMaterials extends Item
{
    public static final ItemMaterials INSTANCE;
    public static ItemStack slimeCrystal;
    public static ItemStack slimeCrystalBlue;
    
    private ItemMaterials() {
        this.func_77627_a(true);
    }
    
    protected ItemStack addVariant(final int meta, final String name) {
        return new ItemStack((Item)this, 1, meta);
    }
    
    public int func_77647_b(final int damage) {
        return damage;
    }
    
    static {
        INSTANCE = new ItemMaterials();
        ItemMaterials.slimeCrystal = ItemMaterials.INSTANCE.addVariant(0, "SlimeCrystal");
        ItemMaterials.slimeCrystalBlue = ItemMaterials.INSTANCE.addVariant(1, "SlimeCrystalBlue");
    }
}
