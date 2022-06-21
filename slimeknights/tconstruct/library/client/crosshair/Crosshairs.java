package slimeknights.tconstruct.library.client.crosshair;

import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.*;

@SideOnly(Side.CLIENT)
public interface Crosshairs
{
    public static final Crosshair SQUARE = new Crosshair(Util.getResource("textures/gui/crosshair/square.png"));
    public static final Crosshair X = new CrosshairTriangle(Util.getResource("textures/gui/crosshair/x.png"));
    public static final Crosshair INVERSE = new CrosshairTriangle(Util.getResource("textures/gui/crosshair/inverse.png"));
    public static final Crosshair PLUS = new Crosshair(Util.getResource("textures/gui/crosshair/plus.png"));
    public static final Crosshair T = new CrosshairInverseT(Util.getResource("textures/gui/crosshair/t.png"), 15);
}
