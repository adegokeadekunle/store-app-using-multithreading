package operations.implementations;

import enums.Gender;
import enums.Qualification;
import exceptions.InvalidOperationException;
import models.Applicant;
import models.Store;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicantOperationImplTest {
    private Applicant applicant1;
    private  Applicant applicant2;
    Store decagon;
    ApplicantOperationImpl applicantOperation;

    @Before
    public void setUp(){
        applicant1 = new Applicant("Joshua", "Innocent", Gender.MALE, Qualification.OND);
        applicant2 = new Applicant("Repos", "Emmanuel", Gender.MALE, Qualification.SSCE);
        decagon = new Store("Damzxyno Food Store");
        applicantOperation = new ApplicantOperationImpl();
    }

    @Test
    public void applicantsNameShouldBeAddedToTheApplicantListInCompanyWhenTheApply() throws InvalidOperationException {
        assertFalse("Initially, decagon doesn't have applicant 1!", decagon.getApplicantList().contains(applicant1));
        applicantOperation.apply(decagon, applicant1);
        assertTrue("Applicant1 should be contained in applicant list after applying", decagon.getApplicantList().contains(applicant1));
    }

    @Test
    public void exceptionShouldBeThrownWhenApplicantApplyMoreThanOnce() throws InvalidOperationException {
        applicantOperation.apply(decagon, applicant2);
        Exception exception = assertThrows(InvalidOperationException.class, () -> applicantOperation.apply(decagon, applicant2));
        assertTrue("You already applied!".contains(exception.getMessage()));
    }
}