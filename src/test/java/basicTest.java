import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class basicTest {

    @Test
    public void BTest(){
        RestAssured.baseURI="http://192.168.7.142/wordpress/wp-json/wp/v2/";

        try{
            given().when().get("/posts").then().assertThat().statusCode(200);
        }catch(AssertionError ae){
            System.out.println(ae.getMessage());
            Assert.fail();
        }
    }
}
