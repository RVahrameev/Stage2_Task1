package vtb.courses.stage2;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.InvalidClassException;
import java.util.ArrayDeque;
import java.util.function.Consumer;

@ToString @EqualsAndHashCode
public class Account implements Savable {
    @Getter
    private String owner;
    private SubAccounts byCurrency;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private final transient ArrayDeque<Consumer<Account>> undoList;

    public Account(String owner) {
        setOwner(owner);
        byCurrency = new SubAccounts();
        byCurrency.put(Currency.RUB, 0);
        undoList = new ArrayDeque<>();
    }

    public Account(Account account) {
        this(account.owner);
        this.byCurrency.putAll(account.byCurrency);
    }

    public void undo(){
        if (undoList.isEmpty()) {
            throw new IllegalStateException("Нечего откатывать!");
        }
       undoList.pop().accept(this);
    }

    public boolean undoAvailable() {
        return !undoList.isEmpty();
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
        return new AccountMemento(this);
    }

    @Override
    public void restoreSave(Object save) throws InvalidClassException {
        if (save instanceof AccountMemento) {
            AccountMemento savedAccount = (AccountMemento) save;
            this.owner = savedAccount.owner;
            this.byCurrency = savedAccount.byCurrency;
            this.undoList.clear();
        } else {
            throw new InvalidClassException("Не могу восстановить состояние из объекта класса " + save.getClass().getName() + ". Требуется объект класса AccountMemento.");
        }
    }

    @Override
    protected Account clone() {
        return new Account(this);
    }

    private static class AccountMemento {
        private final String owner;
        private final SubAccounts byCurrency;

        private AccountMemento(Account account) {
            this.owner = account.owner;
            this.byCurrency = (SubAccounts) account.byCurrency.clone();
        }
    }

}
