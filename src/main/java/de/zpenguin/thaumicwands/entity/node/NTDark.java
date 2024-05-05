
package de.zpenguin.thaumicwands.entity.node;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import thaumcraft.common.lib.utils.Utils;
import thaumcraft.common.world.biomes.BiomeHandler;

public class NTDark
extends NTNormal {
    public NTDark(int id) {
        super(id);
    }

    @Override
    void performPeriodicEvent(EntityAuraNode node) {
        super.performPeriodicEvent(node);
        int rad = node.world.rand.nextInt(180) * 2;
        Vec3d vsource = new Vec3d(node.posX, node.posY, node.posZ);
        int r = (int)(4.0 + Math.sqrt(node.getNodeSize()));
        for (int q = 0; q < r; ++q) {
            Vec3d vtar = new Vec3d(q, 0.0, 0.0);
            vtar.rotateYaw((float)rad / 180.0f * (float)Math.PI);
            Vec3d vres = vsource.add(vtar.x, vtar.y, vtar.z);
            BlockPos t = new BlockPos(MathHelper.floor(vres.x), MathHelper.floor(vres.y), MathHelper.floor(vres.z));
            Biome biome = node.world.getBiome(t);
            if (biome.getBiomeName() == BiomeHandler.EERIE.biomeName) continue;
            Utils.setBiomeAt(node.world, t, BiomeHandler.EERIE);
            break;
        }
    }

    @Override
    int calculateStrength(EntityAuraNode node) {
        int m = node.world.provider.getMoonPhase(node.world.getWorldInfo().getWorldTime());
        float b = 1.0f - (float)(Math.abs(m - 4) - 2) / 5.0f;
        return (int)Math.max(1.0, Math.sqrt((float)node.getNodeSize() / 3.0f) * (double)(b += (node.getBrightness() - 0.5f) / 3.0f));
    }
}
