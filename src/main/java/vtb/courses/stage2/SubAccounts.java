package vtb.courses.stage2;

import java.math.BigDecimal;
import java.util.HashMap;

public class SubAccounts extends HashMap<Currency, Integer> {
    @Override
    public Integer put(Currency key, Integer value) {
        if (key == null) {
            throw new IllegalArgumentException("Валюта не может быть пустой!");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Количество не может быть отрицательным!");
        }

        return super.put(key, value);
    }
}
