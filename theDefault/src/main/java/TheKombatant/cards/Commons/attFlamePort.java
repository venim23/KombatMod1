package TheKombatant.cards.Commons;

import TheKombatant.Kombatmod;
import TheKombatant.actions.ExtenderAction;
import TheKombatant.actions.MixupDamageAction;
import TheKombatant.actions.SFXVAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.cards.CardHeaders;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.DrunkenFistPower;
import TheKombatant.powers.SpecialCancelPower;
import TheKombatant.powers.ToastyPower;
import TheKombatant.util.SoundEffects;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GhostlyFireEffect;

import static TheKombatant.Kombatmod.makeCardPath;

public class attFlamePort extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attFlamePort.class.getSimpleName());
    public static final String IMG = makeCardPath("attFlamePort.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int TOASTY = 3;
    private static final int UPGRADE_PLUS_TOASTY = 2;
    private boolean CostModded = false;

    //Stuff for Kombatant
    private static final boolean ComboCard = false;
    private static final boolean SpecialCard = true;
    private static final boolean EnhancedEffect = false;


    // /STAT DECLARATION/


    public attFlamePort() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = TOASTY;
        this.SetCardHeader(CardHeaders.Special);
        tags.add(CardTagEnum.SPECIAL);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.yelPort.getKey(), 1.1F));


        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new GhostlyFireEffect(m.hb.cX , m.hb.cY), 0.2F));

        AbstractDungeon.actionManager.addToBottom(
                new MixupDamageAction(p, m, damage));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p,new ToastyPower(m,p,magicNumber),magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ExtenderAction(p));
    }

    public float calculateModifiedCardDamage(final AbstractPlayer player, final AbstractMonster mo, final float tmp) {
        int bo_bonus = 0;

        if ((mo != null) && (player.hasPower(DrunkenFistPower.POWER_ID))){
            bo_bonus += player.getPower(DrunkenFistPower.POWER_ID).amount;
        }

        return tmp + bo_bonus;
    }

    public void applyPowers()
    {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(SpecialCancelPower.POWER_ID)){
            if (this.costForTurn > 0) {
                this.costForTurn = this.cost - 1;
                CostModded = true;
            }
        } else if (CostModded){
            this.costForTurn = this.cost;
            CostModded = false;
        }
        initializeDescription();

    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_TOASTY);
            initializeDescription();
        }
    }
}