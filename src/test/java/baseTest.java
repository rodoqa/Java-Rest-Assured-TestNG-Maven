import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import static utils.SupportFactory.*;

public class baseTest {

    public static final Logger logger = LogManager.getLogger(baseTest.class.getName());

    @BeforeSuite
    public void beforeSuite(){
        try {
            setExcelFile("base");
            RestAssured.baseURI= ipaddress();
            RestAssured.basePath=getCellData(1, 1);
            PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
            authScheme.setUserName(getCellData(1, 2));
            authScheme.setPassword(getCellData(1, 3));
            RestAssured.authentication = authScheme;
            logger.info("Starting");
        } catch (Exception e) {
            logger.fatal("Before suite crashed: " + e.getMessage());
        }
    }

    @AfterSuite
    public void afterSuite(){
        logger.info("Finishing");
    }
}
