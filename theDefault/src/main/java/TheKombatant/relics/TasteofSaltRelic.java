package TheKombatant.relics;

import TheKombatant.Kombatmod;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static TheKombatant.Kombatmod.makeRelicOutlinePath;
import static TheKombatant.Kombatmod.makeRelicPath;

public class TasteofSaltRelic extends CustomRelic {
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     *
     * At the start of each combat, gain 1 Strength (i.e. Vajra)
     */

    // ID, images, text.
    public static final String ID = Kombatmod.makeID("TasteofSaltRelic");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("tastesalt_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("tastesalt_relic.png"));

    private static final int BLOCKUP = 2;

    public TasteofSaltRelic() {
        super(ID, IMG, OUTLINE, RelicTier.RARE, LandingSound.FLAT);
    }


    // Gain 1 Strength on on equip.
    public void onCardDraw(final AbstractCard card) {
        if (card.hasTag(CardTagEnum.SPECIAL)){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCKUP));
            this.flash();
        }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
