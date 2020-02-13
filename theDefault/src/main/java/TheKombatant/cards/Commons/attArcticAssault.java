package TheKombatant.cards.Commons;

import TheKombatant.Kombatmod;
import TheKombatant.actions.SFXVAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.ChilledPower;
import TheKombatant.util.SoundEffects;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.IceShatterEffect;

import static TheKombatant.Kombatmod.makeCardPath;

public class attArcticAssault extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attArcticAssault.class.getSimpleName());
    public static final String IMG = makeCardPath("attArcticAssault.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 1;
    private static final int COSTUP = 0;
    private static final int DAMAGE = 8;
    //private static final int UPGRADE_PLUS_DMG = 3;
    private static final int CHILL= 4;
    private static final int UPGRADE_PLUS_CHILL = 2;


    //Stuff for Kombatant
    private static final boolean ComboCard = true;
    private static final boolean SpecialCard = false;
    private static final boolean EnhancedEffect = false;


    // /STAT DECLARATION/


    public attArcticAssault() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = CHILL;
        //Tags
        tags.add(CardTagEnum.COMBO);

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.blueStabs.getKey(), 1.1F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FrostOrbActivateEffect(m.hb.cX , m.hb.cY), 0.1F));
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p, new ChilledPower(m,p,magicNumber),magicNumber));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(COSTUP);
            //upgradeDamage(UPGRADE_PLUS_DMG);
            //upgradeMagicNumber();
            initializeDescription();
        }
    }
}