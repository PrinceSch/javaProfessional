import java.sql.*;

public class SimpleAuthService implements AuthService {
    private static Connection connection;
    private static Statement stmt;
    private static PreparedStatement psInsert;

//    private class UserData {
//        String login;
//        String password;
//        String nickname;
//
//        public UserData(String login, String password, String nickname) {
//            this.login = login;
//            this.password = password;
//            this.nickname = nickname;
//        }
//    }


    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        try {
            connect();
            ResultSet rs = stmt.executeQuery("SELECT nickname FROM users WHERE login = '"+login+
            "' AND password = '"+password+"';");
            return rs.getString("nickname");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            disconnect();
        }
        return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        try {
            psInsert = connection.prepareStatement("INSERT INTO users (login, password, nickname)" +
                    "VALUES (? , ? , ?);");
            psInsert.setString(1,login);
            psInsert.setString(2,password);
            psInsert.setString(3,nickname);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                psInsert.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:users.db");
        stmt = connection.createStatement();
    }

    private static void disconnect(){
        try {
            stmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}