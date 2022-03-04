package models;

import lombok.*;

import java.util.*;

@RequiredArgsConstructor
@Setter
@Getter
@ToString

public class Store {
    private final String storeName;
    private double storeAccount = 0.0;
    private List <Staff> staffList = new ArrayList<>();
    private List <Applicant> applicantList = new ArrayList<>();
    private Storage goods = new Storage();
    private List<Product> productList;
    private CustomPriorityQueue cartListQueue = new CustomPriorityQueue();
   // private PriorityQueue<Customer> cartListQueue = new PriorityQueue<>();
}
