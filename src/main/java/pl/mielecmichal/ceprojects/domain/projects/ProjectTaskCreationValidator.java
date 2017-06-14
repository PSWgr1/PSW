package pl.mielecmichal.ceprojects.domain.projects;

import pl.mielecmichal.ceprojects.domain.validators.ValidationMessages;
import pl.mielecmichal.ceprojects.domain.validators.Validator;

public class ProjectTaskCreationValidator implements Validator<ProjectTask> {
    @Override
    public ValidationMessages validate(ProjectTask projectTask) {
        return ValidationMessages.create();
    }
}
