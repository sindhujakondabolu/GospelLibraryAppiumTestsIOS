package UI;

import com.thoughtworks.selenium.webdriven.commands.Click;
import com.thoughtworks.selenium.webdriven.commands.GetElementWidth;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import javafx.util.Pair;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.JavascriptExecutor;


import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Delayed;


import static UI.Content.setBooks;
import static UI.Strings.*;
import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.fail;



public class GospelLibrary {
    IOSDriver driver;
    @Rule public TestName testName = new TestName();
    private Object TestName;

    @Before
    public void setUp() throws Exception {
        System.out.println("Class Name: " + testName.getMethodName());
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","iOS");
        capabilities.setCapability("platformVersion","12.4");
        capabilities.setCapability("deviceName", "iPhone X");
        capabilities.setCapability("automationName","XCUITest");
        capabilities.setCapability("app", System.getProperty("user.dir") + "/../../App/GospelLibrary.app");
//        capabilities.setCapability("setOrientation", "PORTRAIT");
//        capabilities.setCapability("eventTimings",true);
        capabilities.setCapability("waitForQuiescence",false);
//        capabilities.setCapability("newCommandTimeout",1);
        capabilities.setCapability("wdaEventloopIdleDelay",1);
        capabilities.setCapability("useNewWDA",false);
        capabilities.setCapability("noReset",true);
        capabilities.setCapability("launchTimeout",1);
        capabilities.setCapability("waitForAppScript", "$.delay(0); true;");
//        capabilities.setCapability("noReset",false);
        driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
//        driver.manage().timeouts().pageLoadTimeout(4,TimeUnit.SECONDS);
//        driver.manage().timeouts().setScriptTimeout(4,TimeUnit.SECONDS);
//        driver.manage().timeouts().implicitlyWait(4,TimeUnit.SECONDS);
        setBooks();
        delay(5);


    }

    @After
    public void tearDown() throws Exception {
//        ClickUIElementByAccessibilityID("Settings");
//        if (WebElementsByAccessibilityId("Account options").size() > 0){
//            ClickUIElementByAccessibilityID("Account options");
//            ClickUIElementByAccessibilityID("Sign Out");
//        }
        driver.closeApp();
        driver.removeApp("org.lds.gospelstudy-Alpha");
        driver.quit();


    }



    //********************************************** Functions **********************************************
    //Logs the current test's current id
    public void logTestId(String TestId) throws Exception{
        log("Enterprise Tester ID: "+ TestId);
    }


    //Replaces letters with dots
    public String hidePassword(String password) throws Exception {
        String passwordDotted = "";
        int passwordLength = password.length();
        for (int i = 0; i < passwordLength; i++) {
            passwordDotted = passwordDotted.concat("•");
        }
        return passwordDotted;
    }

    //delay
    public void delay(int delayTime) throws Exception{
        sleep(milliseconds_1 * delayTime);
    }

    //log
    public void log(String stringToLog) throws Exception{
        System.out.println(stringToLog);
    }

    public void openAnnotationMenu(WebElement theParagraph){
        driver.tap(1,theParagraph,1000);
    }


    //Get Element by text
    public String FindElementByText(String text) throws Exception {

        String xPathofText = "//*[@name='" + text + "']";
        //System.out.println("Xpath is: '"+xPathofText+"");
        return xPathofText;
    }


    //Get Element by text contains
    public String FindElementByContainsText(String text) throws Exception {
        String xPathofText = "//*[contains(@name, '" + text + "')]";
        //System.out.println("Xpath is: '"+xPathofText+"'");
        return xPathofText;
    }

    //Create WebElement by text
    public WebElement WebElementByText(String text) throws Exception {
        String xPathofText = "//*[@name='" + text + "']";
        //System.out.println("Xpath of current item is: "+xPathofText+"");
        WebElement tempElement = driver.findElement(By.xpath(xPathofText));
        return tempElement;
    }

    //Create WebElement by text contains
    public WebElement WebElementByTextContains(String text) throws Exception {
        String xPathofText = "//*[contains(@name, '" + text + "')]";
        //System.out.println("Xpath of current item is: "+xPathofText+"");
        WebElement tempElement = driver.findElement(By.xpath(xPathofText));
        return tempElement;
    }

    //Create WebElement by text
    public WebElement WebElementByName(String name) throws Exception {
        WebElement tempElement = driver.findElementByName(name);
        return tempElement;
    }

    //Create WebElements by text (List)
    public List WebElementsByText(String text) throws Exception {
        String xPathofText = "//*[@name='" + text + "']";
        //System.out.println("Xpath of current item is: "+xPathofText+"");
        List tempElement = driver.findElements(By.xpath(xPathofText));
        if (tempElement.size() <1){
            swipeDown();
            tempElement = driver.findElements(By.xpath(xPathofText));
            if (tempElement.size() < 1){
                System.out.println("\n" + text + " was not found on the screen. xpath was: " + xPathofText);
            }
        }
        return tempElement;
    }

    //Create WebElements by text contains (List)
    public List WebElementsByTextContains(String text) throws Exception {
        String xPathofText = "//*[contains(@name, '" + text + "')]";
        //System.out.println("Xpath of current item is: "+xPathofText+"");
        List tempElement = driver.findElements(By.xpath(xPathofText));
        if (tempElement.size() <1){
            swipeDown();
            tempElement = driver.findElements(By.xpath(xPathofText));
            if (tempElement.size() < 1){
                System.out.println("\n" + text + " was not found on the screen. xpath was: " + xPathofText);
            }
        }
        return tempElement;
    }

    //Create WebElements by id
    public List WebElementsById(String id) throws Exception {
        List tempElement = driver.findElementsById(id);
        if (tempElement.size() <1){
            swipeDown();
            tempElement = driver.findElementsById(id);
            if (tempElement.size() < 1){
                System.out.println("\n" + id + " was not found on the screen.");
            }
        }
        return tempElement;
    }

    //Create WebElements by id
    public List WebElementsByIdExpectFalse(String id) throws Exception {
        List tempElement = driver.findElementsById(id);
        if (tempElement.size() <1){
            System.out.println("\n" + id + " was not found on the screen.");
        }
        return tempElement;
    }

    //Create Webelements by name
    public List WebElementsByName(String name) throws Exception {
        List tempElement = driver.findElementsByName(name);
        if (tempElement.size() <1){
            swipeDown();
            tempElement = driver.findElementsByName(name);
            if (tempElement.size() < 1){
                System.out.println("\n" + name + " was not found on the screen.");
            }
        }
        return tempElement;
    }


    //Create WebElements by accessibility id
    public List WebElementsByAccessibilityId(String id) throws Exception {
        List tempElement = driver.findElementsByAccessibilityId(id);
        if (tempElement.size() <1){
            swipeDown();
            tempElement = driver.findElementsByAccessibilityId(id);
            if (tempElement.size() < 1){
                System.out.println("\n" + id + " was not found on the screen.");
            }
        }
        return tempElement;
    }

    //Create WebElement by xPath
    public WebElement WebElementByXpath(String text) throws Exception {
        WebElement tempElement = driver.findElementByXPath(text);
        return tempElement;
    }

    //Create WebElements by xpath (list)
    public List WebElementsByXpath(String xpath) throws Exception {
        String xPathofText = xpath;
        System.out.println("Xpath of current item is: "+xPathofText+"");
        List tempElement = driver.findElements(By.xpath(xPathofText));
        if (tempElement.size() <1){
            swipeDown();
            tempElement = driver.findElements(By.xpath(xPathofText));
            if (tempElement.size() < 1){
                System.out.println("\n" + xpath + " was not found on the screen. xpath was: " + xPathofText);
            }
        }
        return tempElement;
    }

    //Create WebElements by xpath in webview (list)
    public List WebElementsByXpathWebview(String xpath) throws Exception {
        String xPathofText = xpath;
        System.out.println("Xpath of current item is: "+xPathofText+"");
        List tempElement = driver.findElements(By.xpath(xPathofText));
        if (tempElement.size() <1){
                System.out.println("\n" + xpath + " was not found on the screen. xpath was: " + xPathofText);
        }
        return tempElement;
    }

    //Create WebElement by id
    public WebElement WebElementById(String id) throws Exception {
        WebElement tempElement = driver.findElementById(id);
        return tempElement;
    }

    //Create WebElement by AccessibilityId
    public WebElement WebElementByAccessibilityId(String accessibilityid) throws Exception {
        WebElement tempElement = driver.findElementByAccessibilityId(accessibilityid);
        return tempElement;
    }

    //Click Element by Text
    public void ClickUIElementByText(String text) throws Exception {
        WebElementByText(text).click();
        System.out.println("Clicking: '" + text + "' using text by xPath with TextView");
        sleep(milliseconds_1);
    }

    //Click Element by Text Contains
    public void ClickUIElementByTextContains(String text) throws Exception {
        WebElementByTextContains(text).click();
        System.out.println("Clicking: '" + text + "' using text by xPath with TextView");
        sleep(milliseconds_1);
    }

    //Click Element by 2 text items
    public void ClickUIElementBy2TextStings(String book, String number) throws Exception {
        String xPathofText = "//*[contains(@text, '" + book + "')][contains(@text, '" + number + "')]";
        //System.out.println("Xpath of current item is: "+xPathofText+"");
        WebElementByXpath(xPathofText).click();
        System.out.println("Clicking: '" + book + " " + number + "' using 2 text contains by xPath");
        sleep(milliseconds_1);
    }

    //Click Element by Accessibility ID
    public void ClickUIElementByAccessibilityID(String elementAccessibilityID) throws Exception {
        WebElementByAccessibilityId(elementAccessibilityID).click();
        System.out.println("Clicking: '" + elementAccessibilityID + "' by Accessibility ID");
        sleep(milliseconds_1);
    }

    //Click Element by ID
    public void ClickUIElementByID(String elementID) throws Exception {
        WebElementById(elementID).click();
        System.out.println("Clicking: '" + elementID + "' by ID");
        sleep(milliseconds_1);
    }

    //Click Element by Name
    public void ClickUIElementByName(String name) throws Exception {
        WebElementByName(name).click();
        System.out.println("Clicking: '" + name + "' by Name");
        sleep(milliseconds_1);
    }


    //Click Element by Xpath
    public void ClickUIElementByXpath(String xpath) throws Exception {
        WebElementByXpath(xpath).click();
        System.out.println("Clicking: '" + xpath + "' by xPath");
        sleep(milliseconds_1);
    }

    //Tap in the center of the screen
    public void TapCenterScreen() throws Exception {
        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();
        TouchAction action = new TouchAction(driver);
        action.tap(screenWidth / 2, screenHeight / 2).perform();
        System.out.println("Tapping center of screen");
    }


    public void TapAndDragWebElement(WebElement startPoint, WebElement endPoint) throws Exception{
        int tapX = startPoint.getLocation().getX();
        int tapY = startPoint.getLocation().getY();
        int tapElementWidth = startPoint.getSize().getWidth();
        int tapElementHeight = startPoint.getSize().getHeight();
        int startX = tapX + (tapElementWidth/2);
        int startY = tapY + (tapElementHeight/2);

        tapX = endPoint.getLocation().getX();
        tapY = endPoint.getLocation().getY();
        tapElementWidth = endPoint.getSize().getWidth();
        tapElementHeight = endPoint.getSize().getHeight();
        int endX = tapX + (tapElementWidth/2);
        int endY = tapY;

        TouchAction action = new TouchAction(driver);

        action
                .longPress(startX, startY)
                .moveTo(endX, endY)
                .release()
                .perform();

        driver.getPageSource();
    }

    public void TapAndDrag(int startX, int startY, int endX,int endY) throws Exception{
        TouchAction action = new TouchAction(driver);

        action
                .longPress(startX, startY)
                .moveTo(endX, endY)
                .release()
                .perform();

        driver.getPageSource();
    }

    //Enter Text to a field by ID
    //click field, clear field, enter text
    public void sendText(String AccessibilityID, String text) throws Exception {
        WebElement textfield = driver.findElementById(AccessibilityID);
        textfield.clear();
        textfield.sendKeys(text);
        System.out.println("Sending Text: '" + text + "'");
        sleep(milliseconds_1);
    }

    //click field, enter text
    public void sendTextWithoutClearing(String AccessibilityID, String text) throws Exception {
        WebElement textfield = driver.findElementById(AccessibilityID);
        textfield.sendKeys(text);
        System.out.println("Sending Text: '" + text + "'");
        sleep(milliseconds_1);
    }

    public void selectOptionByDisplayedText(WebElement Text)throws Exception{




    }

    //Scroll down the page
    public void scrollDown() {
        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();
        driver.swipe(screenWidth / 2, screenHeight / 10 * 8, screenWidth / 2, screenHeight / 10 * 3, 2000);
        System.out.println("Scrolling Down...");

    }

    //Scroll down the page
    public void swipeDown() {
        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();
        driver.swipe(screenWidth / 2, screenHeight / 10 * 6, screenWidth / 2, screenHeight / 10 * 5, 2000);
        System.out.println("Scrolling Down...");

    }

    //Scroll up the page
    public void scrollUp() {
        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();
        driver.swipe(screenWidth / 2, screenHeight / 10 * 2, screenWidth / 2, screenHeight / 10 * 8, 2000);
        System.out.println("Scrolling Up...");

    }

    //Swipe next chapter
    public void swipeNextChapter() throws Exception {
        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();
        driver.swipe(screenWidth / 20 * 18, screenHeight / 2, screenWidth / 20 * 2, screenHeight / 2, 300);
        System.out.println("Swiping right...");
        sleep(milliseconds_1);
    }

    //Swipe previous chapter
    public void swipePreviousChapter() throws Exception {
        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();
        driver.swipe(screenWidth / 20 * 3, screenHeight / 2, screenWidth / 20 * 19, screenHeight / 2, 300);
        System.out.println("Swiping left...");
        sleep(milliseconds_1);
    }

    //Swipe to delete
    public void swipeToDelete(WebElement theWebElement, boolean delete) throws Exception {

        int elementY = theWebElement.getLocation().y;
        int elementHeight = theWebElement.getSize().height;
        int swipeY = (elementY+(elementHeight/2));
        log(String.valueOf(swipeY));
        int screenWidth = driver.manage().window().getSize().getWidth();
        log(String.valueOf(screenWidth / 20 * 18));
        log(String.valueOf(screenWidth / 20 * 2));
        if (delete) {
            driver.swipe(screenWidth / 20 * 18, swipeY, screenWidth / 20 * 2, swipeY, 300);
            log("Swiping to delete...");
        } else {
            driver.swipe(screenWidth / 20 * 18, swipeY, screenWidth / 20 * 6, swipeY, 300);
            assertElementExistsBy(WebElementsByText(DeleteString));
            log("Swiping");
        }
        sleep(milliseconds_1);
    }

    //Dismiss Dialog
    public void dismissDialog(WebElement dialogBox) throws Exception {
        int dialogYLocation = (dialogBox.getLocation().getY());
        int dialogXLocation = (dialogBox.getLocation().getX());
        int dialogHeight = (dialogBox.getSize().height);
        int dialogWidth = (dialogBox.getSize().width);
        int screenWidth = driver.manage().window().getSize().getWidth();
        int screenHeight = driver.manage().window().getSize().getHeight();
        int yTapPoint = (screenHeight / 2);
        int xTapPoint = (screenWidth / 2);
        if (dialogYLocation + dialogHeight +10 < screenHeight){
            yTapPoint = ((screenHeight - (dialogYLocation + dialogHeight)) / 2 + (dialogYLocation + dialogHeight));
        } else if (dialogYLocation - 10 > 1){
            yTapPoint = (dialogYLocation / 2);
        } else if (dialogXLocation -10 > 1){
            xTapPoint = (dialogXLocation / 2);
        } else if (dialogXLocation + dialogWidth +10 < screenWidth){
            xTapPoint = ((screenWidth - (dialogXLocation + dialogWidth)) / 2 + (dialogXLocation + dialogWidth));
        }
        System.out.println("Screen Height is: " + screenHeight);
        System.out.println("Y Tap Point is:   " + yTapPoint);
        System.out.println("Screen Width is: " + screenWidth);
        System.out.println("X Tap Point is:  " + xTapPoint);
        TouchAction action = new TouchAction(driver);
        action.tap(xTapPoint, yTapPoint).perform();
        sleep(1000);
    }


    //

    public void ClickSeekBarAt(WebElement webelement, int StartPositionOf7, int positionOf7) throws Exception {
        int upperY = webelement.getLocation().getY();
        int upperX = webelement.getLocation().getX();
        int seekbarWidth = webelement.getSize().getWidth();
        int seekbarHeight = webelement.getSize().getHeight();
        int TapTargetY = (upperY + (seekbarHeight/2));
        //Seekbar positions
        int setting1 = (upperX);
        int setting2 = (int) (upperX + (seekbarWidth / 6) * 1);
        int setting3 = (int) (upperX + (seekbarWidth / 6) * 2);
        int setting4 = (upperX + (seekbarWidth / 2));
        int setting5 = (int) (upperX + ((seekbarWidth / 6) * 4));
        int setting6 = (int) (upperX + ((seekbarWidth / 6) * 5));
        int setting7 = (upperX + (seekbarWidth));

        if(StartPositionOf7 == 1){
           verifyValue("0%",webelement);
            StartPositionOf7 = setting1;
        } else if (StartPositionOf7 == 2){
            verifyValue("17%",webelement);
            StartPositionOf7 = setting2;
        } else if (StartPositionOf7 == 3){
            verifyValue("33%",webelement);
            StartPositionOf7 = setting3;
        } else if (StartPositionOf7 == 4){
            verifyValue("50%",webelement);
            StartPositionOf7 = setting4;
        } else if (StartPositionOf7 == 5){
            verifyValue("67%",webelement);
            StartPositionOf7 = setting5;
        } else if (StartPositionOf7 == 6){
            verifyValue("83%",webelement);
            StartPositionOf7 = setting6;
        } else if (StartPositionOf7 == 7){
            verifyValue("100%",webelement);
            StartPositionOf7 = setting7;
        }



        if (positionOf7 == 1) {
            //12px
            TapAndDrag(StartPositionOf7,TapTargetY,setting1,TapTargetY);
            verifyValue("0%",webelement);
            sleep(milliseconds_1);
        } else if (positionOf7 == 2) {
            //18px
            TapAndDrag(StartPositionOf7,TapTargetY,setting2,TapTargetY);
            //verifyValue("17%",webelement);
            sleep(milliseconds_1);
        } else if (positionOf7 == 3) {
            //21px
            TapAndDrag(StartPositionOf7,TapTargetY,setting3,TapTargetY);
            //verifyValue("33%",webelement);
            sleep(milliseconds_1);
        } else if (positionOf7 == 4) {
            TapAndDrag(StartPositionOf7,TapTargetY,setting4,TapTargetY);
            //verifyValue("50%",webelement);
            sleep(milliseconds_1);
        } else if (positionOf7 == 5) {
            TapAndDrag(StartPositionOf7,TapTargetY,setting5,TapTargetY);
            //verifyValue("67%",webelement);
            sleep(milliseconds_1);
        } else if (positionOf7 == 6) {
            TapAndDrag(StartPositionOf7,TapTargetY,setting6,TapTargetY);
            //verifyValue("83%",webelement);
            sleep(milliseconds_1);
        } else if (positionOf7 == 7) {
            TapAndDrag(StartPositionOf7,TapTargetY,setting7,TapTargetY);
            //verifyValue("100%",webelement);
            sleep(milliseconds_1);
        } else {
            TapAndDrag(StartPositionOf7,TapTargetY,setting3,TapTargetY);
            sleep(milliseconds_1);
        }
    }

    public void OpenScripture(String work, String book, String chapter, String verse) throws Exception {
        assertElementExistsBy(WebElementsByText("Scriptures"));
        ClickUIElementByID("Scriptures");
        sleep(milliseconds_1);
        ClickUIElementByID(work +", Installed");
        sleep(milliseconds_1);
        if (book != "") {
            scrollToById(book);
            ClickUIElementByID(book);
            sleep(milliseconds_2);
            if (chapter != "") {
                scrollToById(chapter);
                ClickUIElementByID(chapter);
                sleep(milliseconds_1);
            }
            if (verse != "") {
                sleep(milliseconds_1);
                System.out.println("Scrolling to " + verse);
                scrollToById(verse);
            }
        }

    }


    public void OpenConference(String month, String year, String talkTitle,boolean Installed) throws Exception {
        assertElementExistsBy(WebElementsByAccessibilityId(GeneralConferenceString));
        ClickUIElementByAccessibilityID(GeneralConferenceString);
        sleep(milliseconds_1);
        if(! Installed){
            String ConferenceNotInstalled = month + " " + year+", Not Installed";
            scrollToById(ConferenceNotInstalled);
            ClickUIElementByAccessibilityID(ConferenceNotInstalled);
        }



        String Conference = month + " " + year+", Installed";

        if (month != "") {
            scrollToById(Conference);
            assertElementExistsBy(WebElementsByText(Conference));
            ClickUIElementByText(Conference);
            sleep(milliseconds_1);
            if (talkTitle != "") {
                scrollToById(talkTitle);
                ClickUIElementByText(talkTitle);
            }
        }
    }

    public void NavigateToLibrary()throws Exception{
        ClickUIElementByXpath("//XCUIElementTypeNavigationBar");
        ClickUIElementByName(LibraryString);
    }


    //*************************************************************** Assert Functions ***************************************************************

    //Scroll to
    public void scrollDownTo(String text) throws Exception {
        Boolean isNotPresent = WebElementsByText(text).size() <= 0;
        int i = 0;
        while ((isNotPresent)) {
            if (i == 15){
                fail("The item was not found on the screen within " + i + " downward scrolls");
                break;
            }
            System.out.println("" + text + " isn't on the screen... Scrolling to find");
            System.out.println("Scroll " + i + " of 15");
            scrollDown();
            isNotPresent = WebElementsByText(text).size() <= 0;
            i = i + 1;
        }
    }

    //Scroll to
    public void scrollDownToWithMaxScroll(String text, int MaxScroll) {
        Boolean isNotPresent = driver.findElements(By.xpath("//*[contains(@text, '" + text + "')]")).size() <= 0;
        int i = 0;
        while ((isNotPresent)) {
            if (i == MaxScroll){
                fail("The item was not found on the screen within " + i + " downward scrolls");
                break;
            }
            System.out.println("" + text + " isn't on the screen... Scrolling to find");
            System.out.println("Scroll " + i + " of " + MaxScroll);
            scrollDown();
            isNotPresent = driver.findElements(By.xpath("//*[@text='" + text + "']")).size() <= 0;
            i = i + 1;
        }
    }

    //Verify Text
    public void verifyText(String expectedText, WebElement webElementActual) throws Exception {
        String webelementActualAsText = webElementActual.getText();
        System.out.println("Validating text Expected: '" + expectedText + "' Actual: '" + webelementActualAsText + "'");
        Assert.assertEquals(expectedText, webelementActualAsText);
    }

    //Verify Value
    public void verifyValue(String expectedValue, WebElement webElementActual) throws Exception {
        String webelementActualValue = webElementActual.getAttribute("value");
        System.out.println("Validating value Expected: '" + expectedValue + "' Actual: '" + webelementActualValue + "'");
        Assert.assertEquals(expectedValue, webelementActualValue);
    }


    //Assert Object Exists Using WebElementsBy
    public void assertElementExistsBy(List webElementsBy) {
        Boolean tempElement = webElementsBy.size() > 0;
        System.out.println("assert element is present. Expected: true [] Actual: " + tempElement + " Element: " + webElementsBy.toString() + "");
        if (tempElement == false) {
            System.out.println("Found " + webElementsBy.size() + ". List of Elements Found: " + webElementsBy);
        }
        assert tempElement == true;
    }

    //Assert Object Doesn't Exist Using WebElementsBy
    public void assertElementNotPresentBy(List webElementsBy) {
        Boolean tempElement = webElementsBy.size() == 0;
        System.out.println("assert element is not present. Expected: false [] Actual: " + tempElement + " Element: " + webElementsBy.toString() + "");
        if (tempElement == false) {
            System.out.println("Found " + webElementsBy.size() + ". List of Elements Found: " + webElementsBy);
        }
        assert tempElement == true;
    }

    //Assert item count
    public void assertElementCount (List webElementsBy, Integer expectedCount) throws Exception{
        Integer elementCount = webElementsBy.size();
        log("Expected: " + expectedCount + " element(s) Found: "+ elementCount + " element(s)");
        assert expectedCount == elementCount;
    }

    //Assert Expected Switch Status
    public void assertExpectedSwitchStatus(String IOSElementName, Integer expectedSwitchStatus) throws Exception{
        Integer SwitchStatus = Integer.valueOf(WebElementByText(IOSElementName).getAttribute("value"));
        if (expectedSwitchStatus != SwitchStatus){
            System.out.println("Switch Status of " + IOSElementName + " didn't match expected. Expected: " + expectedSwitchStatus + " was: " + SwitchStatus);
        } else {
            System.out.println("Switch Status of " + IOSElementName + " matched expected. Expected: " + expectedSwitchStatus);
        }
        Assertions.assertTrue(expectedSwitchStatus == SwitchStatus);
    }

    //Assert Header with Option
    public void assertHeaderWithOption(String header, String option, boolean tap) throws Exception{
        scrollDownTo(header.toUpperCase());
        assertElementExistsBy(WebElementsByText(header.toUpperCase()));
        assertElementExistsBy(WebElementsByXpath("//*[@name='" + header.toUpperCase() +"']/*[@name='"+ option +"']"));
        if (tap){
            ClickUIElementByXpath("//*[@name='" + header.toUpperCase() +"']/*[@name='"+ option +"']");
        }

    }

    public void assertMoreInfoButton (String neighborElementName, Boolean tap) throws Exception{
        assertElementExistsBy(WebElementsByXpath("//*[@name='" + neighborElementName +"']/../*[@name='More Info']"));
        if (tap){
            ClickUIElementByXpath("//*[@name='" + neighborElementName +"']/../*[@name='More Info']");
        }
    }

    public void assertItemIsNextTo (String neighborElementName, String ElementName, Boolean tap) throws Exception{
        scrollDownTo(neighborElementName);
        assertElementExistsBy(WebElementsByText(neighborElementName));
        assertElementExistsBy(WebElementsByXpath("//*[@name='" + neighborElementName +"']/../*[@name='" + ElementName + "']"));
        if (tap){
            ClickUIElementByXpath("//*[@name='" + neighborElementName +"']/../*[@name='More Info']");
        }
    }

    public void isEnabled (WebElement theElement, Boolean isEnabled) throws Exception{
        log("Expected " + theElement + "'s enabled attribute to be "+ isEnabled +". Actual was: " + theElement.getAttribute("enabled")+".");
        assert (Boolean.parseBoolean(theElement.getAttribute("enabled")) == isEnabled);
    }
    public void isVisible (WebElement theElement, Boolean isVisible) throws Exception{
        log("Expected " + theElement + "'s visible attribute to be "+ isVisible +". Actual was: " + theElement.getAttribute("visible")+".");
        assert (Boolean.parseBoolean(theElement.getAttribute("visible")) == isVisible);
    }

    public void verifyItemOrder(WebElement topItem, WebElement bottomItem) throws Exception{
        int topItemY = topItem.getLocation().getY();
        int bottomItemY = bottomItem.getLocation().getY();
        log("The Top Item Y is: " + String.valueOf(topItemY));
        log("The Bottom item Y is: "+ String.valueOf(bottomItemY));
        if (topItemY < bottomItemY){
            log("Item order is as expected");
        } else {
            log("Item order is not as expected... topItem is below bottomItem");
        }
        Assertions.assertTrue(topItemY < bottomItemY);
    }



    //*************************************************************** Assert Page Functions ***************************************************************

    public void assertNavBar(String topLeftButton, Boolean LeftIsEnabled, String title, String topRightButton, Boolean RightIsEnabled) throws Exception {
        if (topLeftButton != "") {
            assertElementExistsBy(WebElementsByAccessibilityId(topLeftButton));
            assert (Boolean.parseBoolean(WebElementByAccessibilityId(topLeftButton).getAttribute("enabled")) == LeftIsEnabled);
        }
        assertElementExistsBy(WebElementsByName(title));
        if (topRightButton != "") {
            assertElementExistsBy(WebElementsByAccessibilityId(topRightButton));
            assert (Boolean.parseBoolean(WebElementByAccessibilityId(topRightButton).getAttribute("enabled")) == RightIsEnabled);
        }
    }

    //assert the Nav bar
    public void assertNavBarDropDownNav(String title1, String title2, String title3, String title4, String title5, String title6, String title7, Boolean close) throws Exception{

        //Click Main Toolbar
        ClickUIElementByAccessibilityID(title1);
        sleep(milliseconds_1);
        assertElementExistsBy(WebElementsByAccessibilityId(title1));
        if (title2 != ""){
            assertElementExistsBy(WebElementsByXpath("//XCUIElementTypeButton[@name=\"" + title2 + "\"]"));
            if (title3 != ""){
                assertElementExistsBy(WebElementsByXpath("//XCUIElementTypeButton[@name=\"" + title3 + "\"]"));
                if (title4 != ""){
                    assertElementExistsBy(WebElementsByXpath("//XCUIElementTypeButton[@name=\"" + title4 + "\"]"));
                    if (title5 != ""){
                        assertElementExistsBy(WebElementsByXpath("//XCUIElementTypeButton[@name=\"" + title5 + "\"]"));
                        if (title6 != ""){
                            assertElementExistsBy(WebElementsByXpath("//XCUIElementTypeButton[@name=\"" + title6 + "\"]"));
                            if (title7 != ""){
                                assertElementExistsBy(WebElementsByXpath("//XCUIElementTypeButton[@name=\"" + title7 + "\"]"));
                            }
                        }
                    }
                }
            }
        }

        if (close) {
            //Dismiss the toolbar
            ClickUIElementByAccessibilityID(title1);
        }
    }

    public void assertToolBar() throws Exception{
        assertElementExistsBy(WebElementsByAccessibilityId("Settings"));
        assertElementExistsBy(WebElementsByAccessibilityId("Search"));
        assertElementExistsBy(WebElementsByAccessibilityId("Screens"));
        assertElementExistsBy(WebElementsByAccessibilityId("Bookmarks"));
    }

    public void assertTabBar(String button1, String button2, String button3) throws Exception{
        assertElementExistsBy(WebElementsByAccessibilityId(button1));
        assertElementExistsBy(WebElementsByAccessibilityId(button2));
        assertElementExistsBy(WebElementsByAccessibilityId(button3));
    }

    public void assertTabBarNotesScreen() throws Exception{
        assertTabBar(AllString,TagsString,NotebooksString);
    }

    public void assertAnnotationMenu(boolean isNewHighlight, boolean DefineIsEnabled) throws Exception{
        if (isNewHighlight){
            assertElementExistsBy(WebElementsByAccessibilityId(MarkString));
            assertElementExistsBy(WebElementsByAccessibilityId(RemoveString));
            isEnabled(WebElementByAccessibilityId(RemoveString), false);
        } else {
            assertElementExistsBy(WebElementsByAccessibilityId(StyleString));
            assertElementExistsBy(WebElementsByAccessibilityId(RemoveString));
            isEnabled(WebElementByAccessibilityId(RemoveString), true);
        }
        assertElementExistsBy(WebElementsByAccessibilityId(NoteString));
        isEnabled(WebElementByAccessibilityId(NoteString),true);
        assertElementExistsBy(WebElementsByAccessibilityId(TagString));
        isEnabled(WebElementByAccessibilityId(TagString),true);
        assertElementExistsBy(WebElementsByAccessibilityId(AddToString));
        isEnabled(WebElementByAccessibilityId(AddToString),true);
        assertElementExistsBy(WebElementsByAccessibilityId(LinkString));
        isEnabled(WebElementByAccessibilityId(LinkString),true);
        assertElementExistsBy(WebElementsByAccessibilityId(CopyString));
        isEnabled(WebElementByAccessibilityId(CopyString),true);
        assertElementExistsBy(WebElementsByAccessibilityId(ShareString));
        isEnabled(WebElementByAccessibilityId(ShareString),true);
        assertElementExistsBy(WebElementsByAccessibilityId(SearchString));
        isEnabled(WebElementByAccessibilityId(SearchString),true);
        assertElementExistsBy(WebElementsByAccessibilityId(DefineString));
        isEnabled(WebElementByAccessibilityId(DefineString), DefineIsEnabled);
    }

    public void assertStyleMenu(String Style) throws Exception{
        assertElementExistsBy(WebElementsByAccessibilityId(RedString));
        assertElementExistsBy(WebElementsByAccessibilityId(OrangeString));
        assertElementExistsBy(WebElementsByAccessibilityId(YellowString));
        assertElementExistsBy(WebElementsByAccessibilityId(GreenString));
        assertElementExistsBy(WebElementsByAccessibilityId(BlueString));
        assertElementExistsBy(WebElementsByAccessibilityId(DarkBlueString));
        assertElementExistsBy(WebElementsByAccessibilityId(PurpleString));
        assertElementExistsBy(WebElementsByAccessibilityId(PinkString));
        assertElementExistsBy(WebElementsByAccessibilityId(BrownString));
        assertElementExistsBy(WebElementsByAccessibilityId(GrayString));
        assertElementExistsBy(WebElementsByAccessibilityId("Highlight Style"));
        assertElementExistsBy(WebElementsByAccessibilityId("Underline Style"));
        assertElementExistsBy(WebElementsByAccessibilityId("Clear Style"));

        if (Style == "Solid") {
            assertElementExistsBy(WebElementsByXpath("//*[@name=\"Highlight Style\"]/../*[@name=\"active-style-indicator\"]"));
        } else if (Style == "Underline"){
            assertElementExistsBy(WebElementsByXpath("//*[@name=\"Underline Style\"]/../*[@name=\"active-style-indicator\"]"));
        } else if (Style == "Clear"){
            assertElementExistsBy(WebElementsByXpath("//*[@name=\"Clear Style\"]/../*[@name=\"active-style-indicator\"]"));
        } else {
            fail("Style must be one of \"Solid\" \"Underline\" or \"Clear\"");
        }

    }

    public void clickAndAssertStyleMenu(String Color, String StartingStyle, String StyleToClick) throws Exception{
        assertStyleMenu(StartingStyle);
        if (Color == "Red"){
            ClickUIElementByAccessibilityID(RedString);
        } else if(Color =="Orange"){
            ClickUIElementByAccessibilityID(OrangeString);
        } else if(Color =="Yellow"){
            ClickUIElementByAccessibilityID(YellowString);
        } else if(Color =="Green"){
            ClickUIElementByAccessibilityID(GreenString);
        } else if(Color =="Blue"){
            ClickUIElementByAccessibilityID(BlueString);
        } else if(Color =="Dark Blue"){
            ClickUIElementByAccessibilityID(DarkBlueString);
        } else if(Color =="Purple"){
            ClickUIElementByAccessibilityID(PurpleString);
        } else if(Color =="Pink"){
            ClickUIElementByAccessibilityID(PinkString);
        } else if(Color =="Brown"){
            ClickUIElementByAccessibilityID(BrownString);
        } else if(Color =="Gray"){
            ClickUIElementByAccessibilityID(GrayString);
        } else {
            fail("Color must be one of 'Red' 'Orange' 'Yellow' 'Green' 'Blue' 'Dark Blue' 'Purple' 'Pink' 'Brown' or 'Gray'");
        }

        if (StyleToClick == "Solid") {
            ClickUIElementByAccessibilityID("Highlight Style");
            assertElementExistsBy(WebElementsByXpath("//*[@name=\"Highlight Style\"]/../*[@name=\"active-style-indicator\"]"));
        } else if (StyleToClick == "Underline"){
            ClickUIElementByAccessibilityID("Underline Style");
            assertElementExistsBy(WebElementsByXpath("//*[@name=\"Underline Style\"]/../*[@name=\"active-style-indicator\"]"));
        } else if (StyleToClick == "Clear"){
            ClickUIElementByAccessibilityID("Clear Style");
            assertElementExistsBy(WebElementsByXpath("//*[@name=\"Clear Style\"]/../*[@name=\"active-style-indicator\"]"));
        } else {
            fail("Style must be one of \"Solid\" \"Underline\" or \"Clear\"");
        }

        ClickUIElementByAccessibilityID("PopoverDismissRegion");

    }

    public void annotationStyleTest(String Color, String StartingStyle, String StyleToClick) throws Exception{
        OpenScripture(BookOfMormonString,JacobString,"1","1");
        openAnnotationMenu(WebElementByAccessibilityId("1"));
        assertAnnotationMenu(true,true);
        ClickUIElementByAccessibilityID(MarkString);
        Backup(false);
        assertAnnotationMenu(false,true);
        ClickUIElementByAccessibilityID(StyleString);
        clickAndAssertStyleMenu(Color,StartingStyle,StyleToClick);
        ClickUIElementByAccessibilityID("1");
        assertAnnotationMenu(false,true);
        ClickUIElementByAccessibilityID(RemoveString);
    }

    //Backup
    public void Backup(Boolean signIn) throws Exception{
        //Back Up Annotations?
        assertElementExistsBy(WebElementsByAccessibilityId(BackUpString));
        assertElementExistsBy(WebElementsByXpath("//*[@name= \"" + SignInBackupString + "\"]"));
        assertElementExistsBy(WebElementsByAccessibilityId(SignInString));
        assertElementExistsBy(WebElementsByAccessibilityId(CreateChurchAccountString));
        assertElementExistsBy(WebElementsByAccessibilityId(NoThanksString));
        if (signIn) {
            ClickUIElementByAccessibilityID(SignInString);
        } else {
            ClickUIElementByAccessibilityID(NoThanksString);
        }
    }


    public void assertCollectionsAndBooks(ArrayList Titles) throws Exception{
        int theCount = 0;
        for (int i = 0; i < Titles.size(); i++) {
            System.out.println(Titles.get(i));
            scrollDownTo((String) Titles.get(i));
            assertElementExistsBy(WebElementsByText((String) Titles.get(i)));
        }
    }

    public void assertSettingsitems(ArrayList<Pair<String,Integer>> Titles) throws Exception{
        int theCount = 0;
        for (int i = 0; i < Titles.size(); i++) {
            Pair<String, Integer> CurrentTitle = Titles.get(i);
            String IOSElementName = CurrentTitle.getKey();
            int expectedSwitchStatus = CurrentTitle.getValue();
            scrollDownTo(IOSElementName);
            assertElementExistsBy(WebElementsByText(IOSElementName));
            if (expectedSwitchStatus < 2){
                assertExpectedSwitchStatus(IOSElementName,expectedSwitchStatus);
            }

        }
    }


    public String getWebview() throws Exception {
        Set<java.lang.String> ContextHandles = driver.getContextHandles();
        String theWebviewToReturn = "";
        for (String theWebview : ContextHandles) {
            System.out.println("Context handle is now: " + theWebview);
            if (theWebview.contains("WEBVIEW")) {
                theWebviewToReturn = theWebview;
                break;
            }
        }
        return theWebviewToReturn;


    }





    public String getComputedCssUsingXpath(String xPath, String cssAttribute) throws Exception{
        driver.context(getWebview());

        WebElement we = WebElementByXpath(xPath);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        String script = "var s = getComputedStyle(arguments[0],null).getPropertyValue(arguments[1]);" +
                "return s;";
        String scriptReturn = (executor.executeScript(script, we, cssAttribute)).toString();
        System.out.println(scriptReturn);
        driver.context("NATIVE_APP");
        return scriptReturn;
    }



    public void assertSettingsScreen(Boolean isFirstLaunch) throws Exception{
        assertNavBar("",false,"Settings","Done",true);
        if (isFirstLaunch) {
            assertSettingsitems(UI.Content.SettingsItems);
        } else {
            fail("No asserts for settings screen not on first launch");
        }
    }


    public void assertAndClickThemeScreen(String CurrentTheme, String NewTheme, Boolean Click) throws Exception{

        assertItemIsNextTo(CurrentTheme, MoreInfoString, false);
        assertNavBar(BackString, true, ThemeString, "",false);
        assertElementExistsBy(WebElementsByName(DefaultThemeString));
        assertElementExistsBy(WebElementsByName(NightThemeString));
        assertElementExistsBy(WebElementsByName(SepiaThemeString));
        assertElementExistsBy(WebElementsByName(DarkBlueString));
        assertElementExistsBy(WebElementsByName(MagentaThemeString));
        if(Click) {
            ClickUIElementByName(NewTheme);

        }
            ClickUIElementByAccessibilityID(BackString);






    }

    public void assertChurchAccountScreen() throws Exception{
        assertNavBar("Back",true, AccountString, "",false);
        assertElementExistsBy(WebElementsByAccessibilityId("Username"));
        verifyText(Strings.UserNameString, WebElementByAccessibilityId("Username"));
        assertElementExistsBy(WebElementsByAccessibilityId("Password"));
        verifyText(PasswordString,WebElementByAccessibilityId("Password"));
        assertElementExistsBy(WebElementsByAccessibilityId("Toggle Password Visibility"));
        System.out.println(WebElementByAccessibilityId("Toggle Password Visibility").getAttribute("value"));
        verifyValue(null, WebElementByAccessibilityId("Toggle Password Visibility"));
        assertElementExistsBy(WebElementsByAccessibilityId("Sign In"));
        verifyText(SignInString,WebElementByAccessibilityId("Sign In"));
        assertElementExistsBy(WebElementsByAccessibilityId("Having trouble signing in?"));
        verifyText(TroubleSigningInString,WebElementByAccessibilityId("Having trouble signing in?"));
        assertElementExistsBy(WebElementsByXpath("//*[@name=\"Terms of Use (Updated 2018-09-01)\"]/../../XCUIElementTypeTextView"));
        verifyValue(SignInTermsOfUseString,WebElementByXpath("//*[@name=\"Terms of Use (Updated 2018-09-01)\"]/../../XCUIElementTypeTextView"));
        assertElementExistsBy(WebElementsByAccessibilityId("Terms of Use (Updated 2018-09-01)"));
        verifyText(TermsOfUseString,WebElementByAccessibilityId("Terms of Use (Updated 2018-09-01)"));
        assertElementExistsBy(WebElementsByAccessibilityId("Privacy Notice (Updated 2018-09-01)"));
        verifyText(PrivacyNoticeString,WebElementByAccessibilityId("Privacy Notice (Updated 2018-09-01)"));
    }

    public void fillAndAssertAccountScreen(String theUserName, String thePassword, Boolean showPassword, Boolean signIn) throws Exception{
        assertChurchAccountScreen();
        assertElementExistsBy(WebElementsByAccessibilityId("Username"));
        sendText("Username",theUserName);
        verifyValue(theUserName,WebElementByAccessibilityId("Username"));
        assertElementExistsBy(WebElementsByAccessibilityId("Password"));
        sendText("Password",thePassword);
        verifyValue(hidePassword(thePassword), WebElementByAccessibilityId("Password"));
        if (showPassword){
            ClickUIElementByAccessibilityID("Toggle Password Visibility");
            verifyValue("1",WebElementByAccessibilityId("Toggle Password Visibility"));
            verifyValue(thePassword,WebElementByAccessibilityId("Password"));
        }
        if (signIn){
            ClickUIElementByAccessibilityID("Sign In");

        }
    }


    public void assertSigninErrorScreen () throws Exception{

        assertElementExistsBy(WebElementsByName(SignInErrorString));
        assertElementExistsBy(WebElementsByAccessibilityId(WrongUserNameAndPasswordString));
        assertElementExistsBy(WebElementsByAccessibilityId(OkString));
    }

    public void assertLanguageScreen(String Language, String EnglishLanguageName) throws Exception{
        assertNavBar("Back",true, LanguagesTitleString, "", false);
        assertElementExistsBy(WebElementsByAccessibilityId(FindByNameString));
        assertElementExistsBy(WebElementsByAccessibilityId(DownloadedString.toUpperCase()));
        assertElementExistsBy(WebElementsByText(Language));
        if (Language == EnglishLanguageName){
            assertElementExistsBy(WebElementsByXpath("//*[@name='"+ EnglishLanguageName +"'][2]"));
        } else {
            assertElementExistsBy(WebElementsByText(EnglishLanguageName));
        }
        assertMoreInfoButton(Language,false);
        assertElementExistsBy(WebElementsByAccessibilityId(AllString.toUpperCase()));
    }

    public void assertDownloadsScreen(boolean isFreshInstall) throws Exception{
        assertNavBar("Back",true,(DownloadsString + ", " + LanguageEnglishString),"",false);
        if (isFreshInstall){
            assertHeaderWithOption(ScripturesString,DeleteAllString,false);
            assertMoreInfoButton("101.5 MB",true);
            assertNavBar("Back",true,(DownloadsString + ", " + LanguageEnglishString),"", false);
            assertHeaderWithOption(ScripturesString, DeleteAllString, false);
            assertItemIsNextTo(OldTestamentString,"18.6 MB",false);
            assertItemIsNextTo(NewTestamentString,"7.2 MB",false);
            assertItemIsNextTo(BookOfMormonString,"8 MB", false);
            assertItemIsNextTo(DoctrineAndCovenantsString, "5.3 MB",false);
            assertItemIsNextTo(PearlOfGreatPriceString, "1 MB",false);
            assertHeaderWithOption(StudyHelpsString,DeleteAllString,false);
            assertItemIsNextTo(GuideToTheScripturesString, "5.2 MB",false);
            assertItemIsNextTo(TopicalGuideString,"28.3 MB",false);
            assertItemIsNextTo(BibleDictionaryString,"4.9 MB",false);
            assertItemIsNextTo(BibleChronologyString,"356 KB", false);
            assertItemIsNextTo(HarmonyOfTheGospelsString, "553 KB", false);
            assertItemIsNextTo(JSTAppendixString,"713 KB",false);
            assertItemIsNextTo(BibleMapsString,"590 KB",false);
            assertItemIsNextTo(BiblePhotographsString,"348 KB",false);
            assertItemIsNextTo(IndexToTripleCombinationString,"19.6 MB",false);
            assertItemIsNextTo(ChurchHistoryMapsString,"344 KB",false);
            assertItemIsNextTo(ChurchHistoryPhotographsString,"270 KB",false);
            assertItemIsNextTo(AbbreviationsString,"217 KB",false);
            ClickUIElementByAccessibilityID("Back");
            assertHeaderWithOption(InspirationString,DeleteAllString,false);
            assertMoreInfoButton("38.4 MB",true);
            assertNavBar("Back",true,(DownloadsString + ", " + LanguageEnglishString),"", false);
            assertItemIsNextTo(GuideToTheScripturesString, "5.2 MB",false);
            assertItemIsNextTo(TopicalGuideString,"28.3 MB",false);
            assertItemIsNextTo(BibleDictionaryString,"4.9 MB",false);
        }
    }

    public void assertNotebookScreen(boolean isEmptyState, String sortBy) throws Exception {
        assertNavBar(HistoryBackString,true,"Notes",EditString,true);
        assertNavBarDropDownNav(NotesString,LibraryString,"","","","","",true);
        assertTabBarNotesScreen();
        if (isEmptyState){
            assertElementExistsBy(WebElementsByAccessibilityId(FindByNameString));
            isEnabled(WebElementByAccessibilityId(FindByNameString),true);
            isVisible(WebElementByAccessibilityId(FindByNameString),false);
            assertElementExistsBy(WebElementsByAccessibilityId("empty-state-notebook"));
            assertElementExistsBy(WebElementsByAccessibilityId(NoNotebooksString));
            assertElementExistsBy(WebElementsByAccessibilityId(CreateAsManyNotebooksString));
            assertElementExistsBy(WebElementsByAccessibilityId(CreateNotebookString));
        }else {
            assertElementExistsBy(WebElementsByAccessibilityId(FindByNameString));
            isEnabled(WebElementByAccessibilityId(FindByNameString),true);
            isVisible(WebElementByAccessibilityId(FindByNameString),true);
            if(sortBy == ""){
                log("Skipping sortBy assertion because nothing was passed in");
            } else if (sortBy.toLowerCase() == "recent"){
                assertElementExistsBy(WebElementsByAccessibilityId(ByMostRecentString));
            } else if (sortBy.toLowerCase() == "name"){
                assertElementExistsBy(WebElementsByAccessibilityId(ByNameString));
            } else if (sortBy.toLowerCase() == "count"){
                assertElementExistsBy(WebElementsByAccessibilityId(ByCountString));
            } else fail("Must pass in \"\", \"recent\", \"name\", or \"count\"");
            assertElementExistsBy(WebElementsByAccessibilityId(ByMostRecentString));
            isEnabled(WebElementByAccessibilityId(ByMostRecentString),true);
            isVisible(WebElementByAccessibilityId(ByMostRecentString),true);
            assertElementExistsBy(WebElementsByAccessibilityId(CreateNotebookString));
        }
        assertToolBar();
    }

    public void assertAndCreate_CreateNotebookScreen(String Title, Boolean Save)throws Exception{
        assertNavBar(CancelString,true,CreateNotebookString,SaveString,true);
        assertElementExistsBy(WebElementsByAccessibilityId("Notebook Name"));
        verifyValue(UntitledString,WebElementByAccessibilityId("Notebook Name"));
        sendText("Notebook Name", Title);
        if (Save){
            ClickUIElementByAccessibilityID(SaveString);
            assertElementExistsBy(WebElementsByXpath("//*[@name=\"" + Title + "\"]"));
            assertElementExistsBy(WebElementsByXpath("//*[@name=\""+ Title +"\"]/*[@name=\"0 items\"]"));
        } else {
            ClickUIElementByAccessibilityID(CancelString);
        }
    }

    public void assertSortByMenuAndClick(String sortBy, boolean cancel) throws Exception{
        assertElementExistsBy(WebElementsByAccessibilityId(SortMenuByMostRecentString));
        assertElementExistsBy(WebElementsByAccessibilityId(SortMenuByNameString));
        assertElementExistsBy(WebElementsByAccessibilityId(SortMenuByCountString));
        assertElementExistsBy(WebElementsByAccessibilityId(CancelString));
        if (cancel){
            ClickUIElementByAccessibilityID(CancelString);
        } else {
            if(sortBy.toLowerCase() == "recent"){
                ClickUIElementByAccessibilityID(SortMenuByMostRecentString);
                assertElementExistsBy(WebElementsByAccessibilityId(ByMostRecentString));
            } else if (sortBy.toLowerCase() == "name"){
                ClickUIElementByAccessibilityID(SortMenuByNameString);
                assertElementExistsBy(WebElementsByAccessibilityId(ByNameString));
            } else if (sortBy.toLowerCase() == "count"){
                ClickUIElementByAccessibilityID(SortMenuByCountString);
                assertElementExistsBy(WebElementsByAccessibilityId(ByCountString));
            } else {
                fail("Must pass in \"recent\", \"name\", or \"count\"");
            }
        }
    }

    public void assertEditNotebookScreen(String notebookTitle) throws Exception{
        assertNavBar(CancelString,true, EditNotebookString,SaveString,true);
        assertElementExistsBy(WebElementsByAccessibilityId(NotebookNameString));
        verifyValue(notebookTitle,WebElementByAccessibilityId(NotebookNameString));
        assertElementExistsBy(WebElementsByAccessibilityId(ClearTextString));
    }

    public void renameNotebook(String startTitle, String newTitle, Boolean Save)throws Exception{
        ClickUIElementByAccessibilityID(EditString);
        assertNavBar(HistoryBackString,true,NotesString,DoneString,true);
        ClickUIElementByName(startTitle);
        assertEditNotebookScreen(startTitle);
        sendTextWithoutClearing(NotebookNameString,NotebookTitleAddOn);
        verifyValue(startTitle + NotebookTitleAddOn, WebElementByAccessibilityId(NotebookNameString));
        ClickUIElementByAccessibilityID(ClearTextString);
        verifyValue(UntitledString,WebElementByAccessibilityId(NotebookNameString));
        sendText(NotebookNameString,newTitle);
        verifyValue(newTitle,WebElementByAccessibilityId(NotebookNameString));
        if (Save){
            ClickUIElementByAccessibilityID(SaveString);
            assertElementExistsBy(WebElementsByName(newTitle));
        }else{
            ClickUIElementByAccessibilityID(CancelString);
            assertElementExistsBy(WebElementsByName(startTitle));
        }
        assertNavBar(HistoryBackString,true,NotesString,DoneString,true);
        ClickUIElementByAccessibilityID(DoneString);
        //This line might need to change in the future if sort by was changed before calling rename Notebook
        assertNotebookScreen(false,"recent");


    }

    public void assertBookmarksScreen(Boolean isEmptyState, Boolean isOnContent)throws Exception{
        assertNavBar(CancelString,true,BookmarksString,EditString,true);
        if (isEmptyState){
            assertBookmarksScreenEmptyState();
        }else{
            log("Skipping empty state check as no empty state was expected");
            assertElementExistsBy(WebElementsByName(HistoryString));
        }
        if (isOnContent){
            assertElementExistsBy(WebElementsByName(AddBookmarkString));
        } else {
            assertElementNotPresentBy(WebElementsByName(AddBookmarkString));
        }


    }

    public void assertBookmarksScreenEditMode(Boolean isEmptyState,String title, String subtitle,Boolean delete)throws Exception{
        assertNavBar(CancelString,true,BookmarksString,DoneString,true);
        if (isEmptyState){
            assertBookmarksScreenEmptyState();
        }else{
            log("Skipping empty state check as no empty state was expected");
            assertElementExistsBy(WebElementsByName(HistoryString));
        }
        assertElementNotPresentBy(WebElementsByName(AddBookmarkString));
        assertElementExistsBy(WebElementsByAccessibilityId(HistoryIconString));
        assertElementExistsBy(WebElementsByName(title + "; " + subtitle));
        assertElementExistsBy(WebElementsByName(title));
        assertElementExistsBy(WebElementsByName(subtitle));
        assertElementExistsBy(WebElementsByName("bookmarkTag_gray"));
        assertElementNotPresentBy(WebElementsByName(UpdateString + " " + title));
        assertElementExistsBy(WebElementsByName(DeleteString + " " + title + "; " + subtitle));
        assertElementExistsBy(WebElementsByName(ReorderString + " " + title + "; " + subtitle));
        if (delete){
            ClickUIElementByName(DeleteString + " " + title + "; " + subtitle);
            assertElementExistsBy(WebElementsByName(DeleteString));
            ClickUIElementByName(DeleteString);
        }




    }

    public void assertRenameBookmarkScreen(String oldName, String newName, boolean rename)throws Exception{
        assertNavBar(BackString, true, RenameString, SaveString,true);
        assertElementExistsBy(WebElementsByAccessibilityId(BookmarkNameString));
        verifyValue(oldName,WebElementByAccessibilityId(BookmarkNameString));
        assertElementExistsBy(WebElementsByAccessibilityId(ClearTextString));
        if(rename){
            sendText(BookmarkNameString, newName);
            ClickUIElementByAccessibilityID(SaveString);
        }else{
            ClickUIElementByAccessibilityID(BackString);
        }

    }

    public void assertBookmarksScreenAndClick(Boolean isEmptyState, Boolean isOnContent, Boolean saveNew)throws Exception{
        assertBookmarksScreen(isEmptyState,isOnContent);
        if (saveNew){
            ClickUIElementByName(AddBookmarkString);
        } else {
            ClickUIElementByAccessibilityID(CancelString);
        }


    }

    public void assertBookmarksScreenEmptyState()throws Exception{
        //currently empty state elements are invisible to appium
        log("WARNING: empty state elements aren't visible to appium");
    }

    public void assertBookmarkOptionalUpdate(String title, String subtitle,Boolean update)throws Exception{
        assertElementExistsBy(WebElementsByName(title + "; " + subtitle));
        assertElementExistsBy(WebElementsByName(title));
        assertElementExistsBy(WebElementsByName(subtitle));
        assertElementExistsBy(WebElementsByName("bookmarkTag_gray"));
        assertElementExistsBy(WebElementsByName(UpdateString + " " + title));
        if (update){
            ClickUIElementByName(UpdateString + " " + title);
        }
    }

    public void assertScreensScreen(Boolean isEditMode, String currentScreenTitle) throws Exception {
        assertElementExistsBy(WebElementsByName(currentScreenTitle));
        if (isEditMode) {
            assertNavBar(CancelString, true, ScreensString, DoneString, true);
            assertElementNotPresentBy(WebElementsByAccessibilityId(AddScreenString));
        } else {
            assertNavBar(CancelString, true, ScreensString, EditString, true);
            assertElementExistsBy(WebElementsByAccessibilityId(AddScreenString));
            assertElementExistsBy(WebElementsByXpath("//*[@name=\""+ currentScreenTitle + "\"]/*[@name=\""+MoreInfoString+"\"]"));
        }

    }

    public void doubleTapScreen(WebElement Element)throws Exception {
        TouchAction action = new TouchAction(driver);
        action.tap(Element).perform();
        action.tap(Element).perform();
        log("Double Tap performed");


    }




    //*************************************************************** Nav Functions ***************************************************************

    //Scroll to by id
    public void scrollToById(String id) throws Exception {
        System.out.println("Scrolling to: " + id);
        WebElement idIsPresent = WebElementByAccessibilityId(id);
        int screenHeight = driver.manage().window().getSize().getHeight();
        int upperY = idIsPresent.getLocation().getY();
        System.out.println("Screen Height is " + screenHeight + "");
        System.out.println("upper Y is " + upperY + "");
        int currentLocationOfY;
        while (upperY <= 88) {
            log("Element is off the screen... scrolling to find.");
            scrollDown();
            delay(1);
            idIsPresent = WebElementByAccessibilityId(id);
            upperY = idIsPresent.getLocation().getY();
        }
        while (upperY >= screenHeight / 2) {
            System.out.println("scrolling down y '" + upperY + "' is >= " + screenHeight / 2 + "");
            currentLocationOfY = idIsPresent.getLocation().getY();
            scrollDown();
            delay(1);
            idIsPresent = WebElementByAccessibilityId(id);
            upperY = idIsPresent.getLocation().getY();
            log("Upper Y is:" + upperY);
            if (currentLocationOfY == upperY){
                return;
            }
        }
        while (upperY <= screenHeight / 10) {
            System.out.println("scrolling up y '" + upperY + "' is <= " + screenHeight / 8 + "");
            scrollUp();
            idIsPresent = WebElementByAccessibilityId(id);
            upperY = idIsPresent.getLocation().getY();
        }

    }

    //Scroll to by
    public void scrollToBy(WebElement TempElement) {
        System.out.println(TempElement);
        int screenHeight = driver.manage().window().getSize().getHeight();
        int upperY = TempElement.getLocation().getY();
        System.out.println("Screen Height is " + screenHeight + "");
        System.out.println("upper Y is " + upperY + "");
        while (upperY >= screenHeight / 2) {
            System.out.println("scrolling down y '" + upperY + "' is >= " + screenHeight / 2 + "");
            scrollDown();
            upperY = TempElement.getLocation().getY();
        }
        while (upperY <= screenHeight / 8) {
            System.out.println("scrolling up y '" + upperY + "' is <= " + screenHeight / 8 + "");
            scrollUp();
            upperY = TempElement.getLocation().getY();
        }

    }


    //*************************************************************** Tests ***************************************************************

    @Test
    public void launchTest() throws Exception{
        fail("Test Not Written");
    }

    //********** Tips Screen **********
    @Test
    public void skipTips() throws Exception {
        fail("Test Not Written");

    }


    @Test
    public void allTips() throws Exception {
        fail("Test Not Written");
    }

    //********** Login Screen *********

    @Test
    public void login() throws Exception {
        assertNavBar(HistoryBackString,false, LibraryString,EditString,true);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByName(SignInString);
        fillAndAssertAccountScreen(user,password,true,true);
    assertElementExistsBy(WebElementsByAccessibilityId(AccountName+" ("+user+")"));

    }

    @Test
    public void invalidLogin() throws Exception {
        assertNavBar(HistoryBackString,false, LibraryString,EditString,true);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByName(SignInString);
        fillAndAssertAccountScreen(user,wrongPassword,true,true);
        assertSigninErrorScreen();

    }

    @Test
    public void troubleSigningIn() throws Exception {
        assertNavBar(HistoryBackString,false, LibraryString,EditString,true);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByName(SignInString);
        ClickUIElementByAccessibilityID(TroubleSigningInString);
        assertElementExistsBy(WebElementsByText("URL"));
        verifyValue(AccountRecoveryURLString,WebElementByText("URL"));
        ClickUIElementByAccessibilityID("Done");
        ClickUIElementByAccessibilityID("Back");
        ClickUIElementByAccessibilityID("Done");

    }

    @Test
    public void signInCreateAccount() throws Exception {
        assertNavBar(HistoryBackString,false, LibraryString,EditString,true);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByName(CreateChurchAccountString);
        assertElementExistsBy(WebElementsByText("URL"));
        verifyValue(CreateAccountURLString,WebElementByText("URL"));
        ClickUIElementByAccessibilityID("Done");


    }




    //*********** Library Screen ***********
    @Test
    public void LibraryContentScreen() throws Exception {
        assertNavBar("History Back", false, "Library","Edit",true);
        assertToolBar();
        assertCollectionsAndBooks(UI.Content.LibraryCollections);
    }


    //********** Notes Landing Page **********
    @Test
    public void NotesLandingPage_NotSignedIn() throws Exception {
        ClickUIElementByAccessibilityID("Notes");
        assertNotebookScreen(true,"");

    }

    @Test
    public void CreateNewNotebook() throws Exception {
        ClickUIElementByAccessibilityID(NotesString);
        assertNotebookScreen(true,"");
        ClickUIElementByAccessibilityID(CreateNotebookString);
        assertAndCreate_CreateNotebookScreen(NotebookTitle1,true);


    }

    @Test
    public void DeleteANotebook_SwipeToDelete() throws Exception{
        CreateNewNotebook();
        swipeToDelete(driver.findElementByName(NotebookTitle1),true);
        assertElementNotPresentBy(WebElementsByXpath("//*[@name=\""+NotebookTitle1+"\"]"));
        assertNotebookScreen(true, "");
    }

    @Test
    public void DeleteANotebook_PartialSwipeToDelete() throws Exception{
        CreateNewNotebook();
        swipeToDelete(driver.findElementByName(NotebookTitle1),false);
        ClickUIElementByAccessibilityID(DeleteString);
        assertElementNotPresentBy(WebElementsByXpath("//*[@name=\""+NotebookTitle1+"\"]"));
        assertNotebookScreen(true, "");
    }

    @Test
    public void DeleteANotebook_Edit_Delete() throws Exception{
        CreateNewNotebook();
        ClickUIElementByAccessibilityID(EditString);
        assertNavBar(HistoryBackString,true,NotesString,DoneString,true);
        assertElementExistsBy(WebElementsByName("Delete "+ NotebookTitle1));
        ClickUIElementByName("Delete "+ NotebookTitle1);
        assertElementNotPresentBy(WebElementsByName(NotebookTitle1));
        assertNavBar(HistoryBackString,true,NotesString,DoneString,true);
        ClickUIElementByAccessibilityID(DoneString);
        assertNotebookScreen(true, "");
    }

    @Test
    public void CancelDeleteANotebook() throws Exception{
        CreateNewNotebook();
        ClickUIElementByAccessibilityID(EditString);
        assertNavBar(HistoryBackString,true,NotesString,DoneString,true);
        ClickUIElementByName("Delete "+ NotebookTitle1);
        ClickUIElementByName(NotebookTitle1);
        assertElementExistsBy(WebElementsByName(NotebookTitle1));
        assertNavBar(HistoryBackString,true,NotesString,DoneString,true);
        ClickUIElementByAccessibilityID(DoneString);
        assertNotebookScreen(false, "recent");
    }

    @Test
    public void RenameANotebook() throws Exception{
        CreateNewNotebook();
        renameNotebook(NotebookTitle1,NotebookTitle2,true);
    }

    @Test
    public void CancelRenamingANotebook() throws Exception{
        CreateNewNotebook();
        renameNotebook(NotebookTitle1,NotebookTitle2,false);
    }


    @Test
    public void CreateNewNoteInNewNotebook() throws Exception {
        ClickUIElementByAccessibilityID(NotesString);
        assertNotebookScreen(true,"");
        ClickUIElementByAccessibilityID(CreateNotebookString);
        assertAndCreate_CreateNotebookScreen(NotebookTitle1,true);
        ClickUIElementByName(NotebookTitle1);
        ClickUIElementByName(CreateNoteString);
        assertNavBar(CancelString,true,NoteString, SaveString,false);
        sendText(NoteTextString,"faith");
        assertNavBar(CancelString,true,NoteString, SaveString,true);
        ClickUIElementByAccessibilityID(SaveString);







    }

    @Test
    public void CreateNew20000CharacterNoteInNewNotebook() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void NotesSectionDeleteNote() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void NotesSectionCancelDeleteNote() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void NotesSectionEditNote() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void NotesSectionAddNoteToNotebook() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void NotesSectionSortNotebooksByCount() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void NotesSectionDefaultSortNotebooksByMostRecent() throws Exception{
        CreateNewNotebook();
        ClickUIElementByAccessibilityID(CreateNotebookString);
        assertAndCreate_CreateNotebookScreen(NotebookTitle2,true);
        assertElementExistsBy(WebElementsByAccessibilityId(ByMostRecentString));
        verifyItemOrder(WebElementByName(NotebookTitle2),WebElementByName(NotebookTitle1));
    }

    @Test
    public void NotesSectionSortNotebooksByName() throws Exception{
        logTestId("9e01ff40-0e51-441d-b67f-aa48008cd5e3");
        CreateNewNotebook();
        ClickUIElementByAccessibilityID(CreateNotebookString);
        assertAndCreate_CreateNotebookScreen(NotebookTitle2,true);
        assertElementExistsBy(WebElementsByAccessibilityId(ByMostRecentString));
        verifyItemOrder(WebElementByName(NotebookTitle2),WebElementByName(NotebookTitle1));
        ClickUIElementByAccessibilityID(ByMostRecentString);
        assertSortByMenuAndClick("name",false);
        verifyItemOrder(WebElementByName(NotebookTitle1),WebElementByName(NotebookTitle2));
    }

    @Test
    public void NotesSectionSortNotebooksByNameThenByRecent() throws Exception{
        CreateNewNotebook();
        ClickUIElementByAccessibilityID(CreateNotebookString);
        assertAndCreate_CreateNotebookScreen(NotebookTitle2,true);
        assertElementExistsBy(WebElementsByAccessibilityId(ByMostRecentString));
        verifyItemOrder(WebElementByName(NotebookTitle2),WebElementByName(NotebookTitle1));
        ClickUIElementByAccessibilityID(ByMostRecentString);
        assertSortByMenuAndClick("name",false);
        verifyItemOrder(WebElementByName(NotebookTitle1),WebElementByName(NotebookTitle2));
        ClickUIElementByAccessibilityID(ByNameString);
        assertSortByMenuAndClick("recent",false);
        verifyItemOrder(WebElementByName(NotebookTitle2),WebElementByName(NotebookTitle1));
    }

    @Test
    public void NotesSectionUseBoldFormatting() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void NotesSectionUseItalicFormatting() throws Exception {
        fail("Test Not Written");
    }


    @Test
    public void NotesSectionUseItalicAndBoldFormattingInSameNote() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void NotesSectionUseItalicAndBoldFormattingOnSameWord() throws Exception {
        fail("Test Not Written");
    }


    @Test
    public void NotesSectionUseNumberedAndBulletedLists() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void NotesSectionUseBulletedList() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void NotesSectionUseNumberedList() throws Exception {
        fail("Test Not Written");
    }


    //********** Bookmarks Landing Page **********
    @Test
    public void BookmarksLandingPageFromLibrary_NotSignedIn() throws Exception {
        ClickUIElementByAccessibilityID(BookmarksString);
        assertBookmarksScreen(true,false);
    }

    @Test
    public void BookmarksLandingPageFromContent_NotSignedIn() throws Exception {
        OpenScripture(BookOfMormonString,JacobString,"5","");
        ClickUIElementByAccessibilityID(BookmarksString);
        assertBookmarksScreen(true,true);
    }

    @Test
    public void BookmarksCreateBookmarkFromScripture() throws Exception{
        OpenScripture(BookOfMormonString,JacobString,"5","");
        ClickUIElementByAccessibilityID(BookmarksString);
        assertBookmarksScreen(true,true);
        ClickUIElementByAccessibilityID(AddBookmarkString);
    }

    @Test
    public void BookmarksCreateBookmarkFromScriptureCancel() throws Exception{
        OpenScripture(BookOfMormonString,JacobString,"5","");
        ClickUIElementByAccessibilityID(BookmarksString);
        assertBookmarksScreenAndClick(true,true,false);
        assertNavBar(HistoryBackString,true,"Jacob 5, Book of Mormon",ShowRelatedContentString,true);
    }

    @Test
    public void BookmarksCreateBookmarkFromScriptureDefault() throws Exception{
        OpenScripture(BookOfMormonString,JacobString,"5","");
        ClickUIElementByAccessibilityID(BookmarksString);
        assertBookmarksScreenAndClick(true,true,true);
        assertNavBar(HistoryBackString,true,"Jacob 5, Book of Mormon",ShowRelatedContentString,true);
        ClickUIElementByAccessibilityID(BookmarksString);
        assertBookmarksScreen(false,true);
        assertBookmarkOptionalUpdate("Jacob 5","Book of Mormon",false);
    }

    @Test
    public void BookmarksDefaultBookmarkTitleUpdatesItself() throws Exception{
        BookmarksCreateBookmarkFromScriptureDefault();
        ClickUIElementByAccessibilityID(CancelString);
        swipeNextChapter();
        ClickUIElementByAccessibilityID(BookmarksString);
        assertBookmarksScreen(false,true);
        assertBookmarkOptionalUpdate("Jacob 5","Book of Mormon",true);
        assertNavBar(HistoryBackString,true,"Jacob 6, Book of Mormon",ShowRelatedContentString,true);
        ClickUIElementByAccessibilityID(BookmarksString);
        assertBookmarksScreen(false,true);
        assertBookmarkOptionalUpdate("Jacob 6","Book of Mormon",false);
    }

    @Test
    public void BookmarkMoveNamedBookmarkToNewChapter() throws Exception{
        BookmarksCreateBookmarkFromScriptureDefault();
        ClickUIElementByAccessibilityID(BookmarksString);
        assertBookmarksScreen(false,true);
        ClickUIElementByAccessibilityID(EditString);
        assertBookmarksScreenEditMode(false,"Jacob 5","Book of Mormon", false);
        ClickUIElementByAccessibilityID("Jacob 5");
        assertRenameBookmarkScreen("Jacob 5",BookmarkOneTitle, true);
        assertBookmarksScreenEditMode(false, BookmarkOneTitle, "Jacob 5",false);
        ClickUIElementByAccessibilityID(DoneString);
        assertBookmarksScreen(false, true);
        ClickUIElementByAccessibilityID(CancelString);
        swipeNextChapter();
        ClickUIElementByAccessibilityID(BookmarksString);
        assertBookmarkOptionalUpdate(BookmarkOneTitle, "Jacob 6", true);
        ClickUIElementByAccessibilityID(BookmarksString);
        assertBookmarkOptionalUpdate(BookmarkOneTitle, "Jacob 6", false);


    }

    @Test
    public void BookmarksRenameABookmark() throws Exception{
        BookmarksCreateBookmarkFromScriptureDefault();
        ClickUIElementByAccessibilityID(BookmarksString);
        assertBookmarksScreen(false,true);
        ClickUIElementByAccessibilityID(EditString);
        assertBookmarksScreenEditMode(false,"Jacob 5","Book of Mormon", false);
        ClickUIElementByAccessibilityID("Jacob 5");
        assertRenameBookmarkScreen("Jacob 5",BookmarkOneTitle, true);
        assertBookmarksScreenEditMode(false, BookmarkOneTitle, "Jacob 5",false);
        ClickUIElementByAccessibilityID(DoneString);
        assertBookmarksScreen(false, true);
        ClickUIElementByAccessibilityID(CancelString);
    }

    @Test
    public void BookmarksRenameABookmarkCancel() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void BookmarksRenameABookmarkDefault() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void BookmarksRenameABookmarkDefaultBookmarkUpdatesToNewChapterTitle() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void BookmarksSwipeDeleteBookmark() throws Exception{
        BookmarksCreateBookmarkFromScriptureDefault();
        swipeToDelete(WebElementByName("Jacob 5; Book of Mormon"),true);
        assertBookmarksScreen(true,true);
        assertElementNotPresentBy(WebElementsByName("Jacob 5; Book of Mormon"));
    }

    @Test
    public void BookmarksSwipeDeletePartialSwipeBookmark() throws Exception{
        BookmarksCreateBookmarkFromScriptureDefault();
        swipeToDelete(WebElementByName("Jacob 5; Book of Mormon"),false);
        TapCenterScreen();
        assertElementNotPresentBy(WebElementsByText(DeleteString));
        assertBookmarksScreen(false,true);
        assertElementExistsBy(WebElementsByName("Jacob 5; Book of Mormon"));
    }

    @Test
    public void BookmarksSwipeDeletePartialSwipeTapDeleteBookmark() throws Exception{
        BookmarksCreateBookmarkFromScriptureDefault();
        swipeToDelete(WebElementByName("Jacob 5; Book of Mormon"),false);
        ClickUIElementByText(DeleteString);
        assertElementNotPresentBy(WebElementsByText(DeleteString));
        assertBookmarksScreen(true,true);
        assertElementNotPresentBy(WebElementsByName("Jacob 5; Book of Mormon"));
    }

    @Test
    public void BookmarksDeleteBookmark() throws Exception{
        BookmarksCreateBookmarkFromScriptureDefault();
        ClickUIElementByAccessibilityID(EditString);
        assertBookmarksScreenEditMode(false,"Jacob 5","Book of Mormon",true);
        assertBookmarksScreen(true,true);
        assertElementNotPresentBy(WebElementsByName("Jacob 5; Book of Mormon"));
        ClickUIElementByAccessibilityID(CancelString);

    }

    @Test
    public void BookmarksCancelDeleteBookmark() throws Exception{
        BookmarksCreateBookmarkFromScriptureDefault();
        ClickUIElementByAccessibilityID(EditString);
        assertBookmarksScreenEditMode(false,"Jacob 5","Book of Mormon",false);
        ClickUIElementByAccessibilityID(DoneString);
        assertBookmarksScreen(true,true);
        assertElementExistsBy(WebElementsByName("Jacob 5; Book of Mormon"));
        ClickUIElementByAccessibilityID(CancelString);
    }

    @Test
    public void BookmarksHideAddAndUpdateButtonsWhenNotOnChapter() throws Exception{
        BookmarksCreateBookmarkFromScriptureDefault();
        ClickUIElementByAccessibilityID(CancelString);
        NavigateToLibrary();
        ClickUIElementByAccessibilityID(BookmarksString);
        assertBookmarksScreen(false,false);
        assertElementNotPresentBy(WebElementsByName(UpdateString + " " + "Jacob 5"));
    }

    @Test
    public void BookmarksReorderBookmarks() throws Exception{
        fail("Test Not Written");

    }


    @Test
    public void BookmarksLandingPageFromNotes_NotSignedIn() throws Exception {
        ClickUIElementByAccessibilityID(NotesString);
        assertNotebookScreen(true,"");
        ClickUIElementByAccessibilityID(BookmarksString);
        assertBookmarksScreen(true,false);
        ClickUIElementByAccessibilityID(CancelString);
        assertNotebookScreen(true,"");

    }

    @Test
    public void ScreensScreenFromNotesScreen() throws Exception {
        ClickUIElementByAccessibilityID(NotesString);
        ClickUIElementByAccessibilityID(ScreensString);
        assertScreensScreen(false,NotesString);

    }

    @Test
    public void ScreensScreenFromLibrary() throws Exception {
        ClickUIElementByAccessibilityID(ScreensString);
        assertScreensScreen(false,LibraryString);


    }


    //********** Settings Screen **********
    @Test
    public void settingsScreenLandingPageNotSignedIn() throws Exception {
        ClickUIElementByAccessibilityID("Settings");
        assertNavBar("",false,"Settings","Done",true);
        scrollDownTo(Strings.AccountString.toUpperCase());
        assertElementExistsBy(WebElementsByText(Strings.AccountString.toUpperCase()));
        scrollDownTo(Strings.SignInString);
        assertElementExistsBy(WebElementsByText(Strings.SignInString));
        scrollDownTo(Strings.CreateChurchAccountString);
        assertElementExistsBy(WebElementsByText(Strings.CreateChurchAccountString));
        scrollDownTo(Strings.ChurchAccountExplainationString);
        assertElementExistsBy(WebElementsByText(Strings.ChurchAccountExplainationString));
        scrollDownTo(Strings.FeedbackString.toUpperCase());
        assertElementExistsBy(WebElementsByText(Strings.FeedbackString.toUpperCase()));
        scrollDownTo(Strings.SendFeedbackString);
        assertElementExistsBy(WebElementsByText(Strings.SendFeedbackString));
        scrollDownTo(Strings.ContentString.toUpperCase());
        assertElementExistsBy(WebElementsByText(Strings.ContentString.toUpperCase()));
        scrollDownTo(Strings.LanguageTitleString);
        assertElementExistsBy(WebElementsByText(Strings.LanguageTitleString));
        scrollDownTo(Strings.LanguageEnglishString);
        assertElementExistsBy(WebElementsByText(Strings.LanguageEnglishString));
        scrollDownTo(Strings.DownloadsString);
        assertElementExistsBy(WebElementsByText(Strings.DownloadsString));
        scrollDownTo(Strings.DisplayString.toUpperCase());
        assertElementExistsBy(WebElementsByText(Strings.DisplayString.toUpperCase()));
        scrollDownTo(Strings.TextSizeString);
        assertElementExistsBy(WebElementsByText(Strings.TextSizeString));
        scrollDownTo(Strings.ThemeString);
        assertElementExistsBy(WebElementsByText(Strings.ThemeString));
        scrollDownTo(Strings.ListModeString);
        assertElementExistsBy(WebElementsByText(Strings.ListModeString));
        scrollDownTo("List Mode Switch");
        assertElementExistsBy(WebElementsByText("List Mode Switch"));
        assertExpectedSwitchStatus("List Mode Switch",0);
        scrollDownTo(Strings.HideFootnotesString);
        assertElementExistsBy(WebElementsByText(Strings.HideFootnotesString));
        scrollDownTo("Hide Footnotes Switch");
        assertElementExistsBy(WebElementsByText("Hide Footnotes Switch"));
        assertExpectedSwitchStatus("Hide Footnotes Switch",0);
        scrollDownTo(Strings.AllowFullscreenString);
        assertElementExistsBy(WebElementsByText(Strings.AllowFullscreenString));
        scrollDownTo("Allow Fullscreen Switch");
        assertElementExistsBy(WebElementsByText("Allow Fullscreen Switch"));
        assertExpectedSwitchStatus("Allow Fullscreen Switch",1);
        scrollDownTo(Strings.AdvancedString.toUpperCase());
        assertElementExistsBy(WebElementsByText(Strings.AdvancedString.toUpperCase()));
        scrollDownTo(Strings.InAppNotificationsString);
        assertElementExistsBy(WebElementsByText(Strings.InAppNotificationsString));
        scrollDownTo("Should Allow In-App Notifications");
        assertElementExistsBy(WebElementsByText("Should Allow In-App Notifications"));
        assertExpectedSwitchStatus("Should Allow In-App Notifications",1);
        scrollDownTo(Strings.ObsoleteContentString);
        assertElementExistsBy(WebElementsByText(Strings.ObsoleteContentString));
        scrollDownTo("Should Show Obsolete Content");
        assertElementExistsBy(WebElementsByText("Should Show Obsolete Content"));
        assertExpectedSwitchStatus("Should Show Obsolete Content",0);
        scrollDownTo(Strings.CellularDataString);
        assertElementExistsBy(WebElementsByText(Strings.CellularDataString));
        scrollDownTo("Should Update the Content Catalog Over Cellular");
        assertElementExistsBy(WebElementsByText("Should Update the Content Catalog Over Cellular"));
        assertExpectedSwitchStatus("Should Update the Content Catalog Over Cellular",1);
        scrollDownTo(Strings.AdditionalInfoString.toUpperCase());
        assertElementExistsBy(WebElementsByText(Strings.AdditionalInfoString.toUpperCase()));
        scrollDownTo(Strings.FeaturedAppsString);
        assertElementExistsBy(WebElementsByText(Strings.FeaturedAppsString));
        scrollDownTo(Strings.AppDetailsString);
        assertElementExistsBy(WebElementsByText(Strings.AppDetailsString));
        scrollDownTo(Strings.AboutString);
        assertElementExistsBy(WebElementsByText(Strings.AboutString));
        scrollDownTo(Strings.ViewSourceString);
        assertElementExistsBy(WebElementsByText(Strings.ViewSourceString));
        scrollDownTo("Should View Source");
        assertElementExistsBy(WebElementsByText("Should View Source"));
        assertExpectedSwitchStatus("Should View Source",1);

    }

    @Test
    public void settingsScreenLoginCorrectUserNameAndPassword() throws Exception {
        ClickUIElementByAccessibilityID("Settings");
        assertNavBar("",false,"Settings","Done",true);
        scrollDownTo(SignInString);
        assertElementExistsBy(WebElementsByText(SignInString));
        ClickUIElementByText(SignInString);
        fillAndAssertAccountScreen(user, password,true,true);
        assertElementExistsBy(WebElementsByAccessibilityId("Account options"));
        verifyValue(AccountName, WebElementByAccessibilityId("Account options"));
        assertElementExistsBy(WebElementsByAccessibilityId(AccountNameAndUserName));
        //Large amount of annotations causes the following to time out. Commented out to save time

        //delay(15);
        //assertElementExistsBy(WebElementsByTextContains("Last Sync"));
        ClickUIElementByAccessibilityID("Done");
    }

    @Test
    public void settingsScreenLoginInvalidLogin() throws Exception {
        ClickUIElementByAccessibilityID("Settings");
        assertNavBar("",false,"Settings","Done",true);
        scrollDownTo(SignInString);
        assertElementExistsBy(WebElementsByText(SignInString));
        ClickUIElementByText(SignInString);
        fillAndAssertAccountScreen(user, wrongPassword,true,true);
        assertElementExistsBy(WebElementsByText(SignInErrorString));
        assertElementExistsBy(WebElementsByAccessibilityId(WrongUserNameAndPasswordString));
        assertElementExistsBy(WebElementsByAccessibilityId("OK"));
        ClickUIElementByAccessibilityID("OK");
        ClickUIElementByAccessibilityID("Back");
        ClickUIElementByAccessibilityID("Done");
    }

    @Test
    public void settingsScreenLoginTroubleSigningIn() throws Exception {
        ClickUIElementByAccessibilityID("Settings");
        assertNavBar("",false,"Settings","Done",true);
        scrollDownTo(SignInString);
        assertElementExistsBy(WebElementsByText(SignInString));
        ClickUIElementByText(SignInString);
        fillAndAssertAccountScreen(user, wrongPassword,true,true);
        assertElementExistsBy(WebElementsByText(SignInErrorString));
        assertElementExistsBy(WebElementsByAccessibilityId(WrongUserNameAndPasswordString));
        assertElementExistsBy(WebElementsByAccessibilityId("OK"));
        ClickUIElementByAccessibilityID("OK");
        ClickUIElementByAccessibilityID(TroubleSigningInString);
        assertElementExistsBy(WebElementsByText("URL"));
        verifyValue(AccountRecoveryURLString,WebElementByText("URL"));
        ClickUIElementByAccessibilityID("Done");
        ClickUIElementByAccessibilityID("Back");
        ClickUIElementByAccessibilityID("Done");



    }


    @Test
    public void settingsScreenCreateChurchAccount() throws Exception {
        ClickUIElementByAccessibilityID("Settings");
        assertNavBar("",false,"Settings","Done",true);
        scrollDownTo(CreateChurchAccountString);
        assertElementExistsBy(WebElementsByText(CreateChurchAccountString));
        ClickUIElementByText(CreateChurchAccountString);
        assertElementExistsBy(WebElementsByText("URL"));
        verifyValue(AccountURLString,WebElementByText("URL"));
        ClickUIElementByAccessibilityID("Done");


    }


    @Test
    public void settingsScreenWhatsNewSwitch() throws Exception{
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByName(WhatsNewString);
        assertElementExistsBy(WebElementsByXpath("//*[@name=\"TipsViewController\"]/XCUIElementTypeCell"));
        log(WebElementByXpath("//*[@name=\"TipsViewController\"]/XCUIElementTypeCell").getAttribute("name"));
        Assert.assertTrue(WebElementByXpath("//*[@name=\"TipsViewController\"]/XCUIElementTypeCell").getAttribute("name")!="");
        assertNavBar(BackString,true,"What’s New", "", false);
        ClickUIElementByAccessibilityID(BackString);
        assertNavBar("",false,"Settings",DoneString,true);
        ClickUIElementByAccessibilityID(SettingsString);


    }

    @Test
    public void settingsScreenUserGuideSwitch() throws Exception{
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByName(UserGuideString);
        verifyValue(UserGuideURLString,WebElementByName("URL"));
        FindElementByContainsText("Done");
        ClickUIElementByText("Done");
        assertNavBar("",false,"Settings",DoneString,true);
        ClickUIElementByAccessibilityID(SettingsString);

    }





    @Test
    public void settingsScreenLanguagePage() throws Exception {
        ClickUIElementByAccessibilityID("Settings");
        assertNavBar("",false,"Settings","Done",true);
        scrollDownTo(LanguageTitleString);
        ClickUIElementByText(LanguageTitleString);
        assertLanguageScreen(LanguageEnglishString,LanguageEnglishString);
    }

    @Test
    public void settingsScreenLanguageSearchDutAndNeder() throws Exception{
        settingsScreenLanguagePage();
        sendText(FindByNameString,"Dut");
        assertElementExistsBy(WebElementsByAccessibilityId("Clear text"));
        assertElementExistsBy(WebElementsByAccessibilityId(LanguageDutchString));
        assertElementExistsBy(WebElementsByAccessibilityId(LanguageNederlandsString));
        assertElementExistsBy(WebElementsByAccessibilityId(LanguageNederlandsString + ", " + LanguageDutchString));
        ClickUIElementByAccessibilityID("Clear text");
        sendText(FindByNameString,"Neder");
        assertElementExistsBy(WebElementsByAccessibilityId("Clear text"));
        assertElementExistsBy(WebElementsByAccessibilityId(LanguageDutchString));
        assertElementExistsBy(WebElementsByAccessibilityId(LanguageNederlandsString));
        assertElementExistsBy(WebElementsByAccessibilityId(LanguageNederlandsString + ", " + LanguageDutchString));

    }

    @Test
    public void settingsScreenLanguageSelectDutch() throws Exception{
        settingsScreenLanguageSearchDutAndNeder();
        ClickUIElementByAccessibilityID("Dutch");
        delay(2);
        assertNavBar("History Back",true,LibraryDutchString,EditString,true);
        assertElementExistsBy(WebElementsByAccessibilityId(ScripturesDutchString));
        assertElementExistsBy(WebElementsByAccessibilityId(GeneralConferenceDutchString));
        ClickUIElementByAccessibilityID("Settings");
        assertElementExistsBy(WebElementsByText(LanguageDutchString));
        ClickUIElementByAccessibilityID(LanguageTitleString);
        assertLanguageScreen(LanguageNederlandsString,LanguageDutchString);
    }

    @Test
    public void settingsScreenLanguageEnglishDutchEnglish() throws Exception{
        settingsScreenLanguageSelectDutch();
        ClickUIElementByAccessibilityID(LanguageEnglishString);
        delay(2);
        assertNavBar("History Back",true,LibraryString,EditString,true);
        ClickUIElementByAccessibilityID("Settings");
        assertElementExistsBy(WebElementsByText(LanguageEnglishString));
        ClickUIElementByAccessibilityID(LanguageTitleString);
        assertLanguageScreen(LanguageEnglishString,LanguageEnglishString);

    }

    @Test
    public void settingsScreenLanguageSwipeDeleteDutch() throws Exception{
        settingsScreenLanguageEnglishDutchEnglish();
        swipeToDelete(WebElementByAccessibilityId(LanguageNederlandsString),true);
        assertElementCount(WebElementsByText(LanguageNederlandsString),1);
    }

    @Test
    public void settingsScreenLanguagePartialSwipetoDeleteDutchTapDelete() throws Exception{
        settingsScreenLanguageEnglishDutchEnglish();
        swipeToDelete(WebElementByAccessibilityId(LanguageNederlandsString),false);
        ClickUIElementByText(DeleteString);
        assertElementCount(WebElementsByText(LanguageNederlandsString),1);
    }

    @Test
    public void settingsScreenDownloadedMediaLandingPage_Empty() throws Exception {
        ClickUIElementByAccessibilityID("Settings");
        assertNavBar("",false,"Settings","Done",true);
        scrollDownTo(DownloadsString);
        ClickUIElementByText(DownloadsString);
        assertDownloadsScreen(true);
    }

    @Test
    public void settingsScreenDownloadedMediaLandingPage_Empty_MoreOptionsMenu() throws Exception {
        ClickUIElementByAccessibilityID("Settings");
        assertNavBar("", false, "Settings", "Done", true);
        scrollDownTo(DownloadsString);
        ClickUIElementByText(DownloadsString);
        assertDownloadsScreen(true);

    }

    @Test
    public void settingsScreenDownloadedMediaGeneralConferenceDownloaded_AccessedThroughMoreOptionsMenu() throws Exception {
        OpenConference("October", "2017", "Turn On Your Light", false);
        assertNavBarDropDownNav("Turn On Your Light", "October 2017","General Conference", "Library", "", "", "", true);
        NavigateToLibrary();
        ClickUIElementByAccessibilityID("Settings");
        assertNavBar("", false, "Settings", "Done", true);
        scrollDownTo(DownloadsString);
        ClickUIElementByText(DownloadsString);
        ClickUIElementByAccessibilityID(GeneralConferenceString);
        assertElementExistsBy(WebElementsByName("2017"));
        assertElementExistsBy(WebElementsByAccessibilityId(DeleteAllString));
        assertElementExistsBy(WebElementsByAccessibilityId("October 2017"));
        assertElementExistsBy(WebElementsByAccessibilityId("1.1 MB"));



    }

    @Test
    public void settingsScreenDownloadedMediaGeneralConferenceDownloaded_CancelAllWithOneDownload() throws Exception {
        OpenConference("October", "2017", "", false);
        assertNavBarDropDownNav("Turn On Your Light", "October 2017","General Conference", "Library", "", "", "", true);
        NavigateToLibrary();
        ClickUIElementByAccessibilityID("Settings");
        assertNavBar("", false, "Settings", "Done", true);
        scrollDownTo(DownloadsString);
        ClickUIElementByText(DownloadsString);
        ClickUIElementByAccessibilityID(GeneralConferenceString);
        assertElementExistsBy(WebElementsByName("2017"));
        assertElementExistsBy(WebElementsByAccessibilityId(DeleteAllString));
        assertElementExistsBy(WebElementsByAccessibilityId("October 2017"));
        assertElementExistsBy(WebElementsByAccessibilityId("1.1 MB"));

    }

//    @Test
//    public void settingsScreenDownloadedMediaOTDownloaded_CancelAll_MultipleDownloads() throws Exception {
//       fail("Test Not Written");
//
//    }

    @Test
    public void settingsScreenLimitMobileNetworkUseSwitch() throws Exception {
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(CellularDataString);
        ClickUIElementByAccessibilityID(CellularButtonString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);





    }

    //Can't turn off wifi yet
//    @Test
//    public void settingsScreenLimitMobileNetworkUseAttemptToDownloadBook() throws Exception {
//        fail("Test Not Written");
//
//
//    }

    @Test
    public void settingsScreenThemeRadioButtons() throws Exception {
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ThemeString);
        ClickUIElementByAccessibilityID(ThemeString);
        assertAndClickThemeScreen(DefaultThemeString, NightThemeString, true);
        ClickUIElementByAccessibilityID(ThemeString);
        assertAndClickThemeScreen(NightThemeString, SepiaThemeString, true);
        ClickUIElementByAccessibilityID(ThemeString);
        assertAndClickThemeScreen(SepiaThemeString, DarkBlueThemeString, true);
        ClickUIElementByAccessibilityID(ThemeString);
        assertAndClickThemeScreen(DarkBlueThemeString, MagentaThemeString, true);
        ClickUIElementByAccessibilityID(ThemeString);
        assertAndClickThemeScreen(MagentaThemeString, DefaultThemeString, true);


    }


    @Test
    public void settingsScreenThemeAllThemesTextColorAndFootnoteColor() throws Exception {
        OpenScripture("Book of Mormon", "Jacob", "1", "");
        getComputedCssUsingXpath("//*[@id=\"title1\"]/span", "color");
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ThemeString);
        ClickUIElementByAccessibilityID(ThemeString);
        assertAndClickThemeScreen(DefaultThemeString, NightThemeString, true);
        assertNavBar("", false, "Settings", DoneString, true);
        ClickUIElementByAccessibilityID(DoneString);
        getComputedCssUsingXpath("//*[@id=\"title1\"]/span", "color");
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ThemeString);
        ClickUIElementByAccessibilityID(ThemeString);
        assertAndClickThemeScreen(NightThemeString, DefaultThemeString, true);
        assertNavBar("", false, "Settings", DoneString, true);
        ClickUIElementByAccessibilityID(DoneString);


    }


    @Test
    public void settingsScreenTextSize() throws Exception {
        OpenScripture("Book of Mormon","Jacob","1", "1");
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(TextSizeString);
        ClickUIElementByAccessibilityID(TextSizeString);
        ClickSeekBarAt(WebElementByXpath("//XCUIElementTypeOther[@name=\"Text Size\"]"), 3, 5);
        assertNavBar(BackString, true,TextSizeString, "", false);
        ClickUIElementByAccessibilityID(BackString);
        assertNavBar("",false,SettingsString, DoneString, true);
        ClickUIElementByAccessibilityID(DoneString);




    }

    @Test
    public void settingsScreenTextSizeCancelSameContent() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenTextSizeChangeSliderThenCancelSameContent() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenTextSizeAllSizesSameRelatedContentScripture() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenTextSizeOneTitle_Subtitle_Body() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenTextSizeTwoTitle_Subtitle_Body() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenTextSizeThreeTitle_Subtitle_Body() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenTextSizeFourTitle_Subtitle_Body() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenTextSizeFiveTitle_Subtitle_Body() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenTextSizeSixTitle_Subtitle_Body() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenTextSizeSevenTitle_Subtitle_Body() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenListModeSwitch() throws Exception {
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ListModeString);
        ClickUIElementByAccessibilityID(ListModeString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
    }

    @Test
    public void settingsScreenListModeSwitchPersists() throws Exception {
        ClickUIElementByAccessibilityID(ScripturesString);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ListModeString);
        ClickUIElementByAccessibilityID(ListModeString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ListModeString);
        ClickUIElementByAccessibilityID(ListModeString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);

    }

    @Test
    public void settingsScreenListModeLibraryScreenScripturesWidth() throws Exception {
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ListModeString);
        ClickUIElementByAccessibilityID(ListModeString);
        assertNavBar("",false,"Settings",DoneString,true);
        ClickUIElementByAccessibilityID(DoneString);
        ClickUIElementByAccessibilityID(ScripturesString);
        ClickUIElementByAccessibilityID(DoctrineAndCovenantsInstalledString);



    }

    @Test
    public void settingsScreenListModeLibraryContentScreenCategories() throws Exception {
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ListModeString);
        ClickUIElementByAccessibilityID(ListModeString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        assertNavBar(HistoryBackString,false,"Library", EditString,true);
        assertElementExistsBy(WebElementsByName(CheckScripturesString));
        assertElementExistsBy(WebElementsByAccessibilityId(ScripturesString));
        assertElementExistsBy(WebElementsByAccessibilityId(ComeFollowMeString));
        assertElementExistsBy(WebElementsByAccessibilityId(GeneralConferenceString));
        assertElementExistsBy(WebElementsByName(MyCollectionsString));
        assertElementExistsBy(WebElementsByAccessibilityId(NotesString));
        assertElementExistsBy(WebElementsByAccessibilityId(StudyPlansString));
        assertElementExistsBy(WebElementsByName(InspirationString.toUpperCase()));
        assertElementExistsBy(WebElementsByAccessibilityId(MusicString));
        assertElementExistsBy(WebElementsByAccessibilityId(MagazinesString));
        assertElementExistsBy(WebElementsByAccessibilityId(SeminariesAncInstituesString));
        assertElementExistsBy(WebElementsByAccessibilityId(TeachingsOfPresidentsString));
        assertElementExistsBy(WebElementsByAccessibilityId(VideosString));
        assertElementExistsBy(WebElementsByAccessibilityId(TopicsString));
        assertElementExistsBy(WebElementsByAccessibilityId(JesusChristString));
        assertElementExistsBy(WebElementsByAccessibilityId(MissionaryString));
        scrollToById(IndividualsAndFamiliesString);
        assertElementExistsBy(WebElementsByName(AudiencesString));
        assertElementExistsBy(WebElementsByAccessibilityId(IndividualsAndFamiliesString));
        assertElementExistsBy(WebElementsByAccessibilityId(YoungAdultsString));
        assertElementExistsBy(WebElementsByAccessibilityId(YouthString));
        assertElementExistsBy(WebElementsByAccessibilityId(ChildrenString));
        assertElementExistsBy(WebElementsByAccessibilityId(LeadersString));
        assertElementExistsBy(WebElementsByAccessibilityId(OtherString.toUpperCase()));
        assertElementExistsBy(WebElementsByAccessibilityId(ChurchHistoryString));
        assertElementExistsBy(WebElementsByAccessibilityId(TempleAndFamilyHistoryString));
        assertElementExistsBy(WebElementsByAccessibilityId(LifeHelpString));
        assertElementExistsBy(WebElementsByAccessibilityId(SelfRelianceString));
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ListModeString);
        ClickUIElementByAccessibilityID(ListModeString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);



    }

    @Test
    public void settingsScreenListModeScripturesContentScreenCategories() throws Exception {
        fail("Test Not Written");
        //Need to work with Coordinates.



    }

    @Test
    public void settingsScreenListModeScripturesScreenCategories() throws Exception {
        ClickUIElementByAccessibilityID(ScripturesString);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ListModeString);
        ClickUIElementByAccessibilityID(ListModeString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        assertElementExistsBy(WebElementsByName(ScripturesString.toUpperCase()));
        assertElementExistsBy(WebElementsByAccessibilityId(OldTestamentString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(NewTestamentString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(BookOfMormonString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(DoctrineAndCovenantsString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(PearlOfGreatPriceString+InstalledString));
        assertElementExistsBy(WebElementsByName(StudyHelpsString.toUpperCase()));
        assertElementExistsBy(WebElementsByAccessibilityId(GuideToTheScripturesString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(TopicalGuideString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(BibleDictionaryString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(BibleChronologyString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(HarmonyOfTheGospelsString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(JSTAppendixString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(BibleMapsString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(BiblePhotographsString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(IndexToTripleCombinationString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(ChurchHistoryMapsString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(ChurchHistoryPhotographsString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(AbbreviationsString+InstalledString));
        assertElementExistsBy(WebElementsByAccessibilityId(AboutScripturesString+NotInstalledString));
        assertNavBar(HistoryBackString, true,"Scriptures, Library", EditString,true);
        ClickUIElementByAccessibilityID(HistoryBackString);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ListModeString);
        ClickUIElementByAccessibilityID(ListModeString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
    }


    @Test
    public void settingsScreenHideFootnotesSwitch() throws Exception {
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(HideFootnotesString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
    }

    @Test
    public void settingsScreenHideFootnotesSwitchPersists() throws Exception {
        ClickUIElementByAccessibilityID(ScripturesString);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(HideFootnotesString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(HideFootnotesString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
    }

    @Test
    public void settingsScreenHideFootnotesInContent() throws Exception {
        OpenScripture("Book of Mormon", "Jacob", "1", "");
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(HideFootnotesString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        delay(1);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        assertNavBar(HistoryBackString, true, "Jacob 1, Book of Mormon", ShowRelatedContentString, true);
        ClickUIElementByAccessibilityID(HistoryBackString);




    }

    @Test
    public void settingsScreenHideFootnotesInContentTextColor() throws Exception {
        OpenScripture("Book of Mormon", "Jacob", "1", "");
        getComputedCssUsingXpath("//*[@id=\"p1\"]/span[2]", "color");
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(HideFootnotesString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        delay(1);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        getComputedCssUsingXpath("//*[@id=\"p1\"]/span[2]", "color");
        assertNavBarDropDownNav("Jacob 1","Jacob","Book of Mormon","Scriptures","Library","","",false );
        ClickUIElementByAccessibilityID(LibraryString);

    }

    @Test
    public void settingsScreenHideFootnotesInContentTextColorThenShow() throws Exception {
        OpenScripture("Book of Mormon", "Jacob", "1", "");
        getComputedCssUsingXpath("//*[@id=\"p1\"]/span[2]", "color");
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(HideFootnotesString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        delay(1);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        getComputedCssUsingXpath("//*[@id=\"p1\"]/span[2]", "color");
        delay(1);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        getComputedCssUsingXpath("//*[@id=\"p1\"]/span[2]", "color");
        assertNavBarDropDownNav("Jacob 1","Jacob","Book of Mormon","Scriptures","Library","","",false );
        ClickUIElementByAccessibilityID(LibraryString);
    }


    @Test
    public void settingsScreenHideFootnotesInContentThenShow() throws Exception {
        OpenScripture("Book of Mormon", "Jacob", "1", "");
        getComputedCssUsingXpath("//*[@id=\"p1\"]/span[2]/sup", "display");
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(HideFootnotesString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        delay(1);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        getComputedCssUsingXpath("//*[@id=\"p1\"]/span[2]/sup", "display");
        delay(1);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        getComputedCssUsingXpath("//*[@id=\"p1\"]/span[2]/sup", "display");
        assertNavBarDropDownNav("Jacob 1","Jacob","Book of Mormon","Scriptures","Library","","",false );
        ClickUIElementByAccessibilityID(LibraryString);
    }

    @Test
    public void settingsScreenHideFootnotesInContentDCJumpLinks() throws Exception {
        ClickUIElementByAccessibilityID(ScripturesString);
        ClickUIElementByAccessibilityID(DoctrineAndCovenantsInstalledString);
        ClickUIElementByName("8");
        getComputedCssUsingXpath("//*[@id=\"p1\"]/span[2]", "color");
        ClickUIElementByName("1–5");
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(HideFootnotesString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        getComputedCssUsingXpath("//*[@id=\"p1\"]/span[2]", "color");
        assertNavBarDropDownNav("Doctrine and Covenants 8","Doctrine and Covenants","Scriptures","Library","","","",false );
        ClickUIElementByAccessibilityID(LibraryString);
    }



    @Test
    public void settingsScreenHideFootnotesInContentTextColorDCJumpLinks() throws Exception {
        ClickUIElementByAccessibilityID(ScripturesString);
        ClickUIElementByAccessibilityID(DoctrineAndCovenantsInstalledString);
        ClickUIElementByName("8");
        getComputedCssUsingXpath("//*[@id=\"p1\"]/span[2]", "color");
        ClickUIElementByName("1–5");
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(HideFootnotesString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        getComputedCssUsingXpath("//*[@id=\"p1\"]/span[2]", "color");
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(HideFootnotesString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        getComputedCssUsingXpath("//*[@id=\"p1\"]/span[2]", "color");
        assertNavBarDropDownNav("Doctrine and Covenants 8","Doctrine and Covenants","Scriptures","Library","","","",false );
        ClickUIElementByAccessibilityID(LibraryString);
    }


    @Test
    public void settingsAllowFullscreenSwitch() throws Exception {
        OpenScripture("Book of Mormon", "Jacob", "1","");
        doubleTapScreen(WebElementByAccessibilityId(ChapterOneString));
        delay(1);
        doubleTapScreen(WebElementByAccessibilityId(ChapterOneString));
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(AllowFullscreenString);
        ClickUIElementByAccessibilityID(AllowFullscreenString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        doubleTapScreen(WebElementByAccessibilityId(ChapterOneString));
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(AllowFullscreenString);
        ClickUIElementByAccessibilityID(AllowFullscreenString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        doubleTapScreen(WebElementByAccessibilityId(ChapterOneString));
        assertElementNotPresentBy(WebElementsByName("JACOB 1"));
        assertElementNotPresentBy(WebElementsByAccessibilityId("Toolbar"));
        doubleTapScreen(WebElementByAccessibilityId(ChapterOneString));
        assertNavBar(HistoryBackString,true,"Jacob 1",ShowRelatedContentString,true);
        ClickUIElementByAccessibilityID(HistoryBackString);
    }

    @Test
    public void settingsAllowFullscreenSwitchPersists() throws Exception {
        OpenScripture(BookOfMormonString,JacobString,"1","");
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(AllowFullscreenString);
        ClickUIElementByAccessibilityID(AllowFullscreenString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(AllowFullscreenString);
        ClickUIElementByAccessibilityID(AllowFullscreenString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);

    }

    @Test
    public void settingsAllowFullscreenSwitchCheckTwoScreensOptionOnToOffFromMainScreen() throws Exception {
        OpenScripture(BookOfMormonString,JacobString,"1","");
        doubleTapScreen(WebElementByAccessibilityId(ChapterOneString));
        delay(1);
        doubleTapScreen(WebElementByAccessibilityId(ChapterOneString));
        assertToolBar();
        ClickUIElementByAccessibilityID(ScreensString);
        ClickUIElementByAccessibilityID(AddScreenString);
        OpenScripture(BookOfMormonString,JacobString,"2","");
        doubleTapScreen(WebElementByAccessibilityId(ChapterTwoString));
        delay(1);
        doubleTapScreen(WebElementByAccessibilityId(ChapterTwoString));
        ClickUIElementByAccessibilityID(ScreensString);
        ClickUIElementByAccessibilityID(AddScreenString);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(AllowFullscreenString);
        ClickUIElementByAccessibilityID(AllowFullscreenString);
        assertNavBar("", false,"Settings", DoneString,true);
        ClickUIElementByAccessibilityID(DoneString);
        assertToolBar();
        ClickUIElementByAccessibilityID(ScreensString);
        ClickUIElementByName("Jacob 1");
        doubleTapScreen(WebElementByAccessibilityId(ChapterOneString));
        delay(1);
        doubleTapScreen(WebElementByAccessibilityId(ChapterOneString));


    }

    @Test
    public void settingsAllowFullscreenSwitchCheckTwoScreensOptionOnToOffFromSecondaryScreen() throws Exception {
        OpenScripture(BookOfMormonString, JacobString, "1", "");
        doubleTapScreen(WebElementByAccessibilityId(ChapterOneString));
        delay(1);
        doubleTapScreen(WebElementByAccessibilityId(ChapterOneString));
        assertToolBar();
        ClickUIElementByAccessibilityID(ScreensString);
        ClickUIElementByAccessibilityID(AddScreenString);
        OpenScripture(BookOfMormonString, JacobString, "2", "");
        doubleTapScreen(WebElementByAccessibilityId(ChapterTwoString));
        delay(1);
        doubleTapScreen(WebElementByAccessibilityId(ChapterTwoString));
        assertToolBar();
        ClickUIElementByAccessibilityID(ScreensString);
        ClickUIElementByAccessibilityID(AddScreenString);
        assertToolBar();
        ClickUIElementByAccessibilityID(ScreensString);
        ClickUIElementByName("Jacob 1");
        doubleTapScreen(WebElementByAccessibilityId(ChapterOneString));
        delay(1);
        doubleTapScreen(WebElementByAccessibilityId(ChapterOneString));
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(AllowFullscreenString);
        assertNavBar("", false, "Settings", DoneString, true);
        ClickUIElementByAccessibilityID(DoneString);
    }

    //Show Obsolete Content
    @Test
    public void settingsScreenShowObsoleteContentSwitch() throws Exception {
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ObsoleteContentString);
        ClickUIElementByAccessibilityID(ObsoleteContentString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
    }

    @Test
    public void settingsScreenShowObsoleteContentSwitchPersists() throws Exception {
        ClickUIElementByAccessibilityID(ComeFollowMeString);
        ClickUIElementByAccessibilityID(OtherResourcesString);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ObsoleteContentString);
        ClickUIElementByAccessibilityID(ObsoleteContentString);
        assertNavBar("",false,"Settings",DoneString,true );
        ClickUIElementByAccessibilityID(DoneString);
        assertNavBar(HistoryBackString,true,"Other Resources, Come, Follow Me","",false);
        ClickUIElementByAccessibilityID(HistoryBackString);
        ClickUIElementByAccessibilityID(OtherResourcesString);
        assertElementExistsBy(WebElementsByAccessibilityId("Instructions for Curriculum 2018, Not Installed"));
        assertElementExistsBy(WebElementsByAccessibilityId("Instructions for Curriculum 2017, Not Installed"));
        delay(1);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ObsoleteContentString);
        ClickUIElementByAccessibilityID(ObsoleteContentString);
        assertNavBar("",false,"Settings",DoneString,true );
        ClickUIElementByAccessibilityID(DoneString);
        assertNavBar(HistoryBackString,true,"Other Resources, Come, Follow Me","",false);
        ClickUIElementByAccessibilityID(HistoryBackString);
        ClickUIElementByAccessibilityID(OtherResourcesString);
        delay(1);
    }


    //No current way to guarantee an inapp notification will fire, can only test setting is on the screen, and can be switched
    @Test
    public void settingsScreenAllowInAppNotificationsSwitch() throws Exception {
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(InAppNotificationsString);
        ClickUIElementByAccessibilityID(InAppNotificationsString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);

    }

    @Test
    public void settingsScreenAllowInAppNotificationsSwitchPersists() throws Exception {
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(InAppNotificationsString);
        ClickUIElementByAccessibilityID(InAppNotificationsString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(InAppNotificationsString);
        ClickUIElementByAccessibilityID(InAppNotificationsString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);


    }



    @Test
    public void FeaturedAppsScreen() throws Exception {
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(FeaturedAppsString);
        ClickUIElementByAccessibilityID(FeaturedAppsString);
        assertElementExistsBy(WebElementsByName("Bible Videos"));
        assertElementExistsBy(WebElementsByName("Book of Mormon"));
        assertElementExistsBy(WebElementsByName("Doctrinal Mastery"));
        assertElementExistsBy(WebElementsByName("Facility Issue Reporting"));
        assertElementExistsBy(WebElementsByName("FamilySearch Memories"));
        assertElementExistsBy(WebElementsByName("FamilySearch Tree"));
        assertElementExistsBy(WebElementsByName("Gospel Media"));
        assertElementExistsBy(WebElementsByName("JustServe"));
        assertElementExistsBy(WebElementsByName("LDS Youth"));
        assertElementExistsBy(WebElementsByName("Member Tools"));
        assertElementExistsBy(WebElementsByName("Mormon Channel"));
        assertElementExistsBy(WebElementsByName("Sacred Music"));
        assertElementExistsBy(WebElementsByName("Scripture Stories"));
        assertElementExistsBy(WebElementsByName("Seminary and Institute"));
        assertElementExistsBy(WebElementsByName("Sing Along Hymns"));
        assertElementExistsBy(WebElementsByName("The Tabernacle Choir"));
        assertNavBar(BackString, true, "Featured Apps", "", false);
        ClickUIElementByAccessibilityID(BackString);
        assertNavBar("", false, "Settings", DoneString, true);
        ClickUIElementByAccessibilityID(DoneString);

    }

    @Test
    public void SettingsScreenAppDetails() throws Exception{
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(AppDetailsString);
        ClickUIElementByAccessibilityID(AppDetailsString);
        assertNavBar(BackString,true,"App Details","",false);
        ClickUIElementByAccessibilityID(BackString);
        assertNavBar("", false, "Settings", DoneString, true);
        ClickUIElementByAccessibilityID(DoneString);

    }

    @Test
    public void SendFeedbackScreenSendFeedback() throws Exception {
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(SendFeedbackString);
        ClickUIElementByAccessibilityID(InsertImageString);
        ClickUIElementByTextContains("OK");
        ClickUIElementByXpath("(//*/XCUIElementTypeImage)[1]/../*");
        assertNavBar(IconArrowBackString, true, "Camera Roll", DoneString, true);
        ClickUIElementByAccessibilityID(DoneString);
        sendTextWithoutClearing("Name (Optional)", "John");
        sendTextWithoutClearing("Email", "TestEmail@test.com");
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(HowDoIString);
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(ComplimentString);
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(FeatureRequestString);
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(FunctionalityIssueString);
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(ContentIssueString);
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(SignInOrSyncingIssueString);
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(OtherString);
        sendTextWithoutClearing("Description", "This is a test Automation email, please ignore!");
        ClickUIElementByAccessibilityID(SubmitString);



    }

    @Test
    public void SendFeedbackScreenSendFeedbackWithoutName() throws Exception {
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(SendFeedbackString);
        sendTextWithoutClearing("Name (Optional)", "");
        sendTextWithoutClearing("Email", "TestEmail@test.com");
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(HowDoIString);
        sendTextWithoutClearing("Description", "This is a test Automation email, please ignore!");
        assertNavBar(CancelString, true, "Feedback", SubmitString, true);
        ClickUIElementByAccessibilityID(SubmitString);

    }

    @Test
    public void SendFeedbackScreenSendFeedbackWithphoto() throws Exception {
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(SendFeedbackString);
        sendTextWithoutClearing("Name (Optional)", "John");
        sendTextWithoutClearing("Email", "TestEmail@test.com");
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(HowDoIString);
        sendTextWithoutClearing("Description", "This is a test Automation email, please ignore!");
        assertNavBar(CancelString, true, "Feedback", SubmitString, true);
        ClickUIElementByAccessibilityID(SubmitString);

    }

    @Test
    public void SendFeedbackScreenAddPhotoThenRemove() throws Exception {
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(SendFeedbackString);
        ClickUIElementByAccessibilityID(InsertImageString);
        ClickUIElementByTextContains("OK");
        ClickUIElementByXpath("(//*/XCUIElementTypeImage)[1]/../*");
        assertNavBar(IconArrowBackString, true, "Camera Roll", DoneString, true);
        ClickUIElementByAccessibilityID(DoneString);
        ClickUIElementByAccessibilityID(RemoveImageString);
        assertNavBar(CancelString, true, "Feedback", SubmitString, false);
        ClickUIElementByAccessibilityID(CancelString);
        assertNavBar("", false, "Settings", DoneString, true);
        ClickUIElementByAccessibilityID(DoneString);


    }

    @Test
    public void SendFeedbackScreenInvalidEmail() throws Exception {
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(SendFeedbackString);
        sendTextWithoutClearing("Name (Optional)", "John");
        sendTextWithoutClearing("Email", "Test Email");
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(HowDoIString);
        assertNavBar(CancelString,true, "Feedback", SubmitString, false);
        sendText("Email", "TestEmail@test.com");
        sendTextWithoutClearing("Description", "This is a test Automation email, please ignore!");
        assertNavBar(CancelString, true, "Feedback", SubmitString, true);
        ClickUIElementByAccessibilityID(SubmitString);
    }

    @Test
    public void SendFeedbackScreenMissingRequiredFieldEmail() throws Exception {
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(SendFeedbackString);
        sendTextWithoutClearing("Name (Optional)", "John");
        sendTextWithoutClearing("Email", "");
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(HowDoIString);
        assertNavBar(CancelString,true, "Feedback", SubmitString, false);
        sendTextWithoutClearing("Email", "TestEmail@test.com");
        sendTextWithoutClearing("Description", "This is a test Automation email, please ignore!");
        assertNavBar(CancelString, true, "Feedback", SubmitString, true);
        ClickUIElementByAccessibilityID(SubmitString);
    }

    @Test
    public void SendFeedbackScreenMissingRequiredFieldCategory() throws Exception {
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(SendFeedbackString);
        sendTextWithoutClearing("Name (Optional)", "John");
        sendTextWithoutClearing("Email", "TestEmail@test.com");
        ClickUIElementByAccessibilityID(MoreInfoString);
        assertNavBar(BackString, true, "Select a Category", "", false);
        ClickUIElementByAccessibilityID(BackString);
        sendTextWithoutClearing("Description", "This is a test Automation email, please ignore!");
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(HowDoIString);
        assertNavBar(CancelString, true, "Feedback", SubmitString, true);
        ClickUIElementByAccessibilityID(SubmitString);


    }

    @Test
    public void SendFeedbackScreenMissingRequiredFieldDescription() throws Exception {
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(SendFeedbackString);
        sendTextWithoutClearing("Name (Optional)", "John");
        sendTextWithoutClearing("Email", "TestEmail@test.com");
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(HowDoIString);
        sendTextWithoutClearing("Description", "");
        assertNavBar(CancelString,true, "Feedback", SubmitString, false);
        sendTextWithoutClearing("Description", "This is a test Automation email, please ignore!");
        assertNavBar(CancelString, true, "Feedback", SubmitString, true);
        ClickUIElementByAccessibilityID(SubmitString);



    }

    @Test
    public void SendFeedbackScreenAutofillFields() throws Exception {
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        ClickUIElementByAccessibilityID(SendFeedbackString);
        sendTextWithoutClearing("Name (Optional)", "John");
        sendTextWithoutClearing("Email", "TestEmail@test.com");
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(HowDoIString);
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(ComplimentString);
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(FeatureRequestString);
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(FunctionalityIssueString);
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(ContentIssueString);
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(SignInOrSyncingIssueString);
        ClickUIElementByAccessibilityID(MoreInfoString);
        ClickUIElementByName(OtherString);
        sendTextWithoutClearing("Description", "This is a test Automation email, please ignore!");
        ClickUIElementByAccessibilityID(SubmitString);


    }

    @Test
    public void AboutScreen() throws Exception {
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(AboutString);
        ClickUIElementByAccessibilityID(AboutString);
        assertNavBar(BackString,true,"About", "",false);
        ClickUIElementByAccessibilityID(BackString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);


    }

    @Test
    public void ViewSourceSettings() throws Exception{
        assertToolBar();
        ClickUIElementByAccessibilityID(SettingsString);
        scrollToById(ViewSourceString);
        ClickUIElementByAccessibilityID(ViewSourceString);
        assertNavBar("", false, "Settings", "Done", true);
        ClickUIElementByAccessibilityID(DoneString);

    }


    //********** Content Interaction **********

    @Test
    public void AnnotationMenuTapMark() throws Exception{
        OpenScripture(BookOfMormonString,JacobString,"1","1");
        openAnnotationMenu(WebElementByAccessibilityId("1"));
        assertAnnotationMenu(true,true);
        ClickUIElementByAccessibilityID(MarkString);

        Set handles = driver.getContextHandles();
        List theHandles = new ArrayList(handles);
//    log(String.valueOf(theHandles));
//        for (int i = 1; i < theHandles.size(); i++) {
//            driver.context((String) theHandles.get(i));
//            log("Window handle is now: " + (String)theHandles.get(i));
//        }

        assertAnnotationMenu(false,true);
        ClickUIElementByAccessibilityID(RemoveString);

    }

    @Test
    public void AnnotationMenuTapMarkAndStyle() throws Exception{
        OpenScripture(BookOfMormonString,JacobString,"1","1");
        openAnnotationMenu(WebElementByAccessibilityId("1"));
        assertAnnotationMenu(true,true);
        ClickUIElementByAccessibilityID(MarkString);

        assertAnnotationMenu(false,true);
        ClickUIElementByAccessibilityID(StyleString);
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleClear() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleRedUnderline() throws Exception{
        annotationStyleTest("Red","Solid","Underline");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleOrangeUnderline() throws Exception{
        annotationStyleTest("Orange","Solid","Underline");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleYellowUnderline() throws Exception{
        annotationStyleTest("Yellow","Solid","Underline");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleGreenUnderlined() throws Exception{
        annotationStyleTest("Green","Solid","Underline");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleBlueUnderline() throws Exception{
        annotationStyleTest("Blue","Solid","Underline");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleDarkBlueUnderline() throws Exception{
        annotationStyleTest("Dark Blue","Solid","Underline");
    }

    @Test
    public void AnnotationMenuTapMarkAndStylePurpleUnderline() throws Exception{
        annotationStyleTest("Purple","Solid","Underline");
    }

    @Test
    public void AnnotationMenuTapMarkAndStylePinkUnderline() throws Exception{
        annotationStyleTest("Pink","Solid","Underline");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleBrownUnderline() throws Exception{
        annotationStyleTest("Brown","Solid","Underline");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleGrayUnderline() throws Exception{
        annotationStyleTest("Gray","Solid","Underline");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleRedSolid() throws Exception{
        annotationStyleTest("Red","Solid","Solid");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleOrangeSolid() throws Exception{
        annotationStyleTest("Orange","Solid","Solid");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleYellowSolid() throws Exception{
        annotationStyleTest("Yellow","Solid","Solid");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleGreenSolid() throws Exception{
        annotationStyleTest("Green","Solid","Solid");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleBlueSolid() throws Exception{
        annotationStyleTest("Blue","Solid","Solid");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleDarkBlueSolid() throws Exception{
        annotationStyleTest("Dark Blue","Solid","Solid");
    }

    @Test
    public void AnnotationMenuTapMarkAndStylePurpleSolid() throws Exception{
        annotationStyleTest("Purple","Solid","Solid");
    }

    @Test
    public void AnnotationMenuTapMarkAndStylePinkSolid() throws Exception{
        annotationStyleTest("Pink","Solid","Solid");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleBrownSolid() throws Exception{
        annotationStyleTest("Brown","Solid","Solid");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleGraySolid() throws Exception{
        annotationStyleTest("Gray","Solid","Solid");
    }

    @Test
    public void AnnotationMenuTapNote() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuCreateNoteAnnotationIndicatorIcon() throws Exception{
        fail("Test Not Written");

    }

    @Test
    public void AnnotationMenuCreateNote() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuCreateNoteWithLink() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuCreateNoteWithTag() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuCreateNoteAddtoNotebook() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapTag() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuCreateTag() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuCreateTagAnnotationIndicatorIcon() throws Exception{
            fail("Test Not Written");

    }

    @Test
    public void AnnotationMenuOpenTagInSidebarGoToTag() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapAddTo() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuAddToNotebook() throws Exception{
        fail("Test Not Written");

    }

    @Test
    public void AnnotationMenuAddToAnnotationIndicatorIcon() throws Exception{
        fail("Test Not Written");

    }

    @Test
    public void AnnotationMenuTapLink() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuCreateLinkToSingleChapterBook() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuCreateLinkAnnotationIndicatorIcon() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapCopy() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapShare() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapSearch() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapDefine() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapRemove() throws Exception{
        fail("Test Not Written");
    }
    @Test
    public void AnnotationMenuTapCreateRemove() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationChooseBetweenOverlappingHighlights() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationChooseBetweenOverlappingHighlightsOfDifferentTypeTagAndMark() throws Exception{
        fail("Test Not Written");

    }

    @Test
    public void AnnotationChooseBetweenOverlappingHighlightsOfDifferentTypeLinkAndMark() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationChooseBetweenOverlappingHighlightsOfDifferentTypeNoteAndMark() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationDeleteHighlightWithNote() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void AnnotationCancelDeleteHighlightWithNote() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationDeleteHighlightWithLink() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationCancelDeleteHighlightWithLink() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationDeleteHighlightWithTag() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationCancelDeleteHighlightWithTag() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationDeleteHighlightNoteWithLink() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationCancelDeleteHighlightNoteWithLink() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationAddSelectionToNewNotebook() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuRecentHighlightStyles() throws Exception {
        fail("Test Not Written");
    }


    //********** General Conference Section **********

    @Test
    public void generalConferenceVerifyAll() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void generalConferenceDownloadAllFromMoreOptionsMenu() throws Exception{
        fail("Test Not Written");

    }


    @Test
    public void generalConferenceRemoveAllFromMoreOptionsMenu() throws Exception{
        fail("Test Not Written");
    }


    @Test
    public void generalConferenceDownloadAllFromLibraryContextMenu() throws Exception{
        fail("Test Not Written");

    }


    @Test
    public void generalConferenceRemoveAllFromLibraryContextMenu() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void generalConferenceDownloadLatestConferenceViaContextMenu() throws Exception {
        fail("Test Not Written");

    }

    @Test
        public void generalConferenceListenToAudioSessionAutoProgress() throws Exception {
        fail("Test Not Written");

    }



    //********** Search Section **********

    @Test
    public void ElementsOnSearchScreen() throws Exception {
        fail("Test Not Written");
    }

}



