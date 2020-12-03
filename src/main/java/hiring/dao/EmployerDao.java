package hiring.dao;

import hiring.exception.ServerException;
import hiring.model.Employee;
import hiring.model.Employer;
import hiring.model.Requirement;
import hiring.model.Vacancy;

import java.util.List;

public interface EmployerDao extends UserDao {
    Employer getByToken(String token) throws ServerException;

    void changeEmployer(Employer employer);

    //int getIndexByVacancy(Employer employer, Vacancy vacancy) throws ServerException;

    void addVacancy(Employer employer, Vacancy vacancy);

    void deleteVacancy(Employer employer, Vacancy vacancy);

    void addRequirement(Employer employer, Vacancy vacancy, Requirement requirement);

    void changeRequirement(Employer employer, Vacancy vacancy, Requirement requirement) throws ServerException;

    void deleteRequirement(Employer employer, Vacancy vacancy, Requirement requirement) throws ServerException;

    List<Vacancy> getAllVacancies(Employer employer, int typeVacancies);

    List<Employee> getEmployeesByVacancy(Vacancy vacancy, int selectionType) throws ServerException;

    void acceptEmployee(Employer employer, Vacancy vacancy, Employee employee);

    void clearDataBase();
}
