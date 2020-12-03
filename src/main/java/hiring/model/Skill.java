package hiring.model;

import java.util.Objects;

public class Skill {
    private int id;
    private String name;
    private int level;

    public Skill(int id, String name, int level) {
        this.id = id;
        this.name = name;
        this.level = level;
    }

    public Skill(String name, int level) {
        this(0, name, level);
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

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Skill)) return false;
        Skill skill = (Skill) o;
        return getId() == skill.getId() &&
                getLevel() == skill.getLevel() &&
                Objects.equals(getName(), skill.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getLevel());
    }
}
