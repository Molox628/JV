package slimeknights.tconstruct.plugin.waila;

import mcp.mobius.waila.api.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import slimeknights.tconstruct.library.tileentity.*;
import slimeknights.tconstruct.library.*;

public class WailaRegistrar
{
    static final String CONFIG_TANK;
    static final String CONFIG_CASTING;
    static final String CONFIG_PROGRESS;
    
    public static void wailaCallback(final IWailaRegistrar registrar) {
        registrar.addConfig("Tinkers' Construct", WailaRegistrar.CONFIG_TANK, true);
        registrar.addConfig("Tinkers' Construct", WailaRegistrar.CONFIG_CASTING, true);
        registrar.addConfig("Tinkers' Construct", WailaRegistrar.CONFIG_PROGRESS, true);
        final CastingDataProvider castingDataProvider = new CastingDataProvider();
        registrar.registerBodyProvider((IWailaDataProvider)castingDataProvider, (Class)TileCasting.class);
        final TankDataProvider tankDataProvider = new TankDataProvider();
        registrar.registerBodyProvider((IWailaDataProvider)tankDataProvider, (Class)TileTank.class);
        registrar.registerBodyProvider((IWailaDataProvider)tankDataProvider, (Class)TileCasting.class);
        final ProgressDataProvider progressDataProvider = new ProgressDataProvider();
        registrar.registerBodyProvider((IWailaDataProvider)progressDataProvider, (Class)IProgress.class);
    }
    
    static {
        CONFIG_TANK = Util.prefix("tank");
        CONFIG_CASTING = Util.prefix("casting");
        CONFIG_PROGRESS = Util.prefix("progress");
    }
}
