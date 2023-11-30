package vtb.courses.stage2;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ObjectSavepoint {
    @Getter
    private Object object;
    @Getter
    private String savePointName;

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj instanceof ObjectSavepoint) {
            ObjectSavepoint sp = (ObjectSavepoint)obj;
            return (this.object == obj) && this.savePointName.equals(sp.savePointName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return object.hashCode() + savePointName.hashCode();
    }}
