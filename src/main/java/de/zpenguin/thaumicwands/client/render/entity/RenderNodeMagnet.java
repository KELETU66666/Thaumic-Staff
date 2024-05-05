
package de.zpenguin.thaumicwands.client.render.entity;

import de.zpenguin.thaumicwands.client.render.ModelNodeMagnet;
import de.zpenguin.thaumicwands.entity.EntityNodeMagnet;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
public class RenderNodeMagnet extends RenderLiving<EntityNodeMagnet> {
    private static final ResourceLocation rl = new ResourceLocation("thaumicwands", "textures/models/nodemagnet.png");

    public RenderNodeMagnet(RenderManager rm) {
        super(rm, new ModelNodeMagnet(), 0.5f);
    }

    protected ResourceLocation getEntityTexture(EntityNodeMagnet entity) {
        return rl;
    }
}
