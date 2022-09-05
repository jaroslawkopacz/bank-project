package org.bestbank;

import org.bestbank.Client;
import org.bestbank.ClientRepository;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class JDBCClientRepository implements ClientRepository {

    public static final String USER = "postgres";
    public static final String PASSWORD = "qwerty";
    public static final String JDBC_URL = "jdbc:postgresql://127.0.0.1:5433/test";

    @Override
    public void save(Client client) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            String name = client.getName();
            String email = client.getEmail();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users(first_name, mail) values(?,?)");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Client findByEmail(String email) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            PreparedStatement preparedStatement = connection.prepareStatement("select first_name, mail from users where mail=?");
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String name = resultSet.getString("first_name");
                String mail = resultSet.getString("mail");
                return new Client(name, mail, null);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
