package com.theo222.liminality.entities;

import com.theo222.liminality.Liminality;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.resources.ResourceLocation;

public class Entity_1_Renderer extends ZombieRenderer {
    // In our constructor, we just forward to super.
    private static final ResourceLocation MONSTER_LOCATION = ResourceLocation.fromNamespaceAndPath(Liminality.MODID, "textures/entity/1.png");
    public Entity_1_Renderer(EntityRendererProvider.Context context) {
        super(context);
    }
    public ResourceLocation getTextureLocation(ZombieRenderState p_365391_) {
        return MONSTER_LOCATION;
    }




}
