package com.theo222.liminality;

import com.theo222.liminality.entities.Entity_1;
import com.theo222.liminality.entities.Entity_1_Renderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Zombie;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
@EventBusSubscriber(modid = Liminality.MODID, value = Dist.CLIENT)
public class EntityRegistries {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Liminality.MODID);
    public static final Supplier<EntityType<Entity_1>> ENTITY_1 = ENTITY_TYPES.register("protect1on_087", () ->
            EntityType.Builder.of(Entity_1::new, MobCategory.CREATURE).build(
                    ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Liminality.MODID, "protect1on_087"))));
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ENTITY_1.get(), Zombie.createAttributes().add(Attributes.ATTACK_DAMAGE, (double) 1f).add(Attributes.ATTACK_KNOCKBACK, (double) 5f).add(Attributes.ATTACK_SPEED, (double) 999f).add(Attributes.KNOCKBACK_RESISTANCE, (double) 1).add(Attributes.MAX_HEALTH, (double) 80f).add(Attributes.MOVEMENT_EFFICIENCY, (double) 1f).add(Attributes.BURNING_TIME, (double) 0f).add(Attributes.SAFE_FALL_DISTANCE, (double) 999f).add(Attributes.STEP_HEIGHT, (double) 1f).add(Attributes.ENTITY_INTERACTION_RANGE, (double) 4f).add(Attributes.ARMOR, (double) 8f).build());
    }
    @SubscribeEvent // on the mod event bus only on the physical client
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistries.ENTITY_1.get(), Entity_1_Renderer::new);
    }
}
