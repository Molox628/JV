package slimeknights.tconstruct.library.tinkering;

import gnu.trove.set.hash.*;
import net.minecraft.world.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.events.*;
import net.minecraft.nbt.*;
import java.util.function.*;
import java.util.stream.*;
import slimeknights.tconstruct.common.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.*;
import slimeknights.mantle.util.*;
import java.util.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.materials.*;
import javax.annotation.*;
import net.minecraft.client.util.*;
import net.minecraft.util.text.*;
import net.minecraft.client.*;
import net.minecraft.entity.*;
import net.minecraft.util.text.translation.*;
import slimeknights.tconstruct.common.config.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.modifiers.*;

public abstract class TinkersItem extends Item implements ITinkerable, IModifyable, IRepairable
{
    protected final PartMaterialType[] requiredComponents;
    protected final Set<Category> categories;
    
    public TinkersItem(final PartMaterialType... requiredComponents) {
        this.categories = (Set<Category>)new THashSet();
        this.requiredComponents = requiredComponents;
        this.func_77625_d(1);
    }
    
    public List<PartMaterialType> getRequiredComponents() {
        return (List<PartMaterialType>)ImmutableList.copyOf((Object[])this.requiredComponents);
    }
    
    public List<PartMaterialType> getToolBuildComponents() {
        return this.getRequiredComponents();
    }
    
    protected void addCategory(final Category... categories) {
        Collections.addAll(this.categories, categories);
    }
    
    public boolean hasCategory(final Category category) {
        return this.categories.contains(category);
    }
    
    protected Category[] getCategories() {
        final Category[] out = new Category[this.categories.size()];
        int i = 0;
        for (final Category category : this.categories) {
            out[i++] = category;
        }
        return out;
    }
    
    public boolean hasCustomEntity(final ItemStack stack) {
        return true;
    }
    
    @Nonnull
    public Entity createEntity(final World world, final Entity location, final ItemStack itemstack) {
        final EntityItem entity = new IndestructibleEntityItem(world, location.field_70165_t, location.field_70163_u, location.field_70161_v, itemstack);
        if (location instanceof EntityItem) {
            final NBTTagCompound tag = new NBTTagCompound();
            location.func_189511_e(tag);
            entity.func_174867_a((int)tag.func_74765_d("PickupDelay"));
        }
        entity.field_70159_w = location.field_70159_w;
        entity.field_70181_x = location.field_70181_x;
        entity.field_70179_y = location.field_70179_y;
        return (Entity)entity;
    }
    
    public boolean validComponent(final int slot, final ItemStack stack) {
        return slot <= this.requiredComponents.length && slot >= 0 && this.requiredComponents[slot].isValid(stack);
    }
    
    @Nonnull
    public ItemStack buildItemFromStacks(final NonNullList<ItemStack> stacks) {
        final long itemCount = stacks.stream().filter(stack -> !stack.func_190926_b()).count();
        final List<Material> materials = new ArrayList<Material>(stacks.size());
        if (itemCount != this.requiredComponents.length) {
            return ItemStack.field_190927_a;
        }
        for (int i = 0; i < itemCount; ++i) {
            if (!this.validComponent(i, (ItemStack)stacks.get(i))) {
                return ItemStack.field_190927_a;
            }
            materials.add(TinkerUtil.getMaterialFromStack((ItemStack)stacks.get(i)));
        }
        return this.buildItem(materials);
    }
    
    @Nonnull
    public ItemStack buildItem(final List<Material> materials) {
        final ItemStack tool = new ItemStack((Item)this);
        tool.func_77982_d(this.buildItemNBT(materials));
        return tool;
    }
    
    public NBTTagCompound buildItemNBT(final List<Material> materials) {
        final NBTTagCompound basetag = new NBTTagCompound();
        final NBTTagCompound toolTag = this.buildTag(materials);
        final NBTTagCompound dataTag = this.buildData(materials);
        basetag.func_74782_a("TinkerData", (NBTBase)dataTag);
        basetag.func_74782_a("Stats", (NBTBase)toolTag);
        basetag.func_74782_a("StatsOriginal", (NBTBase)toolTag.func_74737_b());
        TagUtil.setCategories(basetag, this.getCategories());
        this.addMaterialTraits(basetag, materials);
        TinkerEvent.OnItemBuilding.fireEvent(basetag, (ImmutableList<Material>)ImmutableList.copyOf((Collection)materials), this);
        return basetag;
    }
    
    private NBTTagCompound buildData(final List<Material> materials) {
        final NBTTagCompound base = new NBTTagCompound();
        final NBTTagList materialList = new NBTTagList();
        for (final Material material : materials) {
            materialList.func_74742_a((NBTBase)new NBTTagString(material.identifier));
        }
        final NBTTagList modifierList = new NBTTagList();
        modifierList.func_74742_a((NBTBase)new NBTTagString());
        modifierList.func_74744_a(0);
        base.func_74782_a("Materials", (NBTBase)materialList);
        base.func_74782_a("Modifiers", (NBTBase)modifierList);
        return base;
    }
    
    @Nonnull
    public ItemStack buildItemForRendering(final List<Material> materials) {
        final ItemStack tool = new ItemStack((Item)this);
        final NBTTagCompound base = new NBTTagCompound();
        base.func_74782_a("TinkerData", (NBTBase)this.buildData(materials));
        tool.func_77982_d(base);
        return tool;
    }
    
    @Nonnull
    public ItemStack buildItemForRenderingInGui() {
        final List<Material> materials = IntStream.range(0, this.getRequiredComponents().size()).mapToObj((IntFunction<?>)this::getMaterialForPartForGuiRendering).collect((Collector<? super Object, ?, List<Material>>)Collectors.toList());
        return this.buildItemForRendering(materials);
    }
    
    @SideOnly(Side.CLIENT)
    public Material getMaterialForPartForGuiRendering(final int index) {
        return ClientProxy.RenderMaterials[index % ClientProxy.RenderMaterials.length];
    }
    
    public abstract NBTTagCompound buildTag(final List<Material> p0);
    
    public boolean hasValidMaterials(final ItemStack stack) {
        final NBTTagList list = TagUtil.getBaseMaterialsTagList(stack);
        final List<Material> materials = TinkerUtil.getMaterialsFromTagList(list);
        if (materials.size() != this.requiredComponents.length) {
            return false;
        }
        for (int i = 0; i < materials.size(); ++i) {
            final Material material = materials.get(i);
            final PartMaterialType required = this.requiredComponents[i];
            if (!required.isValidMaterial(material)) {
                return false;
            }
        }
        return true;
    }
    
    public void addMaterialTraits(final NBTTagCompound root, final List<Material> materials) {
        int size = this.requiredComponents.length;
        if (materials.size() < size) {
            size = materials.size();
        }
        for (int i = 0; i < size; ++i) {
            final PartMaterialType required = this.requiredComponents[i];
            final Material material = materials.get(i);
            for (final ITrait trait : required.getApplicableTraitsForMaterial(material)) {
                ToolBuilder.addTrait(root, trait, material.materialTextColor);
            }
        }
    }
    
    public int[] getRepairParts() {
        return new int[] { 1 };
    }
    
    public float getRepairModifierForPart(final int index) {
        return 1.0f;
    }
    
    @Nonnull
    public ItemStack repair(final ItemStack repairable, final NonNullList<ItemStack> repairItems) {
        if (repairable.func_77952_i() == 0 && !ToolHelper.isBroken(repairable)) {
            return ItemStack.field_190927_a;
        }
        final List<Material> materials = TinkerUtil.getMaterialsFromTagList(TagUtil.getBaseMaterialsTagList(repairable));
        if (materials.isEmpty()) {
            return ItemStack.field_190927_a;
        }
        final NonNullList<ItemStack> items = Util.deepCopyFixedNonNullList(repairItems);
        boolean foundMatch = false;
        for (final int index : this.getRepairParts()) {
            final Material material = materials.get(index);
            if (this.repairCustom(material, items) > 0) {
                foundMatch = true;
            }
            Optional<RecipeMatch.Match> match = (Optional<RecipeMatch.Match>)material.matches((NonNullList)items);
            if (match.isPresent()) {
                foundMatch = true;
                while ((match = (Optional<RecipeMatch.Match>)material.matches((NonNullList)items)).isPresent()) {
                    RecipeMatch.removeMatch((NonNullList)items, (RecipeMatch.Match)match.get());
                }
            }
        }
        if (!foundMatch) {
            return ItemStack.field_190927_a;
        }
        for (int i = 0; i < repairItems.size(); ++i) {
            if (!((ItemStack)repairItems.get(i)).func_190926_b() && ItemStack.func_77989_b((ItemStack)repairItems.get(i), (ItemStack)items.get(i))) {
                return ItemStack.field_190927_a;
            }
        }
        final ItemStack item = repairable.func_77946_l();
        do {
            final int amount = this.calculateRepairAmount(materials, repairItems);
            if (amount <= 0) {
                break;
            }
            ToolHelper.repairTool(item, this.calculateRepair(item, amount));
            final NBTTagCompound tag = TagUtil.getExtraTag(item);
            tag.func_74768_a("RepairCount", tag.func_74762_e("RepairCount") + 1);
            TagUtil.setExtraTag(item, tag);
        } while (item.func_77952_i() > 0);
        return item;
    }
    
    protected int repairCustom(final Material material, final NonNullList<ItemStack> repairItems) {
        return 0;
    }
    
    protected int calculateRepairAmount(final List<Material> materials, final NonNullList<ItemStack> repairItems) {
        final Set<Material> materialsMatched = (Set<Material>)Sets.newHashSet();
        float durability = 0.0f;
        for (final int index : this.getRepairParts()) {
            final Material material = materials.get(index);
            if (!materialsMatched.contains(material)) {
                durability += this.repairCustom(material, repairItems) * this.getRepairModifierForPart(index);
                final Optional<RecipeMatch.Match> matchOptional = (Optional<RecipeMatch.Match>)material.matches((NonNullList)repairItems);
                if (matchOptional.isPresent()) {
                    final RecipeMatch.Match match = matchOptional.get();
                    final HeadMaterialStats stats = material.getStats("head");
                    if (stats != null) {
                        materialsMatched.add(material);
                        durability += stats.durability * (float)match.amount * this.getRepairModifierForPart(index) / 144.0f;
                        RecipeMatch.removeMatch((NonNullList)repairItems, match);
                    }
                }
            }
        }
        durability *= 1.0f + (materialsMatched.size() - 1.0f) / 9.0f;
        return (int)durability;
    }
    
    protected int calculateRepair(final ItemStack tool, final int amount) {
        final float origDur = (float)TagUtil.getOriginalToolStats(tool).durability;
        final float actualDur = (float)ToolHelper.getDurabilityStat(tool);
        final float durabilityFactor = actualDur / origDur;
        float increase = amount * Math.min(10.0f, durabilityFactor);
        increase = Math.max(increase, actualDur / 64.0f);
        final int modifiersUsed = TagUtil.getBaseModifiersUsed(tool.func_77978_p());
        float mods = 1.0f;
        if (modifiersUsed == 1) {
            mods = 0.95f;
        }
        else if (modifiersUsed == 2) {
            mods = 0.9f;
        }
        else if (modifiersUsed >= 3) {
            mods = 0.85f;
        }
        increase *= mods;
        final NBTTagCompound tag = TagUtil.getExtraTag(tool);
        final int repair = tag.func_74762_e("RepairCount");
        float repairDimishingReturns = (100 - repair / 2) / 100.0f;
        if (repairDimishingReturns < 0.5f) {
            repairDimishingReturns = 0.5f;
        }
        increase *= repairDimishingReturns;
        return (int)Math.ceil(increase);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_77624_a(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn) {
        final boolean shift = Util.isShiftKeyDown();
        final boolean ctrl = Util.isCtrlKeyDown();
        if (!shift && !ctrl) {
            this.getTooltip(stack, tooltip);
            tooltip.add("");
            tooltip.add(Util.translate("tooltip.tool.holdShift", new Object[0]));
            tooltip.add(Util.translate("tooltip.tool.holdCtrl", new Object[0]));
            if (worldIn != null) {
                tooltip.add(TextFormatting.BLUE + I18n.func_74837_a("attribute.modifier.plus.0", new Object[] { Util.df.format(ToolHelper.getActualDamage(stack, (EntityLivingBase)Minecraft.func_71410_x().field_71439_g)), I18n.func_74838_a("attribute.name.generic.attackDamage") }));
            }
        }
        else if (Config.extraTooltips && shift) {
            this.getTooltipDetailed(stack, tooltip);
        }
        else if (Config.extraTooltips && ctrl) {
            this.getTooltipComponents(stack, tooltip);
        }
    }
    
    public void getTooltip(final ItemStack stack, final List<String> tooltips) {
        TooltipBuilder.addModifierTooltips(stack, tooltips);
    }
    
    @Nonnull
    public EnumRarity func_77613_e(final ItemStack stack) {
        return EnumRarity.COMMON;
    }
    
    public boolean isBookEnchantable(final ItemStack stack, final ItemStack book) {
        return false;
    }
    
    public boolean func_179215_a(final NBTTagCompound nbt) {
        if (nbt.func_74764_b("TinkerData")) {
            try {
                ToolBuilder.rebuildTool(nbt, this);
            }
            catch (TinkerGuiException ex) {}
        }
        return true;
    }
}
