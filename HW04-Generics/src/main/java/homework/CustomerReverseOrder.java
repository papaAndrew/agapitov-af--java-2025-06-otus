package homework;

// @SuppressWarnings({"java:S1186", "java:S1135", "java:S1172"}) // при выполнении ДЗ эту аннотацию надо удалить

import java.util.ArrayList;
import java.util.List;

public class CustomerReverseOrder extends CustomerService {

    private final List<Customer> customers = new ArrayList<>();

    private int currentIndex = -1;

    // Done
    // надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    public void add(Customer customer) {
        customers.add(customer);
        currentIndex++;
    }

    public Customer take() {
        return currentIndex < 0 ? null : customers.get(currentIndex--);
    }
}
