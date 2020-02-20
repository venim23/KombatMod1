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

public class UpgradeandAddNewAction extends AbstractGameAction{

    private AbstractPlayer p;
    private int cardnum = 0;
    private CardGroup cgroup;
    public UpgradeandAddNewAction(final CardGroup cardGroup)
    {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.cgroup = cardGroup;
    }

    public void update() {

        for (final AbstractCard c : cgroup.group) {
            this.addToBot(new MakeTempCardInDrawPileAction(c, 1, true, true));
            if (c.canUpgrade()) {
                if (cgroup.type == CardGroup.CardGroupType.HAND) {
                    c.superFlash();
                }
                c.upgrade();
                c.applyPowers();
            }
        }

        this.isDone = true;
    }

}

