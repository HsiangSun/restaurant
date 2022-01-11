package cn.hsiangsun.sunway.controller;

import cn.hsiangsun.sunway.entity.Menu;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@RestController
public class RestaurantController {

    private static final String ONLINE_PASSWORD = "200888";
    private static final String ONLINE_TABLE = "1060";

    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/print")
    public boolean print(@RequestBody List<Menu> menus) {
        String userCookie  = null;
        try {
            for (Menu menu : menus) {
                System.out.println(menu);
            }
//            String url = "http://127.0.0.1/login.php?lang_id=2&logout=1&table_id=&invoice_id=";
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Cookie","mako2__check=check");
//
//            LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//            params.add("mode","1");
//            params.add("password",ONLINE_PASSWORD);
//            params.add("finger_print","");
//
//            HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<MultiValueMap<String,String>>(params,headers);
//            ResponseEntity<String> response = restTemplate.postForEntity(url, httpEntity, String.class);
//            HttpHeaders responseHeads = response.getHeaders();
//
//            List<String> cookies = responseHeads.get("Set-Cookie");
//            String userCookieComplex = cookies.get(1);
//
//            userCookie = userCookieComplex.split(";")[0].split("=")[1];
//
//
//            Document document = null;
//            try {
//                document = Jsoup.connect(String.format("http://127.0.0.1/invoice_profile.php?lang_id=2&table_id=%s",ONLINE_TABLE))
//                        .cookie("mako2__check", "check")
//                        .cookie("mako2__sid", userCookie)
//                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36 Edg/97.0.1072.55")
//                        .timeout(8000).get();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            //get invoice id
//            Element invoice = document.select("input[name=invoice_id]").first();
//            String invoiceID = invoice.val();
//
//            //set user seats
//
//            String mainUrl = String.format("http://127.0.0.1/invoice.php?lang_id=2&invoice_id=%s",invoiceID);
//
//            HttpHeaders headersWithCookie = new HttpHeaders();
//            headersWithCookie.add("Cookie","mako2__check=check");
//            headersWithCookie.add("Cookie",String.format("mako2__sid=%s",userCookie));
//
//            LinkedMultiValueMap<String, String> seatsParams = new LinkedMultiValueMap<>();
//            seatsParams.add("selected_id","0");
//            seatsParams.add("seats","1");//!!!!!!!!!!!!!!!!!!!!user count
//            seatsParams.add("type_id","");
//            seatsParams.add("submit_type","change_seats");
//            seatsParams.add("scroll","0");
//            seatsParams.add("sel_seat","1");
//            seatsParams.add("sel_course_id","-1");
//            seatsParams.add("multi_mode","0");
//            seatsParams.add("sent_minimized","0");
//
//            HttpEntity<MultiValueMap<String,String>> httpEntity2 = new HttpEntity<MultiValueMap<String,String>>(seatsParams,headersWithCookie);
//            restTemplate.postForEntity(mainUrl, httpEntity2, String.class);
//
//            //add menu to order
//            for (Menu menu : menus) {
//                try {
//                    addMenuToOrder(mainUrl,headersWithCookie,menu.getQuantity(),menu.getId(),menu.getMod());
//                } catch (Exception e) {
//                    throw new Exception("When for add menu has error");
//                }
//            }
//
//            //submit order
//            String sendOrderUrl = String.format("http://127.0.0.1/invoice_profile.php?lang_id=2&invoice_id=%s",invoiceID);
//            LinkedMultiValueMap<String, String> sendOrderParams = new LinkedMultiValueMap<>();
//            sendOrderParams.add("invoice_id",invoiceID);
//            sendOrderParams.add("time",String.valueOf(System.currentTimeMillis()));
//            sendOrderParams.add("course_id","0");
//            sendOrderParams.add("submit_type","send");
//            sendOrderParams.add("_","");
//
//            HttpEntity<MultiValueMap<String,String>> httpEntity4 = new HttpEntity<MultiValueMap<String,String>>(sendOrderParams,headersWithCookie);
//            restTemplate.postForEntity(sendOrderUrl, httpEntity4, String.class);
//
//            String checkOrderUrl = String.format("http://127.0.0.1/index.php?lang_id=2&&table_id=%s&unlock=%s",ONLINE_TABLE,invoiceID);
//            restTemplate.exchange(checkOrderUrl,
//                    HttpMethod.GET,
//                    new HttpEntity(headersWithCookie), String.class);
            return false;
        } catch (Exception e) {
           return false;
        }
    }

    private Integer orderCount(String userCookie,String invoiceId){
        String checkUrl = String.format( "http://127.0.0.1/index.php?lang_id=2&&table_id=1060&unlock=%s",invoiceId);

        Document document = null;
        try {
            document = Jsoup.connect(checkUrl)
                    .cookie("mako2__check", "check")
                    .cookie("mako2__sid", userCookie)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/97.0.4692.71 Safari/537.36 Edg/97.0.1072.55")
                    .data("table_id",ONLINE_TABLE)
                    .data("submit_type","display_table_info")
                    .data("_","")
                    .timeout(8000)
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element infoBody = document.select(".more_info_tbls > td").last();
        String count = infoBody.text();
        return Integer.valueOf(count);
    }

    private void addMenuToOrder(String mainUrl,HttpHeaders headersWithCookie,String quantity,String product_id,String mod_key) throws Exception {
        try {
            LinkedMultiValueMap<String, String> orderParams = new LinkedMultiValueMap<>();
            orderParams.add("quantity",quantity);//TODO!!!!!!!!!!!!!!!!!!!
            orderParams.add("product_id",product_id);//TODO!!!!!!!!!!!!!!!!!!!
            orderParams.add("parent_sales_id","0");
            orderParams.add("selected_id","0");
            orderParams.add("group_id","0");
            orderParams.add("submit_type","add");//TODO
            orderParams.add("scroll","0");
            orderParams.add("sel_seat","1");
            orderParams.add("sel_course_id","-1");
            orderParams.add("swap_id","");
            orderParams.add("scale_data","");
            orderParams.add("weight","");
            orderParams.add("size_id","1");
            orderParams.add("mod_key",mod_key);//TODO kou wei :1013:
            orderParams.add("notes","");
            orderParams.add("name","");
            orderParams.add("combo_qty","");
            orderParams.add("combo_price","");
            orderParams.add("multi_mode","0");
            orderParams.add("sent_minimized","0");

            HttpEntity<MultiValueMap<String,String>> httpEntity3 = new HttpEntity<MultiValueMap<String,String>>(orderParams,headersWithCookie);
            restTemplate.postForEntity(mainUrl, httpEntity3, String.class);
        } catch (RestClientException e) {
            throw new Exception("Method addMenuToOrder throw error");
        }
    }

}
