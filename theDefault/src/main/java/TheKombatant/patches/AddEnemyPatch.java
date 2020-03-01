package TheKombatant.patches;

import TheKombatant.characters.TheKombatant;
import TheKombatant.monsters.bossShaoKahn;
import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheBeyond;

@SpirePatch(clz = AbstractDungeon.class, method = "initializeCardPools")

public class AddEnemyPatch {
    public static void Prefix(final AbstractDungeon dungeon_instance) {
        if ((AbstractDungeon.player instanceof TheKombatant)) {
            if (!AbstractDungeon.bossList.contains(bossShaoKahn.ID)) {
                BaseMod.addBoss(TheBeyond.ID, bossShaoKahn.ID, "TheKombatantResources/images/ui/map/ShaoKahnIcon.png", "TheKombatantResources/images/ui/map/bossIcon-outline.png");
            }
        }
    }
}