package models;

import enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class Person {
    private String firstName;
    private String lastName;
    private Gender gender;
}
