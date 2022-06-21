package slimeknights.tconstruct.shared;

import net.minecraftforge.fml.common.gameevent.*;
import slimeknights.tconstruct.common.config.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.item.*;
import net.minecraftforge.items.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.*;

public class PlayerDataEvents
{
    public static final String TAG_PLAYER_HAS_BOOK;
    
    @SubscribeEvent
    public void onPlayerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        if (Config.spawnWithBook) {
            final NBTTagCompound playerData = event.player.getEntityData();
            final NBTTagCompound data = TagUtil.getTagSafe(playerData, "PlayerPersisted");
            if (!data.func_74767_n(PlayerDataEvents.TAG_PLAYER_HAS_BOOK)) {
                ItemHandlerHelper.giveItemToPlayer(event.player, new ItemStack((Item)TinkerCommons.book));
                data.func_74757_a(PlayerDataEvents.TAG_PLAYER_HAS_BOOK, true);
                playerData.func_74782_a("PlayerPersisted", (NBTBase)data);
            }
        }
    }
    
    static {
        TAG_PLAYER_HAS_BOOK = Util.prefix("spawned_book");
    }
}
