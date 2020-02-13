package TheKombatant.cards.Commons;

import TheKombatant.Kombatmod;
import TheKombatant.actions.ExtenderAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheKombatant.Kombatmod.makeCardPath;

public class skillRunCancel extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(skillRunCancel.class.getSimpleName());
    public static final String IMG = makeCardPath("skillRunCancel.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/
    private static final boolean ComboCard = true;
    private static final boolean SpecialCard = false;
    private static final boolean EnhancedEffect = false;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 0;


    // /STAT DECLARATION/


    public skillRunCancel() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET,ComboCard, SpecialCard, EnhancedEffect);
        this.tags.add(CardTagEnum.COMBO);
        this.exhaust = true;


    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded){
            tags.add((CardTagEnum.COMBOEXCEPTION));
        }
        AbstractDungeon.actionManager.addToBottom(new ExtenderAction(p));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}