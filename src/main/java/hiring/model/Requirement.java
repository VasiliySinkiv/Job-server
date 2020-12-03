package hiring.model;

import java.util.Objects;

public class Requirement extends Skill {
    private boolean obligatory;

    public Requirement(int id, String name, int level, boolean required) {
        super(id, name, level);
        this.obligatory = required;
    }

    public Requirement(String name, int level, boolean required) {
        this(0, name, level, required);
    }

    public boolean isRequired() {
        return obligatory;
    }

    public void setRequired(boolean required) {
        this.obligatory = required;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Requirement)) return false;
        if (!super.equals(o)) return false;
        Requirement that = (Requirement) o;
        return obligatory == that.obligatory;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), obligatory);
    }
}
