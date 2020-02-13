package TheKombatant.powers;

import TheKombatant.Kombatmod;
import TheKombatant.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.ChemicalX;

import static TheKombatant.Kombatmod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class ChainedPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = Kombatmod.makeID("ChainedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int ChainedMax;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("chainedDmg_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("chainedDmg_power32.png"));

    public ChainedPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        ChainedMax = 80;
        this.owner = owner;
        this.amount = amount;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        if (amount >= ChainedMax) {
            this.amount = ChainedMax;
        } else {
            this.amount = amount;
        }

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }


    // Should accept a Value, and then reduce the damage dealt by the player by that value. The powerwill expire at the end of a turn. and 80% is the max reduction.
    // On use card, apply (amount) of Dexterity. (Go to the actual power card for the amount.)
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        float decamount = ((float)this.amount/100) ;
        if (type == DamageInfo.DamageType.NORMAL) {
            return (damage * (1 - decamount));
        } else {
            return damage;
        }
    }
    // Note: If you want to apply an effect when a power is being applied you have 3 options:
    //onInitialApplication is "When THIS power is first applied for the very first time only."
    //onApplyPower is "When the owner applies a power to something else (only used by Sadistic Nature)."
    //onReceivePowerPower from StSlib is "When any (including this) power is applied to the owner."

    public void stackPower(int stackAmount) {

        if ((this.amount + stackAmount) > ChainedMax) {
            this.amount = ChainedMax;
        } else {
                this.amount += stackAmount;
        }

        this.updateDescription();
    }

    // At the end of the turn, remove gained Dexterity.
    @Override
    public void atEndOfTurn(final boolean isPlayer) {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, this.ID));

    }

    // Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ChainedPower(owner, amount);
    }
}
