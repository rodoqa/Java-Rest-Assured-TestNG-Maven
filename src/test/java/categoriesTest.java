import dao.CatDAO;
import dao.ResponseCodesDAO;
import dto.CatDTO;
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

public class categoriesTest extends baseTest  {

    private int catID;
    private CatDAO catDao = new CatDAO();
    private CatDTO cat = catDao.filler();
    private Response res = null;
    private ResponseCodesDAO rcDAO = new ResponseCodesDAO();
    private ResponseCodesDTO rcDTO = new ResponseCodesDTO();

    public static final Logger logger = LogManager.getLogger(categoriesTest.class.getName());

    @Test(priority = 1)
    public void createCategory() {
        try {
            res = given().
                    log().ifValidationFails().
                    queryParam("name",cat.getCat_name()).
                    queryParam("description",cat.getCat_description()).
                    queryParam("slug",cat.getCat_slug()).
                    when().post("/categories").then().assertThat().statusCode(201).and().contentType(ContentType.JSON).extract().response();

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 2)
    public void listCategories() {
        try {
            res = given().log().ifValidationFails().when().get("/categories").then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();

            List<Map<String, Object>> cats = get("/categories").as(new TypeRef<List<Map<String, Object>>>() { });

            this.catID = (int) cats.get(0).get("id");

            assertThat(cats, hasSize(2));
            assertThat(cats.get(0).get("name"), Matchers.<Object>equalTo(cat.getCat_name()));
            assertThat(cats.get(0).get("description"), Matchers.<Object>equalTo(cat.getCat_description()));
            assertThat(cats.get(0).get("slug"), Matchers.<Object>equalTo(cat.getCat_slug()));
            assertThat(cats.get(0).get("taxonomy"), Matchers.<Object>equalTo(cat.getCat_taxonomy()));

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 3)
    public void retrieveCategory() {
        try {
            res = given().log().ifValidationFails().when().get("/categories/" + this.catID).then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();
            assertThat(res.path("id"), Matchers.<Object>equalTo(this.catID));
            assertThat(res.path("name"), Matchers.<Object>equalTo(cat.getCat_name()));
            assertThat(res.path("description"), Matchers.<Object>equalTo(cat.getCat_description()));
            assertThat(res.path("slug"), Matchers.<Object>equalTo(cat.getCat_slug()));

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 4)
    public void updateCategory() {
        try {
            res = given().log().ifValidationFails().
                    queryParam("name",cat.getCat_name() + " (EDITED)").
                    queryParam("description","JRTM category description (EDITED)").
                    queryParam("slug","jrtm321").
                    when().post("/categories/" + this.catID).then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }

    @Test(priority = 5)
    public void deleteCategory() {
        try {
            res = given().log().ifValidationFails().
                    queryParam("force","true").
                    when().delete("/categories/" + this.catID).then().assertThat().statusCode(200).and().contentType(ContentType.JSON).extract().response();

            int sc = res.getStatusCode();
            rcDTO = rcDAO.getCodeName(sc);

            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
        } catch (AssertionError | Exception e) {
            logger.fatal(Thread.currentThread().getStackTrace()[1].getMethodName()+ " " + rcDTO.getDetailStatusCode());
            Assert.fail(e.getMessage());
        }
    }
}
