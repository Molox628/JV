package slimeknights.tconstruct.tools.ranged;

import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.events.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.tools.ranged.item.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Mod.EventBusSubscriber({ Side.CLIENT })
public class RangedEvents
{
    @SubscribeEvent
    public static void onToolPartReplacement(final TinkerEvent.OnToolPartReplacement event) {
        if (event.toolStack.func_77973_b() == TinkerRangedWeapons.bolt) {
            final List<ItemStack> extraParts = event.replacementParts.stream().filter(Objects::nonNull).filter(stack -> stack.func_77973_b() == TinkerTools.boltCore).map((Function<? super Object, ?>)BoltCore::getHeadStack).collect((Collector<? super Object, ?, List<ItemStack>>)Collectors.toList());
            event.replacementParts.addAll((Collection)new ArrayList(extraParts));
        }
    }
}
