package com.theo222.liminality;

import com.mojang.brigadier.StringReader;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Random;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Liminality.MODID)
public class Liminality {
    public boolean firsttick = false;
    // Define mod id in a common place for everything to reference
    public static final String MODID = "liminality";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "liminality" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "liminality" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "liminality" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, MODID);

    public static final Supplier<SoundEvent> CAVESOUND = SOUND_EVENTS.register("cave_sound_main", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MODID, "cave_sound_main")));

    /*
    // Creates a new Block with the id "liminality:example_block", combining the namespace and path
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", p -> p.mapColor(MapColor.STONE));
    // Creates a new BlockItem with the id "liminality:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    // Creates a new food item with the id "liminality:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", p -> p.food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    // Creates a creative tab with the id "liminality:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.liminality")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get());
                output.accept(EXAMPLE_BLOCK_ITEM.get());// Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());
    */

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Liminality(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        SOUND_EVENTS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);



        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // So setup code
        //LOGGER.info(String.valueOf(Config.LOG_DIRT_BLOCK.isTrue()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            //event.accept(EXAMPLE_BLOCK_ITEM);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call !!!!
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        firsttick = true;
        //event.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> serverPlayer.setGameMode(GameType.SURVIVAL));
        LiminalitySavedData save = event.getServer().overworld().getDataStorage().computeIfAbsent(LiminalitySavedData.ID);
        if (save.HallucinationChance == -1) {
            save.HallucinationChance = Config.HALLUCINATION_CHANCE.getRaw();
        }
        LOGGER.info("TS server is ahh");
    }
    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Pre event) {
        LiminalitySavedData save = event.getServer().overworld().getDataStorage().computeIfAbsent(LiminalitySavedData.ID);
        if (firsttick && !Config.SANDBOX.isTrue()) {
            firsttick = false;
            event.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> serverPlayer.setGameMode(GameType.SURVIVAL));
            event.getServer().enforceGameTypeForPlayers(GameType.SURVIVAL);
        }

        save.Ticks += 1;
        save.foo();
        if (((double) save.Ticks / 100) == (Math.floor(((double) save.Ticks / 100)))) {
            int the_random_value = new Random().nextInt((99) + 1) + 1;
            if (the_random_value < (save.HallucinationChance + 1)) {
                LOGGER.info("dih: " + String.valueOf(the_random_value));
                event.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> serverPlayer.playSound(CAVESOUND.get()));
                //event.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> serverPlayer.setHealth(0));
            }
            else {
                save.HallucinationChance += 3;
                save.foo();
                LOGGER.info("clih: " + String.valueOf(save.HallucinationChance));
            }

        }
        //LOGGER.info("ticks: " + String.valueOf(save.Ticks));


    }
    @SubscribeEvent
    public void onPlayerChangeGameMode(PlayerEvent.PlayerChangeGameModeEvent event) {
        if (event.getNewGameMode() != GameType.SURVIVAL && !Config.SANDBOX.isTrue()) {
            event.setNewGameMode(GameType.SURVIVAL);
        }
    }
    @SubscribeEvent
    public void onPlayerLogOn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        if (serverPlayer.gameMode() != GameType.SURVIVAL && !Config.SANDBOX.isTrue()) return;
        serverPlayer.setGameMode(GameType.SURVIVAL);
    }



}
