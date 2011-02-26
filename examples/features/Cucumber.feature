Feature: Cucumber
  In order to implement BDD in my Scala project
  As a developer
  I want to be able to run Cucumber from within SBT

  Scenario: Execute feature with console output
    Given An SBT project
	And I have 4 cucumbers in my hands
	And I have entered hello into the console
    When I run the cucumber goal
    Then Cucumber is executed against my features and step definitions