package com.majesttyx.ancientbeginnings.mixin;

import com.cobblemon.mod.common.client.gui.startselection.StarterSelectionScreen;
import com.cobblemon.mod.common.client.gui.startselection.widgets.preview.StarterRoundabout;
import com.cobblemon.mod.common.config.starter.RenderableStarterCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StarterSelectionScreen.class)
public abstract class StarterSelectionScreenMixin {
    @Shadow
    private RenderableStarterCategory currentCategory;

    @Shadow
    private StarterRoundabout starterRoundaboutLeft;

    @Shadow
    private StarterRoundabout starterRoundaboutRight;

    @Inject(method = "m_7856_", at = @At("TAIL"))
    private void ancientbeginnings$afterInit(CallbackInfo ci) {
        ancientbeginnings$applyTwoStarterLayout();
    }

    @Inject(method = "updateSelection", at = @At("TAIL"))
    private void ancientbeginnings$afterUpdateSelection(CallbackInfo ci) {
        ancientbeginnings$applyTwoStarterLayout();
    }

    @Unique
    private void ancientbeginnings$applyTwoStarterLayout() {
        if (this.currentCategory == null || this.currentCategory.getPokemon() == null) {
            return;
        }

        int size = this.currentCategory.getPokemon().size();

        if (size == 2) {
            if (this.starterRoundaboutLeft != null) {
                this.starterRoundaboutLeft.visible = false;
                this.starterRoundaboutLeft.active = false;
            }

            if (this.starterRoundaboutRight != null) {
                this.starterRoundaboutRight.visible = true;
                this.starterRoundaboutRight.active = true;
            }
            return;
        }

        if (this.starterRoundaboutLeft != null) {
            this.starterRoundaboutLeft.visible = true;
            this.starterRoundaboutLeft.active = true;
        }

        if (this.starterRoundaboutRight != null) {
            this.starterRoundaboutRight.visible = true;
            this.starterRoundaboutRight.active = true;
        }
    }
}