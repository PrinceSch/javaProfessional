import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class History {

    File chatHistory;
    FileInputStream chIn;
    FileOutputStream chOut;
    PrintWriter fileWriter;
    BufferedWriter bf;
    BufferedReader br;

    public void writeHistory (String login, String msg){
        chatHistory = new File ("server/src/main/resources/history_"+login+".txt");
        if(!chatHistory.exists()){
            try {
                chatHistory.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            chOut = new FileOutputStream(chatHistory, true);
            fileWriter = new PrintWriter(chOut);
            bf = new BufferedWriter(fileWriter);
            bf.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bf.close();
                fileWriter.close();
                chOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> readHistory (String login){
        List<String> historyList = new ArrayList<>();
        chatHistory = new File ("server/src/main/resources/history_"+login+".txt");
        if(chatHistory.exists()){
            try {
                chIn = new FileInputStream(chatHistory);
                br = new BufferedReader(new InputStreamReader(chIn));
                while (br.readLine() != null){
                    historyList.add(br.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                    chIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return historyList;
        }
        return null;
    }

}
