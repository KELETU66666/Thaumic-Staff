package de.zpenguin.thaumicwands.main;

import fermiumbooter.FermiumRegistryAPI;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;

import java.util.Map;

@IFMLLoadingPlugin.MCVersion("1.12.2")
public class ThaumicWandsPlugin implements IFMLLoadingPlugin {

    public ThaumicWandsPlugin() {
        MixinBootstrap.init();
        //False for Vanilla/Coremod mixins, true for regular mod mixins
        //ermiumRegistryAPI.enqueueMixin(false, "mixins.replacememodid.vanilla.json");
        FermiumRegistryAPI.enqueueMixin(true, "mixins.thaumicwands.json", () -> Loader.isModLoaded("thaumcraft"));
        //--> Replaced by @MixinConfig.MixinToggle in ForgeConfigHandler. This way is still an option for more complicated conditions
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}