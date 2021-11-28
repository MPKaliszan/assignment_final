Feature: Hotel Planisphere login page
  This feature file contains test cases related to the Hotel Planisphere login page. Users can use this page to log into their accounts. 
  further information regarding the to-be-tested page along with a list of general preconditions go here
	
	Test cases regarding e-mail addresses and their validity based on, and taken from, Standards document RFC 3696.
	TODO: Inquire about precise policy regarding account creation and which emails are considered valid. 
	As it currently stands, assumptions were made based on above Standards document regarding valid e-mail addresses standards.

	@happy_path
  Scenario: User succesfully logs into their account
  	Given user has an account for Hotel Planisphere with the e-mail "validemail@gmail.com"
    When user enters "validemail@gmail.com" as their e-mail address
    And user enters "password" as their password
  	And the user clicks the login button
    Then the user is taken to the next page
    
  @happy_path
  Scenario Outline: User succesfully logs into their account using non-standard yet valid email address
  	Given user has an account for Hotel Planisphere with the e-mail "<valid>"
  	When user enters "<valid>" as their e-mail address
  	And user enters "password" as their password
  	And the user clicks the login button
  	Then the user is taken to the next page
  	
  	Examples:
  	|valid								  |
  	|avalidemail@gmail.com	|
		|a_valid_email@gmail.com|
		|valid_email61@gmail.com|
		|a.Valid.Email@gmail.com|
		|a+valid+email@gmail.com|
		#alternate domain address check to make sure security allows for domains other than gmail.com
		|avalidemail@hotmail.com|
		
  @invalid_username
  Scenario: User tries to login using an invalid e-mail address(blank field)
    Given user has an account for Hotel Planisphere with the e-mail "validemail@gmail.com"
    When user enters "password" as their password
  	And the user clicks the login button
    Then an error message appears beneath the e-mail field reading "このフィールドを入力してください。"
    
  @invalid_username
  Scenario Outline: User tries to login using an invalid e-mail address
    Given user has an account for Hotel Planisphere with the e-mail "validemail@gmail.com"
    When user enters "<invalid>" as their e-mail address 
    And user enters "<invalidPass>" as their password
  	And the user clicks the login button
    Then an error message appears beneath the e-mail field reading "<error>"
    
    Examples: 
      | invalid 									  | error 							  |
      | thisIsAnInvalidEmailAddress | メールアドレスを入力してください。	|
      | emailaddressgmail.com 			| メールアドレスを入力してください。	|
      | @gmail.com									| メールアドレスを入力してください。	|
      | invalidemail-@gmail.com			| メールアドレスを入力してください。	|
      | -invalidemail@gmail.com			| メールアドレスを入力してください。	|
      |	an--invalidemail@gmail.com	| メールアドレスを入力してください。	|
     	| invalidemail@.com						| メールアドレスを入力してください。	|
     	|	invalidemail@gmail					| メールアドレスを入力してください。	|
     	
   @invalid_password
   Scenario: user has a valid e-mail address but enters the wrong password
   Given user has an account for Hotel Planisphere with the e-mail "validemail@gmail.com"
   When user enters "validemail@gmail.com" as their e-mail address
   And user enters "<invalidPass>" as their password
   And the user clicks the login button
   Then an error message appears beneath the e-mail field reading "<errorEmail>"
   And an error message appears beneath the password field reading "<errorPass>"
   
   Examples:
   |invalidPass	 |errorEmail   						   |errorPass									  |
   |wrongPassword|メールアドレスまたはパスワードが違います。|メールアドレスまたはパスワードが違います。|
   |p@ssw0rd		 |メールアドレスまたはパスワードが違います。|メールアドレスまたはパスワードが違います。|
   
   @invalid_password
   Scenario: user has a valid e-mail address but does not enter a password
   Given user has an account for Hotel Planisphere with the e-mail "validemail@gmail.com"
   When user enters "validemail@gmail.com" as their e-mail address
   And the user clicks the login button
   Then an error message appears beneath the password field reading "このフィールドを入力してください。"
   
   @link_check
   Scenario: user clicks on the GitHub link and is redirected to GitHub
   When user clicks the "GitHub" link
   Then user is taken to the "gitHub" page
   
   @link_check
   Scenario: user clicks the home link and is redirected to the home page
   When user clicks the "ホーム" link
   Then user is taken to the "home" page
   
   @link_check
   Scenario: user clicks the home link and is redirected to the plans page
   When user clicks the "宿泊予約" link
   Then user is taken to the "plans" page
   
   @link_check
   Scenario: user clicks the home link and is redirected to the signup page
   When user clicks the "会員登録" link
   Then user is taken to the "signup" page
   
   @link_check
   Scenario: user clicks the home link and is redirected to the login page
   When user clicks the "ログイン" link
   Then user is taken to the "login" page
   
   #These are special test cases in case working with a mock server that handles the login process is possible, in which case different status codes would be returned to the front-end
   @error_handling
   Scenario Outline: User tries to login while the backend is returning server statuscodes other than 200
   Given Backend server is "<serverStatus>"
   When user enters "validemail@gmail.com" as their e-mail address
   And user enters "password" as their password
   And the user clicks the login button
   Then user is taken to a "<errorCode>" error page
   Examples:
   |serverStatus		|errorCode|
   |offline					|404			|
   |unavailable			|503			|
   |denying entrance|402			|
