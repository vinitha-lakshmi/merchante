Feature: UI Sample Test 

@NEWUSER_CREATE @UI
Scenario Outline: Verify items can be added to shopping cart 
	Given page is opened successfully 
	Then I create new user "<UserName>" and "<Password>" and "<Email>"
	And verify banner message "<SuccessMessage>"
	Then filter username "<FilterOption>" user "<UserName>"
	And submit the filter options
	Then I verify filtered record "1"
	Then I delete the new user "<UserName>"
	And verify banner message "<DeleteMessage>"
	
	Examples: 
	|UserName	|Password	|Email			|SuccessMessage					|DeleteMessage						|FilterOption	|
	|vinitha	|vinitha	|qa@gmail.com	|User was successfully created.	|User was successfully destroyed.	|Equals			|
	
@NEWUSER_ERROR_VALIDATE @UI
Scenario Outline: Verify items can be added to shopping cart 
	Given page is opened successfully 
	Then I create new user "<UserName>" and "<Password>" and "<Email>"
	And I verify field errors "<UserNameError>" and "<PasswordError>" and "<EmailError>"
	
	Examples: 
	|UserName	|Password	|Email			|UserNameError		|PasswordError								|EmailError			|
	|			|			|				|can't be blank		|can't be blank								|is invalid			|
	|ABC		|			|				|					|can't be blank								|is invalid			|
	|ABC		|123		|				|					|is too short (minimum is 4 characters)		|is invalid			|
	|ABC		|1234		|				|					|											|is invalid			|
	
@FILTER @UI
Scenario Outline: Verify items can be added to shopping cart 
	Given page is opened successfully
	And filter username "<UserFilterOption>" user "<UserName>"
	And filter email "<EmailFilterOption>" user "<Email>"
	And filter date "<FROM>" to "<TO>"
	And submit the filter options
	Then I verify filtered record "<Count>"
	
	Examples: 
	|UserName	|UserFilterOption	|Email		|EmailFilterOption		|FROM		|TO			|Count	|
	|rob		|Contains			|aus		|Contains				|2020-03-23	|2020-03-23	|1		|
	|rob		|Contains			|aus		|Contains				|2021-03-23	|2021-03-23	|0		|