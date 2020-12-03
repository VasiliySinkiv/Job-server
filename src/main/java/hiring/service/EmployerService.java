package hiring.service;

import com.google.gson.Gson;
import hiring.dao.EmployerDao;
import hiring.daoimpl.EmployerDaoImpl;
import hiring.dto.request.*;
import hiring.dto.response.*;
import hiring.exception.ServerErrorCode;
import hiring.exception.ServerException;
import hiring.model.Employee;
import hiring.model.Employer;
import hiring.model.Requirement;
import hiring.model.Vacancy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployerService {
    private EmployerDao employerDao = new EmployerDaoImpl();
    private Gson gson = new Gson();

    public String registerEmployer(String requestJsonString) {
        try {
            RegisterEmployerDtoRequest request = gson.fromJson(requestJsonString, RegisterEmployerDtoRequest.class);
            validate(request);
            Employer employer = new Employer(request.getLastName(), request.getFirstName(), request.getMiddleName(),
                    request.getLogin(), request.getPassword(), request.getCompany(), request.getAddress(), request.getEmail());
            RegisterDtoResponse response = new RegisterDtoResponse(employerDao.insert(employer));
            return gson.toJson(response);
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String getEmployer(String requestJsonString) {
        try {
            GetUserDtoRequest request = gson.fromJson(requestJsonString, GetUserDtoRequest.class);
            Employer employer = employerDao.getByToken(request.getToken());
            GetEmployerDtoResponse response = new GetEmployerDtoResponse(employer.getId(), employer.getLastName(),
                    employer.getFirstName(), employer.getMiddleName(), employer.getLogin(), employer.getPassword(),
                    employer.getCompany(), employer.getAddress(), employer.getEmail(), employer.getVacancies());
            return gson.toJson(response);
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String deleteEmployer(String requestJsonString) {
        try {
            DeleteDtoRequest request = gson.fromJson(requestJsonString, DeleteDtoRequest.class);
            Employer employer = employerDao.getByToken(request.getToken());
            employerDao.delete(employer, request.getToken());
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String loginEmployer(String requestJsonString) {
        try {
            LoginDtoRequest request = gson.fromJson(requestJsonString, LoginDtoRequest.class);
            validate(request);
            String token = employerDao.login(request.getLogin(), request.getPassword());
            return gson.toJson(new LoginDtoResponse(token));
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String logoutEmployer(String requestJsonString) {
        try {
            LogoutDtoRequest request = gson.fromJson(requestJsonString, LogoutDtoRequest.class);
            Employer employer = employerDao.getByToken(request.getToken());
            employerDao.logout(employer, request.getToken());
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String changeEmployer(String requestJsonString) {
        try {
            ChangeEmployerDtoRequest request = gson.fromJson(requestJsonString, ChangeEmployerDtoRequest.class);
            Employer employer = employerDao.getByToken(request.getToken());
            validate(request);
            employer.setLastName(request.getLastName());
            employer.setFirstName(request.getFirstName());
            employer.setMiddleName(request.getMiddleName());
            employer.setPassword(request.getPassword());
            employer.setCompany(request.getCompany());
            employer.setAddress(request.getAddress());
            employer.setEmail(request.getEmail());
            employerDao.changeEmployer(employer);
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String addVacancy(String requestJsonString) {
        try {
            AddVacancyDtoRequest request = gson.fromJson(requestJsonString, AddVacancyDtoRequest.class);
            Employer employer = employerDao.getByToken(request.getToken());
            validate(request);
            Set<Requirement> requirements = new HashSet<>();
            for (RequirementDtoRequest request1 : request.getRequirements()) {
                requirements.add(new Requirement(request1.getName(), request1.getLevel(), request1.isObligatory()));
            }
            Vacancy vacancy = new Vacancy(request.getName(), request.getSalary(), requirements, request.isActive());
            employerDao.addVacancy(employer, vacancy);
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String deleteVacancy(String requestJsonString) {
        try {
            DeleteVacancyDtoRequest request = gson.fromJson(requestJsonString, DeleteVacancyDtoRequest.class);
            Employer employer = employerDao.getByToken(request.getToken());
            Vacancy vacancy = request.getVacancy();
            employerDao.deleteVacancy(employer, vacancy);
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String addRequirement(String requestJsonString) {
        try {
            RequirementDtoRequest request = gson.fromJson(requestJsonString, RequirementDtoRequest.class);
            Employer employer = employerDao.getByToken(request.getToken());
            validate(request);
            Requirement requirement = new Requirement(request.getName(), request.getLevel(), request.isObligatory());
            Vacancy vacancy = request.getVacancy();
            employerDao.addRequirement(employer, vacancy, requirement);
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String changeRequirement(String requestJsonString) {
        try {
            RequirementDtoRequest request = gson.fromJson(requestJsonString, RequirementDtoRequest.class);
            Employer employer = employerDao.getByToken(request.getToken());
            validate(request);
            Requirement requirement = new Requirement(request.getName(), request.getLevel(), request.isObligatory());
            Vacancy vacancy = request.getVacancy();
            employerDao.changeRequirement(employer, vacancy, requirement);
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String deleteRequirement(String requestJsonString) {
        try {
            RequirementDtoRequest request = gson.fromJson(requestJsonString, RequirementDtoRequest.class);
            Employer employer = employerDao.getByToken(request.getToken());
            validate(request);
            Requirement requirement = new Requirement(request.getName(), request.getLevel(), request.isObligatory());
            Vacancy vacancy = request.getVacancy();
            employerDao.deleteRequirement(employer, vacancy, requirement);
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String getVacancy(String requestJsonString) {
        try {
            GetVacancyDtoRequest request = gson.fromJson(requestJsonString, GetVacancyDtoRequest.class);
            Employer employer = employerDao.getByToken(request.getToken());
            Vacancy vacancy = employer.getVacancies().get(request.getIndexVacancy());
            GetVacancyDtoResponse response = new GetVacancyDtoResponse(vacancy);
            return gson.toJson(response);
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String getAllVacancies(String requestJsonString) {
        try {
            GetAllVacanciesDtoRequest request = gson.fromJson(requestJsonString, GetAllVacanciesDtoRequest.class);
            Employer employer = employerDao.getByToken(request.getToken());
            validate(request);
            List<Vacancy> vacancies = employerDao.getAllVacancies(employer, request.getTypeVacancies());
            GetVacanciesDtoResponse response = new GetVacanciesDtoResponse(vacancies);
            return gson.toJson(response);
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String getEmployeesByVacancy(String requestJsonString) {
        try {
            GetEmployeesByVacancyDtoRequest request = gson.fromJson(requestJsonString, GetEmployeesByVacancyDtoRequest.class);
            Employer employer = employerDao.getByToken(request.getToken());
            validate(request);
            Vacancy vacancy = request.getVacancy();
            List<Employee> list = employerDao.getEmployeesByVacancy(vacancy, request.getSelectionType());
            GetEmployeesDtoResponse response = new GetEmployeesDtoResponse(list);
            return gson.toJson(response);
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String acceptEmployee(String requestJsonString) {
        try {
            AcceptEmployeeDtoRequest request = gson.fromJson(requestJsonString, AcceptEmployeeDtoRequest.class);
            Employer employer = employerDao.getByToken(request.getToken());
            Vacancy vacancy = request.getVacancy();
            Employee employee = request.getEmployee();
            employerDao.acceptEmployee(employer, vacancy, employee);
            return makeSuccessResponse();
        } catch (ServerException e) {
            return makeErrorResponse(e);
        }
    }

    public String clearDataBase(){
        employerDao.clearDataBase();
        return makeSuccessResponse();
    }

    private String makeSuccessResponse() {
        return gson.toJson(new SuccessDtoResponse());
    }

    private String makeErrorResponse(ServerException e) {
        return gson.toJson(new ErrorDtoResponse(e.getErrorCodes()));
    }

    private static void validate(RegisterEmployerDtoRequest request) throws ServerException {
        List<ServerErrorCode> errorCodes = new ArrayList<>();
        if (request.getCompany() == null || request.getCompany().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_COMPANY);
        if (request.getAddress() == null || request.getAddress().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_ADDRESS);
        if (request.getEmail() == null || request.getEmail().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_EMAIL);
        if (!request.getEmail().contains("@"))
            errorCodes.add(ServerErrorCode.WRONG_INVALID_EMAIL);
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

    private static void validate(ChangeEmployerDtoRequest request) throws ServerException {
        List<ServerErrorCode> errorCodes = new ArrayList<>();
        if (request.getCompany() == null || request.getCompany().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_COMPANY);
        if (request.getAddress() == null || request.getAddress().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_ADDRESS);
        if (request.getEmail() == null || request.getEmail().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_EMAIL);
        if (!request.getEmail().contains("@"))
            errorCodes.add(ServerErrorCode.WRONG_INVALID_EMAIL);
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

    private static void validate(AddVacancyDtoRequest request) throws ServerException {
        List<ServerErrorCode> errorCodes = new ArrayList<>();
        if (request.getName() == null || request.getName().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_VACANCY_NAME);
        if (request.getSalary() < 0)
            errorCodes.add(ServerErrorCode.WRONG_VACANCY_SALARY);
        if (request.getRequirements().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_REQUIREMENT);
        for (RequirementDtoRequest requirement : request.getRequirements())
            try {
                validate(requirement);
            }catch (ServerException e) {
                errorCodes.addAll(e.getErrorCodes());
            }
        if (!errorCodes.isEmpty()) throw new ServerException(errorCodes);
    }

    private static void validate(RequirementDtoRequest request) throws ServerException {
        List<ServerErrorCode> errorCodes = new ArrayList<>();
        if (request.getName() == null || request.getName().isEmpty())
            errorCodes.add(ServerErrorCode.WRONG_SKILL);
        if (request.getLevel() < 1 || request.getLevel() > 5)
            errorCodes.add(ServerErrorCode.WRONG_SKILL_LEVEL);
        if (!errorCodes.isEmpty()) throw new ServerException(errorCodes);
    }

    private static void validate(GetAllVacanciesDtoRequest request) throws ServerException {
        if (request.getTypeVacancies() < 1 || request.getTypeVacancies() > 3)
            throw new ServerException(ServerErrorCode.WRONG_TYPE_VACANCIES);
    }

    private static void validate(GetEmployeesByVacancyDtoRequest request) throws ServerException {
        if (request.getSelectionType() < 1 || request.getSelectionType() > 4)
            throw new ServerException(ServerErrorCode.WRONG_SELECTION_TYPE);
    }
}
