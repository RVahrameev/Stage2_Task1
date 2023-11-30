package vtb.courses.stage2;

import lombok.Getter;

import java.util.Stack;
import java.util.function.Consumer;

public class Account {
    @Getter
    protected String owner;
    private SubAccounts byCurrency;

    private Stack<Consumer<Account>> undoList;

    public Account(String owner) {
        byCurrency = new SubAccounts();
        byCurrency.put(Currency.RUB, Integer.valueOf(0));
        undoList = new Stack<>();
        setOwner(owner);
    }

    public void undo(){
        if (undoList.empty()) {
            throw new IllegalStateException("Нет изменений к откату!");
        }
        undoList.pop().accept(this);
    }

    public void setOwner(String owner) {
        if ((owner == null) || owner.isBlank()) {
            throw new IllegalArgumentException(" Имя владельца счёта не может быть пустым!");
        }

        // Формируем данные для отката
        if (this.owner != null) {
            String oldOwner = this.owner;
            undoList.push(x -> x.owner = oldOwner);
        }

        this.owner = owner;
    }

    public SubAccounts getByCurrency() {
        return (SubAccounts) byCurrency.clone();
    }

    public void setAmount(Currency currency, Integer amount) {
        // Формируем данные для отката
        if (byCurrency.containsKey(currency)) {
            Integer oldValue = byCurrency.get(currency);
            undoList.push(x -> x.byCurrency.put(currency, oldValue));
        } else {
            undoList.push(x -> x.byCurrency.remove(currency));
        }


        byCurrency.put(currency, amount);
    }
    @Override
    public String toString() {
        return "Account{" +
                "owner='" + owner + '\'' +
                ", byCurrency=" + byCurrency +
                '}';
    }
}
