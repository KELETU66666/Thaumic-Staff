package de.zpenguin.thaumicwands.block.tile;

import java.util.List;

import de.zpenguin.thaumicwands.entity.node.EntityAuraNode;
import net.minecraft.entity.Entity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.lib.utils.EntityUtils;
import thaumcraft.common.tiles.TileThaumcraft;

public class TileNodeStabilizer
extends TileThaumcraft
implements ITickable {
    public int count = 0;
    int delay = 0;
    List<Entity> nodes = null;

    public void update() {
        if (this.nodes == null || this.delay % 100 == 0) {
            this.nodes = EntityUtils.getEntitiesInRange(this.world, (double)this.pos.getX() + 0.5, (double)this.pos.getY() + 1.5, (double)this.pos.getZ() + 0.5, null, EntityAuraNode.class, 0.5);
        }
        if (!this.gettingPower()) {
            boolean notFirst = false;
            for (Entity e : this.nodes) {
                Vec3d v2;
                if (e == null || e.isDead || !(e instanceof EntityAuraNode)) continue;
                EntityAuraNode an = (EntityAuraNode)e;
                an.stablized = !notFirst;
                Vec3d v1 = new Vec3d((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 1.5, (double)this.pos.getZ() + 0.5);
                double d = v1.squareDistanceTo(v2 = new Vec3d(an.posX, an.posY, an.posZ));
                if (d > 0.001) {
                    v1 = v1.subtract(v2).normalize();
                    if (notFirst) {
                        an.motionX -= v1.x / 750.0;
                        an.motionY -= v1.y / 750.0;
                        an.motionZ -= v1.z / 750.0;
                    } else {
                        an.motionX += v1.x / 1000.0;
                        an.motionY += v1.y / 1000.0;
                        an.motionZ += v1.z / 1000.0;
                    }
                } else if (notFirst) {
                    an.motionY += 0.005;
                }
                notFirst = true;
            }
        }
        if (this.world.isRemote && this.nodes.size() > 0) {
            if (!this.gettingPower()) {
                if (this.delay == 0) {
                    this.count = 37;
                }
                if (this.count < 37) {
                    ++this.count;
                }
            } else if (this.count > 0) {
                --this.count;
            }
        }
        if (this.delay == 0) {
            this.delay = this.world.rand.nextInt(100);
        }
        ++this.delay;
    }

    @SideOnly(value=Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return new AxisAlignedBB((double)this.getPos().getX() - 0.3, (double)this.getPos().getY() - 0.3, (double)this.getPos().getZ() - 0.3, (double)this.getPos().getX() + 1.3, (double)this.getPos().getY() + 1.3, (double)this.getPos().getZ() + 1.3);
    }
}
