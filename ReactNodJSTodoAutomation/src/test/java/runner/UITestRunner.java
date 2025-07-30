package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "stepdefs.ui",
    tags = "@ui",
    plugin = {
        "pretty",
        "html:target/ui-cucumber-report.html",
        "json:target/ui-cucumber-report.json"
    },
    monochrome = true
)
public class UITestRunner {
}