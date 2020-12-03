package hiring.dto.request;

public class EmployeeSkillDtoRequest {
    private String token;
    private String name;
    private int level;

    public EmployeeSkillDtoRequest(String token, String name, int level) {
        this.token = token;
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String getToken(){
        return token;
    }
}
