package operations.implementations;

import exceptions.InsufficientFundException;
import exceptions.OutOfStockException;
import exceptions.StockDoesNotExistException;
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
    public void purchaseGoodsInCart(Customer customer) throws InsufficientFundException{
        if (customer.getTotalGoodsPrice() > customer.getWallet()) throw new InsufficientFundException("Customer doesn't have sufficient fund!");
        else customer.setCheckOut(true);
    }


    @Override
    public void fundWallet(Customer customer, double amount){customer.setWallet(amount + customer.getWallet());}
}
