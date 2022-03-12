import enums.Designation;
import enums.Gender;
import enums.Qualification;
import exceptions.*;
import models.*;
import operations.implementations.AdministrativeOperationsImpl;
import operations.implementations.ApplicantOperationImpl;
import operations.implementations.CustomerOperationsImpl;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws NotAuthorizedException, OutOfStockException, StockDoesNotExistException, InvalidOperationException, InsufficientFundException {
        Store decagonEnt = new Store("Decagon Enterprise");
        Staff mike = new Staff("Mike", "Peter", Gender.MALE, Designation.MANAGER);
        Staff mary = new Staff("Mary", "Samuel", Gender.FEMALE, Designation.CASHIER);
        Customer mercy = new Customer("Mercy", "Maxwell", Gender.FEMALE);
        Applicant sammie = new Applicant("Sammie", "Richard", Gender.MALE, Qualification.OND);
        CustomerOperationsImpl customerOperations = new CustomerOperationsImpl();
        AdministrativeOperationsImpl administrativeOperations = new AdministrativeOperationsImpl();
        ApplicantOperationImpl applicantOperation = new ApplicantOperationImpl();
        Product milk = new Product("sh22", "Milk", "diary", 25000, 10);
        Customer caleb = new Customer("Caleb", "Max", Gender.MALE);
        Customer smith = new Customer("Smith", "Matty", Gender.MALE);
        Customer deen = new Customer("Abi", "Deen", Gender.MALE);
        Customer evelyn = new Customer("Evlyn", "Morris", Gender.FEMALE);

        administrativeOperations.loadProductsFromExcelFile(decagonEnt, mike, "src/main/resources/excel_files/decagon_Ent_products.xlsx");

        System.out.println(Arrays.toString(new Storage[]{decagonEnt.getGoods()}));

        caleb.setWallet(1000000);
        customerOperations.addProductToCart(decagonEnt, caleb, "DAMFS-CER-001", 2);
        customerOperations.joinQueue(decagonEnt, caleb);
        caleb.setCheckOut(true);


        smith.setWallet(1000000);
        customerOperations.addProductToCart(decagonEnt, smith, "DAMFS-CER-002", 1);
        customerOperations.joinQueue(decagonEnt, smith);
        smith.setCheckOut(true);


        mercy.setWallet(1000000);
        customerOperations.addProductToCart(decagonEnt, mercy, "DAMFS-CER-001", 3);
        customerOperations.joinQueue(decagonEnt, mercy);
        mercy.setCheckOut(true);

        deen.setWallet(1000000);
        customerOperations.addProductToCart(decagonEnt, deen, "DAMFS-CER-002", 3);
        customerOperations.joinQueue(decagonEnt, deen);
        deen.setCheckOut(true);

        evelyn.setWallet(1000000);
        customerOperations.addProductToCart(decagonEnt, evelyn, "DAMFS-CER-001", 2);
        customerOperations.joinQueue(decagonEnt, evelyn);
        evelyn.setCheckOut(true);

       // System.out.println(decagonEnt.getCartListQueue());


        ThreadedClass.cashierThread(decagonEnt,mary);

//        administrativeOperations.sellProductWithPriorityQueue(mary, decagonEnt);
//        System.out.println(decagonEnt.getCartListQueue());




    }
}