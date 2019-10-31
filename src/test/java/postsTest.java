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
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

public class postsTest extends baseTest {

    private int postID;

    @Test
    public void listPosts(){
        try{
            given().when().get("/posts").then().assertThat().statusCode(200).and().contentType(ContentType.JSON);
            Response res = given().when().get("/posts").then().extract().response();
            System.out.println(res.asString());

            List<Map<String, Object>> posts = get("/posts").as(new TypeRef<List<Map<String, Object>>>() {});

            this.postID = (int) posts.get(0).get("id");

            assertThat(posts, hasSize(1));
            assertThat(posts.get(0).get("status"), Matchers.<Object>equalTo("publish"));
            assertThat(posts.get(0).get("type"), Matchers.<Object>equalTo("post"));
            assertThat(posts.get(0).get("author"), Matchers.<Object>equalTo(2));
        }catch(AssertionError ae){
            System.out.println(ae.getMessage());
            Assert.fail();
        }
    }

    @Test
    public void retrievePost(){
        try{
            given().when().get("/posts/"+ this.postID).then().assertThat().statusCode(200).and().contentType(ContentType.JSON);
            Response res = given().when().get("/posts/"+ this.postID).then().extract().response();
            System.out.println(res.asString());
            assertThat(res.path("id"),Matchers.<Object>equalTo(this.postID));
        }catch(AssertionError ae){
            System.out.println(ae.getMessage());
            Assert.fail();
        }
    }
}
