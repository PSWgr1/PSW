package pl.mielecmichal.ceprojects.domain.projects;

import lombok.Builder;
import lombok.Value;
import pl.mielecmichal.ceprojects.domain.users.User;

import java.util.List;

@Value
@Builder
public class Project {

    private String name;
    private List<ProjectMembership> memberships;

    public boolean isMember(User user) {
        return memberships.stream()
                .anyMatch(m -> m.getUser().getLogin().equals(user.getLogin()));
    }

    public boolean hasRole(User user, ProjectRole projectRole) {
        return memberships.stream()
                .filter(m -> m.getUser().getLogin().equals(user.getLogin()))
                .anyMatch(m -> m.getProjectRole() == projectRole);
    }

}
