package nl.han.oose.DAO;

import nl.han.oose.ConnectionFactory;
import nl.han.oose.objects.Token;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class TokenDAO {

        @Inject
        private ConnectionFactory connectionFactory;

        public Token createNewToken(String username) {
            Token userToken;
            try (Connection connection = connectionFactory.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "INSERT INTO token (token, username, expire_date) VALUES (?,?,?)")) {
                Random random = new Random();
                String token = String.format("%04d", random.nextInt(10000));
                for(int i = 0; i < 2; i++){
                    token += "-"+String.format("%04d", random.nextInt(10000));
                }
                LocalDateTime dateWhenExpired = LocalDateTime.now().plusHours(24);

                preparedStatement.setString(1, token);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, dateWhenExpired.toString());
                preparedStatement.execute();

                userToken = new Token(token, username);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return userToken;
        }


        public boolean tokenValidation(Token userToken) {
            boolean isValid = false;
            try (
                    Connection connection = connectionFactory.getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(
                            "SELECT * FROM token WHERE token = ? AND username = ?");
            ) {
                preparedStatement.setString(1, userToken.getToken());
                preparedStatement.setString(2, userToken.getUsername());
                ResultSet resultSet = preparedStatement.executeQuery();
                LocalDateTime currentDateTime = LocalDateTime.now();
                while (resultSet.next()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime expiryDate = LocalDateTime.parse(resultSet.getString("expire_date"), formatter);
                    if (expiryDate.isAfter(currentDateTime)) {
                        isValid = true;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return isValid;
        }

        public Token getToken(String token) {
            Token userToken = null;

            try (
                    Connection connection = connectionFactory.getConnection();
                    PreparedStatement getTokenStatement = connection.prepareStatement("SELECT * FROM token WHERE token = ?")
            ) {
                getTokenStatement.setString(1, token);
                ResultSet resultSet = getTokenStatement.executeQuery();
                while (resultSet.next()) {
                    String tokenString = resultSet.getString("token");
                    String user = resultSet.getString("username");
                    userToken = new Token(tokenString, user);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return userToken;
        }
    }
