package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class TraitTasty extends AbstractTrait
{
    public static final int NOM_COST = 5;
    
    public TraitTasty() {
        super("tasty", TextFormatting.RED);
    }
    
    @Override
    public void onUpdate(final ItemStack tool, final World world, final Entity entity, final int itemSlot, final boolean isSelected) {
        if (!isSelected || !(entity instanceof EntityPlayer) || entity.func_130014_f_().field_72995_K) {
            return;
        }
        final FoodStats foodStats = ((EntityPlayer)entity).func_71024_bL();
        float chance = 0.01f;
        if (((EntityPlayer)entity).func_110143_aJ() < ((EntityPlayer)entity).func_110138_aP()) {
            chance += (float)0.02;
        }
        if (!foodStats.func_75121_c()) {
            return;
        }
        if (foodStats.func_75116_a() > 10) {
            if (TraitTasty.random.nextFloat() < chance) {
                this.nom(tool, (EntityPlayer)entity);
            }
        }
        else {
            chance += (5 - foodStats.func_75116_a()) * 0.0025f;
            chance -= foodStats.func_75115_e() * 0.005f;
            if (TraitTasty.random.nextFloat() < chance) {
                this.nom(tool, (EntityPlayer)entity);
            }
        }
    }
    
    protected void nom(final ItemStack tool, final EntityPlayer player) {
        if (ToolHelper.isBroken(tool) || ToolHelper.getCurrentDurability(tool) < 5) {
            return;
        }
        player.func_71024_bL().func_75122_a(1, 0.0f);
        player.func_130014_f_().func_184133_a((EntityPlayer)null, player.func_180425_c(), SoundEvents.field_187537_bA, SoundCategory.PLAYERS, 0.8f, 1.0f);
        ToolHelper.damageTool(tool, 5, (EntityLivingBase)player);
    }
}
