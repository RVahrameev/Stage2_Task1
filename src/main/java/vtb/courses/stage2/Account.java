package vtb.courses.stage2;

import lombok.Getter;
import lombok.ToString;

import java.io.InvalidClassException;
import java.util.Stack;
import java.util.function.Consumer;

@ToString
public class Account implements Savable {
    @Getter
    protected String owner;
    private SubAccounts byCurrency;
    @ToString.Exclude
    private transient Stack<Consumer<Account>> undoList;

    public Account(String owner) {
        setOwner(owner);
        byCurrency = new SubAccounts();
        byCurrency.put(Currency.RUB, Integer.valueOf(0));
        undoList = new Stack<>();
    }

    public Account(Account account) {
        this(account.owner);
        account.byCurrency.forEach((currency, integer) -> this.byCurrency.put(currency, integer));
    }

    public void undo(){
        if (undoList.empty()) {
            throw new IllegalStateException("Нечего откатывать!");
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
    public Object getSave() {
        return new Account(this);
    }

    @Override
    public void restoreSave(Object save) throws InvalidClassException {
        if (save instanceof Account) {
            Account savedAccount = (Account) save;

            this.owner = savedAccount.owner;
            this.byCurrency = savedAccount.byCurrency;
            this.undoList.clear();
        } else {
            throw new InvalidClassException("Не могу восстановить состояние из класса " + save.getClass().getName());
        }
    }

}
