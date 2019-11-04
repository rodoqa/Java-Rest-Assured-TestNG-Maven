import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static utils.SupportFactory.getCellData;
import static utils.SupportFactory.setExcelFile;

public class postsTest extends baseTest {

    private int postID;

    @Test(priority = 2)
    public void listPosts() {
        try {
            given().when().get("/posts").then().assertThat().statusCode(200).and().contentType(ContentType.JSON);
            Response res = given().when().get("/posts").then().extract().response();
            System.out.println(res.asString());

            List<Map<String, Object>> posts = get("/posts").as(new TypeRef<List<Map<String, Object>>>() {
            });

            this.postID = (int) posts.get(0).get("id");

            assertThat(posts, hasSize(1));
            assertThat(posts.get(0).get("status"), Matchers.<Object>equalTo("publish"));
            assertThat(posts.get(0).get("type"), Matchers.<Object>equalTo("post"));
            assertThat(posts.get(0).get("author"), Matchers.<Object>equalTo(2));
        } catch (AssertionError ae) {
            System.out.println(ae.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 3)
    public void retrievePost() {
        try {
            given().when().get("/posts/" + this.postID).then().assertThat().statusCode(200).and().contentType(ContentType.JSON);
            Response res = given().when().get("/posts/" + this.postID).then().extract().response();
            System.out.println(res.asString());
            assertThat(res.path("id"), Matchers.<Object>equalTo(this.postID));
        } catch (AssertionError ae) {
            System.out.println(ae.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 1)
    public void createPost() {
        try {
            setExcelFile("base");

            System.out.println(getCellData(1, 4) + "\n" + getCellData(1, 5) + "\n" + getCellData(1, 6) + "\n" + getCellData(1, 7) + "\n" + getCellData(1, 8));

            Object title = getCellData(1, 4);
            Object content = getCellData(1, 5);
            Object author = getCellData(1, 6);
            Object status = getCellData(1, 7);
            Object format = getCellData(1, 8);

            Response res = given().
                    queryParam("title","JRTM post title").
                    queryParam("content","JRTM post content").
                    queryParam("author","2").
                    queryParam("status","publish").
                    queryParam("format","standard").
                    when().post("/posts").then().assertThat().statusCode(201).and().contentType(ContentType.JSON).extract().response();
            System.out.println(res.asString());

        } catch (AssertionError ae) {
            System.out.println(ae.getMessage());
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 4)
    public void deletePost() {
        try {
            ValidatableResponse res = given().delete("/posts/" + this.postID).then().assertThat().statusCode(200).and().contentType(ContentType.JSON);
            System.out.println(res.toString());
        } catch (AssertionError ae) {
            System.out.println(ae.getMessage());
            Assert.fail();
        }
    }
}
