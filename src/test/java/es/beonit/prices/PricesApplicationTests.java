package es.beonit.prices;
import io.restassured.response.Response;
import es.beonit.prices.controller.Controller;
import es.beonit.prices.service.Service;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.notNullValue;
import static io.restassured.RestAssured.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

@SpringBootTest
class PricesApplicationTests {

	@BeforeAll
	public static void setup() {
		baseURI = "http://localhost";
		port = 7000;
	}
	@Test
	String Login(){
		ResponseBody r =  given().header("user", "antonio")
				.header("pass", "antonio")
				.when().get("/login").then()
				.extract().response().getBody();
		String bodyAsString = r.asString();
		Assertions.assertTrue(bodyAsString.contains("Token"));
	return r.jsonPath().getString("Token");

	}
	@Test
	void getPriceResponse() {
		ResponseBody pricesResponse = given().header("Authorization",Login())
				.when().get("/getPriceApply?DateApply=2020-06-14 13.30.00&productId=35455&brandId=1").then()
				.extract().response().getBody();
		String ProductId = pricesResponse.jsonPath().getString("productId");


	}
}
