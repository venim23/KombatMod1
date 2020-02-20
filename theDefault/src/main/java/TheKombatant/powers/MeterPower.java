package TheKombatant.powers;

import TheKombatant.Kombatmod;
import TheKombatant.cards.AbstractKombatCard;
import TheKombatant.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("meter_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("0meter_power32.png"));
    private static final Texture noameter = TextureLoader.getTexture(makePowerPath("00meter_power32.png"));
    private static final Texture nobmeter = TextureLoader.getTexture(makePowerPath("01meter_power32.png"));
    private static final Texture nocmeter = TextureLoader.getTexture(makePowerPath("02meter_power32.png"));
    private static final Texture oneameter = TextureLoader.getTexture(makePowerPath("10meter_power32.png"));
    private static final Texture onebmeter = TextureLoader.getTexture(makePowerPath("11meter_power32.png"));
    private static final Texture onecmeter = TextureLoader.getTexture(makePowerPath("12meter_power32.png"));
    private static final Texture twoameter = TextureLoader.getTexture(makePowerPath("20meter_power32.png"));
    private static final Texture twobmeter = TextureLoader.getTexture(makePowerPath("21meter_power32.png"));
    private static final Texture twocmeter = TextureLoader.getTexture(makePowerPath("22meter_power32.png"));
    private static final Texture threemeter = TextureLoader.getTexture(makePowerPath("30meter_power32.png"));

    private int maxMeter = 100;
    public static int BaseMeterGain = 8;
    private int attackMeter = BaseMeterGain;
    public  static int combometer = 6;
    private int hplossMeter = 8;
    private int damageBlockedMeter = 6;
    private int oldAmt = 0;
    private Texture currImg = tex32;

    public MeterPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        if (amount < maxMeter)
        {
            this.amount = amount;
        } else {
            this.amount = maxMeter;
        }
        if (this.amount == 0) {
            this.amount = 1;
        }



        type = PowerType.BUFF;
        isTurnBased = false;
        canGoNegative = true;


        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(currImg, -12, -10, 64, 64);

        updateDescription();
    }
    @Override
    public void playApplyPowerSfx(){

    }

    @Override
    public void reducePower(int reduceAmount) {
        if ((this.amount - reduceAmount) <= 0) {
            this.fontScale = 8.0F;
            this.amount = 1;
        } else {
            this.fontScale = 8.0F;
            this.amount -= reduceAmount;
        }
        updateDescription();
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
        updateDescription();
    }

    @Override
    public void onUseCard(final AbstractCard card, final UseCardAction action) {

        if ((card.type == AbstractCard.CardType.ATTACK) && (card instanceof AbstractKombatCard)){
            AbstractKombatCard c = (AbstractKombatCard) card;
            if ((!c.isSpecialCard) && (!c.hasEX) && (!c.isComboCard)){
                this.amount += attackMeter * c.hitTimes;
            } else if ((!c.isSpecialCard) && (!c.hasEX)){
                this.amount += combometer * c.hitTimes;
            } else if ((c.isSpecialCard) && !c.hasEX){
                this.amount += combometer;
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

        if (damageAmount <= this.owner.currentBlock){
            this.amount += damageBlockedMeter;

        }
        if (this.amount > maxMeter){
            this.amount = maxMeter;}
        updateDescription();
        return damageAmount;

    }

    /*
    @Override
    protected void loadRegion(String fileName) {
        this.region48 = new TextureAtlas.AtlasRegion(currImg, -12, -8, 64, 64);
        super.loadRegion(fileName);

    }
    */
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
        if (this.amount <11){
            currImg = noameter;
        } else if ((this.amount >=11) && (this.amount <22)){
            currImg = nobmeter;
        } else if ((this.amount >=22) && (this.amount <33)){
            currImg = nocmeter;
        } else if ((this.amount >=33) && (this.amount <44)){
            currImg = oneameter;
        } else if ((this.amount >=44) && (this.amount <55)){
            currImg = onebmeter;
        } else if ((this.amount >=55) && (this.amount <66)){
            currImg = onecmeter;
        } else if ((this.amount >=66) && (this.amount <77)){
            currImg = twoameter;
        } else if ((this.amount >=77) && (this.amount <88)){
            currImg = twobmeter;
        } else if ((this.amount >=88) && (this.amount <99)){
            currImg = twocmeter;
        } else {
            currImg = threemeter;
        }
        this.region48 = new TextureAtlas.AtlasRegion(currImg, -12, -8, 64, 64);


           description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount/33 + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new MeterPower(owner, amount);
    }
}
