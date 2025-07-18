package homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder extends CustomerService {

    private final Deque<Customer> customers = new ArrayDeque<>();

    public void add(Customer customer) {
        customers.add(customer);
    }

    public Customer take() {
        return customers.pollLast();
    }
}
