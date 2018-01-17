package com.fan.mysqlconnector.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Administrator on 2018/1/17.
 */
public class MysqlConnMain {

    public static void main(String[] args) {

        // 驱动名称
        String driver = "com.mysql.jdbc.Driver";
        // URL指向要访问的数据库名scutcs
        String url = "jdbc:mysql://172.18.100.62:4000/mysql";
        // MySQL配置时的用户名
        String user = "root";
        // Java连接MySQL配置时的密码
        String password = "";

        // 执行
        String sql = "select * from user";
        try {
            // 加载驱动程序
            Class.forName(driver);
            // 连续数据库
            Connection conn = DriverManager.getConnection(url, user, password);
            if (!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            // statement用来执行SQL语句
            Statement statement = conn.createStatement();
            // 要执行的SQL语句


            ResultSet rs = statement.executeQuery(sql);

            System.out.println("执行sql语句");
            while (rs.next()) {
                System.out.println(rs.getObject(1));
            }

        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }
}
