package hiring.database.mybatis.mappers;

import hiring.model.Employer;
import hiring.model.Vacancy;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface VacancyMapper {

    @Select("SELECT id, name, salary, activity FROM vacancy WHERE userid = #{id}")
    @Result(property = "requirements", column = "id", javaType = Set.class,
            many = @Many(select = "hiring.database.mybatis.mappers.RequirementMapper.getRequirementsByVacancy"))
    List<Vacancy> getVacanciesByEmployer(Employer employer);
}
