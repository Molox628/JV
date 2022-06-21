package slimeknights.tconstruct.common.config;

import net.minecraft.client.gui.*;
import slimeknights.tconstruct.library.*;
import net.minecraftforge.fml.client.config.*;
import com.google.common.collect.*;
import net.minecraftforge.common.config.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.*;
import java.util.*;

public class ConfigGui extends GuiConfig
{
    public ConfigGui(final GuiScreen parentScreen) {
        super(parentScreen, (List)getConfigElements(), "tconstruct", false, false, Util.prefix("configgui.title"));
    }
    
    private static List<IConfigElement> getConfigElements() {
        final List<IConfigElement> list = (List<IConfigElement>)Lists.newArrayList();
        list.add((IConfigElement)new ConfigElement(Config.Modules));
        list.add((IConfigElement)new ConfigElement(Config.Gameplay));
        list.add((IConfigElement)new ConfigElement(Config.Worldgen));
        list.add((IConfigElement)new ConfigElement(Config.ClientSide));
        return list;
    }
    
    public static class ConfigGuiFactory implements IModGuiFactory
    {
        public void initialize(final Minecraft minecraftInstance) {
        }
        
        public boolean hasConfigGui() {
            return true;
        }
        
        public GuiScreen createConfigGui(final GuiScreen parentScreen) {
            return (GuiScreen)new ConfigGui(parentScreen);
        }
        
        public Set<IModGuiFactory.RuntimeOptionCategoryElement> runtimeGuiCategories() {
            return null;
        }
    }
}
