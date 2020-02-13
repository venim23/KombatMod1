package TheKombatant.cards.Commons;

import TheKombatant.Kombatmod;
import TheKombatant.actions.EXEffectAction;
import TheKombatant.actions.SFXVAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.cards.CardHeaders;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.SpecialCancelPower;
import TheKombatant.util.SoundEffects;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static TheKombatant.Kombatmod.makeCardPath;

public class skillLightningPort extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */


    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(skillLightningPort.class.getSimpleName());
    public static final String IMG = makeCardPath("skillLightningPort.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 1;
    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int ENBACK = 1;
    private boolean CostModded = false;


    // /STAT DECLARATION/
    private static final boolean ComboCard = false;
    private static final boolean SpecialCard = true;
    private static final boolean EnhancedEffect = true;


    public skillLightningPort() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET,ComboCard, SpecialCard, EnhancedEffect);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = ENBACK;

        this.SetCardHeader(CardHeaders.Special);
        tags.add(CardTagEnum.SPECIAL);
        tags.add(CardTagEnum.ENHANCED);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.raidPort.getKey(), 1.1F));

        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new LightningEffect(p.hb.cX , p.hb.cY), 0.1F));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));

        AbstractDungeon.actionManager.addToBottom(new EXEffectAction(new GainEnergyAction(ENBACK)));


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

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
