import dao.PostDAO;
import dto.PostDTO;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
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
            given().
                    queryParam("title", post.getTitle()).
                    queryParam("content", post.getPostContent()).
                    queryParam("author", post.getAuthor()).
                    queryParam("status", post.getStatus()).
                    queryParam("format", post.getFormat()).
                    when().post("/posts")
                    .then().assertThat().statusCode(201)
                    .and().body("title.raw", Matchers.<Object>equalTo("JRTM post title"))
                    .and().body("content.raw", Matchers.equalTo("JRTM post content"))
                    .and().body("author", Matchers.equalTo(3))
                    .and().body("format", Matchers.equalTo("standard"))
                    .and().header("Allow", Matchers.equalTo("GET, POST"))
                    .and().time(Matchers.lessThan(2000L));
        } catch (AssertionError | Exception e) {
            logger.fatal("Create Post Test" + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 2)
    public void listPosts() {
        try {
            given().
                    when().get("/posts")
                    .then().assertThat().statusCode(200)
                    .and().contentType(ContentType.JSON)
                    .and().time(Matchers.lessThan(2000L));

            List<Map<String, Object>> posts = get("/posts").as(new TypeRef<List<Map<String, Object>>>() {
            });

            this.postID = (int) posts.get(0).get("id");

            assertThat(posts, hasSize(1));
            assertThat(posts.get(0).get("status"), Matchers.<Object>equalTo(post.getStatus()));
            assertThat(posts.get(0).get("type"), Matchers.<Object>equalTo("post"));
            assertThat(posts.get(0).get("author"), Matchers.<Object>equalTo(3));
            assertThat(posts.get(0).get("format"), Matchers.equalTo("standard"));
        } catch (AssertionError | Exception e) {
            logger.fatal("List Post Test" + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 3)
    public void retrievePost() {
        try {
            given().
                    when().get("/posts/" + this.postID)
                    .then().assertThat().statusCode(200)
                    .and().contentType(ContentType.JSON)
                    .and().time(Matchers.lessThan(2000L))
                    .and().body("id", Matchers.<Object>equalTo(this.postID))
                    .and().body("status", Matchers.equalTo("publish"))
                    .and().body("type", Matchers.equalTo("post"))
                    .and().body("title.rendered", Matchers.equalTo("JRTM post title"))
                    .and().body("author", Matchers.equalTo(3))
                    .and().body("format", Matchers.equalTo("standard"))
                    .and().header("Content-Type", Matchers.equalTo("application/json; charset=UTF-8"))
                    .and().header("Connection", Matchers.equalTo("Keep-Alive"))
                    .and().header("Keep-Alive", Matchers.equalTo("timeout=5, max=100"))
                    .and().header("Allow", Matchers.equalTo("GET, POST, PUT, PATCH, DELETE"))
                    .and().header("Cache-Control", Matchers.equalTo("no-cache, must-revalidate, max-age=0"))
                    .and().header("Access-Control-Allow-Headers", Matchers.equalTo("Authorization, Content-Type"))
                    .and().header("Access-Control-Expose-Headers", Matchers.equalTo("X-WP-Total, X-WP-TotalPages"))
                    .and().header("X-Content-Type-Options", Matchers.equalTo("nosniff"));
        } catch (AssertionError | Exception e) {
            logger.fatal("Retrieve Post Test" + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 4)
    public void updatePost() {
        try {
            given().
                    queryParam("title", post.getTitle()).
                    queryParam("content", post.getPostContent()).
                    queryParam("author", post.getAuthor()).
                    queryParam("status", post.getStatus()).
                    queryParam("format", post.getFormat()).
                    when().post("/posts/" + this.postID)
                    .then().assertThat().statusCode(200)
                    .and().contentType(ContentType.JSON)
                    .and().time(Matchers.lessThan(2000L))
                    .and().body("id", Matchers.<Object>equalTo(this.postID))
                    .and().body("status", Matchers.equalTo("publish"))
                    .and().body("type", Matchers.equalTo("post"))
                    .and().body("title.rendered", Matchers.equalTo("JRTM post title"))
                    .and().body("author", Matchers.equalTo(3))
                    .and().body("format", Matchers.equalTo("standard"))
                    .and().header("Content-Type", Matchers.equalTo("application/json; charset=UTF-8"))
                    .and().header("Connection", Matchers.equalTo("Keep-Alive"))
                    .and().header("Keep-Alive", Matchers.equalTo("timeout=5, max=100"))
                    .and().header("Allow", Matchers.equalTo("GET, POST, PUT, PATCH, DELETE"))
                    .and().header("Cache-Control", Matchers.equalTo("no-cache, must-revalidate, max-age=0"))
                    .and().header("Access-Control-Allow-Headers", Matchers.equalTo("Authorization, Content-Type"))
                    .and().header("Access-Control-Expose-Headers", Matchers.equalTo("X-WP-Total, X-WP-TotalPages"))
                    .and().header("X-Content-Type-Options", Matchers.equalTo("nosniff"));
        } catch (AssertionError | Exception e) {
            logger.fatal("Update Post Test" + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 5)
    public void deletePost() {
        try {
            given().
                    delete("/posts/" + this.postID)
                    .then().assertThat().statusCode(200)
                    .and().contentType(ContentType.JSON)
                    .and().time(Matchers.lessThan(2000L)).and().body("id", Matchers.<Object>equalTo(this.postID))
                    .and().body("status", Matchers.equalTo("trash"))
                    .and().body("type", Matchers.equalTo("post"))
                    .and().body("title.rendered", Matchers.equalTo("JRTM post title"))
                    .and().body("author", Matchers.equalTo(3))
                    .and().body("format", Matchers.equalTo("standard"))
                    .and().header("Content-Type", Matchers.equalTo("application/json; charset=UTF-8"))
                    .and().header("Connection", Matchers.equalTo("Keep-Alive"))
                    .and().header("Keep-Alive", Matchers.equalTo("timeout=5, max=100"))
                    .and().header("Allow", Matchers.equalTo("GET, POST, PUT, PATCH, DELETE"))
                    .and().header("Cache-Control", Matchers.equalTo("no-cache, must-revalidate, max-age=0"))
                    .and().header("Access-Control-Allow-Headers", Matchers.equalTo("Authorization, Content-Type"))
                    .and().header("Access-Control-Expose-Headers", Matchers.equalTo("X-WP-Total, X-WP-TotalPages"))
                    .and().header("X-Content-Type-Options", Matchers.equalTo("nosniff"));
        } catch (AssertionError | Exception e) {
            logger.fatal("Delete Post Test" + e.getMessage());
            Assert.fail(e.getMessage());
        }
    }
}
