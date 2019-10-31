import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import org.testng.annotations.*;

import static utils.SupportFactory.*;

public class baseTest {

    @BeforeSuite
    public void beforeSuite(){
        try {
            setExcelFile("base");
            RestAssured.baseURI=getCellData(1, 0);
            RestAssured.basePath=getCellData(1, 1);
            PreemptiveBasicAuthScheme authScheme = new PreemptiveBasicAuthScheme();
            authScheme.setUserName(getCellData(1, 2));
            authScheme.setPassword(getCellData(1, 3));
            RestAssured.authentication = authScheme;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("Done");
    }
}
