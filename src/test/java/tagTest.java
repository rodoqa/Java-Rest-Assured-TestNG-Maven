import dao.ResponseCodesDAO;
import dao.TagDAO;
import dto.ResponseCodesDTO;
import dto.TagDTO;
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

public class tagTest extends baseTest {
    private int tagID;
    private TagDAO tagDao = new TagDAO();
    private TagDTO tag = tagDao.filler();
    private Response res = null;
    private ResponseCodesDAO rcDAO = new ResponseCodesDAO();
    private ResponseCodesDTO rcDTO = new ResponseCodesDTO();

    public static final Logger logger = LogManager.getLogger(tagTest.class.getName());

    @Test(priority = 1)
    public void createTag() {
        try {
            res = given().
                    queryParam("name",tag.getTag_name()).
                    queryParam("description",tag.getTag_description()).
                    queryParam("slug",tag.getTag_slug()).
                    when().post("/tags").then().assertThat().statusCode(201).and().contentType(ContentType.JSON).extract().response();

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 2)
    public void listTags() {
        try {
            res = given().when().get("/tags").then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();

            List<Map<String, Object>> tags = get("/tags").as(new TypeRef<List<Map<String, Object>>>() { });

            this.tagID = (int) tags.get(0).get("id");

            assertThat(tags, hasSize(1));
            assertThat(tags.get(0).get("name"), Matchers.<Object>equalTo(tag.getTag_name()));
            assertThat(tags.get(0).get("description"), Matchers.<Object>equalTo(tag.getTag_description()));
            assertThat(tags.get(0).get("slug"), Matchers.<Object>equalTo(tag.getTag_slug()));
            assertThat(tags.get(0).get("taxonomy"), Matchers.<Object>equalTo(tag.getTag_taxonomy()));

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 3)
    public void retrieveTag() {
        try {
            res = given().when().get("/tags/" + this.tagID).then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();
            assertThat(res.path("id"), Matchers.<Object>equalTo(this.tagID));
            assertThat(res.path("name"), Matchers.<Object>equalTo(tag.getTag_name()));
            assertThat(res.path("description"), Matchers.<Object>equalTo(tag.getTag_description()));
            assertThat(res.path("slug"), Matchers.<Object>equalTo(tag.getTag_slug()));

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 4)
    public void updateTag() {
        try {
            res = given().
                    queryParam("name",tag.getTag_name() + " (EDITED)").
                    queryParam("description",tag.getTag_description() + " (EDITED)").
                    queryParam("slug",tag.getTag_slug()).
                    when().post("/tags/" + this.tagID).then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 5)
    public void deleteTag() {
        try {
            res = given().
                    queryParam("force","true").
                    when().delete("/tags/" + this.tagID).then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }
}
