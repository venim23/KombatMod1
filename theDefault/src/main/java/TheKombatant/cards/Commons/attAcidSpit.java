package TheKombatant.cards.Commons;

import TheKombatant.Kombatmod;
import TheKombatant.actions.SFXVAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.cards.CardHeaders;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.SpecialCancelPower;
import TheKombatant.util.SoundEffects;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.FlyingOrbEffect;
import com.megacrit.cardcrawl.vfx.combat.FlyingSpikeEffect;
import com.megacrit.cardcrawl.vfx.combat.IceShatterEffect;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

import java.awt.*;

import static TheKombatant.Kombatmod.makeCardPath;

public class attAcidSpit extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attAcidSpit.class.getSimpleName());
    public static final String IMG = makeCardPath("attAcidSpit.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 1;
    private static final int CHAINVALUE = 20;
    private static final int DAMAGE = 6;
    private static final int POISON = 2;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int UPGRADE_PLUS_POISON = 1;
    private boolean CostModded = false;

    //Stuff for Kombatant
    private static final boolean ComboCard = false;
    private static final boolean SpecialCard = true;
    private static final boolean EnhancedEffect = false;


    // /STAT DECLARATION/


    public attAcidSpit() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = POISON;
        this.SetCardHeader(CardHeaders.Special);
        tags.add(CardTagEnum.SPECIAL);

        tags.add(CardTagEnum.CustomAnim);
        tags.add(CardTagEnum.AnimGreen1);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.VgreenSpit.getKey(), 1.1F));
        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.greenSpit1.getKey(), 1.3F));

        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ThrowDaggerEffect(m.hb.cX , m.hb.cY), 0.3F));

        //AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FlyingSpikeEffect(p.hb.cX , p.hb.cY, 0.0F, m.hb.cX , m.hb.cY, Color.GREEN), 0.6F));

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.POISON));

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m,p,new PoisonPower(m,p,magicNumber),magicNumber));

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
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_POISON);
            initializeDescription();
        }
    }
}