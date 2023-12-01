package vtb.courses.stage2;

import java.io.InvalidClassException;
import java.util.HashMap;

/**
 * Класс SavePoints - реализует функционал для управления хранением именованных состояний объектов
 * поддерживающих интерфейс {@link Savable} и восстановлением их из одного из ранее сохранённых состоияний.
 * При этом реализации упаковки и распаковка объектов сохранения
 * остаётся на совести самих объектов реализующих интерфейс {@link Savable}
 */
public class SavePoints {
    private final HashMap<ObjectSavepoint, Object> savepoints = new HashMap<>();

    public void addSavePoint(Savable object, String savepointName) {
        savepoints.put(new ObjectSavepoint(object, savepointName), object.getSave());
    }

    public void restoreSavePoint(Savable object, String savepointName) throws InvalidClassException {
        ObjectSavepoint key = new ObjectSavepoint(object, savepointName);
        if (savepoints.containsKey(key)) {
            object.restoreSave(savepoints.get(key));
        }
    }

    public void clear(){
        savepoints.clear();
    }
}
