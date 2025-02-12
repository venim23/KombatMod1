package TheKombatant;

import TheKombatant.cards.Commons.*;
import TheKombatant.cards.Rares.*;
import TheKombatant.cards.Uncommons.*;
import TheKombatant.characters.TheKombatant;
import TheKombatant.monsters.bossShaoKahn;
import TheKombatant.relics.*;
import TheKombatant.util.SoundEffects;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.abstracts.CustomUnlockBundle;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.TheBeyond;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import TheKombatant.cards.*;
import TheKombatant.util.IDCheckDontTouchPls;
import TheKombatant.util.TextureLoader;
import TheKombatant.variables.DefaultCustomVariable;
import TheKombatant.variables.DefaultSecondMagicNumber;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
//TODO: DON'T MASS RENAME/REFACTOR
// Please don't just mass replace "TheKombatant" with "yourMod" everywhere.
// It'll be a bigger pain for you. You only need to replace it in 3 places.
// I comment those places below, under the place where you set your ID.

//TODO: FIRST THINGS FIRST: RENAME YOUR PACKAGE AND ID NAMES FIRST-THING!!!
// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "TheKombatant:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "TheKombatant". You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class Kombatmod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        AddAudioSubscriber,
        PostInitializeSubscriber,
        SetUnlocksSubscriber{
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(Kombatmod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "The Kombatant";
    private static final String AUTHOR = "VenIM"; // And pretty soon - You!
    private static final String DESCRIPTION = "Mortal Kombat in Slay the Spire???";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color KOMBAT_SLATE = CardHelper.getColor(77.0f, 77.0f, 106.0f);
    public static final Color KOMBAT_GOLD = CardHelper.getColor(204.0f, 158.0f, 0.0f);
    
    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
    
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
    // ONCE YOU CHANGE YOUR MOD ID (BELOW, YOU CAN'T MISS IT) CHANGE THESE PATHS!!!!!!!!!!!
  
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "TheKombatantResources/images/512/bg_attack_BKMK.png";
    private static final String SKILL_DEFAULT_GRAY = "TheKombatantResources/images/512/bg_Skill_BKMK.png";
    private static final String POWER_DEFAULT_GRAY = "TheKombatantResources/images/512/bg_Power_BKMK.png";
    
    private static final String ENERGY_ORB_DEFAULT_GRAY = "TheKombatantResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "TheKombatantResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "TheKombatantResources/images/1024/bg_attack_BKMK.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "TheKombatantResources/images/1024/bg_Skill_BKMK.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "TheKombatantResources/images/1024/bg_Power_BKMK.png";

    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "TheKombatantResources/images/1024/card_default_gray_orb.png";
    
    // Character assets
    private static final String THE_DEFAULT_BUTTON = "TheKombatantResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "TheKombatantResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "TheKombatantResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "TheKombatantResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "TheKombatantResources/images/char/defaultCharacter/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "TheKombatantResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "TheKombatantResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "TheKombatantResources/images/char/defaultCharacter/skeleton.json";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }
    public static String makeAudioPath(String resourcePath) {
        return getModID() + "Resources/audio/sounds/" + resourcePath;
    }
    public static String makeMonsterPath(String resourcePath) {
        return getModID() + "Resources/images/monsters/" + resourcePath;
    }

    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================
    
    public Kombatmod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
      /*
           (   ( /(  (     ( /( (            (  `   ( /( )\ )    )\ ))\ )
           )\  )\()) )\    )\()))\ )   (     )\))(  )\()|()/(   (()/(()/(
         (((_)((_)((((_)( ((_)\(()/(   )\   ((_)()\((_)\ /(_))   /(_))(_))
         )\___ _((_)\ _ )\ _((_)/(_))_((_)  (_()((_) ((_|_))_  _(_))(_))_
        ((/ __| || (_)_\(_) \| |/ __| __| |  \/  |/ _ \|   \  |_ _||   (_)
         | (__| __ |/ _ \ | .` | (_ | _|  | |\/| | (_) | |) |  | | | |) |
          \___|_||_/_/ \_\|_|\_|\___|___| |_|  |_|\___/|___/  |___||___(_)
      */
      
        setModID("TheKombatant");
        // cool
        // TODO: NOW READ THIS!!!!!!!!!!!!!!!:
        
        // 1. Go to your resources folder in the project panel, and refactor> rename TheKombatantResources to
        // yourModIDResources.
        
        // 2. Click on the localization > eng folder and press ctrl+shift+r, then select "Directory" (rather than in Project)
        // replace all instances of TheKombatant with yourModID.
        // Because your mod ID isn't the default. Your cards (and everything else) should have Your mod id. Not mine.
        
        // 3. FINALLY and most importantly: Scroll up a bit. You may have noticed the image locations above don't use getModID()
        // Change their locations to reflect your actual ID rather than TheKombatant. They get loaded before getID is a thing.
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + TheKombatant.Enums.COLOR_SLATE.toString());
        
        BaseMod.addColor(TheKombatant.Enums.COLOR_SLATE, KOMBAT_SLATE, KOMBAT_SLATE, KOMBAT_SLATE,
                KOMBAT_SLATE, KOMBAT_SLATE, KOMBAT_SLATE, KOMBAT_SLATE,
                ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
                ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
                ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    // ====== NO EDIT AREA ======
    // DON'T TOUCH THIS STUFF. IT IS HERE FOR STANDARDIZATION BETWEEN MODS AND TO ENSURE GOOD CODE PRACTICES.
    // IF YOU MODIFY THIS I WILL HUNT YOU DOWN AND DOWNVOTE YOUR MOD ON WORKSHOP
    
    public static void setModID(String ID) { // DON'T EDIT
        Gson coolG = new Gson(); // EY DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
        InputStream in = Kombatmod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
        } else { // NO EDIT AREA
            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
        } // NO
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    } // NO
    
    public static String getModID() { // NO
        return modID; // DOUBLE NO
    } // NU-UH
    
    private static void pathCheck() { // ALSO NO
        Gson coolG = new Gson(); // NNOPE DON'T EDIT THIS
        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i still hate u btw Gdx.files
        InputStream in = Kombatmod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THISSSSS
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // NAH, NO EDIT
        String packageName = Kombatmod.class.getPackage().getName(); // STILL NO EDIT ZONE
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources"); // PLEASE DON'T EDIT THINGS HERE, THANKS
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { // LEAVE THIS EDIT-LESS
            if (!packageName.equals(getModID())) { // NOT HERE ETHER
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID()); // THIS IS A NO-NO
            } // WHY WOULD U EDIT THIS
            if (!resourcePathExists.exists()) { // DON'T CHANGE THIS
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources"); // NOT THIS
            }// NO
        }// NO
    }// NO
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        Kombatmod defaultmod = new Kombatmod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheKombatant.Enums.THE_KOMBATANT.toString());
        
        BaseMod.addCharacter(new TheKombatant("the Default", TheKombatant.Enums.THE_KOMBATANT),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, TheKombatant.Enums.THE_KOMBATANT);
        
        receiveEditPotions();
        logger.info("Added " + TheKombatant.Enums.THE_KOMBATANT.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================
        receiveEditMonsters();
        // This event will be exclusive to the City (act 2). If you want an event that's present at any
        // part of the game, simply don't include the dungeon ID
        // If you want to have a character-specific event, look at slimebound (CityRemoveEventPatch).
        // Essentially, you need to patch the game and say "if a player is not playing my character class, remove the event from the pool"
        //BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        
        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }

    public void receiveEditMonsters(){
        BaseMod.addMonster(bossShaoKahn.ID, () -> new bossShaoKahn());
        logger.info("Bosses Loaded");
        //BaseMod.addBoss(TheBeyond.ID, bossShaoKahn.ID, "TheKombatantResources/images/ui/map/ShaoKahnIcon.png", "TheKombatantResources/images/ui/map/bossIcon-outline.png");

    }
    
    // =============== / POST-INITIALIZE/ =================

    @Override
    public void receiveAddAudio() {
        addAudio(SoundEffects.Fatality);
        addAudio(SoundEffects.toasty);
        addAudio(SoundEffects.Suck);
        addAudio(SoundEffects.Spear);
        addAudio(SoundEffects.Fight);
        addAudio(SoundEffects.SpearThrow);
        addAudio(SoundEffects.Spit);
        addAudio(SoundEffects.Shock1);
        addAudio(SoundEffects.Shock2);
        addAudio(SoundEffects.Flying);
        addAudio(SoundEffects.Kombo);
        addAudio(SoundEffects.Ice);
        addAudio(SoundEffects.Slide);
        //Red
        addAudio(SoundEffects.VredDefy);
        addAudio(SoundEffects.VredDie);
        addAudio(SoundEffects.VredMany);
        addAudio(SoundEffects.VredOne);
        addAudio(SoundEffects.redBall);
        addAudio(SoundEffects.redChoke);
        addAudio(SoundEffects.redStrike1);
        addAudio(SoundEffects.redStrike2);
        //Green
        addAudio(SoundEffects.VgreenBite);
        addAudio(SoundEffects.VgreenBurns);
        addAudio(SoundEffects.VgreenSnarl);
        addAudio(SoundEffects.VgreenSpit);
        addAudio(SoundEffects.greenCold);
        addAudio(SoundEffects.greenForce);
        addAudio(SoundEffects.greenReveal);
        addAudio(SoundEffects.greenSlide);
        addAudio(SoundEffects.greenSpit1);
        addAudio(SoundEffects.greenStealth);
        //Blue
        addAudio(SoundEffects.VblueEnd);
        addAudio(SoundEffects.VblueHit1);
        addAudio(SoundEffects.VblueLin);
        addAudio(SoundEffects.VblueWeaker);
        addAudio(SoundEffects.blueIceball);
        addAudio(SoundEffects.blueKlone);
        addAudio(SoundEffects.blueShatter);
        addAudio(SoundEffects.blueSlide);
        addAudio(SoundEffects.blueStabs);
        //Yell
        addAudio(SoundEffects.VyelBurn);
        addAudio(SoundEffects.VyelHell);
        addAudio(SoundEffects.VyelHit1);
        addAudio(SoundEffects.VyelHit2);
        addAudio(SoundEffects.yelIgnite);
        addAudio(SoundEffects.yelPort);
        //raid
        addAudio(SoundEffects.Vraid1);
        addAudio(SoundEffects.VraidHit1);
        addAudio(SoundEffects.VraidOver);
        addAudio(SoundEffects.raidHit01);
        addAudio(SoundEffects.raidHit02);
        addAudio(SoundEffects.raidHit03);
        addAudio(SoundEffects.raidPort);
        addAudio(SoundEffects.raidRapid2);
        addAudio(SoundEffects.raidRapidBig);
        //SK
        addAudio(SoundEffects.SKexcellent);
        addAudio(SoundEffects.SKhammer);
        addAudio(SoundEffects.SKhit);
        addAudio(SoundEffects.SKlaugh);
        addAudio(SoundEffects.SKpathetic);
        addAudio(SoundEffects.SKsuck);
        addAudio(SoundEffects.SKtrying);
        addAudio(SoundEffects.SKwin);
        addAudio(SoundEffects.Skscream);

    }


    private void addAudio(Pair<String, String> audioData)
    {
        BaseMod.addAudio(audioData.getKey(), audioData.getValue());
    }
    
    
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potion. If you want your potion to not be class-specific,
        // just remove the player class at the end (in this case the "TheDefaultEnum.THE_DEFAULT".
        // Remember, you can press ctrl+P inside parentheses like addPotions)
        //BaseMod.addPotion(PlaceholderPotion.class, PLACEHOLDER_POTION_LIQUID, PLACEHOLDER_POTION_HYBRID, PLACEHOLDER_POTION_SPOTS, PlaceholderPotion.POTION_ID, TheKombatant.Enums.THE_KOMBATANT);
        
        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");
        
        // This adds a character specific relic. Only when you play with the mentioned color, will you get this relic.
        //BaseMod.addRelicToCustomPool(new PlaceholderRelic(), TheKombatant.Enums.COLOR_SLATE);
        //BaseMod.addRelicToCustomPool(new ShinnoksAmuletRelic(), TheKombatant.Enums.COLOR_SLATE);
        BaseMod.addRelicToCustomPool(new Shinnoks2ndAmuletRelic(), TheKombatant.Enums.COLOR_SLATE);
        BaseMod.addRelicToCustomPool(new JinseiRelic(), TheKombatant.Enums.COLOR_SLATE);
        BaseMod.addRelicToCustomPool(new BookofBoonRelic(), TheKombatant.Enums.COLOR_SLATE);
        BaseMod.addRelicToCustomPool(new TasteofSaltRelic(), TheKombatant.Enums.COLOR_SLATE);
        BaseMod.addRelicToCustomPool(new CoreCyraxRelic(), TheKombatant.Enums.COLOR_SLATE);
        BaseMod.addRelicToCustomPool(new CoreSektorRelic(), TheKombatant.Enums.COLOR_SLATE);
        BaseMod.addRelicToCustomPool(new CoreSmokeRelic(), TheKombatant.Enums.COLOR_SLATE);
       // BaseMod.addRelicToCustomPool(new BottledPlaceholderRelic(), TheKombatant.Enums.COLOR_SLATE);
        //BaseMod.addRelicToCustomPool(new DefaultClickableRelic(), TheKombatant.Enums.COLOR_SLATE);
        
        // This adds a relic to the Shared pool. Every character can find this relic.
        //BaseMod.addRelic(new PlaceholderRelic2(), RelicType.SHARED);
        
        // Mark relics as seen (the others are all starters so they're marked as seen in the character file
        //UnlockTracker.markRelicAsSeen(BottledPlaceholderRelic.ID);
        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        //Ignore this
        pathCheck();
        // Add the Custom Dynamic Variables
        logger.info("Add variabls");
        // Add the Custom Dynamic variabls
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new DefaultSecondMagicNumber());
        
        logger.info("Adding cards");
        // Add the cards
        // Don't comment out/delete these cards (yet). You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        BaseMod.addCard(new attAcidSlide());
        BaseMod.addCard(new attElderStorm());
        BaseMod.addCard(new attArcticAssault());
        BaseMod.addCard(new attLowKick());
        BaseMod.addCard(new attTeleslam());
        BaseMod.addCard(new attBicycleKick());
        BaseMod.addCard(new skillIceBall());
        BaseMod.addCard(new attIceSlide());
        BaseMod.addCard(new attSoulBall());
        BaseMod.addCard(new attCertainDeath());
        BaseMod.addCard(new attUppercut());
        BaseMod.addCard(new attColdEncounter());
        BaseMod.addCard(new attJudgement());
        BaseMod.addCard(new attRoundhouse());
        BaseMod.addCard(new attAcidSpit());
        BaseMod.addCard(new attCurse());
        BaseMod.addCard(new attTouchofRain());
        BaseMod.addCard(new attColdBlooded());
        BaseMod.addCard(new attEvilTwin());
        BaseMod.addCard(new attFannado());
        BaseMod.addCard(new attDamnation());
        BaseMod.addCard(new attSheddingSkin());
        BaseMod.addCard(new attQuickDraw());
        BaseMod.addCard(new attPosessed());
        BaseMod.addCard(new attGodFist());
        BaseMod.addCard(new attRekindle());
        BaseMod.addCard(new attGravedigger());
        BaseMod.addCard(new attWeWin());
        BaseMod.addCard(new attShortHop());
        BaseMod.addCard(new attThrow());
        BaseMod.addCard(new attSoulChoke());
        BaseMod.addCard(new attRisingThunder());
        BaseMod.addCard(new attFrostBitten());
        BaseMod.addCard(new attSwampStrikes());
        BaseMod.addCard(new attGodsRage());
        BaseMod.addCard(new attWaterfall());
        BaseMod.addCard(new attWhiteLightning());
        BaseMod.addCard(new attFlamePort());
        BaseMod.addCard(new attFatality());
        BaseMod.addCard(new attFlyingThundergod());
        BaseMod.addCard(new attHighStrike_s());
        BaseMod.addCard(new attYouSuck());
        BaseMod.addCard(new attFaceEater());

        BaseMod.addCard(new pwrSmokescreen());
        BaseMod.addCard(new pwrSecretShang());
        BaseMod.addCard(new pwrHatTricks());
        BaseMod.addCard(new pwrDrunkenFist());
        BaseMod.addCard(new pwrToxicAir());
        BaseMod.addCard(new pwrMeatandGreet());
        BaseMod.addCard(new pwrEtherealSais());
        BaseMod.addCard(new pwrTarkatanRage());
        BaseMod.addCard(new pwrElderGodsCouncil());
        BaseMod.addCard(new skillSpear());
        BaseMod.addCard(new skillStealth());
        BaseMod.addCard(new skillCyberInitiative());
        BaseMod.addCard(new skillFlameFists());
        BaseMod.addCard(new skillHighGarrote());
        BaseMod.addCard(new skillSmokeParry());
        BaseMod.addCard(new skillLowParry());
        BaseMod.addCard(new skillUndyingBlaze());
        BaseMod.addCard(new skillReflect());
        BaseMod.addCard(new skillDarkness());
        BaseMod.addCard(new skillFaceReveal());
        BaseMod.addCard(new skillBreaker());
        BaseMod.addCard(new skillEnenra());
        BaseMod.addCard(new skillRunCancel());
        BaseMod.addCard(new skillKenshisSpirits());
        BaseMod.addCard(new skillRestand());
        BaseMod.addCard(new skillForceBubble());
        BaseMod.addCard(new skillIceKlone());
        BaseMod.addCard(new skillDodgeJump());
        BaseMod.addCard(new skillWhiffPunish());
        BaseMod.addCard(new skillExtraShades());
        BaseMod.addCard(new skillHellfire());
        BaseMod.addCard(new skillGuard_s());
        BaseMod.addCard(new skillLightningPort());

        
        logger.info("Making sure the cards are unlocked.");
        // Unlock the cards
        // This is so that they are all "seen" in the library, for people who like to look at the card list
        // before playing your mod.
        UnlockTracker.unlockCard(skillWhiffPunish.ID);

        UnlockTracker.unlockCard(skillReflect.ID);
        UnlockTracker.unlockCard(skillIceKlone.ID);
        UnlockTracker.unlockCard(skillExtraShades.ID);
        UnlockTracker.unlockCard(skillForceBubble.ID);
        UnlockTracker.unlockCard(skillBreaker.ID);
        UnlockTracker.unlockCard(skillFaceReveal.ID);
        UnlockTracker.unlockCard(skillSpear.ID);
        UnlockTracker.unlockCard(skillLowParry.ID);
        UnlockTracker.unlockCard(skillDarkness.ID);
        UnlockTracker.unlockCard(skillHellfire.ID);
        UnlockTracker.unlockCard(skillRunCancel.ID);
        UnlockTracker.unlockCard(skillHighGarrote.ID);
        UnlockTracker.unlockCard(skillLightningPort.ID);
        UnlockTracker.unlockCard(skillRestand.ID);
        UnlockTracker.unlockCard(skillEnenra.ID);
        UnlockTracker.unlockCard(skillKenshisSpirits.ID);
        UnlockTracker.unlockCard(skillSmokeParry.ID);
        UnlockTracker.unlockCard(skillDodgeJump.ID);
        UnlockTracker.unlockCard(skillUndyingBlaze.ID);

        UnlockTracker.unlockCard(attFrostBitten.ID);
        UnlockTracker.unlockCard(attUppercut.ID);
        UnlockTracker.unlockCard(attAcidSlide.ID);
        UnlockTracker.unlockCard(attElderStorm.ID);
        UnlockTracker.unlockCard(attSoulChoke.ID);
        UnlockTracker.unlockCard(attQuickDraw.ID);
        UnlockTracker.unlockCard(attAcidSpit.ID);
        //UnlockTracker.unlockCard(attBicycleKick.ID);
        UnlockTracker.unlockCard(attRisingThunder.ID);
        UnlockTracker.unlockCard(attCurse.ID);
        UnlockTracker.unlockCard(attTouchofRain.ID);
        UnlockTracker.unlockCard(attFatality.ID);
        //UnlockTracker.unlockCard(attFaceEater.ID);
        UnlockTracker.unlockCard(attFlyingThundergod.ID);
        UnlockTracker.unlockCard(attTeleslam.ID);
        //UnlockTracker.unlockCard(attYouSuck.ID);
        UnlockTracker.unlockCard(attEvilTwin.ID);
        UnlockTracker.unlockCard(attCertainDeath.ID);
        UnlockTracker.unlockCard(attColdBlooded.ID);
        UnlockTracker.unlockCard(attColdEncounter.ID);
        UnlockTracker.unlockCard(attArcticAssault.ID);
        UnlockTracker.unlockCard(attGodFist.ID);
        UnlockTracker.unlockCard(attGodsRage.ID);
        UnlockTracker.unlockCard(attGravedigger.ID);
        UnlockTracker.unlockCard(attWeWin.ID);
        UnlockTracker.unlockCard(attSwampStrikes.ID);
        UnlockTracker.unlockCard(attRoundhouse.ID);
        UnlockTracker.unlockCard(attJudgement.ID);
        UnlockTracker.unlockCard(attFannado.ID);
        UnlockTracker.unlockCard(attSheddingSkin.ID);
        UnlockTracker.unlockCard(attRekindle.ID);
        UnlockTracker.unlockCard(skillIceBall.ID);
        UnlockTracker.unlockCard(attIceSlide.ID);
        UnlockTracker.unlockCard(attDamnation.ID);
        UnlockTracker.unlockCard(attFlamePort.ID);
        UnlockTracker.unlockCard(attPosessed.ID);
        UnlockTracker.unlockCard(attShortHop.ID);
        UnlockTracker.unlockCard(attSoulBall.ID);
        UnlockTracker.unlockCard(attWaterfall.ID);
        UnlockTracker.unlockCard(attWhiteLightning.ID);

        UnlockTracker.unlockCard(pwrEtherealSais.ID);
        UnlockTracker.unlockCard(pwrMeatandGreet.ID);
        UnlockTracker.unlockCard(pwrHatTricks.ID);
        UnlockTracker.unlockCard(pwrToxicAir.ID);
        UnlockTracker.unlockCard(pwrDrunkenFist.ID);
        UnlockTracker.unlockCard(pwrSecretShang.ID);
        UnlockTracker.unlockCard(pwrSmokescreen.ID);


       // UnlockTracker.unlockCard(pwrTarkatanRage.ID);








        //UnlockTracker.unlockCard(skillStealth.ID);



        logger.info("Done adding cards!");
    }




    public void receiveSetUnlocks() {
        BaseMod.addUnlockBundle(new CustomUnlockBundle(attYouSuck.ID, attBicycleKick.ID, attFaceEater.ID), TheKombatant.Enums.THE_KOMBATANT, 0);
        UnlockTracker.addCard(attYouSuck.ID);
        UnlockTracker.addCard(attBicycleKick.ID);
        UnlockTracker.addCard(attFaceEater.ID);
        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC, JinseiRelic.ID, BookofBoonRelic.ID, TasteofSaltRelic.ID), TheKombatant.Enums.THE_KOMBATANT, 1);
        UnlockTracker.addRelic(JinseiRelic.ID);
        UnlockTracker.addRelic(BookofBoonRelic.ID);
        UnlockTracker.addRelic(TasteofSaltRelic.ID);
        BaseMod.addUnlockBundle(new CustomUnlockBundle(skillStealth.ID, skillFlameFists.ID, skillCyberInitiative.ID), TheKombatant.Enums.THE_KOMBATANT, 2);
        UnlockTracker.addCard(skillStealth.ID);
        UnlockTracker.addCard(skillFlameFists.ID);
        UnlockTracker.addCard(skillCyberInitiative.ID);
        BaseMod.addUnlockBundle(new CustomUnlockBundle(AbstractUnlock.UnlockType.RELIC, CoreCyraxRelic.ID, CoreSektorRelic.ID, CoreSmokeRelic.ID),TheKombatant.Enums.THE_KOMBATANT, 3);
        UnlockTracker.addRelic(CoreCyraxRelic.ID);
        UnlockTracker.addRelic(CoreSektorRelic.ID);
        UnlockTracker.addRelic(CoreSmokeRelic.ID);
        BaseMod.addUnlockBundle(new CustomUnlockBundle(pwrElderGodsCouncil.ID, pwrElderGodsCouncil.ID, pwrElderGodsCouncil.ID), TheKombatant.Enums.THE_KOMBATANT, 4);
        UnlockTracker.addCard(pwrElderGodsCouncil.ID);
        UnlockTracker.addCard(pwrElderGodsCouncil.ID);
        UnlockTracker.addCard(pwrElderGodsCouncil.ID);



    }
    
    // There are better ways to do this than listing every single individual card, but I do not want to complicate things
    // in a "tutorial" mod. This will do and it's completely ok to use. If you ever want to clean up and
    // shorten all the imports, go look take a look at other mods, such as Hubris.
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/theKombatant-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/theKombatant-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/theKombatant-Relic-Strings.json");

        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/theKombatant-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/theKombatant-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/theKombatant-Character-Strings.json");
        // Monster Strings
        BaseMod.loadCustomStringsFile(MonsterStrings.class,
                getModID() + "Resources/localization/eng/theKombatant-Monster-Strings.json");
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/theKombatant-Orb-Strings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/eng/theKombatant-UI-Strings.json");
        
        logger.info("Done edittting strings");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================
    
    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/theKombatant-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
