Feature: API Sample Test

@POSTAPI @API
Scenario Outline: Verify posts api is working correctly
	Given I post data "<FirstName>" and "<LastName>"
	Then I get the posts data to verify it
	Then I update the data "JOHN" and "DOE"
	Then I get the posts data to verify it
	And I delete the posts data
	
	Examples:
	 	|FirstName			|LastName	|StatusCode	|
	 	|Vinitha			|Lakshmi	|200		|
	 	
@COMMENTAPI @API
Scenario Outline: Verify comments api is working correctly
	Given I comment data "<User>" and "<Comment>" and "<Sport>"
	Then I get the comment data to verify it
	Then I update the comment data "DUMMY USER" and "DUMMY COMMENT" and "DUMMY SPORT"
	Then I get the comment data to verify it
	And I delete the comment data
	
	Examples:
	 	|User			|Comment					|Sport		|
	 	|Vinitha		|Soccer game is awesome		|Soccer		|
	 	
	 	
@USERAPI @API
Scenario: Verify users api is working correctly
	Given I set the user data
	Then I get the user data to verify it
	Then I update the user data
	Then I get the user data to verify it
	And I delete the user data