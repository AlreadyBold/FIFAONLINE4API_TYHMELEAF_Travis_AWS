package com.fifatoy.controller;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class DBconn {

    /*
     * @Value("${spring.datasource.driver-class-name}")
     * private static String driver;
     * 
     * @Value("${spring.datasource.username}")
     * private static String username;
     * 
     * @Value("${spring.datasource.password}")
     * private static String password;
     */

    public static void main(String[] args) {
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ConfigurableEnvironment env = ctx.getEnvironment();
        String driver = env.getProperty("spring.datasource.driver-class-name"); // "oracle.jdbc.OracleDriver";
        String url = env.getProperty("spring.datasource.url");// "jdbc:oracle:thin:@ZY0AG10DCLGRPHWU_high?TNS_ADMIN=/Users/user/Downloads/OCIWallet";
        String username = env.getProperty("spring.datasource.username"); // "admin";
        String password = env.getProperty("spring.datasource.password"); // "Dlwnsgud!1@2";

        // driver = env.getProperty("spring.datasource.driver-class-name");
        System.out.println("driver = " + driver);
        System.out.println("username = " + username);
        System.out.println("password = " + password);

        try {
            // driver 로딩
            Class.forName(driver);
            System.out.println("jdbc driver 로딩 성공");
            // DB와 연결
            DriverManager.getConnection(driver, username, password);
            System.out.println("오라클 연결 성공");
        } catch (ClassNotFoundException e) {
            System.out.println("jdbc driver 로딩 실패");
        } catch (SQLException e) {
            System.out.println("오라클 연결 실패");
        }

    }
}
