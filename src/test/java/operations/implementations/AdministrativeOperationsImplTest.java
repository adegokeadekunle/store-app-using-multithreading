package operations.implementations;

import enums.Designation;
import enums.Gender;
import exceptions.*;
import models.*;
import operations.interfaces.AdministrativeOperations;
import operations.interfaces.CustomerOperations;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static org.junit.Assert.*;

public class AdministrativeOperationsImplTest {
    AdministrativeOperations administrativeOperations = new AdministrativeOperationsImpl();
    CustomerOperations customerOperations = new CustomerOperationsImpl();
    Store store = new Store("Damzxyno Food Store");
    Staff cashier = new Staff("Mary", "Ololade", Gender.FEMALE, Designation.CASHIER);
    Staff manager = new Staff("Wilfred", "Omokpo", Gender.MALE, Designation.MANAGER);
    Customer sade = new Customer("Sade", "Oluwaseyi", Gender.FEMALE);
    Customer tola = new Customer("Tola", "Samuel", Gender.MALE);
    Customer steve = new Customer("Steve", "Mathew", Gender.MALE);
    Customer emeka = new Customer("Emeka", "Chibuzor", Gender.MALE);
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
        customerOperations.addProductToCart(store, sade, "DAMFS-CSP-006", 9);

        assertThrows(NotAuthorizedException.class, ()-> administrativeOperations.sellProductsInCart(store, manager, sade));
    }

    @Test
    public void testForSellingToCustomerThatHasNotCheckedOut() throws NotAuthorizedException, OutOfStockException, StockDoesNotExistException{
        administrativeOperations.loadProductsFromExcelFile(store, manager, excelFilePath);
        customerOperations.addProductToCart(store, sade, "DAMFS-CSP-006", 9);

        assertThrows(InvalidOperationException.class, ()-> administrativeOperations.sellProductsInCart(store, cashier, sade));
    }

    @Test
    public void companyProductShouldReduceAfterPurchase() throws NotAuthorizedException, OutOfStockException, StockDoesNotExistException, InsufficientFundException, InvalidOperationException {
        administrativeOperations.loadProductsFromExcelFile(store, manager, excelFilePath);
        assertEquals(13, store.getGoods().get("DAMFS-CSP-006").getProductQuantity());
        customerOperations.fundWallet(sade, 10_000);
        customerOperations.addProductToCart(store, sade, "DAMFS-CSP-006", 9);
        customerOperations.joinQueue(store, sade);
        sade.setCheckOut(true);
        administrativeOperations.sellProductsInCart(store, cashier, sade);
        assertEquals(4, store.getGoods().get("DAMFS-CSP-006").getProductQuantity());
    }

    @Test
    public void cashierCanSeeGoodsByCategory() throws NotAuthorizedException {
        administrativeOperations.loadProductsFromExcelFile(store, manager, excelFilePath);
        assertEquals( 2, administrativeOperations.viewProductByCategory(store, "Cereals").size());
        assertEquals( "Cereals", administrativeOperations.viewProductByCategory(store, "Cereals").get(0).getProductCategory());
        assertEquals( "Cereals", administrativeOperations.viewProductByCategory(store, "Cereals").get(1).getProductCategory());
    }

//    @Test
//    public void shouldCheckIfQueueSellToTheLowestQuantity() throws InvalidOperationException, NotAuthorizedException, OutOfStockException, StockDoesNotExistException, InsufficientFundException {
//       administrativeOperations.loadProductsFromExcelFile(store, manager, excelFilePath);
//
//
//        customerOperations.fundWallet(sade, 200000);
//        customerOperations.addProductToCart(store, sade, "DAMFS-CSP-006", 5);
//        customerOperations.joinQueue(store, sade);
//        sade.setCheckOut(true);
//        sade.isCheckOut();
//
//
//        customerOperations.fundWallet(tola, 200000);
//        customerOperations.addProductToCart(store, tola, "DAMFS-CSP-006", 1);
//        customerOperations.joinQueue(store, tola);
//        tola.setCheckOut(true);
//        tola.isCheckOut();
//
//        customerOperations.fundWallet(steve, 10_000);
//        customerOperations.addProductToCart(store, steve, "DAMFS-CSP-006", 3);
//        customerOperations.joinQueue(store, steve);
//        steve.setCheckOut(true);
//        steve.isCheckOut();
//
//        customerOperations.fundWallet(emeka, 10_000);
//        customerOperations.addProductToCart(store, emeka, "DAMFS-CSP-006", 4);
//        customerOperations.joinQueue(store, emeka);
//        emeka.setCheckOut(true);
//        emeka.isCheckOut();
//
//
//        String expected = "1Tola 2Steve 3Emeka 4Sade";
//        String actualSequence = "";
//        int count = 1;
//
//        while (!store.getCartListQueue().isEmpty()){
//            Customer customer = store.getCartListQueue().poll();
//
//            actualSequence +=count+customer.getFirstName()+" ";
//            count++;
//        }
//
//        System.out.println(actualSequence);
//        assertEquals(expected, actualSequence.trim());
//    }
//    @Test
//    public void shouldCheckIfQueueFollowNaturalOrder() throws InvalidOperationException, NotAuthorizedException, OutOfStockException, StockDoesNotExistException, InsufficientFundException {
//        administrativeOperations.loadProductsFromExcelFile(store, manager, excelFilePath);
//
//
//        customerOperations.fundWallet(sade, 200000);
//        customerOperations.addProductToCart(store, sade, "DAMFS-CSP-006", 5);
//        customerOperations.joinQueue(store, sade);
//        sade.setCheckOut(true);
//        sade.isCheckOut();
//
//
//        customerOperations.fundWallet(tola, 200000);
//        customerOperations.addProductToCart(store, tola, "DAMFS-CER-001", 1);
//        customerOperations.joinQueue(store, tola);
//        tola.setCheckOut(true);
//        tola.isCheckOut();
//
//        customerOperations.fundWallet(steve, 10_000);
//        customerOperations.addProductToCart(store, steve, "DAMFS-CSP-006", 3);
//        customerOperations.joinQueue(store, steve);
//        steve.setCheckOut(true);
//        steve.isCheckOut();
//
//        customerOperations.fundWallet(emeka, 10_000);
//        customerOperations.addProductToCart(store, emeka, "DAMFS-CSP-006", 4);
//        customerOperations.joinQueue(store, emeka);
//        emeka.setCheckOut(true);
//        emeka.isCheckOut();
//
//
//        String expected = "1Steve 2Emeka 3Sade 4Tola";
//        String actualSequence = "";
//        int count = 1;
//
//
//        while (!store.getCartListQueue().isEmpty()){
//            Customer customer = store.getCartListQueue().poll();
//
//            actualSequence +=count+customer.getFirstName()+" ";
//            count++;
//        }
//
//        System.out.println(actualSequence);
//        assertEquals(expected, actualSequence.trim());
//    }

    @Test
    public void shouldCheckAllCustomersBought() throws OutOfStockException, InvalidOperationException, NotAuthorizedException {
        ExecutorService executor ;
        while (!store.getCustomerList().isEmpty()) {
            executor = Executors.newFixedThreadPool(5);
            Callable<String> callable;
            Set<Callable<String>> callableSet;

            Store shop = store;
            Staff staff = cashier;
            Customer customer = store.getCustomerList().poll();

            callable = administrativeOperations.sellProductsInCart(shop, staff, customer);

            callableSet = new HashSet<>();
            callableSet.add(callable);
            System.out.println("\n");


            try {
                List<Future<String>> futures = executor.invokeAll(callableSet);
                for (Future<String> future : futures) {
                    System.out.println(future.get());
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {


            }



        }
     //  executor.shutdown();
        assertEquals(0,store.getCustomerList().size());

    }
}


