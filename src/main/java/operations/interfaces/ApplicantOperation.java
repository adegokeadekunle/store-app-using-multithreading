package operations.interfaces;

import exceptions.InvalidOperationException;
import models.Applicant;
import models.Store;

public interface ApplicantOperation{
    void apply(Store company, Applicant applicant) throws InvalidOperationException;
}
