package vtb.courses.stage2;

import javax.imageio.IIOException;
import javax.print.AttributeException;
import java.io.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );


        for (Currency currency: Currency.values())
            System.out.println(currency);
        Account account = new Account("Вахрамеев");
        System.out.println(account);
        SubAccounts subAccounts = account.getByCurrency();
        subAccounts.put(Currency.RUB, 20);
        System.out.println(subAccounts);
        System.out.println(account);
        account.setAmount(Currency.EUR, 100);
        account.setAmount(Currency.RUB, 200);
        System.out.println(account);
        account.setOwner("Новый владелец");
        System.out.println(account);
        account.undo();
        System.out.println(account);
        account.undo();
        System.out.println(account);
        account.undo();
        System.out.println(account);
        account.undo();
    }
}
