package saultsc.cobblemon.team.preview.mixin.client;

import com.cobblemon.mod.common.client.battle.ClientBattleChallenge;
import com.cobblemon.mod.common.client.gui.interact.battleRequest.BattleConfigureGUI;
import com.cobblemon.mod.common.net.messages.client.PlayerInteractOptionsPacket;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import saultsc.cobblemon.team.preview.battle.PreviewFormats;
import saultsc.cobblemon.team.preview.client.ChallengePageNavigation;
import saultsc.cobblemon.team.preview.client.ChallengeScreenHooks;
import saultsc.cobblemon.team.preview.client.PreviewVariantHolder;

import java.util.List;

/**
 * Gives the Singles and Doubles pages of Cobblemon's challenge screen a second "preview" page.
 */
@Mixin(value = BattleConfigureGUI.class, remap = false)
public abstract class BattleConfigureGUIMixin implements PreviewVariantHolder {
    @Shadow
    private int currentPage;

    @Shadow
    @Final
    @Nullable
    private ClientBattleChallenge activeRequest;

    @Shadow
    private static List<? extends PlayerInteractOptionsPacket.Options> options;

    @Unique
    private boolean cobblemon_team_preview$previewVariant;

    @Unique
    private boolean cobblemon_team_preview$enterVariantAfterMove;

    @Override
    public boolean isTeamPreviewVariant() {
        // When answering a challenge the variant is whatever the sender chose.
        if (this.activeRequest != null) {
            return PreviewFormats.INSTANCE.of(this.activeRequest.getBattleFormat()) != null;
        }
        return this.cobblemon_team_preview$previewVariant;
    }

    @Inject(method = "setCurrentPage", at = @At("HEAD"), cancellable = true)
    private void cobblemon_team_preview$beforePageChange(int value, CallbackInfo ci) {
        ChallengePageNavigation.PageChange change = ChallengePageNavigation.INSTANCE.onPageChange(
            this.currentPage,
            value,
            options.size(),
            this.cobblemon_team_preview$currentPageSupportsPreview(),
            this.cobblemon_team_preview$previewVariant,
            ChallengeScreenHooks.INSTANCE.isMouseOnLeftHalf()
        );

        this.cobblemon_team_preview$previewVariant = change.getPreviewVariant();
        this.cobblemon_team_preview$enterVariantAfterMove = change.getEnterVariantAfterMove();
        if (change.getCancelMove()) {
            ci.cancel();
        }
    }

    @Inject(method = "setCurrentPage", at = @At("RETURN"))
    private void cobblemon_team_preview$afterPageChange(int value, CallbackInfo ci) {
        if (this.cobblemon_team_preview$enterVariantAfterMove) {
            this.cobblemon_team_preview$enterVariantAfterMove = false;
            this.cobblemon_team_preview$previewVariant = this.cobblemon_team_preview$currentPageSupportsPreview();
        }
    }

    @Unique
    private boolean cobblemon_team_preview$currentPageSupportsPreview() {
        if (this.currentPage < 0 || this.currentPage >= options.size()) {
            return false;
        }
        return ChallengeScreenHooks.INSTANCE.previewFormatOf(options.get(this.currentPage)) != null;
    }
}
