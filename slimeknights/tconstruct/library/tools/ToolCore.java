package slimeknights.tconstruct.library.tools;

import net.minecraft.creativetab.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;
import net.minecraft.util.text.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.traits.*;
import net.minecraft.client.gui.*;
import slimeknights.tconstruct.common.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.common.config.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.tools.traits.*;
import javax.annotation.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.mantle.util.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.modifiers.*;

public abstract class ToolCore extends TinkersItem implements IToolStationDisplay
{
    public static final int DEFAULT_MODIFIERS = 3;
    public static final String TAG_SWITCHED_HAND_HAX = "SwitchedHand";
    
    public ToolCore(final PartMaterialType... requiredComponents) {
        super(requiredComponents);
        this.func_77637_a((CreativeTabs)TinkerRegistry.tabTools);
        this.setNoRepair();
        TinkerRegistry.registerTool(this);
        this.addCategory(Category.TOOL);
    }
    
    public int getMaxDamage(final ItemStack stack) {
        return ToolHelper.getDurabilityStat(stack);
    }
    
    public void setDamage(final ItemStack stack, final int damage) {
        final int max = this.getMaxDamage(stack);
        super.setDamage(stack, Math.min(max, damage));
        if (this.getDamage(stack) == max) {
            ToolHelper.breakTool(stack, null);
        }
    }
    
    public boolean func_77645_m() {
        return true;
    }
    
    public boolean showDurabilityBar(final ItemStack stack) {
        return super.showDurabilityBar(stack) && !ToolHelper.isBroken(stack);
    }
    
    public float miningSpeedModifier() {
        return 1.0f;
    }
    
    public abstract float damagePotential();
    
    public float damageCutoff() {
        return 15.0f;
    }
    
    public abstract double attackSpeed();
    
    public float knockback() {
        return 1.0f;
    }
    
    public boolean dealDamage(final ItemStack stack, final EntityLivingBase player, final Entity entity, final float damage) {
        if (player instanceof EntityPlayer) {
            return entity.func_70097_a(DamageSource.func_76365_a((EntityPlayer)player), damage);
        }
        return entity.func_70097_a(DamageSource.func_76358_a(player), damage);
    }
    
    protected boolean readyForSpecialAttack(final EntityLivingBase player) {
        return player instanceof EntityPlayer && ((EntityPlayer)player).func_184825_o(0.5f) > 0.9f;
    }
    
    public void reduceDurabilityOnHit(final ItemStack stack, final EntityPlayer player, float damage) {
        damage = Math.max(1.0f, damage / 10.0f);
        if (!this.hasCategory(Category.WEAPON)) {
            damage *= 2.0f;
        }
        ToolHelper.damageTool(stack, (int)damage, (EntityLivingBase)player);
    }
    
    public float func_150893_a(final ItemStack stack, final IBlockState state) {
        if (this.isEffective(state) || ToolHelper.isToolEffective(stack, state)) {
            return ToolHelper.calcDigSpeed(stack, state);
        }
        return super.func_150893_a(stack, state);
    }
    
    public boolean isEffective(final IBlockState state) {
        return false;
    }
    
    public boolean canHarvestBlock(@Nonnull final IBlockState state, final ItemStack stack) {
        return this.isEffective(state) && !ToolHelper.isBroken(stack);
    }
    
    public boolean onBlockStartBreak(final ItemStack itemstack, final BlockPos pos, final EntityPlayer player) {
        if (!ToolHelper.isBroken(itemstack) && this instanceof IAoeTool && ((IAoeTool)this).isAoeHarvestTool()) {
            for (final BlockPos extraPos : ((IAoeTool)this).getAOEBlocks(itemstack, player.func_130014_f_(), player, pos)) {
                this.breakExtraBlock(itemstack, player.func_130014_f_(), player, extraPos, pos);
            }
        }
        if (DualToolHarvestUtils.shouldUseOffhand((EntityLivingBase)player, pos, player.func_184614_ca())) {
            final ItemStack off = player.func_184592_cb();
            this.switchItemsInHands((EntityLivingBase)player);
            final NBTTagCompound tag = TagUtil.getTagSafe(off);
            tag.func_74772_a("SwitchedHand", player.func_130014_f_().func_82737_E());
            off.func_77982_d(tag);
        }
        return this.breakBlock(itemstack, pos, player);
    }
    
    protected boolean breakBlock(final ItemStack itemstack, final BlockPos pos, final EntityPlayer player) {
        return super.onBlockStartBreak(itemstack, pos, player);
    }
    
    protected void breakExtraBlock(final ItemStack tool, final World world, final EntityPlayer player, final BlockPos pos, final BlockPos refPos) {
        ToolHelper.breakExtraBlock(tool, world, player, pos, refPos);
    }
    
    public boolean onLeftClickEntity(final ItemStack stack, final EntityPlayer player, final Entity entity) {
        return ToolHelper.attackEntity(stack, this, (EntityLivingBase)player, entity);
    }
    
    public boolean onEntitySwing(final EntityLivingBase entityLiving, final ItemStack stack) {
        return super.onEntitySwing(entityLiving, stack);
    }
    
    public boolean func_77644_a(final ItemStack stack, final EntityLivingBase target, final EntityLivingBase attacker) {
        final float speed = ToolHelper.getActualAttackSpeed(stack);
        final int time = Math.round(20.0f / speed);
        if (time < target.field_70172_ad / 2) {
            target.field_70172_ad = (target.field_70172_ad + time) / 2;
            target.field_70737_aN = (target.field_70737_aN + time) / 2;
        }
        return super.func_77644_a(stack, target, attacker);
    }
    
    @Nonnull
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull final EntityEquipmentSlot slot, final ItemStack stack) {
        final Multimap<String, AttributeModifier> multimap = (Multimap<String, AttributeModifier>)super.getAttributeModifiers(slot, stack);
        if (slot == EntityEquipmentSlot.MAINHAND && !ToolHelper.isBroken(stack)) {
            multimap.put((Object)SharedMonsterAttributes.field_111264_e.func_111108_a(), (Object)new AttributeModifier(ToolCore.field_111210_e, "Weapon modifier", (double)ToolHelper.getActualAttack(stack), 0));
            multimap.put((Object)SharedMonsterAttributes.field_188790_f.func_111108_a(), (Object)new AttributeModifier(ToolCore.field_185050_h, "Weapon modifier", ToolHelper.getActualAttackSpeed(stack) - 4.0, 0));
        }
        TinkerUtil.getTraitsOrdered(stack).forEach(trait -> trait.getAttributeModifiers(slot, stack, multimap));
        return multimap;
    }
    
    @Override
    public List<String> getInformation(final ItemStack stack) {
        return this.getInformation(stack, true);
    }
    
    @Override
    public void getTooltip(final ItemStack stack, final List<String> tooltips) {
        if (ToolHelper.isBroken(stack)) {
            tooltips.add("" + TextFormatting.DARK_RED + TextFormatting.BOLD + this.getBrokenTooltip(stack));
        }
        super.getTooltip(stack, tooltips);
    }
    
    protected String getBrokenTooltip(final ItemStack itemStack) {
        return Util.translate("tooltip.tool.broken", new Object[0]);
    }
    
    public void getTooltipDetailed(final ItemStack stack, final List<String> tooltips) {
        tooltips.addAll(this.getInformation(stack, false));
    }
    
    public List<String> getInformation(final ItemStack stack, final boolean detailed) {
        final TooltipBuilder info = new TooltipBuilder(stack);
        info.addDurability(!detailed);
        if (this.hasCategory(Category.HARVEST)) {
            info.addHarvestLevel();
            info.addMiningSpeed();
        }
        if (this.hasCategory(Category.LAUNCHER)) {
            info.addDrawSpeed();
            info.addRange();
            info.addProjectileBonusDamage();
        }
        info.addAttack();
        if (ToolHelper.getFreeModifiers(stack) > 0) {
            info.addFreeModifiers();
        }
        if (detailed) {
            info.addModifierInfo();
        }
        return info.getTooltip();
    }
    
    public void getTooltipComponents(final ItemStack stack, final List<String> tooltips) {
        final List<Material> materials = TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(stack));
        final List<PartMaterialType> component = this.getRequiredComponents();
        if (materials.size() < component.size()) {
            return;
        }
        for (int i = 0; i < component.size(); ++i) {
            final PartMaterialType pmt = component.get(i);
            final Material material = materials.get(i);
            final Iterator<IToolPart> partIter = pmt.getPossibleParts().iterator();
            if (partIter.hasNext()) {
                final IToolPart part = partIter.next();
                final ItemStack partStack = part.getItemstackWithMaterial(material);
                if (partStack != null) {
                    tooltips.add(material.getTextColor() + TextFormatting.UNDERLINE + partStack.func_82833_r());
                    final Set<ITrait> usedTraits = (Set<ITrait>)Sets.newHashSet();
                    for (final IMaterialStats stats : material.getAllStats()) {
                        if (pmt.usesStat(stats.getIdentifier())) {
                            tooltips.addAll(stats.getLocalizedInfo());
                            for (final ITrait trait : pmt.getApplicableTraitsForMaterial(material)) {
                                if (!usedTraits.contains(trait)) {
                                    tooltips.add(material.getTextColor() + trait.getLocalizedName());
                                    usedTraits.add(trait);
                                }
                            }
                        }
                    }
                    tooltips.add("");
                }
            }
        }
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer(final ItemStack stack) {
        return ClientProxy.fontRenderer;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean func_77636_d(final ItemStack stack) {
        return TagUtil.hasEnchantEffect(stack);
    }
    
    @Nonnull
    public String func_77653_i(@Nonnull final ItemStack stack) {
        final List<Material> materials = TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(stack));
        final Set<Material> nameMaterials = (Set<Material>)Sets.newLinkedHashSet();
        for (final int index : this.getRepairParts()) {
            if (index < materials.size()) {
                nameMaterials.add(materials.get(index));
            }
        }
        return Material.getCombinedItemName(super.func_77653_i(stack), nameMaterials);
    }
    
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            this.addDefaultSubItems((List<ItemStack>)subItems, new Material[0]);
        }
    }
    
    protected void addDefaultSubItems(final List<ItemStack> subItems, final Material... fixedMaterials) {
        for (final Material head : TinkerRegistry.getAllMaterials()) {
            final List<Material> mats = new ArrayList<Material>(this.requiredComponents.length);
            for (int i = 0; i < this.requiredComponents.length; ++i) {
                if (fixedMaterials.length > i && fixedMaterials[i] != null && this.requiredComponents[i].isValidMaterial(fixedMaterials[i])) {
                    mats.add(fixedMaterials[i]);
                }
                else {
                    mats.add(head);
                }
            }
            final ItemStack tool = this.buildItem(mats);
            if (this.hasValidMaterials(tool)) {
                subItems.add(tool);
                if (!Config.listAllMaterials) {
                    break;
                }
                continue;
            }
        }
    }
    
    protected void addInfiTool(final List<ItemStack> subItems, final String name) {
        final ItemStack tool = this.getInfiTool(name);
        if (this.hasValidMaterials(tool)) {
            subItems.add(tool);
        }
    }
    
    protected ItemStack getInfiTool(final String name) {
        List<Material> materials = (List<Material>)ImmutableList.of((Object)TinkerMaterials.slime, (Object)TinkerMaterials.cobalt, (Object)TinkerMaterials.ardite, (Object)TinkerMaterials.ardite);
        materials = materials.subList(0, this.requiredComponents.length);
        final ItemStack tool = this.buildItem(materials);
        tool.func_151001_c(name);
        InfiTool.INSTANCE.apply(tool);
        return tool;
    }
    
    public int getHarvestLevel(final ItemStack stack, final String toolClass, @Nullable final EntityPlayer player, @Nullable final IBlockState blockState) {
        if (ToolHelper.isBroken(stack)) {
            return -1;
        }
        if (this.getToolClasses(stack).contains(toolClass)) {
            return ToolHelper.getHarvestLevelStat(stack);
        }
        return super.getHarvestLevel(stack, toolClass, player, blockState);
    }
    
    public Set<String> getToolClasses(final ItemStack stack) {
        if (ToolHelper.isBroken(stack)) {
            return Collections.emptySet();
        }
        return (Set<String>)super.getToolClasses(stack);
    }
    
    public String getIdentifier() {
        return this.getRegistryName().func_110623_a();
    }
    
    @Override
    public String getLocalizedToolName() {
        return Util.translate(this.func_77658_a() + ".name", new Object[0]);
    }
    
    public String getLocalizedToolName(final Material material) {
        return material.getLocalizedItemName(this.getLocalizedToolName());
    }
    
    public String getLocalizedDescription() {
        return Util.translate(this.func_77658_a() + ".desc", new Object[0]);
    }
    
    @Override
    protected int repairCustom(final Material material, final NonNullList<ItemStack> repairItems) {
        final Optional<RecipeMatch.Match> matchOptional = (Optional<RecipeMatch.Match>)RecipeMatch.of((Item)TinkerTools.sharpeningKit).matches((NonNullList)repairItems);
        if (!matchOptional.isPresent()) {
            return 0;
        }
        final RecipeMatch.Match match = matchOptional.get();
        for (final ItemStack stacks : match.stacks) {
            if (TinkerTools.sharpeningKit.getMaterial(stacks) != material) {
                return 0;
            }
        }
        RecipeMatch.removeMatch((NonNullList)repairItems, match);
        final HeadMaterialStats stats = material.getStats("head");
        float durability = (float)(stats.durability * match.amount * TinkerTools.sharpeningKit.getCost());
        durability /= 144.0f;
        return (int)durability;
    }
    
    public void func_77663_a(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected) {
        super.func_77663_a(stack, worldIn, entityIn, itemSlot, isSelected);
        this.onUpdateTraits(stack, worldIn, entityIn, itemSlot, isSelected);
    }
    
    protected void onUpdateTraits(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected) {
        final boolean isSelectedOrOffhand = isSelected || (entityIn instanceof EntityPlayer && ((EntityPlayer)entityIn).func_184592_cb() == stack);
        TinkerUtil.getTraitsOrdered(stack).forEach(trait -> trait.onUpdate(stack, worldIn, entityIn, itemSlot, isSelectedOrOffhand));
    }
    
    public boolean func_179218_a(final ItemStack stack, final World worldIn, final IBlockState state, final BlockPos pos, final EntityLivingBase entityLiving) {
        if (stack != null && entityLiving != null && stack.func_77942_o()) {
            final NBTTagCompound tag = stack.func_77978_p();
            assert tag != null;
            if (tag.func_74763_f("SwitchedHand") == entityLiving.func_130014_f_().func_82737_E()) {
                tag.func_82580_o("SwitchedHand");
                stack.func_77982_d(tag);
                this.switchItemsInHands(entityLiving);
            }
        }
        if (ToolHelper.isBroken(stack)) {
            return false;
        }
        final boolean effective = this.isEffective(state) || ToolHelper.isToolEffective(stack, worldIn.func_180495_p(pos));
        final int damage = effective ? 1 : 2;
        this.afterBlockBreak(stack, worldIn, state, pos, entityLiving, damage, effective);
        return this.hasCategory(Category.TOOL);
    }
    
    protected void switchItemsInHands(final EntityLivingBase entityLiving) {
        final ItemStack main = entityLiving.func_184614_ca();
        final ItemStack off = entityLiving.func_184592_cb();
        entityLiving.func_184611_a(EnumHand.OFF_HAND, main);
        entityLiving.func_184611_a(EnumHand.MAIN_HAND, off);
    }
    
    public void afterBlockBreak(final ItemStack stack, final World world, final IBlockState state, final BlockPos pos, final EntityLivingBase player, final int damage, final boolean wasEffective) {
        TinkerUtil.getTraitsOrdered(stack).forEach(trait -> trait.afterBlockBreak(stack, world, state, pos, player, wasEffective));
        ToolHelper.damageTool(stack, damage, player);
    }
    
    public RayTraceResult func_77621_a(@Nonnull final World worldIn, @Nonnull final EntityPlayer playerIn, final boolean useLiquids) {
        return super.func_77621_a(worldIn, playerIn, useLiquids);
    }
    
    protected void preventSlowDown(final Entity entityIn, final float originalSpeed) {
        TinkerTools.proxy.preventPlayerSlowdown(entityIn, originalSpeed, this);
    }
    
    public boolean shouldCauseBlockBreakReset(final ItemStack oldStack, final ItemStack newStack) {
        return this.shouldCauseReequipAnimation(oldStack, newStack, false);
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldCauseReequipAnimation(final ItemStack oldStack, @Nonnull final ItemStack newStack, final boolean slotChanged) {
        if (TagUtil.getResetFlag(newStack)) {
            TagUtil.setResetFlag(newStack, false);
            return true;
        }
        if (oldStack == newStack) {
            return false;
        }
        if (slotChanged) {
            return true;
        }
        if (oldStack.func_77962_s() != newStack.func_77962_s()) {
            return true;
        }
        final Multimap<String, AttributeModifier> attributesNew = (Multimap<String, AttributeModifier>)newStack.func_111283_C(EntityEquipmentSlot.MAINHAND);
        final Multimap<String, AttributeModifier> attributesOld = (Multimap<String, AttributeModifier>)oldStack.func_111283_C(EntityEquipmentSlot.MAINHAND);
        if (attributesNew.size() != attributesOld.size()) {
            return true;
        }
        for (final String key : attributesOld.keySet()) {
            if (!attributesNew.containsKey((Object)key)) {
                return true;
            }
            final Iterator<AttributeModifier> iter1 = attributesNew.get((Object)key).iterator();
            final Iterator<AttributeModifier> iter2 = attributesOld.get((Object)key).iterator();
            while (iter1.hasNext() && iter2.hasNext()) {
                if (!iter1.next().equals((Object)iter2.next())) {
                    return true;
                }
            }
        }
        if (oldStack.func_77973_b() == newStack.func_77973_b() && newStack.func_77973_b() instanceof ToolCore) {
            return !isEqualTinkersItem(oldStack, newStack);
        }
        return !ItemStack.func_77989_b(oldStack, newStack);
    }
    
    protected ToolNBT buildDefaultTag(final List<Material> materials) {
        final ToolNBT data = new ToolNBT();
        if (materials.size() >= 2) {
            final HandleMaterialStats handle = materials.get(0).getStatsOrUnknown("handle");
            final HeadMaterialStats head = materials.get(1).getStatsOrUnknown("head");
            data.head(head);
            if (materials.size() >= 3) {
                final ExtraMaterialStats binding = materials.get(2).getStatsOrUnknown("extra");
                data.extra(binding);
            }
            data.handle(handle);
        }
        data.modifiers = 3;
        return data;
    }
    
    public static boolean isEqualTinkersItem(final ItemStack item1, final ItemStack item2) {
        if (item1 == null || item2 == null || item1.func_77973_b() != item2.func_77973_b()) {
            return false;
        }
        if (!(item1.func_77973_b() instanceof ToolCore)) {
            return false;
        }
        final NBTTagCompound tag1 = TagUtil.getTagSafe(item1);
        final NBTTagCompound tag2 = TagUtil.getTagSafe(item2);
        final NBTTagList mods1 = TagUtil.getModifiersTagList(tag1);
        final NBTTagList mods2 = TagUtil.getModifiersTagList(tag2);
        if (mods1.func_74745_c() != mods1.func_74745_c()) {
            return false;
        }
        for (int i = 0; i < mods1.func_74745_c(); ++i) {
            final NBTTagCompound tag3 = mods1.func_150305_b(i);
            final ModifierNBT data = ModifierNBT.readTag(tag3);
            final IModifier modifier = TinkerRegistry.getModifier(data.identifier);
            if (modifier != null && !modifier.equalModifier(tag3, mods2.func_150305_b(i))) {
                return false;
            }
        }
        return TagUtil.getBaseMaterialsTagList(tag1).equals((Object)TagUtil.getBaseMaterialsTagList(tag2)) && TagUtil.getBaseModifiersUsed(tag1) == TagUtil.getBaseModifiersUsed(tag2) && TagUtil.getOriginalToolStats(tag1).equals(TagUtil.getOriginalToolStats(tag2));
    }
}
