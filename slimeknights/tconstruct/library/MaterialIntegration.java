package slimeknights.tconstruct.library;

import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.*;
import net.minecraftforge.fluids.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraftforge.oredict.*;
import slimeknights.tconstruct.smeltery.*;
import net.minecraftforge.registries.*;
import net.minecraft.item.crafting.*;
import slimeknights.tconstruct.tools.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.shared.*;

public class MaterialIntegration
{
    public Material material;
    public Fluid fluid;
    public String oreSuffix;
    public String[] oreRequirement;
    public String representativeItem;
    private boolean integrated;
    private boolean preInit;
    private boolean toolforge;
    private boolean addedFluidBlock;
    
    public MaterialIntegration(final Material material) {
        this(material, null);
    }
    
    public MaterialIntegration(final Material material, final Fluid fluid) {
        this(null, material, fluid, null);
    }
    
    public MaterialIntegration(final Material material, final Fluid fluid, final String oreSuffix) {
        this("ingot" + oreSuffix, material, fluid, oreSuffix);
    }
    
    public MaterialIntegration(final String oreRequirement, final Material material, final Fluid fluid, final String oreSuffix) {
        this(material, fluid, oreSuffix, new String[] { oreRequirement });
    }
    
    public MaterialIntegration(final Material material, final Fluid fluid, final String oreSuffix, final String... oreRequirement) {
        this.toolforge = false;
        this.material = material;
        this.fluid = fluid;
        this.oreSuffix = oreSuffix;
        this.representativeItem = "ingot" + oreSuffix;
        this.oreRequirement = ((oreRequirement[0] == null) ? new String[0] : oreRequirement);
        this.integrated = false;
        this.preInit = false;
        this.addedFluidBlock = false;
    }
    
    public MaterialIntegration toolforge() {
        this.toolforge = true;
        return this;
    }
    
    public MaterialIntegration setRepresentativeItem(final String representativeItem) {
        this.representativeItem = representativeItem;
        return this;
    }
    
    public boolean isIntegrated() {
        return this.integrated;
    }
    
    public void preInit() {
        if (this.preInit) {
            return;
        }
        this.preInit = true;
        if (!TConstruct.pulseManager.isPulseLoaded("TinkerSmeltery")) {
            this.fluid = null;
        }
        if (this.fluid != null) {
            final Fluid registeredFluid = FluidRegistry.getFluid(this.fluid.getName());
            if (!FluidRegistry.getBucketFluids().contains(registeredFluid)) {
                FluidRegistry.addBucketForFluid(registeredFluid);
            }
        }
        if (this.material != null) {
            TinkerRegistry.addMaterial(this.material);
            if (this.fluid != null) {
                this.material.setFluid(this.fluid);
                this.material.setCastable(true);
            }
            else {
                this.material.setCraftable(true);
            }
        }
    }
    
    public void integrate() {
        if (this.integrated) {
            return;
        }
        if (this.oreRequirement != null && this.oreRequirement.length > 0 && !Config.forceRegisterAll) {
            for (final String ore : this.oreRequirement) {
                if (OreDictionary.getOres(ore, false).isEmpty()) {
                    return;
                }
            }
        }
        this.integrated = true;
        if (this.fluid != null && this.oreSuffix != null) {
            TinkerSmeltery.registerOredictMeltingCasting(this.fluid, this.oreSuffix);
        }
        if (this.material != null) {
            this.material.setVisible();
            TinkerSmeltery.registerToolpartMeltingCasting(this.material);
            this.registerRepresentativeItem();
        }
    }
    
    private void registerRepresentativeItem() {
        if (this.material.getRepresentativeItem().func_190926_b() && this.representativeItem != null && !this.representativeItem.isEmpty()) {
            this.material.setRepresentativeItem(this.representativeItem);
        }
    }
    
    public void registerToolForgeRecipe(final IForgeRegistry<IRecipe> registry) {
        if (this.toolforge && this.oreSuffix != null && !this.oreSuffix.isEmpty()) {
            TinkerTools.registerToolForgeBlock(registry, "block" + this.oreSuffix);
        }
    }
    
    public void registerFluidBlock(final IForgeRegistry<Block> registry) {
        if (this.fluid != null && this.fluid.getBlock() == null) {
            this.addedFluidBlock = true;
            TinkerFluids.registerMoltenBlock(registry, this.fluid);
        }
    }
    
    public void registerFluidModel() {
        if (this.addedFluidBlock) {
            TinkerFluids.proxy.registerFluidModels(this.fluid);
        }
    }
}
