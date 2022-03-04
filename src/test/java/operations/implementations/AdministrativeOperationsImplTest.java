package operations.implementations;

import enums.Designation;
import enums.Gender;
import exceptions.*;
import models.*;
import operations.interfaces.AdministrativeOperations;
import operations.interfaces.CustomerOperations;
import org.junit.Test;

import static org.junit.Assert.*;

public class AdministrativeOperationsImplTest {
    AdministrativeOperations administrativeOperations = new AdministrativeOperationsImpl();
    CustomerOperations customerOperations = new CustomerOperationsImpl();
    Store store = new Store("Damzxyno Food Store");
    Staff cashier = new Staff("Mary", "Ololade", Gender.FEMALE, Designation.CASHIER);
    Staff manager = new Staff("Wilfred", "Omokpo", Gender.MALE, Designation.MANAGER);
    Customer customer = new Customer("Sade", "Oluwaseyi", Gender.FEMALE);
    Customer customer2 = new Customer("Tola", "Samuel", Gender.MALE);
    Customer customer3 = new Customer("Steve", "Mathew", Gender.MALE);
    Customer customer4 = new Customer("Emeka", "Chibuzor", Gender.MALE);
    String excelFilePath = "src/main/resources/excel_files/decagon_Ent_products.xlsx";

    @Test
    public void testForUnAuthorizedLoadingOfProductFromExcelFile() {
        Exception exception = assertThrows(NotAuthorizedException.class,
                ()->administrativeOperations.loadProductsFromExcelFile(store, cashier,excelFilePath));
        assertEquals("Only Manager can load product into store!", exception.getMessage());
    }

    @Test
    public void companyStorageShouldContainTheQuantityOfGoodsFromExcelFileAfterSuccessfulLoading() throws NotAuthorizedException {
        administrativeOperations.loadProductsFromExcelFile(store, manager, excelFilePath);
        assertEquals(16, store.getGoods().size());
    }


    @Test
    public void testForUnAuthorizedPersonSellingToCustomer() throws NotAuthorizedException, OutOfStockException, StockDoesNotExistException{
        administrativeOperations.loadProductsFromExcelFile(store, manager, excelFilePath);
        customerOperations.addProductToCart(store, customer, "DAMFS-CSP-006", 9);

        assertThrows(NotAuthorizedException.class, ()-> administrativeOperations.sellProductsInCart(store, manager, customer));
    }

    @Test
    public void testForSellingToCustomerThatHasNotCheckedOut() throws NotAuthorizedException, OutOfStockException, StockDoesNotExistException{
        administrativeOperations.loadProductsFromExcelFile(store, manager, excelFilePath);
        customerOperations.addProductToCart(store, customer, "DAMFS-CSP-006", 9);

        assertThrows(InvalidOperationException.class, ()-> administrativeOperations.sellProductsInCart(store, cashier, customer));
    }

    @Test
    public void companyProductShouldReduceAfterPurchase() throws NotAuthorizedException, OutOfStockException, StockDoesNotExistException, InsufficientFundException, InvalidOperationException {
        administrativeOperations.loadProductsFromExcelFile(store, manager, excelFilePath);
        assertEquals(13, store.getGoods().get("DAMFS-CSP-006").getProductQuantity());
        customerOperations.fundWallet(customer, 10_000);
        customerOperations.addProductToCart(store, customer, "DAMFS-CSP-006", 9);
        customerOperations.joinQueue(store,customer);
        customer.setCheckOut(true);
        administrativeOperations.sellProductsInCart(store, cashier, customer);
        assertEquals(4, store.getGoods().get("DAMFS-CSP-006").getProductQuantity());
    }

    @Test
    public void cashierCanSeeGoodsByCategory() throws NotAuthorizedException {
        administrativeOperations.loadProductsFromExcelFile(store, manager, excelFilePath);
        assertEquals( 2, administrativeOperations.viewProductByCategory(store, "Cereals").size());
        assertEquals( "Cereals", administrativeOperations.viewProductByCategory(store, "Cereals").get(0).getProductCategory());
        assertEquals( "Cereals", administrativeOperations.viewProductByCategory(store, "Cereals").get(1).getProductCategory());
    }

    @Test
    public void shouldCheckIfQueueFollowPriority() throws InvalidOperationException, NotAuthorizedException, OutOfStockException, StockDoesNotExistException, InsufficientFundException {
       administrativeOperations.loadProductsFromExcelFile(store, manager, excelFilePath);


        customerOperations.fundWallet(customer, 200000);
        customerOperations.addProductToCart(store, customer, "DAMFS-CSP-006", 5);
        customerOperations.joinQueue(store,customer);
        customer.setCheckOut(true);
        customer.isCheckOut();


        customerOperations.fundWallet(customer2, 200000);
        customerOperations.addProductToCart(store, customer2, "DAMFS-CSP-006", 1);
        customerOperations.joinQueue(store,customer2);
        customer2.setCheckOut(true);
        customer2.isCheckOut();

        customerOperations.fundWallet(customer3, 10_000);
        customerOperations.addProductToCart(store, customer3, "DAMFS-CSP-006", 3);
        customerOperations.joinQueue(store,customer3);
        customer3.setCheckOut(true);
        customer3.isCheckOut();

        customerOperations.fundWallet(customer4, 10_000);
        customerOperations.addProductToCart(store, customer4, "DAMFS-CSP-006", 4);
        customerOperations.joinQueue(store,customer4);
        customer4.setCheckOut(true);
        customer4.isCheckOut();


        String expected = "1Tola 2Steve 3Emeka 4Sade";
        String actualSequence = "";
        int count = 1;

        while (!store.getCartListQueue().isEmpty()){
            Customer customer = store.getCartListQueue().poll();

            actualSequence +=count+customer.getFirstName()+" ";
            count++;
        }

        System.out.println(actualSequence);
        assertEquals(expected, actualSequence.trim());
    }
}