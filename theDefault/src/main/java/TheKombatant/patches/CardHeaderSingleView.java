package TheKombatant.patches;

import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.cards.AbstractKombatCard;
import TheKombatant.cards.CardHeader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import java.util.*;

@SpirePatch(
        clz= SingleCardViewPopup.class,
        method="renderTitle"
)

public class CardHeaderSingleView {
    @SpireInsertPatch(
            rloc = 0,
            localvars={"card"}
    )
    public static void Insert(final SingleCardViewPopup __instance, final SpriteBatch sb, final AbstractCard card) {
        if (card instanceof AbstractKombatCard){
            if ((((AbstractKombatCard) card).HasCardHeader(card))){
        if (card != null && !card.isFlipped) {
            ((AbstractKombatCard) card).renderInSingleCardPopup(sb);
        }
            }
        }
    }
}


/*
    in  private void updateUpgradePreview() {
    
    private void loadPortraitImg() {
        if (Settings.PLAYTESTER_ART_MODE || UnlockTracker.betaCardPref.getBoolean(this.card.cardID, false)) {
            this.portraitImg = ImageMaster.loadImage("images/1024PortraitsBeta/" + this.card.assetUrl + ".png");
        }
        else {
            this.portraitImg = ImageMaster.loadImage("images/1024Portraits/" + this.card.assetUrl + ".png");
            if (this.portraitImg == null) {
                this.portraitImg = ImageMaster.loadImage("images/1024PortraitsBeta/" + this.card.assetUrl + ".png");
            }
        }
    }
 */