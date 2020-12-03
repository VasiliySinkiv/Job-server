package hiring.database.mybatis.mappers;

import hiring.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Set;

public interface EmployerMapper {

    @Select("SELECT id, lastname, firstname, middlename, login, password, company, address, email FROM user WHERE id = (SELECT userid FROM session WHERE token = #{token})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "vacancies", column = "id", javaType = List.class,
                    many = @Many(select = "hiring.database.mybatis.mappers.VacancyMapper.getVacanciesByEmployer", fetchType = FetchType.LAZY))
    })
    Employer getEmployerByToken(String token);

    @Insert("INSERT INTO user (lastname, firstname, middlename, login, password, company, address, email) " +
            "VALUES(#{lastName}, #{firstName}, #{middleName}, #{login}, #{password}, #{company}, #{address}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insert(Employer employer);

    @Update("UPDATE user SET lastname = #{lastName}, firstname = #{firstName}, middlename = #{middleName}, " +
            "password = #{password}, company = #{company}, address = #{address}, email = #{email} " +
            "WHERE id = #{id}")
    void updateEmployer(Employer employer);

    @Delete("DELETE FROM user WHERE id = #{id}")
    void delete(User user);

    @Insert("INSERT INTO session SET token = #{token}, userid = " +
            "(SELECT id FROM user WHERE login = #{login} AND password = #{password}) ON DUPLICATE KEY UPDATE token = token")
    Integer login(@Param("login") String login, @Param("password") String password, @Param("token") String token);

    @Delete("DELETE FROM session WHERE userid = #{id}")
    void logout(User user);

    @Insert("INSERT INTO vacancy (name, salary, activity, userid) VALUES" +
            "(#{vacancy.name}, #{vacancy.salary}, #{vacancy.activity}, #{employer.id})")
    @Options(useGeneratedKeys = true, keyProperty = "vacancy.id")
    void addVacancy(@Param("employer") Employer employer, @Param("vacancy") Vacancy vacancy);

    @Delete("DELETE FROM vacancy WHERE id = #{id}")
    void deleteVacancy(Vacancy vacancy);

    @Insert("INSERT INTO requirement SET level = #{requirement.level}, obligatory = #{requirement.obligatory}, " +
            "vacancyid = #{vacancy.id}, nameid = (SELECT id FROM skill_name WHERE name = #{requirement.name})")
    @Options(useGeneratedKeys = true, keyProperty = "requirement.id")
    void addRequirement(@Param("vacancy") Vacancy vacancy, @Param("requirement") Requirement requirement);

    @Update("UPDATE requirement SET obligatory = #{requirement.obligatory}, level = #{requirement.level} " +
            "WHERE vacancyid = #{vacancy.id} AND nameid = (SELECT id FROM skill_name WHERE name = #{requirement.name})")
    Integer changeRequirement(@Param("vacancy") Vacancy vacancy, @Param("requirement") Requirement requirement);

    @Delete("DELETE FROM requirement WHERE vacancyid = #{vacancy.id} AND nameid = " +
            "(SELECT id FROM skill_name WHERE name = #{requirement.name})")
    Integer deleteRequirement(@Param("vacancy") Vacancy vacancy, @Param("requirement") Requirement requirement);

    @Select({"<script>",
            "SELECT id, name, salary, activity FROM vacancy",
            "<where> userid = #{employer.id}" +
            "<if test='typeVacancies == 2'> AND activity = true",
            "</if>",
            "<if test='typeVacancies == 3'> AND activity = false",
            "</if>",
            "</where>" +
                    "</script>"})
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "requirements", column = "id", javaType = Set.class,
                    many = @Many(select = "hiring.database.mybatis.mappers.RequirementMapper.getRequirementsByVacancy", fetchType = FetchType.EAGER))
    })
    List<Vacancy> getAllVacancies(@Param("employer") Employer employer, @Param("typeVacancies") int typeVacancies);

    @Select({"<script>",
        "SELECT t1.id, lastname, firstname, middlename, login, password, active FROM user as t1 " +
        "INNER JOIN skill as t2 INNER JOIN requirement as t3 ",
        "<where> t1.id = t2.userId AND t1.active = true AND t3.vacancyid = #{vacancy.id} AND t2.nameid = t3.nameid " +
                "<if test='selectionType == 1'> AND t2.level &gt;= t3.level " +
                "GROUP BY t1.id HAVING COUNT(t1.id) = (SELECT COUNT(id) FROM requirement WHERE vacancyid = #{vacancy.id})",
        "</if>",
        "<if test='selectionType == 2'> AND t2.level &gt;= t3.level AND t3.obligatory = true " +
        "GROUP BY t1.id HAVING COUNT(t1.id) = (SELECT COUNT(id) FROM requirement WHERE vacancyid = #{vacancy.id} AND obligatory = true)",
        "</if>",
        "<if test='selectionType == 3'> GROUP BY t1.id HAVING COUNT(t1.id) = (SELECT COUNT(id) FROM requirement WHERE vacancyid = #{vacancy.id})",
        "</if>",
        "<if test='selectionType == 4'> AND t2.level &gt;= t3.level GROUP BY t1.id",
        "</if>",
        "</where>" +
                "</script>"})
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "skills", column = "id", javaType = Set.class,
                    // с параметром LAZY ошибка сериализации Employee - не найден адаптер типа.
                    many = @Many(select = "hiring.database.mybatis.mappers.SkillMapper.getSkillsByEmployee", fetchType = FetchType.EAGER))
    })
    List<Employee> getEmployeesByVacancy(@Param("vacancy") Vacancy vacancy, @Param("selectionType") int selectionType);

    @Update("UPDATE user INNER JOIN vacancy ON vacancy.id = #{vacancy.id} " +
            "SET user.active = false, vacancy.activity = false WHERE user.id = #{employee.id}")
    void acceptEmployeeAndDeactivateVacancy(@Param("vacancy") Vacancy vacancy, @Param("employee") Employee employee);

}
