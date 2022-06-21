package slimeknights.tconstruct.tools.common.client;

import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.tools.common.client.module.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.tools.common.tileentity.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import slimeknights.mantle.client.gui.*;
import net.minecraft.inventory.*;
import slimeknights.tconstruct.library.client.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.util.*;
import slimeknights.mantle.util.*;
import net.minecraft.util.text.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.tools.*;
import net.minecraft.util.text.translation.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.*;
import java.util.*;
import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.library.*;

@SideOnly(Side.CLIENT)
public class GuiPartBuilder extends GuiTinkerStation
{
    private static final ResourceLocation BACKGROUND;
    public static final int Column_Count = 4;
    protected GuiButtonsPartCrafter buttons;
    protected GuiInfoPanel info;
    protected GuiSideInventory sideInventory;
    protected ContainerPatternChest.DynamicChestInventory chestContainer;
    
    public GuiPartBuilder(final InventoryPlayer playerInv, final World world, final BlockPos pos, final TilePartBuilder tile) {
        super(world, pos, (ContainerTinkerStation<?>)tile.createContainer(playerInv, world, pos));
        if (this.field_147002_h instanceof ContainerPartBuilder) {
            final ContainerPartBuilder container = (ContainerPartBuilder)this.field_147002_h;
            if (container.isPartCrafter()) {
                this.addModule((GuiModule)(this.buttons = new GuiButtonsPartCrafter(this, (Container)container, container.patternChest)));
            }
            else {
                this.chestContainer = (ContainerPatternChest.DynamicChestInventory)container.getSubContainer((Class)ContainerPatternChest.DynamicChestInventory.class);
                if (this.chestContainer != null) {
                    this.addModule((GuiModule)(this.sideInventory = new GuiSideInventory(this, (Container)this.chestContainer, this.chestContainer.getSlotCount(), this.chestContainer.columns)));
                }
            }
            this.info = new GuiInfoPanel(this, (Container)container);
            this.info.field_147000_g = this.field_147000_g;
            this.addModule((GuiModule)this.info);
        }
    }
    
    public void func_146977_a(final Slot slotIn) {
        if (this.field_147002_h instanceof ContainerPartBuilder) {
            final ContainerPartBuilder container = (ContainerPartBuilder)this.field_147002_h;
            if (container.isPartCrafter() && slotIn.field_75224_c == container.patternChest) {
                return;
            }
        }
        super.func_146977_a(slotIn);
    }
    
    public boolean func_146981_a(final Slot slotIn, final int mouseX, final int mouseY) {
        if (this.field_147002_h instanceof ContainerPartBuilder) {
            final ContainerPartBuilder container = (ContainerPartBuilder)this.field_147002_h;
            if (container.isPartCrafter() && slotIn.field_75224_c == container.patternChest) {
                return false;
            }
        }
        return super.func_146981_a(slotIn, mouseX, mouseY);
    }
    
    protected void func_146976_a(final float partialTicks, final int mouseX, final int mouseY) {
        this.drawBackground(GuiPartBuilder.BACKGROUND);
        if (this.sideInventory != null) {
            this.sideInventory.updateSlotCount(this.chestContainer.getSizeInventory());
        }
        this.drawIconEmpty(this.container.func_75139_a(1), Icons.ICON_Shard);
        this.drawIconEmpty(this.container.func_75139_a(2), Icons.ICON_Pattern);
        this.drawIconEmpty(this.container.func_75139_a(3), Icons.ICON_Ingot);
        this.drawIconEmpty(this.container.func_75139_a(4), Icons.ICON_Block);
        String amount = null;
        final Material material = this.getMaterial(this.container.func_75139_a(3).func_75211_c(), this.container.func_75139_a(4).func_75211_c());
        if (material != null) {
            final int count = 0;
            final Optional<RecipeMatch.Match> matchOptional = (Optional<RecipeMatch.Match>)material.matchesRecursively((NonNullList)ListUtil.getListFrom(this.container.func_75139_a(3).func_75211_c(), this.container.func_75139_a(4).func_75211_c()));
            if (matchOptional.isPresent()) {
                final int matchAmount = matchOptional.get().amount;
                amount = Util.df.format(matchAmount / 144.0f);
                final Item part = Pattern.getPartFromTag(this.container.func_75139_a(2).func_75211_c());
                if (part instanceof IToolPart && matchAmount < ((IToolPart)part).getCost()) {
                    amount = TextFormatting.DARK_RED + amount + TextFormatting.RESET;
                }
            }
        }
        if (amount != null) {
            int x = this.cornerX + this.realWidth / 2;
            final int y = this.cornerY + 63;
            final String text = Util.translateFormatted("gui.partbuilder.material_value", amount, material.getLocalizedName());
            x -= this.field_146289_q.func_78256_a(text) / 2;
            this.field_146289_q.func_180455_b(text, (float)x, (float)y, 7829367, false);
        }
        super.func_146976_a(partialTicks, mouseX, mouseY);
    }
    
    @Override
    public void updateDisplay() {
        final ItemStack output = this.container.func_75139_a(0).func_75211_c();
        if (!output.func_190926_b()) {
            if (output.func_77973_b() instanceof ToolPart) {
                final ToolPart toolPart = (ToolPart)output.func_77973_b();
                final Material material = toolPart.getMaterial(output);
                if (!toolPart.canUseMaterial(material)) {
                    final String materialName = material.getLocalizedNameColored() + TextFormatting.WHITE;
                    final String error = I18n.func_74837_a("gui.error.useless_tool_part", new Object[] { materialName, new ItemStack((Item)toolPart).func_82833_r() });
                    this.warning(error);
                }
                else {
                    this.setDisplayForMaterial(material);
                }
            }
        }
        else {
            final Material material2 = this.getMaterial(this.container.func_75139_a(3).func_75211_c(), this.container.func_75139_a(4).func_75211_c());
            if (material2 != null) {
                this.setDisplayForMaterial(material2);
            }
            else {
                this.info.setCaption(this.container.getInventoryDisplayName());
                this.info.setText(I18n.func_74838_a("gui.partbuilder.info"));
            }
        }
    }
    
    @Override
    public void error(final String message) {
        this.info.setCaption(I18n.func_74838_a("gui.error"));
        this.info.setText(message);
    }
    
    @Override
    public void warning(final String message) {
        this.info.setCaption(I18n.func_74838_a("gui.warning"));
        this.info.setText(message);
    }
    
    public void updateButtons() {
        if (this.buttons != null) {
            Minecraft.func_71410_x().func_152344_a(() -> this.buttons.updatePosition(this.cornerX, this.cornerY, this.realWidth, this.realHeight));
        }
    }
    
    protected void setDisplayForMaterial(final Material material) {
        this.info.setCaption(material.getLocalizedNameColored());
        final List<String> stats = (List<String>)Lists.newLinkedList();
        final List<String> tips = (List<String>)Lists.newArrayList();
        for (final IMaterialStats stat : material.getAllStats()) {
            final List<String> info = stat.getLocalizedInfo();
            if (!info.isEmpty()) {
                stats.add(TextFormatting.UNDERLINE + stat.getLocalizedName());
                stats.addAll(info);
                stats.add(null);
                tips.add(null);
                tips.addAll(stat.getLocalizedDesc());
                tips.add(null);
            }
        }
        for (final ITrait trait : material.getAllTraits()) {
            if (!trait.isHidden()) {
                stats.add(material.getTextColor() + trait.getLocalizedName());
                tips.add(material.getTextColor() + trait.getLocalizedDesc());
            }
        }
        if (!stats.isEmpty() && stats.get(stats.size() - 1) == null) {
            stats.remove(stats.size() - 1);
            tips.remove(tips.size() - 1);
        }
        this.info.setText(stats, tips);
    }
    
    protected Material getMaterial(final ItemStack... stacks) {
        for (final ItemStack stack : stacks) {
            if (!stack.func_190926_b()) {
                if (stack.func_77973_b() instanceof IMaterialItem) {
                    return ((IMaterialItem)stack.func_77973_b()).getMaterial(stack);
                }
            }
        }
        for (final Material material : TinkerRegistry.getAllMaterials()) {
            if (material.matches(stacks).isPresent()) {
                return material;
            }
        }
        return null;
    }
    
    private Material getMaterialItem(final ItemStack stack) {
        return null;
    }
    
    static {
        BACKGROUND = Util.getResource("textures/gui/partbuilder.png");
    }
}
