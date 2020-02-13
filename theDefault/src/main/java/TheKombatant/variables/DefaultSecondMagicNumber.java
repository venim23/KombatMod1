package TheKombatant.variables;

import TheKombatant.cards.AbstractKombatCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static TheKombatant.Kombatmod.makeID;

public class DefaultSecondMagicNumber extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("SecondMagic");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "TheKombatant:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractKombatCard) card).isDefaultSecondMagicNumberModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractKombatCard) card).defaultSecondMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractKombatCard) card).defaultBaseSecondMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractKombatCard) card).upgradedDefaultSecondMagicNumber;
    }
}