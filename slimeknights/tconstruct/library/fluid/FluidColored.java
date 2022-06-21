package slimeknights.tconstruct.library.fluid;

import net.minecraft.util.*;
import net.minecraftforge.fluids.*;
import net.minecraft.util.text.translation.*;
import slimeknights.tconstruct.library.*;

public class FluidColored extends Fluid
{
    public static ResourceLocation ICON_LiquidStill;
    public static ResourceLocation ICON_LiquidFlowing;
    public static ResourceLocation ICON_MilkStill;
    public static ResourceLocation ICON_MilkFlowing;
    public static ResourceLocation ICON_StoneStill;
    public static ResourceLocation ICON_StoneFlowing;
    public final int color;
    
    public FluidColored(final String fluidName, final int color) {
        this(fluidName, color, FluidColored.ICON_LiquidStill, FluidColored.ICON_LiquidFlowing);
    }
    
    public FluidColored(final String fluidName, int color, final ResourceLocation still, final ResourceLocation flowing) {
        super(fluidName, still, flowing);
        if ((color >> 24 & 0xFF) == 0x0) {
            color |= 0xFF000000;
        }
        this.color = color;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public String getLocalizedName(final FluidStack stack) {
        final String s = this.getUnlocalizedName();
        return (s == null) ? "" : I18n.func_74838_a(s + ".name");
    }
    
    static {
        FluidColored.ICON_LiquidStill = Util.getResource("blocks/fluids/liquid");
        FluidColored.ICON_LiquidFlowing = Util.getResource("blocks/fluids/liquid_flow");
        FluidColored.ICON_MilkStill = Util.getResource("blocks/fluids/milk");
        FluidColored.ICON_MilkFlowing = Util.getResource("blocks/fluids/milk_flow");
        FluidColored.ICON_StoneStill = Util.getResource("blocks/fluids/liquid_stone");
        FluidColored.ICON_StoneFlowing = Util.getResource("blocks/fluids/liquid_stone_flow");
    }
}
