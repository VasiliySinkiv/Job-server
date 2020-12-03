package hiring.service;

import com.google.gson.Gson;
import hiring.dao.EmployeeDao;
import hiring.daoimpl.EmployeeDaoImpl;
import hiring.dto.request.*;
import hiring.dto.response.*;
import hiring.exception.ServerErrorCode;
import hiring.exception.ServerException;
import hiring.model.Employee;
import hiring.model.Skill;
import hiring.model.Vacancy;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private EmployeeDao employeeDao = new EmployeeDaoImpl();
    private Gson gson = new Gson();

    public String registerEmployee(String requestJsonString) {
        try {
            RegisterEmployeeDtoRequest request = gson.fromJson(requestJsonString, RegisterEmployeeDtoRequest.class);
            validate(request);
            Employee employee = new Employee(request.getLastName(), request.getFirstName(), request.getMiddleName(),
                    request.getLogin(), request.getPassword());
            RegisterDtoResponse response = new RegisterDtoResponse(employeeDao.insert(employee));
            return gson.toJson(response);
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String getEmployee(String requestJsonString) {
        try {
            GetUserDtoRequest request = gson.fromJson(requestJsonString, GetUserDtoRequest.class);
            Employee employee = employeeDao.getByToken(request.getToken());
            GetEmployeeDtoResponse response = new GetEmployeeDtoResponse(employee.getId(), employee.getLastName(),
                    employee.getFirstName(), employee.getMiddleName(), employee.getLogin(), employee.getPassword(),
                    employee.isActive(), employee.getSkills());
            return gson.toJson(response);
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String deleteEmployee(String requestJsonString) {
        try {
            DeleteDtoRequest request = gson.fromJson(requestJsonString, DeleteDtoRequest.class);
            Employee employee = employeeDao.getByToken(request.getToken());
            employeeDao.delete(employee, request.getToken());
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String loginEmployee(String requestJsonString) {
        try {
            LoginDtoRequest request = gson.fromJson(requestJsonString, LoginDtoRequest.class);
            validate(request);
            String token = employeeDao.login(request.getLogin(), request.getPassword());
            return gson.toJson(new LoginDtoResponse(token));
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String logoutEmployee(String requestJsonString) {
        try {
            LogoutDtoRequest request = gson.fromJson(requestJsonString, LogoutDtoRequest.class);
            Employee employee = employeeDao.getByToken(request.getToken());
            employeeDao.logout(employee, request.getToken());
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String changeEmployee(String requestJsonString) {
        try {
            ChangeEmployeeDtoRequest request = gson.fromJson(requestJsonString, ChangeEmployeeDtoRequest.class);
            Employee employee = employeeDao.getByToken(request.getToken());
            validate(request);
            employee.setLastName(request.getLastName());
            employee.setFirstName(request.getFirstName());
            employee.setMiddleName(request.getMiddleName());
            employee.setPassword(request.getPassword());
            employeeDao.changeEmployee(employee);
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String addEmployeeSkill(String requestJsonString) {
        try {
            EmployeeSkillDtoRequest request = gson.fromJson(requestJsonString, EmployeeSkillDtoRequest.class);
            Employee employee = employeeDao.getByToken(request.getToken());
            validate(request);
            Skill skill = new Skill(request.getName(), request.getLevel());
            employeeDao.addSkill(employee, skill);
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String getSkills(String requestJsonString) {
        try {
            EmployeeSkillDtoRequest request = gson.fromJson(requestJsonString, EmployeeSkillDtoRequest.class);
            Employee employee = employeeDao.getByToken(request.getToken());
            validate(request);
            Skill skill = new Skill(request.getName(), request.getLevel());
            employeeDao.addSkill(employee, skill);
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String changeEmployeeSkill(String requestJsonString) {
        try {
            EmployeeSkillDtoRequest request = gson.fromJson(requestJsonString, EmployeeSkillDtoRequest.class);
            Employee employee = employeeDao.getByToken(request.getToken());
            validate(request);
            Skill skill = new Skill(request.getName(), request.getLevel());
            employeeDao.changeSkill(employee, skill);
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String deleteEmployeeSkill(String requestJsonString) {
        try {
            EmployeeSkillDtoRequest request = gson.fromJson(requestJsonString, EmployeeSkillDtoRequest.class);
            Employee employee = employeeDao.getByToken(request.getToken());
            validate(request);
            Skill skill = new Skill(request.getName(), request.getLevel());
            employeeDao.deleteSkill(employee, skill);
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String getVacanciesByEmployee(String requestJsonString) {
        try {
            GetVacanciesByEmployeeDtoRequest request = gson.fromJson(requestJsonString, GetVacanciesByEmployeeDtoRequest.class);
            Employee employee = employeeDao.getByToken(request.getToken());
            List<Vacancy> vacancies = employeeDao.getVacanciesByEmployee(employee, request.getSelectionType());
            GetVacanciesDtoResponse response = new GetVacanciesDtoResponse(vacancies);
            return gson.toJson(response);
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String changeProfileActivity(String requestJsonString) {
        try {
            ChangeActivityProfileEmployeeDtoRequest request = gson.fromJson(requestJsonString,
                    ChangeActivityProfileEmployeeDtoRequest.class);
            Employee employee = employeeDao.getByToken(request.getToken());
            employeeDao.changeActivityProfile(employee, request.isActivity());
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    private String makeSuccessResponse() {
        return gson.toJson(new SuccessDtoResponse());
    }

    private String makeErrorResponse(ServerException e) {
        return gson.toJson(new ErrorDtoResponse(e.getErrorCodes()));
    }

    private static void validate(RegisterEmployeeDtoRequest request) throws ServerException {
        List<ServerErrorCode> errorCodes = new ArrayList<>();
        if (request.getFirstName() == null || request.getFirstName().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_FIRSTNAME);
        if (request.getLastName() == null || request.getLastName().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_LASTNAME);
        if (request.getLogin() == null || request.getLogin().isEmpty()) {
            errorCodes.add(ServerErrorCode.WRONG_LOGIN_EMPTY);
        } else if (request.getLogin().length() < 5)
            errorCodes.add(ServerErrorCode.WRONG_LOGIN_LENGTH);
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            errorCodes.add(ServerErrorCode.WRONG_PASSWORD);
        } else if (request.getPassword().length() < 6)
            errorCodes.add(ServerErrorCode.WRONG_PASSWORD_LENGTH);
        if (!errorCodes.isEmpty()) throw new ServerException(errorCodes);
    }

    private static void validate(ChangeEmployeeDtoRequest request) throws ServerException {
        List<ServerErrorCode> errorCodes = new ArrayList<>();
        if (request.getFirstName() == null || request.getFirstName().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_FIRSTNAME);
        if (request.getLastName() == null || request.getLastName().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_LASTNAME);
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            errorCodes.add(ServerErrorCode.WRONG_PASSWORD);
        } else if (request.getPassword().length() < 6)
            errorCodes.add(ServerErrorCode.WRONG_PASSWORD_LENGTH);
        if (!errorCodes.isEmpty()) throw new ServerException(errorCodes);
    }

    private static void validate(LoginDtoRequest request) throws ServerException {
        List<ServerErrorCode> errorCodes = new ArrayList<>();
        if (request.getLogin() == null || request.getLogin().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_LOGIN_EMPTY);
        if (request.getPassword() == null || request.getPassword().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_PASSWORD);
        if (!errorCodes.isEmpty()) throw new ServerException(errorCodes);
    }

    private static void validate(EmployeeSkillDtoRequest request) throws ServerException {
        List<ServerErrorCode> errorCodes = new ArrayList<>();
        if (request.getName() == null || request.getName().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_SKILL);
        if (request.getLevel() < 1 || request.getLevel() > 5)
            errorCodes.add(ServerErrorCode.WRONG_SKILL_LEVEL);
        if (!errorCodes.isEmpty()) throw new ServerException(errorCodes);
    }
}
