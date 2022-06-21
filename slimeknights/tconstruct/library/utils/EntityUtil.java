package slimeknights.tconstruct.library.utils;

import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import java.util.*;

public final class EntityUtil
{
    private EntityUtil() {
    }
    
    public static RayTraceResult raytraceEntityPlayerLook(final EntityPlayer player, final float range) {
        final Vec3d eye = new Vec3d(player.field_70165_t, player.field_70163_u + player.func_70047_e(), player.field_70161_v);
        final Vec3d look = player.func_70676_i(1.0f);
        return raytraceEntity((Entity)player, eye, look, range, true);
    }
    
    public static RayTraceResult raytraceEntity(final Entity entity, final Vec3d start, final Vec3d look, final double range, final boolean ignoreCanBeCollidedWith) {
        final Vec3d direction = start.func_72441_c(look.field_72450_a * range, look.field_72448_b * range, look.field_72449_c * range);
        Entity pointedEntity = null;
        Vec3d hit = null;
        final AxisAlignedBB bb = entity.func_174813_aQ().func_72321_a(look.field_72450_a * range, look.field_72448_b * range, look.field_72449_c * range).func_72321_a(1.0, 1.0, 1.0);
        final List<Entity> entitiesInArea = (List<Entity>)entity.func_130014_f_().func_72839_b(entity, bb);
        double range2 = range;
        for (final Entity candidate : entitiesInArea) {
            if (ignoreCanBeCollidedWith || candidate.func_70067_L()) {
                final double colBorder = candidate.func_70111_Y();
                final AxisAlignedBB entityBB = candidate.func_174813_aQ().func_72321_a(colBorder, colBorder, colBorder);
                final RayTraceResult movingobjectposition = entityBB.func_72327_a(start, direction);
                if (entityBB.func_72318_a(start)) {
                    if (0.0 >= range2 && range2 != 0.0) {
                        continue;
                    }
                    pointedEntity = candidate;
                    hit = ((movingobjectposition == null) ? start : movingobjectposition.field_72307_f);
                    range2 = 0.0;
                }
                else {
                    if (movingobjectposition == null) {
                        continue;
                    }
                    final double dist = start.func_72438_d(movingobjectposition.field_72307_f);
                    if (dist >= range2 && range2 != 0.0) {
                        continue;
                    }
                    if (candidate == entity.func_184187_bx() && !entity.canRiderInteract()) {
                        if (range2 != 0.0) {
                            continue;
                        }
                        pointedEntity = candidate;
                        hit = movingobjectposition.field_72307_f;
                    }
                    else {
                        pointedEntity = candidate;
                        hit = movingobjectposition.field_72307_f;
                        range2 = dist;
                    }
                }
            }
        }
        if (pointedEntity != null && range2 < range) {
            return new RayTraceResult(pointedEntity, hit);
        }
        return null;
    }
}
