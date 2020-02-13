package TheKombatant.actions;

import TheKombatant.powers.ChilledPower;
import TheKombatant.powers.MeterPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.WallopEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;

public class IceBallAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCreature m;
    private int dmgvalue;
    private int NewVal;
    private int times;
    private boolean upgraded;

    public IceBallAction(AbstractPlayer p, AbstractCreature target) {
        this.p = p;
        actionType = ActionType.DEBUFF;
        this.m = target;
    }

    @Override
    public void update() {
        if (m.hasPower(ChilledPower.POWER_ID)) {
            NewVal = (m.getPower(ChilledPower.POWER_ID).amount / 2);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, (0 - NewVal)), (0 - NewVal)));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, ChilledPower.POWER_ID));
        }
        isDone = true;
    }
    // might change to use calculate modified card damage on each card instead
}
