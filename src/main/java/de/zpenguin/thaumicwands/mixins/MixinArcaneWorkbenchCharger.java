package de.zpenguin.thaumicwands.mixins;

import de.zpenguin.thaumicwands.main.TW_GuiHandler;
import de.zpenguin.thaumicwands.main.ThaumicWands;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import thaumcraft.Thaumcraft;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.common.blocks.crafting.BlockArcaneWorkbenchCharger;

@Mixin(BlockArcaneWorkbenchCharger.class)
public class MixinArcaneWorkbenchCharger {

    @Inject(method = "onBlockActivated", at = @At(value = "HEAD"), cancellable = true)
    public void mixinOnBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ, CallbackInfoReturnable<Boolean> cir){
        if (world.isRemote)
            cir.setReturnValue(true);
        if (world.getBlockState(pos.down()).getBlock() == BlocksTC.arcaneWorkbench)
            player.openGui(ThaumicWands.instance, TW_GuiHandler.guiArcaneWorkbench, world, pos.getX(), pos.getY()-1, pos.getZ());
        if (world.getBlockState(pos.down()).getBlock() == BlocksTC.wandWorkbench)
            player.openGui(Thaumcraft.instance, 7, world, pos.getX(), pos.down().getY(), pos.getZ());
        cir.setReturnValue(true);
    }
}
