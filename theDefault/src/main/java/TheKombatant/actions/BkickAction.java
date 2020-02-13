package TheKombatant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BkickAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCreature m;
    private int dmgvalue;
    private int blockvalue;
    private int newdamage;
    private int times;
    private boolean upgraded;

    public BkickAction(AbstractPlayer p, AbstractCreature target, int damage, int reps) {
        this.p = p;
        actionType = ActionType.DAMAGE;
        this.m = target;
        this.times = reps;
        this.dmgvalue = damage;
        this.blockvalue = m.currentBlock;
    }

    @Override
    public void update() {
        for (int i = this.times; i > 0; i--){
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, dmgvalue), AttackEffect.BLUNT_LIGHT));
        }
        isDone = true;
    }
    // might change to use calculate modified card damage on each card instead
}
