package allegro;

import java.util.concurrent.TimeUnit;

import javax.xml.xpath.XPath;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class allegro {

	public static void main(String args[]){
		System.setProperty("webdriver.chrome.driver", "C://seleniumdrivers//chromedriver.exe");
		WebDriver driver = new ChromeDriver();	
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.get("https://www.allegro.pl");
			
		driver.findElement(By.xpath("//input[@id='main-search-text']")).sendKeys("Samsung galaxy s8");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		
		driver.findElement(By.xpath("(//a[contains(text(),'SMARTFON SAMSUNG GALAXY S8')])[1]")).click();
		
		driver.findElement(By.xpath("//a[@id='add-to-cart']")).click();
		
		driver.findElement(By.xpath("//img[@class='m-brands-allegro']")).click();
		
		
		
		
	
		
		driver.close();
		
		System.out.println("Test pass");
		
	}
}