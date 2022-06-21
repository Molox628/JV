package slimeknights.tconstruct.library;

import net.minecraftforge.fml.relauncher.*;
import org.apache.logging.log4j.*;
import java.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.client.texture.*;
import com.google.common.collect.*;

@SideOnly(Side.CLIENT)
public final class TinkerRegistryClient
{
    public static final Logger log;
    private static final Map<Item, ToolBuildGuiInfo> toolBuildInfo;
    private static final Map<String, AbstractColoredTexture> textureProcessors;
    
    private TinkerRegistryClient() {
    }
    
    public static void addToolBuilding(final ToolBuildGuiInfo info) {
        TinkerRegistryClient.toolBuildInfo.put(info.tool.func_77973_b(), info);
    }
    
    public static ToolBuildGuiInfo getToolBuildInfoForTool(final Item tool) {
        return TinkerRegistryClient.toolBuildInfo.get(tool);
    }
    
    public static void clear() {
        TinkerRegistryClient.toolBuildInfo.clear();
    }
    
    static {
        log = Util.getLogger("API-Client");
        toolBuildInfo = Maps.newLinkedHashMap();
        textureProcessors = Maps.newHashMap();
    }
}
