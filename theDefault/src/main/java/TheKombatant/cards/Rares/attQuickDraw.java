package TheKombatant.cards.Rares;

import TheKombatant.Kombatmod;
import TheKombatant.actions.MixupDamageAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.cards.CardHeaders;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.DrunkenFistPower;
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

public class attQuickDraw extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attQuickDraw.class.getSimpleName());
    public static final String IMG = makeCardPath("attQuickDraw.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 0;
    private static final int CHAINVALUE = 0;
    private static final int DAMAGE = 8;
    private static final int DAMAGEUP = 4;
    private int ATTACKS = 0;


    //Stuff for Kombatant
    private static final boolean ComboCard = false;
    private static final boolean SpecialCard = false;
    private static final boolean EnhancedEffect = false;


    // /STAT DECLARATION/


    public attQuickDraw() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

       AbstractDungeon.actionManager.addToBottom(new MixupDamageAction(p,m,damage));

    }
    public float calculateModifiedCardDamage(final AbstractPlayer player, final AbstractMonster mo, final float tmp) {
        int bo_bonus = 0;

        if ((mo != null) && (player.hasPower(DrunkenFistPower.POWER_ID))){
            bo_bonus += player.getPower(DrunkenFistPower.POWER_ID).amount;
        }

        return tmp + bo_bonus;
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