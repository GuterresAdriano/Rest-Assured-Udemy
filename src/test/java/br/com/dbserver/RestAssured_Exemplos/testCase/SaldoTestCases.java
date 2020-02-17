package br.com.dbserver.RestAssured_Exemplos.testCase;

import static br.com.dbserver.RestAssured_Exemplos.tools.Tools.getIdContaPeloNome;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.com.dbserver.RestAssured_Exemplos.core.Constantes;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;



public class SaldoTestCases implements Constantes{
	
	private static String TOKEN;
	
	@BeforeAll 
	public static void setup() {
		
    RestAssured.baseURI  = BASE_URL;
	RestAssured.port     = BASE_PORT;
	RestAssured.basePath = BASE_PATH;
	
	RequestSpecBuilder request = new RequestSpecBuilder()
			.setContentType(BASE_CONTENT_TYPE);
	RestAssured.requestSpecification = request.build();
	
	ResponseSpecBuilder response = new ResponseSpecBuilder()
			.expectResponseTime(Matchers.lessThan(BASE_MAX_TIMEOUT));
	RestAssured.responseSpecification = response.build();
	
	RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();		
	
	Map<String, String> map = new HashMap<String, String>();
	map.put("email", "guterres.adriano@gmail.com");
	map.put("senha", "12345");
	
	TOKEN =		
	    given()
			.body(map)
		.when()
			.post("/signin")
		.then()
			.extract().path("token");	
	
	RestAssured.requestSpecification.header("Authorization", "JWT " + TOKEN);
	
	RestAssured.get("/reset").then().statusCode(200);
	
	}	
	
	@Test
	public void verificarSaldoConta() {										
		given()
		.when()
			.get("/saldo")
		.then()
			.statusCode(200)
			.body("find{it.conta_id == "+getIdContaPeloNome("Conta para saldo")+"}.saldo", is("534.00") )
			;
	}

}
