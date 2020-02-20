package TheKombatant.cards.Commons;

import TheKombatant.Kombatmod;
import TheKombatant.actions.ChillAllAction;
import TheKombatant.actions.EXEffectAction;
import TheKombatant.actions.SFXVAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.cards.CardHeaders;
import TheKombatant.characters.TheKombatant;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.SpecialCancelPower;
import TheKombatant.util.SoundEffects;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheKombatant.Kombatmod.makeCardPath;

public class attIceSlide extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attIceSlide.class.getSimpleName());
    public static final String IMG = makeCardPath("attIceSlide.png");
    public static final String IMGALT = makeCardPath("attIceSlideAlt.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheKombatant.Enums.COLOR_SLATE;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int CHILL= 2;
    private static final int UPGRADE_PLUS_CHILL = 2;
    private boolean CostModded = false;


    //Stuff for Kombatant
    private static final boolean ComboCard = false;
    private static final boolean SpecialCard = true;
    private static final boolean EnhancedEffect = true;


    // /STAT DECLARATION/


    public attIceSlide() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = CHILL;
        //Tags
        this.SetCardHeader(CardHeaders.Special);
        this.isMultiDamage = true;
        tags.add(CardTagEnum.SPECIAL);
        tags.add(CardTagEnum.ENHANCED);


        tags.add(CardTagEnum.CustomAnim);
        tags.add(CardTagEnum.AnimBlue2);

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.blueSlide.getKey(), 1.1F));

        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        AbstractDungeon.actionManager.addToBottom(new EXEffectAction(new ChillAllAction(p,magicNumber)));
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
            loadCardImage(IMGALT);
            this.textureImg = IMGALT;
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_CHILL);
            initializeDescription();
        }
    }
}