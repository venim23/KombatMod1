package TheKombatant.cards.Commons;

import TheKombatant.Kombatmod;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.characters.TheDefault;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;

import static TheKombatant.Kombatmod.makeCardPath;

public class attEvilTwin extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attEvilTwin.class.getSimpleName());
    public static final String IMG = makeCardPath("attEvilTwin.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 1;
    private static final int DAMAGE = 6;
    private static final int UPGRADE_PLUS_DAMAGE = 2;


    //Stuff for Kombatant
    private static final boolean ComboCard = false;
    private static final boolean SpecialCard = false;
    private static final boolean EnhancedEffect = false;


    // /STAT DECLARATION/


    public attEvilTwin() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;
        //Tags

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(
                new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));

    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }
}