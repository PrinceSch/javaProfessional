package lesson4;

public class Main {

    //1. Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз
    // (порядок – ABСABСABС). Используйте wait/notify/notifyAll.

    static Object monitor = new Object();
    static volatile char letter = 'A';
    static final int count = 5;

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                for (int i = 0; i < count; i++) {
                    synchronized (monitor){
                        while (letter != 'A'){
                            monitor.wait();
                        }
                        System.out.print(letter);
                        letter = 'B';
                        monitor.notifyAll();
                    }
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < count; i++) {
                    synchronized (monitor){
                        while (letter != 'B'){
                            monitor.wait();
                        }
                        System.out.print(letter);
                        letter = 'C';
                        monitor.notifyAll();
                    }
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                for (int i = 0; i < count; i++) {
                    synchronized (monitor){
                        while (letter != 'C'){
                            monitor.wait();
                        }
                        System.out.print(letter);
                        letter = 'A';
                        monitor.notifyAll();
                    }
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }).start();
    }

}
