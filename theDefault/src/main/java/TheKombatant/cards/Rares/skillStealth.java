package TheKombatant.cards.Rares;

import TheKombatant.Kombatmod;
import TheKombatant.actions.SFXVAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.characters.TheKombatant;
import TheKombatant.powers.InvisiblePower;
import TheKombatant.util.SoundEffects;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

import static TheKombatant.Kombatmod.makeCardPath;

public class skillStealth extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(skillStealth.class.getSimpleName());
    public static final String IMG = makeCardPath("skillStealth.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheKombatant.Enums.COLOR_SLATE;

    private static final int COST = 3;
    private static final int UPCOST = 2;
    private static final int BUFFERS = 3;
    private static final int ENTANGLES = 2;




    // /STAT DECLARATION/


    public skillStealth() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = BUFFERS;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BufferPower(p,magicNumber),magicNumber));
        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.greenStealth.getKey(), 1.1F));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new InvisiblePower(p,p)));


    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPCOST);
            initializeDescription();
        }
    }
}
