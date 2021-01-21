import java.util.Arrays;

public class MainApp {

    //1. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
    // Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
    // идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку, иначе в методе
    // необходимо выбросить RuntimeException.
    //Написать набор тестов для этого метода (по 3-4 варианта входных данных).
    //Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
    //
    //2. Написать метод, который проверяет состав массива из чисел 1 и 4.
    // Если в нем нет хоть одной четверки или единицы, то метод вернет false;
    // Написать набор тестов для этого метода (по 3-4 варианта входных данных).
    //[ 1 1 1 4 4 1 4 4 ] -> true
    //[ 1 1 1 1 1 1 ] -> false
    //[ 4 4 4 4 ] -> false
    //[ 1 4 4 1 1 4 3 ] -> false

    public static void main(String[] args) {
        // задание 1
        int [] arr = {2,3,4,1,2,4,7,8,1};
        int[] result = task1(arr);
        System.out.println(Arrays.toString(result));

        //задание 2
        int[]arr2 = {1,4,1,4,1,1,1,4};
        boolean resutl2 = task2(arr2);
        System.out.println(resutl2);

    }

    public static int[] task1 (int[] arr){
        for (int i = (arr.length-1); i > 0; i--) {
            if (arr[i] == 4){
                return Arrays.copyOfRange(arr, (i+1) ,arr.length);
            }
        }
        throw new RuntimeException("no 4");
    }

    public static boolean task2 (int [] arr){
        boolean one = false;
        boolean four = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i]==1){
                one = true;
            } else if (arr[i]==4){
                four = true;
            } else return false;
        }
        return (one&&four);
    }

}
