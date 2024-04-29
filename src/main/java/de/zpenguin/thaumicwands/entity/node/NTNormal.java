// Decompiled with: CFR 0.152
// Class Version: 6
package de.zpenguin.thaumicwands.entity.node;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectHelper;

public class NTNormal
extends NodeType {
    public NTNormal(int id) {
        super(id);
    }

    @Override
    void performTickEvent(EntityAuraNode node) {
    }

    @Override
    void performPeriodicEvent(EntityAuraNode node) {
        if (node.world.isRemote) {
            return;
        }
        Aspect aspect = node.getAspect();
        if (aspect != null) {
            NodeHandler.addNodeRechargeTicket(node, aspect.isPrimal() ? aspect : AspectHelper.getRandomPrimal(node.world.rand, aspect), this.calculateStrength(node));
        }
    }

    @Override
    int calculateStrength(EntityAuraNode node) {
        int m = node.world.provider.getMoonPhase(node.world.getWorldInfo().getWorldTime());
        float b = 1.0f + (float)(Math.abs(m - 4) - 2) / 5.0f;
        return (int)Math.max(1.0, Math.sqrt((float)node.getNodeSize() / 3.0f) * (double)b);
    }
}
