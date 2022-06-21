package slimeknights.tconstruct.common.config;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.player.*;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.util.text.*;
import net.minecraftforge.common.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.common.config.*;
import java.util.*;

public class ConfigSync
{
    @SideOnly(Side.CLIENT)
    private static boolean needsRestart;
    
    @SubscribeEvent
    @SideOnly(Side.SERVER)
    public void playerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player == null || !(event.player instanceof EntityPlayerMP) || FMLCommonHandler.instance().getSide().isClient()) {
            return;
        }
        final ConfigSyncPacket packet = new ConfigSyncPacket();
        packet.categories.add(Config.Modules);
        packet.categories.add(Config.Gameplay);
        TinkerNetwork.sendTo(packet, (EntityPlayerMP)event.player);
    }
    
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void playerJoinedWorld(final TickEvent.ClientTickEvent event) {
        final EntityPlayerSP player = Minecraft.func_71410_x().field_71439_g;
        if (ConfigSync.needsRestart) {
            player.func_145747_a((ITextComponent)new TextComponentString("[TConstruct] " + I18n.func_74838_a("config.synced.restart")));
        }
        else {
            player.func_145747_a((ITextComponent)new TextComponentString("[TConstruct] " + I18n.func_74838_a("config.synced.ok")));
        }
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    public static void syncConfig(final List<ConfigCategory> categories) {
        ConfigSync.needsRestart = false;
        boolean changed = false;
        Config.log.info("Syncing Config with Server");
        for (final ConfigCategory serverCategory : categories) {
            ConfigCategory category = Config.pulseConfig.getCategory();
            if (!serverCategory.getName().equals(category.getName())) {
                category = Config.configFile.getCategory(serverCategory.getName());
            }
            for (final Map.Entry<String, Property> entry : serverCategory.entrySet()) {
                final String name = entry.getKey();
                final Property serverProp = entry.getValue();
                final Property prop = category.get(name);
                if (prop == null) {
                    category.put(name, serverProp);
                }
                else {
                    if (prop.getString().equals(serverProp.getString())) {
                        continue;
                    }
                    prop.setValue(serverProp.getString());
                    ConfigSync.needsRestart |= prop.requiresMcRestart();
                    changed = true;
                    Config.log.debug("Syncing %s - %s: %s", (Object)category.getName(), (Object)prop.getName(), (Object)prop.getString());
                }
            }
        }
        if (Config.configFile.hasChanged()) {
            Config.configFile.save();
        }
        Config.pulseConfig.flush();
        if (changed) {
            MinecraftForge.EVENT_BUS.register((Object)new ConfigSync());
        }
    }
}
