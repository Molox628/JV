package slimeknights.tconstruct.tools.ranged;

import slimeknights.tconstruct.common.*;
import net.minecraftforge.fml.client.registry.*;
import slimeknights.tconstruct.tools.common.entity.*;
import slimeknights.tconstruct.tools.common.client.renderer.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.*;

public class RangedClientProxy extends ClientProxy
{
    @Override
    public void registerModels() {
        super.registerModels();
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityShuriken.class, RenderShuriken::new);
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityArrow.class, RenderArrow::new);
        RenderingRegistry.registerEntityRenderingHandler((Class)EntityBolt.class, RenderBolt::new);
    }
    
    @Override
    public void init() {
        super.init();
        this.registerToolBuildInfo();
    }
    
    private void registerToolBuildInfo() {
        ToolBuildGuiInfo info = new ToolBuildGuiInfo(TinkerRangedWeapons.shuriken);
        info.addSlotPosition(20, 29);
        info.addSlotPosition(44, 29);
        info.addSlotPosition(44, 53);
        info.addSlotPosition(20, 53);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerRangedWeapons.shortBow);
        info.addSlotPosition(36, 23);
        info.addSlotPosition(14, 45);
        info.addSlotPosition(38, 47);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerRangedWeapons.longBow);
        info.addSlotPosition(44, 19);
        info.addSlotPosition(10, 53);
        info.addSlotPosition(17, 26);
        info.addSlotPosition(38, 47);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerRangedWeapons.arrow);
        info.addSlotPosition(32, 41);
        info.addSlotPosition(50, 23);
        info.addSlotPosition(14, 59);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerRangedWeapons.crossBow);
        info.addSlotPosition(38, 47);
        info.addSlotPosition(44, 19);
        info.addSlotPosition(14, 23);
        info.addSlotPosition(18, 51);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerRangedWeapons.bolt);
        info.addSlotPosition(40, 37);
        info.addSlotPosition(20, 53);
        TinkerRegistryClient.addToolBuilding(info);
    }
}
