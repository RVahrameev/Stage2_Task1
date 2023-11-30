package vtb.courses.stage2;

import lombok.Getter;

import java.util.Stack;

public class Account {
    @Getter
    protected String owner;
    private SubAccounts byCurrency;

//    private Stack<UndoMethod> undoList;

    public Account(String owner) {
        setOwner(owner);
        byCurrency = new SubAccounts();
        byCurrency.put(Currency.RUB, Integer.valueOf(0));
    }

    public void setOwner(String owner) {
        if ((owner == null) || owner.isBlank()) {
            throw new IllegalArgumentException(" Имя владельца счёта не может быть пустым!");
        }
        this.owner = owner;
//        if (this.owner != null) {
//            undoList.push({(x, y) -> (((Account)x).owner = this.owner)});
//        }
    }

    public SubAccounts getByCurrency() {
        return (SubAccounts) byCurrency.clone();
    }

    public void setAmount(Currency currency, Integer amount) {
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
