package TheKombatant.characters;

import TheKombatant.Kombatmod;
import TheKombatant.cards.Commons.attThrow;
import TheKombatant.patches.CardTagEnum;
import TheKombatant.powers.MeterPower;
import TheKombatant.relics.ShinnoksAmuletRelic;
import TheKombatant.util.SoundEffects;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.ui.MultiPageFtue;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import TheKombatant.cards.*;
import TheKombatant.relics.DefaultClickableRelic;
import TheKombatant.relics.PlaceholderRelic;
import TheKombatant.relics.PlaceholderRelic2;

import java.util.ArrayList;

import static TheKombatant.Kombatmod.*;
import static TheKombatant.characters.TheDefault.Enums.COLOR_SLATE;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in Kombatmod-character-Strings.json in the resources

public class TheDefault extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(Kombatmod.class.getName());

    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_KOMBATANT;
        @SpireEnum(name = "KOMBAT_GOLD_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_SLATE;
        @SpireEnum(name = "KOMBAT_GOLD_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 0;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("DefaultCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    private String currentAtlasURL = "TheKombatantResources/images/char/defaultCharacter/Kombatant.atlas";
    private String currentJsonURL = "TheKombatantResources/images/char/defaultCharacter/Kombatant.json";

    // =============== /STRINGS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {
            "TheKombatantResources/images/char/defaultCharacter/orb/layer1.png",
            "TheKombatantResources/images/char/defaultCharacter/orb/layer2.png",
            "TheKombatantResources/images/char/defaultCharacter/orb/layer3.png",
            "TheKombatantResources/images/char/defaultCharacter/orb/layer4.png",
            "TheKombatantResources/images/char/defaultCharacter/orb/layer5.png",
            "TheKombatantResources/images/char/defaultCharacter/orb/layer6.png",
            "TheKombatantResources/images/char/defaultCharacter/orb/layer1d.png",
            "TheKombatantResources/images/char/defaultCharacter/orb/layer2d.png",
            "TheKombatantResources/images/char/defaultCharacter/orb/layer3d.png",
            "TheKombatantResources/images/char/defaultCharacter/orb/layer4d.png",
            "TheKombatantResources/images/char/defaultCharacter/orb/layer5d.png",};

    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============
    public void reloadAnimation(String key)
    {

        switch (key)
        {
            //for each key, it has a simple little transition between animations,
            //for this example, sets the animation to attack, and not looping, then adds the looping idle animation as next in line.
            case "Grey":
                this.currentAtlasURL = "TheKombatantResources/images/char/defaultCharacter/Kombatant.atlas";
                this.currentJsonURL = "TheKombatantResources/images/char/defaultCharacter/Kombatant.json";
                break;
            case "Red":
                this.currentAtlasURL = "TheKombatantResources/images/char/defaultCharacter/Kombatant.atlas";
                this.currentJsonURL = "TheKombatantResources/images/char/defaultCharacter/Kombatant.json";
                //ENDSCENE1 = "HollowModResources/images/scenes/void1.png";
                //ENDSCENE2 = "HollowModResources/images/scenes/void2.png";
                //ENDSCENE3 = "HollowModResources/images/scenes/void3.png";
                break;
            case "BLUE":
                this.currentAtlasURL = "TheKombatantResources/images/char/defaultCharacter/Kombatant.atlas";
                this.currentJsonURL = "TheKombatantResources/images/char/defaultCharacter/Kombatant.json";

                break;
            case "YELLOW":
                this.currentAtlasURL = "TheKombatantResources/images/char/defaultCharacter/Kombatant.atlas";
                this.currentJsonURL = "TheKombatantResources/images/char/defaultCharacter/Kombatant.json";

                break;
            case "GREEN":
                this.currentAtlasURL = "TheKombatantResources/images/char/defaultCharacter/Kombatant.atlas";
                this.currentJsonURL = "TheKombatantResources/images/char/defaultCharacter/Kombatant.json";

                break;
            case "BLACK":
                this.currentAtlasURL = "TheKombatantResources/images/char/defaultCharacter/Kombatant.atlas";
                this.currentJsonURL = "TheKombatantResources/images/char/defaultCharacter/Kombatant.json";
                break;
        }

        this.loadAnimation(this.currentAtlasURL, this.currentJsonURL, 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }





    // =============== CHARACTER CLASS START =================

    public TheDefault(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "TheKombatantResources/images/char/defaultCharacter/orb/vfx.png", (String) null, null);


        // =============== TEXTURES, ENERGY, LOADOUT =================  

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in Kombatmod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                THE_DEFAULT_SHOULDER_1, // campfire pose
                THE_DEFAULT_SHOULDER_2, // another campfire pose
                THE_DEFAULT_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================  
        reloadAnimation("grey");

        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        retVal.add(attHighStrike_s.ID);
        retVal.add(attHighStrike_s.ID);
        retVal.add(attHighStrike_s.ID);

        retVal.add(attLowKick.ID);
        retVal.add(attLowKick.ID);
        retVal.add(attUppercut.ID);
        retVal.add(attThrow.ID);

        retVal.add(skillGuard_s.ID);
        retVal.add(skillGuard_s.ID);
        retVal.add(skillGuard_s.ID);



        return retVal;
    }

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(ShinnoksAmuletRelic.ID);

        UnlockTracker.markRelicAsSeen(ShinnoksAmuletRelic.ID);


        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playV(SoundEffects.Fight.getKey(), 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_SLATE;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return Kombatmod.KOMBAT_GOLD;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new attHighStrike_s();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new TheDefault(name, chosenClass);
    }


    @Override
    public void preBattlePrep(){
        super.preBattlePrep();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction( AbstractDungeon.player, AbstractDungeon.player, new MeterPower(AbstractDungeon.player, 0),0));
    }

    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        if ((c.type == AbstractCard.CardType.ATTACK) && !(c.hasTag(CardTagEnum.CustomAnim))) {
            final AnimationState.TrackEntry e = this.state.setAnimation(0, "kick", false);
            this.state.addAnimation(0, "idle", true, 0.0f);
            e.setTimeScale(1f);
        }

        if ( !(c.type == AbstractCard.CardType.ATTACK) && (c.block > 0) && !(c.hasTag(CardTagEnum.CustomAnim))){
            final AnimationState.TrackEntry e = this.state.setAnimation(0, "block", false);
            this.state.addAnimation(0, "idle", true, 0.0f);
            e.setTimeScale(1f);
        }
        if (c.hasTag(CardTagEnum.CustomAnim)) {
            if (c.hasTag(CardTagEnum.AnimKombo)) {
                final AnimationState.TrackEntry e = this.state.setAnimation(0, "kombo", false);
                this.state.addAnimation(0, "idle", true, 0.0f);
                e.setTimeScale(1f);
            } else if (c.hasTag(CardTagEnum.AnimBlue1)) {
                final AnimationState.TrackEntry e = this.state.setAnimation(0, "blueSpec1", false);
                this.state.addAnimation(0, "idle", true, 0.0f);
                e.setTimeScale(1f);
            } else if (c.hasTag(CardTagEnum.AnimBlue2)) {
                final AnimationState.TrackEntry e = this.state.setAnimation(0, "blueSpec2", false);
                this.state.addAnimation(0, "idle", true, 0.0f);
                e.setTimeScale(1f);
            } else if (c.hasTag(CardTagEnum.AnimYel1)) {
                final AnimationState.TrackEntry e = this.state.setAnimation(0, "yellowSpec1", false);
                this.state.addAnimation(0, "idle", true, 0.0f);
                e.setTimeScale(1f);
            } else if (c.hasTag(CardTagEnum.AnimYel2)) {
                final AnimationState.TrackEntry e = this.state.setAnimation(0, "yellowSpec2", false);
                this.state.addAnimation(0, "idle", true, 0.0f);
                e.setTimeScale(1f);
            } else if (c.hasTag(CardTagEnum.AnimMulti)) {
                final AnimationState.TrackEntry e = this.state.setAnimation(0, "punching", false);
                this.state.addAnimation(0, "idle", true, 0.0f);
                e.setTimeScale(1f);
            } else if (c.hasTag(CardTagEnum.AnimRed1)) {
                final AnimationState.TrackEntry e = this.state.setAnimation(0, "redSpec1", false);
                this.state.addAnimation(0, "idle", true, 0.0f);
                e.setTimeScale(1f);
            } else if (c.hasTag(CardTagEnum.AnimRed2)) {
                final AnimationState.TrackEntry e = this.state.setAnimation(0, "redSpec2", false);
                this.state.addAnimation(0, "idle", true, 0.0f);
                e.setTimeScale(1f);
            } else if (c.hasTag(CardTagEnum.AnimGreen1)) {
                final AnimationState.TrackEntry e = this.state.setAnimation(0, "greenSpec1", false);
                this.state.addAnimation(0, "idle", true, 0.0f);
                e.setTimeScale(1f);
            } else if (c.hasTag(CardTagEnum.AnimGreen2)) {
                final AnimationState.TrackEntry e = this.state.setAnimation(0, "greenSpec2", false);
                this.state.addAnimation(0, "idle", true, 0.0f);
                e.setTimeScale(1f);
            } else if (c.hasTag(CardTagEnum.AnimPurple)) {
                final AnimationState.TrackEntry e = this.state.setAnimation(0, "purpleSpec1", false);
                this.state.addAnimation(0, "idle", true, 0.0f);
                e.setTimeScale(1f);
            } else if (c.hasTag(CardTagEnum.AnimBlack)) {
               /*
                final AnimationState.TrackEntry e = this.state.setAnimation(0, "blackSpec1", false);
                this.state.addAnimation(0, "idle", true, 0.0f);
                e.setTimeScale(0.6f);
                */

            }
        }



        c.calculateCardDamage(monster);
        c.use(this, monster);
        AbstractDungeon.actionManager.addToBottom(new UseCardAction(c, monster));
        if (!c.dontTriggerOnUseCard) {
            this.hand.triggerOnOtherCardPlayed(c);
        }

        this.hand.removeCard(c);
        this.cardInUse = c;
        c.target_x = (float)(Settings.WIDTH / 2);
        c.target_y = (float)(Settings.HEIGHT / 2);
        if (c.costForTurn > 0 && !c.freeToPlayOnce && (!this.hasPower("Corruption") || c.type != AbstractCard.CardType.SKILL)) {
            this.energy.use(c.costForTurn);
        }

        if (!this.hand.canUseAnyCard() && !this.endTurnQueued) {
            AbstractDungeon.overlayMenu.endTurnButton.isGlowing = true;
        }

    }


    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return Kombatmod.KOMBAT_SLATE;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return Kombatmod.KOMBAT_SLATE;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    @Override
    public void damage(final DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
            final AnimationState.TrackEntry e = this.state.setAnimation(0, "hit", false);
            this.state.addAnimation(0, "idle", true, 0.0f);
            e.setTimeScale(0.6f);
        }
        super.damage(info);
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

}
