package slimeknights.tconstruct.library.book.sectiontransformer;

import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.book.content.*;
import slimeknights.mantle.client.book.data.*;

@SideOnly(Side.CLIENT)
public class ContentListingSectionTransformer extends SectionTransformer
{
    public ContentListingSectionTransformer(final String sectionName) {
        super(sectionName);
    }
    
    @Override
    public void transform(final BookData book, final SectionData data) {
        final ContentListing listing = new ContentListing();
        listing.title = book.translate(this.sectionName);
        final PageData listingPage = new PageData(true);
        listingPage.name = this.sectionName;
        listingPage.source = data.source;
        listingPage.parent = data;
        listingPage.content = listing;
        data.pages.forEach(sectionPage -> this.processPage(book, listing, sectionPage));
        if (listing.hasEntries()) {
            listingPage.load();
            data.pages.add(0, listingPage);
        }
    }
    
    protected void processPage(final BookData book, final ContentListing listing, final PageData page) {
        if (!page.getTitle().equals("hidden")) {
            listing.addEntry(book.translate(page.getTitle()), page);
        }
    }
}
