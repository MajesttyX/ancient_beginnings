package com.majesttyx.ancientbeginnings.cobblemon;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.config.starter.StarterCategory;
import com.cobblemon.mod.common.config.starter.StarterConfig;
import com.majesttyx.ancientbeginnings.AncientBeginnings;
import com.majesttyx.ancientbeginnings.config.AncientBeginningsConfig;

import java.util.ArrayList;
import java.util.List;

public final class CobblemonStarterFileWriter {
    private CobblemonStarterFileWriter() {
    }

    public static void applyCobblemonStarterConfig() {
        try {
            StarterConfig starterConfig = new StarterConfig();
            starterConfig.setAllowStarterOnJoin(true);
            starterConfig.setPromptStarterOnceOnly(true);
            starterConfig.setStarters(buildStarterCategories());

            Cobblemon.INSTANCE.setStarterConfig(starterConfig);

            AncientBeginnings.LOGGER.info(
                    "Ancient Beginnings replaced Cobblemon starter config in memory with {} fossil regions.",
                    starterConfig.getStarters().size()
            );
        } catch (Exception e) {
            AncientBeginnings.LOGGER.error("Failed to apply Ancient Beginnings starter config to Cobblemon.", e);
        }
    }

    private static List<StarterCategory> buildStarterCategories() {
        List<StarterCategory> categories = new ArrayList<>();

        addCategory(categories, "kanto", "Kanto", AncientBeginningsConfig.getKantoStarters());
        addCategory(categories, "hoenn", "Hoenn", AncientBeginningsConfig.getHoennStarters());
        addCategory(categories, "sinnoh", "Sinnoh", AncientBeginningsConfig.getSinnohStarters());
        addCategory(categories, "unova", "Unova", AncientBeginningsConfig.getUnovaStarters());
        addCategory(categories, "kalos", "Kalos", AncientBeginningsConfig.getKalosStarters());
        addCategory(categories, "galar", "Galar", AncientBeginningsConfig.getGalarStarters());

        return categories;
    }

    private static void addCategory(List<StarterCategory> categories, String name, String displayName, List<String> entries) {
        if (entries == null || entries.isEmpty()) {
            return;
        }

        List<PokemonProperties> pokemon = new ArrayList<>();
        for (String entry : entries) {
            String normalized = normalizePokemonEntry(entry);
            pokemon.add(PokemonProperties.Companion.parse(normalized));
        }

        categories.add(new StarterCategory(name, displayName, pokemon));
    }

    private static String normalizePokemonEntry(String entry) {
        if (entry == null) {
            return "";
        }

        String trimmed = entry.trim();
        if (trimmed.isEmpty()) {
            return trimmed;
        }

        if (trimmed.contains(" level=")) {
            return trimmed;
        }

        return trimmed + " level=" + AncientBeginningsConfig.getStarterLevel();
    }
}