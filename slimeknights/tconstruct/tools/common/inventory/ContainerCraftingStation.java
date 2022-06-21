package slimeknights.tconstruct.tools.common.inventory;

import slimeknights.tconstruct.tools.common.tileentity.*;
import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.shared.inventory.*;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.tileentity.*;
import org.apache.commons.lang3.tuple.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraftforge.items.*;
import slimeknights.tconstruct.common.config.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.item.crafting.*;
import net.minecraft.entity.player.*;
import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.tools.common.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import net.minecraft.world.*;
import java.util.stream.*;
import net.minecraft.util.*;
import org.apache.logging.log4j.*;

@Mod.EventBusSubscriber
public class ContainerCraftingStation extends ContainerTinkerStation<TileCraftingStation>
{
    public static final Logger log;
    private static final int SLOT_RESULT = 0;
    private final EntityPlayer player;
    private final InventoryCraftingPersistent craftMatrix;
    private final InventoryCraftResult craftResult;
    private IRecipe lastRecipe;
    private IRecipe lastLastRecipe;
    
    @SubscribeEvent
    public static void onCraftingStationGuiOpened(final PlayerContainerEvent.Open event) {
        if (event.getContainer() instanceof ContainerCraftingStation) {
            ((ContainerCraftingStation)event.getContainer()).onCraftMatrixChanged();
        }
    }
    
    public ContainerCraftingStation(final InventoryPlayer playerInventory, final TileCraftingStation tile) {
        super((TileEntity)tile);
        this.craftResult = new InventoryCraftResult();
        this.craftMatrix = new InventoryCraftingPersistent((Container)this, (IInventory)tile, 3, 3);
        this.player = playerInventory.field_70458_d;
        this.func_75146_a((Slot)new SlotCraftingFastWorkbench(this, playerInventory.field_70458_d, this.craftMatrix, (IInventory)this.craftResult, 0, 124, 35));
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.func_75146_a(new Slot((IInventory)this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }
        TileEntity inventoryTE = null;
        EnumFacing accessDir = null;
        for (final EnumFacing dir : EnumFacing.field_176754_o) {
            final BlockPos neighbor = this.pos.func_177972_a(dir);
            boolean stationPart = false;
            for (final Pair<BlockPos, IBlockState> tinkerPos : this.tinkerStationBlocks) {
                if (((BlockPos)tinkerPos.getLeft()).equals((Object)neighbor)) {
                    stationPart = true;
                    break;
                }
            }
            if (!stationPart) {
                final TileEntity te = this.world.func_175625_s(neighbor);
                if (te != null && !(te instanceof TileCraftingStation)) {
                    if (!this.blacklisted(te.getClass())) {
                        if (!(te instanceof IInventory) || ((IInventory)te).func_70300_a(this.player)) {
                            if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null) && te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null) instanceof IItemHandlerModifiable) {
                                inventoryTE = te;
                                accessDir = null;
                                break;
                            }
                            if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.func_176734_d()) && te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.func_176734_d()) instanceof IItemHandlerModifiable) {
                                inventoryTE = te;
                                accessDir = dir.func_176734_d();
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (inventoryTE != null) {
            this.addSubContainer((Container)new ContainerSideInventory(inventoryTE, accessDir, -114, 8, 6), false);
        }
        this.addPlayerInventory(playerInventory, 8, 84);
    }
    
    private boolean blacklisted(final Class<? extends TileEntity> clazz) {
        if (Config.craftingStationBlacklist.isEmpty()) {
            return false;
        }
        final ResourceLocation registryName = TileEntity.func_190559_a((Class)clazz);
        return (registryName != null && Config.craftingStationBlacklist.contains(registryName.toString())) || Config.craftingStationBlacklist.contains(clazz.getName());
    }
    
    public void func_190896_a(final List<ItemStack> p_190896_1_) {
        this.craftMatrix.setDoNotCallUpdates(true);
        super.func_190896_a((List)p_190896_1_);
        this.craftMatrix.setDoNotCallUpdates(false);
        this.craftMatrix.onCraftMatrixChanged();
    }
    
    public void onCraftMatrixChanged() {
        this.func_75130_a((IInventory)this.craftMatrix);
    }
    
    public void func_75130_a(final IInventory inventoryIn) {
        this.func_192389_a(this.world, this.player, this.craftMatrix, this.craftResult);
    }
    
    protected void func_192389_a(final World world, final EntityPlayer player, final InventoryCrafting inv, final InventoryCraftResult result) {
        ItemStack itemstack = ItemStack.field_190927_a;
        if (this.lastRecipe == null || !this.lastRecipe.func_77569_a(inv, world)) {
            this.lastRecipe = CraftingManager.func_192413_b(inv, world);
        }
        if (this.lastRecipe != null) {
            itemstack = this.lastRecipe.func_77572_b(inv);
        }
        result.func_70299_a(0, itemstack);
        if (!world.field_72995_K) {
            final EntityPlayerMP entityplayermp = (EntityPlayerMP)player;
            final List<EntityPlayerMP> relevantPlayers = this.getAllPlayersWithThisContainerOpen((slimeknights.mantle.inventory.BaseContainer<TileEntity>)this, entityplayermp.func_71121_q());
            this.syncResultToAllOpenWindows(itemstack, relevantPlayers);
            if (this.lastLastRecipe != this.lastRecipe) {
                this.syncRecipeToAllOpenWindows(this.lastRecipe, relevantPlayers);
                this.lastLastRecipe = this.lastRecipe;
            }
        }
    }
    
    private void syncResultToAllOpenWindows(final ItemStack stack, final List<EntityPlayerMP> players) {
        players.forEach(otherPlayer -> otherPlayer.field_71070_bA.func_75141_a(0, stack));
    }
    
    private void syncRecipeToAllOpenWindows(final IRecipe lastRecipe, final List<EntityPlayerMP> players) {
        players.forEach(otherPlayer -> {
            ((ContainerCraftingStation)otherPlayer.field_71070_bA).lastRecipe = lastRecipe;
            TinkerNetwork.sendTo(new LastRecipeMessage(lastRecipe), otherPlayer);
        });
    }
    
    private <T extends TileEntity> List<EntityPlayerMP> getAllPlayersWithThisContainerOpen(final BaseContainer<T> container, final WorldServer server) {
        return (List<EntityPlayerMP>)server.field_73010_i.stream().filter(player -> this.hasSameContainerOpen(container, player)).map(player -> player).collect(Collectors.toList());
    }
    
    private <T extends TileEntity> boolean hasSameContainerOpen(final BaseContainer<T> container, final EntityPlayer playerToCheck) {
        return playerToCheck instanceof EntityPlayerMP && playerToCheck.field_71070_bA.getClass().isAssignableFrom(container.getClass()) && this.sameGui((BaseContainer)playerToCheck.field_71070_bA);
    }
    
    public boolean func_94530_a(final ItemStack p_94530_1_, final Slot p_94530_2_) {
        return p_94530_2_.field_75224_c != this.craftResult && super.func_94530_a(p_94530_1_, p_94530_2_);
    }
    
    protected TileEntity detectInventory() {
        for (final EnumFacing dir : EnumFacing.field_176754_o) {
            final BlockPos neighbor = this.pos.func_177972_a(dir);
            boolean stationPart = false;
            for (final Pair<BlockPos, IBlockState> tinkerPos : this.tinkerStationBlocks) {
                if (((BlockPos)tinkerPos.getLeft()).equals((Object)neighbor)) {
                    stationPart = true;
                    break;
                }
            }
            if (!stationPart) {
                final TileEntity te = this.world.func_175625_s(neighbor);
                if (te != null && te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.func_176734_d()) && te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.func_176734_d()) instanceof IItemHandlerModifiable) {
                    return te;
                }
            }
        }
        return null;
    }
    
    public int getPlayerInventoryStart() {
        return this.playerInventoryStart;
    }
    
    public InventoryCrafting getCraftMatrix() {
        return this.craftMatrix;
    }
    
    public void updateLastRecipeFromServer(final IRecipe recipe) {
        this.lastRecipe = recipe;
        this.craftResult.func_70299_a(0, (recipe != null) ? recipe.func_77572_b((InventoryCrafting)this.craftMatrix) : ItemStack.field_190927_a);
    }
    
    public NonNullList<ItemStack> getRemainingItems() {
        if (this.lastRecipe != null && this.lastRecipe.func_77569_a((InventoryCrafting)this.craftMatrix, this.world)) {
            return (NonNullList<ItemStack>)this.lastRecipe.func_179532_b((InventoryCrafting)this.craftMatrix);
        }
        return (NonNullList<ItemStack>)this.craftMatrix.field_70466_a;
    }
    
    static {
        log = LogManager.getLogger("test");
    }
}
