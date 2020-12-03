package hiring.dao;

import hiring.exception.ServerException;
import hiring.model.Employee;
import hiring.model.Skill;
import hiring.model.Vacancy;

import java.util.List;

public interface EmployeeDao extends UserDao {
    Employee getByToken(String token) throws ServerException;

    void changeEmployee(Employee employee);

    void addSkill(Employee employee, Skill skill);

    void changeSkill(Employee employee, Skill skill) throws ServerException;

    void deleteSkill(Employee employee, Skill skill) throws ServerException;

    List<Vacancy> getVacanciesByEmployee(Employee employee, int type) throws ServerException;

    void changeActivityProfile(Employee employee, boolean isActivity);
}
