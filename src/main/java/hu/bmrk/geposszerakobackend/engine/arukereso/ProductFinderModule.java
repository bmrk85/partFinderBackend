package hu.bmrk.geposszerakobackend.engine.arukereso;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductFinderModule {

    public String search(String productFullName, String productSearchName) {
        System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/geckodriver");
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        WebDriver driver = new FirefoxDriver(options);
        driver.get("https://something something webshop.com");
        WebElement element = driver.findElement(By.name("st"));
        element.sendKeys(productSearchName + " \n");
        try{
            element.submit();
        }catch (StaleElementReferenceException e){
            System.err.println("nem sikerült");
            driver.close();
        }

        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("product-list")));

        List<WebElement> my_list = driver.findElements(By.className("product-box"));
        my_list.stream().filter(node -> node.findElement(By.className("name")).getText().equalsIgnoreCase(productFullName))
                .findFirst().ifPresentOrElse(
                webElement -> webElement.findElement(By.className("image-link-container")).click(),
                () -> System.err.println("nincs ilyen"));

        String url = driver.getCurrentUrl();
        driver.close();
        return url;
    }


}
