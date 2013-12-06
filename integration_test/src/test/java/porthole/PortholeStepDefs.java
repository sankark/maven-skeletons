package porthole;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class PortholeStepDefs {
   @Given("^I have a state$")
   public void I_have_a_state() throws Throwable {
      System.out.println("Hi, I am just an example. Porthole is a funny word");
   }

   @When("^I change that state$")
   public void I_change_that_state() throws Throwable {
      // Express the Regexp above with the code you wish you had
      throw new PendingException();
   }

   @Then("^I should see a new state$")
   public void I_should_see_a_new_state() throws Throwable {
      // Express the Regexp above with the code you wish you had
      throw new PendingException();
   }

}
