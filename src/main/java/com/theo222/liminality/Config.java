package com.theo222.liminality;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import javax.swing.plaf.SliderUI;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec.ConfigValue<Integer> DREAM_CHANCE = BUILDER
            .comment("The chance you will have a dream.(Reroll upon sleeping)")
            .defineInRange("DreamChance", 30, 0, 100);
    public static final ModConfigSpec.ConfigValue<Integer> HALLUCINATION_CHANCE = BUILDER
            .comment("The chance for you to start seeing things that arent there.(Reroll every 2 minutes)")
            .defineInRange("HallucinationChance", 20, 0, 100);
    public static final ModConfigSpec.BooleanValue STARTER_PILLS = BUILDER
            .comment("Whether to give you a couple of pills on the start of a new world.(Makes the game easier but not optimal for content creators)")
            .define("GivePills", false);
    public static final ModConfigSpec.BooleanValue SANDBOX = BUILDER
            .comment("Whether to allow gamemode switching(dont turn this on unless yourve seen it all)")
            .define("AllowGameModes", true);
    public static final ModConfigSpec.BooleanValue DEBUG = BUILDER
            .comment("Some additional info")
            .define("Debug", true);

    /*
    public static final ModConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
            .comment("Whether to log the dirt block on common setup")
            .define("logDirtBlock", true);


    public static final ModConfigSpec.IntValue MAGIC_NUMBER = BUILDER
            .comment("A magic number")
            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

    // a list of strings that are treated as resource locations for items
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);
    */

    static final ModConfigSpec SPEC = BUILDER.build();
}
