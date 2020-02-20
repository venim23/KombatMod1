package TheKombatant.relics;

import TheKombatant.Kombatmod;
import TheKombatant.powers.MeterPower;
import TheKombatant.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RetainCardPower;

import static TheKombatant.Kombatmod.makeRelicOutlinePath;
import static TheKombatant.Kombatmod.makeRelicPath;

public class Shinnoks2ndAmuletRelic extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = Kombatmod.makeID("Shinnoks2ndAmuletRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("shinnoks_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("shinnoks_relic.png"));
    private int RetainVal;
    private int barsVal;

    public Shinnoks2ndAmuletRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        this.counter = 0;
        this.RetainVal = 0;
    }

    // Flash at the start of Battle.
    // Gain 1 energy on equip.

    @Override
    public void atTurnStart() {
        if (AbstractDungeon.player.hasPower(MeterPower.POWER_ID)){
            this.counter = (AbstractDungeon.player.getPower(MeterPower.POWER_ID).amount/33);
            if (this.counter == 0){
                stopPulse();
            } else {
                beginLongPulse();
            }
        }
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (AbstractDungeon.player.hasPower(MeterPower.POWER_ID)){
            this.counter = (AbstractDungeon.player.getPower(MeterPower.POWER_ID).amount/33);
            if (this.counter == 0){
                stopPulse();
            } else {
                beginLongPulse();
            }
        }


        return damageAmount;
    }

    public void onLoseHp(int damageAmount) {
        if (AbstractDungeon.player.hasPower(MeterPower.POWER_ID)){
            this.counter = (AbstractDungeon.player.getPower(MeterPower.POWER_ID).amount/33);
            if (this.counter == 0){
                stopPulse();
            } else {
                beginLongPulse();
            }
        }

    }

    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (AbstractDungeon.player.hasPower(MeterPower.POWER_ID)){
            this.counter = (AbstractDungeon.player.getPower(MeterPower.POWER_ID).amount/33);
            if (this.counter == 0){
                stopPulse();
            } else {
                beginLongPulse();
            }
        }
    }


    @Override
    public void onPlayerEndTurn() {

        if (AbstractDungeon.player.hasPower(MeterPower.POWER_ID)){
            barsVal = (AbstractDungeon.player.getPower(MeterPower.POWER_ID).amount/33);
            if (barsVal > RetainVal){
                int diffVal = barsVal - RetainVal;
                AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new RetainCardPower((AbstractCreature)AbstractDungeon.player, diffVal), diffVal));
            } else if (RetainVal > barsVal){
                int diffVal = RetainVal - barsVal;
                AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ReducePowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, RetainCardPower.POWER_ID, diffVal));
            }
            RetainVal = barsVal;
        }

    }

/*
    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player.hasPower(MeterPower.POWER_ID)){
            if (AbstractDungeon.player.getPower(MeterPower.POWER_ID).amount >= 33){
                if (RetainVal < 1){
                    AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new RetainCardPower((AbstractCreature)AbstractDungeon.player, 1), 1));
                    RetainVal++;
                    beginLongPulse();
                }

            } else {
                AbstractDungeon.actionManager.addToTop((AbstractGameAction)new ReducePowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, RetainCardPower.POWER_ID, RetainVal));
                RetainVal = 0;
                stopPulse();
            }
        }

    }

  */
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
