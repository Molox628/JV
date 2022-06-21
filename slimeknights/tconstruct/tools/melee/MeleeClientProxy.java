package slimeknights.tconstruct.tools.melee;

import slimeknights.tconstruct.common.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.*;

public class MeleeClientProxy extends ClientProxy
{
    @Override
    public void init() {
        super.init();
        this.registerToolBuildInfo();
    }
    
    private void registerToolBuildInfo() {
        ToolBuildGuiInfo info = new ToolBuildGuiInfo(TinkerMeleeWeapons.broadSword);
        info.addSlotPosition(12, 62);
        info.addSlotPosition(48, 26);
        info.addSlotPosition(30, 44);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerMeleeWeapons.longSword);
        info.addSlotPosition(12, 62);
        info.addSlotPosition(48, 26);
        info.addSlotPosition(30, 44);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerMeleeWeapons.rapier);
        info.addSlotPosition(52, 62);
        info.addSlotPosition(18, 26);
        info.addSlotPosition(32, 44);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerMeleeWeapons.battleSign);
        info.addSlotPosition(27, 60);
        info.addSlotPosition(27, 34);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerMeleeWeapons.fryPan);
        info.addSlotPosition(12, 62);
        info.addSlotPosition(34, 36);
        TinkerRegistryClient.addToolBuilding(info);
        info = new ToolBuildGuiInfo(TinkerMeleeWeapons.cleaver);
        info.addSlotPosition(9, 64);
        info.addSlotPosition(25, 36);
        info.addSlotPosition(47, 30);
        info.addSlotPosition(33, 58);
        TinkerRegistryClient.addToolBuilding(info);
    }
}
