import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {
    private final String name;
    private final String lastname;
    private final String address;
    private final String station;
    private final String number;
    private final String date;
    private final String rentPeriod;
    private final String color;
    private final String comment;

    public OrderTest(String name, String lastname, String address, String station, String number, String date, String rentPeriod, String color, String comment) {
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.station = station;
        this.number = number;
        this.date = date;
        this.rentPeriod = rentPeriod;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Ваня", "Петров", "Москва, ул.Московская, д.3, кв.23", "Сокольники", "+79995432100", "10.11.23", "двое суток", "black", "буду дома до 12.00"},
                {"Надя", "Петрова", "Москва, ул.Центральная, д.3, кв.23", "ВДНХ", "+799955555500", "15.11.23", "сутки", "grey", "Доставить вечером"},
        });
    }

    @Test
    public void orderTest1() {
        mainPage.cookie();
        mainPage.clickOrderHeader();
        orderPage.fillOutFirstForm(name, lastname, address, station, number);
        orderPage.clickNextStep();
        orderPage.fillOutSecondPart(date, rentPeriod, color, comment);
        orderPage.clickOrderButton();
        orderPage.clickConfirmButton();
    }

    @Test
    public void orderTest2() {
        mainPage.cookie();
        mainPage.clickOrderMiddle();
        orderPage.fillOutFirstForm(name, lastname, address, station, number);
        orderPage.clickNextStep();
        orderPage.fillOutSecondPart(date, rentPeriod, color, comment);
        orderPage.clickOrderButton();
        orderPage.clickConfirmButton();

    }
}