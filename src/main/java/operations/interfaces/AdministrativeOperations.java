package operations.interfaces;

import exceptions.InvalidOperationException;
import exceptions.NotAuthorizedException;
import exceptions.OutOfStockException;
import models.Applicant;
import models.Customer;
import models.Staff;
import models.Store;

import java.util.concurrent.Callable;

public interface AdministrativeOperations extends CommonOperations {
    void loadProductsFromExcelFile(Store company, Staff staff, String filePath) throws NotAuthorizedException;
    Callable<String> sellProductsInCart(Store company, Staff staff, Customer customer) throws OutOfStockException, NotAuthorizedException, InvalidOperationException;
    void hireCashier(Store store, Staff staff, Applicant applicant) throws NotAuthorizedException, InvalidOperationException;
    void sellProductWithPriorityQueue(Staff staff, Store store) throws InvalidOperationException, NotAuthorizedException, OutOfStockException;
}
