package TheKombatant.powers;

import TheKombatant.Kombatmod;
import TheKombatant.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static TheKombatant.Kombatmod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class ChilledPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = Kombatmod.makeID("ChilledPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int attMin = 1;
    private boolean attackedThis;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("chilled_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("chilled_power32.png"));

    public ChilledPower(final AbstractCreature owner, final AbstractCreature source, int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;
        this.attackedThis = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        this.updateDescription();
    }

    /*
    // On use card, apply (amount) of Dexterity. (Go to the actual power card for the amount.)
    @Override
    public int onAttackToChangeDamage(final DamageInfo info, final int damageAmount) {
        if (info.type == DamageInfo.DamageType.NORMAL) {
            attMod = this.amount;
            this.amount = this.amount / 2;
            if (this.amount == 0) {
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            }
            updateDescription();


        }
        return (damageAmount - attMod);
    }
    */
    @Override
    public float atDamageFinalGive(final float damage, final DamageInfo.DamageType type) {
        if (type != DamageInfo.DamageType.NORMAL) {
            return damage;
        }

        if ((damage - this.amount) <=0){
            return attMin;
        }
        if (damage - this.amount > 0) {

            return damage - this.amount;
        }
        return damage;
    }
    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.owner == this.owner){
            flashWithoutSound();
            this.attackedThis = true;
        }
    }

    @Override
    public void atEndOfRound() {
            if (this.attackedThis) {
                this.amount = this.amount / 2;
                if (this.amount == 0) {
                    this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
                }
                this.attackedThis = false;
                this.updateDescription();
            }
    }

    // At the end of the turn, remove gained Dexterity.


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ChilledPower(owner, source, amount);
    }
}
