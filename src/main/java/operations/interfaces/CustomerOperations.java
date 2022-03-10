package operations.interfaces;

import exceptions.InsufficientFundException;
import exceptions.OutOfStockException;
import exceptions.StockDoesNotExistException;
import models.Customer;
import models.Store;

public interface CustomerOperations extends  CommonOperations {
     void addProductToCart(Store company, Customer customer, String productID, int quantity) throws OutOfStockException, StockDoesNotExistException;
     void joinQueue(Store store, Customer customer) throws InsufficientFundException;
     void fundWallet(Customer customer, double amount);
}
