package hiring.daoimpl;

import hiring.dao.EmployeeDao;
import hiring.exception.ServerErrorCode;
import hiring.exception.ServerException;
import hiring.model.Employee;
import hiring.model.Skill;
import hiring.model.User;
import hiring.model.Vacancy;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class EmployeeDaoImpl extends DaoImplBase implements EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDaoImpl.class);

    @Override
    public Employee getByToken(String token) throws ServerException {
        LOGGER.debug("DAO get Employee by token {}", token);
        Employee employee;
        try (SqlSession sqlSession = getSession()) {
            employee = getEmployeeMapper(sqlSession).getEmployeeByToken(token);
            if (employee == null) {
                LOGGER.debug("Can't get Employee by token {}", token);
                throw new ServerException(ServerErrorCode.WRONG_TOKEN);
            }
        } catch (RuntimeException ex) {
            LOGGER.debug("Can't get Employee by token {}", token, ex);
            throw new ServerException(ServerErrorCode.WRONG_TOKEN);
        }
        return employee;
    }

    @Override
    public String insert(User user) throws ServerException {
        LOGGER.debug("DAO insert Employee {}", user);
        String token = UUID.randomUUID().toString();
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployeeMapper(sqlSession).insert((Employee) user);
                getUserSessionMapper(sqlSession).openSession(user, token);
            } catch (RuntimeException ex) {
                LOGGER.debug("Cant insert Employee {}", user);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.LOGIN_ALREADY_USED);
            }
            sqlSession.commit();
        }
        return token;
    }

    @Override
    public void changeEmployee(Employee employee) {
        LOGGER.debug("DAO change Employee {}", employee);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployeeMapper(sqlSession).updateEmployee(employee);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't change Employee {}", employee, ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public void delete(User user, String token) {
        LOGGER.debug("DAO delete Employee {}", user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployeeMapper(sqlSession).delete(user);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't delete Employee {}", user, ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public String login(String login, String password) throws ServerException {
        LOGGER.debug("DAO login Employee by login {} and password {}", login, password);
        String token = UUID.randomUUID().toString();
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployeeMapper(sqlSession).login(login, password, token);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't Login Employee by login {} and password {}", login, password, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.WRONG_LOGIN_OR_PASSWORD);
            }
            sqlSession.commit();
        }
        return token;
    }

    @Override
    public void logout(User user, String token) {
        LOGGER.debug("DAO logout Employee by token {}", token);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployeeMapper(sqlSession).logout(user);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't logout Employee by token {}", token, ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public void addSkill(Employee employee, Skill skill) {
        LOGGER.debug("DAO add Skill {} Employee {}", skill, employee);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSkillNameMapper(sqlSession).insertSkillName(skill);
                getEmployeeMapper(sqlSession).insertSkill(employee, skill);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't add Skill {} Employee {}", skill, employee, ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public void changeSkill(Employee employee, Skill skill) throws ServerException {
        LOGGER.debug("DAO change Skill {} Employee {}", skill, employee);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployeeMapper(sqlSession).updateSkill(employee, skill);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't change Skill {} Employee {}", skill, employee, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.SKILL_NOT_FOUND);
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteSkill(Employee employee, Skill skill) throws ServerException {
        LOGGER.debug("DAO delete Skill {} Employee {}", skill, employee);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployeeMapper(sqlSession).deleteSkill(employee, skill);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't delete Skill {} Employee {}", skill, employee, ex);
                sqlSession.rollback();
                throw new ServerException(ServerErrorCode.SKILL_NOT_FOUND);
            }
            sqlSession.commit();
        }
    }

    @Override
    public List<Vacancy> getVacanciesByEmployee(Employee employee, int type) throws ServerException {
        LOGGER.debug("DAO get Vacancies by Employee {}", employee);
        try (SqlSession sqlSession = getSession()) {
            return getEmployeeMapper(sqlSession).getVacanciesByEmployee(employee, type);
        } catch (RuntimeException ex) {
            LOGGER.debug("Can't get Vacancies by Employee {}", employee, ex);
            throw new ServerException(ServerErrorCode.WRONG_SELECTION_TYPE);
        }
    }

    @Override
    public void changeActivityProfile(Employee employee, boolean isActivity) {
        LOGGER.debug("DAO change activity profile Employee {} on {}", employee, isActivity);
        try (SqlSession sqlSession = getSession()) {
            try {
                getEmployeeMapper(sqlSession).changeActivityProfile(employee, isActivity);
            } catch (RuntimeException ex) {
                LOGGER.debug("Can't change activity profile Employee {} on {}", employee, isActivity, ex);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }
}
