package TheKombatant.actions;

import TheKombatant.patches.CardTagEnum;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class RecoverSpecialAction extends AbstractGameAction{
    private AbstractPlayer p;
    private int energyOnUse;
    private boolean upgraded;
    private int cards;
    private int energy = 0;

    public RecoverSpecialAction(final AbstractPlayer p, int cards, boolean upgraded)

    {
        this.p = p;
        actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.upgraded = upgraded;
        this.cards = cards;
    }

    @Override
    public void update() {


            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new FetchAction(AbstractDungeon.player.discardPile, card -> card.hasTag(CardTagEnum.SPECIAL), cards, fetchedCards -> {
            }));




       /*
        int effect = EnergyPanel.totalCount;
        if (energyOnUse != -1) {
            effect = energyOnUse;
        }
        if (p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            p.getRelic(ChemicalX.ID).flash();
        }
        if (upgraded) {
            effect++;
        }
         */
        /*if (p.hasPower(DexterityPower.POWER_ID)){
            BlockEffect = (BaseEffect + p.getPower(DexterityPower.POWER_ID).amount);
        }
        if (p.hasPower(StrengthPower.POWER_ID)){
            this.damage = (BaseEffect + p.getPower(StrengthPower.POWER_ID).amount);
        }
        */
        isDone = true;
    }

}
