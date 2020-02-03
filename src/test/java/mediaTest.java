import dao.ResponseCodesDAO;
import dto.ResponseCodesDTO;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
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
import static org.hamcrest.Matchers.hasSize;

public class mediaTest extends baseTest {

    private int mediaID;

    private Response res = null;
    private ValidatableResponse vres = null;
    private ResponseCodesDAO rcDAO = new ResponseCodesDAO();
    private ResponseCodesDTO rcDTO = new ResponseCodesDTO();

    public static final Logger logger = LogManager.getLogger(mediaTest.class.getName());

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

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 2)
    public void listMedia(){
        try{
            res = given().
                    when().get("/media")
                    .then().assertThat().statusCode(200)
                    .and().contentType(ContentType.JSON)
                    .and().time(Matchers.lessThan(2000L)).extract().response();

            List<Map<String, Object>> media = get("/media").as(new TypeRef<List<Map<String, Object>>>() { });

            this.mediaID = (int) media.get(0).get("id");

            assertThat(media, hasSize(1));
            assertThat(media.get(0).get("media_type"), Matchers.<Object>equalTo("image"));
            assertThat(media.get(0).get("mime_type"), Matchers.<Object>equalTo("image/jpeg"));
            assertThat(media.get(0).get("author"), Matchers.<Object>equalTo(1));

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 3)
    public void retrieveMedia() {
        try {
            res = given().
                    when().get("/media/" + this.mediaID)
                    .then().assertThat().statusCode(200)
                    .and().contentType(ContentType.JSON)
                    .and().time(Matchers.lessThan(2000L))
                    .and().body("id", Matchers.equalTo(this.mediaID))
                    .and().body("type", Matchers.equalTo("attachment"))
                    .and().body("author", Matchers.equalTo(3))
                    .and().body("media_type", Matchers.equalTo("image"))
                    .and().body("mime_type", Matchers.equalTo("image/jpeg"))
                    .and().header("Content-Type", Matchers.equalTo("application/json; charset=UTF-8"))
                    .and().header("Connection", Matchers.equalTo("Keep-Alive"))
                    .and().header("Keep-Alive", Matchers.equalTo("timeout=5, max=100"))
                    .and().header("Allow", Matchers.equalTo("GET, POST, PUT, PATCH, DELETE"))
                    .and().header("Cache-Control", Matchers.equalTo("no-cache, must-revalidate, max-age=0"))
                    .and().header("Access-Control-Allow-Headers", Matchers.equalTo("Authorization, Content-Type"))
                    .and().header("Access-Control-Expose-Headers", Matchers.equalTo("X-WP-Total, X-WP-TotalPages"))
                    .and().header("X-Content-Type-Options", Matchers.equalTo("nosniff")).extract().response();

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 4)
    public void updateMedia(){
        try {
            res = given().
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
                    .and().header("X-Content-Type-Options", Matchers.equalTo("nosniff")).extract().response();

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 5)
    public void deleteMedia(){
        try{
            res = given().
                    when().delete("/media/" + this.mediaID + "?force=true")
                    .then().assertThat().statusCode(200)
                    .and().contentType(ContentType.JSON)
                    .and().time(Matchers.lessThan(2000L))
                    .and().body("deleted", Matchers.equalTo(true))
                    .and().body("previous.id", Matchers.equalTo(this.mediaID))
                    .and().body("previous.type", Matchers.equalTo("attachment"))
                    .and().body("previous.author", Matchers.equalTo(3))
                    .and().body("previous.media_type", Matchers.equalTo("image"))
                    .and().body("previous.mime_type", Matchers.equalTo("image/jpeg"))
                    .and().header("Content-Type", Matchers.equalTo("application/json; charset=UTF-8"))
                    .and().header("Connection", Matchers.equalTo("Keep-Alive"))
                    .and().header("Keep-Alive", Matchers.equalTo("timeout=5, max=100"))
                    .and().header("Cache-Control", Matchers.equalTo("no-cache, must-revalidate, max-age=0"))
                    .and().header("Access-Control-Allow-Headers", Matchers.equalTo("Authorization, Content-Type"))
                    .and().header("Access-Control-Expose-Headers", Matchers.equalTo("X-WP-Total, X-WP-TotalPages"))
                    .and().header("X-Content-Type-Options", Matchers.equalTo("nosniff")).extract().response();

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }
}