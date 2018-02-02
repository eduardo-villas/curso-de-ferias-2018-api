package matera.systems.cursoferias2018.api.resources;

import java.util.Base64;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import matera.systems.cursoferias2018.api.domain.response.RelatorioResponse;
import matera.systems.cursoferias2018.api.domain.response.RelatorioResponseEntry;
import matera.systems.cursoferias2018.api.repository.DisciplinasRepositoryStub;

public class RelatoriosResourceIT {

	@Test
	public void shouldFailWhenDisciplinaDoesntExist() {
		UUID disciplinaId = UUID.randomUUID();
    	Response response = 
    			RestAssured
    				.given()
    				.header(getAuthorizationHeader())
    				.get("http://localhost:8080/relatorio/" + disciplinaId)
    				.thenReturn();
    	
    	Assert.assertEquals(404, response.getStatusCode());
	}
	
    @Test
    public void shouldRunWithSuccess() {
    	
    	UUID disciplinaId = DisciplinasRepositoryStub.DISCIPLINA_1;
    	Response response = 
    			RestAssured
    				.given()
    				.header(getAuthorizationHeader())
    				.get("http://localhost:8080/relatorio/" + disciplinaId)
    				.thenReturn();

    	RelatorioResponse relatorio = response.getBody().as(RelatorioResponse.class);
    	
    	Assert.assertEquals(200, response.getStatusCode());
    	Assert.assertEquals(1, relatorio.getEntries().size());
    	
    	RelatorioResponseEntry relatorioEntry = relatorio.getEntries().get(0);
    	Assert.assertNotNull(relatorioEntry.getUsuario());
    	Assert.assertNotNull(relatorioEntry.getPresenca());
    }
    
    private Header getAuthorizationHeader() {

        String clientBasicAuthCredentials =
                Base64.getEncoder().encodeToString("angular:alunos".getBytes());

        Response response = RestAssured.given().
                header(new Header("Authorization", "Basic " + clientBasicAuthCredentials))
                .queryParam("username", "usuario")
                .queryParam("password", "password")
                .queryParam("grant_type", "password")
                .when()
                    .post("/oauth/token")
                .then().extract().response();

        String token = response.getBody().jsonPath().getString("access_token");

        return new Header("Authorization", "bearer " + token);
    }
}
