// Decompiled with: CFR 0.152
// Class Version: 6
package de.zpenguin.thaumicwands.entity.node;

import java.util.concurrent.ConcurrentHashMap;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.common.lib.utils.PosXY;
import thaumcraft.common.world.aura.AuraChunk;

public class NodeWorld {
    int dim;
    ConcurrentHashMap<PosXY, AuraChunk> auraChunks = new ConcurrentHashMap();
    ConcurrentHashMap<PosXY, AspectList> nodeTickets = new ConcurrentHashMap();

    public NodeWorld(int dim) {
        this.dim = dim;
    }

    public ConcurrentHashMap<PosXY, AuraChunk> getAuraChunks() {
        return this.auraChunks;
    }

    public void setAuraChunks(ConcurrentHashMap<PosXY, AuraChunk> auraChunks) {
        this.auraChunks = auraChunks;
    }

    public AuraChunk getAuraChunkAt(int x, int y) {
        return this.getAuraChunkAt(new PosXY(x, y));
    }

    public AuraChunk getAuraChunkAt(PosXY loc) {
        return this.auraChunks.get(loc);
    }

    public ConcurrentHashMap<PosXY, AspectList> getNodeTickets() {
        return this.nodeTickets;
    }

    public void setNodeTickets(ConcurrentHashMap<PosXY, AspectList> nodeTickets) {
        this.nodeTickets = nodeTickets;
    }
}