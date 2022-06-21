package slimeknights.tconstruct.library.potion;

import net.minecraft.util.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;

public class TinkerPotion extends Potion
{
    private final boolean show;
    
    public TinkerPotion(final ResourceLocation location, final boolean badEffect, final boolean showInInventory) {
        this(location, badEffect, showInInventory, 16777215);
    }
    
    public TinkerPotion(final ResourceLocation location, final boolean badEffect, final boolean showInInventory, final int color) {
        super(badEffect, color);
        this.func_76390_b("potion." + location.func_110623_a());
        this.setRegistryName(location);
        this.show = showInInventory;
    }
    
    public boolean shouldRenderInvText(final PotionEffect effect) {
        return this.show;
    }
    
    public PotionEffect apply(final EntityLivingBase entity, final int duration) {
        return this.apply(entity, duration, 0);
    }
    
    public PotionEffect apply(final EntityLivingBase entity, final int duration, final int level) {
        final PotionEffect effect = new PotionEffect((Potion)this, duration, level, false, false);
        entity.func_70690_d(effect);
        return effect;
    }
    
    public int getLevel(final EntityLivingBase entity) {
        final PotionEffect effect = entity.func_70660_b((Potion)this);
        if (effect != null) {
            return effect.func_76458_c();
        }
        return 0;
    }
    
    public boolean shouldRender(final PotionEffect effect) {
        return this.show;
    }
}
