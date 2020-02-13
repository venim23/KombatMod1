package TheKombatant.actions;

import TheKombatant.powers.ChilledPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.UUID;

public class FatalityAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractCreature m;
    private int magic;
    private int blockvalue;
    private int newdamage;
    private int times;
    private boolean upgraded;
    private int Healamount;
    private DamageInfo info;
    private UUID uuid;

    public FatalityAction(AbstractPlayer p, final AbstractCreature target, final DamageInfo info, final int HealAmount) {
        this.p = p;
        actionType = ActionType.SPECIAL;
        this.magic = amount;
        this.setValues(target, this.info = info);
        this.Healamount = HealAmount;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1f;
    }

    @Override
    public void update() {
        if (this.duration == 0.1f && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_VERTICAL));
            this.target.damage(this.info);
            if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                AbstractDungeon.player.heal(this.Healamount, false);
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        isDone = true;
    }
    // might change to use calculate modified card damage on each card instead
}
