package de.zpenguin.thaumicwands.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchCategory;
import thaumcraft.api.research.ResearchEntry;
import thaumcraft.common.lib.research.ResearchManager;

@Mixin(ResearchManager.class)
public class MixinResearchManager {

    @Inject(method = "addResearchToCategory", at = @At(value = "HEAD"), remap = false)
    private static void mixinAddResearchToCategory(ResearchEntry ri, CallbackInfo ci) {
        ResearchCategory locate = ResearchCategories.getResearchCategory(ri.getCategory());
        if (locate != null && locate.research.containsKey(ri.getKey()) && ri.getKey().equals("FIRSTSTEPS")) {
            locate.research.put(ri.getKey(), ri);
        }
    }

}
