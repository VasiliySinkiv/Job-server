package hiring.dto.request;

public class DeleteRequirementDtoRequest {
    private String token;
    private String name;
    private int level;
    private boolean obligatory;

    public DeleteRequirementDtoRequest(String token, String name, int level, boolean obligatory) {
        this.token = token;
        this.name = name;
        this.level = level;
        this.obligatory = obligatory;
    }

    public String getToken() {
        return token;
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
