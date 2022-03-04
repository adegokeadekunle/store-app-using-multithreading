package operations.implementations;

import exceptions.InsufficientFundException;
import exceptions.OutOfStockException;
import exceptions.StockDoesNotExistException;
import models.Product;
import models.Store;
import models.Customer;
import operations.interfaces.CustomerOperations;

public class CustomerOperationsImpl implements CustomerOperations {
    @Override
    public void addProductToCart(Store company, Customer customer, String productID, int quantity) throws OutOfStockException, StockDoesNotExistException {
            if (!company.getGoods().contains(productID)) throw new StockDoesNotExistException("Company doesn't sell desired product");
            if (company.getGoods().get(productID).getProductQuantity() < quantity) throw new OutOfStockException("Company has limited quantity of desired product!");
            customer.getCart().merge(productID, quantity, Integer::sum);
            customer.setTotalGoodsPrice(company.getGoods().get(productID).getProductPrice() * quantity + customer.getTotalGoodsPrice());
    }

    @Override
    public void joinQueue(Store store, Customer customer){
        if (!customer.getCart().isEmpty()) {
            store.getCartListQueue().add(customer);
        }
    }

    @Override
    public void fundWallet(Customer customer, double amount){customer.setWallet(amount + customer.getWallet());}
}
