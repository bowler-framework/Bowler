require 'rspec/expectations'

Before do
  @hello = "Hello World"
end

After do
end

Given /^An SBT project$/ do
 # pending # express the regexp above with the code you wish you had
end

Given /^I have (\d+) cucumbers in my hands$/ do |number|
  number.to_i.should == 4
end

Given /^I have entered (.*) into the console$/ do |greeting|
 greeting.should == "hello"
end

When /^I run the cucumber goal$/ do
#  pending # express the regexp above with the code you wish you had
end

Then /^Cucumber is executed against my features and step definitions$/ do
  @hello.should == "Hello World"
end