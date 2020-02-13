package TheKombatant.cards.Rares;

import TheKombatant.Kombatmod;
import TheKombatant.actions.BkickAction;
import TheKombatant.actions.EXEffectAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.cards.CardHeaders;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.SpecialCancelPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static TheKombatant.Kombatmod.makeCardPath;

public class attBicycleKick extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attBicycleKick.class.getSimpleName());
    public static final String IMG = makeCardPath("attBicycleKick.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 2;
    private static final int CHAINVALUE = 20;
    private static final int DAMAGE = 5;
    private static final int TIMES = 3;
    private static final int UPGRADE_PLUS_DMG = 2;
    private boolean CostModded = false;

    //Stuff for Kombatant
    private static final boolean ComboCard = true;
    private static final boolean SpecialCard = true;
    private static final boolean EnhancedEffect = true;


    // /STAT DECLARATION/


    public attBicycleKick() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = TIMES;
        this.SetCardHeader(CardHeaders.Special);
        tags.add(CardTagEnum.SPECIAL);
        tags.add(CardTagEnum.ENHANCED);
        tags.add(CardTagEnum.COMBO);

        tags.add(CardTagEnum.CustomAnim);
        tags.add(CardTagEnum.AnimMulti);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        for (int i = this.magicNumber; i > 0; i--){
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        AbstractDungeon.actionManager.addToBottom(new EXEffectAction(new BkickAction(p, m, damage, magicNumber)));

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
            initializeDescription();
        }
    }
}