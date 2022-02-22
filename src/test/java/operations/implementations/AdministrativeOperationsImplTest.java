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
    String excelFilePath = "src/main/resources/excel_files/damzxyno_food_store.xlsx";

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
        customerOperations.purchaseGoodsInCart(customer);
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
}