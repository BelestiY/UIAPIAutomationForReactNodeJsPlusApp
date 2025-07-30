package runner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "stepdefs.api",
    tags = "@api",
    plugin = {
        "pretty",
        "html:target/api-cucumber-report.html",
        "json:target/api-cucumber-report.json"
    },
    monochrome = true
)
public class ApiTestRunner {
}
