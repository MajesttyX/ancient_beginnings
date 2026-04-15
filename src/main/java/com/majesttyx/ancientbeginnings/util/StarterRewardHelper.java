package com.majesttyx.ancientbeginnings.util;

import com.majesttyx.ancientbeginnings.AncientBeginnings;
import com.majesttyx.ancientbeginnings.config.AncientBeginningsConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public final class StarterRewardHelper {
    private StarterRewardHelper() {
    }

    public static void giveConfiguredRewards(ServerPlayer player) {
        List<String> rewards = AncientBeginningsConfig.getStarterRewards();

        for (String reward : rewards) {
            if (reward == null || reward.isBlank()) {
                continue;
            }

            ParsedReward parsed = parseReward(reward);
            if (parsed == null) {
                AncientBeginnings.LOGGER.warn("Skipping invalid Ancient Beginnings starter reward entry: {}", reward);
                continue;
            }

            Item item = ForgeRegistries.ITEMS.getValue(parsed.itemId);
            if (item == null) {
                AncientBeginnings.LOGGER.warn("Could not find starter reward item {}", parsed.itemId);
                continue;
            }

            ItemStack stack = new ItemStack(item, parsed.count);
            boolean added = player.getInventory().add(stack);

            if (!added || !stack.isEmpty()) {
                player.drop(stack.copy(), false);
            }
        }
    }

    private static ParsedReward parseReward(String raw) {
        String trimmed = raw.trim();

        String[] split = trimmed.split("\\*", 2);
        String idPart = split[0].trim();
        int count = 1;

        if (split.length == 2) {
            try {
                count = Integer.parseInt(split[1].trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }

        if (count <= 0) {
            return null;
        }

        ResourceLocation itemId = ResourceLocation.tryParse(idPart);
        if (itemId == null) {
            return null;
        }

        return new ParsedReward(itemId, count);
    }

    private record ParsedReward(ResourceLocation itemId, int count) {
    }
}