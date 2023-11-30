package vtb.courses.stage2;

import java.io.InvalidClassException;
import java.util.HashMap;

public class SavePoints {
    private HashMap<ObjectSavepoint, Object> savepoints;

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
