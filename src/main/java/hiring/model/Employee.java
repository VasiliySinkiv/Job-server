package hiring.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Employee extends User {
    private boolean active;
    private Set<Skill> skills;

    public Employee(int id, String lastName, String firstName, String middleName, String login, String password, boolean active, Set<Skill> skills) {
        super(id, lastName, firstName, middleName, login, password);
        this.active = active;
        this.skills = skills;
    }

    public Employee(int id, String lastName, String firstName, String middleName, String login, String password, boolean active) {
        this(id, lastName, firstName, middleName, login, password, active, new HashSet<>());
    }
    public Employee(String lastName, String firstName, String middleName, String login, String password) {
        this(0, lastName, firstName, middleName, login, password, true);
    }

    public Employee(String lastName, String firstName, String login, String password) {
        this(lastName, firstName, null, login, password);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean addSkill(Skill skill) {
        return skills.add(skill);
    }

    public boolean removeSkill(Skill skill){
        return skills.remove(skill);
    }

    public Set<Skill> getSkills() {
        return Collections.unmodifiableSet(skills);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return isActive() == employee.isActive() &&
                Objects.equals(getSkills(), employee.getSkills());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isActive(), getSkills());
    }
}
