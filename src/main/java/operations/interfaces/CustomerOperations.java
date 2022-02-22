package operations.interfaces;

import exceptions.InsufficientFundException;
import exceptions.OutOfStockException;
import exceptions.StockDoesNotExistException;
import models.Store;
import models.Customer;

public interface CustomerOperations extends  CommonOperations {
     void addProductToCart(Store company, Customer customer, String productID, int quantity) throws OutOfStockException, StockDoesNotExistException;
     void purchaseGoodsInCart(Customer customer) throws InsufficientFundException;
     void fundWallet(Customer customer, double amount);
}
