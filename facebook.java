package newpackage;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class facebook {

	public static void main(String args[]) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "C://Users//Iksob//Desktop//chromedriver.exe");
		WebDriver driver = new ChromeDriver();	
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		driver.get("https://www.facebook.com");
		
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("testinio987@gmail.com");
		driver.findElement(By.xpath("//input[@name='pass']")).sendKeys("testinio789");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//input[@id='u_0_s']")).click();
		driver.findElement(By.xpath("//textarea[@class='_3en1 _480e']")).sendKeys("test");
		driver.findElement(By.xpath("//button[@class='_1mf7 _4jy0 _4jy3 _4jy1 _51sy selected _42ft']")).click();
		
		//driver.close();
		
		System.out.println("Test pass");
		
	}
}