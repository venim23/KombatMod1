package TheKombatant.cards.Uncommons;

import TheKombatant.Kombatmod;
import TheKombatant.actions.EXEffectAction;
import TheKombatant.actions.ExtenderAction;
import TheKombatant.actions.IceBallAction;
import TheKombatant.actions.SFXVAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.cards.CardHeaders;
import TheKombatant.characters.TheKombatant;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.ChilledPower;
import TheKombatant.powers.SpecialCancelPower;
import TheKombatant.util.SoundEffects;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.*;

import static TheKombatant.Kombatmod.makeCardPath;

public class skillIceBall extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(skillIceBall.class.getSimpleName());
    public static final String IMG = makeCardPath("skillIceBall.png");
    public static final String IMGALT = makeCardPath("skillIceBallAlt.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;


    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheKombatant.Enums.COLOR_SLATE;

    private static final int COST = 1;
    private static final int CHILL = 2;
    private boolean CostModded = false;

    //Stuff for Kombatant
    private static final boolean ComboCard = false;
    private static final boolean SpecialCard = true;
    private static final boolean EnhancedEffect = true;


    // /STAT DECLARATION/


    public skillIceBall() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        magicNumber =baseMagicNumber = CHILL;
        this.SetCardHeader(CardHeaders.Special);

        tags.add(CardTagEnum.SPECIAL);
        tags.add(CardTagEnum.ENHANCED);

        tags.add(CardTagEnum.CustomAnim);
        tags.add(CardTagEnum.AnimBlue1);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new AnimatedSlashEffect(m.hb.cX,m.hb.cY,p.hb.cX,p.hb.cY, 0.0f,  Color.SKY, Color.CYAN), 0.3F));
        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.blueIceball.getKey(), 1.8F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new IceShatterEffect(p.hb.cX,p.hb.cY), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new IceShatterEffect(p.hb.cX +50.0f,p.hb.cY), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new IceShatterEffect(p.hb.cX +100.0f,p.hb.cY), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new IceShatterEffect(p.hb.cX +150.0f,p.hb.cY), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new IceShatterEffect(p.hb.cX +200.0f,p.hb.cY), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new IceShatterEffect(m.hb.cX-150.0f ,m.hb.cY), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new IceShatterEffect(m.hb.cX-100.0f ,m.hb.cY), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new IceShatterEffect(m.hb.cX-50.0f ,m.hb.cY), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new IceShatterEffect(m.hb.cX ,m.hb.cY), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FrostOrbPassiveEffect(m.hb.cX , m.hb.cY), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p , new ChilledPower(m,p, magicNumber), magicNumber));
        if (upgraded){
            AbstractDungeon.actionManager.addToBottom(new ExtenderAction(p));
        }
        AbstractDungeon.actionManager.addToBottom(new EXEffectAction(new IceBallAction(p,m)));

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
            loadCardImage(IMGALT);
            this.textureImg = IMGALT;
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}