package de.zpenguin.thaumicwands.entity.node;

public class NTAstral
extends NTNormal {
    public NTAstral(int id) {
        super(id);
    }

    @Override
    int calculateStrength(EntityAuraNode node) {
        int m = node.world.provider.getMoonPhase(node.world.getWorldInfo().getWorldTime());
        float b = 1.0f + (float)(Math.abs(m - 4) - 2) / 5.0f;
        return (int)Math.max(1.0, Math.sqrt((float)node.getNodeSize() / 3.0f) * (double)(b -= (node.getBrightness() - 0.5f) / 3.0f));
    }
}
