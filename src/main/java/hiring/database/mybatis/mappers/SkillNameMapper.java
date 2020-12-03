package hiring.database.mybatis.mappers;

import hiring.model.Skill;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;

public interface SkillNameMapper {

    @Insert("INSERT INTO skill_name SET name = #{name} ON DUPLICATE KEY UPDATE name = #{name}")
    void insertSkillName(Skill skill);

    @Delete("DELETE FROM skill_name")
    void deleteSkillName();
}
