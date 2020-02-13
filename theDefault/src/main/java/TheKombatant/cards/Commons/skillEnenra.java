package TheKombatant.cards.Commons;

import TheKombatant.Kombatmod;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.cards.CardHeaders;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.SpecialCancelPower;
import TheKombatant.util.SoundEffects;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.ThrowShivEffect;

import static TheKombatant.Kombatmod.makeCardPath;

public class skillEnenra extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(skillEnenra.class.getSimpleName());
    public static final String IMG = makeCardPath("skillEnenra.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 1;
    private static final int COSTUP = 0;

    private static final int TEMPSTR = 1;
    private static final int STARTSTR = 0;

    // /STAT DECLARATION/


    private static final boolean ComboCard = false;
    private static final boolean SpecialCard = true;
    private static final boolean EnhancedEffect = false;


    public skillEnenra() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET,ComboCard, SpecialCard, EnhancedEffect);
        baseMagicNumber = magicNumber = TEMPSTR;
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber = STARTSTR;
        this.exhaust = true;

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        defaultSecondMagicNumber =0;
        for (final AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.type == CardType.ATTACK) {
                defaultSecondMagicNumber++;
            }
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, defaultSecondMagicNumber),defaultSecondMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new LoseStrengthPower(p, defaultSecondMagicNumber),defaultSecondMagicNumber));
    }

    public void applyPowers()
    {
        super.applyPowers();

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


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(COSTUP);
            initializeDescription();
        }
    }
}