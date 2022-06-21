package slimeknights.tconstruct.library.tinkering;

import java.util.*;
import com.google.common.collect.*;

public class Category
{
    public static Map<String, Category> categories;
    public static final Category TOOL;
    public static final Category WEAPON;
    public static final Category HARVEST;
    public static final Category AOE;
    public static final Category PROJECTILE;
    public static final Category NO_MELEE;
    public static final Category LAUNCHER;
    public final String name;
    
    public Category(final String name) {
        this.name = name.toLowerCase(Locale.US);
        Category.categories.put(name, this);
    }
    
    static {
        Category.categories = (Map<String, Category>)Maps.newHashMap();
        TOOL = new Category("tool");
        WEAPON = new Category("weapon");
        HARVEST = new Category("harvest");
        AOE = new Category("aoe");
        PROJECTILE = new Category("projectile");
        NO_MELEE = new Category("no_melee");
        LAUNCHER = new Category("launcher");
    }
}
