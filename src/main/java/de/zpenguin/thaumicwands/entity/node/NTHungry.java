package de.zpenguin.thaumicwands.entity.node;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.common.world.aura.AuraHandler;

public class NTHungry
extends NTNormal {
    public NTHungry(int id) {
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
        int str = this.calculateStrength(node);
        float f = AuraHelper.getVis(node.world, node.getPosition()/*, aspect*/) / (float)AuraHelper.getAuraBase(node.world, node.getPosition());
        if (aspect != null && node.world.rand.nextFloat() < f && AuraHandler.drainVis(node.world, node.getPosition(), str, false) != 0 && node.world.rand.nextInt(1 + node.getNodeSize() * 2) == 0) {
            node.setNodeSize(node.getNodeSize() + 1);
        }
    }

    @Override
    int calculateStrength(EntityAuraNode node) {
        return (int)Math.max(1.0f, (float)super.calculateStrength(node) * 0.1f);
    }
}
