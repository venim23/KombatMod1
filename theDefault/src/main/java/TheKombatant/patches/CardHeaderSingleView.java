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

@SpirePatch(
        clz= SingleCardViewPopup.class,
        method="renderTitle"
)

public class CardHeaderSingleView {
    @SpireInsertPatch(
            rloc = 1,
            localvars={"card"}
    )
    public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard card)
    {
        CardHeader cHeader;
        String headerplswork;

        if (card instanceof AbstractKombatCard){
            if ((((AbstractKombatCard) card).HasCardHeader(card))){
                cHeader = ((AbstractKombatCard) card).GetCardHeader();
                headerplswork = cHeader.NAME;
                FontHelper.renderFontCentered(sb, FontHelper.SCP_cardTitleFont_small, headerplswork, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F + 400.0F * Settings.scale, Color.GOLD );

            }

        }

    }
}
