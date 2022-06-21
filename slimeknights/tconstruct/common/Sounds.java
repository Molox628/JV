package slimeknights.tconstruct.common;

import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraftforge.event.*;
import java.util.function.*;
import net.minecraftforge.registries.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;

@Mod.EventBusSubscriber(modid = "tconstruct")
public abstract class Sounds
{
    private static final List<SoundEvent> sounds;
    public static final SoundEvent saw;
    public static final SoundEvent frypan_boing;
    public static final SoundEvent toy_squeak;
    public static final SoundEvent slimesling;
    public static final SoundEvent shocking_charged;
    public static final SoundEvent shocking_discharge;
    public static final SoundEvent stone_hit;
    public static final SoundEvent wood_hit;
    public static final SoundEvent crossbow_reload;
    
    private Sounds() {
    }
    
    private static SoundEvent sound(final String name) {
        final ResourceLocation location = Util.getResource(name);
        final SoundEvent event = new SoundEvent(location);
        event.setRegistryName(location);
        Sounds.sounds.add(event);
        return event;
    }
    
    public static void playSoundForAll(final Entity entity, final SoundEvent sound, final float volume, final float pitch) {
        entity.func_130014_f_().func_184133_a((EntityPlayer)null, entity.func_180425_c(), sound, entity.func_184176_by(), volume, pitch);
    }
    
    public static void PlaySoundForPlayer(final Entity entity, final SoundEvent sound, final float volume, final float pitch) {
        if (entity instanceof EntityPlayerMP) {
            TinkerNetwork.sendPacket(entity, (Packet<?>)new SPacketSoundEffect(sound, entity.func_184176_by(), entity.field_70165_t, entity.field_70163_u, entity.field_70161_v, volume, pitch));
        }
    }
    
    @SubscribeEvent
    public static void registerSoundEvent(final RegistryEvent.Register<SoundEvent> event) {
        final IForgeRegistry<SoundEvent> registry = (IForgeRegistry<SoundEvent>)event.getRegistry();
        Sounds.sounds.forEach((Consumer<? super Object>)registry::register);
    }
    
    static {
        sounds = new ArrayList<SoundEvent>();
        saw = sound("little_saw");
        frypan_boing = sound("frypan_hit");
        toy_squeak = sound("toy_squeak");
        slimesling = sound("slimesling");
        shocking_charged = sound("charged");
        shocking_discharge = sound("discharge");
        stone_hit = sound("stone_hit");
        wood_hit = sound("wood_hit");
        crossbow_reload = sound("crossbow_reload");
    }
}
