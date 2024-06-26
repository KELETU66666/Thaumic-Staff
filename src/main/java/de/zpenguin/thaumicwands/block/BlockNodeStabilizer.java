package de.zpenguin.thaumicwands.block;

import de.zpenguin.thaumicwands.block.tile.TileNodeStabilizer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import thaumcraft.common.config.ConfigItems;

import javax.annotation.Nullable;

public class BlockNodeStabilizer
extends BlockContainer{
    public BlockNodeStabilizer() {
        super(Material.ROCK);
        this.setRegistryName("node_stabilizer");
        this.setTranslationKey("node_stabilizer");
        this.setCreativeTab(ConfigItems.TABTC);
        this.setHardness(2.0F);
        this.setResistance(20.0F);
    }
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileNodeStabilizer();
    }

    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState stat) {
        return false;
    }

    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return null;
    }
}
