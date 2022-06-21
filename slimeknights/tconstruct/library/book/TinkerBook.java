package slimeknights.tconstruct.library.book;

import slimeknights.mantle.client.book.data.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.book.content.*;
import slimeknights.tconstruct.*;
import slimeknights.tconstruct.library.*;
import slimeknights.mantle.client.book.repository.*;
import slimeknights.mantle.client.book.*;
import slimeknights.tconstruct.library.book.sectiontransformer.*;

@SideOnly(Side.CLIENT)
public class TinkerBook extends BookData
{
    public static final BookData INSTANCE;
    
    public TinkerBook() {
        super(new BookRepository[0]);
    }
    
    public static void init() {
        BookLoader.registerPageType("toolmaterial", (Class)ContentMaterial.class);
        BookLoader.registerPageType("modifier", (Class)ContentModifier.class);
        BookLoader.registerPageType("modifier_fortify", (Class)ContentModifierFortify.class);
        BookLoader.registerPageType("tool", (Class)ContentTool.class);
        BookLoader.registerPageType("single_stat_material", (Class)ContentSingleStatMultMaterial.class);
        BookLoader.registerPageType("imageText2", (Class)ContentImageText2.class);
        TinkerBook.INSTANCE.addRepository((BookRepository)new ModuleFileRepository(TConstruct.pulseManager, Util.resource("book")));
        TinkerBook.INSTANCE.addTransformer((BookTransformer)new ToolSectionTransformer());
        TinkerBook.INSTANCE.addTransformer((BookTransformer)new MaterialSectionTransformer());
        TinkerBook.INSTANCE.addTransformer((BookTransformer)new ModifierSectionTransformer());
        TinkerBook.INSTANCE.addTransformer((BookTransformer)new BowMaterialSectionTransformer());
        TinkerBook.INSTANCE.addTransformer(BookTransformer.IndexTranformer());
    }
    
    static {
        INSTANCE = BookLoader.registerBook(Util.RESOURCE, false, false, new BookRepository[0]);
    }
}
