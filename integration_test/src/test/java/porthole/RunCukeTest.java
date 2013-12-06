package porthole;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
      format = {"pretty", "html:target/cucumber-html-report", "json:target/cucumber.json"}
)
public class RunCukeTest {
}

