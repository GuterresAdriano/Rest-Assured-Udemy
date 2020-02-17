package br.com.dbserver.RestAssured_Exemplos.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import br.com.dbserver.RestAssured_Exemplos.core.Constantes;
import br.com.dbserver.RestAssured_Exemplos.core.Movimentacao;
import io.restassured.RestAssured;

public class Tools implements Constantes {

	private static DateTimeFormatter DATE_TIME_FORMATER =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private static LocalDateTime     DATE_TIME          = LocalDateTime.now();

	public static String getNovoNomeConta() {		
		return "Conta Teste " + DATE_TIME.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
	}

	public static String alterarDataDias(int dias) {	
		return DATE_TIME.plusDays(dias).format(DATE_TIME_FORMATER);
	}

	public static Movimentacao getMovimentacaoValida(int idConta) { 
		Movimentacao movimentacao = new Movimentacao();

		movimentacao.setConta_id(idConta);
		movimentacao.setDescricao("Descrição");
		movimentacao.setEnvolvido("Envolvido");
		movimentacao.setTipo("REC");
		movimentacao.setData_transacao(alterarDataDias(0));
		movimentacao.setData_pagamento(alterarDataDias(0));
		movimentacao.setValor(1000f);
		movimentacao.setStatus(true);	

		return movimentacao;
	}
	
	public static Integer getIdContaPeloNome(String nome) {
		return RestAssured.get("/contas?nome="+nome).then().extract().body().path("id[0]");		
	}
	
	public static Integer getIdMovPelaDesc(String desc) {
		return RestAssured.get("/transacoes?descricao="+desc).then().extract().body().path("id[0]");		
	}
}
