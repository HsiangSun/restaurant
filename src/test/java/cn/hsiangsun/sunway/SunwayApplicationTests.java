package cn.hsiangsun.sunway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@SpringBootTest
class SunwayApplicationTests {


	@Resource
	private RestTemplate restTemplate;

	@Test
	void contextLoads() {
	}


	@Test
	public void test_get(){
//		String userCookie = "11882458961dade0fe6d603059714723";
//		HttpHeaders headersWithCookie = new HttpHeaders();
//		headersWithCookie.add("Cookie","mako2__check=check");
//		headersWithCookie.add("Cookie",String.format("mako2__sid=%s",userCookie));
//		String sendOrderUrl = String.format("http://127.0.0.1/index.php?lang_id=2&&table_id=%s&unlock=%s",1060,100104);
//		restTemplate.exchange(sendOrderUrl,
//				HttpMethod.GET,
//				new HttpEntity(headersWithCookie), String.class);
	}

}
