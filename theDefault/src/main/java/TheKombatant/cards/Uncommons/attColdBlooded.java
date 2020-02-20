package TheKombatant.cards.Uncommons;

import TheKombatant.Kombatmod;
import TheKombatant.actions.SFXVAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.characters.TheKombatant;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.ChilledPower;
import TheKombatant.util.SoundEffects;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;

import static TheKombatant.Kombatmod.makeCardPath;

public class attColdBlooded extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attColdBlooded.class.getSimpleName());
    public static final String IMG = makeCardPath("attColdBlooded.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheKombatant.Enums.COLOR_SLATE;

    private static final int COST = 1;
    private static final int DAMAGE = 7;

    private static final int BONUS = 2;
    private static final int UPGRADE_PLUS_BONUS = 1;

    //Stuff for Kombatant
    private static final boolean ComboCard = true;
    private static final boolean SpecialCard = false;
    private static final boolean EnhancedEffect = false;


    // /STAT DECLARATION/


    public attColdBlooded() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = BONUS;


        tags.add(CardTagEnum.COMBO);
        //Tags

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.greenCold.getKey(), 1.0F));


        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ClawEffect(m.hb.cX , m.hb.cY, Color.GREEN, Color.LIME), 0.1F));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));

            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ClawEffect(m.hb.cX , m.hb.cY, Color.LIME, Color.GREEN), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.VgreenSnarl.getKey(), 1.4F));






    }

    public float calculateModifiedCardDamage(final AbstractPlayer player, final AbstractMonster mo, final float tmp) {
        int newval = this.magicNumber;
        this.defaultSecondMagicNumber = this.defaultBaseSecondMagicNumber = 0;

        if ((mo != null) && (mo.hasPower(ChilledPower.POWER_ID))){
            defaultSecondMagicNumber += mo.getPower(ChilledPower.POWER_ID).amount;
        }

        if ((mo != null) && (mo.hasPower(PoisonPower.POWER_ID))){
            defaultSecondMagicNumber += mo.getPower(PoisonPower.POWER_ID).amount;
        }

        return tmp + (defaultSecondMagicNumber * newval);
    }



    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_BONUS);
            initializeDescription();
        }
    }
}