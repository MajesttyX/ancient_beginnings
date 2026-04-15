package com.majesttyx.ancientbeginnings.cobblemon;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.cobblemon.mod.common.api.events.starter.StarterChosenEvent;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.pokemon.IVs;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.majesttyx.ancientbeginnings.AncientBeginnings;
import com.majesttyx.ancientbeginnings.config.AncientBeginningsConfig;
import com.majesttyx.ancientbeginnings.util.StarterRewardHelper;
import kotlin.Unit;
import net.minecraft.server.level.ServerPlayer;

import java.util.concurrent.ThreadLocalRandom;

public final class CobblemonStarterEvents {
    private CobblemonStarterEvents() {
    }

    public static void register() {
        CobblemonEvents.STARTER_CHOSEN.subscribe(Priority.NORMAL, CobblemonStarterEvents::onStarterChosen);
    }

    private static Unit onStarterChosen(StarterChosenEvent event) {
        try {
            ServerPlayer player = event.getPlayer();
            Pokemon pokemon = event.getPokemon();

            if (player == null || pokemon == null) {
                AncientBeginnings.LOGGER.warn("Ancient Beginnings could not resolve player or pokemon from STARTER_CHOSEN event.");
                return Unit.INSTANCE;
            }

            applyConfiguredLevel(pokemon);
            applyConfiguredIvs(pokemon);
            applyConfiguredShinyChance(pokemon);
            StarterRewardHelper.giveConfiguredRewards(player);

            AncientBeginnings.LOGGER.info(
                    "Applied Ancient Beginnings starter rules for player {} and starter {}.",
                    player.getGameProfile().getName(),
                    pokemon.getSpecies().getName()
            );
        } catch (Exception e) {
            AncientBeginnings.LOGGER.error("Failed handling Cobblemon STARTER_CHOSEN event in Ancient Beginnings.", e);
        }

        return Unit.INSTANCE;
    }

    private static void applyConfiguredLevel(Pokemon pokemon) {
        pokemon.setLevel(AncientBeginningsConfig.getStarterLevel());
    }

    private static void applyConfiguredIvs(Pokemon pokemon) {
        int cobblemonMaxIv = IVs.MAX_VALUE;
        int configuredMin = AncientBeginningsConfig.getMinIv();
        int configuredMax = AncientBeginningsConfig.getMaxIv();

        int min = clamp(configuredMin, 0, cobblemonMaxIv);
        int max = clamp(configuredMax, 0, cobblemonMaxIv);

        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }

        pokemon.setIV(Stats.HP, rollInclusive(min, max));
        pokemon.setIV(Stats.ATTACK, rollInclusive(min, max));
        pokemon.setIV(Stats.DEFENCE, rollInclusive(min, max));
        pokemon.setIV(Stats.SPECIAL_ATTACK, rollInclusive(min, max));
        pokemon.setIV(Stats.SPECIAL_DEFENCE, rollInclusive(min, max));
        pokemon.setIV(Stats.SPEED, rollInclusive(min, max));
    }

    private static void applyConfiguredShinyChance(Pokemon pokemon) {
        double shinyRate = AncientBeginningsConfig.getShinyRate();
        if (shinyRate <= 0.0D) {
            return;
        }

        boolean makeShiny = ThreadLocalRandom.current().nextDouble() < (1.0D / shinyRate);
        if (makeShiny) {
            pokemon.setShiny(true);
        }
    }

    private static int rollInclusive(int min, int max) {
        if (min == max) {
            return min;
        }
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}