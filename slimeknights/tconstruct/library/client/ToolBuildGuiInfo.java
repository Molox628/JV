package slimeknights.tconstruct.library.client;

import net.minecraft.item.*;
import javax.annotation.*;
import java.util.*;
import org.lwjgl.util.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.tinkering.*;

public class ToolBuildGuiInfo
{
    @Nonnull
    public final ItemStack tool;
    public final List<Point> positions;
    
    public ToolBuildGuiInfo() {
        this.positions = (List<Point>)Lists.newArrayList();
        this.tool = ItemStack.field_190927_a;
    }
    
    public ToolBuildGuiInfo(@Nonnull final TinkersItem tool) {
        this.positions = (List<Point>)Lists.newArrayList();
        this.tool = tool.buildItemForRenderingInGui();
    }
    
    public static ToolBuildGuiInfo default3Part(@Nonnull final TinkersItem tool) {
        final ToolBuildGuiInfo info = new ToolBuildGuiInfo(tool);
        info.addSlotPosition(13, 62);
        info.addSlotPosition(53, 22);
        info.addSlotPosition(33, 42);
        return info;
    }
    
    public void addSlotPosition(final int x, final int y) {
        this.positions.add(new Point(x, y));
    }
}
