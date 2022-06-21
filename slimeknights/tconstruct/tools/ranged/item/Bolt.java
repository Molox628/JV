package slimeknights.tconstruct.tools.ranged.item;

import slimeknights.tconstruct.library.tools.ranged.*;
import slimeknights.tconstruct.library.tinkering.*;
import com.google.common.collect.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.common.config.*;
import java.util.*;
import java.util.stream.*;
import slimeknights.tconstruct.library.utils.*;
import javax.annotation.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import slimeknights.tconstruct.tools.traits.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.tools.melee.item.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.library.entity.*;
import slimeknights.tconstruct.tools.common.entity.*;
import slimeknights.tconstruct.library.tools.*;

public class Bolt extends ProjectileCore
{
    protected final List<PartMaterialType> toolBuildComponents;
    
    public Bolt() {
        super(new PartMaterialType[] { PartMaterialType.arrowShaft(TinkerTools.boltCore), new BoltHeadPartMaterialType(TinkerTools.boltCore), PartMaterialType.fletching(TinkerTools.fletching) });
        this.addCategory(Category.NO_MELEE, Category.PROJECTILE);
        this.toolBuildComponents = (List<PartMaterialType>)ImmutableList.of((Object)this.requiredComponents[0], (Object)this.requiredComponents[2]);
    }
    
    @Override
    public List<PartMaterialType> getToolBuildComponents() {
        return this.toolBuildComponents;
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            for (final Material head : TinkerRegistry.getAllMaterials()) {
                final List<Material> mats = new ArrayList<Material>(3);
                if (head.hasStats("head")) {
                    mats.add(TinkerMaterials.wood);
                    mats.add(head);
                    mats.add(TinkerMaterials.feather);
                    final ItemStack tool = this.buildItem(mats);
                    if (!this.hasValidMaterials(tool)) {
                        continue;
                    }
                    subItems.add((Object)tool);
                    if (!Config.listAllMaterials) {
                        break;
                    }
                    continue;
                }
            }
        }
    }
    
    @Override
    public Material getMaterialForPartForGuiRendering(final int index) {
        return super.getMaterialForPartForGuiRendering(index + 1);
    }
    
    @Override
    public ItemStack buildItemForRenderingInGui() {
        final List<Material> materials = IntStream.range(0, this.getRequiredComponents().size()).mapToObj(x$0 -> super.getMaterialForPartForGuiRendering(x$0)).collect((Collector<? super Object, ?, List<Material>>)Collectors.toList());
        return this.buildItemForRendering(materials);
    }
    
    @Override
    public float damagePotential() {
        return 1.0f;
    }
    
    @Override
    public double attackSpeed() {
        return 1.0;
    }
    
    @Nonnull
    @Override
    public ItemStack buildItemFromStacks(final NonNullList<ItemStack> inputStacks) {
        final List<ItemStack> stacks = inputStacks.stream().filter(itemStack -> !itemStack.func_190926_b()).collect((Collector<? super Object, ?, List<ItemStack>>)Collectors.toList());
        if (stacks.size() != 2) {
            return ItemStack.field_190927_a;
        }
        final ItemStack boltCore = stacks.get(0);
        final ItemStack fletching = stacks.get(1);
        final ItemStack boltCoreHead = BoltCore.getHeadStack(boltCore);
        return super.buildItemFromStacks(ListUtil.getListFrom(boltCore, boltCoreHead, fletching));
    }
    
    @Override
    public boolean dealDamageRanged(final ItemStack stack, final Entity projectile, final EntityLivingBase player, final Entity target, final float damage) {
        if (target instanceof EntityEnderman && ((EntityEnderman)target).func_70660_b((Potion)TraitEnderference.Enderference) != null) {
            return target.func_70097_a((DamageSource)new DamageSourceProjectileForEndermen("arrow", projectile, (Entity)player), damage);
        }
        final DamageSource damageSource = new EntityDamageSourceIndirect("arrow", projectile, (Entity)player).func_76349_b();
        return Rapier.dealHybridDamage(damageSource, target, damage);
    }
    
    @Override
    public ProjectileNBT buildTagData(final List<Material> materials) {
        final ProjectileNBT data = new ProjectileNBT();
        final ArrowShaftMaterialStats shaft = materials.get(0).getStatsOrUnknown("shaft");
        final HeadMaterialStats head = materials.get(1).getStatsOrUnknown("head");
        final FletchingMaterialStats fletching = materials.get(2).getStatsOrUnknown("fletching");
        data.head(head);
        data.fletchings(fletching);
        data.shafts(this, shaft);
        final ProjectileNBT projectileNBT = data;
        projectileNBT.durability *= (int)0.8f;
        return data;
    }
    
    @Override
    public EntityProjectileBase getProjectile(final ItemStack stack, final ItemStack bow, final World world, final EntityPlayer player, final float speed, float inaccuracy, final float power, final boolean usedAmmo) {
        inaccuracy -= (1.0f - 1.0f / ProjectileNBT.from(stack).accuracy) * speed / 2.0f;
        return new EntityBolt(world, player, speed, inaccuracy, power, this.getProjectileStack(stack, world, player, usedAmmo), bow);
    }
    
    private static class BoltHeadPartMaterialType extends PartMaterialType
    {
        public BoltHeadPartMaterialType(final IToolPart part) {
            super(part, new String[] { "head" });
        }
        
        @Override
        public boolean isValidMaterial(final Material material) {
            return material.isCastable() && super.isValidMaterial(material);
        }
    }
}
