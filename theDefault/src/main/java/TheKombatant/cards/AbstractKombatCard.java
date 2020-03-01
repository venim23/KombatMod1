package TheKombatant.cards;

import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.MeterPower;
import TheKombatant.util.Utilities;
import basemod.abstracts.CustomCard;
import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue.Save;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Objects;
import java.util.List;
import java.util.ArrayList;

public abstract class AbstractKombatCard extends CustomCard {

    // Custom Abstract Cards can be a bit confusing. While this is a simple base for simply adding a second magic number,
    // if you're new to modding I suggest you skip this file until you know what unique things that aren't provided
    // by default, that you need in your own cards.

    // In this example, we use a custom Abstract Card in order to define a new magic number. From here on out, we can
    // simply use that in our cards, so long as we put "extends AbstractDynamicKombatCard" instead of "extends CustomCard" at the start.
    // In simple terms, it's for things that we don't want to define again and again in every single card we make.

    public int defaultSecondMagicNumber;        // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int defaultBaseSecondMagicNumber;    // And our base stat - the number in it's base state. It will reset to that by default.
    public boolean upgradedDefaultSecondMagicNumber; // A boolean to check whether the number has been upgraded or not.
    public boolean isDefaultSecondMagicNumberModified; // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)
    public boolean isComboCard = false;
    public boolean hasEX = false;
    public boolean isSpecialCard = false;
    public int hitTimes = 1;
    private Color Saveval;

    private CardHeader cardheader;

    public AbstractKombatCard(final String id,
                               final String name,
                               final String img,
                               final int cost,
                               final String rawDescription,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        // Set all the things to their default values.
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isDefaultSecondMagicNumberModified = false;
        this.glowColor.set(Color.GOLDENROD);
    }
    public AbstractKombatCard(final String id,
                              final String name,
                              final String img,
                              final int cost,
                              final String rawDescription,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target,
                              final Boolean isCombo,
                              final Boolean isSpecial,
                              final Boolean hasEnhanced) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        // Set all the things to their default values.
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isDefaultSecondMagicNumberModified = false;
        isComboCard = isCombo;
        isSpecialCard = isSpecial;
        hasEX = hasEnhanced;
        this.glowColor.set(Color.GOLDENROD);


    }

   /* public boolean teamworkUseCheck(final AbstractPlayer p, final AbstractMonster m) {
        boolean canUse = false;
        final int size = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
        if (size > 0) {
            final AbstractCard card = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(size - 1);
            if (card instanceof AbstractDefaultCard) {
                final AbstractDefaultCard lastcard = (AbstractDefaultCard)card;
                if (lastcard.character != null && lastcard.character != senshiNames.names.ASSIST) {
                    if (lastcard.character == senshiNames.names.MAMORU) {
                        if (AbstractDungeon.player.hasRelic(RoseRelic.ID)) {
                            canUse = true;
                        }
                    }
                    else {
                        canUse = true;
                    }
                }
            }
        }
        if (!canUse) {
            this.cantUseMessage = AbstractDefaultCard.CAN_USE_TEXT[0];
        }
        return canUse;
    }
*/
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m)
    {
        if (this.isComboCard) {

            boolean canUse = false;
            final int size = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();
            if (size > 0) {
                final AbstractCard card = AbstractDungeon.actionManager.cardsPlayedThisTurn.get(size - 1);
                if (((card.type == CardType.ATTACK) && (!((card.hasTag(CardTagEnum.COMBO)) || (card.hasTag(CardTagEnum.SPECIAL))))) || card.hasTag(CardTagEnum.COMBOEXCEPTION)){
                    canUse = true;

                }
                else {
                    this.cantUseMessage = "THAT'S NOT HOW KOMBO KARDS WORK, READ THE KEYWORD KLOSER.";
                            //AbstractKombatCard.CAN_USE_TEXT[0];
                }
            } else {
                this.cantUseMessage = "YOU THOUGHT YOU KOULD START WITH A KOMBO KARD?";
            }
            return canUse && this.cardPlayable(m) && this.hasEnoughEnergy();

        }
        if ((this.hasEX) && AbstractDungeon.player.hasPower(MeterPower.POWER_ID)){
            if (AbstractDungeon.player.getPower(MeterPower.POWER_ID).amount >=33) {
                this.glowColor.set(Color.GOLD);
                //1.0f, 0.8f, 0.2f, 0.75f
            } else {
                this.glowColor.set(Color.GOLDENROD);
            }
        }
        return this.cardPlayable(m) && this.hasEnoughEnergy();
    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
        if (upgradedDefaultSecondMagicNumber) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            defaultSecondMagicNumber = defaultBaseSecondMagicNumber; // Show how the number changes, as out of combat, the base number of a card is shown.
            isDefaultSecondMagicNumberModified = true; // Modified = true, color it green to highlight that the number is being changed.
        }

    }

    public void upgradeDefaultSecondMagicNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        defaultBaseSecondMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber; // Set the number to be equal to the base value.
        upgradedDefaultSecondMagicNumber = true; // Upgraded = true - which does what the above method does.
    }


    public boolean HasCardHeader(AbstractCard other)
    {
        AbstractKombatCard card = Utilities.SafeCast(other, AbstractKombatCard.class);
        if (card != null && card.cardheader != null)
        {
            return this.cardheader != null && (HasExactCardHeader(card.cardheader));
        }

        return false;
    }

    public boolean HasExactCardHeader(CardHeader cardheader)
    {
        return Objects.equals(this.cardheader, cardheader);
    }



    public CardHeader GetCardHeader()
    {
        return cardheader;
    }

    @Override
    public void render(SpriteBatch sb)
    {
        super.render(sb);
        RenderHeader(sb,false);
    }

    @Override
    public void renderInLibrary(SpriteBatch sb)
    {
        super.renderInLibrary(sb);
        RenderHeader(sb,false);
    }

    public void renderInSingleCardPopup(final SpriteBatch sb) {
            this.RenderHeader(sb, true);
    }


    private void RenderCardHeader(SpriteBatch sb)
    {


        {
            if (!this.isFlipped)//room == null || !(room.event instanceof GremlinMatchGame))
            {
                float originalScale = FontHelper.cardTitleFont.getData().scaleX;


                // New Color : public static final Color COOLBLUE = new Color(0.4F, 0.7F, 1.0F, 1.0F);
                /*if (HasActiveCardHeader())
                {
                    FontHelper.cardTitleFont_small_N.getData().setScale(this.drawScale * 0.85f);
                    textColor = Color.YELLOW.cpy();
                }
                else
                {*/
                FontHelper.cardTitleFont.getData().setScale(this.drawScale * 0.9F);
                Color textColor = Color.GOLD.cpy();
                //textColor = Settings.CREAM_COLOR.cpy();
                //}

                // New Color : public static final Color COOLBLUE = new Color(0.4F, 0.7F, 1.0F, 1.0F);

                FontHelper.renderRotatedText(sb, FontHelper.cardTitleFont, this.cardheader.NAME,
                        this.current_x, this.current_y, 0.0F, 400.0F * Settings.scale * this.drawScale / 2.0F,
                        this.angle, true, textColor);

                FontHelper.cardTitleFont.getData().setScale(originalScale);
            }
        }
    }

    protected String GetHeaderText() {
        return null;
    }

    protected void RenderHeader(final SpriteBatch sb, final boolean isCardPopup) {
        if (this.cardheader == null || this.isFlipped || this.isLocked || this.transparency <= 0.0f) {
            return;
        }
        BitmapFont font;
        float xPos;
        float yPos;
        float offsetY;
        if (isCardPopup) {
            font = FontHelper.SCP_cardTitleFont_small;
            xPos = Settings.WIDTH / 2.0f + 10.0f * Settings.scale;
            yPos = Settings.HEIGHT / 2.0f + 393.0f * Settings.scale;
            offsetY = 0.0f;

            if (this.upgraded) {
                loadCardImage(this.textureImg);
            }
        }
        else {
            font = FontHelper.cardTitleFont_small;
            xPos = this.current_x;
            yPos = this.current_y;
            offsetY = 400.0f * Settings.scale * this.drawScale / 2.0f;
        }
        final BitmapFont.BitmapFontData fontData = font.getData();
        final float originalScale = fontData.scaleX;
        float scaleMulti = 0.8f;
        final int length = this.cardheader.NAME.length();
        if (length > 20) {
            scaleMulti -= 0.02f * (length - 20);
            if (scaleMulti < 0.5f) {
                scaleMulti = 0.5f;
            }
        }
        fontData.setScale(scaleMulti * (isCardPopup ? 1.0f : this.drawScale));
        final Color color = Color.GOLD.cpy();
        color.a = this.transparency;
        FontHelper.renderRotatedText(sb, font, this.cardheader.NAME, xPos, yPos, 0.0f, offsetY, this.angle, true, color);
        fontData.setScale(originalScale);
    }


    public void SetCardHeader(CardHeader cardheader)
    {
        //SetCardHeader(cardheader, false);
        this.cardheader = cardheader;
    }
}