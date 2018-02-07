package com.fan.testletgo;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 * Created by maobuji on 2018/2/6.
 */
public class App {

    public static void main(String[] args) {

    }

    private static String agentInfo = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36";
    private static String getPostUrl(){
        return "https://pet-chain.baidu.com/data/pet/queryPetById?appId=1&petId=1855388900793785538&requestId=requestId";
    }
    private static Connection getJSoupConnection(int page) {
        Connection con = Jsoup.connect(getPostUrl()).userAgent(agentInfo).timeout(10000);
        con.header("Accept", "application/json");
        con.header("Accept-Encoding", "gzip, deflate, br");
        con.header("Accept-Language", "zh-CN,zh;q=0.9");
        con.header("Connection", "keep-alive");
        con.header("Content-Type", "application/json");
        con.header("Cookie", "BAIDUID=58D9623F9C84B3FF278F79328DC80C98:FG=1; FP_UID=40ecb7309f4b67209d9cb680cc8874fd");
        con.header("Host", "pet-chain.baidu.com");
        con.header("Origin", "https://pet-chain.baidu.com");
        con.header("Referer", "https://pet-chain.baidu.com/chain/detail?channel=market&petId=1855388900793785538");
        con.header("Content-Length","76");

        con.data("page.noticeBean.sourceCH", "");
        return con;
    }
}
