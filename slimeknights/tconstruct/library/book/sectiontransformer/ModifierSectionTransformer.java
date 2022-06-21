package slimeknights.tconstruct.library.book.sectiontransformer;

import net.minecraftforge.fml.relauncher.*;
import slimeknights.mantle.client.book.data.*;
import slimeknights.tconstruct.library.book.content.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.modifiers.*;

@SideOnly(Side.CLIENT)
public class ModifierSectionTransformer extends ContentListingSectionTransformer
{
    public ModifierSectionTransformer() {
        super("modifiers");
    }
    
    @Override
    protected void processPage(final BookData book, final ContentListing listing, final PageData page) {
        if (page.content instanceof ContentModifier) {
            final IModifier modifier = TinkerRegistry.getModifier(((ContentModifier)page.content).modifierName);
            if (modifier != null) {
                listing.addEntry(modifier.getLocalizedName(), page);
            }
        }
    }
}
