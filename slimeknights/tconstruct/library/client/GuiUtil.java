package slimeknights.tconstruct.library.client;

import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.fluids.*;
import net.minecraft.util.text.*;
import slimeknights.tconstruct.smeltery.client.*;
import java.util.function.*;
import slimeknights.tconstruct.smeltery.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.smeltery.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.smeltery.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.*;

public class GuiUtil
{
    protected static Minecraft mc;
    private static Map<Fluid, List<FluidGuiEntry>> fluidGui;
    private static boolean smelteryLoaded;
    
    private GuiUtil() {
    }
    
    public static void renderTiledTextureAtlas(final int x, final int y, final int width, final int height, final float depth, final TextureAtlasSprite sprite, final boolean upsideDown) {
        final Tessellator tessellator = Tessellator.func_178181_a();
        final BufferBuilder worldrenderer = tessellator.func_178180_c();
        worldrenderer.func_181668_a(7, DefaultVertexFormats.field_181707_g);
        GuiUtil.mc.field_71446_o.func_110577_a(TextureMap.field_110575_b);
        putTiledTextureQuads(worldrenderer, x, y, width, height, depth, sprite, upsideDown);
        tessellator.func_78381_a();
    }
    
    public static void renderTiledFluid(final int x, final int y, final int width, final int height, final float depth, final FluidStack fluidStack) {
        final TextureAtlasSprite fluidSprite = GuiUtil.mc.func_147117_R().func_110572_b(fluidStack.getFluid().getStill(fluidStack).toString());
        RenderUtil.setColorRGBA(fluidStack.getFluid().getColor(fluidStack));
        renderTiledTextureAtlas(x, y, width, height, depth, fluidSprite, fluidStack.getFluid().isGaseous(fluidStack));
    }
    
    public static void putTiledTextureQuads(final BufferBuilder renderer, final int x, int y, final int width, int height, final float depth, final TextureAtlasSprite sprite, final boolean upsideDown) {
        final float u1 = sprite.func_94209_e();
        final float v1 = sprite.func_94206_g();
        do {
            final int renderHeight = Math.min(sprite.func_94216_b(), height);
            height -= renderHeight;
            final float v2 = sprite.func_94207_b((double)(16.0f * renderHeight / sprite.func_94216_b()));
            int x2 = x;
            int width2 = width;
            do {
                final int renderWidth = Math.min(sprite.func_94211_a(), width2);
                width2 -= renderWidth;
                final float u2 = sprite.func_94214_a((double)(16.0f * renderWidth / sprite.func_94211_a()));
                if (upsideDown) {
                    renderer.func_181662_b((double)x2, (double)y, (double)depth).func_187315_a((double)u2, (double)v1).func_181675_d();
                    renderer.func_181662_b((double)x2, (double)(y + renderHeight), (double)depth).func_187315_a((double)u2, (double)v2).func_181675_d();
                    renderer.func_181662_b((double)(x2 + renderWidth), (double)(y + renderHeight), (double)depth).func_187315_a((double)u1, (double)v2).func_181675_d();
                    renderer.func_181662_b((double)(x2 + renderWidth), (double)y, (double)depth).func_187315_a((double)u1, (double)v1).func_181675_d();
                }
                else {
                    renderer.func_181662_b((double)x2, (double)y, (double)depth).func_187315_a((double)u1, (double)v1).func_181675_d();
                    renderer.func_181662_b((double)x2, (double)(y + renderHeight), (double)depth).func_187315_a((double)u1, (double)v2).func_181675_d();
                    renderer.func_181662_b((double)(x2 + renderWidth), (double)(y + renderHeight), (double)depth).func_187315_a((double)u2, (double)v2).func_181675_d();
                    renderer.func_181662_b((double)(x2 + renderWidth), (double)y, (double)depth).func_187315_a((double)u2, (double)v1).func_181675_d();
                }
                x2 += renderWidth;
            } while (width2 > 0);
            y += renderHeight;
        } while (height > 0);
    }
    
    @Deprecated
    public static List<String> drawTankTooltip(final SmelteryTank tank, final int mouseX, final int mouseY, final int xmin, final int ymin, final int xmax, final int ymax) {
        return getTankTooltip(tank, mouseX, mouseY, xmin, ymin, xmax, ymax);
    }
    
    public static List<String> getTankTooltip(final SmelteryTank tank, final int mouseX, final int mouseY, final int xmin, final int ymin, final int xmax, final int ymax) {
        if (xmin <= mouseX && mouseX < xmax && ymin <= mouseY && mouseY < ymax) {
            final FluidStack hovered = getFluidHovered(tank, ymax - mouseY - 1, ymax - ymin);
            final List<String> text = (List<String>)Lists.newArrayList();
            final Consumer<Integer> stringFn = Util.isShiftKeyDown() ? (i -> amountToString(i, text)) : (i -> amountToIngotString(i, text));
            if (hovered == null) {
                final int usedCap = tank.getFluidAmount();
                final int maxCap = tank.getCapacity();
                text.add(TextFormatting.WHITE + Util.translate("gui.smeltery.capacity", new Object[0]));
                stringFn.accept(maxCap);
                text.add(Util.translateFormatted("gui.smeltery.capacity_available", new Object[0]));
                stringFn.accept(maxCap - usedCap);
                text.add(Util.translateFormatted("gui.smeltery.capacity_used", new Object[0]));
                stringFn.accept(usedCap);
                if (!Util.isShiftKeyDown()) {
                    text.add("");
                    text.add(Util.translate("tooltip.tank.holdShift", new Object[0]));
                }
            }
            else {
                text.add(TextFormatting.WHITE + hovered.getLocalizedName());
                liquidToString(hovered, text);
            }
            return text;
        }
        return null;
    }
    
    private static FluidStack getFluidHovered(final SmelteryTank tank, int y, final int height) {
        final int[] heights = calcLiquidHeights(tank.getFluids(), tank.getCapacity(), height);
        for (int i = 0; i < heights.length; ++i) {
            if (y < heights[i]) {
                return tank.getFluids().get(i);
            }
            y -= heights[i];
        }
        return null;
    }
    
    private static int[] calcLiquidHeights(final List<FluidStack> liquids, final int capacity, final int height) {
        return SmelteryTankRenderer.calcLiquidHeights(liquids, capacity, height, 3);
    }
    
    public static void drawGuiTank(final SmelteryTank liquids, final int x, final int y, final int w, final int height, final float zLevel) {
        if (liquids.getFluidAmount() > 0) {
            final int capacity = Math.max(liquids.getFluidAmount(), liquids.getCapacity());
            final int[] heights = calcLiquidHeights(liquids.getFluids(), capacity, height);
            int bottom = y + w;
            for (int i = 0; i < heights.length; ++i) {
                final int h = heights[i];
                final FluidStack liquid = liquids.getFluids().get(i);
                renderTiledFluid(x, bottom - h, w, h, zLevel, liquid);
                bottom -= h;
            }
        }
    }
    
    public static FluidStack getFluidStackAtPosition(final SmelteryTank tank, final int mouseX, final int mouseY, final int xmin, final int ymin, final int xmax, final int ymax) {
        return getFluidStackIndexAtPosition(tank, mouseX, mouseY, xmin, ymin, xmax, ymax).map((Function<? super Integer, ? extends FluidStack>)tank.getFluids()::get).orElse(null);
    }
    
    public static void handleTankClick(final SmelteryTank tank, final int mouseX, final int mouseY, final int xmin, final int ymin, final int xmax, final int ymax) {
        getFluidStackIndexAtPosition(tank, mouseX, mouseY, xmin, ymin, xmax, ymax).ifPresent(i -> TinkerNetwork.sendToServer((AbstractPacket)new SmelteryFluidClicked(i)));
    }
    
    public static Optional<Integer> getFluidStackIndexAtPosition(final SmelteryTank tank, final int mouseX, final int mouseY, final int xmin, final int ymin, final int xmax, final int ymax) {
        if (xmin <= mouseX && mouseX < xmax && ymin <= mouseY && mouseY < ymax) {
            final int[] heights = calcLiquidHeights(tank.getFluids(), tank.getCapacity(), ymax - ymin);
            int y = ymax - mouseY - 1;
            for (int i = 0; i < heights.length; ++i) {
                if (y < heights[i]) {
                    return Optional.of(i);
                }
                y -= heights[i];
            }
        }
        return Optional.empty();
    }
    
    public static void liquidToString(final FluidStack fluid, final List<String> text) {
        int amount = fluid.amount;
        if (GuiUtil.smelteryLoaded && !Util.isShiftKeyDown()) {
            List<FluidGuiEntry> entries = GuiUtil.fluidGui.get(fluid.getFluid());
            if (entries == null) {
                entries = calcFluidGuiEntries(fluid.getFluid());
                GuiUtil.fluidGui.put(fluid.getFluid(), entries);
            }
            for (final FluidGuiEntry entry : entries) {
                amount = calcLiquidText(amount, entry.amount, entry.getText(), text);
            }
        }
        amountToString(amount, text);
    }
    
    public static void onFluidTooltip(final int slotIndex, final boolean input, final FluidStack fluid, final List<String> text) {
        final String ingredientName = text.get(0);
        final String modName = text.get(text.size() - 1);
        text.clear();
        text.add(ingredientName);
        final int originalAmount;
        int amount = originalAmount = fluid.amount;
        if (GuiUtil.smelteryLoaded && !Util.isShiftKeyDown()) {
            List<FluidGuiEntry> entries = GuiUtil.fluidGui.get(fluid.getFluid());
            if (entries == null) {
                entries = calcFluidGuiEntries(fluid.getFluid());
                GuiUtil.fluidGui.put(fluid.getFluid(), entries);
            }
            for (final FluidGuiEntry entry : entries) {
                amount = calcLiquidText(amount, entry.amount, entry.getText(), text);
            }
        }
        calcLiquidText(amount, 1, Util.translate("gui.smeltery.liquid.millibucket", new Object[0]), text);
        if (!Util.isShiftKeyDown() && amount != originalAmount) {
            text.add(Util.translate("tooltip.tank.holdShift", new Object[0]));
        }
        text.add(modName);
    }
    
    public static void amountToIngotString(int amount, final List<String> text) {
        amount = calcLiquidText(amount, 144, Util.translate("gui.smeltery.liquid.ingot", new Object[0]), text);
        amountToString(amount, text);
    }
    
    public static void amountToString(int amount, final List<String> text) {
        amount = calcLiquidText(amount, 1000000, Util.translate("gui.smeltery.liquid.kilobucket", new Object[0]), text);
        amount = calcLiquidText(amount, 1000, Util.translate("gui.smeltery.liquid.bucket", new Object[0]), text);
        calcLiquidText(amount, 1, Util.translate("gui.smeltery.liquid.millibucket", new Object[0]), text);
    }
    
    private static List<FluidGuiEntry> calcFluidGuiEntries(final Fluid fluid) {
        final List<FluidGuiEntry> list = (List<FluidGuiEntry>)Lists.newArrayList();
        for (final ICastingRecipe irecipe : TinkerRegistry.getAllBasinCastingRecipes()) {
            if (irecipe instanceof CastingRecipe) {
                final CastingRecipe recipe = (CastingRecipe)irecipe;
                if (recipe.getFluid().getFluid() != fluid || recipe.cast != null) {
                    continue;
                }
                list.add(new FluidGuiEntry(recipe.getFluid().amount, "gui.smeltery.liquid.block"));
            }
        }
        for (final ICastingRecipe irecipe : TinkerRegistry.getAllTableCastingRecipes()) {
            if (irecipe instanceof CastingRecipe) {
                final CastingRecipe recipe = (CastingRecipe)irecipe;
                if (recipe.getFluid().getFluid() != fluid || recipe.cast == null) {
                    continue;
                }
                if (recipe.cast.matches((NonNullList)ListUtil.getListFrom(TinkerSmeltery.castNugget)).isPresent()) {
                    list.add(new FluidGuiEntry(recipe.getFluid().amount, "gui.smeltery.liquid.nugget"));
                }
                if (recipe.cast.matches((NonNullList)ListUtil.getListFrom(TinkerSmeltery.castIngot)).isPresent()) {
                    list.add(new FluidGuiEntry(recipe.getFluid().amount, "gui.smeltery.liquid.ingot"));
                }
                if (!recipe.cast.matches((NonNullList)ListUtil.getListFrom(TinkerSmeltery.castGem)).isPresent()) {
                    continue;
                }
                list.add(new FluidGuiEntry(recipe.getFluid().amount, "gui.smeltery.liquid.gem"));
            }
        }
        list.sort((o1, o2) -> o2.amount - o1.amount);
        return (List<FluidGuiEntry>)ImmutableList.copyOf((Collection)list);
    }
    
    private static int calcLiquidText(final int amount, final int divider, final String unit, final List<String> text) {
        final int full = amount / divider;
        if (full > 0) {
            text.add(String.format("%s%d %s", TextFormatting.GRAY, full, unit));
        }
        return amount % divider;
    }
    
    static {
        GuiUtil.mc = Minecraft.func_71410_x();
        GuiUtil.fluidGui = (Map<Fluid, List<FluidGuiEntry>>)Maps.newHashMap();
        GuiUtil.smelteryLoaded = TConstruct.pulseManager.isPulseLoaded("TinkerSmeltery");
    }
    
    private static class FluidGuiEntry
    {
        public final int amount;
        public final String unlocName;
        
        private FluidGuiEntry(final int amount, final String unlocName) {
            this.amount = amount;
            this.unlocName = unlocName;
        }
        
        public String getText() {
            return Util.translate(this.unlocName, new Object[0]);
        }
    }
}
