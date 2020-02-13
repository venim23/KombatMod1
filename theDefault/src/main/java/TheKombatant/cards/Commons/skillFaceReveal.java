package TheKombatant.cards.Commons;

import TheKombatant.Kombatmod;
import TheKombatant.actions.SFXVAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.characters.TheDefault;
import TheKombatant.powers.ChilledPower;
import TheKombatant.powers.ToastyPower;
import TheKombatant.util.SoundEffects;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static TheKombatant.Kombatmod.makeCardPath;

public class skillFaceReveal extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(skillFaceReveal.class.getSimpleName());
    public static final String IMG = makeCardPath("skillFaceReveal.png");

    // /TEXT DECLARATION/
    private static final boolean ComboCard = false;
    private static final boolean SpecialCard = false;
    private static final boolean EnhancedEffect = false;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 1;


    private static final int DEBUFFS = 1;
    private static final int UPDEBUFFS = 1;


    // /STAT DECLARATION/


    public skillFaceReveal() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET,ComboCard, SpecialCard, EnhancedEffect);
        magicNumber = baseMagicNumber = DEBUFFS;

        //tags.add(CardTagEnum.ENHANCED);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.greenReveal.getKey(), 1.1F));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p,new PoisonPower(m,p,magicNumber),magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p,new ChilledPower(m,p,magicNumber),magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p,new ToastyPower(m,p,magicNumber),magicNumber));


    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPDEBUFFS);
            initializeDescription();
        }
    }
}