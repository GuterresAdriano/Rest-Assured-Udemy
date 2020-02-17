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
import br.com.dbserver.RestAssured_Exemplos.tools.Tools;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;



public class ContaTestCases implements Constantes{
	
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
	public void cadastrarContaComSucesso() {	
		
		given()		
			.body("{\"nome\": \""+Tools.getNovoNomeConta()+"\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(201)
			;
	}
	
	@Test
	public void alterarContaComSucesso() {			
		
		given()
			.body("{\"nome\": \"Alterada "+Tools.getNovoNomeConta()+"\"}")
		.when()
			.put("/contas/"+getIdContaPeloNome("Conta para alterar"))
		.then()
			.statusCode(200);		
	}
		
	@Test
	public void naoPermitirContaDuplicada() {		
		
		given()
			.body("{\"nome\": \"Conta mesmo nome\"}")
		.when()
			.post("/contas")
		.then()
			.statusCode(400)
			.body("error", is("Já existe uma conta com esse nome!"));		
	}
	
	@Test
	public void nãoPermitirRemocaoContaComTransacao() {	
		
		given()
		.when()
			.delete("/contas/65576")
		.then()
			.statusCode(404)			
			;
	}
}
