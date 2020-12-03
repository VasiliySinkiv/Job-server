package hiring.daoimpl;

import hiring.dao.EmployerDao;
import hiring.exception.ServerErrorCode;
import hiring.exception.ServerException;
import hiring.model.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class EmployerDaoImpl extends DaoImplBase implements EmployerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployerDaoImpl.class);

    @Override
    public Employer getByToken(String token) throws ServerException {
        LOGGER.debug("DAO get Employer by token {}", token);
        Employer employer;
        try (SqlSession sqlSession = getSession()) {
            employer = getEmployerMapper(sqlSession).getEmployerByToken(token);
            if (employer == null) {
                LOGGER.debug("Can't get Employer by token {}", token);
                throw new ServerException(ServerErrorCode.WRONG_TOKEN);
            }
        } catch (RuntimeException ex) {
            LOGGER.debug("Can't get Employer by token {}", token, ex);
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
        return employer;
    }

    @Override
    public String insert(User user) throws ServerException {
        LOGGER.debug("DAO insert Employer {}", user);
        String token = UUID.randomUUID().toString();
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployerMapper(sqlSession).insert((Employer) user);
                getUserSessionMapper(sqlSession).openSession(user, token);
            } catch (RuntimeException ex) {
                LOGGER.debug("Cant insert Employer {}", user, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.LOGIN_ALREADY_USED);
            }
            sqlSession.commit();
        }
        return token;
    }

    @Override
    public void changeEmployer(Employer employer) {
        LOGGER.debug("DAO change Employer {}, ", employer);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployerMapper(sqlSession).updateEmployer(employer);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't change Employer {}", employer, ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public void delete(User user, String token) {
        LOGGER.debug("DAO delete Employer {}", user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployerMapper(sqlSession).delete(user);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't delete Employer {}", user, ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public String login(String login, String password) throws ServerException {
        LOGGER.debug("DAO login Employer by login {} and password {}", login, password);
        String token = UUID.randomUUID().toString();
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployerMapper(sqlSession).login(login, password, token);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't Login Employer by login {} and password {}", login, password, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.WRONG_LOGIN_OR_PASSWORD);
            }
            sqlSession.commit();
        }
        return token;
    }

    @Override
    public void logout(User user, String token) {
        LOGGER.debug("DAO logout Employer by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployerMapper(sqlSession).logout(user);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't logout Employer by token {}", token, ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public void addVacancy(Employer employer, Vacancy vacancy) {
        LOGGER.debug("DAO add Vacancy {} Employer {}", vacancy, employer);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployerMapper(sqlSession).addVacancy(employer, vacancy);
                for (Requirement requirement : vacancy.getRequirements()){
                    getSkillNameMapper(sqlSession).insertSkillName(requirement);
                    getEmployerMapper(sqlSession).addRequirement(vacancy, requirement);
                }
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't add Vacancy {} Employer {}", vacancy, employer,ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteVacancy(Employer employer, Vacancy vacancy) {
        LOGGER.debug("DAO delete Vacancy by Vacancy {} for Employer {}", vacancy, employer);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployerMapper(sqlSession).deleteVacancy(vacancy);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't delete Vacancy by Vacancy {} for Employer {}", vacancy, employer, ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public void addRequirement(Employer employer, Vacancy vacancy, Requirement requirement) {
        LOGGER.debug("DAO add Requirement {} by Vacancy {} for Employer {}", requirement, vacancy, employer);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSkillNameMapper(sqlSession).insertSkillName(requirement);
                getEmployerMapper(sqlSession).addRequirement(vacancy, requirement);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't add Requirement {} by Vacancy {} for Employer {}", requirement, vacancy, employer);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public void changeRequirement(Employer employer, Vacancy vacancy, Requirement requirement) throws ServerException {
        LOGGER.debug("DAO change Requirement {} by Vacancy {} for Employer {}", requirement, vacancy, employer);
        try (SqlSession sqlSession = getSession()) {
            try {
                int result = getEmployerMapper(sqlSession).changeRequirement(vacancy, requirement);
                if (result == 0){
                    LOGGER.debug("Can't change Requirement {} by Vacancy {} for Employer {}", requirement, vacancy, employer);
                    throw new ServerException(ServerErrorCode.REQUIREMENT_NOT_FOUND);
                }
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't change Requirement {} by Vacancy {} for Employer {}", requirement, vacancy, employer, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.REQUIREMENT_NOT_FOUND);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteRequirement(Employer employer, Vacancy vacancy, Requirement requirement) throws ServerException {
        LOGGER.debug("DAO delete Requirement {} by Vacancy {} for Employer {}", requirement, vacancy, employer);
        try (SqlSession sqlSession = getSession()) {
            try {
                int result = getEmployerMapper(sqlSession).deleteRequirement(vacancy, requirement);
                if (result == 0){
                    LOGGER.debug("Can't delete Requirement {} by Vacancy {} for Employer {}", requirement, vacancy, employer);
                    throw new ServerException(ServerErrorCode.REQUIREMENT_NOT_FOUND);
                }
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't delete Requirement {} by Vacancy {} for Employer {}", requirement, vacancy, employer, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.REQUIREMENT_NOT_FOUND);
            }
            sqlSession.commit();
        }
    }

    @Override
    public List<Vacancy> getAllVacancies(Employer employer, int typeVacancies) {
        LOGGER.debug("DAO get all Vacancies by Employer {} for type {}", employer, typeVacancies);
        try (SqlSession sqlSession = getSession()) {
            return getEmployerMapper(sqlSession).getAllVacancies(employer, typeVacancies);
        } catch (RuntimeException ex) {
            LOGGER.debug("Can't get all Vacancies by Employer {} for type {}", employer, typeVacancies, ex);
            throw ex;
        }
    }

    @Override
    public List<Employee> getEmployeesByVacancy(Vacancy vacancy, int selectionType) throws ServerException {
        LOGGER.debug("DAO get Employees by Vacancy {} for selection type {}", vacancy, selectionType);
        // не работает через маппер xml, не может найти конструктор Employee со всеми полями запроса
        /*
        HashMap<String, Object> map = new HashMap<>();
        map.put("vacancyId", vacancy.getId());
        map.put("selectionType", selectionType);
         */
        try (SqlSession sqlSession = getSession()) {
            //return sqlSession.selectList("net.thumbtack.school.hiring.database.mybatis.mappers.EmployerMapper.getEmployeesByVacancyUsingJoin", map);
            return getEmployerMapper(sqlSession).getEmployeesByVacancy(vacancy, selectionType);
        } catch (RuntimeException ex) {
            LOGGER.debug("Can't get Employees by Vacancy {} for selection type {}", vacancy, selectionType, ex);
            throw ex;
        }
    }

    @Override
    public void acceptEmployee(Employer employer, Vacancy vacancy, Employee employee) {
        LOGGER.debug("DAO accept Employee {} and deactivate Vacancy {}", employee, vacancy);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployerMapper(sqlSession).acceptEmployeeAndDeactivateVacancy(vacancy, employee);
            } catch (RuntimeException ex) {
                sqlSession.rollback();
                LOGGER.debug("Can't accept Employee {} and deactivate Vacancy {}",employee, vacancy, ex);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void clearDataBase() {
        LOGGER.debug("DAO clear DataBase");
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployeeMapper(sqlSession).deleteAll();
                getSkillNameMapper(sqlSession).deleteSkillName();
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't clear DataBase", ex);
                sqlSession.rollback();
                throw ex;
            }
            sqlSession.commit();
        }
    }
}
