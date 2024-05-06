
package de.zpenguin.thaumicwands.entity.node;

import thaumcraft.common.world.aura.AuraHandler;

public class NTTaint
extends NTNormal {
    public NTTaint(int id) {
        super(id);
    }

    @Override
    void performPeriodicEvent(EntityAuraNode node) {
        super.performPeriodicEvent(node);
        float f = AuraHandler.getFlux(node.world, node.getPosition()/*, Aspect.FLUX*/) / (float)AuraHandler.getAuraBase(node.world, node.getPosition());
        if (node.world.rand.nextFloat() > f * 0.8f) {
            AuraHandler.addFlux(node.world, node.getPosition(), (int)Math.max(1.0, (double)super.calculateStrength(node) * 0.2));
        }
    }
}
