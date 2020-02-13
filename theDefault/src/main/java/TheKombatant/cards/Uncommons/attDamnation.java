package TheKombatant.cards.Uncommons;

import TheKombatant.Kombatmod;
import TheKombatant.actions.SFXVAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.SpecialCancelPower;
import TheKombatant.powers.ToastyPower;
import TheKombatant.util.SoundEffects;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SearingBlowEffect;

import static TheKombatant.Kombatmod.makeCardPath;

public class attDamnation extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attDamnation.class.getSimpleName());
    public static final String IMG = makeCardPath("attDamnation.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 0;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 4;

    //Stuff for Kombatant
    private static final boolean ComboCard = true;
    private static final boolean SpecialCard = false;
    private static final boolean EnhancedEffect = false;


    // /STAT DECLARATION/


    public attDamnation() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;

        //Tags
        tags.add(CardTagEnum.COMBO);
        tags.add(CardTagEnum.CANCEL);

        tags.add(CardTagEnum.CustomAnim);
        tags.add(CardTagEnum.AnimYel2);

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.VyelHit1.getKey(), 1.1F));

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.FIRE));
        if (m.hasPower(ToastyPower.POWER_ID)){
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new SearingBlowEffect(m.hb.cX , m.hb.cY, 2), 0.3F));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new SpecialCancelPower(p,1),1));
        }


    }


    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}