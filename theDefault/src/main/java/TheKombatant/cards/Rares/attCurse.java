package TheKombatant.cards.Rares;

import TheKombatant.Kombatmod;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.cards.CardHeaders;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.SpecialCancelPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.DamagePerAttackPlayedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheKombatant.Kombatmod.makeCardPath;

public class attCurse extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attCurse.class.getSimpleName());
    public static final String IMG = makeCardPath("attCurse.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 1;
    private static final int CHAINVALUE = 20;
    private static final int DAMAGE = 4;
    private static final int DAMAGEUP = 2;
    private int ATTACKS = 0;
    private boolean CostModded = false;


    //Stuff for Kombatant
    private static final boolean ComboCard = false;
    private static final boolean SpecialCard = true;
    private static final boolean EnhancedEffect = false;


    // /STAT DECLARATION/


    public attCurse() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;
        this.SetCardHeader(CardHeaders.Special);
        tags.add(CardTagEnum.SPECIAL);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(
                new DamagePerAttackPlayedAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

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

        int newcount = 0;
        for (final AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == CardType.ATTACK) {
                newcount++;
            }
        }

        this.magicNumber = this.baseMagicNumber = newcount;
        this.rawDescription = (DESCRIPTION + EXTENDED_DESCRIPTION[0] + newcount);
        initializeDescription();

    }

    public void onMoveToDiscard()
    {
        this.rawDescription = DESCRIPTION;
        initializeDescription();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(DAMAGEUP);
            initializeDescription();
        }
    }
}