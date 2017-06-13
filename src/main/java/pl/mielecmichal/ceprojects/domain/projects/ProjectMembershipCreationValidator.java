package pl.mielecmichal.ceprojects.domain.projects;

import pl.mielecmichal.ceprojects.domain.validators.ValidationMessages;
import pl.mielecmichal.ceprojects.domain.validators.Validator;

public class ProjectMembershipCreationValidator implements Validator<ProjectMembership> {

    @Override
    public ValidationMessages validate(ProjectMembership membership) {
        return ValidationMessages.create();
    }
}
