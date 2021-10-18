package com.udacity.pricing;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.client.RestTemplate;

//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
////
////import org.hamcrest.MatcherAssert;
////import org.junit.Test;
////import org.junit.runner.RunWith;
//
//
////import org.junit.jupiter.api.Test;
////import org.junit.jupiter.api.extension.ExtendWith;
////import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
////import org.springframework.boot.test.context.SpringBootTest;
////import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
////import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//
//import antlr.collections.List;
//
//import org.junit.jupiter.api.Assertions;
//
//
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
//@RunWith(SpringRunner.class)
//@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PricingServiceApplicationTests {

	@LocalServerPort
	private int port;
	
//	@Autowired
//	private TestRestTemplate testRestTemplate;
//	
	
	
//	
//	public PricingServiceApplicationTests(TestRestTemplate testRestTemplate) {
//		this.testRestTemplate = testRestTemplate;
//	}

	@Autowired
	private MockMvc mvc;
	
//	private String getAllPricesUrl = "http://localhost:"+port+"/prices";
	int id=1;
	@Test
	public void contextLoads() {
//		Assertions.assertNotNull(testRestTemplate);
	}

	String url;
    @BeforeEach
    public void setUp() {
        url = String.format("http://localhost:%d/", port);
    }
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Test
	public void getPricesUsingMvc() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/prices"))
		.andDo(MockMvcResultHandlers.print())
//	    .andExpect(200);
//	    .andExpect(result -> Assertions.assertEquals("404 NOT_FOUND \"Price Not Found\"; nested exception is com.udacity.pricing.service.PriceException: Cannot find price for Vehicle 200", result.getResolvedException().getMessage()));

		.andExpect(status().isOk());

	}
	
	@Test
	public void getAllPricesUsingTestRestTemplate() throws Exception{
		
		
		ResponseEntity<String> response = this.restTemplate.getForEntity(url+"/prices", String.class);
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
//		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
//		System.out.println(response.getBody());
	}
	
	@Test
	public void getPriceByIdUsingJunit5() throws URISyntaxException{

		RestTemplate restTemplate = new RestTemplate();
		 final String baseUrl = "http://localhost:"+port+"/prices/"+id;
	        URI uri = new URI(baseUrl);
	        

	        ResponseEntity<String> response = restTemplate.
	        		  getForEntity(uri , String.class);
	        
	        String jsonResponce = response.getBody();
	        System.out.println("");
	        System.out.println("#		Testing Json Responce 		#");
	        System.out.println(jsonResponce);
	        
	        
	        
//	        String[] arrOfStr = jsonResponce.split("\"_links\"", 2);
//	        String priceJson= arrOfStr[0];
//	        priceJson.substring(0, priceJson.length() - 1);
//	        priceJson=priceJson+"}";
//	        System.out.println("#######################################");
//	        System.out.println(priceJson);
	        
	        
//	        Price price;
//			try {
//				price = new ObjectMapper().readValue(jsonResponce, Price.class);
//		        System.out.println("#######################################");
//		        System.out.println(price.toString());
//			} catch (JsonProcessingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
	        

	        
//	        Assertions.assertEquals(response.getStatusCode(), equalTo(HttpStatus.OK));
	        Assertions.assertEquals(200, response.getStatusCodeValue());
	        
	        Assertions.assertTrue(jsonResponce.contains("\"currency\" : \"USD\""));
	        Assertions.assertTrue(jsonResponce.contains("\"price\" : 1000.00"));

	
	        
//	        HttpHeaders headers = new HttpHeaders();
//	        headers.set("X-COM-LOCATION", "USA");
	 
//	        HttpEntity<Price> requestEntity = new HttpEntity<>(null, headers);
	        
	        
	 
//	        try
//	        {
//	            restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
//	            Assertions.fail();
//	        }
//	        catch(HttpClientErrorException ex) 
//	        {
//	            //Verify bad request and missing header
//	            Assertions.assertEquals(400, ex.getRawStatusCode());
//	            Assertions.assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
//	        }
	    }
	}


