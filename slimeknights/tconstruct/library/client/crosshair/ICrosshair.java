package slimeknights.tconstruct.library.client.crosshair;

import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public interface ICrosshair
{
    public static final ICrosshair DEFAULT = new ICrosshair() {
        @Override
        public void render(final float charge, final float width, final float height, final float partialTicks) {
        }
    };
    
    void render(final float p0, final float p1, final float p2, final float p3);
}
