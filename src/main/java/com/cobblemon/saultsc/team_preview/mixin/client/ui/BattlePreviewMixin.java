package com.cobblemon.saultsc.team_preview.mixin.client.ui;

import com.cobblemon.mod.common.battles.ChallengeManager;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChallengeManager.class)
public class BattlePreviewMixin {
  @Inject(method = "onAccept(Lcom/cobblemon/mod/common/battles/ChallengeManager$BattleChallenge;)V", at = @At("TAIL"), remap = false)
  private void afterAccept(ChallengeManager.BattleChallenge challenge, CallbackInfo ci) {

  }
}