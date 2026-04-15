package com.majesttyx.ancientbeginnings.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;

public final class AncientBeginningsConfig {
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.IntValue STARTER_LEVEL;
    public static final ForgeConfigSpec.IntValue MIN_IV;
    public static final ForgeConfigSpec.IntValue MAX_IV;
    public static final ForgeConfigSpec.DoubleValue SHINY_RATE;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> KANTO_STARTERS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> HOENN_STARTERS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> SINNOH_STARTERS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> UNOVA_STARTERS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> KALOS_STARTERS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> GALAR_STARTERS;

    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> STARTER_REWARDS;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("starter_settings");

        STARTER_LEVEL = builder
                .comment("Level applied to the selected fossil starter. Cobblemon defaults to level 10.")
                .defineInRange("starterLevel", 10, 1, 100);

        MIN_IV = builder
                .comment("Minimum IV rolled per stat for the starter.")
                .defineInRange("minIV", 15, 0, 31);

        MAX_IV = builder
                .comment("Maximum IV rolled per stat for the starter. Runtime is still clamped to Cobblemon's actual IV cap.")
                .defineInRange("maxIV", 31, 0, 31);

        SHINY_RATE = builder
                .comment("1 in X chance for the selected fossil starter to be shiny. Example: 4096.0 = 1/4096. Set to 0 to disable.")
                .defineInRange("shinyRate", 4096.0D, 0.0D, 1_000_000_000.0D);

        builder.pop();

        builder.push("starter_regions");

        KANTO_STARTERS = builder
                .comment("Format: species or 'species level=10'. The addon will still force the configured level after selection.")
                .defineListAllowEmpty(
                        List.of("kanto"),
                        () -> Arrays.asList("kabuto", "omanyte", "aerodactyl"),
                        AncientBeginningsConfig::isString
                );

        HOENN_STARTERS = builder
                .defineListAllowEmpty(
                        List.of("hoenn"),
                        () -> Arrays.asList("lileep", "anorith"),
                        AncientBeginningsConfig::isString
                );

        SINNOH_STARTERS = builder
                .defineListAllowEmpty(
                        List.of("sinnoh"),
                        () -> Arrays.asList("cranidos", "shieldon"),
                        AncientBeginningsConfig::isString
                );

        UNOVA_STARTERS = builder
                .defineListAllowEmpty(
                        List.of("unova"),
                        () -> Arrays.asList("tirtouga", "archen"),
                        AncientBeginningsConfig::isString
                );

        KALOS_STARTERS = builder
                .defineListAllowEmpty(
                        List.of("kalos"),
                        () -> Arrays.asList("tyrunt", "amaura"),
                        AncientBeginningsConfig::isString
                );

        GALAR_STARTERS = builder
                .defineListAllowEmpty(
                        List.of("galar"),
                        () -> Arrays.asList("dracozolt", "arctozolt", "dracovish", "arctovish"),
                        AncientBeginningsConfig::isString
                );

        builder.pop();

        builder.push("starter_rewards");

        STARTER_REWARDS = builder
                .comment("Format: namespace:item_name*count")
                .defineListAllowEmpty(
                        List.of("items"),
                        () -> Arrays.asList(
                                "minecraft:brush*1",
                                "cobblemon:ancient_poke_ball*10",
                                "cobblemon:tumblestone*3"
                        ),
                        AncientBeginningsConfig::isString
                );

        builder.pop();

        SPEC = builder.build();
    }

    private AncientBeginningsConfig() {
    }

    private static boolean isString(Object value) {
        return value instanceof String;
    }

    public static int getStarterLevel() {
        return STARTER_LEVEL.get();
    }

    public static int getMinIv() {
        return MIN_IV.get();
    }

    public static int getMaxIv() {
        return MAX_IV.get();
    }

    public static double getShinyRate() {
        return SHINY_RATE.get();
    }

    public static List<String> getKantoStarters() {
        return castStringList(KANTO_STARTERS.get());
    }

    public static List<String> getHoennStarters() {
        return castStringList(HOENN_STARTERS.get());
    }

    public static List<String> getSinnohStarters() {
        return castStringList(SINNOH_STARTERS.get());
    }

    public static List<String> getUnovaStarters() {
        return castStringList(UNOVA_STARTERS.get());
    }

    public static List<String> getKalosStarters() {
        return castStringList(KALOS_STARTERS.get());
    }

    public static List<String> getGalarStarters() {
        return castStringList(GALAR_STARTERS.get());
    }

    public static List<String> getStarterRewards() {
        return castStringList(STARTER_REWARDS.get());
    }

    @SuppressWarnings("unchecked")
    private static List<String> castStringList(List<? extends String> list) {
        return (List<String>) list;
    }
}