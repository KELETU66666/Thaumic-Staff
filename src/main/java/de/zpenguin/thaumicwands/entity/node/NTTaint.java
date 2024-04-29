// Decompiled with: CFR 0.152
// Class Version: 6
package de.zpenguin.thaumicwands.entity.node;

import thaumcraft.api.aspects.Aspect;
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
            NodeHandler.addNodeRechargeTicket(node, Aspect.FLUX, (int)Math.max(1.0, (double)super.calculateStrength(node) * 0.2));
        }
    }
}
