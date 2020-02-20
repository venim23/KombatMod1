package TheKombatant.relics;

import TheKombatant.Kombatmod;
import TheKombatant.powers.MeterPower;
import TheKombatant.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.RetainCardPower;

import static TheKombatant.Kombatmod.makeRelicOutlinePath;
import static TheKombatant.Kombatmod.makeRelicPath;

public class ShinnoksAmuletRelic extends CustomRelic {

    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * Gain 1 energy.
     */

    // ID, images, text.
    public static final String ID = Kombatmod.makeID("ShinnoksAmuletRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("shinnoks_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("shinnoks_relic.png"));
    private int RetainVal;

    public ShinnoksAmuletRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStart() {
        this.flash();
        RetainVal = 0;
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new RetainCardPower((AbstractCreature)AbstractDungeon.player, 1), 1));
    }
    // Gain 1 energy on equip.
    /*
    @Override
    public void atTurnStart() {
        if (AbstractDungeon.player.hasPower(MeterPower.POWER_ID)){
            if (AbstractDungeon.player.getPower(MeterPower.POWER_ID).amount >= 33){
                if (RetainVal < 1){
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new RetainCardPower((AbstractCreature)AbstractDungeon.player, 1), 1));
                    RetainVal++;
                    beginLongPulse();
                }

            } else {
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ReducePowerAction((AbstractCreature)AbstractDungeon.player, (AbstractCreature)AbstractDungeon.player, RetainCardPower.POWER_ID, RetainVal));
                RetainVal = 0;
                stopPulse();
            }
        }
    }
    */
    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player.hasPower(MeterPower.POWER_ID)){
            if (AbstractDungeon.player.getPower(MeterPower.POWER_ID).amount >= 66){
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
