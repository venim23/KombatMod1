package TheKombatant.patches;

import TheKombatant.characters.TheKombatant;
import TheKombatant.monsters.bossShaoKahn;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;

@SpirePatch(clz = AbstractDungeon.class, method = "initializeBoss")

public class RemoveEnemyPatch {
    public static void PostFix(final AbstractDungeon dungeon_instance) {
        if (!(AbstractDungeon.player instanceof TheKombatant)) {
                AbstractDungeon.bossList.remove(bossShaoKahn.ID);
        }

    }
}
