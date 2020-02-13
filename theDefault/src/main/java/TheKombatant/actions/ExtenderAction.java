package TheKombatant.actions;

import TheKombatant.powers.ChainedPower;
import TheKombatant.powers.CommonPower;
import TheKombatant.powers.HatTrickPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ExtenderAction extends AbstractGameAction {
    private int magicNumber;
    private AbstractPlayer p;
    private int EXTENDERENERGY;
    private int CHAINVALUE;
    private int CARDDRAW;
    private int CARDPOWERUP = 2;

    public ExtenderAction(final AbstractPlayer p) {
        this.p = p;
        actionType = ActionType.ENERGY;
        EXTENDERENERGY = 1;
        CHAINVALUE = 10;
    }
    
    @Override
    public void update() {
        CARDDRAW = 1;
        if (AbstractDungeon.player.hasPower(HatTrickPower.POWER_ID)){
            CARDDRAW += p.getPower(HatTrickPower.POWER_ID).amount;
        }
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(EXTENDERENERGY));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(CARDDRAW));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new ChainedPower(p,CHAINVALUE), CHAINVALUE));
        isDone = true;
    }
}
