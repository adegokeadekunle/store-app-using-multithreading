package models;

import enums.Gender;
import enums.Qualification;
import lombok.Getter;

@Getter
public class Applicant extends Person{
    private final Qualification qualification;

    public Applicant(String lastName, String firstName, Gender gender, Qualification qualification) {
        super(lastName, firstName, gender);
        this.qualification = qualification;
    }
}
