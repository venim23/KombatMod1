package TheKombatant.monsters;

import TheKombatant.Kombatmod;
import TheKombatant.actions.SFXVAction;
import TheKombatant.powers.ImpaledPower;
import TheKombatant.util.SoundEffects;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.combat.*;

public class bossShaoKahn extends AbstractMonster {

    public static final String ID = Kombatmod.makeID("ShaoKahn");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStrings.DIALOG;
    // ****** MOVE AND STAT VALUES ********//
    private int ShoulderBlock = 20;
    private int baseShoulderDmg = 15;
    private int BlastAmt = 2;
    private int baseHammerDmg = 25;
    private int baseKickDmg = 15;
    private int baseKickHits = 2;
    private int SpearAmt = 5;
    private int SpearTurns = 0;
    private int LaughArmor = 6;
    private int LaughStrength = 3;
    private int maxHP = 600;
    private int minHP = 550;
    private int numTurns = 0;
    // ******* END OF MOVE AND STAT VALUES *********//


    //LIST THE ANIM NAMES HERE IN COMMENTS FOR EASY MEMORY//
    private String animIdle = "idle";
    private String animHammer = "hammer";
    private String animShoulder = "shoulder";
    private String animKick = "kick";
    private String animSpear = "spear";
    private String animLaugh= "laugh";
    private String animBlast = "blast";


    // the Main "Constructer" iirc that's what the big method/function that the class relies upon is called.
    public bossShaoKahn()
    {
        //the stuff that gets sent up the line to AbstractMonster to do what it does
    /*here's what these refer to: (final String name, final String id, final int maxHealth, final float hb_x, final float hb_y, final float hb_w, final float hb_h, final String imgUrl,


	final float offsetX, final float offsetY,
	final boolean ignoreBlights: Not included as false by default?
	*/
        super(NAME, ID, 600, 0, 0, 400.0F, 400.0F, null, 1.0F, 1.0F);
        this.type = AbstractMonster.EnemyType.BOSS;

        if (AbstractDungeon.ascensionLevel >=9)
        {
            //For monsters encountered at higher ascension levels adds a bit more HP
            this.minHP += 25;
            this.maxHP += 25;

        }
        if (AbstractDungeon.ascensionLevel >=4)
        {

            this.baseKickHits += 1;
            this.baseHammerDmg += 5;
            this.ShoulderBlock += 5;
            //increases the power of his multihit and debufff for higher ascensions

        }
        if (AbstractDungeon.ascensionLevel >= 19)
        {
            this.SpearAmt+=1;

        }
        //set the min and max hp bounds,
        setHp(this.minHP, this.maxHP);
        //****** DAMAGE INFO ARRAYS? **** //
        //creates a list 0,1,2 of damageinfos to pull from for later.
        this.damage.add(new DamageInfo(this, this.baseKickDmg)); // attack 0 damage
        this.damage.add(new DamageInfo(this, this.baseHammerDmg)); // attack 1 damage
        this.damage.add(new DamageInfo(this, this.baseShoulderDmg)); //attack 2 damagee
        // **** END ARRAYS **** //
        loadAnimation(
                //loads the animation
                Kombatmod.makeMonsterPath("shaokahn/ShaoKahn.atlas"),
                Kombatmod.makeMonsterPath("shaokahn/ShaoKahn.json"), 1.1F);
        //starts the animation called idle i think, im unsire of the 1st variable, but i think the second is the animation name, and the 3rd is a boolean for islooping?
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        //no idea
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    @Override
    public void usePreBattleAction() {

        AbstractDungeon.scene.fadeOutAmbiance();
        CardCrawlGame.music.playTempBgmInstantly("BOSS_KAHN", true);
        CardCrawlGame.sound.playV(SoundEffects.SKsuck.getKey(),2.0F);
    }

    //take turn is actually one of the later things to occur, it happens AFTER the action has been decided and displayed for a turn as intent. deciding the move happens in GetMove
    public void takeTurn()
    {


        /*
         public static final byte KICK = 0; //X
        public static final byte SHOULDER = 1; //X
        public static final byte LAUGH = 2; //X
        public static final byte SPEAR = 3; //X
        public static final byte HAMMER= 4; //X
        public static final byte BLAST= 5;

         */

        //Just a handy little shortener Blank seems to use to make writing the actions easier
        AbstractPlayer p = AbstractDungeon.player;
        //very simple, it checks what you've assinged as .nextMove's value. that happens in getMove
        switch (this.nextMove)
        {
            case 0:  // kicks
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "KICK"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.7f));
                for (int i = 0; i < this.baseKickHits; ++i) {
                    AbstractDungeon.actionManager.addToBottom(new DamageAction(p, this.damage.get(0), AbstractGameAction.AttackEffect.BLUNT_LIGHT, false));
                    AbstractDungeon.actionManager.addToBottom(new WaitAction(0.7f));
                }
                break;
            case 1: //tShoulder CHarge
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "SHOULDER"));
                AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.SKexcellent.getKey(), 2.0F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(p, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this, this, this.ShoulderBlock));
                break;
            case 2: //calls attack 2 damage info
                AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.SKlaugh.getKey(), 2.0F));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "LAUGH"));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, this.LaughArmor), this.LaughArmor));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, this.LaughStrength), this.LaughStrength));
                break;
            case 3: //SPEAR
                AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.SKtrying.getKey(), 2.0F));
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "SPEAR"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(new CleaveEffect(true), 0.2f));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, this, new ImpaledPower(p, this, this.SpearAmt), this.SpearAmt));
                break;
            case 4: //HAMMER
                //add SFX later
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "HAMMER"));
                AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
                AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.SKhammer.getKey(), 2.0F));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(p, this.damage.get(1), AbstractGameAction.AttackEffect.SMASH));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FrailPower(p, 1, true),1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new WeakPower(p, 1, true),1));
                break;
            case 5: //BLAST
                //add SFX later\
                AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "BLAST"));
                AbstractDungeon.actionManager.addToBottom(new SFXVAction(SoundEffects.SKpathetic.getKey(), 2.0F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new FireballEffect(this.hb.cX , this.hb.cY, p.hb.cX , p.hb.cY), 0.2F));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new SwirlyBloodEffect(p.hb.cX , p.hb.cY), 0.0F));
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Wound(),2));
                break;
        }
        //unsure here, I think it basically  uses an action to send this monsters data to the AbstractMonster.rollMove , which activates the monsterFalseKnight.getMove and sends a rng amount?
        //this seems to basically be the "get the intent for the next turn's move thing"
        //unsure here, I think it basically  uses an action to send this monsters data to the AbstractMonster.rollMove , which activates the DefaultMonster.getMove and sends a rng amount?
        //this seems to basically be the "get the intent for the next turn's move thing"
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    //this is the method that the ChangeStateAction requires within the class of any monster thant calls it.
    public void changeState(String key)
    {
        switch (key)
        {
            //for each key, it has a simple little transition between animations,
            //for this example, sets the animation to attack, and not looping, then adds the looping idle animation as next in line.
            case "KICK":
                this.state.setAnimation(0, animKick, false);
                this.state.addAnimation(0, animKick, false, 0.70F);
                if (AbstractDungeon.ascensionLevel >=4){
                    this.state.addAnimation(0, animKick, false, 1.40F);
                }
                this.state.addAnimation(0, animIdle, true, 0.0F);
                break;
            case "HAMMER":
                this.state.setAnimation(0, animHammer, false);
                this.state.addAnimation(0, animIdle, true, 0.0F);
                break;
            case "LAUGH":
                this.state.setAnimation(0, animLaugh, false);
                this.state.addAnimation(0, animIdle, true, 0.0F);
                break;
            case "SPEAR":
                this.state.setAnimation(0, animSpear, false);
                this.state.addAnimation(0, animIdle, true, 0.0F);
                break;
            case "BLAST":
                this.state.setAnimation(0, animBlast, false);
                this.state.addAnimation(0, animIdle, true, 0.0F);
                break;
            case "SHOULDER":
                this.state.setAnimation(0, animShoulder, false);
                this.state.addAnimation(0, animIdle, true, 0.0F);
                break;
        }
    }
    //Unsure, but I think this handles the event of Taking damage, not sure if it's needed or not.
    //basically works just like the change state attack, the oof animation plays once. then it sets the looping idle animation to play again afterwards.
  /*  public void damage(DamageInfo info)
    {
        super.damage(info);
        //just checks to make sure the attack came from the plaer basically.
        if ((info.owner != null) && (info.type != DamageInfo.DamageType.THORNS) && (info.output > 0))
        {
            this.state.setAnimation(0, "oof", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
        }
    }
*/    //This is where the monster recieves a roll between 0 and 99 (so a full 1/100 chances is easily done) the getMove method uses that number to determine probability of assigning a specific action
    //
    protected void getMove(int i) {
        numTurns++;
        SpearTurns++;

        //third turn do spear move
        if ((this.numTurns == 3) || (SpearTurns == 4)) {
            setMove(MOVES[3], (byte) 3, Intent.MAGIC);
            SpearTurns = 0;
            return;
        }
        //if below half hp, start doing this move
        if ((this.currentHealth <= (this.maxHP / 2)) && (i < 20) && (!this.lastMove((byte) 4))) {
            setMove(MOVES[4], (byte) 4, Intent.ATTACK_DEBUFF, ((DamageInfo) this.damage.get(1)).base);
        } else if ((i < 30) && (!this.lastMove((byte) 0))) {
            setMove((byte) 0, Intent.ATTACK, ((DamageInfo) this.damage.get(0)).base, this.baseKickHits, true);
        } else if ((i < 55) && (!this.lastMove((byte) 5))& (!lastMoveBefore((byte) 5))) {
            setMove(MOVES[5],(byte) 5, Intent.STRONG_DEBUFF);
        } else if ((this.numTurns > 3) && ((i < 75) && (!lastMove((byte) 2)) && (!lastMoveBefore((byte) 2)))) {
            setMove((byte) 2, Intent.BUFF);
        } else {
            setMove((byte) 1, Intent.ATTACK_DEFEND, ((DamageInfo) this.damage.get(2)).base);
        }
    }

    public void die() {
        this.state.setTimeScale(0.1f);
        this.useShakeAnimation(5.0f);
        super.die();
        this.onBossVictoryLogic();
    }

    //Assigns byte values to the attack names. I can't find this directly called, maybe it's just put in the output for debugging
    public static class MoveBytes
    {
        public static final byte KICK = 0; //X
        public static final byte SHOULDER = 1; //X
        public static final byte LAUGH = 2; //X
        public static final byte SPEAR = 3; //X
        public static final byte HAMMER= 4; //X
        public static final byte BLAST= 5;

    }



}

