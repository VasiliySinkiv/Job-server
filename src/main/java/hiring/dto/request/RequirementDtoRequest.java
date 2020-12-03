package hiring.dto.request;

import hiring.model.Vacancy;

public class RequirementDtoRequest {
    private String token;
    private Vacancy vacancy;
    private String name;
    private int level;
    private boolean obligatory;

    public RequirementDtoRequest(String token, Vacancy vacancy, String name, int level, boolean obligatory) {
        this.token = token;
        this.vacancy = vacancy;
        this.name = name;
        this.level = level;
        this.obligatory = obligatory;
    }

    public RequirementDtoRequest(String name, int level, boolean obligatory) {
        this(null, null, name, level, obligatory);
    }

    public String getToken() {
        return token;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public boolean isObligatory() {
        return obligatory;
    }
}
