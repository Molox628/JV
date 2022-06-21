package slimeknights.tconstruct.tools.modifiers;

import net.minecraftforge.common.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraftforge.event.entity.living.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.capability.projectile.*;
import java.util.function.*;
import net.minecraft.util.*;
import net.minecraft.entity.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.entity.monster.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.library.*;

public class ModBeheading extends ToolModifier
{
    private static String BEHEADING_ID;
    private static String CLEAVER_MODIFIER_ID;
    private static int BEHEADING_COLOR;
    public static ModBeheading CLEAVER_BEHEADING_MOD;
    
    public ModBeheading() {
        this("beheading");
        this.addAspects(ModifierAspect.freeModifier);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    ModBeheading(final String traitBeheading) {
        super(traitBeheading, ModBeheading.BEHEADING_COLOR);
        this.addAspects(new ModifierAspect.LevelAspect(this, 10), new ModifierAspect.DataAspect((T)this));
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        final NBTTagCompound tag = TinkerUtil.getModifierTag(rootCompound, ModBeheading.CLEAVER_MODIFIER_ID);
        if (!tag.func_82582_d()) {
            if (!modifierTag.func_74767_n("absorbedCleaver")) {
                final ModifierNBT tag2;
                final ModifierNBT data = tag2 = ModifierNBT.readTag(modifierTag);
                tag2.level += ModifierNBT.readTag(tag).level;
                data.write(modifierTag);
                modifierTag.func_74757_a("absorbedCleaver", true);
            }
            final NBTTagList tagList = TagUtil.getModifiersTagList(rootCompound);
            final int index = TinkerUtil.getIndexInCompoundList(tagList, ModBeheading.CLEAVER_MODIFIER_ID);
            tagList.func_74744_a(index);
            TagUtil.setModifiersTagList(rootCompound, tagList);
        }
    }
    
    @SubscribeEvent
    public void onLivingDrops(final LivingDropsEvent event) {
        if (event.getSource().func_76346_g() instanceof EntityPlayer) {
            final ItemStack item = CapabilityTinkerProjectile.getTinkerProjectile(event.getSource()).map((Function<? super ITinkerProjectile, ? extends ItemStack>)ITinkerProjectile::getItemStack).orElse(((EntityPlayer)event.getSource().func_76346_g()).func_184586_b(EnumHand.MAIN_HAND));
            NBTTagCompound tag = TinkerUtil.getModifierTag(item, this.getIdentifier());
            int level = ModifierNBT.readTag(tag).level;
            if (level == 0) {
                tag = TinkerUtil.getModifierTag(item, ModBeheading.CLEAVER_MODIFIER_ID);
                level = ModifierNBT.readTag(tag).level;
            }
            if (level > 0) {
                final ItemStack head = this.getHeadDrop(event.getEntityLiving());
                if (head != null && !head.func_190926_b() && level > ModBeheading.random.nextInt(10) && !this.alreadyContainsDrop(event, head)) {
                    final EntityItem entityitem = new EntityItem(event.getEntityLiving().func_130014_f_(), event.getEntityLiving().field_70165_t, event.getEntityLiving().field_70163_u, event.getEntityLiving().field_70161_v, head);
                    entityitem.func_174869_p();
                    event.getDrops().add(entityitem);
                }
            }
        }
    }
    
    private boolean alreadyContainsDrop(final LivingDropsEvent event, final ItemStack head) {
        return event.getDrops().stream().map(EntityItem::func_92059_d).anyMatch(drop -> ItemStack.func_77989_b(drop, head));
    }
    
    private ItemStack getHeadDrop(final EntityLivingBase entity) {
        if (entity instanceof EntitySkeleton) {
            return new ItemStack(Items.field_151144_bL, 1, 0);
        }
        if (entity instanceof EntityWitherSkeleton) {
            return new ItemStack(Items.field_151144_bL, 1, 1);
        }
        if (entity instanceof EntityZombie) {
            return new ItemStack(Items.field_151144_bL, 1, 2);
        }
        if (entity instanceof EntityCreeper) {
            return new ItemStack(Items.field_151144_bL, 1, 4);
        }
        if (entity instanceof EntityPlayer) {
            final ItemStack head = new ItemStack(Items.field_151144_bL, 1, 3);
            NBTUtil.func_180708_a(head.func_190925_c("SkullOwner"), ((EntityPlayer)entity).func_146103_bH());
            return head;
        }
        return null;
    }
    
    static {
        ModBeheading.BEHEADING_ID = "beheading";
        ModBeheading.CLEAVER_MODIFIER_ID = ModBeheading.BEHEADING_ID + "_cleaver";
        ModBeheading.BEHEADING_COLOR = 1070923;
        ModBeheading.CLEAVER_BEHEADING_MOD = new ModBeheadingCleaver();
    }
    
    private static class ModBeheadingCleaver extends ModBeheading
    {
        public ModBeheadingCleaver() {
            super(ModBeheading.CLEAVER_MODIFIER_ID);
        }
        
        @Override
        public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
        }
        
        @Override
        public String getLocalizedDesc() {
            return Util.translate("modifier.%s.desc", ModBeheading.BEHEADING_ID);
        }
        
        @Override
        public String getLocalizedName() {
            return Util.translate("modifier.%s.name", ModBeheading.BEHEADING_ID);
        }
    }
}
