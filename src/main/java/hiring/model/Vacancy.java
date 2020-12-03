package hiring.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Vacancy {
    private int id;
    private String name;
    private int salary;
    private Set<Requirement> requirements;
    private boolean activity;

    public Vacancy(int id, String name, int salary, Set<Requirement> requirements, boolean activity) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.requirements = requirements;
        this.activity = activity;
    }

    public Vacancy(int id, String name, int salary, boolean activity) {
        this(id, name, salary, new HashSet<>(), activity);
    }

    public Vacancy(String name, int salary, Set<Requirement> requirements, boolean activity) {
        this(0, name, salary, requirements, activity);
    }

    public Vacancy(String name, int salary, boolean activity) {
        this(0, name, salary, new HashSet<>(), activity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public Set<Requirement> getRequirements() {
        return requirements;
    }

    public boolean isActivity() {
        return activity;
    }

    public void setActivity(boolean activity) {
        this.activity = activity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vacancy)) return false;
        Vacancy vacancy = (Vacancy) o;
        return getId() == vacancy.getId() &&
                getSalary() == vacancy.getSalary() &&
                isActivity() == vacancy.isActivity() &&
                Objects.equals(getName(), vacancy.getName()) &&
                Objects.equals(getRequirements(), vacancy.getRequirements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSalary(), getRequirements(), isActivity());
    }
}
