import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class sandboxTest {

    @Test
    public void BTest(){
        RestAssured.baseURI="http://192.168.7.142/wordpress/wp-json/wp/v2/";

        try{
            given().when().get("/posts").then().assertThat().statusCode(200).and().contentType(ContentType.JSON);

            Response res = given().when().get("/posts").then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();

            List<Map<String, Object>> posts = get("/posts").as(new TypeRef<List<Map<String, Object>>>() {});

            // Now you can do validations on the extracted objects:
            assertThat(posts, hasSize(1));
            assertThat(posts.get(0).get("status"), Matchers.<Object>equalTo("publish"));
            assertThat(posts.get(0).get("type"), Matchers.<Object>equalTo("post"));
            assertThat(posts.get(0).get("author"), Matchers.<Object>equalTo(1));
            //assertThat(posts.get(0).get("title.rendered"), Matchers.<Object>equalTo("hola"));

            System.out.println(res.asString());

        }catch(AssertionError ae){
            System.out.println(ae.getMessage());
            Assert.fail();
        }
    }
}
