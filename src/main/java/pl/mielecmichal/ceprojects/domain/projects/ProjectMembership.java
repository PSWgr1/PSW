package pl.mielecmichal.ceprojects.domain.projects;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import pl.mielecmichal.ceprojects.domain.users.User;

@Value
@Builder
@Getter
public class ProjectMembership {
    private final User user;
    private final ProjectRole projectRole;
}
