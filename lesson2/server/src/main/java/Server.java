import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

public class Server {

    private static int PORT = 8189;
    ServerSocket server = null;
    Socket socket = null;
    List<ClientHandler> clients;
    private AuthService authService;

    public Server() {
        clients = new Vector<>();
        authService = new SimpleAuthService();

        try {
            server = new ServerSocket(PORT);
            System.out.println("Server launched");

            while (true) {
                socket = server.accept();
                System.out.println("User connected");
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void broadCastMsg(ClientHandler sender, String msg) {
        SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
        String message = String.format("%s %s : %s", date.format(new Date()), sender.getNickname(), msg);
        for (ClientHandler client : clients) {
            client.sendMsg(message + "\n");
        }
    }

    public void subscribe(ClientHandler clientHandler){
        clients.add(clientHandler);
        broadClientList();
    }

    public void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
        broadClientList();
    }

    public AuthService getAuthService(){
        return authService;
    }

    void privateMessage (ClientHandler sender, String nick, String msg){
        SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
        String message = String.format("%s From %s to %s: %s", date.format(new Date()), sender.getNickname(), nick, msg);
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(nick)){
                client.sendMsg(message + "\n");
                if(!client.equals(sender)){
                    sender.sendMsg(message + "\n");
                }
                return;
            }
        }
    }

    public boolean isLoginAuthenticated(String login){
        for (ClientHandler c: clients) {
            if(c.getLogin().equals(login)){
                return true;
            }
        }
        return false;
    }

    public void broadClientList() {
        StringBuilder sb = new StringBuilder("/clientList ");
        for(ClientHandler c: clients){
            sb.append(c.getNickname()).append(" ");
        }
        String msg = sb.toString();
        for (ClientHandler c: clients){
            c.sendMsg(msg);
        }
    }
}
