import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

public class mediaTest extends baseTest {

    private int mediaID;

    private Response res = null;
    private ValidatableResponse vres = null;

    @Test(priority = 1,enabled = false)
    public void createMedia(){
        try{
            res = given()
                    //.header("Content-Disposition","form-data; filename=\"C:/cat.jpg\"")
                    .contentType("image/jpeg")
                    //.formParam("test-file","C:/cat.jpg")
                    .param("filename","C:/cat.jpg")
                    .when()
                    .post("/media")
                    .then().assertThat().statusCode(200)
                    .and().contentType(ContentType.JSON).extract().response();
        } catch (AssertionError | Exception e) {
            logger.fatal("Create Media Test" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 2)
    public void listMedia(){
        try{
            vres = given().
                    when().get("/media")
                    .then().assertThat().statusCode(200)
                    .and().contentType(ContentType.JSON)
                    .and().time(Matchers.lessThan(2000L));

            List<Map<String, Object>> media = get("/media").as(new TypeRef<List<Map<String, Object>>>() { });

            this.mediaID = (int) media.get(0).get("id");

            assertThat(media, hasSize(1));
            assertThat(media.get(0).get("media_type"), Matchers.<Object>equalTo("image"));
            assertThat(media.get(0).get("mime_type"), Matchers.<Object>equalTo("image/jpeg"));
            assertThat(media.get(0).get("author"), Matchers.<Object>equalTo(1));
        } catch (AssertionError | Exception e) {
            logger.fatal("List Media Test" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 3)
    public void retrieveMedia() {
        try {
            given().
                    when().get("/media/" + this.mediaID)
                    .then().assertThat().statusCode(200)
                    .and().contentType(ContentType.JSON)
                    .and().time(Matchers.lessThan(2000L))
                    .and().body("id", Matchers.equalTo(this.mediaID))
                    .and().body("type", Matchers.equalTo("attachment"))
                    .and().body("author", Matchers.equalTo(1))
                    .and().body("media_type", Matchers.equalTo("image"))
                    .and().body("mime_type", Matchers.equalTo("image/jpeg"))
                    .and().header("Content-Type", Matchers.equalTo("application/json; charset=UTF-8"))
                    .and().header("Connection", Matchers.equalTo("Keep-Alive"))
                    .and().header("Keep-Alive", Matchers.equalTo("timeout=5, max=100"))
                    .and().header("Allow", Matchers.equalTo("GET, POST, PUT, PATCH, DELETE"))
                    .and().header("Cache-Control", Matchers.equalTo("no-cache, must-revalidate, max-age=0"))
                    .and().header("Access-Control-Allow-Headers", Matchers.equalTo("Authorization, Content-Type"))
                    .and().header("Access-Control-Expose-Headers", Matchers.equalTo("X-WP-Total, X-WP-TotalPages"))
                    .and().header("X-Content-Type-Options", Matchers.equalTo("nosniff"));
        } catch (AssertionError | Exception e) {
            logger.fatal("Retrieve Media Test" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 4)
    public void updateMedia(){
        try {
            given().
                    when().post("/media/" + this.mediaID)
                    .then().assertThat().statusCode(200)
                    .and().contentType(ContentType.JSON)
                    .and().time(Matchers.lessThan(2000L))
                    .and().body("id", Matchers.equalTo(this.mediaID))
                    .and().body("type", Matchers.equalTo("attachment"))
                    .and().body("author", Matchers.equalTo(1))
                    .and().body("media_type", Matchers.equalTo("image"))
                    .and().body("mime_type", Matchers.equalTo("image/jpeg"))
                    .and().header("Content-Type", Matchers.equalTo("application/json; charset=UTF-8"))
                    .and().header("Connection", Matchers.equalTo("Keep-Alive"))
                    .and().header("Keep-Alive", Matchers.equalTo("timeout=5, max=100"))
                    .and().header("Allow", Matchers.equalTo("GET, POST, PUT, PATCH, DELETE"))
                    .and().header("Cache-Control", Matchers.equalTo("no-cache, must-revalidate, max-age=0"))
                    .and().header("Access-Control-Allow-Headers", Matchers.equalTo("Authorization, Content-Type"))
                    .and().header("Access-Control-Expose-Headers", Matchers.equalTo("X-WP-Total, X-WP-TotalPages"))
                    .and().header("X-Content-Type-Options", Matchers.equalTo("nosniff"));
        } catch (AssertionError | Exception e) {
            logger.fatal("Update Media Test" + e.getMessage());
            Assert.fail();
        }
    }

    @Test(priority = 5)
    public void deleteMedia(){
        try{
            given().
                    when().delete("/media/" + this.mediaID + "?force=true")
                    .then().assertThat().statusCode(200)
                    .and().contentType(ContentType.JSON)
                    .and().time(Matchers.lessThan(2000L))
                    .and().body("deleted", Matchers.equalTo(true))
                    .and().body("previous.id", Matchers.equalTo(this.mediaID))
                    .and().body("previous.type", Matchers.equalTo("attachment"))
                    .and().body("previous.author", Matchers.equalTo(1))
                    .and().body("previous.media_type", Matchers.equalTo("image"))
                    .and().body("previous.mime_type", Matchers.equalTo("image/jpeg"))
                    .and().header("Content-Type", Matchers.equalTo("application/json; charset=UTF-8"))
                    .and().header("Connection", Matchers.equalTo("Keep-Alive"))
                    .and().header("Keep-Alive", Matchers.equalTo("timeout=5, max=100"))
                    .and().header("Cache-Control", Matchers.equalTo("no-cache, must-revalidate, max-age=0"))
                    .and().header("Access-Control-Allow-Headers", Matchers.equalTo("Authorization, Content-Type"))
                    .and().header("Access-Control-Expose-Headers", Matchers.equalTo("X-WP-Total, X-WP-TotalPages"))
                    .and().header("X-Content-Type-Options", Matchers.equalTo("nosniff"));
        } catch (AssertionError | Exception e) {
            logger.fatal("Delete Media Test" + e.getMessage());
            Assert.fail();
        }
    }
}