package slimeknights.tconstruct.tools.modifiers;

import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.shared.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class ModGlowing extends ModifierTrait
{
    public ModGlowing() {
        super("glowing", 16777130);
    }
    
    @Override
    public void onUpdate(final ItemStack tool, final World world, final Entity entity, final int itemSlot, final boolean isSelected) {
        if (isSelected && !world.field_72995_K && !ToolHelper.isBroken(tool)) {
            final BlockPos pos = entity.func_180425_c();
            if (world.func_175671_l(pos) < 8) {
                for (final BlockPos candidate : new BlockPos[] { pos, pos.func_177984_a(), pos.func_177978_c(), pos.func_177974_f(), pos.func_177968_d(), pos.func_177976_e(), pos.func_177977_b() }) {
                    if (TinkerCommons.blockGlow.addGlow(world, candidate, EnumFacing.values()[ModGlowing.random.nextInt(6)])) {
                        EntityLivingBase entityLiving = null;
                        if (entity instanceof EntityLivingBase) {
                            entityLiving = (EntityLivingBase)entity;
                        }
                        if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).field_71075_bZ.field_75098_d) {
                            ToolHelper.damageTool(tool, 1, entityLiving);
                        }
                        return;
                    }
                }
            }
        }
    }
}
