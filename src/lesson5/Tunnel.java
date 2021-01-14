package lesson5;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

    Semaphore tunnel;

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        tunnel = new Semaphore(MainApp.CARS_COUNT / 2);
    }
    @Override
    public void go(Car c) {
        try {
            if (!tunnel.tryAcquire()) {
                System.out.println(c.getName() + " готовится к этапу: " + description);
                tunnel.acquire();
            }
            System.out.println(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(c.getName() + " закончил этап: " + description);
            tunnel.release();
        }
    }
}
