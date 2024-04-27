package de.zpenguin.thaumicwands.event;

import de.zpenguin.thaumicwands.api.item.wand.IStaff;
import de.zpenguin.thaumicwands.api.item.wand.IWand;
import de.zpenguin.thaumicwands.entity.EntityVisOrb;
import de.zpenguin.thaumicwands.main.ThaumicWands;
import de.zpenguin.thaumicwands.tile.TileArcaneWorkbenchNew;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.blocks.BlocksTC;

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
    public static void createArcaneWorkbench(PlayerInteractEvent.RightClickBlock e) {
        EntityPlayer player = e.getEntityPlayer();
        for (EnumHand hand : EnumHand.values()) {
            ItemStack wand = player.getHeldItem(hand);
            if (wand.getItem() instanceof IWand) {
                if (player.world.getBlockState(e.getPos()).getBlock() == BlocksTC.tableWood) {
                    player.world.setBlockState(e.getPos(), BlocksTC.arcaneWorkbench.getDefaultState());
                    player.world.setTileEntity(e.getPos(), new TileArcaneWorkbenchNew());
                    TileArcaneWorkbenchNew taw = (TileArcaneWorkbenchNew) player.world.getTileEntity(e.getPos());
                    if ((taw != null) && !(wand.getItem() instanceof IStaff)) {
                        taw.inventoryCraft.setInventorySlotContents(15, wand.copy());
                        player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
                    }
                    taw.markDirty();
                    player.world.notifyBlockUpdate(e.getPos(), player.world.getBlockState(e.getPos()), player.world.getBlockState(e.getPos()), 3);

                }
            }
        }
    }
}
