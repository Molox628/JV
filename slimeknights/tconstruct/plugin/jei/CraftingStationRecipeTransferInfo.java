package slimeknights.tconstruct.plugin.jei;

import mezz.jei.api.recipe.transfer.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import javax.annotation.*;
import java.util.*;
import net.minecraft.inventory.*;

public class CraftingStationRecipeTransferInfo implements IRecipeTransferInfo<ContainerCraftingStation>
{
    @Nonnull
    public Class<ContainerCraftingStation> getContainerClass() {
        return ContainerCraftingStation.class;
    }
    
    @Nonnull
    public String getRecipeCategoryUid() {
        return "minecraft.crafting";
    }
    
    public boolean canHandle(final ContainerCraftingStation container) {
        return true;
    }
    
    @Nonnull
    public List<Slot> getRecipeSlots(final ContainerCraftingStation container) {
        final List<Slot> slots = new ArrayList<Slot>();
        for (int i = 1; i < 10; ++i) {
            slots.add(container.func_75139_a(i));
        }
        return slots;
    }
    
    @Nonnull
    public List<Slot> getInventorySlots(final ContainerCraftingStation container) {
        final List<Slot> slots = new ArrayList<Slot>();
        for (int i = 10; i < container.field_75151_b.size(); ++i) {
            slots.add(container.func_75139_a(i));
        }
        return slots;
    }
}
