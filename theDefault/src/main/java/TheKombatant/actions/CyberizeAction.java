package TheKombatant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class CyberizeAction
        extends AbstractGameAction
{
    //private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RecycleAction");
    //public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private int cardnum = 0;
    public CyberizeAction()
    {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {

        final int count = AbstractDungeon.player.hand.size();
        for (int i = 0; i < count; ++i) {
            cardnum++;
        }
        for (int i = 0; i < count; ++i) {
            if (Settings.FAST_MODE) {
                this.addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST));
            } else {
                this.addToTop(new ExhaustAction(1, true, true));
            }
        }
        this.addToTop(new DrawCardAction(this.target, count));

        this.addToBot(new UpgradeandAddNewAction(AbstractDungeon.player.hand));


        this.isDone = true;
    }
}