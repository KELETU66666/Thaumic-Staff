package de.zpenguin.thaumicwands.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import de.zpenguin.thaumicwands.api.item.wand.IWand;
import de.zpenguin.thaumicwands.entity.EntityVisOrb;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import thaumcraft.api.aura.AuraHelper;
import thaumcraft.api.items.RechargeHelper;
import thaumcraft.common.world.aura.AuraHandler;

public class ItemPrimalCharm extends ItemBase implements IBauble {

	public ItemPrimalCharm(String name) {
		super(name);
		this.maxStackSize = 1;
	}


	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean isSelected) {
		if(!(entity instanceof EntityPlayer)) return;
		EntityPlayer player = (EntityPlayer) entity;

		if(!world.isRemote && player.ticksExisted % 200 == 42 && AuraHelper.getVis(world, player.getPosition()) > 0) {
			AuraHelper.drainVis(world, player.getPosition(), 1, false);
			world.spawnEntity(new EntityVisOrb(world, player.posX + (world.rand.nextGaussian()* 2) - 0.5D, player.posY, player.posZ + (world.rand.nextGaussian()*2) - 0.5D, 1));

			player.playSound(new SoundEvent(new ResourceLocation("thaumcraft","random.orb")), 0.4F, 2.0F + world.rand.nextFloat() * 0.4F);
		}

	}

	@Override
	public void onWornTick(ItemStack stack, EntityLivingBase player) {
		if (!player.world.isRemote && player instanceof EntityPlayer && player.getEntityWorld().getTotalWorldTime() % 20 == 0) {
			for (EnumHand hand : EnumHand.values())
				if (player.getHeldItem(hand).getItem() instanceof IWand && AuraHandler.getAuraBase(player.world, player.getPosition()) > 1) {
					RechargeHelper.rechargeItemBlindly(player.getHeldItem(hand), (EntityPlayer) player, 1);
					AuraHandler.drainVis(player.world, player.getPosition(), 1, false);
				}
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack stack) {
		return BaubleType.CHARM;
	}

}
