package TheKombatant.cards.Commons;

import TheKombatant.Kombatmod;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

import static TheKombatant.Kombatmod.makeCardPath;

public class skillSmokeParry extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(skillSmokeParry.class.getSimpleName());
    public static final String IMG = makeCardPath("skillSmokeParry.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 1;

    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int DEX = 2;
    private static final int UPGRADE_PLUS_DEX = 1;

    // /STAT DECLARATION/


    public skillSmokeParry() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = DEX;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new DexterityPower(p,magicNumber),magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new LoseDexterityPower(p,magicNumber),magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_PLUS_DEX);
            initializeDescription();
        }
    }
}