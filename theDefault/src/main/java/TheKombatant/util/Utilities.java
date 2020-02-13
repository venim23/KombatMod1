package TheKombatant.util;

public class Utilities {
    public static <T> T SafeCast(Object o, Class<T> type)
    {
        return type.isInstance(o) ? type.cast(o) : null;
    }
}
