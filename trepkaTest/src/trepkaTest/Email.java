package trepkaTest;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Email {

	public static void main(String args[]) throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver", "C://seleniumdrivers//chromedriver.exe");
		
		WebDriver driver = new ChromeDriver ();
		driver.get("https://www.gmail.com");
		driver.findElement(By.xpath("//input[@id='Email']")).sendKeys("wwsisSeleniumtest@gmail.com");
		driver.findElement(By.xpath("//input[@id='next']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id='Passwd']")).sendKeys("haslodotestu");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id='signIn']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[@class = 'T-I J-J5-Ji T-I-KE L3']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//textarea[@id=':9i']")).sendKeys("grzegorzklimek77@gmail.com");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//input[@id=':92']")).sendKeys("temat");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@id=':a5']")).sendKeys("treść");
		Thread.sleep(2000);
		driver.findElement(By.xpath("//div[@id=':8s']")).click();
		Thread.sleep(2000);

	}

}
