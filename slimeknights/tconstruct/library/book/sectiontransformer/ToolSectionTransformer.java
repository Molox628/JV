package slimeknights.tconstruct.library.book.sectiontransformer;

import net.minecraftforge.fml.relauncher.*;
import slimeknights.mantle.client.book.data.*;
import slimeknights.tconstruct.library.book.content.*;
import slimeknights.tconstruct.library.*;
import java.util.*;
import slimeknights.tconstruct.library.tools.*;

@SideOnly(Side.CLIENT)
public class ToolSectionTransformer extends ContentListingSectionTransformer
{
    public ToolSectionTransformer() {
        super("tools");
    }
    
    @Override
    protected void processPage(final BookData book, final ContentListing listing, final PageData page) {
        if (page.content instanceof ContentTool) {
            final String toolName = ((ContentTool)page.content).toolName;
            final Optional<ToolCore> tool = TinkerRegistry.getTools().stream().filter(toolCore -> toolName.equals(toolCore.getIdentifier())).findFirst();
            tool.ifPresent(toolCore -> listing.addEntry(toolCore.getLocalizedName(), page));
        }
        else {
            super.processPage(book, listing, page);
        }
    }
}
