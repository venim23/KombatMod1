package TheKombatant.actions;

import TheKombatant.powers.ChilledPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

public class ChillAllAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCreature m;
    private int magic;
    private int blockvalue;
    private int newdamage;
    private int times;
    private boolean upgraded;

    public ChillAllAction(AbstractPlayer p, int amount) {
        this.p = p;
        actionType = ActionType.DEBUFF;
        this.magic = amount;
    }

    @Override
    public void update() {

        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new BlizzardEffect(1,false), 0.1F));
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if ((!monster.isDead) && (!monster.isDying)) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p , new ChilledPower(monster,p, magic), magic));

            }
        }
        isDone = true;
    }
    // might change to use calculate modified card damage on each card instead
}
