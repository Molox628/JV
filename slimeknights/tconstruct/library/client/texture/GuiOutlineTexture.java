package slimeknights.tconstruct.library.client.texture;

import net.minecraft.util.*;
import slimeknights.tconstruct.library.client.*;

public class GuiOutlineTexture extends ExtraUtilityTexture
{
    public GuiOutlineTexture(final ResourceLocation baseTexture, final String spriteName) {
        super(baseTexture, spriteName);
    }
    
    @Override
    protected int colorPixel(final int pixel, final int pxCoord) {
        if (this.trans[pxCoord]) {
            return pixel;
        }
        if (this.edge[pxCoord]) {
            return RenderUtil.compose(50, 50, 50, 255);
        }
        return 0;
    }
}
