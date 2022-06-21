package slimeknights.tconstruct.shared.block;

import slimeknights.mantle.block.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.*;
import slimeknights.tconstruct.shared.*;
import net.minecraft.util.math.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.entity.*;
import javax.annotation.*;
import net.minecraft.world.*;
import net.minecraftforge.common.*;
import slimeknights.tconstruct.world.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockSoil extends EnumBlock<SoilTypes>
{
    public static final PropertyEnum<SoilTypes> TYPE;
    
    public BlockSoil() {
        super(Material.field_151595_p, (PropertyEnum)BlockSoil.TYPE, (Class)SoilTypes.class);
        this.field_149765_K = 0.8f;
        this.func_149711_c(3.0f);
        this.func_149672_a(SoundType.field_185855_h);
        this.setHarvestLevel("shovel", -1);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGeneral);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        for (final SoilTypes type : SoilTypes.values()) {
            if (this.isTypeEnabled(type)) {
                list.add((Object)new ItemStack((Block)this, 1, type.getMeta()));
            }
        }
    }
    
    protected boolean isTypeEnabled(final SoilTypes type) {
        switch (type) {
            case GROUT: {
                return TConstruct.pulseManager.isPulseLoaded("TinkerSmeltery");
            }
            case SLIMY_MUD_BLUE: {
                return TinkerCommons.matSlimeBallBlue != null;
            }
            case SLIMY_MUD_MAGMA: {
                return TinkerCommons.matSlimeBallMagma != null;
            }
            case SLIMY_MUD_GREEN:
            case GRAVEYARD:
            case CONSECRATED: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public void func_176199_a(final World worldIn, final BlockPos pos, final Entity entityIn) {
        final IBlockState state = worldIn.func_180495_p(pos);
        switch ((SoilTypes)state.func_177229_b((IProperty)BlockSoil.TYPE)) {
            case SLIMY_MUD_BLUE:
            case SLIMY_MUD_GREEN: {
                this.processSlimyMud(entityIn);
                break;
            }
            case GRAVEYARD: {
                this.processGraveyardSoil(entityIn);
                break;
            }
            case CONSECRATED: {
                this.processConsecratedSoil(entityIn);
                break;
            }
        }
    }
    
    protected void processSlimyMud(final Entity entity) {
        entity.field_70159_w *= 0.4;
        entity.field_70179_y *= 0.4;
        if (entity instanceof EntityLivingBase) {
            ((EntityLivingBase)entity).func_70690_d(new PotionEffect(MobEffects.field_76437_t, 1));
        }
    }
    
    protected void processConsecratedSoil(final Entity entity) {
        if (entity instanceof EntityLiving) {
            final EntityLivingBase entityLiving = (EntityLivingBase)entity;
            if (entityLiving.func_70668_bt() == EnumCreatureAttribute.UNDEAD) {
                entityLiving.func_70097_a(DamageSource.field_76376_m, 1.0f);
                entityLiving.func_70015_d(1);
            }
        }
    }
    
    protected void processGraveyardSoil(final Entity entity) {
        if (entity instanceof EntityLiving) {
            final EntityLivingBase entityLiving = (EntityLivingBase)entity;
            if (entityLiving.func_70668_bt() == EnumCreatureAttribute.UNDEAD) {
                entityLiving.func_70691_i(1.0f);
            }
        }
    }
    
    public boolean canSustainPlant(@Nonnull final IBlockState state, @Nonnull final IBlockAccess world, final BlockPos pos, @Nonnull final EnumFacing direction, final IPlantable plantable) {
        final SoilTypes type = (SoilTypes)world.func_180495_p(pos).func_177229_b((IProperty)BlockSoil.TYPE);
        if (type == SoilTypes.SLIMY_MUD_GREEN || type == SoilTypes.SLIMY_MUD_BLUE) {
            return plantable.getPlantType(world, pos) == TinkerWorld.slimePlantType;
        }
        return super.canSustainPlant(state, world, pos, direction, plantable);
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)SoilTypes.class);
    }
    
    public enum SoilTypes implements IStringSerializable, EnumBlock.IEnumMeta
    {
        GROUT, 
        SLIMY_MUD_GREEN, 
        SLIMY_MUD_BLUE, 
        GRAVEYARD, 
        CONSECRATED, 
        SLIMY_MUD_MAGMA;
        
        public final int meta;
        
        private SoilTypes() {
            this.meta = this.ordinal();
        }
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
        
        public int getMeta() {
            return this.meta;
        }
    }
}
