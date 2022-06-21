package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.biome.*;

public class TraitAridiculous extends AbstractTrait
{
    public TraitAridiculous() {
        super("aridiculous", TextFormatting.DARK_RED);
    }
    
    @Override
    public void miningSpeed(final ItemStack tool, final PlayerEvent.BreakSpeed event) {
        final float coeff = this.calcAridiculousness(event.getEntityPlayer().func_130014_f_(), event.getPos()) / 10.0f;
        event.setNewSpeed(event.getNewSpeed() + event.getOriginalSpeed() * coeff);
    }
    
    @Override
    public float damage(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final float newDamage, final boolean isCritical) {
        final float extraDamage = 2.0f * this.calcAridiculousness(player.func_130014_f_(), player.func_180425_c());
        return extraDamage + super.damage(tool, player, target, damage, newDamage, isCritical);
    }
    
    protected float calcAridiculousness(final World world, final BlockPos pos) {
        final Biome biome = world.getBiomeForCoordsBody(pos);
        final float rain = world.func_72896_J() ? (biome.func_76727_i() / 2.0f) : 0.0f;
        return (float)(Math.pow(1.25, 3.0 * (0.5f + biome.func_185353_n() - biome.func_76727_i())) - 1.25) - rain;
    }
}
