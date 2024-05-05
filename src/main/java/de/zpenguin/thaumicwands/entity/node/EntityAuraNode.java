
package de.zpenguin.thaumicwands.entity.node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.zpenguin.thaumicwands.main.CommonProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectHelper;
import thaumcraft.common.lib.utils.EntityUtils;

public class EntityAuraNode extends Entity {
    private static final DataParameter<Integer> NODE_SIZE = EntityDataManager.createKey(EntityAuraNode.class, DataSerializers.VARINT);
    private static final DataParameter<String> ASPECT_TYPE = EntityDataManager.createKey(EntityAuraNode.class, DataSerializers.STRING);
    private static final DataParameter<Byte> NODE_TYPE = EntityDataManager.createKey(EntityAuraNode.class, DataSerializers.BYTE);

    private int tickCounter = -1;
    int checkDelay = -1;
    List<EntityAuraNode> neighbours = null;
    public boolean stablized = false;

    public EntityAuraNode(World worldIn) {
        super(worldIn);
        this.setSize(0.5f, 0.5f);
        this.isImmuneToFire = true;
        setRenderDistanceWeight(4.0);
        this.noClip = true;
    }

    public void onUpdate() {
        if (this.getNodeSize() == 0) {
            this.randomizeNode();
        }
        if (this.tickCounter < 0) {
            this.tickCounter = this.rand.nextInt(200);
        }
        this.world.profiler.startSection("entityBaseTick");
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        NodeType.nodeTypes[this.getNodeType()].performTickEvent(this);
        if (this.tickCounter++ > 200) {
            this.tickCounter = 0;
            NodeType.nodeTypes[this.getNodeType()].performPeriodicEvent(this);
        }
        this.checkAdjacentNodes();
        if (this.motionX != 0.0 || this.motionY != 0.0 || this.motionZ != 0.0) {
            this.motionX *= 0.8;
            this.motionY *= 0.8;
            this.motionZ *= 0.8;
            super.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        }
        this.world.profiler.endSection();
    }

    private void checkAdjacentNodes() {
        if (this.neighbours == null || this.checkDelay < this.ticksExisted) {
            this.neighbours = EntityUtils.getEntitiesInRange(this.world, this.posX, this.posY, this.posZ, this, EntityAuraNode.class, 32.0);
            this.checkDelay = this.ticksExisted + 750;
        }
        if (!this.stablized) {
            try {
                Iterator<EntityAuraNode> i = this.neighbours.iterator();
                while (i.hasNext()) {
                    Aspect a;
                    Entity e = i.next();
                    if (e == null || e.isDead || !(e instanceof EntityAuraNode)) {
                        i.remove();
                        continue;
                    }
                    EntityAuraNode an = (EntityAuraNode)e;
                    if (an.stablized) continue;
                    double xd = this.posX - an.posX;
                    double yd = this.posY - an.posY;
                    double zd = this.posZ - an.posZ;
                    double d = xd * xd + yd * yd + zd * zd;
                    if (d < (double)(this.getNodeSize() + an.getNodeSize()) * 1.5 && d > 0.1) {
                        float tq = (float)(this.getNodeSize() + an.getNodeSize()) * 1.5f;
                        float xm = (float)(-xd / d / (double)tq * ((double)an.getNodeSize() / 50.0));
                        float ym = (float)(-yd / d / (double)tq * ((double)an.getNodeSize() / 50.0));
                        float zm = (float)(-zd / d / (double)tq * ((double)an.getNodeSize() / 50.0));
                        this.motionX += xm;
                        this.motionY += ym;
                        this.motionZ += zm;
                        continue;
                    }
                    if (!(d <= 0.1) || this.getNodeSize() < an.getNodeSize() || this.world.isRemote) continue;
                    int bonus = (int)Math.sqrt(an.getNodeSize());
                    int n = this.getNodeSize() + bonus;
                    this.setNodeSize(n);
                    if ((double)this.rand.nextInt(100) < Math.sqrt(an.getNodeSize()) && (a = AspectHelper.getCombinationResult(this.getAspect(), an.getAspect())) != null) {
                        this.setAspectTag(a.getTag());
                    }
                    if (this.getNodeType() == 0 && an.getNodeType() != 0 && this.rand.nextInt(3) == 0 || this.getNodeType() != 0 && an.getNodeType() != 0 && (double)this.rand.nextInt(100) < Math.sqrt(an.getNodeSize() / 2)) {
                        this.setNodeType(an.getNodeType());
                    }
                    an.setDead();
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (this.ticksExisted % 50 == 0) {
            this.stablized = this.world.getBlockState(this.getPosition().down()).getBlock() == CommonProxy.nodeStabilizer && !this.world.isBlockPowered(this.getPosition().down());
        }
    }

    public int getNodeSize() {
        return this.dataManager.get(NODE_SIZE);
    }

    public void setNodeSize(int p) {
        this.dataManager.set(NODE_SIZE, p);
    }

    public String getAspectTag() {
        return this.dataManager.get(ASPECT_TYPE);
    }

    public Aspect getAspect() {
        return Aspect.getAspect(this.getAspectTag());
    }

    public void setAspectTag(String t) {
        this.dataManager.set(ASPECT_TYPE, String.valueOf(t));
    }

    public int getNodeType() {
        return this.dataManager.get(NODE_TYPE);
    }

    public void setNodeType(int p) {
        this.dataManager.set(NODE_TYPE, ((byte) MathHelper.clamp(p, 0, NodeType.nodeTypes.length - 1)));
    }

    protected void entityInit() {
        this.dataManager.register(NODE_SIZE, 0);
        this.dataManager.register(ASPECT_TYPE, "");
        this.dataManager.register(NODE_TYPE, (byte) 0);
    }

    public void randomizeNode() {
        this.setNodeSize(2 + 100 / 3 + this.rand.nextInt(2 + 100 / 3));
        if (this.rand.nextInt(10) == 0 && NodeType.nodeTypes.length > 1) {
            this.setNodeType(1 + this.rand.nextInt(NodeType.nodeTypes.length - 1));
            if (this.getNodeType() == 4 && (double)this.rand.nextFloat() < 0.33) {
                this.setNodeType(0);
            }
            //if (!Config.genTaint && this.getNodeType() == 4) {
            //    this.setNodeType(0);
            //}
        } else {
            this.setNodeType(0);
        }
        ArrayList<Aspect> al = Aspect.getPrimalAspects();
        if (this.rand.nextInt(20) == 0) {
            al = Aspect.getCompoundAspects();
        }
        this.setAspectTag(al.get(this.rand.nextInt(al.size())).getTag());
    }

    public boolean isPushedByWater() {
        return false;
    }

    public boolean isImmuneToExplosions() {
        return true;
    }

    public boolean hitByEntity(Entity entityIn) {
        return false;
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    public void addVelocity(double x, double y, double z) {
    }

    public void move(MoverType type, double x, double y, double z) {
    }

    protected boolean shouldSetPosAfterLoading() {
        return false;
    }

    private void onBroken(Entity entity) {
    }

    protected void readEntityFromNBT(NBTTagCompound tagCompound) {
        this.setNodeSize(tagCompound.getInteger("size"));
        this.setNodeType(tagCompound.getByte("type"));
        this.setAspectTag(tagCompound.getString("aspect"));
    }

    protected void writeEntityToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger("size", this.getNodeSize());
        tagCompound.setByte("type", (byte)this.getNodeType());
        tagCompound.setString("aspect", this.getAspectTag());
    }

    @SideOnly(value=Side.CLIENT)
    public void setPositionAndRotation(double p_180426_1_, double p_180426_3_, double p_180426_5_, float p_180426_7_, float p_180426_8_) {
        this.setPosition(p_180426_1_, p_180426_3_, p_180426_5_);
        this.setRotation(p_180426_7_, p_180426_8_);
    }

    @SideOnly(value=Side.CLIENT)
    public boolean canRenderOnFire() {
        return false;
    }
}
