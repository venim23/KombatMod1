package TheKombatant.cards.Uncommons;

import TheKombatant.Kombatmod;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.cards.CardHeaders;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.DarknessPower;
import TheKombatant.powers.HellfirePower;
import TheKombatant.powers.SpecialCancelPower;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RetainCardPower;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;

import static TheKombatant.Kombatmod.makeCardPath;

public class skillDarkness extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(skillDarkness.class.getSimpleName());
    public static final String IMG = makeCardPath("skillDarkness.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 0;
    private static final int BLOCK = 6;
    private static final int CARDS = 1;



    // /STAT DECLARATION/


    private static final boolean ComboCard = true;
    private static final boolean SpecialCard = false;
    private static final boolean EnhancedEffect = false;


    public skillDarkness() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET,ComboCard, SpecialCard, EnhancedEffect);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = CARDS;

        tags.add(CardTagEnum.COMBO);

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(magicNumber));

        if (upgraded){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new RetainCardPower(p,1),1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new DarknessPower(p,1),1));
        }


    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
