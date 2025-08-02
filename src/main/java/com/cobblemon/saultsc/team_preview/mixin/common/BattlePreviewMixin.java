package com.cobblemon.saultsc.team_preview.mixin.common;

import com.cobblemon.mod.common.battles.ChallengeManager;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChallengeManager.class, remap = false)
public abstract class BattlePreviewMixin {

    @Inject(method = "onAccept(Lcom/cobblemon/mod/common/battles/ChallengeManager$BattleChallenge;)V", at = @At("HEAD"), cancellable = true)
    private void onAccept(ChallengeManager.BattleChallenge challenge, @NotNull CallbackInfo ci) {
        ci.cancel();
    }
}