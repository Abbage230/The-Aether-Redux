package net.zepalesque.redux.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.zepalesque.redux.config.enums.MimicModelType;
import net.zepalesque.redux.config.enums.QuicksoilSetting;
import net.zepalesque.redux.config.enums.MoaFeetType;
import net.zepalesque.redux.config.enums.SpawnerType;
import net.zepalesque.redux.config.enums.dungeon.BossRoomType;
import net.zepalesque.redux.config.enums.dungeon.ChestRoomType;
import net.zepalesque.redux.config.enums.dungeon.LobbyType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ReduxConfig {

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    public ReduxConfig() {
    }

    static {
        Pair<Common, ForgeConfigSpec> commonSpecPair = (new ForgeConfigSpec.Builder()).configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

        Pair<Client, ForgeConfigSpec> clientSpecPair = (new ForgeConfigSpec.Builder()).configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }

    public static class Common {

        public final ForgeConfigSpec.BooleanValue improved_cockatrice_behavior;
        public final ForgeConfigSpec.BooleanValue cockatrice_burn_in_daylight;
        public final ForgeConfigSpec.BooleanValue pl_swet_behavior;
        public final ForgeConfigSpec.BooleanValue nerf_gummy_swets;
        public final ForgeConfigSpec.EnumValue<QuicksoilSetting> quicksoil_movement_system;
        public final ForgeConfigSpec.BooleanValue mobs_avoid_quicksoil;
        public final ForgeConfigSpec.BooleanValue mossy_holystone_ores;
        public final ForgeConfigSpec.BooleanValue smaller_mimic_hitbox;
        public final ForgeConfigSpec.EnumValue<SpawnerType> genesis_spawner_mobs;
        public final ForgeConfigSpec.BooleanValue consistent_mine_speeds;

        public final ForgeConfigSpec.BooleanValue auto_reset_biome_values;
        public final ForgeConfigSpec.ConfigValue<Integer> region_size;
        public final ForgeConfigSpec.BooleanValue enable_the_blight;
        public final ForgeConfigSpec.BooleanValue enable_gilded_biomes;
        public final ForgeConfigSpec.BooleanValue enable_snowy_biomes;
        public final ForgeConfigSpec.BooleanValue enable_skyfields;
        public final ForgeConfigSpec.BooleanValue enable_cloudcaps;
        public final ForgeConfigSpec.BooleanValue enable_skyroot_shrublands;

        public final ForgeConfigSpec.BooleanValue change_double_plant_hitbox;
        public final ForgeConfigSpec.BooleanValue better_conversion_sounds;
        public final ForgeConfigSpec.BooleanValue raw_ores;
        public final ForgeConfigSpec.BooleanValue gravitite_ingot;
        public final ForgeConfigSpec.BooleanValue dungeon_stone_recipes;
        public final ForgeConfigSpec.BooleanValue enchanted_gilded_grass;
        public final ForgeConfigSpec.BooleanValue better_water_color;
        public final ForgeConfigSpec.BooleanValue enchanted_vines;
        public final ForgeConfigSpec.BooleanValue wall_roots;
        public final ForgeConfigSpec.BooleanValue alternate_gilded_trees;
        public final ForgeConfigSpec.BooleanValue enderman_spawns;

        public final ForgeConfigSpec.EnumValue<BossRoomType> bronze_boss_room;
        public final ForgeConfigSpec.EnumValue<ChestRoomType> bronze_chest_room;
        public final ForgeConfigSpec.EnumValue<LobbyType> bronze_lobby;

        public final ForgeConfigSpec.BooleanValue apply_cloud_layer_pack;

        public final ForgeConfigSpec.DoubleValue cloud_layer_threshold_min;
        public final ForgeConfigSpec.DoubleValue cloud_layer_threshold_max;

        public final ForgeConfigSpec.BooleanValue vanilla_swets;

        // TODO: organize

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Gameplay Changes");
            builder.push("Existing Mob Changes");
            this.improved_cockatrice_behavior = builder.comment("Makes Cockatrices shoot at you and chase you if they hit you. Also disables Cockatrice spawns in the Blight. Requires world restart to refresh existing mob AI.").worldRestart().define("Improved Cockatrice Behavior", true);
            this.cockatrice_burn_in_daylight = builder.comment("Makes Cockatrices burn in daylight. Requires world restart to refresh existing mob AI.").worldRestart().define("Cockatrices burn in daylight", false);
            this.pl_swet_behavior = builder.comment("Gives Swets less irritating behavior, based on their behavior in older builds of the Paradise Lost mod. Requires world restart to refresh existing mob AI.").worldRestart().define("PL-Like Swet Behavior", true);
            this.smaller_mimic_hitbox = builder.comment("Decreases the size of mimics. Intended to be used with the client-side model. Requires an MC restart").define("Smaller Mimic Hitbox", true);
            this.mobs_avoid_quicksoil = builder.comment("Causes mobs to try to avoid walking on quicksoil.").define("Mobs Avoid Quicksoil", true);
            builder.pop();
            builder.push("Mob Spawns");
            this.enderman_spawns = builder.comment("Allows Endermen to spawn in the Aether").worldRestart().define("Enderman Spawns", false);
            this.vanilla_swets = builder.comment("Allows Vanilla Swets to spawn in the Aether").worldRestart().define("Vanilla Swet Spawns", true);
            builder.pop();
            builder.push("Existing Content Alterations");
            this.nerf_gummy_swets = builder.comment("Nerfs Gummy Swets, but also makes them craftable. Note: Requires the Aether's Gummy Swet Healing config to be false to properly nerf the hunger effect. Requires datapack reload.").worldRestart().define("Nerf Gummy Swets", true);
            this.consistent_mine_speeds = builder.comment("Makes some Aether blocks take a bit longer to break, as they are extremely quick currently").define("Consistent Mining Speeds", false);
            this.quicksoil_movement_system = builder.comment("Changes quicksoil between various behaviors. Genesis is the most chaotic, highlands is the most fine-tuned, classic is the original").defineEnum("Quicksoil Movement System", QuicksoilSetting.highlands);
            this.gravitite_ingot = builder.comment("Replaces Enchanted Gravitite with Gravitite Ingots. The blocks of this have the original floating behavior.").worldRestart().define("Gravitite Ingots", true);
            this.dungeon_stone_recipes = builder.comment("Makes Light Dungeon stones use better recipes").worldRestart().define("Better Light Dungeon Stone Recipes", true);
            builder.pop();
            builder.push("Additions");
            this.raw_ores = builder.comment("Use Raw ores for different Aether metals.").define("Raw Ores", true);
            builder.pop();
            builder.push("Misc");
            this.change_double_plant_hitbox = builder.comment("Changes the hitboxes of vanilla double plants. Disable if it causes odd behavior.").define("Change Double Plant Hitboxes", true);
            this.better_conversion_sounds = builder.comment("Makes blockstate conversion sounds (ambrosium, swet ball) better").define("Better Conversion Sounds", true);
            builder.pop(2);

            builder.push("Datapack Application");
            this.apply_cloud_layer_pack = builder.comment("Whether or not to automatically add the Cloud Layer datapack.").define("Cloud Layer Datapack", true);
            builder.pop();

            builder.push("Worldgen");
            builder.push("Additional Features");
            this.mossy_holystone_ores = builder.comment("Enables Mossy Holystone as an ore. Configurable so that if you disable it, it's easier to tell if you've come across a dungeon.").worldRestart().define("Mossy Holystone Ores", true);
            this.enchanted_vines = builder.comment("Enables Enchanted/Gilded vines on trees").worldRestart().define("Enable Enchanted and Gilded Vines", true);
            this.wall_roots = builder.comment("Enables roots made of logs and log walls on the sides of Large Cloudcap mushrooms and Blightwillow trees.").worldRestart().define("Wall Roots on Blightwillows and Large Cloudcaps", true);
            this.alternate_gilded_trees = builder.comment("Uses an alternate Gilded Oak shape, more like vanilla Oak trees and Skyroots, for the Gilded biomes.").worldRestart().define("Alternate Gilded Trees", false);
            builder.pop();

            builder.push("Cloud Layer");
            this.cloud_layer_threshold_min = builder.comment("Minimum value for the cloud layer's noise threshold").defineInRange("Cloud Layer Threshold Min", 0D, -4D, 4D);
            this.cloud_layer_threshold_max = builder.comment("Maximum value for the cloud layer's noise threshold").defineInRange("Cloud Layer Threshold Max", 1D, -4D, 4D);
            builder.pop();

            builder.push("Bronze Dungeon");
            this.genesis_spawner_mobs = builder.comment("Adds dungeon mobs from the Aether: Genesis to the spawners added to the Bronze Dungeon if it is installed").defineEnum("Genesis Mobs in Spawners", SpawnerType.all);
            this.bronze_boss_room = builder.comment("Which type of Bronze Dungeon Boss Room to use").defineEnum("Bronze Boss Room Type", BossRoomType.vault);
            this.bronze_chest_room = builder.comment("Which type of Bronze Dungeon Chest Room to use").defineEnum("Bronze Chest Room Type", ChestRoomType.pillars);
            this.bronze_lobby = builder.comment("Which type of Bronze Dungeon Lobby Room to use").defineEnum("Bronze Lobby Type", LobbyType.doors);
            builder.pop();

            builder.push("Biomes");
            this.auto_reset_biome_values = builder.comment("When enabled, any biome value config changes will be reset. It is recommended that you disable this if you want to keep any custom biome parameters you may have set.").define("Auto-Reset Biome Values", true);
            this.region_size = builder.comment("The value of Redux's biome region size. Larger values correspond to larger biome clumps").define("Region Size", 20);
            this.better_water_color = builder.comment("Improves the water color of Aether biomes, making the color match the rest of the dimension better").define("Better Water Color", true);

            this.enable_the_blight = builder.comment("Enables the Blight biome.").worldRestart().define("Enable the Blight", true);
            this.enable_gilded_biomes = builder.comment("Enables the Gilded Groves and Gilded Grasslands biomes.").worldRestart().define("Enable Gilded Biomes", true);
            this.enable_snowy_biomes = builder.comment("Enables the Frosted Tundra and Glacial Tagia biomes.").worldRestart().define("Enable Snowy Biomes", true);
            this.enable_skyfields = builder.comment("Enables the Skyfields biome.").worldRestart().define("Enable Skyfields", true);
            this.enable_cloudcaps = builder.comment("Enables the Cloudcaps biome.").worldRestart().define("Enable the Cloudcaps", true);
            this.enable_skyroot_shrublands = builder.comment("Enables the Skyroot Shrublands biome.").worldRestart().define("Enable Skyroot Shrublands", true);
            this.enchanted_gilded_grass = builder.comment("Uses Enchanted Aether Grass for the ground in the Gilded Groves. May require a Minecraft restart.").worldRestart().define("Enchanted Gilded Grass", true);
            builder.pop(2);
        }
    }

    public static class Client {

        public final ForgeConfigSpec.BooleanValue moa_model_upgrade;
        public final ForgeConfigSpec.EnumValue<MoaFeetType> moa_feet_type;
        public final ForgeConfigSpec.BooleanValue cockatrice_model_upgrade;
        public final ForgeConfigSpec.BooleanValue first_startup;
        public final ForgeConfigSpec.EnumValue<MimicModelType> mimic_model_upgrade;
        public final ForgeConfigSpec.BooleanValue mimic_slam_sound;
        public final ForgeConfigSpec.BooleanValue sentry_model_upgrade;
        public final ForgeConfigSpec.BooleanValue sheepuff_model_upgrade;
        public final ForgeConfigSpec.BooleanValue phyg_model_upgrade;
        public final ForgeConfigSpec.BooleanValue flying_cow_model_upgrade;
        public final ForgeConfigSpec.ConfigValue<String> version_id;
        public final ForgeConfigSpec.BooleanValue better_leaf_particles;
        public final ForgeConfigSpec.BooleanValue enable_adrenaline_postproccess;
        public final ForgeConfigSpec.BooleanValue fix_biome_modifier_bug;
        public final ForgeConfigSpec.BooleanValue cycle_menu;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push(List.of("Rendering", "Mob Model Upgrades"));
            builder.push("Moa");
            this.moa_model_upgrade = builder.comment("Makes Moas resemble their Mutation models").define("Moa Model Upgrade", true);
            this.moa_feet_type = builder.comment("Determines whether the updated Moa model should use more peaceful-appearing toes, or more aggressive talons").defineEnum("Moa Foot Type", MoaFeetType.toes);
            builder.pop();
            this.cockatrice_model_upgrade = builder.comment("Makes Cockatrices look far better").define("Cockatrice Model Upgrade", true);
            this.sentry_model_upgrade = builder.comment("Improves the model for Sentries, giving them a new model and a springy jump animation").define("Sentry Model Upgrade", true);
            this.mimic_model_upgrade = builder.comment("Whether or not to use the upgraded Mimic model. Note that unless you use sync_with_server, there may be hitbox differences.").defineEnum("Mimic Model Upgrade", MimicModelType.sync_with_server);
            this.sheepuff_model_upgrade = builder.comment("Gives the Sheepuff's model some touchups and improvements").define("Sheepuff Model Upgrade", true);
            this.phyg_model_upgrade = builder.comment("Gives the Phyg's model some touchups and improvements").define("Phyg Model Upgrade", true);
            this.flying_cow_model_upgrade = builder.comment("Gives the Flying Cow's model some touchups and improvements").define("Flying Cow Model Upgrade", true);
            builder.pop();
            builder.push("Shader FX");
            this.enable_adrenaline_postproccess = builder.comment("Enables a post processing effect for the Shroom Ring's adrenaline ability. Only works with Fabulous graphics, and will cause issues when underwater (even when the ability is not active)").define("Enable Adrenaline Post Processing Effect", false);
            builder.pop();
            builder.push("GUI");
            this.cycle_menu = builder.comment("Cycles between Redux's menus.").define("Cycle Menu", true);
            builder.pop();
            builder.push("Particles");
            this.better_leaf_particles = builder.comment("Improves the leaf particles for Golden Oaks, Gilded Oaks, and Crystal Trees, based on Minecraft 1.20's new cherry tree particles.").define("Better Leaf Particles", true);
            builder.pop(2);
            builder.push("Fixes");
            this.fix_biome_modifier_bug = builder.comment("Fixes a forge/neoforge bug causing biome modifiers to not be able to change grass colors, so Redux can have vanilla grass use vanilla colors in the Aether").define("Fix Biome Modifier Grass Bug", true);
            builder.pop();
            builder.push("Audio");
            this.mimic_slam_sound = builder.comment("Enables a slamming sound for mimics when using the new model. Disable if it gets too annoying XD").define("Mimic Slam Sound Effect", true);
            builder.pop();
            builder.push("Internal");
            this.first_startup = builder.comment("Internal value to decide if it is the first startup. DO NOT EDIT THIS VALUE, unless you want things to break!").define("First Startup?", true);
            this.version_id = builder.comment("Value used to track the version of the Aether: Redux most recently used.").define("Version ID", "undefined");
            builder.pop();
        }
    }
}
