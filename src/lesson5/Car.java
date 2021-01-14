package lesson5;

import java.util.concurrent.atomic.AtomicInteger;

public class Car implements Runnable {
    static AtomicInteger winner = new AtomicInteger(0);
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            MainApp.cbReady.await();
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        if (winner.incrementAndGet()==1){
            System.out.println(name + " WINS!!!");
        }
        MainApp.cbEnd.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
