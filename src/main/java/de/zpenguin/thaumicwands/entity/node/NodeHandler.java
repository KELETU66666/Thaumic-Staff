package de.zpenguin.thaumicwands.entity.node;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.utils.PosXY;

import java.util.concurrent.ConcurrentHashMap;

public class NodeHandler {

    static ConcurrentHashMap<Integer, NodeWorld> auras = new ConcurrentHashMap<>();

    public static void addNodeRechargeTicket(EntityAuraNode node, Aspect aspect, int strength) {
        PosXY pos = new PosXY(MathHelper.floor(node.posX) >> 4, MathHelper.floor(node.posZ) >> 4);
        NodeHandler.addRechargeTicket(node.world.provider.getDimension(), pos, aspect, strength);
    }

    public static void addRechargeTicket(World world, BlockPos bp, Aspect aspect) {
        PosXY pos = new PosXY(bp.getX() >> 4, bp.getZ() >> 4);
        NodeHandler.addRechargeTicket(world.provider.getDimension(), pos, aspect, 1);
    }

    public static void addRechargeTicket(World world, BlockPos bp, Aspect aspect, int amt) {
        PosXY pos = new PosXY(bp.getX() >> 4, bp.getZ() >> 4);
        NodeHandler.addRechargeTicket(world.provider.getDimension(), pos, aspect, amt);
    }

    public static void addRechargeTicket(int dim, PosXY pos, Aspect aspect, int amt) {
        NodeWorld aw = auras.get(dim);
        if (aw != null) {
            AspectList al;
            if (!aw.getNodeTickets().containsKey(pos)) {
                aw.getNodeTickets().put(pos, new AspectList());
            }
            if ((al = aw.getNodeTickets().get(pos)) != null) {
                al.add(aspect, amt);
            }
        }
    }
}
