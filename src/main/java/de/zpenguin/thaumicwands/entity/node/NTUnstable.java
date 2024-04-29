// Decompiled with: CFR 0.152
// Class Version: 6
package de.zpenguin.thaumicwands.entity.node;

import thaumcraft.api.aspects.Aspect;

public class NTUnstable
extends NTNormal {
    public NTUnstable(int id) {
        super(id);
    }

    @Override
    void performPeriodicEvent(EntityAuraNode node) {
        super.performPeriodicEvent(node);
        if (node.world.rand.nextInt(33) == 0) {
            int s = Aspect.getPrimalAspects().size();
            node.setAspectTag(Aspect.getPrimalAspects().get(node.world.rand.nextInt(s)).getTag());
        }
    }
}
