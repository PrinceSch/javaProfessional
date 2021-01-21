import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Start {

    private static final Logger log = Logger.getLogger("");

    public static void main(String[] args) {
        try{
            Handler handler = new FileHandler("log%g.txt",10*1024,10,true);
            handler.setFormatter(new SimpleFormatter());
            log.addHandler(handler);
        } catch (IOException e){
            e.printStackTrace();
        }
        new Server();
    }
}
