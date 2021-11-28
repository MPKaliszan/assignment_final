package coreKara_StepDefinitions;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitions extends GenericFunctions {
	
	/*
	 * Notes: 
	 * 
	 * genericFunctions.convertStringToUtf() is used here to convert strings that contain Japanese characters into UTF-8 strings. 
	 * During testing there was a formatting problem, scrambling the text and making it unreadable. This function solves that.
	 * 
	 * 
	 */
	
	private WebDriver driver = new ChromeDriver();
	private GenericFunctions genericFunctions = new GenericFunctions();
	
    private String loginPageUrl;
	private String convertedErrorMessagePassword;
	private String convertedErrorMessageEmail;
	private String convertedElementMessage;
	private WebElement element;
	private String linkedPageUrl;

    @Before
	public void beforeLaunch() {
		driver.manage().window().maximize(); //Open a browser and maximize it
		driver.get("https://hotel.testplanisphere.dev/ja/login.html"); //go to the page that is to be tested
		loginPageUrl = driver.getCurrentUrl().toString(); //Save page as a variable for use in methods on this page
		
    }
    
	@After
	public void afterTest() {
		driver.close();
		//There should be code here to clean the database, removing the user that was created for the test case
		//This wasn't included because I do not know how the database is set up
	}
			
    
    @Given("user has an account for Hotel Planisphere with the e-mail {string}")
	public void creatUserDatabase(String email) {
		//step to create a user for the test case in the database along with a valid password
	}
    

	@When("user enters {string} as their e-mail address") 
	public void enterAsEmail(String email) {
		driver.findElement(By.xpath("/html/body/div/div[2]/div/form/div[1]/input")).sendKeys(email);
	}
	
	@When("user clicks the {string} link")
	public void clickLinkButton(String link) throws InterruptedException {
		link = genericFunctions.convertStringToUtf(link);
		driver.findElement(By.linkText(link)).click();
		Thread.sleep(500);
		linkedPageUrl=driver.getCurrentUrl().toString();
			
	}
	
	@And("user enters {string} as their password")
		public void enterPassword(String password) {
		driver.findElement(By.xpath("/html/body/div/div[2]/div/form/div[2]/input")).sendKeys(password);
	}
	
	@And("an error message appears beneath the password field reading {string}")
	public void checkErrorMessagePassword(String errorMessagePassword) {
		
		element = driver.findElement(By.xpath("/html/body/div/div[2]/div/form/div[2]/div"));
	
		convertedErrorMessagePassword = genericFunctions.convertStringToUtf(errorMessagePassword);
		convertedElementMessage = genericFunctions.convertStringToUtf(element.getText());
	
		assertEquals(convertedErrorMessagePassword, convertedElementMessage);	
	}
	
	@And("the user clicks the login button")
	public void clickLoginButton() {
		driver.findElement(By.xpath("/html/body/div/div[2]/div/form/button")).click();
	}
	
	@Then("the user is taken to the next page")
	//This method asserts that the user has changed page. As I do not know currently the URL of the succesfull login page, I opted to make an assertion that 
	//looks whether the page URL has changed. TODO: Find out successful login account to use, then revamp this test with proper URL assertion
		public void userLoginSuccessful() throws InterruptedException {
		assertNotEquals(driver.getCurrentUrl(), loginPageUrl);
		Thread.sleep(5000);
	}
	
	@Then("an error message appears beneath the e-mail field reading {string}")
	public void checkErrorMessageEmail(String errorMessageEmail) {
		element = driver.findElement(By.xpath("/html/body/div/div[2]/div/form/div[1]/div"));	
		
		convertedErrorMessageEmail = genericFunctions.convertStringToUtf(errorMessageEmail);
		convertedElementMessage = genericFunctions.convertStringToUtf(element.getText());
		
		assertEquals(convertedErrorMessageEmail, convertedElementMessage);	
	}
		
	@Then("user is taken to the {string} page")
	public void userIsOnNewPage(String linkedPage) {
		
		switch(linkedPage) {
		case "GitHub":
			linkedPage="https://github.com/testplanisphere/hotel-example-site";
			break;
		case "home":
			linkedPage="https://hotel.testplanisphere.dev/ja/index.html";
			break;
		case "plans":
			linkedPage="https://hotel.testplanisphere.dev/ja/plans.html";
			break;
		case "signup":
			linkedPage="https://hotel.testplanisphere.dev/ja/signup.html";
			break;
		case "login":
			linkedPage=loginPageUrl;
			break;
		}
		
		assertEquals(linkedPage, linkedPageUrl);
	}
	/*
	 * ============================================================================
	 * Test steps to be implemented in the future beneath this line
	 * ============================================================================
	 */
	@Given("Backend server is {string}")
	public void serverStatusIs(String serverStatus) {
		//TODO: Implement switch with server statuses and fitting mock configuration
	}
	
	@Then("user is taken to a {string} error page")
	public void takeUsertoErrorPage(int errorNumber) {
		//TODO: implement switch with assertion to ensure that the system routs to the correct error page
	}
}
