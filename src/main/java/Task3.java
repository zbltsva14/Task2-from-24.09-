import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Task3 {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Иванов И.И.", 25, "IT", 80055.0));
        employees.add(new Employee("Петров П.П.", 35, "HR", 60234.4));
        employees.add(new Employee("Сидоров С.С.", 42, "IT", 120300.66));
        employees.add(new Employee("Кузнецова А.А.", 29, "Finance", 95110.58));
        employees.add(new Employee("Смирнов В.В.", 50, "IT", 150000.09));
        System.out.println("Исходный список:");
        employees.forEach(System.out::println);
        System.out.println("\nСотрудники, отсортированные по возрастанию зарплаты:");
        employees.stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary))
                .forEach(System.out::println);
    }
}
