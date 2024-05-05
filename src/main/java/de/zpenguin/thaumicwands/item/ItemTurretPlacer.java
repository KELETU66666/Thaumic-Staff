package de.zpenguin.thaumicwands.item;
import java.util.List;

import de.zpenguin.thaumicwands.entity.EntityNodeMagnet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.common.entities.construct.EntityOwnedConstruct;


public class ItemTurretPlacer extends ItemBase
{
    public ItemTurretPlacer(String name) {
        super(name);
    }
    
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        if (side == EnumFacing.DOWN) {
            return EnumActionResult.PASS;
        }
        boolean flag = world.getBlockState(pos).getBlock().isReplaceable(world, pos);
        BlockPos blockpos = flag ? pos : pos.offset(side);
        if (!player.canPlayerEdit(blockpos, side, player.getHeldItem(hand))) {
            return EnumActionResult.PASS;
        }
        BlockPos blockpos2 = blockpos.up();
        boolean flag2 = !world.isAirBlock(blockpos) && !world.getBlockState(blockpos).getBlock().isReplaceable(world, blockpos);
        flag2 |= (!world.isAirBlock(blockpos2) && !world.getBlockState(blockpos2).getBlock().isReplaceable(world, blockpos2));
        if (flag2) {
            return EnumActionResult.PASS;
        }
        double d0 = blockpos.getX();
        double d2 = blockpos.getY();
        double d3 = blockpos.getZ();
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(d0, d2, d3, d0 + 1.0, d2 + 2.0, d3 + 1.0));
        if (!list.isEmpty()) {
            return EnumActionResult.PASS;
        }
        if (!world.isRemote) {
            world.setBlockToAir(blockpos);
            world.setBlockToAir(blockpos2);
            EntityOwnedConstruct turret = new EntityNodeMagnet(world, blockpos);
            world.spawnEntity(turret);
            turret.setOwned(true);
            turret.setValidSpawn();
            turret.setOwnerId(player.getUniqueID());
            world.playSound(null, turret.posX, turret.posY, turret.posZ, SoundEvents.ENTITY_ARMORSTAND_PLACE, SoundCategory.BLOCKS, 0.75f, 0.8f);
            player.getHeldItem(hand).shrink(1);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }
}