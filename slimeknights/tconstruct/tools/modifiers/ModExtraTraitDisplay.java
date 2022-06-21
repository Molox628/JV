package slimeknights.tconstruct.tools.modifiers;

import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.tools.harvest.*;
import slimeknights.tconstruct.library.tinkering.*;
import java.util.stream.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.*;
import java.util.function.*;
import java.util.*;
import com.google.common.collect.*;

public class ModExtraTraitDisplay extends Modifier implements IModifierDisplay
{
    public ModExtraTraitDisplay() {
        super("extratrait");
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
    }
    
    @Override
    public int getColor() {
        return 14540253;
    }
    
    @Override
    public List<List<ItemStack>> getItems() {
        return TinkerHarvestTools.pickaxe.getRequiredComponents().stream().map((Function<? super Object, ?>)PartMaterialType::getPossibleParts).flatMap((Function<? super Object, ? extends Stream<?>>)Collection::stream).map((Function<? super Object, ?>)this::getItems).collect((Collector<? super Object, ?, List<List<ItemStack>>>)Collectors.toList());
    }
    
    private List<ItemStack> getItems(final IToolPart part) {
        final List<Material> possibleMaterials = TinkerRegistry.getAllMaterials().stream().filter(part::canUseMaterial).collect((Collector<? super Material, ?, List<Material>>)Collectors.toList());
        final Material material = possibleMaterials.get(new Random().nextInt(possibleMaterials.size()));
        return (List<ItemStack>)ImmutableList.builder().add((Object)part.getItemstackWithMaterial(material)).addAll((Iterable)ModExtraTrait.EMBOSSMENT_ITEMS).build();
    }
}
