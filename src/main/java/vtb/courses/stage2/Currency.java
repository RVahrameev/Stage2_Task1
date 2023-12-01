package vtb.courses.stage2;

/**
* Поскольку тестовое задание разрешает конкретный перечень валют указать в коде, то используем Enum.
* В реальной жизни скорее всего следует использовать обычный класс с подгрузкой возможных значений из Базы данных
 * */

public enum Currency {
    RUB("Российский рубль"),
    USD("Доллар США"),
    EUR("Евро"),
    CNY("Юань");
    final private String rusName;

    Currency(String name) {
        this.rusName = name;
    }

    @Override
    public String toString() {
        return  this.name() + "(" + rusName + ')';
    }
}
