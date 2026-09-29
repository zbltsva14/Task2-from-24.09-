import java.util.Random;
import java.util.Scanner;

/**
 * Пусть массив заполняется случ. значениями из диапазона [-100;100].
 */
public class Task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество элементов n: ");
        int n = scanner.nextInt();
        if (n <= 0) {
            System.out.println("Массив пуст, среднего значения нет.");
            return;
        }
        int[] arr = new int[n];
        Random random = new Random();
        long sum = 0;
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(201) - 100;
            sum += arr[i];
        }
        System.out.print("Массив: ");
        for (int v : arr) System.out.print(v + " ");
        double average = (double) sum / n;
        System.out.println("Среднее значение: " + average);
    }
}
