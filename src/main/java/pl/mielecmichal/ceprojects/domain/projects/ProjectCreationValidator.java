package pl.mielecmichal.ceprojects.domain.projects;

import pl.mielecmichal.ceprojects.domain.validators.ValidationMessages;
import pl.mielecmichal.ceprojects.domain.validators.Validator;

public class ProjectCreationValidator implements Validator<Project> {
    @Override
    public ValidationMessages validate(Project project) {
        return ValidationMessages.create();
    }
}
