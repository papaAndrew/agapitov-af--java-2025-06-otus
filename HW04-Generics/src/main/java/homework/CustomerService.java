package homework;

import java.util.*;

// @SuppressWarnings({"java:S1186", "java:S1135", "java:S1172"}) // при выполнении ДЗ эту аннотацию надо удалить

public class CustomerService {

    TreeMap<Customer, String> customerData = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    // DONE
    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        // Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk

        return customerData.firstEntry();
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        // a key-value mapping associated with the least key strictly greater than the given key, or
        // null if there is no such key.

        Map.Entry<Customer, String> higherEntry = customerData.higherEntry(customer);
        if (higherEntry == null) return null;

        return new AbstractMap.SimpleEntry<>(Customer.clone(higherEntry.getKey()), higherEntry.getValue());
    }

    public void add(Customer customer, String data) {
        customerData.put(customer, data);
    }
}
