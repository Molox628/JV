package slimeknights.tconstruct.tools.ranged;

import slimeknights.tconstruct.library.smeltery.*;
import net.minecraft.item.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.tools.ranged.item.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.*;
import java.util.function.*;

public class BoltCoreCastingRecipe implements ICastingRecipe
{
    public static final BoltCoreCastingRecipe INSTANCE;
    public static final int boltCoreAmount = 288;
    
    protected BoltCoreCastingRecipe() {
    }
    
    @Override
    public ItemStack getResult(final ItemStack cast, final Fluid fluid) {
        final Material shaftMaterial = TinkerTools.arrowShaft.getMaterial(cast);
        final Material headMaterial = this.getMaterialForFluid(fluid);
        return BoltCore.getItemstackWithMaterials(shaftMaterial, headMaterial);
    }
    
    private Material getMaterialForFluid(final Fluid fluid) {
        return TinkerRegistry.getAllMaterials().stream().filter(mat -> fluid.equals(mat.getFluid())).findFirst().orElse(Material.UNKNOWN);
    }
    
    @Override
    public boolean matches(final ItemStack cast, final Fluid fluid) {
        return cast.func_77973_b() == TinkerTools.arrowShaft && TinkerTools.arrowShaft.getMaterial(cast).hasStats("shaft") && this.isFluidWithHeadMaterial(fluid);
    }
    
    private boolean isFluidWithHeadMaterial(final Fluid fluid) {
        return TinkerRegistry.getAllMaterials().stream().filter(mat -> mat.hasStats("head")).map((Function<? super Material, ?>)Material::getFluid).anyMatch(fluid::equals);
    }
    
    @Override
    public boolean switchOutputs() {
        return false;
    }
    
    @Override
    public boolean consumesCast() {
        return true;
    }
    
    @Override
    public int getTime() {
        return 120;
    }
    
    @Override
    public int getFluidAmount() {
        return 288;
    }
    
    static {
        INSTANCE = new BoltCoreCastingRecipe();
    }
}
