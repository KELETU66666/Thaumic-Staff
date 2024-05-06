package de.zpenguin.thaumicwands.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import thaumcraft.common.world.aura.AuraThread;

@Mixin(AuraThread.class)
public class MixinAuraChunk {

    @Redirect(method={"processAuraChunk"}, at=@At(value="INVOKE", target = "Ljava/lang/Math;min(FF)F", ordinal = 2), remap = false)
    private float preventAuraRegen(float v, float v1) {
        return 0;
    }
}