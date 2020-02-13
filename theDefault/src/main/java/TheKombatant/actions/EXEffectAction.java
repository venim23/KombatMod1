package TheKombatant.actions;

import TheKombatant.powers.MeterPower;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.cards.*;




public class EXEffectAction extends AbstractGameAction {
    private AbstractGameAction action;
    private int NONEXVAL = 3;

    public EXEffectAction (AbstractGameAction EXEffect){
        this.action = EXEffect;
    }

    public void update(){
        if (AbstractDungeon.player.hasPower(MeterPower.POWER_ID)){
            if (AbstractDungeon.player.getPower(MeterPower.POWER_ID).amount >=33){
                AbstractDungeon.actionManager.addToTop(this.action);
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, MeterPower.POWER_ID, 33));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MeterPower(AbstractDungeon.player, NONEXVAL),NONEXVAL));
            }

        }
        this.isDone = true;

    }




}
