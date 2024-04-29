// Decompiled with: CFR 0.152
// Class Version: 6
package de.zpenguin.thaumicwands.entity.node;

import net.minecraft.util.math.BlockPos;
import thaumcraft.common.world.aura.AuraHandler;

public class NTPure
extends NTNormal {
    public NTPure(int id) {
        super(id);
    }

    @Override
    void performPeriodicEvent(EntityAuraNode node) {
        super.performPeriodicEvent(node);
        if (AuraHandler.drainFlux(node.world, new BlockPos(node.posX, node.posY, node.posZ), /*Aspect.FLUX, */1, false) != 0 && node.world.rand.nextFloat() < 0.025f) {
            node.setNodeSize(node.getNodeSize() - 1);
            if (node.getNodeSize() <= 0) {
                node.setDead();
            }
        }
    }
}
