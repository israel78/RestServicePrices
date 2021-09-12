package es.beonit.prices;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import static io.restassured.RestAssured.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PricesApplicationTests {

	//Acepte como parámetros de entrada: fecha de aplicación, identificador de
	//producto, identificador de cadena.
	private static final String DATE_APPLY = "2020-06-14 13.30.00";
	private static final String PRODUCT_ID = "35455";
	private static final String BRAND_ID = "1";

	//Datos de salida: identificador de producto, identificador de
	//cadena, tarifa a aplicar, fechas de aplicación y precio final a aplicar.
	private static final String PRODUCT_ID_EXPECTED = "35455";
	private static final String BRAND_ID_EXPECTED = "1";
	private static final String PRICE_LIST_EXPECTED = "2";
	private static final String START_DATE_EXPECTED = "2020-06-14T12:30:00.000+0200";
	private static final String END_DATE_EXPECTED = "2020-06-14T15:00:00.000+0200";
	private static final String FINAL_PRICE = "25.45";

	//Valor entregado cuando la consulta no tiene resultados
	private static final String PRODUCT_ID_EMPTY_EXPECTED = "0" ;
	//Valor de fecha no exitente en el intervalo entre fecha de inicio y fecha fin
	private static final String DATE_APPLY_NOT_RESULT_EXPECTED = "2021-06-18 13.30.00";


	@LocalServerPort
	private int port;

	@Before
	public static void setup() {
		baseURI = "http://localhost";
	}

	@Test
	void notLogin(){
		Response r =  given().port(port).header("user", "paula")
				.header("pass", "paula")
				.when().get("/login").then()
				.extract().response();

		int requestStatus = r.getStatusCode();
		Assertions.assertTrue(requestStatus == HttpStatus.UNAUTHORIZED.value());
	}

	//LLamada correcta con datos para devolver resultado esperado
	@Test
	void getPriceResponse() {
		//La aplicación maneja sesiones y usuarios para logarse, y hacer consultas mediante token de acceso
		//despues del login
		Response r =  given().port(port).header("user", "antonio")
				.header("pass", "antonio")
				.when().get("/login").then()
				.extract().response();
		String bodyAsString = r.getBody().asString();
		Assertions.assertTrue(bodyAsString.contains("Token"));
		String sessionId = r.getCookie("JSESSIONID");

		r.jsonPath().getString("Token");
		ResponseBody pricesResponse = given()
				.port(port).cookie("JSESSIONID", sessionId)
				.header("Authorization", r.jsonPath().getString("Token"))
				.when().get("/getPriceApply?DateApply="+DATE_APPLY+"&productId="+PRODUCT_ID+"&brandId="+BRAND_ID).then()
				.extract().response().getBody();

		Assertions.assertEquals(pricesResponse.jsonPath().getString("productId"),PRODUCT_ID_EXPECTED);
		Assertions.assertEquals(pricesResponse.jsonPath().getString("brandId"),BRAND_ID_EXPECTED);
		Assertions.assertEquals(pricesResponse.jsonPath().getString("priceList"),PRICE_LIST_EXPECTED);
		Assertions.assertEquals(pricesResponse.jsonPath().getString("startDate"),START_DATE_EXPECTED);
		Assertions.assertEquals(pricesResponse.jsonPath().getString("endDate"),END_DATE_EXPECTED);
		Assertions.assertEquals(pricesResponse.jsonPath().getString("price"),FINAL_PRICE);
	}
	//Consulta sin datos
	@Test
	void getEmptyPriceResponse() {
		//La aplicación maneja sesiones y usuarios para logarse, y hacer consultas mediante token de acceso
		//despues del login
		Response r =  given().port(port).header("user", "pedro")
				.header("pass", "pedro")
				.when().get("/login").then()
				.extract().response();
		String bodyAsString = r.getBody().asString();
		Assertions.assertTrue(bodyAsString.contains("Token"));
		String sessionId = r.getCookie("JSESSIONID");

		r.jsonPath().getString("Token");
		ResponseBody pricesResponse = given()
				.port(port).cookie("JSESSIONID", sessionId)
				.header("Authorization", r.jsonPath().getString("Token"))
				.when().get("/getPriceApply?DateApply="+DATE_APPLY_NOT_RESULT_EXPECTED+"&productId="+PRODUCT_ID+"&brandId="+BRAND_ID).then()
				.extract().response().getBody();

		Assertions.assertEquals(pricesResponse.jsonPath().getString("productId"),PRODUCT_ID_EMPTY_EXPECTED);
	}
}
