package com.theo222.liminality;

import com.theo222.liminality.entities.TestEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class EntityRegistries {
    /*
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Liminality.MODID);

    public static final Supplier<EntityType<TestEntity>> TEST = ENTITY_TYPES.register("test_entity", () ->
            EntityType.Builder.of(TestEntity::new, MobCategory.CREATURE).build(
                    ResourceKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(Liminality.MODID, "test_entity"))));
     */
}
