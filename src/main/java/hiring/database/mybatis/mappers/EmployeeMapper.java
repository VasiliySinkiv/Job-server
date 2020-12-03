package hiring.database.mybatis.mappers;

import hiring.model.Employee;
import hiring.model.Skill;
import hiring.model.User;
import hiring.model.Vacancy;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Set;

public interface EmployeeMapper {

    @Select("SELECT id, lastname, firstname, middlename, login, password, active FROM user WHERE id = (SELECT userid FROM session WHERE token = #{token})")
    @Result(property = "skills", column = "id", javaType = Set.class,
            many = @Many(select = "hiring.database.mybatis.mappers.SkillMapper.getSkillsByEmployee", fetchType = FetchType.LAZY))
    Employee getEmployeeByToken(String token);

    @Insert("INSERT INTO user (lastname, firstname, middlename, login, password, active) VALUES " +
            "(#{lastName}, #{firstName}, #{middleName}, #{login}, #{password}, #{active})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(Employee employee);

    @Update("UPDATE user SET lastname = #{lastName}, firstname = #{firstName}, middlename = #{middleName}, " +
            "password = #{password} WHERE id = #{id}")
    Integer updateEmployee(Employee employee);

    @Delete("DELETE FROM user WHERE id = #{id}")
    void delete(User user);

    @Insert("INSERT INTO session SET token = #{token}, userid = " +
            "(SELECT id FROM user WHERE login = #{login} AND password = #{password}) ON DUPLICATE KEY UPDATE token = token")
    Integer login(@Param("login") String login, @Param("password") String password, @Param("token") String token);

    @Delete("DELETE FROM session WHERE userid = #{id}")
    void logout(User user);

    @Insert("INSERT INTO skill SET level = #{skill.level}, userid = #{employee.id}, nameid = " +
            "(SELECT id FROM skill_name WHERE name = #{skill.name})")
    @Options(useGeneratedKeys = true, keyProperty = "skill.id")
    void insertSkill(@Param("employee") Employee employee, @Param("skill") Skill skill);

    @Update("UPDATE skill SET level = #{skill.level} WHERE userid = #{employee.id} AND nameid = " +
            "(SELECT id FROM skill_name WHERE name = #{skill.name})")
    void updateSkill(@Param("employee") Employee employee, @Param("skill") Skill skill);

    @Delete("DELETE FROM skill WHERE userid = #{employee.id} AND nameid = " +
            "(SELECT id FROM skill_name WHERE name = #{skill.name})")
    void deleteSkill(@Param("employee") Employee employee, @Param("skill") Skill skill);

    @Select({"<script>",
            "SELECT vacancy.id, name, salary, activity FROM vacancy " +
                    "INNER JOIN requirement as t1 ON vacancy.id = t1.vacancyid AND activity = true ",
                    "<if test='selectType == 1'> WHERE vacancyid IN " +
                            "(SELECT DISTINCT t1.vacancyid FROM requirement as t1, skill as t2 WHERE  t2.userid = #{employee.id} AND t1.nameid = t2.nameid AND t1.level &lt;= t2.level) " +
                            "GROUP BY vacancyid HAVING COUNT(vacancyid) &lt;= (SELECT COUNT(id) FROM skill WHERE userid = #{employee.id})",
            "</if>",
            "<if test='selectType == 2'> AND obligatory = true WHERE vacancyid IN " +
                    "(SELECT DISTINCT t1.vacancyid FROM requirement as t1, skill as t2 WHERE  t2.userid = #{employee.id} AND t1.nameid = t2.nameid AND t1.level &lt;= t2.level) " +
                    "GROUP BY vacancyid HAVING COUNT(vacancyid) &lt;= (SELECT COUNT(id) FROM skill WHERE userid = #{employee.id})",
            "</if>",
            "<if test='selectType == 3'> WHERE vacancyid IN " +
                    "(SELECT DISTINCT t1.vacancyid FROM requirement as t1, skill as t2 WHERE  t2.userid = #{employee.id} AND t1.nameid = t2.nameid) " +
                    "GROUP BY vacancyid HAVING COUNT(vacancyid) &lt;= (SELECT COUNT(id) FROM skill WHERE userid = #{employee.id})",
            "</if>",
            "<if test='selectType == 4'> INNER JOIN skill as t2 ON t2.userid = #{employee.id} AND t1.nameid = t2.nameid AND t1.level &lt;= t2.level " +
                    "GROUP BY vacancy.id HAVING COUNT(vacancy.id) &gt;= 1 ORDER BY COUNT(vacancy.id) DESC",
            "</if>",
                    "</script>"})
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "requirements", column = "id", javaType = Set.class,
                    many = @Many(select = "hiring.database.mybatis.mappers.RequirementMapper.getRequirementsByVacancy", fetchType = FetchType.EAGER))
    })
    List<Vacancy> getVacanciesByEmployee(@Param("employee") Employee employee, @Param("selectType") int selectType);

    @Update("UPDATE user SET active = #{isActivity} WHERE id = #{employee.id}")
    void changeActivityProfile(@Param("employee") Employee employee, @Param("isActivity") boolean isActivity);

    @Delete("DELETE FROM user")
    void deleteAll();
}
