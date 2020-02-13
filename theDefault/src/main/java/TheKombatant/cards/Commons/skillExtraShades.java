package TheKombatant.cards.Commons;

import TheKombatant.Kombatmod;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheKombatant.Kombatmod.makeCardPath;

public class skillExtraShades extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(skillExtraShades.class.getSimpleName());
    public static final String IMG = makeCardPath("skillSpareShades.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 1;

    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public skillExtraShades() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
        tags.add(CardTagEnum.COMBOEXCEPTION);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}