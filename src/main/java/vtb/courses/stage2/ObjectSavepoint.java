package vtb.courses.stage2;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Класс ObjectSavepoint создан для организации составного ключа для HashMap
 * Для иденификации сохранённого состояния используется
 * ссылка на сохраняемый объект и название сохранения (по аналогии с именем точки сохранения в Oracle)
 */
@AllArgsConstructor
public class ObjectSavepoint {
    @Getter
    private Object object;
    @Getter
    private String savePointName;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ObjectSavepoint) {
            ObjectSavepoint sp = (ObjectSavepoint)obj;
            return (this.object == sp.object) && this.savePointName.equals(sp.savePointName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return savePointName.hashCode();
    }
}
