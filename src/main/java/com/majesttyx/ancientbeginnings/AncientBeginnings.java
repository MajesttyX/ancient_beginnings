package com.majesttyx.ancientbeginnings;

import com.majesttyx.ancientbeginnings.cobblemon.CobblemonStarterEvents;
import com.majesttyx.ancientbeginnings.cobblemon.CobblemonStarterFileWriter;
import com.majesttyx.ancientbeginnings.config.AncientBeginningsConfig;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(AncientBeginnings.MODID)
public class AncientBeginnings {
    public static final String MODID = "ancientbeginnings";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AncientBeginnings() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, AncientBeginningsConfig.SPEC);

        MinecraftForge.EVENT_BUS.register(this);
        CobblemonStarterEvents.register();

        LOGGER.info("Ancient Beginnings loaded.");
    }

    @SubscribeEvent
    public void onServerAboutToStart(ServerAboutToStartEvent event) {
        CobblemonStarterFileWriter.applyCobblemonStarterConfig();
    }
}