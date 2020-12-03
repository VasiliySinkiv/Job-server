package hiring.dto.request;

import java.util.Set;

public class AddVacancyDtoRequest {
    private String token;
    private String name;
    private int salary;
    private Set<RequirementDtoRequest> requirements;
    private boolean active;

    public AddVacancyDtoRequest(String token, String name, int salary, Set<RequirementDtoRequest> requirements, boolean active) {
        this.token = token;
        this.name = name;
        this.salary = salary;
        this.requirements = requirements;
        this.active = active;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public Set<RequirementDtoRequest> getRequirements() {
        return requirements;
    }

    public boolean isActive() {
        return active;
    }
}
