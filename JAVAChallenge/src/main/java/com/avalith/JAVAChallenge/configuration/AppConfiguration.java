package com.avalith.JAVAChallenge.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource(value="classpath:application.properties")
public class AppConfiguration {

    @Primary
    @Bean("connection1")
    public Connection getConnection(@Value("${db.url}") String url,
                                    @Value("${db.name") String dbname,
                                    @Value("${db.user") String user,
                                    @Value("${db.password") String password) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost/javachallenge","root", "");
    }

}
