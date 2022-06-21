package slimeknights.tconstruct.tools.ranged;

import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.client.event.*;
import net.minecraft.entity.player.*;
import net.minecraft.client.entity.*;
import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;
import net.minecraft.item.*;

@Mod.EventBusSubscriber({ Side.CLIENT })
public class RangedRenderEvents
{
    @SubscribeEvent
    public static void onRenderPlayer(final RenderLivingEvent.Pre<EntityPlayer> event) {
        if (!(event.getEntity() instanceof EntityPlayer)) {
            return;
        }
        final EntityPlayer player = (EntityPlayer)event.getEntity();
        EnumHand right = EnumHand.MAIN_HAND;
        EnumHand left = EnumHand.OFF_HAND;
        if (player instanceof EntityPlayerSP && player.func_184591_cq() == EnumHandSide.LEFT) {
            right = EnumHand.OFF_HAND;
            left = EnumHand.MAIN_HAND;
        }
        if (event.getRenderer().func_177087_b() instanceof ModelBiped) {
            if (isCarryingLoadedCrossbow(player, right)) {
                ((ModelBiped)event.getRenderer().func_177087_b()).field_187076_m = ModelBiped.ArmPose.BOW_AND_ARROW;
            }
            else if (isCarryingLoadedCrossbow(player, left)) {
                ((ModelBiped)event.getRenderer().func_177087_b()).field_187075_l = ModelBiped.ArmPose.BOW_AND_ARROW;
            }
        }
    }
    
    private static boolean isCarryingLoadedCrossbow(final EntityPlayer entityPlayer, final EnumHand hand) {
        return Optional.ofNullable(entityPlayer).map(player -> player.func_184586_b(hand)).filter(stack -> stack.func_77973_b() == TinkerRangedWeapons.crossBow).map(stack -> TinkerRangedWeapons.crossBow.isLoaded(stack)).orElse(false);
    }
}
