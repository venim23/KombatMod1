package TheKombatant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PreserveAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCreature m;
    private AbstractCard c;
    private int blockvalue;
    private int newdamage;
    private int times;
    private boolean upgraded;

    public PreserveAction(AbstractPlayer p, AbstractCard card) {
        this.p = p;
        actionType = ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        isDone = true;
    }
    // might change to use calculate modified card damage on each card instead
}
