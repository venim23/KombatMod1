package TheKombatant.patches;

import TheKombatant.Kombatmod;
import com.badlogic.gdx.audio.Music;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.audio.MainMusic;
import com.megacrit.cardcrawl.audio.TempMusic;

@SpirePatch(
        clz = TempMusic.class,
        method = "getSong")

public class BossMusicPatch {


    @SpirePostfixPatch
    public static SpireReturn<Music> Prefix(TempMusic __instance, String key) {
        Kombatmod.logger.info("Music patch Temp hit");
        switch (key) {
            case "BOSS_KAHN": {
                return SpireReturn.Return(MainMusic.newMusic("TheKombatantResources/audio/sounds/MKtheme2.ogg"));
            }
            default: {
                return SpireReturn.Continue();
            }
        }

    }

}

