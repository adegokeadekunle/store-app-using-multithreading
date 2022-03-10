package models;

import lombok.*;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
    private Queue<Customer> customerList = new ConcurrentLinkedQueue<>();
    private CustomPriorityQueue cartListQueue = new CustomPriorityQueue();
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock writeLock = lock.writeLock();
    private Lock readLock = lock.readLock();
   // private PriorityQueue<Customer> cartListQueue = new PriorityQueue<>();

}
