package br.com.dbserver.RestAssured_Exemplos.core;

public class Movimentacao {
	private int id;
	private String descricao;
	private String envolvido;
	private String tipo;
	private String data_transacao;
	private String data_pagamento;
	private Float  valor;
	private boolean status;
	private int conta_id;
	private Integer usuario_id;
	
	
	
	
	public Movimentacao(int id, String descricao, String envolvido, String tipo, String sata_transação,
			String sata_pagamento, Float valor, boolean estado, int conta_id, Integer usuario_id) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.envolvido = envolvido;
		this.tipo = tipo;
		this.data_transacao = sata_transação;
		this.data_pagamento = sata_pagamento;
		this.valor = valor;
		this.status = estado;
		this.conta_id = conta_id;
		this.usuario_id = usuario_id;
	}
	public Movimentacao() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getEnvolvido() {
		return envolvido;
	}
	public void setEnvolvido(String envolvido) {
		this.envolvido = envolvido;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getData_transacao() {
		return data_transacao;
	}
	public void setData_transacao(String data_transação) {
		this.data_transacao = data_transação;
	}
	public String getData_pagamento() {
		return data_pagamento;
	}
	public void setData_pagamento(String sata_pagamento) {
		this.data_pagamento = sata_pagamento;
	}
	public Float getValor() {
		return valor;
	}
	public void setValor(Float valor) {
		this.valor = valor;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean estado) {
		this.status = estado;
	}
	public int getConta_id() {
		return conta_id;
	}
	public void setConta_id(int conta_id) {
		this.conta_id = conta_id;
	}
	public Integer getUsuario_id() {
		return usuario_id;
	}
	public void setUsuario_id(Integer usuario_id) {
		this.usuario_id = usuario_id;
	}

}
