package hiring;

import hiring.exception.ServerErrorCode;
import hiring.dto.request.*;
import hiring.dto.response.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TestEmployer extends TestBase {

    @Test
    public void testRegisterEmployer() {
        RegisterEmployerDtoRequest registerRequest = new RegisterEmployerDtoRequest("Petrov",
                "Denis", "Vasilevich", "denis200", "1234567", "Company",
                "Omsk", "company@mail.ru");
        String jsonResponse = server.registerEmployer(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class);
        String token = registerResponse.getToken();

        String jsonResponseGetEmployer = server.getEmployer(gson.toJson(new GetUserDtoRequest(token)));
        GetEmployerDtoResponse getEmployerResponse = gson.fromJson(jsonResponseGetEmployer, GetEmployerDtoResponse.class);
        assertEquals(jsonResponse, gson.toJson(registerResponse));
        assertNotEquals(0, getEmployerResponse.getId());
        assertEquals(registerRequest.getLastName(), getEmployerResponse.getLastName());
        assertEquals(registerRequest.getFirstName(), getEmployerResponse.getFirstName());
        assertEquals(registerRequest.getMiddleName(), getEmployerResponse.getMiddleName());
        assertEquals(registerRequest.getLogin(), getEmployerResponse.getLogin());
        assertEquals(registerRequest.getPassword(), getEmployerResponse.getPassword());
        assertEquals(registerRequest.getCompany(), getEmployerResponse.getCompany());
        assertEquals(registerRequest.getAddress(), getEmployerResponse.getAddress());
        assertEquals(registerRequest.getEmail(), getEmployerResponse.getEmail());
        assertEquals(0, getEmployerResponse.getVacancies().size());
    }

    @Test
    public void testLoginEmployer() {
        RegisterEmployerDtoRequest registerRequest = new RegisterEmployerDtoRequest("Petrov",
                "Denis", "Vasilevich", "denis211", "1234567", "Company",
                "Omsk", "company@mail.ru");
        server.registerEmployer(gson.toJson(registerRequest));// Регистрация

        LoginDtoRequest loginRequest = new LoginDtoRequest(registerRequest.getLogin(), registerRequest.getPassword());
        String jsonLoginResponse = server.loginEmployer(gson.toJson(loginRequest));
        LoginDtoResponse loginResponse = gson.fromJson(jsonLoginResponse, LoginDtoResponse.class);
        assertEquals(jsonLoginResponse, gson.toJson(loginResponse));
    }

    @Test
    public void testLogoutEmployer() {
        RegisterEmployerDtoRequest registerRequest = new RegisterEmployerDtoRequest("Petrov",
                "Denis", "Vasilevich", "denis222", "1234567", "Company",
                "Omsk", "company@mail.ru");
        String jsonResponse = server.registerEmployer(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class);
        String token = registerResponse.getToken();

        LogoutDtoRequest logoutRequest = new LogoutDtoRequest(token);
        String jsonLogoutResponse = server.logoutEmployer(gson.toJson(logoutRequest));
        SuccessDtoResponse rightLogoutResponse = gson.fromJson(jsonLogoutResponse, SuccessDtoResponse.class);
        assertEquals(jsonLogoutResponse, gson.toJson(rightLogoutResponse));
    }

    @Test
    public void testDeleteEmployer() {
        RegisterEmployerDtoRequest registerRequest = new RegisterEmployerDtoRequest("Petrov",
                "Denis", "Vasilevich", "denis233", "1234567", "Company",
                "Omsk", "company@mail.ru");
        String jsonResponse = server.registerEmployer(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class);
        String token = registerResponse.getToken();

        DeleteDtoRequest deleteRequest = new DeleteDtoRequest(token);
        String jsonDeleteResponse = server.deleteEmployer(gson.toJson(deleteRequest));
        SuccessDtoResponse rightDeleteResponse = gson.fromJson(jsonDeleteResponse, SuccessDtoResponse.class);
        assertEquals(jsonDeleteResponse, gson.toJson(rightDeleteResponse));
    }

    @Test
    public void testEmployerWrongToken() {
        RegisterEmployerDtoRequest registerRequest = new RegisterEmployerDtoRequest("Petrov",
                "Denis","Company", "Omsk", "company@mail.ru", "Vasilevich",
                "denis244", "1234567");
        server.registerEmployer(gson.toJson(registerRequest));// Регистрация
        String wrongToken = "12345678910987654321";
        LogoutDtoRequest logoutRequest = new LogoutDtoRequest(wrongToken);
        String jsonLogoutResponse = server.logoutEmployer(gson.toJson(logoutRequest));
        ErrorDtoResponse errorResponse = gson.fromJson(jsonLogoutResponse, ErrorDtoResponse.class);
        assertEquals(jsonLogoutResponse, gson.toJson(errorResponse));
        assertEquals(1, errorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_TOKEN, errorResponse.getErrorCodes().get(0));
    }

    @Test
    public void testRegisterEmployerWrongLastName () {
        RegisterEmployerDtoRequest registerRequest = new RegisterEmployerDtoRequest("", "Denis",
                "Vasilevich", "denis255", "1234567", "Company", "Omsk",
                "company@mail.ru");
        String jsonRegisterResponse = server.registerEmployer(gson.toJson(registerRequest));// Регистрация
        ErrorDtoResponse registerErrorResponse = gson.fromJson(jsonRegisterResponse, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse, gson.toJson(registerErrorResponse));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_LASTNAME, registerErrorResponse.getErrorCodes().get(0));

        String jsonRegisterRequest1 = gson.toJson(new RegisterEmployerDtoRequest(null, "Denis",
                "Vasilevich", "denis266", "1234567", "Company", "Omsk",
                "company@mail.ru"));
        String jsonRegisterResponse1 = server.registerEmployer(jsonRegisterRequest1);// Регистрация
        ErrorDtoResponse registerErrorResponse1 = gson.fromJson(jsonRegisterResponse1, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse1, gson.toJson(registerErrorResponse1));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_LASTNAME, registerErrorResponse1.getErrorCodes().get(0));
    }

    @Test
    public void testRegisterEmployerWrongFirstName () {
        String jsonRegisterRequest = gson.toJson(new RegisterEmployerDtoRequest("Petrov", "",
                "Vasilevich", "denis277", "1234567", "Company", "Omsk",
                "company@mail.ru"));
        String jsonRegisterResponse = server.registerEmployer(jsonRegisterRequest);// Регистрация
        ErrorDtoResponse registerErrorResponse = gson.fromJson(jsonRegisterResponse, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse, gson.toJson(registerErrorResponse));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_FIRSTNAME, registerErrorResponse.getErrorCodes().get(0));

        String jsonRegisterRequest1 = gson.toJson(new RegisterEmployerDtoRequest("Petrov", null,
                "Vasilevich", "denis288", "1234567", "Company", "Omsk",
                "company@mail.ru"));
        String jsonRegisterResponse1 = server.registerEmployer(jsonRegisterRequest1);// Регистрация
        ErrorDtoResponse registerErrorResponse1 = gson.fromJson(jsonRegisterResponse1, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse1, gson.toJson(registerErrorResponse1));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_FIRSTNAME, registerErrorResponse1.getErrorCodes().get(0));
    }

    @Test
    public void testRegisterEmployerWrongLogin () {
        String jsonRegisterRequest = gson.toJson(new RegisterEmployerDtoRequest("Petrov", "Denis",
                "Vasilevich", "", "1234567", "Company", "Omsk",
                "company@mail.ru"));
        String jsonRegisterResponse = server.registerEmployer(jsonRegisterRequest);// Регистрация
        ErrorDtoResponse registerErrorResponse = gson.fromJson(jsonRegisterResponse, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse, gson.toJson(registerErrorResponse));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_LOGIN_EMPTY, registerErrorResponse.getErrorCodes().get(0));

        RegisterEmployerDtoRequest registerEmployerDtoRequest1 = new RegisterEmployerDtoRequest("Petrov", "Denis",
                "Vasilevich", null, "1234567", "Company", "Omsk",
                "company@mail.ru");
        String jsonRegisterRequest1 = gson.toJson(registerEmployerDtoRequest1);
        String jsonRegisterResponse1 = server.registerEmployer(jsonRegisterRequest1);// Регистрация
        ErrorDtoResponse registerErrorResponse1 = gson.fromJson(jsonRegisterResponse1, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse1, gson.toJson(registerErrorResponse1));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_LOGIN_EMPTY, registerErrorResponse1.getErrorCodes().get(0));

        RegisterEmployerDtoRequest registerEmployerDtoRequest2 = new RegisterEmployerDtoRequest( "Petrov",
                "Denis", "Vasilevich", "deni", "1234567", "Company",
                "Omsk", "company@mail.ru");
        String jsonRegisterRequest2 = gson.toJson(registerEmployerDtoRequest2);
        String jsonRegisterResponse2 = server.registerEmployer(jsonRegisterRequest2);// Регистрация
        ErrorDtoResponse registerErrorResponse2 = gson.fromJson(jsonRegisterResponse2, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse2, gson.toJson(registerErrorResponse2));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_LOGIN_LENGTH, registerErrorResponse2.getErrorCodes().get(0));
    }

    @Test
    public void testRegisterEmployerWrongPassword () {
        String jsonRegisterRequest = gson.toJson(new RegisterEmployerDtoRequest("Petrov", "Denis",
                "Vasilevich", "denis299", "","Company", "Omsk",
                "company@mail.ru"));
        String jsonRegisterResponse = server.registerEmployer(jsonRegisterRequest);// Регистрация
        ErrorDtoResponse registerErrorResponse = gson.fromJson(jsonRegisterResponse, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse, gson.toJson(registerErrorResponse));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_PASSWORD, registerErrorResponse.getErrorCodes().get(0));

        String jsonRegisterRequest1 = gson.toJson(new RegisterEmployerDtoRequest("Petrov", "Denis",
                "Vasilevich", "denis300", null, "Company", "Omsk",
                "company@mail.ru"));
        String jsonRegisterResponse1 = server.registerEmployer(jsonRegisterRequest1);// Регистрация
        ErrorDtoResponse registerErrorResponse1 = gson.fromJson(jsonRegisterResponse1, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse1, gson.toJson(registerErrorResponse1));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_PASSWORD, registerErrorResponse1.getErrorCodes().get(0));

        String jsonRegisterRequest2 = gson.toJson(new RegisterEmployerDtoRequest("Petrov", "Denis",
                "Vasilevich", "denis311", "12345", "Company", "Omsk",
                "company@mail.ru"));
        String jsonRegisterResponse2 = server.registerEmployer(jsonRegisterRequest2);// Регистрация
        ErrorDtoResponse registerErrorResponse2 = gson.fromJson(jsonRegisterResponse2, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse2, gson.toJson(registerErrorResponse2));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_PASSWORD_LENGTH, registerErrorResponse2.getErrorCodes().get(0));
    }

    @Test
    public void testLoginEmployerWrongLoginOrPassword () {
        String jsonRegisterRequest = gson.toJson(new RegisterEmployerDtoRequest("Petrov", "Denis",
                "Vasilevich", "denis322", "123456789", "Company", "Omsk",
                "company@mail.ru"));
        server.registerEmployer(jsonRegisterRequest);// Регистрация

        LoginDtoRequest loginRequest = new LoginDtoRequest("denis5555", "1234567");
        String jsonLoginResponse = server.loginEmployer(gson.toJson(loginRequest));
        ErrorDtoResponse loginErrorResponse = gson.fromJson(jsonLoginResponse, ErrorDtoResponse.class);
        assertEquals(jsonLoginResponse, gson.toJson(loginErrorResponse));
        assertEquals(1, loginErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_LOGIN_OR_PASSWORD, loginErrorResponse.getErrorCodes().get(0));

        LoginDtoRequest loginRequest1 = new LoginDtoRequest("", "123456789");
        String jsonLoginResponse1 = server.loginEmployer(gson.toJson(loginRequest1));
        ErrorDtoResponse loginErrorResponse1 = gson.fromJson(jsonLoginResponse1, ErrorDtoResponse.class);
        assertEquals(jsonLoginResponse1, gson.toJson(loginErrorResponse1));
        assertEquals(1, loginErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_LOGIN_EMPTY, loginErrorResponse1.getErrorCodes().get(0));

        LoginDtoRequest loginRequest2 = new LoginDtoRequest(null, "123456789");
        String jsonLoginResponse2 = server.loginEmployer(gson.toJson(loginRequest2));
        ErrorDtoResponse loginErrorResponse2 = gson.fromJson(jsonLoginResponse2, ErrorDtoResponse.class);
        assertEquals(jsonLoginResponse2, gson.toJson(loginErrorResponse2));
        assertEquals(1, loginErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_LOGIN_EMPTY, loginErrorResponse2.getErrorCodes().get(0));

        LoginDtoRequest loginRequest3 = new LoginDtoRequest("denis322", "");
        String jsonLoginResponse3 = server.loginEmployer(gson.toJson(loginRequest3));
        ErrorDtoResponse loginErrorResponse3 = gson.fromJson(jsonLoginResponse3, ErrorDtoResponse.class);
        assertEquals(jsonLoginResponse3, gson.toJson(loginErrorResponse3));
        assertEquals(1, loginErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_PASSWORD, loginErrorResponse3.getErrorCodes().get(0));

        LoginDtoRequest loginRequest4 = new LoginDtoRequest("denis322", null);
        String jsonLoginResponse4 = server.loginEmployer(gson.toJson(loginRequest4));
        ErrorDtoResponse loginErrorResponse4 = gson.fromJson(jsonLoginResponse4, ErrorDtoResponse.class);
        assertEquals(jsonLoginResponse4, gson.toJson(loginErrorResponse4));
        assertEquals(1, loginErrorResponse.getErrorCodes().size());
        Assert.assertEquals(ServerErrorCode.WRONG_PASSWORD, loginErrorResponse4.getErrorCodes().get(0));
    }

    @Test
    public void testChangeEmployer() {
        RegisterEmployerDtoRequest registerRequest = new RegisterEmployerDtoRequest("Petrov",
                "Denis", "Vasilevich", "denis333", "1234567", "Company",
                "Omsk", "company@mail.ru");
        String jsonResponse = server.registerEmployer(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class);
        String token = registerResponse.getToken();
        ChangeEmployerDtoRequest changeRequest = new ChangeEmployerDtoRequest(token, "Ivanov",
                "Ivan", null, "abcdef", "NoName", "Novosibirsk",
                "noname@mail.ru");
        String jsonResponse1 = server.changeEmployer(gson.toJson(changeRequest));// Изменение регистрационных данных
        SuccessDtoResponse successResponse = gson.fromJson(jsonResponse1, SuccessDtoResponse.class);

        String jsonResponseGetEmployer = server.getEmployer(gson.toJson(new GetUserDtoRequest(token)));
        GetEmployerDtoResponse getEmployerResponse = gson.fromJson(jsonResponseGetEmployer, GetEmployerDtoResponse.class);
        assertEquals(jsonResponse1, gson.toJson(successResponse));
        assertEquals(changeRequest.getLastName(), getEmployerResponse.getLastName());
        assertEquals(changeRequest.getFirstName(), getEmployerResponse.getFirstName());
        assertNull(getEmployerResponse.getMiddleName());
        assertEquals(registerRequest.getLogin(), getEmployerResponse.getLogin());
        assertEquals(changeRequest.getPassword(), getEmployerResponse.getPassword());
        assertEquals(changeRequest.getCompany(), getEmployerResponse.getCompany());
        assertEquals(changeRequest.getAddress(), getEmployerResponse.getAddress());
        assertEquals(changeRequest.getEmail(), getEmployerResponse.getEmail());
        assertEquals(0, getEmployerResponse.getVacancies().size());
    }

    @Test
    public void testAddAndDeleteVacancy() {
        RegisterEmployerDtoRequest registerRequest = new RegisterEmployerDtoRequest("Petrov",
                "Denis", "Vasilevich", "denis344", "1234567","Company",
                "Omsk", "company@mail.ru");
        String jsonResponse = server.registerEmployer(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class);
        String token = registerResponse.getToken();

        Set<RequirementDtoRequest> requests = new HashSet<>();
        requests.add(new RequirementDtoRequest("Java", 3, true));
        requests.add(new RequirementDtoRequest("C++", 1, false));
        AddVacancyDtoRequest addVacancyDtoRequest = new AddVacancyDtoRequest(token, "Devs", 100500,
                requests, true);
        String jsonAddVacancyResponse = server.addVacancy(gson.toJson(addVacancyDtoRequest)); //Добавление вакансии
        SuccessDtoResponse rightAddVacancyResponse = gson.fromJson(jsonAddVacancyResponse, SuccessDtoResponse.class);

        String jsonResponseGetEmployer = server.getEmployer(gson.toJson(new GetUserDtoRequest(token)));
        GetEmployerDtoResponse getEmployerDtoResponse = gson.fromJson(jsonResponseGetEmployer, GetEmployerDtoResponse.class);
        assertEquals(jsonAddVacancyResponse, gson.toJson(rightAddVacancyResponse));
        assertEquals(1, getEmployerDtoResponse.getVacancies().size());
        assertEquals(2, getEmployerDtoResponse.getVacancies().get(0).getRequirements().size());

        String jsonResponseGetVacancy = server.getVacancy(gson.toJson(new GetVacancyDtoRequest(token, 0)));
        GetVacancyDtoResponse getVacancyDtoResponse = gson.fromJson(jsonResponseGetVacancy, GetVacancyDtoResponse.class);

        assertEquals("Devs", getVacancyDtoResponse.getVacancy().getName());
        assertEquals(100500, getVacancyDtoResponse.getVacancy().getSalary());
        assertTrue(getVacancyDtoResponse.getVacancy().isActivity());

        DeleteVacancyDtoRequest deleteVacancyDtoRequest = new DeleteVacancyDtoRequest(token, getVacancyDtoResponse.getVacancy());
        String jsonDeleteVacancyResponse = server.deleteVacancy(gson.toJson(deleteVacancyDtoRequest));
        SuccessDtoResponse rightDeleteVacancyResponse = gson.fromJson(jsonDeleteVacancyResponse, SuccessDtoResponse.class);
        String jsonResponseGetEmployer1 = server.getEmployer(gson.toJson(new GetUserDtoRequest(token)));
        GetEmployerDtoResponse getEmployerDtoResponse1 = gson.fromJson(jsonResponseGetEmployer1, GetEmployerDtoResponse.class);
        assertEquals(jsonDeleteVacancyResponse, gson.toJson(rightDeleteVacancyResponse));
        assertEquals(0, getEmployerDtoResponse1.getVacancies().size());
    }

    @Test
    public void testAddRequirement() {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("Petrov",
                "Denis", "Vasilevich", "denis355", "1234567", "Company",
                "Omsk", "company@mail.ru");
        String jsonResponse = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));// Регистрация
        RegisterDtoResponse registerEmployerDtoResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class);
        String token = registerEmployerDtoResponse.getToken();

        Set<RequirementDtoRequest> addRequirementDtoRequests = new HashSet<>();
        addRequirementDtoRequests.add(new RequirementDtoRequest("Java", 3, true));
        AddVacancyDtoRequest addVacancyDtoRequest = new AddVacancyDtoRequest(token, "Devs", 100500,
                addRequirementDtoRequests, true);
        server.addVacancy(gson.toJson(addVacancyDtoRequest));
        String jsonResponseGetVacancy = server.getVacancy(gson.toJson(new GetVacancyDtoRequest(token, 0)));
        GetVacancyDtoResponse getVacancyDtoResponse = gson.fromJson(jsonResponseGetVacancy, GetVacancyDtoResponse.class);
        RequirementDtoRequest requirementDtoRequest = new RequirementDtoRequest(token, getVacancyDtoResponse.getVacancy(),
                "C++", 5, false);
        String jsonAddRequirementRequest = gson.toJson(requirementDtoRequest);
        String jsonAddRequirementResponse = server.addRequirement(jsonAddRequirementRequest);
        SuccessDtoResponse rightAddRequirementResponse = gson.fromJson(jsonAddRequirementResponse, SuccessDtoResponse.class);

        String jsonResponseGetVacancy1 = server.getVacancy(gson.toJson(new GetVacancyDtoRequest(token, 0)));
        GetVacancyDtoResponse getVacancyDtoResponse1 = gson.fromJson(jsonResponseGetVacancy1, GetVacancyDtoResponse.class);
        assertEquals(jsonAddRequirementResponse, gson.toJson(rightAddRequirementResponse));
        assertEquals(2, getVacancyDtoResponse1.getVacancy().getRequirements().size());
    }

    @Test
    public void testChangeRequirement() {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest( "Petrov",
                "Denis", "Vasilevich", "denis366", "1234567", "Company",
                "Omsk", "company@mail.ru");
        String jsonResponse = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));// Регистрация
        RegisterDtoResponse registerEmployerDtoResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class);
        String token = registerEmployerDtoResponse.getToken();

        Set<RequirementDtoRequest> addRequirementDtoRequests = new HashSet<>();
        addRequirementDtoRequests.add(new RequirementDtoRequest("Java", 3, true));
        AddVacancyDtoRequest addVacancyDtoRequest = new AddVacancyDtoRequest(token, "Devs", 100500,
                addRequirementDtoRequests, true);
        server.addVacancy(gson.toJson(addVacancyDtoRequest));
        String jsonResponseGetVacancy = server.getVacancy(gson.toJson(new GetVacancyDtoRequest(token, 0)));
        GetVacancyDtoResponse getVacancyDtoResponse = gson.fromJson(jsonResponseGetVacancy, GetVacancyDtoResponse.class);
        RequirementDtoRequest requirementDtoRequest = new RequirementDtoRequest(token, getVacancyDtoResponse.getVacancy(),
                "Java", 5, false);
        String jsonChangeRequirementRequest = gson.toJson(requirementDtoRequest);
        String jsonChangeRequirementResponse = server.changeRequirement(jsonChangeRequirementRequest);
        SuccessDtoResponse rightChangeRequirementResponse = gson.fromJson(jsonChangeRequirementResponse, SuccessDtoResponse.class);
        assertEquals(jsonChangeRequirementResponse, gson.toJson(rightChangeRequirementResponse));
        assertEquals(1, getVacancyDtoResponse.getVacancy().getRequirements().size());
    }

    @Test
    public void testDeleteRequirement() {
        RegisterEmployerDtoRequest registerEmployerDtoRequest = new RegisterEmployerDtoRequest("Petrov",
                "Denis", "Vasilevich", "denis377", "1234567", "Company",
                "Omsk", "company@mail.ru");
        String jsonResponse = server.registerEmployer(gson.toJson(registerEmployerDtoRequest));// Регистрация
        RegisterDtoResponse registerEmployerDtoResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class);
        String token = registerEmployerDtoResponse.getToken();

        Set<RequirementDtoRequest> addRequirementDtoRequests = new HashSet<>();
        addRequirementDtoRequests.add(new RequirementDtoRequest("Java", 3, true));
        AddVacancyDtoRequest addVacancyDtoRequest = new AddVacancyDtoRequest(token, "Devs", 100500,
                addRequirementDtoRequests, true);
        server.addVacancy(gson.toJson(addVacancyDtoRequest));

        String jsonResponseGetVacancy = server.getVacancy(gson.toJson(new GetVacancyDtoRequest(token, 0)));
        GetVacancyDtoResponse getVacancyDtoResponse = gson.fromJson(jsonResponseGetVacancy, GetVacancyDtoResponse.class);
        RequirementDtoRequest requirementDtoRequest = new RequirementDtoRequest(token, getVacancyDtoResponse.getVacancy(), "Java",
                5, false);
        String jsonDeleteRequirementRequest = gson.toJson(requirementDtoRequest);
        String jsonDeleteRequirementResponse = server.deleteRequirement(jsonDeleteRequirementRequest);
        SuccessDtoResponse rightDeleteRequirementResponse = gson.fromJson(jsonDeleteRequirementResponse, SuccessDtoResponse.class);
        String jsonResponseGetVacancy1 = server.getVacancy(gson.toJson(new GetVacancyDtoRequest(token, 0)));
        GetVacancyDtoResponse getVacancyDtoResponse1 = gson.fromJson(jsonResponseGetVacancy1, GetVacancyDtoResponse.class);
        assertEquals(jsonDeleteRequirementResponse, gson.toJson(rightDeleteRequirementResponse));
        assertEquals(0, getVacancyDtoResponse1.getVacancy().getRequirements().size());
    }


    @Test
    public void testGetAllVacanciesBySelectionType() {

        String token = registerEmployerWithVacancies();

        //get all Vacancies
        GetAllVacanciesDtoRequest getAllVacanciesRequest = new GetAllVacanciesDtoRequest(token, 1);
        String getJsonResponse = server.getAllVacancies(gson.toJson(getAllVacanciesRequest));
        GetVacanciesDtoResponse response = gson.fromJson(getJsonResponse, GetVacanciesDtoResponse.class);
        assertEquals(6, response.getVacancies().size());
        //get activity Vacancies
        GetAllVacanciesDtoRequest getAllVacanciesRequest2 = new GetAllVacanciesDtoRequest(token, 2);
        String getJsonResponse2 = server.getAllVacancies(gson.toJson(getAllVacanciesRequest2));
        GetVacanciesDtoResponse response2 = gson.fromJson(getJsonResponse2, GetVacanciesDtoResponse.class);
        assertEquals(5, response2.getVacancies().size());
        //get no activity Vacancies
        GetAllVacanciesDtoRequest getAllVacanciesRequest3 = new GetAllVacanciesDtoRequest(token, 3);
        String getJsonResponse3 = server.getAllVacancies(gson.toJson(getAllVacanciesRequest3));
        GetVacanciesDtoResponse response3 = gson.fromJson(getJsonResponse3, GetVacanciesDtoResponse.class);
        assertEquals(1, response3.getVacancies().size());
    }

    @Test
    public void testGetEmployeesByVacancyBySelectionType1() {
        registerTraineeDeveloper();
        registerMiddleDeveloper();
        registerSeniorDeveloper();

        String token = registerEmployerWithVacancies();

        GetAllVacanciesDtoRequest getVacanciesRequest = new GetAllVacanciesDtoRequest(token, 1);
        String jsonGetVacanciesResponse = server.getAllVacancies(gson.toJson(getVacanciesRequest));
        GetVacanciesDtoResponse response = gson.fromJson(jsonGetVacanciesResponse, GetVacanciesDtoResponse.class);
        // Type 1 - всех работников, имеющих все необходимые для этой вакансии умения на уровне не ниже уровня, указанного в требовании
        sortGetVacanciesDtoResponse(response);
        GetEmployeesByVacancyDtoRequest request = new GetEmployeesByVacancyDtoRequest(token, response.getVacancies().get(0), 1);
        String jsonGetVacancyEmployees = server.getEmployeesByVacancy(gson.toJson(request));
        GetEmployeesDtoResponse getEmployeesDtoResponse = gson.fromJson(jsonGetVacancyEmployees, GetEmployeesDtoResponse.class);
        assertEquals(3, getEmployeesDtoResponse.getEmployees().size());
    }

    @Test
    public void testGetEmployeesByVacancyBySelectionType2() {
        registerTraineeDeveloper();
        registerMiddleDeveloper();
        registerSeniorDeveloper();

        String token = registerEmployerWithVacancies();

        GetAllVacanciesDtoRequest getVacanciesRequest = new GetAllVacanciesDtoRequest(token, 1);
        String jsonGetVacanciesResponse = server.getAllVacancies(gson.toJson(getVacanciesRequest));
        GetVacanciesDtoResponse response = gson.fromJson(jsonGetVacanciesResponse, GetVacanciesDtoResponse.class);
        // Type 2 - всех работников, имеющих все обязательные для этой вакансии умения на уровне не ниже уровня, указанного в требовании
        sortGetVacanciesDtoResponse(response);
        GetEmployeesByVacancyDtoRequest request2 = new GetEmployeesByVacancyDtoRequest(token, response.getVacancies().get(1), 2);
        String jsonGetVacancyEmployees2 = server.getEmployeesByVacancy(gson.toJson(request2));
        GetEmployeesDtoResponse getEmployeesDtoResponse2 = gson.fromJson(jsonGetVacancyEmployees2, GetEmployeesDtoResponse.class);
        assertEquals(2, getEmployeesDtoResponse2.getEmployees().size());
    }

    @Test
    public void testGetEmployeesByVacancyBySelectionType3() {
        registerTraineeDeveloper();
        registerMiddleDeveloper();
        registerSeniorDeveloper();

        String token = registerEmployerWithVacancies();

        GetAllVacanciesDtoRequest getVacanciesRequest = new GetAllVacanciesDtoRequest(token, 1);
        String jsonGetVacanciesResponse = server.getAllVacancies(gson.toJson(getVacanciesRequest));
        GetVacanciesDtoResponse response = gson.fromJson(jsonGetVacanciesResponse, GetVacanciesDtoResponse.class);
        // Type 3 - всех работников, имеющих все необходимые для этой вакансии умения на любом уровне
        sortGetVacanciesDtoResponse(response);
        GetEmployeesByVacancyDtoRequest request3 = new GetEmployeesByVacancyDtoRequest(token, response.getVacancies().get(2), 3);
        String jsonGetVacancyEmployees3 = server.getEmployeesByVacancy(gson.toJson(request3));
        GetEmployeesDtoResponse getEmployeesDtoResponse3 = gson.fromJson(jsonGetVacancyEmployees3, GetEmployeesDtoResponse.class);
        assertEquals(2, getEmployeesDtoResponse3.getEmployees().size());
    }

    @Test
    public void testGetEmployeesByVacancyBySelectionType4() {
        registerTraineeDeveloper();
        registerMiddleDeveloper();
        registerSeniorDeveloper();

        String token = registerEmployerWithVacancies();

        GetAllVacanciesDtoRequest getVacanciesRequest = new GetAllVacanciesDtoRequest(token, 1);
        String jsonGetVacanciesResponse = server.getAllVacancies(gson.toJson(getVacanciesRequest));
        GetVacanciesDtoResponse response = gson.fromJson(jsonGetVacanciesResponse, GetVacanciesDtoResponse.class);
        // Type 4 - всех работников, имеющих хотя бы одно необходимое для этой вакансии умение на уровне не ниже уровня, указанного в требовании
        sortGetVacanciesDtoResponse(response);
        GetEmployeesByVacancyDtoRequest request4 = new GetEmployeesByVacancyDtoRequest(token, response.getVacancies().get(1), 4);
        String jsonGetVacancyEmployees4 = server.getEmployeesByVacancy(gson.toJson(request4));
        GetEmployeesDtoResponse getEmployeesDtoResponse4 = gson.fromJson(jsonGetVacancyEmployees4, GetEmployeesDtoResponse.class);
        assertEquals(2, getEmployeesDtoResponse4.getEmployees().size());
    }

    @Test
    public void testHireEmployeeAndDeactivateVacancy() {
        registerTraineeDeveloper();
        registerMiddleDeveloper();
        registerSeniorDeveloper();

        String token = registerEmployerWithVacancies();

        GetAllVacanciesDtoRequest getVacanciesRequest = new GetAllVacanciesDtoRequest(token, 1);
        String jsonGetVacanciesResponse = server.getAllVacancies(gson.toJson(getVacanciesRequest));
        GetVacanciesDtoResponse response = gson.fromJson(jsonGetVacanciesResponse, GetVacanciesDtoResponse.class);

        GetEmployeesByVacancyDtoRequest request = new GetEmployeesByVacancyDtoRequest(token, response.getVacancies().get(0), 1);
        String jsonGetVacancyEmployees = server.getEmployeesByVacancy(gson.toJson(request));
        GetEmployeesDtoResponse getEmployeesDtoResponse = gson.fromJson(jsonGetVacancyEmployees, GetEmployeesDtoResponse.class);
        assertEquals(3, getEmployeesDtoResponse.getEmployees().size());

        AcceptEmployeeDtoRequest acceptRequest =
                new AcceptEmployeeDtoRequest(token, response.getVacancies().get(0), getEmployeesDtoResponse.getEmployees().get(0));
        String jsonAcceptResponse = server.acceptEmployee(gson.toJson(acceptRequest));
        SuccessDtoResponse rightAcceptResponse = gson.fromJson(jsonAcceptResponse, SuccessDtoResponse.class);
        assertEquals(jsonAcceptResponse, gson.toJson(rightAcceptResponse));

        GetAllVacanciesDtoRequest getVacanciesRequest2 = new GetAllVacanciesDtoRequest(token, 1);
        String jsonGetVacanciesResponse2 = server.getAllVacancies(gson.toJson(getVacanciesRequest2));
        GetVacanciesDtoResponse response2 = gson.fromJson(jsonGetVacanciesResponse2, GetVacanciesDtoResponse.class);
        assertNotEquals(response.getVacancies().get(0).isActivity(), response2.getVacancies().get(0).isActivity());

        GetEmployeesByVacancyDtoRequest request3 = new GetEmployeesByVacancyDtoRequest(token, response.getVacancies().get(0), 1);
        String jsonGetVacancyEmployees3 = server.getEmployeesByVacancy(gson.toJson(request3));
        GetEmployeesDtoResponse getEmployeesDtoResponse3 = gson.fromJson(jsonGetVacancyEmployees3, GetEmployeesDtoResponse.class);
        assertEquals(2, getEmployeesDtoResponse3.getEmployees().size());
    }
}
