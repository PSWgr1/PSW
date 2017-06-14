package pl.mielecmichal.ceprojects.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Mapping {

    INTRO("/"),
    PROJECTS("/projects"),
    PROJECT_DETAILS("/projects/:projectName"),
    PROJECT_CREATION("/projectCreation"),
    PROJECT_REMOVAL("/projectRemoval"),
    PROJECT_MEMBERSHIP_CREATION("/projectMembershipCreation"),
    PROJECT_TASK_CREATION("/projectTaskCreation"),
    PROJECT_TASK_DELETION("/projectTaskDeletion"),
    PROJECT_TASK_CHANGE("/projectTaskChange"),
    REGISTRATION("/registration"),
    LOGIN("/login"),
    LOGOUT("/logout");

    private final String path;

}
