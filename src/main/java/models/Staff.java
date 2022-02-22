package models;

import enums.Designation;
import enums.Gender;
import lombok.Getter;


@Getter
public class Staff extends Person{
    private final Designation designation;

    public Staff(String firstName, String lastName, Gender gender, Designation designation) {
        super(firstName, lastName, gender);
        this.designation = designation;
    }
}
