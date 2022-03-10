package models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    private String productID;
    private String productName;
    private String productCategory;
    private double productPrice;
    private AtomicInteger productQuantity;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock writeLock = lock.writeLock();
    private Lock readLock = lock.readLock();

    public Product(String productID, String productName, String productCategory, double productPrice, int productQuantity) {
        this.productID = productID;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productPrice = productPrice;
        this.productQuantity = new AtomicInteger(productQuantity);
    }

    public int getProductQuantity() {
        try {
            readLock.lock();
            return productQuantity.get();
        }finally {
            readLock.unlock();
        }
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity.set(productQuantity);
    }
}
