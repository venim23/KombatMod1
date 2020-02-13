package TheKombatant.powers;

import TheKombatant.Kombatmod;
import TheKombatant.cards.AbstractKombatCard;
import TheKombatant.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

import static TheKombatant.Kombatmod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class MeterPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = Kombatmod.makeID("MeterPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    private int maxMeter = 100;
    public static int BaseMeterGain = 8;
    private int attackMeter = BaseMeterGain;
    private int combometer = 6;
    private int hplossMeter = 10;
    private int damageBlockedMeter = 5;

    public MeterPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        if (amount < maxMeter){
            this.amount = amount;
        } else {this.amount = maxMeter;}




        type = PowerType.BUFF;
        isTurnBased = false;
        canGoNegative = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    @Override
    public void playApplyPowerSfx(){

    }

    @Override
    public void reducePower(int reduceAmount) {
        if (this.amount - reduceAmount <= 0) {
            this.fontScale = 8.0F;
            this.amount = 1;
        } else {
            this.fontScale = 8.0F;
            this.amount -= reduceAmount;
        }
    }
    @Override
    public void stackPower(int stackAmount)
    {
        if ((this.amount + stackAmount) < maxMeter){
            this.amount += stackAmount;
        } else {
            this.amount = maxMeter;
        }
        if (this.amount == 0){
            this.amount = 1;
        }

    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {

        if ((card.type == AbstractCard.CardType.ATTACK) && (card instanceof AbstractKombatCard)){
            AbstractKombatCard c = (AbstractKombatCard) card;
            if ((!c.isSpecialCard) && (!c.hasEX) && (!c.isComboCard)){
                this.amount += attackMeter * c.hitTimes;
            } else if ((!c.isSpecialCard) && (!c.hasEX)){
                this.amount += combometer * c.hitTimes;
            }

        } else if (card.type == AbstractCard.CardType.ATTACK){
            this.amount += attackMeter;
        } else if (card instanceof AbstractKombatCard){
            AbstractKombatCard c = (AbstractKombatCard) card;
            if ((c.isSpecialCard) && !c.hasEX){
                this.amount += combometer;
            }
        }
        if (this.amount > maxMeter){
            this.amount = maxMeter;}
        updateDescription();



    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount){

        if (damageAmount < this.owner.currentBlock){
            this.amount += damageBlockedMeter;

        }
        if (this.amount > maxMeter){
            this.amount = maxMeter;}
        updateDescription();
        return damageAmount;

    }

    @Override
    public int onLoseHp(int damageAmount){

        this.amount += hplossMeter;
        if (this.amount > maxMeter){
            this.amount = maxMeter;}
        updateDescription();
        return damageAmount;

    }

    // Note: If you want to apply an effect when a power is being applied you have 3 options:
    //onInitialApplication is "When THIS power is first applied for the very first time only."
    //onApplyPower is "When the owner applies a power to something else (only used by Sadistic Nature)."
    //onReceivePowerPower from StSlib is "When any (including this) power is applied to the owner."


    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
           description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount/33 + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new MeterPower(owner, amount);
    }
}
