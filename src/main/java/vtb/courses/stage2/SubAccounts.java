package vtb.courses.stage2;

import java.util.HashMap;

/**
 * Класс SubAccounts служит для хранения пар значений Валюта - Кол-во
 * По сути является расширением класса {@link HashMap} с дополнительными проверками сохраняемых значений на корректность
 */

public class SubAccounts extends HashMap<Currency, Integer> {
    @Override
    public Integer put(Currency key, Integer value) {
        if (key == null) {
            throw new IllegalArgumentException("Валюта не может быть пустой!");
        }
        if ((value == null) || (value < 0)) {
            throw new IllegalArgumentException("Остаток валюты должен быть >= 0!");
        }

        return super.put(key, value);
    }
}
