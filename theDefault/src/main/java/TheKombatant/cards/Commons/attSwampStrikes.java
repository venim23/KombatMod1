package TheKombatant.cards.Commons;

import TheKombatant.Kombatmod;
import TheKombatant.actions.ExtenderAction;
import TheKombatant.actions.SFXVAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.characters.TheKombatant;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.util.SoundEffects;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;

import static TheKombatant.Kombatmod.makeCardPath;

public class attSwampStrikes extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attSwampStrikes.class.getSimpleName());
    public static final String IMG = makeCardPath("attSwampSpray.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheKombatant.Enums.COLOR_SLATE;

    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int VULNAMT = 2;

    //Stuff for Kombatant
    private static final boolean ComboCard = true;
    private static final boolean SpecialCard = false;
    private static final boolean EnhancedEffect = false;


    // /STAT DECLARATION/


    public attSwampStrikes() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = VULNAMT;
        tags.add(CardTagEnum.COMBO);
        //Tags

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.VgreenBurns.getKey(), 1.1F));

        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ClawEffect(m.hb.cX , m.hb.cY, Color.GREEN, com.badlogic.gdx.graphics.Color.LIME), 0.3F));

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.POISON));
        if (m.hasPower(PoisonPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p,new VulnerablePower(m, magicNumber, false),magicNumber));
            if (upgraded){
               AbstractDungeon.actionManager.addToBottom(new ExtenderAction(p));
            }
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