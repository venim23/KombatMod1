package TheKombatant.actions;

import TheKombatant.powers.CommonPower;
import TheKombatant.powers.DrunkenFistPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class MixupDamageAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCreature m;
    private int dmgvalue;
    private int blockvalue;
    private int newdamage;
    private int OGdamage;

    private boolean upgraded;

    public MixupDamageAction(AbstractPlayer p, AbstractCreature target, int damage) {
        this.p = p;
        actionType = ActionType.DAMAGE;
        this.m = target;
        this.dmgvalue = damage;
        //6, 6
        this.OGdamage = damage;
        //6 ,6
        this.blockvalue = m.currentBlock;
        //4 , 12
    }
    
    @Override
    public void update() {
        //( nd = 0 ,nd < 4,12, ND++)
        for (newdamage = 0; newdamage < blockvalue/2; newdamage++ ){
            if (newdamage <= OGdamage){
                dmgvalue++;
            }
        }
        /*
        if (p.hasPower(DrunkenFistPower.POWER_ID)){
            dmgvalue += p.getPower(DrunkenFistPower.POWER_ID).amount;
        }
        */
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, dmgvalue), AttackEffect.BLUNT_HEAVY));
        isDone = true;
    }
    // might change to use calculate modified card damage on each card instead
}
