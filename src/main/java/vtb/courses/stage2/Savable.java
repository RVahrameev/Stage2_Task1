package vtb.courses.stage2;

import java.io.InvalidClassException;

public interface Savable {
    public Object getSave();
    public void restoreSave(Object save) throws InvalidClassException;
}
