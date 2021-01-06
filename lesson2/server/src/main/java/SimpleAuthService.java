import java.sql.*;

public class SimpleAuthService implements AuthService {
    private static Connection connection;
    private static PreparedStatement psGetNickname;
    private static PreparedStatement psRegistration;
    private static PreparedStatement psChangeNick;


    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        String nick = null;
        try {
            psGetNickname.setString(1, login);
            psGetNickname.setString(2, password);
            ResultSet rs = psGetNickname.executeQuery();
            if (rs.next()) {
                nick = rs.getString(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nick;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        try {
            psRegistration.setString(1, login);
            psRegistration.setString(2, password);
            psRegistration.setString(3, nickname);
            psRegistration.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean changeNick(String oldNick, String newNick) {
        try {
            psChangeNick.setString(1, newNick);
            psChangeNick.setString(2, oldNick);
            psChangeNick.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:users.db");
            prepareAllStatements();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void prepareAllStatements() throws SQLException {
        psGetNickname = connection.prepareStatement("SELECT nickname FROM users WHERE login = ? AND password = ?;");
        psRegistration = connection.prepareStatement("INSERT INTO users(login, password, nickname) VALUES (? ,? ,? );");
        psChangeNick = connection.prepareStatement("UPDATE users SET nickname = ? WHERE nickname = ?;");
    }

    public static void disconnect(){
        try {
            psGetNickname.close();
            psChangeNick.close();
            psRegistration.close();
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