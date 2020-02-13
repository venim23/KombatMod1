package TheKombatant.cards.Uncommons;

import TheKombatant.Kombatmod;
import TheKombatant.actions.EXEffectAction;
import TheKombatant.actions.ExtenderAction;
import TheKombatant.actions.SFXVAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.cards.CardHeaders;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.SpecialCancelPower;
import TheKombatant.util.SoundEffects;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;

import static TheKombatant.Kombatmod.makeCardPath;

public class attSoulChoke extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attSoulChoke.class.getSimpleName());
    public static final String IMG = makeCardPath("attSoulChoke.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 1;
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int LIFE = 5;
    private static final int UPGRADE_PLUS_LIFE = 3;
    private boolean CostModded = false;

    //Stuff for Kombatant
    private static final boolean ComboCard = true;
    private static final boolean SpecialCard = true;
    private static final boolean EnhancedEffect = true;


    // /STAT DECLARATION/


    public attSoulChoke() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = LIFE;
        this.SetCardHeader(CardHeaders.Special);
        tags.add(CardTagEnum.SPECIAL);
        tags.add(CardTagEnum.COMBO);
        tags.add(CardTagEnum.ENHANCED);

        tags.add(CardTagEnum.CustomAnim);
        tags.add(CardTagEnum.AnimRed2);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.redChoke.getKey(), 1.2F));

        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FlyingOrbEffect(m.hb.cX , m.hb.cY), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FlyingOrbEffect(m.hb.cX , m.hb.cY), 0.0F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FlyingOrbEffect(m.hb.cX , m.hb.cY), 0.0F));



        //Perfect Choice for VFX
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.POISON));

        AbstractDungeon.actionManager.addToBottom(new EXEffectAction(new AddTemporaryHPAction(p,p,(damage/2))));

    }

    @Override
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
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}