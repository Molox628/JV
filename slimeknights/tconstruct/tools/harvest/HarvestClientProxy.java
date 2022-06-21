package slimeknights.tconstruct.tools.harvest;

import slimeknights.tconstruct.common.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.*;

public class HarvestClientProxy extends ClientProxy
{
    @Override
    public void init() {
        super.init();
        this.registerToolBuildInfo();
    }
    
    private void registerToolBuildInfo() {
        ToolBuildGuiInfo info = new ToolBuildGuiInfo(TinkerHarvestTools.pickaxe);
        info.addSlotPosition(15, 60);
        info.addSlotPosition(53, 22);
        info.addSlotPosition(33, 42);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerHarvestTools.shovel);
        info.addSlotPosition(33, 42);
        info.addSlotPosition(51, 24);
        info.addSlotPosition(13, 62);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerHarvestTools.hatchet);
        info.addSlotPosition(22, 53);
        info.addSlotPosition(31, 22);
        info.addSlotPosition(51, 34);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerHarvestTools.mattock);
        info.addSlotPosition(22, 53);
        info.addSlotPosition(31, 22);
        info.addSlotPosition(51, 34);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerHarvestTools.kama);
        info.addSlotPosition(22, 53);
        info.addSlotPosition(31, 22);
        info.addSlotPosition(51, 34);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerHarvestTools.hammer);
        info.addSlotPosition(21, 52);
        info.addSlotPosition(44, 29);
        info.addSlotPosition(57, 48);
        info.addSlotPosition(25, 16);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerHarvestTools.excavator);
        info.addSlotPosition(25, 46);
        info.addSlotPosition(45, 26);
        info.addSlotPosition(25, 26);
        info.addSlotPosition(7, 62);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerHarvestTools.lumberAxe);
        info.addSlotPosition(32, 46);
        info.addSlotPosition(33, 22);
        info.addSlotPosition(53, 38);
        info.addSlotPosition(13, 62);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerHarvestTools.scythe);
        info.addSlotPosition(17, 54);
        info.addSlotPosition(36, 19);
        info.addSlotPosition(56, 29);
        info.addSlotPosition(37, 47);
        TinkerRegistryClient.addToolBuilding(info);
    }
}
