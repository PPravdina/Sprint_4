package practicum.scooter;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class OrderPage {
    private final WebDriver webDriver;
    //поле Имя
    private final By nameInput = By.xpath("//input[@placeholder='* Имя']");
    //поле Фамилия
    private final By lastnameInput = By.xpath("//input[@placeholder='* Фамилия']");
    //поле Адрес
    private final By adressInput = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    //поле Метро
    private final By metroChoose = By.xpath("//input[@placeholder='* Станция метро']");
    //поле телефон
    private final By phoneInput = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    //кнопка далее
    private final By nextStep = By.xpath("//button[text()='Далее']");
    //вторая часть формы
    private final By secondPartForm = By.className("Order_Content__bmtHS");
    //дата доставки
    private final By dateDelivery = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    //Кнопка для дропдаун списка со сроком аренды
    private final By buttonRentPeriod = By.xpath(".//span[@class='Dropdown-arrow']");
    //поле с комменатрием курьеру
    private final By leftComment = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    //кнопка заказать
    private final By orderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");
    //подтвердить заказ
    private final By orderConfirmButton = By.xpath(".//button[text()='Да']");
    //поп-ап подтверждения заказа
    private final By confirmedOrder = By.xpath(".//div[text()='Заказ оформлен']");

    public OrderPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void fillOutFirstForm(String name, String lastname, String address, String station, String number) {
        //заполняем поля формы
        webDriver.findElement(nameInput).sendKeys(name);
        webDriver.findElement(lastnameInput).sendKeys(lastname);
        webDriver.findElement(adressInput).sendKeys(address);
        webDriver.findElement(phoneInput).sendKeys(number);
        //выбираем метро и ждем появления списка
        webDriver.findElement(metroChoose).click();
        WebDriverWait wait = new WebDriverWait(webDriver, 3);
        WebElement stationElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//div[@class='Order_Text__2broi' and text()='"+ station +"']")));
        // Скроллим до нужной станции и выбираем ее
        ((JavascriptExecutor)webDriver).executeScript("arguments[0].scrollIntoView();", stationElement);
        stationElement.click();
        /* отдадочная инфа
        String nameValue = webDriver.findElement(nameInput).getAttribute("value");
        System.out.println("Значение поля Имя: " + nameValue);
        String lastnameValue = webDriver.findElement(lastnameInput).getAttribute("value");
        System.out.println("Значение поля Фамилия: " + lastnameValue);
        String addressValue = webDriver.findElement(adressInput).getAttribute("value");
        System.out.println("Значение поля адресс: " + addressValue);
        String phoneValue = webDriver.findElement(phoneInput).getAttribute("value");
        System.out.println("Значение поля телефон: " + phoneValue);
        String metroValue = webDriver.findElement(metroChoose).getAttribute("value");
        System.out.println("Значение поля метро: " + metroValue);
         */
    }

    public void clickNextStep() {
        // Нажимаем кнопку "Далее"
        webDriver.findElement(nextStep).click();
        // Ждем появления следующего элемента
        new WebDriverWait(webDriver, 3).until(ExpectedConditions.visibilityOfElementLocated(secondPartForm));
        assertTrue("Переход на вторую часть форму не осуществлен", webDriver.findElement(secondPartForm).isDisplayed());
    }
    //сначала хотела разбить заполнение на несколько блоков, но посчитала, что это лишнее. С другой стороны в допах было написать тест для ошибок при заполнении формы и тут детализация бы помогла
    public void fillOutSecondPart(String date, String rentPeriod, String color, String comment) {
        //заполнили дату - рукописный ввод подходит
        webDriver.findElement(dateDelivery).sendKeys(date);
        //кликаем на период аренды
        webDriver.findElement(buttonRentPeriod).click();
        //ищем и скролим до нужного периода
        By rentPeriodOption = By.xpath(".//div[@class='Dropdown-option' and text()='" + rentPeriod + "']");
        WebDriverWait wait = new WebDriverWait(webDriver, 3);
        WebElement rentPeriodElement = wait.until(ExpectedConditions.visibilityOfElementLocated(rentPeriodOption));
        ((JavascriptExecutor)webDriver).executeScript("arguments[0].scrollIntoView();", rentPeriodElement);
        //кликаем на нужный период
        rentPeriodElement.click();
        //Выбираем чек-бокс
        By colorCheckbox = By.xpath("//input[@type='checkbox' and @id='" + color + "']");
        webDriver.findElement(colorCheckbox).click();
        //добавляем комментарий
        webDriver.findElement(leftComment).sendKeys(comment);
        /*инфа для отладки
        String dateDeliveryValue = webDriver.findElement(dateDelivery).getAttribute("value");
        System.out.println("Значение поля дата доставки: " + dateDeliveryValue);
        By selectedRentPeriodPath = By.xpath(".//div[@class='Dropdown-placeholder is-selected']");
        String selectedRentPeriodText = webDriver.findElement(selectedRentPeriodPath).getText();
        System.out.println("Выбранный период аренды: " + selectedRentPeriodText);
        // Найти отмеченный чекбокс
        WebElement selectedCheckbox = webDriver.findElement(By.cssSelector("input[type='checkbox']:checked"));
        String selectedCheckboxValue = selectedCheckbox.getAttribute("id");
        System.out.println("Выбранный цвет самоката: " + selectedCheckboxValue);
        String commentValue = webDriver.findElement(leftComment).getAttribute("value");
        System.out.println("Значение поля комментарий: " + commentValue);

         */
    }
    public void clickOrderButton() {
        //нажимаем Заказать
        webDriver.findElement(orderButton).click();
        //Ожидаем поп-ап с подтверждением заказа
        WebDriverWait wait = new WebDriverWait(webDriver, 3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderConfirmButton));
        assertTrue("Поп-ап с подтверждением заказа не появился", webDriver.findElement(orderConfirmButton).isDisplayed());
    }
    public void clickConfirmButton(){
        //нажимаем на кнопку подтверждения заказа
        webDriver.findElement(orderConfirmButton).click();
        WebDriverWait wait = new WebDriverWait(webDriver, 3);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(confirmedOrder));
        } catch (TimeoutException e) {
            // Если элемент не появился, выводим сообщение об ошибке
            fail("Сообщение о подтверждении заказа не появилось");
        }
    }
}
