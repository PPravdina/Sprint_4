package practicum.scooter;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    public static final String URL = "https://qa-scooter.praktikum-services.ru/";

    private final WebDriver webDriver;
    //Блок вопросов о важном
    private final By accordion = By.xpath(".//div[@class='accordion']");
    //аккордион с вопросами
    private final By orderHeader = By.xpath(".//button[@class='Button_Button__ra12g']");
    //Кнопка заказать в центре
    private final By orderMiddle = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");
    //форма заказа
    private final By formOrder = By.xpath(".//div[@class='Order_Header__BZXOb']");
    private final OrderPage orderPage;

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        orderPage = new OrderPage(webDriver);
    }

    public MainPage open() {
        webDriver.get(URL);
        return this;
    }

    //скролл до блока с вопросами
    public MainPage scrollToBlock() {
        WebElement blockElement = webDriver.findElement(accordion);
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView();", blockElement);
        return this;
    }

    //найти конкретный вопрос,кликнуть на него и получить ответ
    public String findQuestionClickAndGetAnswer(String questionText) {
        //Найти вопрос
        By question = By.xpath(".//div[@class='accordion__button' and text()='" + questionText + "']");
        WebElement questionElement = webDriver.findElement(question);
        //кликнуть на вопрос
        questionElement.click();
        //получить id вопроса
        String attribute = webDriver.findElement(question).getAttribute("id");
        //преобразовать для дальнейшего использования
        int idQuestion = attribute.lastIndexOf("-");
        String idNumStr = attribute.substring(idQuestion);
        String attributAnswer = "accordion__panel" + idNumStr;
        //составить xpath для конкретного ответа
        String answerXPath = ".//div[@id='" + attributAnswer + "']/p";
        By answer = By.xpath(answerXPath);
        //найти ответ и достать его текст
        WebDriverWait wait = new WebDriverWait(webDriver, 3);
        WebElement answerElement = wait.until(ExpectedConditions.presenceOfElementLocated(answer));
        System.out.println(answerElement.getText());
        return answerElement.getText();
    }

    public void cookie() {
        //кликнули на куки
        WebElement cookieButton = webDriver.findElement(By.id("rcc-confirm-button"));
        if (cookieButton.isDisplayed()) {
            cookieButton.click();
        }
    }

    //Кликнуть на Заказать в хэдере
    public void clickOrderHeader() {
        webDriver.findElement(orderHeader).click();
        new WebDriverWait(webDriver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(formOrder));
    }

    //кликнуть вторую кнопку заказать
    public void clickOrderMiddle() {
        WebElement orderMiddleElement = webDriver.findElement(orderMiddle);
        // Прокручиваем к этому элементу
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", orderMiddleElement);
        orderMiddleElement.click();
        new WebDriverWait(webDriver, 3)
                .until(ExpectedConditions.visibilityOfElementLocated(formOrder));
    }
}
/* изначально хотела реализовать другим способом, но падало все после первого вопроса, не поняла почему.
Из предположений: что-то не то с ожиданием появления ответа
public MainPage scrollToBlock(){
    WebElement blockElement = webDriver.findElement(accordion);
    ((JavascriptExecutor)webDriver).executeScript("arguments[0].scrollIntoView();", blockElement);
    return this;
}
    //найти конкретный вопрос,кликнуть на него и получить ответ
    public String findQuestionAndClick(String questionText) {
        //Найти вопрос
        By questionWithText = By.xpath(".//div[contains(text()='"+ questionText +"')]");
        WebElement questionElement = webDriver.findElement(question);//answer - была private final c путем .//div[start-with(@class,'accordion__panel-')]
        //кликнуть на вопрос
        questionElement.click();
    public String getAnswer(){
        WebDriverWait wait = new WebDriverWait(webDriver, 3);
        WebElement answerElement = wait.until(ExpectedConditions.presenceOfElementLocated(answer));
        return answerElement.getText();//тут уже все ломалось и текст не полчался
    }
 */
