package slimeknights.tconstruct.library.book.sectiontransformer;

import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.mantle.client.book.data.content.*;
import slimeknights.tconstruct.library.book.content.*;

@SideOnly(Side.CLIENT)
public class MaterialSectionTransformer extends AbstractMaterialSectionTransformer
{
    public MaterialSectionTransformer() {
        super("materials");
    }
    
    @Override
    protected boolean isValidMaterial(final Material material) {
        return material.hasStats("head") || material.hasStats("head") || material.hasStats("head");
    }
    
    @Override
    protected PageContent getPageContent(final Material material) {
        return new ContentMaterial(material);
    }
}
