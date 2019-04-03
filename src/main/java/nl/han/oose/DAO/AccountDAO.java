package nl.han.oose.DAO;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import nl.han.oose.ConnectionFactory;
import org.bson.Document;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.IntStream;


public class AccountDAO {

    private ConnectionFactory connectionFactory;

    public AccountDAO() {
        connectionFactory = new ConnectionFactory();
    }

//    public boolean accountValidation(String username, String password) {
//        try (Connection connection = connectionFactory.getConnection();
//             PreparedStatement statement = connection.prepareStatement("SELECT * FROM account WHERE username = ? AND password = ?")) {
//            statement.setString(1, username);
//            statement.setString(2, password);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                return true;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return false;
//    }
        public boolean accountValidation(String username, String password) {
                MongoDatabase database = connectionFactory.getDatabase();
                MongoCollection<Document> collection = database.getCollection("account");
                BasicDBObject query = new BasicDBObject();
                query.put("username", username);
                query.put("password", password);
                MongoCursor<Document> cursor = collection.find(query).iterator();
                while(cursor.hasNext()){
                    return true;
                }
            return false;
        }
}
