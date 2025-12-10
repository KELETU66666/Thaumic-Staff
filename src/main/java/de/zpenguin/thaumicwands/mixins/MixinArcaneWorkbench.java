package de.zpenguin.thaumicwands.mixins;

import de.zpenguin.thaumicwands.main.TW_GuiHandler;
import de.zpenguin.thaumicwands.main.ThaumicWands;
import de.zpenguin.thaumicwands.tile.TileArcaneWorkbenchNew;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.common.blocks.BlockTCDevice;
import thaumcraft.common.blocks.crafting.BlockArcaneWorkbench;

@Mixin(BlockArcaneWorkbench.class)
public class MixinArcaneWorkbench extends BlockTCDevice {

    public MixinArcaneWorkbench(Material mat, Class tc, String name) {
        super(mat, tc, name);
    }

    @Inject(method = "onBlockActivated", at = @At(value = "HEAD"), cancellable = true)
    public void mixinOnBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ, CallbackInfoReturnable<Boolean> cir) {
        if (world.isRemote)
            cir.setReturnValue(true);
        player.openGui(ThaumicWands.instance, TW_GuiHandler.guiArcaneWorkbench, world, pos.getX(), pos.getY(), pos.getZ());
        cir.setReturnValue(true);
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileArcaneWorkbenchNew();
    }
}
