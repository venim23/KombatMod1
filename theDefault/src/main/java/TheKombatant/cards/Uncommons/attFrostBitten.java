package TheKombatant.cards.Uncommons;

import TheKombatant.Kombatmod;
import TheKombatant.cards.AbstractDynamicKombatCard;
import TheKombatant.characters.TheKombatant;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.ChilledPower;
import TheKombatant.powers.SpecialCancelPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlyingSpikeEffect;

import java.util.ArrayList;

import static TheKombatant.Kombatmod.makeCardPath;

public class attFrostBitten extends AbstractDynamicKombatCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Big Slap Deal 10(15)) damage.
     */

    // TEXT DECLARATION

    public static final String ID = Kombatmod.makeID(attFrostBitten.class.getSimpleName());
    public static final String IMG = makeCardPath("attFrostbitten.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheKombatant.Enums.COLOR_SLATE;

    private static final int COST = 2;
    private static final int DAMAGE = 14;
    private static final int UPDAMAGE = 6;


    private static final int BONUS = 1;
    private static final int UPGRADE_PLUS_BONUS = 1;

    //Stuff for Kombatant
    private static final boolean ComboCard = false;
    private static final boolean SpecialCard = false;
    private static final boolean EnhancedEffect = false;


    // /STAT DECLARATION/


    public attFrostBitten() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET, ComboCard, SpecialCard, EnhancedEffect);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = BONUS;

        tags.add(CardTagEnum.CANCEL);
        //Tags

    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY));



        if (m.hasPower(ChilledPower.POWER_ID)){
            ArrayList<AbstractMonster> mo = AbstractDungeon.getCurrRoom().monsters.monsters;
            int[] tmp = new int[mo.size()];

            int i;
            for(i = 0; i < tmp.length; ++i) {
                tmp[i] = m.getPower(ChilledPower.POWER_ID).amount;
            }

            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FlyingSpikeEffect(m.hb.cX , m.hb.cY, 135.0F,m.hb.cX , m.hb.cY, Color.SKY ), 0.0F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FlyingSpikeEffect(m.hb.cX , m.hb.cY, 45.0F,m.hb.cX , m.hb.cY, Color.SKY ), 0.0F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FlyingSpikeEffect(m.hb.cX , m.hb.cY, 90.0F,m.hb.cX , m.hb.cY, Color.SKY ), 0.0F));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FlyingSpikeEffect(m.hb.cX , m.hb.cY, 180.0F,m.hb.cX , m.hb.cY, Color.SKY ), 0.0F));
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAllEnemiesAction(p,tmp, damageTypeForTurn,
                            AbstractGameAction.AttackEffect.BLUNT_LIGHT, false));
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new SpecialCancelPower(p,1),1));


    }



    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPDAMAGE);
            initializeDescription();
        }
    }
}