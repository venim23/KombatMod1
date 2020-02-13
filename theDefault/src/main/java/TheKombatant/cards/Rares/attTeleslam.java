package TheKombatant.cards.Rares;

import TheKombatant.Kombatmod;
import TheKombatant.actions.EXEffectAction;
import TheKombatant.actions.PreserveAction;
import TheKombatant.actions.SFXVAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.cards.CardHeaders;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.MeterPower;
import TheKombatant.powers.SpecialCancelPower;
import TheKombatant.util.SoundEffects;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheKombatant.Kombatmod.makeCardPath;

public class attTeleslam extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attTeleslam.class.getSimpleName());
    public static final String IMG = makeCardPath("attTeleslam.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 2;
    private static final int UPCOST = 1;
    private static final int DAMAGE = 9;
    private boolean CostModded = false;

    //Stuff for Kombatant
    private static final boolean ComboCard = false;
    private static final boolean SpecialCard = true;
    private static final boolean EnhancedEffect = true;


    // /STAT DECLARATION/


    public attTeleslam() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;
        this.SetCardHeader(CardHeaders.Special);
        tags.add(CardTagEnum.SPECIAL);
        tags.add(CardTagEnum.ENHANCED);
        this.exhaust = true;


        tags.add(CardTagEnum.CustomAnim);
        tags.add(CardTagEnum.AnimRed1);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.exhaust = false;
        AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.VredMany.getKey(), 1.6F));

        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (m.currentBlock <=0 ){

            AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.VredOne.getKey(), 1.6F));

            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                            AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        }

        AbstractDungeon.actionManager.addToBottom(new EXEffectAction(new PreserveAction(p, this)));

        if (AbstractDungeon.player.hasPower(MeterPower.POWER_ID)){
            if (AbstractDungeon.player.getPower(MeterPower.POWER_ID).amount <33){
                this.exhaust = true;
            }
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

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPCOST);
            initializeDescription();
        }
    }
}