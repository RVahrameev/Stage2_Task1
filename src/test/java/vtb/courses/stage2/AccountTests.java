package vtb.courses.stage2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InvalidClassException;

public class AccountTests {
    @Test
    @DisplayName("Корректность заполнения имени владельца")
    public void checkOwnerRestricts () {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Account((String) null), "Удалось создать счёт с владельцем null!");
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Account("     "), "Удалось создать счёт с пустым владельцем!");
    }

    @Test
    @DisplayName("Массив валют только для чтения")
    public void checkAccountReadonly () {
        Account account = new Account("ВахрамеевРА");
        account.setAmount(Currency.RUB, 100);
        account.setAmount(Currency.USD, 200);
        SubAccounts subAccounts = account.getByCurrency();
        subAccounts.put(Currency.RUB, 300);
        Assertions.assertNotEquals(account.getByCurrency().get(Currency.RUB), 300, "Не соблюдена инкапсуляция массива остатков!");
    }

    @Test
    @DisplayName("Проверка работы с остатками")
    public void checkAccountMovements(){
        Account account = new Account("ВахрамеевРА");
        account.setAmount(Currency.RUB, 100);
        account.setAmount(Currency.USD, 200);
        account.setAmount(Currency.EUR, 300);
        SubAccounts subAccounts = account.getByCurrency();
        Assertions.assertEquals(100, account.getByCurrency().get(Currency.RUB), "Не установились остатки по счёту в RUB");
        Assertions.assertEquals(200, account.getByCurrency().get(Currency.USD), "Не установились остатки по счёту в USD");
        Assertions.assertEquals(300, account.getByCurrency().get(Currency.EUR), "Не установились остатки по счёту в EUR");
        Assertions.assertThrows(IllegalArgumentException.class, ()->account.setAmount(Currency.EUR, null), "Удалось сохранить остаток null");
        Assertions.assertThrows(IllegalArgumentException.class, ()->account.setAmount(Currency.EUR, -100), "Удалось сохранить в качестве остатка отрицательную сумму");
        Assertions.assertDoesNotThrow(()->account.setAmount(Currency.EUR, 0), "Не удалось сохранить остаток 0");
    }

    @Test
    @DisplayName("Проверка откатываемости изменений")
    public void checkUndo(){
        Account account = new Account("ВахрамеевРА");
        Account accountOwner1 = account.clone();
        account.setAmount(Currency.RUB, 100);
        Account accountRub = account.clone();
        account.setAmount(Currency.USD, 200);
        Account accountUsd = account.clone();
        account.setAmount(Currency.EUR, 300);
        Account accountEur = account.clone();
        account.setOwner("Новый Владелец");
        Account accountOwner2 = account.clone();
        account.setAmount(Currency.CNY, 400);
        account.undo();
        Assertions.assertEquals(accountOwner2, account, "Не удалось соверить откат к accountOwner2");
        account.undo();
        Assertions.assertEquals(accountEur, account, "Не удалось соверить откат к accountEur");
        account.undo();
        Assertions.assertEquals(accountUsd, account, "Не удалось соверить откат к accountUsd");
        account.undo();
        Assertions.assertEquals(accountRub, account, "Не удалось соверить откат к accountRub");
        account.undo();
        Assertions.assertEquals(accountOwner1, account, "Не удалось соверить откат к accountOwner1");
        Assertions.assertThrows(IllegalStateException.class, () -> account.undo(), "Не сгенерилось исключение при отсутствии возможности отката");
    }

    @Test
    @DisplayName("Проверка точек сохранения")
    public void checkSavePoints() {
        SavePoints savePoints = new SavePoints();
        Account account = new Account("ВахрамеевРА");
        Account accountOwner1 = account.clone();
        savePoints.addSavePoint(account, "accountOwner1");

        account.setAmount(Currency.RUB, 100);
        Account accountRub = account.clone();
        savePoints.addSavePoint(account, "accountRub");

        account.setAmount(Currency.USD, 200);
        Account accountUsd = account.clone();
        savePoints.addSavePoint(account, "accountUsd");

        account.setAmount(Currency.EUR, 300);
        Account accountEur = account.clone();
        savePoints.addSavePoint(account, "accountEur");

        account.setOwner("Новый Владелец");
        Account accountOwner2 = account.clone();
        savePoints.addSavePoint(account, "accountOwner2");

        account.setAmount(Currency.CNY, 400);
        account.setOwner("Петрович");
        Account accountPetrovich = account.clone();
        savePoints.addSavePoint(account, "accountPetrovich");

        account.setAmount(Currency.RUB, 400);
        account.setOwner("Петрович7777");

        Assertions.assertDoesNotThrow(() -> savePoints.restoreSavePoint(account, "accountOwner1"));
        Assertions.assertEquals(accountOwner1, account, "Не удалось соверить откат к accountOwner1");
        Assertions.assertDoesNotThrow(() -> savePoints.restoreSavePoint(account, "accountRub"));
        Assertions.assertEquals(accountRub, account, "Не удалось соверить откат к accountRub");
        Assertions.assertDoesNotThrow(() -> savePoints.restoreSavePoint(account, "accountUsd"));
        Assertions.assertEquals(accountUsd, account, "Не удалось соверить откат к accountUsd");
        Assertions.assertDoesNotThrow(() -> savePoints.restoreSavePoint(account, "accountEur"));
        Assertions.assertEquals(accountEur, account, "Не удалось соверить откат к accountEur");
        Assertions.assertDoesNotThrow(() -> savePoints.restoreSavePoint(account, "accountOwner2"));
        Assertions.assertEquals(accountOwner2, account, "Не удалось соверить откат к accountOwner2");
        Assertions.assertDoesNotThrow(() -> savePoints.restoreSavePoint(account, "accountPetrovich"));
        Assertions.assertEquals(accountPetrovich, account, "Не удалось соверить откат к accountPetrovich");

    }
}
