package de.zpenguin.thaumicwands.event;

import de.zpenguin.thaumicwands.entity.node.EntityAuraNode;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.world.biomes.BiomeHandler;

import java.util.HashMap;
import java.util.Random;

public class TWWorldGenerator implements IWorldGenerator{

    public static TWWorldGenerator INSTANCE = new TWWorldGenerator();

    HashMap<BlockPos, Boolean> structureNode = new HashMap<>();

    @Override
    public void generate(Random random, int i, int i1, World world, IChunkGenerator iChunkGenerator, IChunkProvider iChunkProvider) {
        int blacklist = BiomeHandler.getDimBlacklist(world.provider.getDimension());
        generateNodes(world, random, i, i1, true, blacklist);
    }

    private void generateNodes(World world, Random random, int chunkX, int chunkZ, boolean newGen, int blacklist) {
        if (blacklist != 0 && blacklist != 2 && newGen) {
            BlockPos var7 = null;
            try {
                var7 = ((WorldServer)world).getChunkProvider().getNearestStructurePos(world, "Stronghold", world.getHeight(new BlockPos(chunkX * 16 + 8, 64, chunkZ * 16 + 8)), false);
            }
            catch (Exception e) {
                // empty catch block
            }
            if (var7 != null && !this.structureNode.containsKey(var7)) {
                this.structureNode.put(var7, true);
                BlockPos bp = var7.up(3);
                spawnNode(world, bp, -1, 1.0f);
            } else {
                int z;
                int x = chunkX * 16 + random.nextInt(16);
                int h = world.getHeight(new BlockPos(x, 64, z = chunkZ * 16 + random.nextInt(16))).getY();
                if (h < world.getActualHeight() / 3) {
                    h = world.getActualHeight() / 3;
                }
                int y = 8 + random.nextInt(h);
                BlockPos bp = new BlockPos(x, y, z);
                while (!world.isAirBlock(bp)) {
                    if (world.getBlockState(bp = bp.up(2)).getBlock() != Blocks.BEDROCK && bp.getY() < world.getActualHeight()) continue;
                    return;
                }
                if (world.isAirBlock(bp) && random.nextInt(Math.max(2, 33)) == 0) {
                    spawnNode(world, bp, -1, 1.0f);
                }
            }
        }
    }

    public static void spawnNode(World world, BlockPos bp, int type, float sizemod) {
        EntityAuraNode e = new EntityAuraNode(world);
        e.setLocationAndAngles((double)bp.getX() + 0.5, (double)bp.getY() + 0.5, (double)bp.getZ() + 0.5, 0.0f, 0.0f);
        world.spawnEntity(e);
        e.randomizeNode();
        if (type >= 0) {
            e.setNodeType(type);
        }
        e.setNodeSize((int)((float)e.getNodeSize() * sizemod));
        if (e.getNodeType() == 4) {
            AuraHelper.polluteAura(world, bp, 100, false);
            for (int a = 0; a < 16; ++a) {
                BlockPos tt = bp.add(world.rand.nextInt(16) - world.rand.nextInt(16), world.rand.nextInt(16) - world.rand.nextInt(16), world.rand.nextInt(16) - world.rand.nextInt(16));
                IBlockState ts = world.getBlockState(tt);
                if (!world.isAirBlock(tt) && !ts.getBlock().isReplaceable(world, tt) || !BlockUtils.isAdjacentToSolidBlock(world, tt)) continue;
                world.setBlockState(tt, BlocksTC.taintFibre.getDefaultState());
            }
        }
    }
}
