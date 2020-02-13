package TheKombatant.cards;

import TheKombatant.Kombatmod;
import TheKombatant.util.Utilities;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;
import java.util.HashMap;

public class CardHeaders {
    public static final String ID = Kombatmod.makeID("CardHeaders");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    private final static HashMap<Integer, CardHeader> All = new HashMap<>();

    public final static CardHeader None = CreateCardHeader(0);
    public final static CardHeader Special = CreateCardHeader(1);
    public final static CardHeader Combo = CreateCardHeader(2);
    public final static CardHeader SpecialCombo = CreateCardHeader(3);


    private static CardHeader CreateCardHeader(int id)
    {
        CardHeader s = new CardHeader(id, uiStrings.TEXT[id]);
        All.put(id, s);
        return s;
    }

    public static CardHeader GetByID(int id)
    {
        return All.get(id);
    }

    public static int Count()
    {
        return All.size() - 1;
    }

    public static ArrayList<AbstractKombatCard> GetAbstractKombatCards()
    {
        ArrayList<AbstractKombatCard> result = new ArrayList<>();
        AddAbstractKombatCards(AbstractDungeon.srcCommonCardPool.group, result);
        AddAbstractKombatCards(AbstractDungeon.srcUncommonCardPool.group, result);
        AddAbstractKombatCards(AbstractDungeon.srcRareCardPool.group, result);

        return result;
    }

    public static ArrayList<AbstractKombatCard> GetCardsWithCardHeader(CardHeader cardheader)
    {
        ArrayList<AbstractKombatCard> result = new ArrayList<>();
        AddCardsWithCardHeader(cardheader, AbstractDungeon.srcCommonCardPool.group, result);
        AddCardsWithCardHeader(cardheader, AbstractDungeon.srcUncommonCardPool.group, result);
        AddCardsWithCardHeader(cardheader, AbstractDungeon.srcRareCardPool.group, result);

        return result;
    }


    public static void AddAbstractKombatCards(ArrayList<AbstractCard> source, ArrayList<AbstractKombatCard> destination)
    {
        for (AbstractCard c : source)
        {
            AbstractKombatCard card = Utilities.SafeCast(c, AbstractKombatCard.class);
            if (card != null)
            {
                destination.add(card);
            }
        }
    }

    public static void AddCardsWithCardHeader(CardHeader cardheader, ArrayList<AbstractCard> source, ArrayList<AbstractKombatCard> destination)
    {
        for (AbstractCard c : source)
        {
            AbstractKombatCard card = Utilities.SafeCast(c, AbstractKombatCard.class);
            if (card != null && cardheader.Equals(card.GetCardHeader()))
            {
                destination.add(card);
            }
        }
    }
}
