import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientHandler {

    Server server = null;
    Socket socket = null;
    DataInputStream in;
    DataOutputStream out;
    private String nickname;
    private String login;


    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            socket.setSoTimeout(12000);

            new Thread(() -> {
                try {
                    // цикл аутентификации
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/auth")) {
                            String[] token = str.split("\\s");
                            String newNick = server.getAuthService().getNicknameByLoginAndPassword(token[1], token[2]);
                            login = token[1];

                            if (newNick != null) {
                                if(!server.isLoginAuthenticated(token[1])) {
                                    socket.setSoTimeout(0);
                                    nickname = newNick;
                                    sendMsg("/authok " + nickname);
                                    server.subscribe(this);
                                    break;
                                }else{
                                    sendMsg("This user logged already");
                                }
                            } else {
                                sendMsg("Incorrect login or password");
                            }
                        }
                        if (str.startsWith("/reg")){
                            String[] token = str.split("\\s");
                            if(token.length < 4){
                                continue;
                            }
                            boolean isRegistration = server.getAuthService()
                                    .registration(token[1], token[2], token[3]);
                            if(isRegistration){
                                sendMsg("/regok");
                            } else {
                                sendMsg("/regno");
                            }
                        }
                    }

                    // цикл работы
                    while (true) {
                        String str = in.readUTF();

                        if(str.startsWith("/")){
                            if (str.equals("/end")) {
                                out.writeUTF("/end");
                                break;
                            }
                            if (str.startsWith("/w")) {
                                String[] prMsg = str.split("\\s+", 3);
                                if (prMsg.length!=3){
                                    continue;
                                }
                                server.privateMessage(this, prMsg[1], prMsg[2]);
                            }
                            if (str.startsWith("/chnick ")) {
                                String[] token = str.split(" ", 2);
                                if (token.length < 2) {
                                    continue;
                                }
                                if (token[1].contains(" ")) {
                                    sendMsg("Ник не может содержать пробелов");
                                    continue;
                                }
                                if (server.getAuthService().changeNick(this.nickname, token[1])) {
                                    sendMsg("/yournickis " + token[1]);
                                    sendMsg("Ваш ник изменен на " + token[1]);
                                    this.nickname = token[1];
                                    server.broadClientList();
                                } else {
                                    sendMsg("Не удалось изменить ник. Ник " + token[1] + " уже существует");
                                }
                            }
                        } else {
                            server.broadCastMsg(this, str);
                        }
                    }
                } catch (SocketTimeoutException e){
                    System.out.println("time out");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    server.unsubscribe(this);
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNickname() {
        return nickname;
    }

    public String getLogin() {
        return login;
    }
}