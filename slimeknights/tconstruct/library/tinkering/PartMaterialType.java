package slimeknights.tconstruct.library.tinkering;

import slimeknights.tconstruct.library.tools.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.materials.*;
import java.util.*;
import slimeknights.tconstruct.library.traits.*;
import com.google.common.collect.*;

public class PartMaterialType
{
    private final Set<IToolPart> neededPart;
    private final String[] neededTypes;
    
    public PartMaterialType(final IToolPart part, final String... statIDs) {
        (this.neededPart = new HashSet<IToolPart>()).add(part);
        this.neededTypes = statIDs;
    }
    
    public boolean isValid(final ItemStack stack) {
        if (stack.func_77973_b() instanceof IToolPart) {
            final IToolPart toolPart = (IToolPart)stack.func_77973_b();
            return this.isValid(toolPart, toolPart.getMaterial(stack));
        }
        return false;
    }
    
    public boolean isValid(final IToolPart part, final Material material) {
        return this.isValidItem(part) && this.isValidMaterial(material);
    }
    
    public boolean isValidItem(final IToolPart part) {
        return this.neededPart.contains(part);
    }
    
    public boolean isValidMaterial(final Material material) {
        for (final String type : this.neededTypes) {
            if (!material.hasStats(type)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean usesStat(final String statID) {
        for (final String type : this.neededTypes) {
            if (type.equals(statID)) {
                return true;
            }
        }
        return false;
    }
    
    public Collection<ITrait> getApplicableTraitsForMaterial(final Material material) {
        if (!this.isValidMaterial(material)) {
            return (Collection<ITrait>)ImmutableList.of();
        }
        final ImmutableList.Builder<ITrait> traits = (ImmutableList.Builder<ITrait>)ImmutableList.builder();
        for (final String type : this.neededTypes) {
            traits.addAll((Iterable)material.getAllTraitsForStats(type));
        }
        if (traits.build().isEmpty()) {
            traits.addAll((Iterable)material.getDefaultTraits());
        }
        return (Collection<ITrait>)traits.build();
    }
    
    public Set<IToolPart> getPossibleParts() {
        return (Set<IToolPart>)ImmutableSet.copyOf((Collection)this.neededPart);
    }
    
    public static PartMaterialType head(final IToolPart part) {
        return new PartMaterialType(part, new String[] { "head" });
    }
    
    public static PartMaterialType handle(final IToolPart part) {
        return new PartMaterialType(part, new String[] { "handle" });
    }
    
    public static PartMaterialType extra(final IToolPart part) {
        return new PartMaterialType(part, new String[] { "extra" });
    }
    
    public static PartMaterialType bow(final IToolPart part) {
        return new PartMaterialType(part, new String[] { "bow", "head" });
    }
    
    public static PartMaterialType bowstring(final IToolPart part) {
        return new PartMaterialType(part, new String[] { "bowstring" });
    }
    
    public static PartMaterialType arrowHead(final IToolPart part) {
        return new PartMaterialType(part, new String[] { "head", "projectile" });
    }
    
    public static PartMaterialType arrowShaft(final IToolPart part) {
        return new PartMaterialType(part, new String[] { "shaft" });
    }
    
    public static PartMaterialType fletching(final IToolPart part) {
        return new PartMaterialType(part, new String[] { "fletching" });
    }
    
    public static PartMaterialType crossbow(final IToolPart part) {
        return new PartMaterialType(part, new String[] { "handle", "extra" });
    }
}
