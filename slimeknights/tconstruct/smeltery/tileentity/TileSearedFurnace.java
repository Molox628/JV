package slimeknights.tconstruct.smeltery.tileentity;

import slimeknights.tconstruct.smeltery.multiblock.*;
import net.minecraft.util.*;
import slimeknights.mantle.common.*;
import org.apache.logging.log4j.*;
import net.minecraft.item.crafting.*;
import javax.annotation.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.inventory.*;
import slimeknights.tconstruct.smeltery.inventory.*;
import net.minecraft.client.gui.inventory.*;
import slimeknights.tconstruct.smeltery.client.*;
import net.minecraftforge.fml.relauncher.*;
import slimeknights.tconstruct.library.*;

public class TileSearedFurnace extends TileHeatingStructureFuelTank<MultiblockSearedFurnace> implements ITickable, IInventoryGui
{
    public static final Logger log;
    protected int tick;
    
    public TileSearedFurnace() {
        super("gui.searedfurnace.name", 0, 16);
        this.setMultiblock(new MultiblockSearedFurnace(this));
    }
    
    public void func_73660_a() {
        if (this.isClientWorld()) {
            return;
        }
        if (!this.isActive()) {
            if (this.tick == 0) {
                this.checkMultiblockStructure();
            }
        }
        else {
            if (this.tick % 4 == 0) {
                this.heatItems();
            }
            if (this.tick == 0) {
                this.interactWithEntitiesInside();
            }
            if (this.needsFuel) {
                this.consumeFuel();
            }
        }
        this.tick = (this.tick + 1) % 20;
    }
    
    public void updateHeatRequired(final int index) {
        final ItemStack stack = this.func_70301_a(index);
        if (!stack.func_190926_b()) {
            final ItemStack result = FurnaceRecipes.func_77602_a().func_151395_a(stack);
            if (!result.func_190926_b()) {
                final int newSize = stack.func_190916_E() * result.func_190916_E();
                if (newSize <= stack.func_77976_d() && newSize <= this.func_70297_j_()) {
                    this.setHeatRequiredForSlot(index, this.getHeatForStack(stack, result));
                }
                else {
                    this.setHeatRequiredForSlot(index, 0);
                    this.itemTemperatures[index] = -1;
                }
                if (!this.hasFuel()) {
                    this.consumeFuel();
                }
                return;
            }
        }
        this.setHeatRequiredForSlot(index, 0);
    }
    
    private int getHeatForStack(@Nonnull final ItemStack input, @Nonnull final ItemStack result) {
        final int base = 200;
        float temp = base * input.func_190916_E() / 4.0f;
        if (result.func_77973_b() instanceof ItemFood) {
            temp *= (float)0.8;
        }
        return (int)temp;
    }
    
    protected boolean onItemFinishedHeating(final ItemStack stack, final int slot) {
        ItemStack result = FurnaceRecipes.func_77602_a().func_151395_a(stack);
        if (result.func_190926_b()) {
            return false;
        }
        result = result.func_77946_l();
        result.func_190920_e(result.func_190916_E() * stack.func_190916_E());
        this.func_70299_a(slot, result);
        this.itemTemperatures[slot] = 1;
        this.itemTempRequired[slot] = 0;
        return false;
    }
    
    @Override
    protected int getUpdatedInventorySize(final int width, final int height, final int depth) {
        return 9 + 3 * width * height * depth;
    }
    
    protected void interactWithEntitiesInside() {
        final AxisAlignedBB bb = this.info.getBoundingBox().func_191195_a(1.0, 1.0, 1.0).func_72317_d(0.0, 0.5, 0.0).func_72321_a(0.0, 0.5, 0.0);
        final List<EntityLivingBase> entities = (List<EntityLivingBase>)this.func_145831_w().func_72872_a((Class)EntityLivingBase.class, bb);
        for (final EntityLivingBase entity : entities) {
            if (entity instanceof EntityMob && entity.func_70089_S()) {
                entity.func_70106_y();
            }
        }
    }
    
    public Container createContainer(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (Container)new ContainerSearedFurnace(inventoryplayer, this);
    }
    
    @SideOnly(Side.CLIENT)
    public GuiContainer createGui(final InventoryPlayer inventoryplayer, final World world, final BlockPos pos) {
        return (GuiContainer)new GuiSearedFurnace((ContainerSearedFurnace)this.createContainer(inventoryplayer, world, pos), this);
    }
    
    static {
        log = Util.getLogger("Furnace");
    }
}
