package pl.mielecmichal.ceprojects.domain.projects;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class ProjectTask {

    private final String name;
    private final String description;
    private final String authorLogin;
    private final String assigneeLogin;
    private final String projectName;
    private final LocalDateTime creationDate;
    private final LocalDateTime expirationDate;
    private final ProjectTaskStatus status;

}
