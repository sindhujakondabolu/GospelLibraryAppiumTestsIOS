package UI;

import com.google.common.base.Joiner;
import io.appium.java_client.TouchAction;
import javafx.util.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.ios.IOSDriver;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static UI.Content.setBooks;
import static UI.Strings.*;
import static java.lang.Integer.parseInt;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;



public class GospelLibrary {
    IOSDriver driver;

    
    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName","iOS");
        capabilities.setCapability("platformVersion","12.2");
        capabilities.setCapability("deviceName", "iPhone X");
        capabilities.setCapability("automationname","XUITest");
        capabilities.setCapability("app", System.getProperty("user.dir") + "/../../App/GospelLibrary.app");
        capabilities.setCapability("deviceOrientation", "portrait");
        capabilities.setCapability("waitForQuiescence",false);
        capabilities.setCapability("wdaEventloopIdleDelay",2);
        capabilities.setCapability("useNewWDA",false);
        capabilities.setCapability("noReset",true);
//        capabilities.setCapability("noReset",false);
        driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        setBooks();

    }

    @After
    public void tearDown() throws Exception {
//        ClickUIElementByAccessibilityID("Settings");
//        if (WebElementsByAccessibilityId("Account options").size() > 0){
//            ClickUIElementByAccessibilityID("Account options");
//            ClickUIElementByAccessibilityID("Sign Out");
//        }
        driver.quit();

    }



    //********************************************** Functions **********************************************
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
        Thread.sleep(milliseconds_1 * delayTime);
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
        Thread.sleep(milliseconds_1);
    }

    //Click Element by Text Contains
    public void ClickUIElementByTextContains(String text) throws Exception {
        WebElementByTextContains(text).click();
        System.out.println("Clicking: '" + text + "' using text by xPath with TextView");
        Thread.sleep(milliseconds_1);
    }

    //Click Element by 2 text items
    public void ClickUIElementBy2TextStings(String book, String number) throws Exception {
        String xPathofText = "//*[contains(@text, '" + book + "')][contains(@text, '" + number + "')]";
        //System.out.println("Xpath of current item is: "+xPathofText+"");
        WebElementByXpath(xPathofText).click();
        System.out.println("Clicking: '" + book + " " + number + "' using 2 text contains by xPath");
        Thread.sleep(milliseconds_1);
    }

    //Click Element by Accessibility ID
    public void ClickUIElementByAccessibilityID(String elementAccessibilityID) throws Exception {
        WebElementByAccessibilityId(elementAccessibilityID).click();
        System.out.println("Clicking: '" + elementAccessibilityID + "' by Accessibility ID");
        Thread.sleep(milliseconds_1);
    }

    //Click Element by ID
    public void ClickUIElementByID(String elementID) throws Exception {
        WebElementById(elementID).click();
        System.out.println("Clicking: '" + elementID + "' by ID");
        Thread.sleep(milliseconds_1);
    }

    //Click Element by Xpath
    public void ClickUIElementByXpath(String xpath) throws Exception {
        WebElementByXpath(xpath).click();
        System.out.println("Clicking: '" + xpath + "' by xPath");
        Thread.sleep(milliseconds_1);
    }

    //Tap in the center of the screen
    public void TapCenterScreen() throws Exception {
        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();
        TouchAction action = new TouchAction(driver);
        action.tap(screenWidth / 2, screenHeight / 2).perform();
        System.out.println("Tapping center of screen");
    }


    public void TapAndDrag(WebElement startPoint,WebElement endPoint) throws Exception{
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

    //Enter Text to a field by ID
    //click field, clear field, enter text
    public void sendText(String elementID, String text) throws Exception {
        WebElement textfield = driver.findElementById(elementID);
        textfield.clear();
        textfield.sendKeys(text);
        System.out.println("Sending Text: '" + text + "'");
        Thread.sleep(milliseconds_1);
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
        Thread.sleep(milliseconds_1);
    }

    //Swipe previous chapter
    public void swipePreviousChapter() throws Exception {
        int screenHeight = driver.manage().window().getSize().getHeight();
        int screenWidth = driver.manage().window().getSize().getWidth();
        driver.swipe(screenWidth / 20 * 3, screenHeight / 2, screenWidth / 20 * 19, screenHeight / 2, 300);
        System.out.println("Swiping left...");
        Thread.sleep(milliseconds_1);
    }

    //Swipe to delete
    public void swipeToDelete(WebElement theWebElement, boolean delete) throws Exception {

        int elementY = theWebElement.getLocation().y;
        int elementHeight = theWebElement.getSize().height;
        int swipeY = (elementY-(elementHeight/2));
        int screenWidth = driver.manage().window().getSize().getWidth();
        if (delete) {
            driver.swipe(screenWidth / 20 * 18, swipeY, screenWidth / 20 * 2, swipeY, 300);
            log("Swiping to delete...");
        } else {
            driver.swipe(screenWidth / 20 * 18, swipeY, screenWidth / 20 * 6, swipeY, 300);
            assertElementExistsBy(WebElementsByText(DeleteString));
            log("Swiping");
        }
        Thread.sleep(milliseconds_1);
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
        Thread.sleep(1000);
    }


    //

    public void ClickSeekBarAt(WebElement webelement, int positionOf7) throws Exception {
        int upperY = webelement.getLocation().getY();
        int upperX = webelement.getLocation().getX();
        int seekbarWidth = webelement.getSize().getWidth();
        int seekbarHeight = webelement.getSize().getHeight();
        int TapTarget = (upperY + (seekbarHeight / 2));
        //Seekbar positions
        int setting1 = (upperX + (seekbarWidth / 20));
        int setting2 = (int) (upperX + (seekbarWidth / 7) * 1.16);
        int setting3 = (int) (upperX + (seekbarWidth / 7) * 2.33);
        int setting4 = (upperX + (seekbarWidth / 2));
        int setting5 = (int) (upperX + ((seekbarWidth / 7) * 4.66));
        int setting6 = (int) (upperX + ((seekbarWidth / 7) * 5.84));
        int setting7 = (upperX + ((seekbarWidth / 20) * 19));


        if (positionOf7 == 1) {
            //12px
            driver.tap(1, setting1, TapTarget, 1);
            Thread.sleep(milliseconds_1);
        } else if (positionOf7 == 2) {
            //18px
            driver.tap(1, setting2, TapTarget, 1);
            Thread.sleep(milliseconds_1);
        } else if (positionOf7 == 3) {
            //21px
            driver.tap(1, setting3, TapTarget, 1);
            Thread.sleep(milliseconds_1);
        } else if (positionOf7 == 4) {
            driver.tap(1, setting4, TapTarget, 1);
            Thread.sleep(milliseconds_1);
        } else if (positionOf7 == 5) {
            driver.tap(1, setting5, TapTarget, 1);
            Thread.sleep(milliseconds_1);
        } else if (positionOf7 == 6) {
            driver.tap(1, setting6, TapTarget, 1);
            Thread.sleep(milliseconds_1);
        } else if (positionOf7 == 7) {
            driver.tap(1, setting7, TapTarget, 1);
            Thread.sleep(milliseconds_1);
        } else {
            driver.tap(1, setting3, TapTarget, 1);
            Thread.sleep(milliseconds_1);
        }
    }

    public void OpenScripture(String work, String book, String chapter, String verse) throws Exception {
        assertElementExistsBy(WebElementsByText("Scriptures"));
        ClickUIElementByID("Scriptures");
        Thread.sleep(milliseconds_1);
        ClickUIElementByID(work +", Installed");
        Thread.sleep(milliseconds_1);
        if (book != "") {
            scrollToById(book);
            ClickUIElementByID(book);
            Thread.sleep(milliseconds_2);
            if (chapter != "") {
                scrollToById(chapter);
                ClickUIElementByID(chapter);
                Thread.sleep(milliseconds_1);
            }
            if (verse != "") {
                Thread.sleep(milliseconds_1);
                System.out.println("Scrolling to " + verse);
                scrollToById(verse);
            }
        }

    }

    public void OpenConference(String month, String year, String talkTitle) throws Exception {
        assertElementExistsBy(WebElementsByText("General Conference"));
        ClickUIElementByText("General Conference");
        Thread.sleep(milliseconds_1);

        if (month != "") {
            scrollToById(month + " " + year);
            assertElementExistsBy(WebElementsByText(month + " " + year));
            ClickUIElementByText(month + " " + year);
            Thread.sleep(milliseconds_1);
            ClickUIElementByText(month + " " + year);
            if (talkTitle != "") {
                scrollToById(talkTitle);
                ClickUIElementByText(talkTitle);
            }
        }
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


    //*************************************************************** Assert Page Functions ***************************************************************

    public void assertNavBar(String topLeftButton, Boolean LeftIsEnabled, String title, String topRightButton, Boolean RightIsEnabled) throws Exception {
        if (topLeftButton != "") {
            assertElementExistsBy(WebElementsByAccessibilityId(topLeftButton));
            assert (Boolean.parseBoolean(WebElementByAccessibilityId(topLeftButton).getAttribute("enabled")) == LeftIsEnabled);
        }
        assertElementExistsBy(WebElementsByText(title));
        if (topRightButton != "") {
            assertElementExistsBy(WebElementsByAccessibilityId(topRightButton));
            assert (Boolean.parseBoolean(WebElementByAccessibilityId(topRightButton).getAttribute("enabled")) == RightIsEnabled);
        }
    }

    public void assertToolBar() throws Exception{
        assertElementExistsBy(WebElementsByAccessibilityId("Settings"));
        assertElementExistsBy(WebElementsByAccessibilityId("Search"));
        assertElementExistsBy(WebElementsByAccessibilityId("Screens"));
        assertElementExistsBy(WebElementsByAccessibilityId("Bookmarks"));
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

    public void assertSettingsScreen(Boolean isFirstLaunch) throws Exception{
        assertNavBar("",false,"Settings","Done",true);
        if (isFirstLaunch) {
            assertSettingsitems(UI.Content.SettingsItems);
        } else {
            fail("No asserts for settings screen not on first launch");
        }
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



    //*************************************************************** Nav Functions ***************************************************************

    //Scroll to by id
    public void scrollToById(String id) throws Exception {
        System.out.println("Scrolling to: " + id);
        WebElement idIsPresent = WebElementById(id);
        int screenHeight = driver.manage().window().getSize().getHeight();
        int upperY = idIsPresent.getLocation().getY();
        System.out.println("Screen Height is " + screenHeight + "");
        System.out.println("upper Y is " + upperY + "");
        while (upperY >= screenHeight / 2) {
            System.out.println("scrolling down y '" + upperY + "' is >= " + screenHeight / 2 + "");
            scrollDown();
            upperY = idIsPresent.getLocation().getY();
        }
        while (upperY <= screenHeight / 10) {
            System.out.println("scrolling up y '" + upperY + "' is <= " + screenHeight / 8 + "");
            scrollUp();
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
        fail("Test Not Written");
    }

    @Test
    public void invalidLogin() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void troubleSigningIn() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void signInCreateAccount() throws Exception {
        fail("Test Not Written");
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
        fail("Test Not Written");

    }

    @Test
    public void CreateNewNotebook() throws Exception {

        fail("Test Not Written");

    }

    @Test
    public void DeleteANotebook() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void CancelDeleteANotebook() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void CreateNewNoteInNewNotebook() throws Exception {
        fail("Test Not Written");
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
    public void NotesSectionMergeTwoNotebooks() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void NotesSectionCancelMergeTwoNotebooks() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void NotesSectionSortNotebooksByCount() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void NotesSectionSortNotebooksByRecent() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void NotesSectionSortNotebooksByName() throws Exception{
        fail("Test Not Written");
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
        fail("Test Not Written");
    }

    @Test
    public void BookmarksLandingPageFromContent_NotSignedIn() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void BookmarksCreateBookmarkFromScripture() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void BookmarksCreateBookmarkFromScriptureCancel() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void BookmarksCreateBookmarkFromScriptureDefault() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void BookmarksDefaultBookmarkTitleUpdatesItself() throws Exception{
        fail("Test Not Written");

    }

    @Test
    public void BookmarkMoveNamedBookmarkToNewChapter() throws Exception{
        fail("Test Not Written");

    }

    @Test
    public void BookmarksRenameABookmark() throws Exception{
        fail("Test Not Written");
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
    public void BookmarksDeleteBookmark() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void BookmarksCancelDeleteBookmark() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void BookmarksHideAddAndUpdateButtonsWhenNotOnChapter() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void BookmarksReorderBookmarks() throws Exception{
        fail("Test Not Written");

    }


    @Test
    public void BookmarksLandingPageFromNotes_NotSignedIn() throws Exception {
        fail("Test Not Written");

    }

    @Test
    public void ScreensScreenFromNotesScreenItemOptions() throws Exception {
        fail("Test Not Written");

    }

    @Test
    public void ScreensScreenFromLibrary() throws Exception {
        fail("Test Not Written");

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
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenDownloadedMediaGeneralConferenceDownloaded_AccessedThroughMoreOptionsMenu() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenDownloadedMediaGeneralConferenceDownloaded_CancelAllWithOneDownload() throws Exception {
        fail("Test Not Written");
    }

//    @Test
//    public void settingsScreenDownloadedMediaOTDownloaded_CancelAll_MultipleDownloads() throws Exception {
//       fail("Test Not Written");
//
//    }

    @Test
    public void settingsScreenLimitMobileNetworkUseSwitch() throws Exception {
        fail("Test Not Written");
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
        fail("Test Not Written");
    }


    @Test
    public void settingsScreenThemeAllThemesTextColorAndFootnoteColor() throws Exception {
        fail("Test Not Written");
    }


    @Test
    public void settingsScreenTextSize() throws Exception {
        fail("Test Not Written");
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
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenListModeSwitchPersists() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenListModeLibraryScreenScripturesWidth() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenListModeLibraryContentScreenCategories() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenListModeScripturesContentScreenCategories() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenListModeScripturesScripturesScreenCategories() throws Exception {
        fail("Test Not Written");
    }


    @Test
    public void settingsScreenHideFootnotesSwitch() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenHideFootnotesSwitchPersists() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenHideFootnotesInContent() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenHideFootnotesInContentTextColor() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenHideFootnotesInContentTextColorThenShow() throws Exception {
        fail("Test Not Written");
    }


    @Test
    public void settingsScreenHideFootnotesInContentThenShow() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenHideFootnotesInContentDCJumpLinks() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenHideFootnotesInContentTextColorDCJumpLinks() throws Exception {
        fail("Test Not Written");
    }


    @Test
    public void settingsScreenShowScreensAsSeparateWindowsSwitch() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenShowScreensAsSeparateWindowsSwitchPersists() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenShowScreensAsSeparateWindowsSwitchCheckTwoScreensOptionOnToOffFromMainScreen() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenShowScreensAsSeparateWindowsSwitchCheckTwoScreensOptionOnToOffFromSecondaryScreen() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenShowScreensAsSeparateWindowsSwitchCheckOffToOnAddScreen() throws Exception {
        fail("Test Not Written");

    }

    @Test
    public void settingsScreenShowScreensAsSeparateWindowsSwitchCheckOffAddScreen() throws Exception {
        fail("Test Not Written");
    }

    //Show Obsolete Content
    @Test
    public void settingsScreenShowObsoleteContentSwitch() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenShowObsoleteContentSwitchPersists() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void settingsScreenShowObsoleteContent() throws Exception {
        fail("Test Not Written");

    }

    //No current way to guarantee an inapp notification will fire, can only test setting is on the screen, and can be switched
    @Test
    public void settingsScreenAllowInAppNotificationsSwitch() throws Exception {
        fail("Test Not Written");

    }

    @Test
    public void settingsScreenAllowInAppNotificationsSwitchPersists() throws Exception {
        fail("Test Not Written");

    }

    @Test
    public void FeaturedAppsScreen() throws Exception {
        fail("Test Not Written");

    }

    @Test
    public void SendFeedbackScreenSendFeedback() throws Exception {
        fail("Test Not Written");

    }

    @Test
    public void SendFeedbackScreenSendFeedbackWithoutName() throws Exception {
        fail("Test Not Written");

    }

    @Test
    public void SendFeedbackScreenSendFeedbackWithphoto() throws Exception {
        fail("Test Not Written");

    }

    @Test
    public void SendFeedbackScreenAddPhotoThenRemove() throws Exception {
        fail("Test Not Written");

    }

    @Test
    public void SendFeedbackScreenInvalidEmail() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void SendFeedbackScreenMissingRequiredFieldEmail() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void SendFeedbackScreenMissingRequiredFieldCategory() throws Exception {
        fail("Test Not Written");

    }

    @Test
    public void SendFeedbackScreenMissingRequiredFieldDescription() throws Exception {
        fail("Test Not Written");
    }

    @Test
    public void SendFeedbackScreenAutofillFields() throws Exception {
        fail("Test Not Written");

    }

    @Test
    public void AboutScreen() throws Exception {
        fail("Test Not Written");

    }

    @Test
    public void turnOnDeveloperSettings() throws Exception{
        fail("Test Not Written");
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
    log(String.valueOf(theHandles));
        for (int i = 1; i < theHandles.size(); i++) {
            driver.context((String) theHandles.get(i));
            log("Window handle is now: " + (String)theHandles.get(i));
            List theWebelements = WebElementsByXpath("//*");
            log(Joiner.on("").join(theWebelements));
        }

    assertAnnotationMenu(false,true);
    ClickUIElementByAccessibilityID(RemoveString);

    }

    @Test
    public void AnnotationMenuTapMarkAndStyle() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleClear() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleRedUnderline() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleOrangeUnderline() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleYellowUnderline() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleGreenUnderlined() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleBlueUnderline() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleDarkBlueUnderline() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStylePurpleUnderline() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStylePinkUnderline() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleBrownUnderline() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleGrayUnderline() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleRedSolid() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleOrangeSolid() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleYellowSolid() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleGreenSolid() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleBlueSolid() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleDarkBlueSolid() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStylePurpleSolid() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStylePinkSolid() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleBrownSolid() throws Exception{
        fail("Test Not Written");
    }

    @Test
    public void AnnotationMenuTapMarkAndStyleGraySolid() throws Exception{
        fail("Test Not Written");
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



