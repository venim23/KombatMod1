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
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

public class ThunderAllAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCreature m;
    private int magic;
    private int blockvalue;
    private int newdamage;
    private int times;
    private boolean upgraded;

    public ThunderAllAction(AbstractPlayer p, int amount) {
        this.p = p;
        actionType = ActionType.DAMAGE;
        this.newdamage = amount;
    }

    @Override
    public void update() {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if ((!monster.isDead) && (!monster.isDying)) {
                AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new LightningEffect(monster.hb.cX , monster.hb.cY), 0.1F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(monster, new DamageInfo(p, newdamage),
                        AttackEffect.LIGHTNING));

            }
            isDone = true;
        }
    }
}
    // might change to use calculate modified card damage on each card instead
