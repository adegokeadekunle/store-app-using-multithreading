package models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
@ToString

public class Store {
    private final String storeName;
    private double storeAccount = 0.0;
    private List <Staff> staffList = new ArrayList<>();
    private List <Applicant> applicantList = new ArrayList<>();
    private Storage goods = new Storage();
}
