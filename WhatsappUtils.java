package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class WhatsappUtils {
	static String chromeDriverPath = "./driver/chromedriver.exe";
	static WebDriver driver;
	
	
	public static void Authenticate() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
		driver  = new ChromeDriver();
		driver.get("https://web.whatsapp.com/");
		driver.manage().window().maximize();
		Thread.sleep(12000);
	}
	

	
	public static void sendMessage(String name, String message) throws InterruptedException {
		Thread.sleep(1500);
		driver.findElement(By.xpath("//*[@id=\"side\"]/div[1]/div/label/div/div[2]")).click();
		WebElement searchInput = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[3]/div/div[1]/div/label/div/div[2]"));
		System.out.println(searchInput.getTagName());
		System.out.println(searchInput.getText());
		searchInput.sendKeys(name);
		Thread.sleep(1500);
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div[3]/div/div[2]/div[1]/div/div/div[1]")).click();
		Thread.sleep(1500);
		WebElement messageInput = driver.findElement(By.xpath("/html/body/div[1]/div/div/div[4]/div/footer/div[1]/div[2]/div/div[2]"));
		messageInput.sendKeys(message);
		Thread.sleep(2000);
		driver.findElement(By.xpath("/html/body/div[1]/div/div/div[4]/div/footer/div[1]/div[3]")).click();
	}
}
