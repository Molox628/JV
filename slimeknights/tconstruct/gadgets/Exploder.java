package slimeknights.tconstruct.gadgets;

import net.minecraft.entity.*;
import slimeknights.tconstruct.gadgets.entity.*;
import net.minecraft.item.*;
import com.google.common.collect.*;
import net.minecraft.init.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.google.common.base.*;
import net.minecraftforge.event.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.tools.common.network.*;
import slimeknights.tconstruct.common.*;
import slimeknights.mantle.network.*;
import net.minecraft.util.math.*;
import net.minecraft.block.*;
import java.util.function.*;
import net.minecraft.block.state.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class Exploder
{
    public final double r;
    public final double rr;
    public final int dist;
    public final double explosionStrength;
    public final int blocksPerIteration;
    public final int x;
    public final int y;
    public final int z;
    public final World world;
    public final Entity exploder;
    public final ExplosionEFLN explosion;
    protected int currentRadius;
    private int curX;
    private int curY;
    private int curZ;
    protected List<ItemStack> droppedItems;
    
    public Exploder(final World world, final ExplosionEFLN explosion, final Entity exploder, final BlockPos location, final double r, final double explosionStrength, final int blocksPerIteration) {
        this.r = r;
        this.world = world;
        this.explosion = explosion;
        this.exploder = exploder;
        this.rr = r * r;
        this.dist = (int)r + 1;
        this.explosionStrength = explosionStrength;
        this.blocksPerIteration = blocksPerIteration;
        this.currentRadius = 0;
        this.x = location.func_177958_n();
        this.y = location.func_177956_o();
        this.z = location.func_177952_p();
        this.curX = 0;
        this.curY = 0;
        this.curZ = 0;
        this.droppedItems = (List<ItemStack>)Lists.newArrayList();
    }
    
    public static void startExplosion(final World world, final ExplosionEFLN explosion, final Entity entity, final BlockPos location, final double r, final double explosionStrength) {
        final Exploder exploder = new Exploder(world, explosion, entity, location, r, explosionStrength, Math.max(50, (int)(r * r * r / 10.0)));
        exploder.handleEntities();
        world.func_184133_a((EntityPlayer)null, location, SoundEvents.field_187539_bB, SoundCategory.BLOCKS, 4.0f, (1.0f + (world.field_73012_v.nextFloat() - world.field_73012_v.nextFloat()) * 0.2f) * 0.7f);
        MinecraftForge.EVENT_BUS.register((Object)exploder);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.WorldTickEvent event) {
        if (event.world == this.world && event.phase == TickEvent.Phase.END && !this.iteration()) {
            this.finish();
        }
    }
    
    void handleEntities() {
        final Predicate<Entity> predicate = (Predicate<Entity>)(entity -> entity != null && !entity.func_180427_aV() && EntitySelectors.field_180132_d.apply((Object)entity) && EntitySelectors.field_94557_a.apply((Object)entity) && entity.func_174791_d().func_186679_c((double)this.x, (double)this.y, (double)this.z) <= this.r * this.r);
        final List<Entity> list = (List<Entity>)this.world.func_175674_a(this.exploder, new AxisAlignedBB(this.x - this.r - 1.0, this.y - this.r - 1.0, this.z - this.r - 1.0, this.x + this.r + 1.0, this.y + this.r + 1.0, this.z + this.r + 1.0), (Predicate)predicate);
        ForgeEventFactory.onExplosionDetonate(this.world, (Explosion)this.explosion, (List)list, this.r * 2.0);
        for (final Entity entity : list) {
            Vec3d dir = entity.func_174791_d().func_178788_d(this.exploder.func_174791_d().func_72441_c(0.0, -this.r / 2.0, 0.0));
            double str = (this.r - dir.func_72433_c()) / this.r;
            str = Math.max(0.3, str);
            dir = dir.func_72432_b();
            dir = dir.func_186678_a(this.explosionStrength * str * 0.3);
            entity.func_70024_g(dir.field_72450_a, dir.field_72448_b + 0.5, dir.field_72449_c);
            entity.func_70097_a(DamageSource.func_94539_a((Explosion)this.explosion), (float)(str * this.explosionStrength));
            if (entity instanceof EntityPlayerMP) {
                TinkerNetwork.sendTo((AbstractPacket)new EntityMovementChangePacket(entity), (EntityPlayerMP)entity);
            }
        }
    }
    
    private void finish() {
        final int d = (int)this.r / 2;
        final BlockPos pos = new BlockPos(this.x - d, this.y - d, this.z - d);
        final Random random = new Random();
        final List<ItemStack> aggregatedDrops = (List<ItemStack>)Lists.newArrayList();
        for (final ItemStack drop : this.droppedItems) {
            boolean notInList = true;
            for (final ItemStack stack : aggregatedDrops) {
                if (ItemStack.func_179545_c(drop, stack) && ItemStack.func_77970_a(drop, stack)) {
                    stack.func_190917_f(drop.func_190916_E());
                    notInList = false;
                    break;
                }
            }
            if (notInList) {
                aggregatedDrops.add(drop);
            }
        }
        for (final ItemStack drop : aggregatedDrops) {
            int stacksize = drop.func_190916_E();
            do {
                final BlockPos spawnPos = pos.func_177982_a(random.nextInt((int)this.r), random.nextInt((int)this.r), random.nextInt((int)this.r));
                final ItemStack dropItemstack = drop.func_77946_l();
                dropItemstack.func_190920_e(Math.min(stacksize, 64));
                Block.func_180635_a(this.world, spawnPos, dropItemstack);
                stacksize -= dropItemstack.func_190916_E();
            } while (stacksize > 0);
        }
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }
    
    private boolean iteration() {
        int count = 0;
        this.explosion.func_180342_d();
        while (count < this.blocksPerIteration && this.currentRadius < (int)this.r + 1) {
            final double d = this.curX * this.curX + this.curY * this.curY + this.curZ * this.curZ;
            if (d <= this.rr) {
                final BlockPos pos = new BlockPos(this.x + this.curX, this.y + this.curY, this.z + this.curZ);
                final IBlockState state = this.world.func_180495_p(pos);
                if (!state.func_177230_c().isAir(state, (IBlockAccess)this.world, pos)) {
                    double f = this.explosionStrength * (1.0 - d / this.rr);
                    final float f2 = (this.exploder != null) ? this.exploder.func_180428_a((Explosion)this.explosion, this.world, pos, state) : state.func_177230_c().getExplosionResistance(this.world, pos, (Entity)null, (Explosion)this.explosion);
                    f -= (f2 + 0.3f) * 0.3f;
                    if (f > 0.0 && (this.exploder == null || this.exploder.func_174816_a((Explosion)this.explosion, this.world, pos, state, (float)f))) {
                        ++count;
                        this.explosion.addAffectedBlock(pos);
                    }
                }
            }
            this.step();
        }
        ForgeEventFactory.onExplosionDetonate(this.world, (Explosion)this.explosion, (List)Collections.emptyList(), this.r * 2.0);
        this.explosion.func_180343_e().forEach(this::explodeBlock);
        return count == this.blocksPerIteration;
    }
    
    private void step() {
        if (++this.curX > this.currentRadius) {
            this.curX = -this.currentRadius;
            if (++this.curZ > this.currentRadius) {
                this.curZ = -this.currentRadius;
                if (--this.curY < -this.currentRadius) {
                    ++this.currentRadius;
                    final int n = -this.currentRadius;
                    this.curZ = n;
                    this.curX = n;
                    this.curY = this.currentRadius;
                }
            }
        }
        if (this.curY != -this.currentRadius && this.curY != this.currentRadius && this.curZ != -this.currentRadius && this.curZ != this.currentRadius && this.curX > -this.currentRadius) {
            this.curX = this.currentRadius;
        }
    }
    
    private void explodeBlock(final BlockPos pos) {
        final IBlockState state = this.world.func_180495_p(pos);
        final Block block = state.func_177230_c();
        if (!this.world.field_72995_K && block.func_149659_a((Explosion)this.explosion)) {
            final List<ItemStack> drops = (List<ItemStack>)block.getDrops((IBlockAccess)this.world, pos, state, 0);
            ForgeEventFactory.fireBlockHarvesting((List)drops, this.world, pos, state, 0, 1.0f, false, (EntityPlayer)null);
            this.droppedItems.addAll(drops);
        }
        if (this.world instanceof WorldServer) {
            ((WorldServer)this.world).func_180505_a(EnumParticleTypes.EXPLOSION_NORMAL, true, (double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), 2, 0.0, 0.0, 0.0, 0.0, new int[0]);
            ((WorldServer)this.world).func_180505_a(EnumParticleTypes.SMOKE_LARGE, true, (double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p(), 1, 0.0, 0.0, 0.0, 0.0, new int[0]);
        }
        block.onBlockExploded(this.world, pos, (Explosion)this.explosion);
    }
}
