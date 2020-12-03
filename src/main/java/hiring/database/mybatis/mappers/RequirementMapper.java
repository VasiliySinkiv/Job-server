package hiring.database.mybatis.mappers;

import hiring.model.Requirement;
import hiring.model.Vacancy;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

public interface RequirementMapper {

    @Select("SELECT requirement.id, name, level, obligatory FROM requirement, skill_name WHERE vacancyid = #{id} AND " +
            "nameid = skill_name.id")
    Set<Requirement> getRequirementsByVacancy(Vacancy vacancy);
}
