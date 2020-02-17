package br.com.dbserver.RestAssured_Exemplos.testCase;

import static br.com.dbserver.RestAssured_Exemplos.tools.Tools.alterarDataDias;
import static br.com.dbserver.RestAssured_Exemplos.tools.Tools.getIdContaPeloNome;
import static br.com.dbserver.RestAssured_Exemplos.tools.Tools.getIdMovPelaDesc;
import static br.com.dbserver.RestAssured_Exemplos.tools.Tools.getMovimentacaoValida;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import br.com.dbserver.RestAssured_Exemplos.core.Constantes;
import br.com.dbserver.RestAssured_Exemplos.core.Movimentacao;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;



public class MovimentacaoTestCases implements Constantes{
	
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
	public void cadastrarUmaMovimentacaoComSucesso() {	
		
		given()
			.body(getMovimentacaoValida(getIdContaPeloNome("Conta para movimentacoes")))			
		.when()
			.post("/transacoes")
		.then()
			.statusCode(201)			
			;
	}
	
	@Test
	public void validarCamposObrigatotriosMovimentacao() {			
		given()
			.body("{}")			
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(8))
			.body("msg", hasItems(
								"Data da Movimentação é obrigatório",
								"Data do pagamento é obrigatório",
								"Descrição é obrigatório",
								"Interessado é obrigatório",
								"Valor é obrigatório", 
								"Valor deve ser um número", 
								"Conta é obrigatório",
								"Situação é obrigatório"
								 ))
			;
	}	

	@Test
	public void naoPermitirMovimentacaoDataFutura() {	
		int id_conta = getIdContaPeloNome("Conta para movimentacoes");
		Movimentacao movimentacao = getMovimentacaoValida(id_conta);
		movimentacao.setData_transacao(alterarDataDias(1));
		
		given()
			.body(movimentacao)			
		.when()
			.post("/transacoes")
		.then()
			.statusCode(400)
			.body("$", hasSize(1))
			.body("msg", hasItem("Data da Movimentação deve ser menor ou igual à data atual"))
			;
	}	
	
	@Test
	public void nãoPermitirRemocaoContaComTransacao() {	
		
		given()
		.when()
			.delete("/contas/"+getIdContaPeloNome("Conta com movimentacao"))
		.then()
			.statusCode(500)
			.body("constraint", is("transacoes_conta_id_foreign"))
			;
	}
	
	@Test
	public void removerUmasMovimentacaoComSucesso() {	
		
		given()
		.when()
			.delete("/transacoes/"+getIdMovPelaDesc("Movimentacao para exclusao"))
		.then()
			.statusCode(204)
			;
	}

}
