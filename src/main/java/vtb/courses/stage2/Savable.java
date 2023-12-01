package vtb.courses.stage2;

import java.io.InvalidClassException;

/**
 * Данный интерфейс должны поддерживать объекты,
 * для которых необходимо сохранять и в последующем восстанавливать
 * одно из ранее сохранённых состояний.
 */

public interface Savable {
    public Object getSave();
    public void restoreSave(Object save) throws InvalidClassException;
}
