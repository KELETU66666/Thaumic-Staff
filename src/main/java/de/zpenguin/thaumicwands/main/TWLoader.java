package de.zpenguin.thaumicwands.main;

import com.google.common.collect.Lists;
import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.List;

public class TWLoader implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        return Lists.newArrayList("mixins.thaumicwands.json");
    }
}