package TheKombatant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class KenshiReflectAction
        extends AbstractGameAction
{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RecycleAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private int blockPer;
    public KenshiReflectAction(int BlockPer)
    {

        this.blockPer = BlockPer;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if (this.duration == Settings.ACTION_DUR_FAST)
        {
            if (this.p.hand.isEmpty())
            {
                this.isDone = true;
                return;
            }
            if (this.p.hand.size() == 1)
            {

                this.p.hand.moveToExhaustPile(this.p.hand.getBottomCard());

                AbstractDungeon.actionManager.addToTop(new GainBlockAction(p,blockPer));
                tickDuration();
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                this.p.hand.moveToExhaustPile(c);
            }
            AbstractDungeon.actionManager.addToTop(new GainBlockAction(p,blockPer));
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
        }
        tickDuration();
    }
}