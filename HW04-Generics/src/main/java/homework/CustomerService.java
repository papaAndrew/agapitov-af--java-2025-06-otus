package homework;

import java.util.*;

public class CustomerService {

    private final TreeMap<Customer, String> customerData = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> higherEntry = customerData.firstEntry();
        if (higherEntry == null) return null;

        return new AbstractMap.SimpleEntry<>(Customer.clone(higherEntry.getKey()), higherEntry.getValue());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> higherEntry = customerData.higherEntry(customer);
        if (higherEntry == null) return null;

        return new AbstractMap.SimpleEntry<>(Customer.clone(higherEntry.getKey()), higherEntry.getValue());
    }

    public void add(Customer customer, String data) {
        customerData.put(customer, data);
    }
}
