import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Task2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество элементов n: ");
        int n = scanner.nextInt();
        ArrayList<Double> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < n; i++) {
            list.add(Math.round((random.nextDouble() * 200 - 100) * 100.0) / 100.0);
        }
        System.out.println("Исходный список: " + list);
        bubbleSort(list);
        System.out.println("Отсортированный список: " + list);
    }

    private static void bubbleSort(ArrayList<Double> list) {
        int size = list.size();
        for (int i = 0; i < size - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < size - 1 - i; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    double temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }
}