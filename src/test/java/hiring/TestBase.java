package hiring;

import com.google.gson.Gson;
import hiring.database.mybatis.utils.MyBatisUtils;
import hiring.dto.response.GetVacanciesDtoResponse;
import hiring.dto.response.RegisterDtoResponse;
import hiring.server.Server;
import hiring.dto.request.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestBase {

    private static boolean setUpIsDone = false;
    protected final Server server = new Server();
    protected final Gson gson = new Gson();

    @BeforeAll()
    public static void setUp() {
        if (!setUpIsDone) {
            boolean initSqlSessionFactory = MyBatisUtils.initSqlSessionFactory();
            if (!initSqlSessionFactory) {
                throw new RuntimeException("Can't create connection, stop");
            }
            setUpIsDone = true;
        }
    }

    @BeforeEach()
    public void clearDatabase() {
        server.startServer(null);
        server.clearDataBase();
    }

    @AfterEach()
    public void stopServer() {
        server.stopServer(null);
    }

    protected void sortGetVacanciesDtoResponse(GetVacanciesDtoResponse response) {
        response.getVacancies().sort((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
    }

    protected void sortGetAllVacanciesDtoResponse(GetVacanciesDtoResponse response) {
        response.getVacancies().sort((p1, p2) -> Integer.compare(p1.getId(), p2.getId()));
    }

    protected void registerTraineeDeveloper() {
        RegisterEmployeeDtoRequest registerRequest = new RegisterEmployeeDtoRequest("Sinkiv",
                "Vasili", "Vasilevich", "vasili1000", "1234567");
        String jsonResponse = server.registerEmployee(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class); //token
        String tokenEmployee = registerResponse.getToken();

        EmployeeSkillDtoRequest addSkillRequest = new EmployeeSkillDtoRequest(tokenEmployee, "Java", 1);
        server.addSkill(gson.toJson(addSkillRequest));
    }

    protected String registerJuniorDeveloper() {
        RegisterEmployeeDtoRequest registerRequest = new RegisterEmployeeDtoRequest("Sinkiv",
                "Vasili", "Vasilevich", "vasili1100", "1234567");
        String jsonResponse = server.registerEmployee(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class); //token
        String tokenEmployee = registerResponse.getToken();

        List<EmployeeSkillDtoRequest> requests = new ArrayList<>();
        requests.add(new EmployeeSkillDtoRequest(tokenEmployee, "Java", 2));
        requests.add(new EmployeeSkillDtoRequest(tokenEmployee, "C++", 4));

        for (EmployeeSkillDtoRequest request : requests)
            server.addSkill(gson.toJson(request));
        return tokenEmployee;
    }

    protected void  registerMiddleDeveloper() {
        RegisterEmployeeDtoRequest registerRequest = new RegisterEmployeeDtoRequest("Родионов", "Семён",
                "Даниилович", "Yannielle", "0VVKUh19");
        String jsonResponse = server.registerEmployee(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class); //token
        String tokenEmployee = registerResponse.getToken();

        List<EmployeeSkillDtoRequest> requests = new ArrayList<>();
        requests.add(new EmployeeSkillDtoRequest(tokenEmployee, "Java", 4));
        requests.add(new EmployeeSkillDtoRequest(tokenEmployee, "MySQL", 4));
        requests.add(new EmployeeSkillDtoRequest(tokenEmployee, "C++", 4));
        requests.add(new EmployeeSkillDtoRequest(tokenEmployee, "C#", 3));

        for (EmployeeSkillDtoRequest request : requests)
            server.addSkill(gson.toJson(request));
    }

    protected void registerSeniorDeveloper() {
        RegisterEmployeeDtoRequest registerRequest = new RegisterEmployeeDtoRequest("Ларин", "Лука",
                "Иванович", "Ctorgiant", "ZSfF0wjW");
        String jsonResponse = server.registerEmployee(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class); //token
        String tokenEmployee = registerResponse.getToken();

        List<EmployeeSkillDtoRequest> requests = new ArrayList<>();
        requests.add(new EmployeeSkillDtoRequest(tokenEmployee, "Java", 4));
        requests.add(new EmployeeSkillDtoRequest(tokenEmployee, "MySQL", 5));
        requests.add(new EmployeeSkillDtoRequest(tokenEmployee, "C++", 5));
        requests.add(new EmployeeSkillDtoRequest(tokenEmployee, "Scala", 5));
        requests.add(new EmployeeSkillDtoRequest(tokenEmployee, "JavaScript", 5));
        requests.add(new EmployeeSkillDtoRequest(tokenEmployee, "PHP", 5));
        requests.add(new EmployeeSkillDtoRequest(tokenEmployee, "PostgreSQL", 5));
        requests.add(new EmployeeSkillDtoRequest(tokenEmployee, "C#", 4));

        for (EmployeeSkillDtoRequest request : requests)
            server.addSkill(gson.toJson(request));
    }

    protected String registerEmployerWithVacancies() {
        RegisterEmployerDtoRequest registerEmployerRequest = new RegisterEmployerDtoRequest("Petrov",
                "Denis", "Vasilevich", "denis1200", "1234567","Company",
                "Omsk", "company@mail.ru");
        String jsonRegisterEmployerResponse = server.registerEmployer(gson.toJson(registerEmployerRequest));// Регистрация
        RegisterDtoResponse registerEmployerResponse = gson.fromJson(jsonRegisterEmployerResponse, RegisterDtoResponse.class);
        String tokenEmployer = registerEmployerResponse.getToken();

        Set<RequirementDtoRequest> addVacancyRequests1 = new HashSet<>();
        addVacancyRequests1.add(new RequirementDtoRequest("Java", 1, true));
        AddVacancyDtoRequest addVacancyRequest1 = new AddVacancyDtoRequest(tokenEmployer, "Trainee", 20000,
                addVacancyRequests1, true);
        server.addVacancy(gson.toJson(addVacancyRequest1)); //Добавление вакансии 1

        Set<RequirementDtoRequest> addVacancyRequests2 = new HashSet<>();
        addVacancyRequests2.add(new RequirementDtoRequest("Java", 3, true));
        AddVacancyDtoRequest addVacancyRequest2 = new AddVacancyDtoRequest(tokenEmployer, "Jun Java", 25000,
                addVacancyRequests2, true);
        server.addVacancy(gson.toJson(addVacancyRequest2)); //Добавление вакансии 2

        Set<RequirementDtoRequest> addVacancyRequests3 = new HashSet<>();
        addVacancyRequests3.add(new RequirementDtoRequest("Java", 1, true));
        addVacancyRequests3.add(new RequirementDtoRequest("C++", 3, true));
        AddVacancyDtoRequest addVacancyRequest3 = new AddVacancyDtoRequest(tokenEmployer, "Jun C++", 30000,
                addVacancyRequests3, true);
        server.addVacancy(gson.toJson(addVacancyRequest3)); //Добавление вакансии 3

        Set<RequirementDtoRequest> addVacancyRequests4 = new HashSet<>();
        addVacancyRequests4.add(new RequirementDtoRequest("Java", 1, true));
        addVacancyRequests4.add(new RequirementDtoRequest("C++", 2, true));
        addVacancyRequests4.add(new RequirementDtoRequest("C#", 3, true));
        AddVacancyDtoRequest addVacancyRequest4 = new AddVacancyDtoRequest(tokenEmployer, "Jun C#", 350000,
                addVacancyRequests4, true);
        server.addVacancy(gson.toJson(addVacancyRequest4)); //Добавление вакансии 4

        Set<RequirementDtoRequest> addVacancyRequests5 = new HashSet<>();
        addVacancyRequests5.add(new RequirementDtoRequest("Java", 2, true));
        addVacancyRequests5.add(new RequirementDtoRequest("C++", 3, true));
        addVacancyRequests5.add(new RequirementDtoRequest("C#", 2, false));
        AddVacancyDtoRequest addVacancyRequest5 = new AddVacancyDtoRequest(tokenEmployer, "Junior", 40000,
                addVacancyRequests5, true);
        server.addVacancy(gson.toJson(addVacancyRequest5)); //Добавление вакансии 5

        Set<RequirementDtoRequest> addVacancyRequests6 = new HashSet<>();
        addVacancyRequests6.add(new RequirementDtoRequest("Java", 5, true));
        addVacancyRequests6.add(new RequirementDtoRequest("MySQL", 5, true));
        addVacancyRequests6.add(new RequirementDtoRequest("C++", 5, true));
        addVacancyRequests6.add(new RequirementDtoRequest("C#", 5, false));
        addVacancyRequests6.add(new RequirementDtoRequest("Scala", 5, true));
        AddVacancyDtoRequest addVacancyRequest6 = new AddVacancyDtoRequest(tokenEmployer, "Senior Developer", 150000,
                addVacancyRequests6, false);
        server.addVacancy(gson.toJson(addVacancyRequest6)); //Добавление вакансии 3

        return tokenEmployer;
    }


}
