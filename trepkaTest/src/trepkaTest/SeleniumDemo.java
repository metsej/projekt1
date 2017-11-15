package trepkaTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumDemo {

	public static void main(String args[]) throws InterruptedException {

		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

		driver.get("https://www.allegro.pl");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("(//a[span[text()='zaloguj']])[2]")).click();
		driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Frezer_02");
		// tuaj wpisaæ login
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("urukhai22");// tutaj
		// wpisaæ
		// hasġo
		driver.findElement(By.xpath("//button[@id='login-button']")).click();
		
		new WebDriverWait(driver, 10).until(
				ExpectedConditions.urlToBe("https://allegro.pl/")
		);
		
		new WebDriverWait(driver, 10).until(
				ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='submit']"))
		);
		
		driver.findElement(By.xpath("//input[@id='main-search-text']")).sendKeys("Iphone 7");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		
		new WebDriverWait(driver, 10).until(
				ExpectedConditions.urlContains("allegro.pl/listing")
		);
		
		driver.findElement(By.xpath("(//a[contains(text(),'iPhone 7')])[1]")).click();
		driver.findElement(By.xpath("(//a[span[text()='wyloguj']])[2]")).click();

		driver.close();

		System.out.println("Test pass");

	}
}
