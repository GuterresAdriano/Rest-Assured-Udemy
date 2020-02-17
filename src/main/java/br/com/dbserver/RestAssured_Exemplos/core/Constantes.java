package br.com.dbserver.RestAssured_Exemplos.core;

import io.restassured.http.ContentType;

public interface Constantes {	
	String		BASE_URL 		  = "https://barrigarest.wcaquino.me";
	String 		BASE_PATH 		  = "";
	int 		BASE_PORT 		  = 443;	
	ContentType BASE_CONTENT_TYPE = ContentType.JSON;	
	long 		BASE_MAX_TIMEOUT  = 5000L;

}
