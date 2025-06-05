package Trabalho.Descolar.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MySQLConnectionFactory implements ConnectionFactory {
    
    @Value("${spring.datasource.url}") 
    private String url; // jdbc:mysql://localhost:3306/descolar_db
    
    @Override
    public Connection getConnection() throws SQLException {
        // Conexão sem senha
        return DriverManager.getConnection(url, "root", "");
    }
}