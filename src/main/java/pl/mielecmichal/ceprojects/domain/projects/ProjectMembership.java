package pl.mielecmichal.ceprojects.domain.projects;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import pl.mielecmichal.ceprojects.domain.users.User;

@Value
@Builder
@Getter
public class ProjectMembership {
    private final String userLogin;
    private final ProjectRole projectRole;
    private final String projectName;
}
