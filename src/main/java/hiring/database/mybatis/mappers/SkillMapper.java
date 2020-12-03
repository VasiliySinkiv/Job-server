package hiring.database.mybatis.mappers;

import hiring.model.Employee;
import hiring.model.Skill;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

public interface SkillMapper {

    @Select("SELECT skill.id, name, level FROM skill, skill_name WHERE userid = #{id} AND nameid = skill_name.id")
    Set<Skill> getSkillsByEmployee(Employee employee);
}
