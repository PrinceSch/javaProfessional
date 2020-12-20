package lesson1;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        //1. Написать метод, который меняет два элемента массива местами
        // (массив может быть любого ссылочного типа);
        Integer array1[] = {1, 2, 3, 4, 5, 6, 7};

        changePlace(array1, 2, 5);

        //2. Написать метод, который преобразует массив в ArrayList;
        String[] array2 = {"A", "B", "C", "D"};
        asList(array2);

        //Задание 3
        Box<Orange> orange1 = new Box<>(new Orange(), new Orange(), new Orange(), new Orange(), new Orange(),
                new Orange(), new Orange(), new Orange(), new Orange(), new Orange(), new Orange(), new Orange());
        Box<Orange> orange2 = new Box<>();
        Box<Apple> apple1 = new Box<>(new Apple(), new Apple(), new Apple(), new Apple(), new Apple());
        Box<Apple> apple2 = new Box<>();
        orange2.addFruit(new Orange(), 12);
        apple2.addFruit(new Apple(), 18);
        System.out.println("Коробка 1: " + orange1.getWeight());
        System.out.println("Коробка 3: " + apple1.getWeight());
        System.out.println("Сравниваем коробки 1 и 3: " + orange1.compareBox(apple1));
        System.out.println("Сравниваем коробки 2 и 4: : " + orange2.compareBox(apple2));
        orange1.pourTo(orange2);
        apple1.pourTo(apple2);
        System.out.println("Коробка 1: " + orange1.getWeight());
        System.out.println("Коробка 2: " + orange2.getWeight());
        System.out.println("Коробка 3: " + apple1.getWeight());
        System.out.println("Коробка 4: " + apple2.getWeight());

    }

    // метод для задачи 1
    public static void changePlace(Object[] arr, int i, int j) {
        System.out.println("Массив: " + Arrays.toString(arr));
        Object change = arr[i];
        arr[i] = arr[j];
        arr[j] = change;
        System.out.println("Перестановка: " + Arrays.toString(arr));
    }

    //метод для задачи 2
    public static <T> void asList(T[] arr) {
        ArrayList<T> alt = new ArrayList<>(Arrays.asList(arr));
    }

}
