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
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.FlyingDaggerEffect;
import com.megacrit.cardcrawl.vfx.combat.IceShatterEffect;
import com.megacrit.cardcrawl.vfx.combat.ThrowShivEffect;

import static TheKombatant.Kombatmod.makeCardPath;

public class skillSpear extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * A Better Defend Gain 1 Plated Armor. Affected by Dexterity.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(skillSpear.class.getSimpleName());
    public static final String IMG = makeCardPath("skillSpear.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 0;

    private static final int VULN = 2;
    private static final int CARDS = 1;
    private static final int CARDSUP = 1;
    private boolean CostModded = false;

    // /STAT DECLARATION/


    private static final boolean ComboCard = false;
    private static final boolean SpecialCard = true;
    private static final boolean EnhancedEffect = false;


    public skillSpear() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET,ComboCard, SpecialCard, EnhancedEffect);
        baseMagicNumber = magicNumber = CARDS;
        this.SetCardHeader(CardHeaders.Special);
        tags.add(CardTagEnum.SPECIAL);
        this.exhaust = true;

        tags.add(CardTagEnum.CustomAnim);
        tags.add(CardTagEnum.AnimYel1);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXAction(SoundEffects.Spear.getKey()));
        AbstractDungeon.actionManager.addToBottom(new SFXAction(SoundEffects.SpearThrow.getKey()));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ThrowShivEffect(m.hb.cX , m.hb.cY), 0.2F));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(m, p, new VulnerablePower(m, VULN, false), VULN));

        AbstractDungeon.actionManager.addToBottom(new FetchAction(p.discardPile, (card -> (card == p.discardPile.getTopCard())), 1));
        if (upgraded) {
            AbstractDungeon.actionManager.addToBottom(new FetchAction(p.discardPile, (card -> (card == p.discardPile.getTopCard())), 1));
        }
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

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(CARDSUP);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}