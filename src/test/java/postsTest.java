import dao.PostDAO;
import dto.PostDTO;
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
    private PostDAO postDao = new PostDAO();
    private PostDTO post = postDao.filler();
    private Response res = null;

    @Test(priority = 1)
    public void createPost() {
        try {
            res = given().
                    queryParam("title", post.getTitle().split("")).
                    queryParam("content", post.getPostContent().split("")).
                    queryParam("author", post.getAuthor()).
                    queryParam("status", post.getStatus()).
                    queryParam("format", post.getFormat()).

                    when().post("/posts").then().assertThat().statusCode(201).and().contentType(ContentType.JSON).extract().response();
        } catch (AssertionError ae) {
            System.out.println(ae.getMessage());
            Assert.fail();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test(priority = 2)
    public void listPosts() {
        try {
            given().when().get("/posts").then().assertThat().statusCode(200).and().contentType(ContentType.JSON);

            List<Map<String, Object>> posts = get("/posts").as(new TypeRef<List<Map<String, Object>>>() { });

            this.postID = (int) posts.get(0).get("id");

            assertThat(posts, hasSize(1));
            assertThat(posts.get(0).get("status"), Matchers.<Object>equalTo(post.getStatus()));
            assertThat(posts.get(0).get("type"), Matchers.<Object>equalTo("post"));
            assertThat(posts.get(0).get("author"), Matchers.<Object>equalTo(3));
        } catch (AssertionError ae) {
            System.out.println(ae.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 3)
    public void retrievePost() {
        try {
            given().when().get("/posts/" + this.postID).then().assertThat().statusCode(200).and().contentType(ContentType.JSON);
            assertThat(res.path("id"), Matchers.<Object>equalTo(this.postID));
        } catch (AssertionError ae) {
            System.out.println(ae.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 4)
    public void updatePost() {
        try {
            res = given().
                    queryParam("title", post.getTitle()).
                    queryParam("content", post.getPostContent()).
                    queryParam("author", post.getAuthor()).
                    queryParam("status", post.getStatus()).
                    queryParam("format", post.getFormat()).
                    when().post("/posts/" + this.postID).then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();
        } catch (AssertionError ae) {
            System.out.println(ae.getMessage());
            Assert.fail();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 5)
    public void deletePost() {
        try {
            given().delete("/posts/" + this.postID).then().assertThat().statusCode(200).and().contentType(ContentType.JSON);
        } catch (AssertionError ae) {
            System.out.println(ae.getMessage());
            Assert.fail();
        }
    }
}
