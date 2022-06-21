package slimeknights.tconstruct.library.capability.projectile;

import net.minecraftforge.common.util.*;
import net.minecraft.item.*;
import java.util.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.*;
import slimeknights.tconstruct.library.traits.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.tools.ranged.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;

public class TinkerProjectileHandler implements ITinkerProjectile, INBTSerializable<NBTTagCompound>
{
    public static final String TAG_PARENT = "parent";
    public static final String TAG_LAUNCHER = "launcher";
    public static final String TAG_POWER = "power";
    private ItemStack parent;
    private ItemStack launcher;
    private List<IProjectileTrait> projectileTraitList;
    private float power;
    
    public TinkerProjectileHandler() {
        this.parent = ItemStack.field_190927_a;
        this.launcher = ItemStack.field_190927_a;
        this.projectileTraitList = (List<IProjectileTrait>)Lists.newArrayList();
        this.power = 1.0f;
    }
    
    @Override
    public ItemStack getItemStack() {
        return this.parent;
    }
    
    @Override
    public void setItemStack(final ItemStack stack) {
        this.parent = stack;
        this.updateTraits();
    }
    
    @Override
    public ItemStack getLaunchingStack() {
        return this.launcher;
    }
    
    @Override
    public void setLaunchingStack(final ItemStack launchingStack) {
        this.launcher = launchingStack;
    }
    
    @Override
    public List<IProjectileTrait> getProjectileTraits() {
        return this.projectileTraitList;
    }
    
    private void updateTraits() {
        if (this.parent != null) {
            this.projectileTraitList.clear();
            final NBTTagList list = TagUtil.getTraitsTagList(this.parent);
            for (int i = 0; i < list.func_74745_c(); ++i) {
                final ITrait trait = TinkerRegistry.getTrait(list.func_150307_f(i));
                if (trait instanceof IProjectileTrait) {
                    this.projectileTraitList.add((IProjectileTrait)trait);
                }
            }
        }
    }
    
    @Override
    public boolean pickup(final EntityLivingBase entity, final boolean simulate) {
        final ItemStack stack = AmmoHelper.getMatchingItemstackFromInventory(this.parent, (Entity)entity, true);
        if (stack.func_77973_b() instanceof IAmmo) {
            if (!simulate && ((IAmmo)this.parent.func_77973_b()).getCurrentAmmo(this.parent) > 0) {
                ToolHelper.unbreakTool(stack);
                ((IAmmo)stack.func_77973_b()).addAmmo(stack, entity);
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void setPower(final float power) {
        this.power = power;
    }
    
    @Override
    public float getPower() {
        return this.power;
    }
    
    public NBTTagCompound serializeNBT() {
        final NBTTagCompound tag = new NBTTagCompound();
        if (this.parent != null) {
            tag.func_74782_a("parent", (NBTBase)this.parent.func_77955_b(new NBTTagCompound()));
        }
        if (this.launcher != null) {
            tag.func_74782_a("launcher", (NBTBase)this.launcher.func_77955_b(new NBTTagCompound()));
        }
        tag.func_74776_a("power", this.power);
        return tag;
    }
    
    public void deserializeNBT(final NBTTagCompound nbt) {
        this.parent = new ItemStack(nbt.func_74775_l("parent"));
        if (this.parent.func_190926_b()) {
            this.parent = new ItemStack(nbt);
        }
        this.launcher = new ItemStack(nbt.func_74775_l("launcher"));
        this.power = nbt.func_74760_g("power");
        this.updateTraits();
    }
}
