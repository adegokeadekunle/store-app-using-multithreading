package models;

import exceptions.InvalidOperationException;
import exceptions.NotAuthorizedException;
import exceptions.OutOfStockException;
import operations.implementations.AdministrativeOperationsImpl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

public class ThreadedClass {
   static ExecutorService executor = Executors.newFixedThreadPool(3);

    public static  void cashierThread(Store store, Staff staff) throws InvalidOperationException, NotAuthorizedException, OutOfStockException {

        while(!store.getCustomerList().isEmpty()){
        AdministrativeOperationsImpl administrativeOperations = new AdministrativeOperationsImpl();
        Callable<String> callable;
        Set<Callable<String>> callableSet;

            Store shop = store;
            Staff cashier = staff;
            Customer customer = store.getCustomerList().poll();

            callable = administrativeOperations.sellProductsInCart(shop, cashier, customer);

            callableSet = new HashSet<>();
            callableSet.add(callable);
            System.out.println("\n");




    try {
        List<Future<String>> futures = executor.invokeAll(callableSet);
        for(Future<String> future : futures){
            System.out.println(future.get());
        }

    }catch (InterruptedException | ExecutionException e){
        e.printStackTrace();
    } finally {

       // System.out.println(Arrays.toString(new List[]{store.getProductList()}));

    }
} executor.shutdown();

    }


}


//
//    public CompletableFuture<Customer> getCustomers(Customer customer){
//        return CompletableFuture.supplyAsync(()-> {LongTask.simulate})
//    }
//
//    public void runThread() throws ExecutionException, InterruptedException {
//        ExecutorService service = Executors.newFixedThreadPool(5);
//        AdministrativeOperationsImpl admin = new AdministrativeOperationsImpl();
//
//        Future<Integer> future = service.submit(admin.sellProductsInCart(new CashierThread(customerThread())));
//        future.get();
//
//
//    }
//
//    public static void customerThread(Customer customer){
//
//
//
//    }
//
//static  class cashierThread {
//
//    public static  void CashierThread(Store store, Staff staff,Customer customer)   {
//
//        AdministrativeOperationsImpl administrativeOperations = new AdministrativeOperationsImpl();
//        Store shop = store;
//        Staff cashier = staff;
//        Customer buyer = customer;
//
//        try{
//            administrativeOperations.sellProductsInCart(shop,cashier,buyer);
//        }
//        catch( InvalidOperationException  | NotAuthorizedException exception){
//            exception.printStackTrace();
//        }
//    }
//
//}