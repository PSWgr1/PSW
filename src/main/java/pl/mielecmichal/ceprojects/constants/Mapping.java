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
    REGISTRATION("/registration"),
    LOGIN("/login"),
    LOGOUT("/logout");

    private final String path;

}
