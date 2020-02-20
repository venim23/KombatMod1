package TheKombatant.util;

import javafx.util.Pair;
import org.apache.logging.log4j.core.jmx.RingBufferAdmin;

import static TheKombatant.Kombatmod.makeAudioPath;

public class SoundEffects {
    public static final Pair<String, String> toasty = new Pair<>("Kombatmod:toasty", makeAudioPath("toasty.ogg"));
    public static final Pair<String, String> Spear = new Pair<>("Kombatmod:Spear", makeAudioPath("getoverhere.ogg"));
    public static final Pair<String, String> Fight = new Pair<>("Kombatmod:Fight", makeAudioPath("fight.ogg"));
    public static final Pair<String, String> Fatality = new Pair<>("Kombatmod:Fatality", makeAudioPath("fatality.ogg"));
    public static final Pair<String, String> Suck = new Pair<>("Kombatmod:Suck", makeAudioPath("youSuck.ogg"));
    public static final Pair<String, String> SpearThrow = new Pair<>("Kombatmod:SpearThrow", makeAudioPath("spearToss.ogg"));
    public static final Pair<String, String> Slide = new Pair<>("Kombatmod:Slide", makeAudioPath("slide.ogg"));
    public static final Pair<String, String> Spit = new Pair<>("Kombatmod:Spit", makeAudioPath("repSpit.ogg"));
    public static final Pair<String, String> Port = new Pair<>("Kombatmod:Port", makeAudioPath("lightport.ogg"));
    public static final Pair<String, String> Ice = new Pair<>("Kombatmod:Ice", makeAudioPath("ice.ogg"));
    public static final Pair<String, String> Shock1 = new Pair<>("Kombatmod:Shock1", makeAudioPath("smallShock.ogg"));
    public static final Pair<String, String> Shock2 = new Pair<>("Kombatmod:Shock2", makeAudioPath("bigShock.ogg"));
    public static final Pair<String, String> Flying = new Pair<>("Kombatmod:Flying", makeAudioPath("wololo.ogg"));
    public static final Pair<String, String> Kombo = new Pair<>("Kombatmod:Kombo", makeAudioPath("kombo.ogg"));
    //Red
    public static final Pair<String, String> VredDie = new Pair<>("Kombatmod:VredDie", makeAudioPath("VredDie.ogg"));
    public static final Pair<String, String> VredMany = new Pair<>("Kombatmod:VredMany", makeAudioPath("VredMany.ogg"));
    public static final Pair<String, String> VredDefy = new Pair<>("Kombatmod:VredDefy", makeAudioPath("VredDefy.ogg"));
    public static final Pair<String, String> VredOne = new Pair<>("Kombatmod:VredOne", makeAudioPath("VredOne.ogg"));
    public static final Pair<String, String> redBall = new Pair<>("Kombatmod:redBall", makeAudioPath("redBall.ogg"));
    public static final Pair<String, String> redChoke = new Pair<>("Kombatmod:redChoke", makeAudioPath("redChoke.ogg"));
    public static final Pair<String, String> redStrike1 = new Pair<>("Kombatmod:redStrike1", makeAudioPath("redStrike1.ogg"));
    public static final Pair<String, String> redStrike2 = new Pair<>("Kombatmod:redStrike2", makeAudioPath("redStrike2.ogg"));
    //blue
    public static final Pair<String, String> VblueEnd = new Pair<>("Kombatmod:VblueEnd", makeAudioPath("VblueEnd.ogg"));
    public static final Pair<String, String> VblueHit1 = new Pair<>("Kombatmod:VblueHit1", makeAudioPath("VblueHit1.ogg"));
    public static final Pair<String, String> VblueLin = new Pair<>("Kombatmod:VblueLin", makeAudioPath("VblueLin.ogg"));
    public static final Pair<String, String> VblueWeaker = new Pair<>("Kombatmod:VblueWeaker", makeAudioPath("VblueWeaker.ogg"));
    public static final Pair<String, String> blueIceball = new Pair<>("Kombatmod:blueIceball", makeAudioPath("blueIceBall.ogg"));
    public static final Pair<String, String> blueKlone = new Pair<>("Kombatmod:blueKlone", makeAudioPath("blueKlone.ogg"));
    public static final Pair<String, String> blueSlide = new Pair<>("Kombatmod:blueSlide", makeAudioPath("blueSlide.ogg"));
    public static final Pair<String, String> blueStabs = new Pair<>("Kombatmod:blueStabs", makeAudioPath("blueStabs.ogg"));
    public static final Pair<String, String> blueShatter = new Pair<>("Kombatmod:blueShatter", makeAudioPath("blueShatter.ogg"));
    //green
    public static final Pair<String, String> VgreenSpit = new Pair<>("Kombatmod:VgreenSpit", makeAudioPath("VgreenSpit.ogg"));
    public static final Pair<String, String> VgreenSnarl = new Pair<>("Kombatmod:VgreenSnarl", makeAudioPath("VgreenSnarl.ogg"));
    public static final Pair<String, String> VgreenBurns = new Pair<>("Kombatmod:VgreenBurns", makeAudioPath("VgreenBurns.ogg"));
    public static final Pair<String, String> VgreenBite = new Pair<>("Kombatmod:VgreenBite", makeAudioPath("VgreenBite.ogg"));
    public static final Pair<String, String> greenSlide = new Pair<>("Kombatmod:greenSlide", makeAudioPath("greenSlide.ogg"));
    public static final Pair<String, String> greenSpit1 = new Pair<>("Kombatmod:greenSpit1", makeAudioPath("greenSpit1.ogg"));
    public static final Pair<String, String> greenStealth = new Pair<>("Kombatmod:greenStealth", makeAudioPath("greenStealth.ogg"));
    public static final Pair<String, String> greenCold = new Pair<>("Kombatmod:greenCold", makeAudioPath("greenCold.ogg"));
    public static final Pair<String, String> greenForce = new Pair<>("Kombatmod:greenForce", makeAudioPath("greenForce.ogg"));
    public static final Pair<String, String> greenReveal = new Pair<>("Kombatmod:greenReveal", makeAudioPath("greenReveal.ogg"));
    //yellow
    public static final Pair<String, String> VyelBurn = new Pair<>("Kombatmod:VyelBurn", makeAudioPath("VyelBurn.ogg"));
    public static final Pair<String, String> VyelHell = new Pair<>("Kombatmod:VyelHell", makeAudioPath("VyelHell.ogg"));
    public static final Pair<String, String> VyelHit1 = new Pair<>("Kombatmod:VyelHit1", makeAudioPath("VyelHit1.ogg"));
    public static final Pair<String, String> VyelHit2 = new Pair<>("Kombatmod:VyelHit2", makeAudioPath("VyelHit2.ogg"));
    public static final Pair<String, String> yelIgnite = new Pair<>("Kombatmod:yelIgnite", makeAudioPath("yelIgnite.ogg"));
    public static final Pair<String, String> yelPort = new Pair<>("Kombatmod:yelPort", makeAudioPath("yelFport.ogg"));
    //Raid
    public static final Pair<String, String> Vraid1 = new Pair<>("Kombatmod:Vraid1", makeAudioPath("Vraid1.ogg"));
    public static final Pair<String, String> VraidHit1 = new Pair<>("Kombatmod:VraidHit1", makeAudioPath("VraidHit1.ogg"));
    public static final Pair<String, String> VraidOver = new Pair<>("Kombatmod:VraidOver", makeAudioPath("VraidOver.ogg"));
    public static final Pair<String, String> raidPort = new Pair<>("Kombatmod:raidPort", makeAudioPath("raidPort.ogg"));
    public static final Pair<String, String> raidHit01 = new Pair<>("Kombatmod:raidHit01", makeAudioPath("raidHit01.ogg"));
    public static final Pair<String, String> raidHit02 = new Pair<>("Kombatmod:raidHit02", makeAudioPath("raidHit02.ogg"));
    public static final Pair<String, String> raidHit03 = new Pair<>("Kombatmod:raidHit03", makeAudioPath("raidHit03.ogg"));
    public static final Pair<String, String> raidRapid2 = new Pair<>("Kombatmod:raidRapid2", makeAudioPath("raidRapid2.ogg"));
    public static final Pair<String, String> raidRapidBig = new Pair<>("Kombatmod:raidRapidBig", makeAudioPath("raidRapidBig.ogg"));









}
