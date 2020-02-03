import dao.PostDAO;
import dao.ResponseCodesDAO;
import dto.PostDTO;
import dto.ResponseCodesDTO;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private ResponseCodesDAO rcDAO = new ResponseCodesDAO();
    private ResponseCodesDTO rcDTO = new ResponseCodesDTO();

    public static final Logger logger = LogManager.getLogger(postsTest.class.getName());

    @Test(priority = 1)
    public void createPost() {
        try {
            res = given().
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
                    .and().time(Matchers.lessThan(2000L)).extract().response();

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 2)
    public void listPosts() {
        try {
            res = given().
                    when().get("/posts")
                    .then().assertThat().statusCode(200)
                    .and().contentType(ContentType.JSON)
                    .and().time(Matchers.lessThan(2000L)).extract().response();

            List<Map<String, Object>> posts = get("/posts").as(new TypeRef<List<Map<String, Object>>>() {
            });

            this.postID = (int) posts.get(0).get("id");

            assertThat(posts, hasSize(1));
            assertThat(posts.get(0).get("status"), Matchers.<Object>equalTo(post.getStatus()));
            assertThat(posts.get(0).get("type"), Matchers.<Object>equalTo("post"));
            assertThat(posts.get(0).get("author"), Matchers.<Object>equalTo(3));
            assertThat(posts.get(0).get("format"), Matchers.equalTo("standard"));

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 3)
    public void retrievePost() {
        try {
            res = given().
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
                    .and().header("X-Content-Type-Options", Matchers.equalTo("nosniff")).extract().response();

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
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
                    .and().header("X-Content-Type-Options", Matchers.equalTo("nosniff")).extract().response();
            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 5)
    public void deletePost() {
        try {
            res = given().
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
                    .and().header("X-Content-Type-Options", Matchers.equalTo("nosniff")).extract().response();

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }
}
