package operations.implementations;

import enums.*;
import exceptions.*;
import models.Store;
import models.Customer;
import models.Staff;
import operations.interfaces.AdministrativeOperations;
import operations.interfaces.CustomerOperations;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomerOperationsImplTest {
    AdministrativeOperations administrativeOperations = new AdministrativeOperationsImpl();
    CustomerOperations customerOperations = new CustomerOperationsImpl();
    Store store = new Store("Damzxyno Food Store");
    Staff manager = new Staff("Wilfred", "Omokpo", Gender.MALE, Designation.MANAGER);
    Customer customer1 = new Customer("Tolani", "Oluwasegun", Gender.MALE);
    String excelFilePath = "src/main/resources/excel_files/damzxyno_food_store.xlsx";

    @Before
    public void setUp() throws NotAuthorizedException {
        administrativeOperations.loadProductsFromExcelFile(store, manager, excelFilePath);
    }


    @Test
    public void customerCartShouldContainProductIDAdded() throws OutOfStockException, StockDoesNotExistException {
        customerOperations.addProductToCart(store, customer1, "DAMFS-CER-001", 3);
        assertTrue(customer1.getCart().containsKey("DAMFS-CER-001"));
    }

    @Test
    public void customerCartShouldContainQuantityAddedToCart() throws OutOfStockException, StockDoesNotExistException {
        customerOperations.addProductToCart(store, customer1, "DAMFS-CER-002", 3);
        assertEquals(3, (int) customer1.getCart().get("DAMFS-CER-002"));
    }

    @Test
    public void shouldThrowOutOfStockExceptionIfCompanyDoesntHaveEnoughGoods() {
        assertThrows(OutOfStockException.class, ()->customerOperations.addProductToCart(store, customer1, "DAMFS-BEV-004", 30));
    }

    @Test
    public void shouldThrowOutOfStockExceptionWhenGoodsIsFinishedOrZero() throws OutOfStockException, StockDoesNotExistException, InsufficientFundException, InvalidOperationException, NotAuthorizedException {
        Customer customer2 = new Customer("Oluwaseun", "Idris", Gender.MALE);
        Staff cashier = new Staff("Mope", "Oke", Gender.FEMALE, Designation.CASHIER);

        customerOperations.addProductToCart(store, customer1, "DAMFS-BEV-004", 20);
        customerOperations.fundWallet(customer1, 70_000);
        customerOperations.purchaseGoodsInCart(customer1);
        administrativeOperations.sellProductsInCart(store, cashier, customer1);

        assertThrows(OutOfStockException.class, ()->
                customerOperations.addProductToCart(store, customer2, "DAMFS-BEV-004", 1));
    }

    @Test
    public  void shouldThrowStockDoesNotExistExceptionWhenUnknownProductIsAddedToCArt(){
        assertThrows(StockDoesNotExistException.class, ()->customerOperations.addProductToCart(store, customer1, "DAMFS-BEV-112", 6));
    }

    @Test
    public void shouldCheckOutCustomer() throws InsufficientFundException {
        customerOperations.purchaseGoodsInCart(customer1);
        assertTrue(customer1.isCheckOut());
    }


    @Test
    public void customerAccountBalanceShouldIncrease(){
        customerOperations.fundWallet(customer1, 500.0);
        assertEquals(500.0, customer1.getWallet(), 0);
        customerOperations.fundWallet(customer1, 600);
        assertEquals(1100.0, customer1.getWallet(), 0);
    }

    @Test
    public void customerCanSeeGoodsByCategory(){
        assertEquals( 4, customerOperations.viewProductByCategory(store, "Beverages").size());
        assertEquals( "Beverages", administrativeOperations.viewProductByCategory(store, "Beverages").get(0).getProductCategory());
        assertEquals( "Beverages", administrativeOperations.viewProductByCategory(store, "Beverages").get(1).getProductCategory());
        assertEquals( "Beverages", administrativeOperations.viewProductByCategory(store, "Beverages").get(2).getProductCategory());
        assertEquals( "Beverages", administrativeOperations.viewProductByCategory(store, "Beverages").get(3).getProductCategory());
    }
}
