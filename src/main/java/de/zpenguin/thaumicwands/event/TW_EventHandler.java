package de.zpenguin.thaumicwands.event;

import de.zpenguin.thaumicwands.api.item.wand.IWand;
import de.zpenguin.thaumicwands.entity.EntityVisOrb;
import de.zpenguin.thaumicwands.main.ThaumicWands;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.items.RechargeHelper;
import thaumcraft.common.world.aura.AuraHandler;

@EventBusSubscriber(modid = ThaumicWands.modID)
public class TW_EventHandler {

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent e) {
        if (!e.getEntityLiving().world.isRemote) {
            AspectList aspects = AspectHelper.getEntityAspects(e.getEntityLiving());
            if (aspects != null && aspects.visSize() > 0 && e.getEntityLiving().getEntityWorld().rand.nextBoolean()) {
                EntityVisOrb orb = new EntityVisOrb(e.getEntityLiving().getEntityWorld(), e.getEntityLiving().posX, e.getEntityLiving().posY, e.getEntityLiving().posZ, 1 + e.getEntityLiving().world.rand.nextInt(1 + Math.floorDiv(aspects.visSize(), 20)));
                e.getEntityLiving().getEntityWorld().spawnEntity(orb);
            }

        }
    }

    @SubscribeEvent
    public static void rechargeWands(TickEvent.PlayerTickEvent e) {
        if (!e.player.world.isRemote) {
            for (EnumHand hand : EnumHand.values())
                if (e.player.getHeldItem(hand).getItem() instanceof IWand && AuraHandler.getAuraBase(e.player.world, e.player.getPosition()) > 1) {
                    RechargeHelper.rechargeItemBlindly(e.player.getHeldItem(hand), e.player, 1);
                    AuraHandler.drainVis(e.player.world, e.player.getPosition(), 1.0F, false);
                }
        }
    }
}
