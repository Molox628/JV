package slimeknights.tconstruct.plugin.theoneprobe;

import com.google.common.base.*;
import mcjty.theoneprobe.api.*;
import javax.annotation.*;

public class GetTheOneProbe implements Function<ITheOneProbe, Void>
{
    @Nullable
    public Void apply(final ITheOneProbe probe) {
        probe.registerProvider((IProbeInfoProvider)new CastingInfoProvider());
        probe.registerProvider((IProbeInfoProvider)new ProgressInfoProvider());
        return null;
    }
}
