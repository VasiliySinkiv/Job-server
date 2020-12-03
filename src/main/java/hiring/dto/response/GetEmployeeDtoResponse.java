package hiring.dto.response;

import hiring.model.Skill;

import java.util.Collections;
import java.util.Set;

public class GetEmployeeDtoResponse {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String login;
    private String password;
    private boolean active;
    private Set<Skill> skills;

    public GetEmployeeDtoResponse(int id, String lastName, String firstName, String middleName, String login, String password,
                                  boolean active, Set<Skill> skills) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.login = login;
        this.password = password;
        this.active = active;
        this.skills = skills;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Set<Skill> getSkills() {
        return Collections.unmodifiableSet(skills);
    }

    public boolean isActive() {
        return active;
    }
}
