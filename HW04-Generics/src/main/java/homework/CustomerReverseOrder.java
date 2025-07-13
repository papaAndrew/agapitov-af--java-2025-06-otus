package homework;

import java.util.ArrayList;
import java.util.List;

public class CustomerReverseOrder extends CustomerService {

    private final List<Customer> customers = new ArrayList<>();

    private int currentIndex = -1;

    public void add(Customer customer) {
        customers.add(customer);
        currentIndex++;
    }

    public Customer take() {
        return currentIndex < 0 ? null : customers.get(currentIndex--);
    }
}
