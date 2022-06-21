package slimeknights.tconstruct.library.events;

import slimeknights.tconstruct.library.materials.*;
import net.minecraftforge.fml.common.eventhandler.*;
import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.*;

public abstract class MaterialEvent extends TinkerEvent
{
    public final Material material;
    
    public MaterialEvent(final Material material) {
        this.material = material;
    }
    
    @Cancelable
    public static class MaterialRegisterEvent extends MaterialEvent
    {
        public MaterialRegisterEvent(final Material material) {
            super(material);
        }
    }
    
    @Event.HasResult
    public static class StatRegisterEvent<T extends IMaterialStats> extends MaterialEvent
    {
        public final T stats;
        public T newStats;
        
        public StatRegisterEvent(final Material material, final T stats) {
            super(material);
            this.stats = stats;
        }
        
        public void overrideResult(final T newStats) {
            if (!this.stats.getIdentifier().equals(newStats.getIdentifier())) {
                TinkerRegistry.log.error("StatRegisterEvent: New stats don't match old stats type. New is {}, old was {}", (Object)newStats.getIdentifier(), (Object)this.stats.getIdentifier());
                return;
            }
            this.newStats = newStats;
            this.setResult(Event.Result.ALLOW);
        }
    }
    
    @Cancelable
    public static class TraitRegisterEvent<T extends ITrait> extends MaterialEvent
    {
        public final T trait;
        
        public TraitRegisterEvent(final Material material, final T trait) {
            super(material);
            this.trait = trait;
        }
    }
    
    @Cancelable
    public static class IntegrationEvent extends MaterialEvent
    {
        public final MaterialIntegration materialIntegration;
        
        public IntegrationEvent(final Material material, final MaterialIntegration materialIntegration) {
            super(material);
            this.materialIntegration = materialIntegration;
        }
    }
}
