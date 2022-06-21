package slimeknights.tconstruct.shared;

import net.minecraftforge.event.entity.living.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.shared.block.*;
import net.minecraft.block.properties.*;
import slimeknights.tconstruct.world.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import slimeknights.tconstruct.*;

public class BlockEvents
{
    private static boolean worldLoaded;
    
    @SubscribeEvent
    public void onLivingJump(final LivingEvent.LivingJumpEvent event) {
        if (event.getEntity() == null) {
            return;
        }
        BlockPos pos = new BlockPos(event.getEntity().field_70165_t, event.getEntity().field_70163_u, event.getEntity().field_70161_v);
        if (event.getEntity().func_130014_f_().func_175623_d(pos)) {
            pos = pos.func_177977_b();
        }
        final IBlockState state = event.getEntity().func_130014_f_().func_180495_p(pos);
        final Block block = state.func_177230_c();
        if (block == TinkerCommons.blockSlimeCongealed) {
            this.bounce(event.getEntity(), 0.25f);
        }
        else if (block == TinkerCommons.blockSoil) {
            if (state.func_177229_b((IProperty)BlockSoil.TYPE) == BlockSoil.SoilTypes.SLIMY_MUD_GREEN || state.func_177229_b((IProperty)BlockSoil.TYPE) == BlockSoil.SoilTypes.SLIMY_MUD_BLUE) {
                this.bounce(event.getEntity(), 0.15f);
            }
        }
        else if (BlockEvents.worldLoaded && (block == TinkerWorld.slimeDirt || block == TinkerWorld.slimeGrass)) {
            this.bounce(event.getEntity(), 0.06f);
        }
    }
    
    private void bounce(final Entity entity, final float amount) {
        entity.field_70181_x += amount;
        entity.func_184185_a(SoundEvents.field_187886_fs, 0.5f + amount, 1.0f);
    }
    
    static {
        BlockEvents.worldLoaded = TConstruct.pulseManager.isPulseLoaded("TinkerWorld");
    }
}
