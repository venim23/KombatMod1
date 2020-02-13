package TheKombatant.cards.Commons;

import TheKombatant.Kombatmod;
import TheKombatant.actions.SFXVAction;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.characters.TheDefault;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.MeterPower;
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
import com.megacrit.cardcrawl.vfx.combat.IceShatterEffect;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

import static TheKombatant.Kombatmod.makeCardPath;

public class attGodFist extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attGodFist.class.getSimpleName());
    public static final String IMG = makeCardPath("attGodFist.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_SLATE;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int HITTIMES = 2;
    private static final int HITTIMES_UP = 1;

    //Stuff for Kombatant
    private static final boolean ComboCard = false;
    private static final boolean SpecialCard = false;
    private static final boolean EnhancedEffect = false;


    // /STAT DECLARATION/


    public attGodFist() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = HITTIMES;
        //Tags

        tags.add(CardTagEnum.CustomAnim);
        tags.add(CardTagEnum.AnimMulti);

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {


        // Good for Acid Spit AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new ThrowDaggerEffect(m.hb.cX , m.hb.cY), 0.4F));
        for (int i = this.magicNumber; i > 0; i--){
            AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.Shock1.getKey(), 1.1F));

            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new MeterPower(p,1), 1));
            AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(randomMonster, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }

    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(HITTIMES_UP);
            initializeDescription();
        }
    }
}