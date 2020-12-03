package hiring;

import hiring.exception.ServerErrorCode;
import hiring.dto.request.*;
import hiring.dto.response.*;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class TestEmployee extends TestBase {

    @Test
    public void testRegisterEmployee() {
        RegisterEmployeeDtoRequest registerRequest = new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "vasili11", "1234567");
        String jsonRegisterResponse = server.registerEmployee(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonRegisterResponse, RegisterDtoResponse.class); //token
        String token = registerResponse.getToken();
        String jsonResponseGetEmployee = server.getEmployee(gson.toJson(new GetUserDtoRequest(token)));
        GetEmployeeDtoResponse getEmployeeResponse = gson.fromJson(jsonResponseGetEmployee, GetEmployeeDtoResponse.class);
        assertEquals(jsonRegisterResponse, gson.toJson(registerResponse));
        assertEquals(registerRequest.getLastName(), getEmployeeResponse.getLastName());
        assertEquals(registerRequest.getFirstName(), getEmployeeResponse.getFirstName());
        assertEquals(registerRequest.getMiddleName(), getEmployeeResponse.getMiddleName());
        assertEquals(registerRequest.getLogin(), getEmployeeResponse.getLogin());
        assertEquals(registerRequest.getPassword(), getEmployeeResponse.getPassword());
        assertEquals(0, getEmployeeResponse.getSkills().size());
        assertTrue(getEmployeeResponse.isActive());
    }

    @Test
    public void testLoginEmployee() {
        RegisterEmployeeDtoRequest registerRequest = new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "vasili22", "1234567");
        server.registerEmployee(gson.toJson(registerRequest));// Регистрация
        LoginDtoRequest loginRequest = new LoginDtoRequest(registerRequest.getLogin(), registerRequest.getPassword());
        String jsonLoginResponse = server.loginEmployee(gson.toJson(loginRequest));
        LoginDtoResponse loginResponse = gson.fromJson(jsonLoginResponse, LoginDtoResponse.class);
        assertEquals(jsonLoginResponse, gson.toJson(loginResponse));
    }

    @Test
    public void testLogoutEmployee() {
        String jsonRegisterResponse = server.registerEmployee(gson.toJson(new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "vasili33", "1234567")));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonRegisterResponse, RegisterDtoResponse.class); //token
        String token = registerResponse.getToken();
        LogoutDtoRequest logoutRequest = new LogoutDtoRequest(token);
        String jsonLogoutResponse = server.logoutEmployee(gson.toJson(logoutRequest));
        SuccessDtoResponse rightLogoutResponse = gson.fromJson(jsonLogoutResponse, SuccessDtoResponse.class);
        assertEquals(jsonLogoutResponse, gson.toJson(rightLogoutResponse));
    }

    @Test
    public void testDeleteEmployee() {
        String jsonRegisterResponse = server.registerEmployee(gson.toJson(new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "vasili44", "1234567")));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonRegisterResponse, RegisterDtoResponse.class); //token
        String token = registerResponse.getToken();
        DeleteDtoRequest deleteRequest = new DeleteDtoRequest(token);
        String jsonDeleteResponse = server.deleteEmployee(gson.toJson(deleteRequest));
        SuccessDtoResponse rightDeleteResponse = gson.fromJson(jsonDeleteResponse, SuccessDtoResponse.class);
        assertEquals(jsonDeleteResponse, gson.toJson(rightDeleteResponse));
    }

    @Test
    public void testEmployeeWrongToken() {
        String jsonRegisterRequest = gson.toJson(new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "vasili55", "1234567"));
        server.registerEmployee(jsonRegisterRequest);// Регистрация
        String wrongToken = "12345678910987654321";
        LogoutDtoRequest logoutRequest = new LogoutDtoRequest(wrongToken);
        String jsonLogoutResponse = server.logoutEmployee(gson.toJson(logoutRequest));
        ErrorDtoResponse errorResponse = gson.fromJson(jsonLogoutResponse, ErrorDtoResponse.class);
        assertEquals(jsonLogoutResponse, gson.toJson(errorResponse));
        assertEquals(1, errorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_TOKEN, errorResponse.getErrorCodes().get(0));

    }

    @Test
    public void testRegisterEmployeeWrongLastname () {
        String jsonRegisterRequest = gson.toJson(new RegisterEmployeeDtoRequest("",
                "Ivan", "Ivanovich", "vasili66", "1234567"));
        String jsonRegisterResponse = server.registerEmployee(jsonRegisterRequest);
        ErrorDtoResponse registerErrorResponse = gson.fromJson(jsonRegisterResponse, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse, gson.toJson(registerErrorResponse));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_LASTNAME, registerErrorResponse.getErrorCodes().get(0));

        String jsonRegisterRequest1 = gson.toJson(new RegisterEmployeeDtoRequest(null,
                "Ivan", "Ivanovich", "vasili77", "1234567"));
        String jsonRegisterResponse1 = server.registerEmployee(jsonRegisterRequest1);
        ErrorDtoResponse registerErrorResponse1 = gson.fromJson(jsonRegisterResponse1, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse1, gson.toJson(registerErrorResponse1));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_LASTNAME, registerErrorResponse1.getErrorCodes().get(0));
    }

    @Test
    public void testRegisterEmployeeWrongFirstName () {
        String jsonRegisterRequest = gson.toJson(new RegisterEmployeeDtoRequest("Ivanov",
                "", "Ivanovich", "vasili88", "1234567"));
        String jsonRegisterResponse = server.registerEmployee(jsonRegisterRequest);
        ErrorDtoResponse registerErrorResponse = gson.fromJson(jsonRegisterResponse, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse, gson.toJson(registerErrorResponse));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_FIRSTNAME, registerErrorResponse.getErrorCodes().get(0));

        String jsonRegisterRequest1 = gson.toJson(new RegisterEmployeeDtoRequest("Ivanov",
                null, "Ivanovich", "vasili99", "1234567"));
        String jsonRegisterResponse1 = server.registerEmployee(jsonRegisterRequest1);
        ErrorDtoResponse registerErrorResponse1 = gson.fromJson(jsonRegisterResponse1, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse1, gson.toJson(registerErrorResponse1));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_FIRSTNAME, registerErrorResponse1.getErrorCodes().get(0));
    }

    @Test
    public void testRegisterEmployeeWrongLogin () {
        String jsonRegisterRequest = gson.toJson(new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "", "1234567"));
        String jsonRegisterResponse = server.registerEmployee(jsonRegisterRequest);
        ErrorDtoResponse registerErrorResponse = gson.fromJson(jsonRegisterResponse, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse, gson.toJson(registerErrorResponse));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_LOGIN_EMPTY, registerErrorResponse.getErrorCodes().get(0));

        String jsonRegisterRequest1 = gson.toJson(new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", null, "1234567"));
        String jsonRegisterResponse1 = server.registerEmployee(jsonRegisterRequest1);
        ErrorDtoResponse registerErrorResponse1 = gson.fromJson(jsonRegisterResponse1, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse1, gson.toJson(registerErrorResponse1));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_LOGIN_EMPTY, registerErrorResponse1.getErrorCodes().get(0));

        String jsonRegisterRequest2 = gson.toJson(new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "vasi", "1234567"));
        String jsonRegisterResponse2 = server.registerEmployee(jsonRegisterRequest2);
        ErrorDtoResponse registerErrorResponse2 = gson.fromJson(jsonRegisterResponse2, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse2, gson.toJson(registerErrorResponse2));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_LOGIN_LENGTH, registerErrorResponse2.getErrorCodes().get(0));
    }

    @Test
    public void testRegisterEmployeeWrongPassword () {
        String jsonRegisterRequest = gson.toJson(new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "VASILI100", ""));
        String jsonRegisterResponse = server.registerEmployee(jsonRegisterRequest);
        ErrorDtoResponse registerErrorResponse = gson.fromJson(jsonRegisterResponse, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse, gson.toJson(registerErrorResponse));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_PASSWORD, registerErrorResponse.getErrorCodes().get(0));

        String jsonRegisterRequest1 = gson.toJson(new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "VASILI111", null));
        String jsonRegisterResponse1 = server.registerEmployee(jsonRegisterRequest1);
        ErrorDtoResponse registerErrorResponse1 = gson.fromJson(jsonRegisterResponse1, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse1, gson.toJson(registerErrorResponse1));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_PASSWORD, registerErrorResponse1.getErrorCodes().get(0));

        String jsonRegisterRequest2 = gson.toJson(new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "VASILI122", "12345"));
        String jsonRegisterResponse2 = server.registerEmployee(jsonRegisterRequest2);
        ErrorDtoResponse registerErrorResponse2 = gson.fromJson(jsonRegisterResponse2, ErrorDtoResponse.class);
        assertEquals(jsonRegisterResponse2, gson.toJson(registerErrorResponse2));
        assertEquals(1, registerErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_PASSWORD_LENGTH, registerErrorResponse2.getErrorCodes().get(0));
    }

    @Test
    public void testLoginEmployeeWrongLoginOrPassword () {
        RegisterEmployeeDtoRequest registerRequest = new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "vasili133", "1234567");
        String jsonRegisterResponse = server.registerEmployee(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonRegisterResponse, RegisterDtoResponse.class); //token
        String token = registerResponse.getToken();
        server.logoutEmployee(gson.toJson(new LogoutDtoRequest(token)));

        LoginDtoRequest loginRequest = new LoginDtoRequest("vasili5555", "1234567");
        String jsonLoginResponse = server.loginEmployee(gson.toJson(loginRequest));
        ErrorDtoResponse loginErrorResponse = gson.fromJson(jsonLoginResponse, ErrorDtoResponse.class);
        assertEquals(jsonLoginResponse, gson.toJson(loginErrorResponse));
        assertEquals(1, loginErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_LOGIN_OR_PASSWORD, loginErrorResponse.getErrorCodes().get(0));

        LoginDtoRequest loginRequest1 = new LoginDtoRequest("", "1234567");
        String jsonLoginResponse1 = server.loginEmployee(gson.toJson(loginRequest1));
        ErrorDtoResponse loginErrorResponse1 = gson.fromJson(jsonLoginResponse1, ErrorDtoResponse.class);
        assertEquals(jsonLoginResponse1, gson.toJson(loginErrorResponse1));
        assertEquals(1, loginErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_LOGIN_EMPTY, loginErrorResponse1.getErrorCodes().get(0));

        LoginDtoRequest loginRequest2 = new LoginDtoRequest(null, "1234567");
        String jsonLoginResponse2 = server.loginEmployee(gson.toJson(loginRequest2));
        ErrorDtoResponse loginErrorResponse2 = gson.fromJson(jsonLoginResponse2, ErrorDtoResponse.class);
        assertEquals(jsonLoginResponse2, gson.toJson(loginErrorResponse2));
        assertEquals(1, loginErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_LOGIN_EMPTY, loginErrorResponse2.getErrorCodes().get(0));

        LoginDtoRequest loginRequest3 = new LoginDtoRequest("vasili133", "");
        String jsonLoginResponse3 = server.loginEmployee(gson.toJson(loginRequest3));
        ErrorDtoResponse loginErrorResponse3 = gson.fromJson(jsonLoginResponse3, ErrorDtoResponse.class);
        assertEquals(jsonLoginResponse3, gson.toJson(loginErrorResponse3));
        assertEquals(1, loginErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_PASSWORD, loginErrorResponse3.getErrorCodes().get(0));

        LoginDtoRequest loginRequest4 = new LoginDtoRequest("vasili133", null);
        String jsonLoginResponse4 = server.loginEmployee(gson.toJson(loginRequest4));
        ErrorDtoResponse loginErrorResponse4 = gson.fromJson(jsonLoginResponse4, ErrorDtoResponse.class);
        assertEquals(jsonLoginResponse4, gson.toJson(loginErrorResponse4));
        assertEquals(1, loginErrorResponse.getErrorCodes().size());
        assertEquals(ServerErrorCode.WRONG_PASSWORD, loginErrorResponse4.getErrorCodes().get(0));
    }


    @Test
    public void testChangeEmployee() {
        RegisterEmployeeDtoRequest registerRequest = new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "vasili144", "1234567");
        String jsonResponseRegisterEmployee = server.registerEmployee(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse =
                gson.fromJson(jsonResponseRegisterEmployee, RegisterDtoResponse.class); //token
        String token = registerResponse.getToken();
        ChangeEmployeeDtoRequest changeRequest = new ChangeEmployeeDtoRequest(token, "Sizov",
                "Vitaliy", "Vitalevich", "000000");
        String jsonResponse1 = server.changeEmployee(gson.toJson(changeRequest));// Изменение регистрационных данных
        SuccessDtoResponse successResponse = gson.fromJson(jsonResponse1, SuccessDtoResponse.class);

        String jsonResponseGetEmployee = server.getEmployee(gson.toJson(new GetUserDtoRequest(token)));
        GetEmployeeDtoResponse getEmployeeResponse = gson.fromJson(jsonResponseGetEmployee, GetEmployeeDtoResponse.class);
        assertEquals(jsonResponse1, gson.toJson(successResponse));
        assertEquals(changeRequest.getLastName(), getEmployeeResponse.getLastName());
        assertEquals(changeRequest.getFirstName(), getEmployeeResponse.getFirstName());
        assertEquals(changeRequest.getMiddleName(), getEmployeeResponse.getMiddleName());
        assertEquals(changeRequest.getPassword(), getEmployeeResponse.getPassword());
        assertEquals(registerRequest.getLogin(), getEmployeeResponse.getLogin());
        assertEquals(0, getEmployeeResponse.getSkills().size());
        assertTrue(getEmployeeResponse.isActive());
    }

    @Test
    public void testAddEmployeeSkill() {
        RegisterEmployeeDtoRequest registerRequest = new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "vasili155", "1234567");
        String jsonResponse = server.registerEmployee(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class); //token
        String token = registerResponse.getToken();

        EmployeeSkillDtoRequest addSkillRequest = new EmployeeSkillDtoRequest(token, "Java", 5);
        EmployeeSkillDtoRequest addSkillRequest1 = new EmployeeSkillDtoRequest(token, "C++", 1);
        String jsonAddSkillResponse = server.addSkill(gson.toJson(addSkillRequest));
        String jsonAddSkillResponse1 = server.addSkill(gson.toJson(addSkillRequest1));

        String jsonResponseGetEmployee = server.getEmployee(gson.toJson(new GetUserDtoRequest(token)));
        GetEmployeeDtoResponse getEmployeeResponse = gson.fromJson(jsonResponseGetEmployee, GetEmployeeDtoResponse.class);

        SuccessDtoResponse successResponse = gson.fromJson(jsonAddSkillResponse, SuccessDtoResponse.class);
        SuccessDtoResponse successResponse1 = gson.fromJson(jsonAddSkillResponse1, SuccessDtoResponse.class);

        assertEquals(jsonAddSkillResponse, gson.toJson(successResponse));
        assertEquals(jsonAddSkillResponse1, gson.toJson(successResponse1));
        assertEquals(2, getEmployeeResponse.getSkills().size());
    }

    @Test
    public void testChangeEmployeeSkill() {
        RegisterEmployeeDtoRequest registerRequest = new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "vasili166", "1234567");
        String jsonResponse = server.registerEmployee(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class); //token
        String token = registerResponse.getToken();

        server.addSkill(gson.toJson(new EmployeeSkillDtoRequest(token, "Java", 5))); // Добавление навыка
        EmployeeSkillDtoRequest changeSkillRequest = new EmployeeSkillDtoRequest(token, "Java", 4);
        String jsonResponse1 = server.changeSkill(gson.toJson(changeSkillRequest));// Изменение навыка
        SuccessDtoResponse successResponse = gson.fromJson(jsonResponse1, SuccessDtoResponse.class);

        String jsonResponseGetEmployee = server.getEmployee(gson.toJson(new GetUserDtoRequest(token)));
        GetEmployeeDtoResponse getEmployeeResponse = gson.fromJson(jsonResponseGetEmployee, GetEmployeeDtoResponse.class);
        assertEquals(jsonResponse1, gson.toJson(successResponse));
        assertEquals(1, getEmployeeResponse.getSkills().size());
    }

    @Test
    public void testDeleteEmployeeSkill() {
        RegisterEmployeeDtoRequest registerRequest = new RegisterEmployeeDtoRequest("Ivanov",
                "Ivan", "Ivanovich", "vasili177", "1234567");
        String jsonResponse = server.registerEmployee(gson.toJson(registerRequest));// Регистрация
        RegisterDtoResponse registerResponse = gson.fromJson(jsonResponse, RegisterDtoResponse.class); //token
        String token = registerResponse.getToken();

        String jsonResponseGetEmployee = server.getEmployee(gson.toJson(new GetUserDtoRequest(token)));
        GetEmployeeDtoResponse getEmployeeResponse = gson.fromJson(jsonResponseGetEmployee, GetEmployeeDtoResponse.class);

        server.addSkill(gson.toJson(new EmployeeSkillDtoRequest(token, "Java", 5))); // Добавление навыка
        EmployeeSkillDtoRequest deleteSkillRequest = new EmployeeSkillDtoRequest(token, "Java", 5);
        String jsonResponse1 = server.deleteSkill(gson.toJson(deleteSkillRequest));// Удаление навыка
        SuccessDtoResponse successResponse = gson.fromJson(jsonResponse1, SuccessDtoResponse.class);
        assertEquals(jsonResponse1, gson.toJson(successResponse));
        assertEquals(0, getEmployeeResponse.getSkills().size());
    }

    @Test
    public void testGetVacanciesByEmployeeBySelectType1() {
        String tokenEmployer = registerEmployerWithVacancies();

        GetAllVacanciesDtoRequest getAllVacanciesRequest = new GetAllVacanciesDtoRequest(tokenEmployer, 1);
        String getJsonResponse = server.getAllVacancies(gson.toJson(getAllVacanciesRequest));
        GetVacanciesDtoResponse getAllVacanciesResponse = gson.fromJson(getJsonResponse, GetVacanciesDtoResponse.class);
        assertEquals(6, getAllVacanciesResponse.getVacancies().size());
        sortGetAllVacanciesDtoResponse(getAllVacanciesResponse);
        String tokenEmployee = registerJuniorDeveloper();
        // Type 1 - все вакансии, для которых набор умений соответствует требованиям на уровне не ниже уровня, указанного в требовании
        GetVacanciesByEmployeeDtoRequest getRequest1 = new GetVacanciesByEmployeeDtoRequest(tokenEmployee, 1);
        String jsonGetResponse1 = server.getVacanciesByEmployee(gson.toJson(getRequest1));
        GetVacanciesDtoResponse getEmployeeVacanciesResponse1 = gson.fromJson(jsonGetResponse1, GetVacanciesDtoResponse.class);
        sortGetVacanciesDtoResponse(getEmployeeVacanciesResponse1);
        assertEquals(2, getEmployeeVacanciesResponse1.getVacancies().size());
        assertEquals(getAllVacanciesResponse.getVacancies().get(0), getEmployeeVacanciesResponse1.getVacancies().get(0));
        assertEquals(getAllVacanciesResponse.getVacancies().get(2), getEmployeeVacanciesResponse1.getVacancies().get(1));
    }

    @Test
    public void testGetVacanciesByEmployeeBySelectType2() {
        String tokenEmployer = registerEmployerWithVacancies();

        GetAllVacanciesDtoRequest getAllVacanciesRequest = new GetAllVacanciesDtoRequest(tokenEmployer, 1);
        String getJsonResponse = server.getAllVacancies(gson.toJson(getAllVacanciesRequest));
        GetVacanciesDtoResponse getAllVacanciesResponse = gson.fromJson(getJsonResponse, GetVacanciesDtoResponse.class);
        assertEquals(6, getAllVacanciesResponse.getVacancies().size());
        sortGetAllVacanciesDtoResponse(getAllVacanciesResponse);
        String tokenEmployee = registerJuniorDeveloper();
        // Type 2 - все вакансии, для которых набор умений соответствует обязательным требованиям на уровне не ниже уровня, указанного в требовании
        GetVacanciesByEmployeeDtoRequest getRequest2 = new GetVacanciesByEmployeeDtoRequest(tokenEmployee, 2);
        String jsonGetResponse2 = server.getVacanciesByEmployee(gson.toJson(getRequest2));
        GetVacanciesDtoResponse getEmployeeVacanciesResponse2 = gson.fromJson(jsonGetResponse2, GetVacanciesDtoResponse.class);
        sortGetVacanciesDtoResponse(getEmployeeVacanciesResponse2);
        assertEquals(3, getEmployeeVacanciesResponse2.getVacancies().size());
        assertEquals(getAllVacanciesResponse.getVacancies().get(0), getEmployeeVacanciesResponse2.getVacancies().get(0));
        assertEquals(getAllVacanciesResponse.getVacancies().get(2), getEmployeeVacanciesResponse2.getVacancies().get(1));
        assertEquals(getAllVacanciesResponse.getVacancies().get(4), getEmployeeVacanciesResponse2.getVacancies().get(2));
    }

    @Test
    public void testGetVacanciesByEmployeeBySelectType3() {
        String tokenEmployer = registerEmployerWithVacancies();

        GetAllVacanciesDtoRequest getAllVacanciesRequest = new GetAllVacanciesDtoRequest(tokenEmployer, 1);
        String getJsonResponse = server.getAllVacancies(gson.toJson(getAllVacanciesRequest));
        GetVacanciesDtoResponse getAllVacanciesResponse = gson.fromJson(getJsonResponse, GetVacanciesDtoResponse.class);
        assertEquals(6, getAllVacanciesResponse.getVacancies().size());
        sortGetAllVacanciesDtoResponse(getAllVacanciesResponse);
        String tokenEmployee = registerJuniorDeveloper();
        // Type 3 - все вакансии, для которых его набор умений соответствует требованиям на любом уровне
        GetVacanciesByEmployeeDtoRequest getRequest3 = new GetVacanciesByEmployeeDtoRequest(tokenEmployee, 3);
        String jsonGetResponse3 = server.getVacanciesByEmployee(gson.toJson(getRequest3));
        GetVacanciesDtoResponse getEmployeeVacanciesResponse3 = gson.fromJson(jsonGetResponse3, GetVacanciesDtoResponse.class);
        sortGetVacanciesDtoResponse(getEmployeeVacanciesResponse3);
        assertEquals(3, getEmployeeVacanciesResponse3.getVacancies().size());
        assertEquals(getAllVacanciesResponse.getVacancies().get(0), getEmployeeVacanciesResponse3.getVacancies().get(0));
        assertEquals(getAllVacanciesResponse.getVacancies().get(1), getEmployeeVacanciesResponse3.getVacancies().get(1));
        assertEquals(getAllVacanciesResponse.getVacancies().get(2), getEmployeeVacanciesResponse3.getVacancies().get(2));
    }

    @Test
    public void testGetVacanciesByEmployeeBySelectType4() {
        String tokenEmployer = registerEmployerWithVacancies();

        GetAllVacanciesDtoRequest getAllVacanciesRequest = new GetAllVacanciesDtoRequest(tokenEmployer, 1);
        String getJsonResponse = server.getAllVacancies(gson.toJson(getAllVacanciesRequest));
        GetVacanciesDtoResponse getAllVacanciesResponse = gson.fromJson(getJsonResponse, GetVacanciesDtoResponse.class);
        assertEquals(6, getAllVacanciesResponse.getVacancies().size());
        sortGetAllVacanciesDtoResponse(getAllVacanciesResponse);
        String tokenEmployee = registerJuniorDeveloper();
        // Type 4 - список всех вакансий, для которых работник имеет хотя бы одно умение из списка в требовании работодателя
        // на уровне не ниже уровня, указанного в требовании. Отсортированным по числу найденных умений, от большего к меньшему
        GetVacanciesByEmployeeDtoRequest getRequest4 = new GetVacanciesByEmployeeDtoRequest(tokenEmployee, 4);
        String jsonGetResponse4 = server.getVacanciesByEmployee(gson.toJson(getRequest4));
        GetVacanciesDtoResponse getEmployeeVacanciesResponse4 = gson.fromJson(jsonGetResponse4, GetVacanciesDtoResponse.class);
        sortGetVacanciesDtoResponse(getEmployeeVacanciesResponse4);
        assertEquals(4, getEmployeeVacanciesResponse4.getVacancies().size());
        assertEquals(getAllVacanciesResponse.getVacancies().get(0), getEmployeeVacanciesResponse4.getVacancies().get(0));
        assertEquals(getAllVacanciesResponse.getVacancies().get(2), getEmployeeVacanciesResponse4.getVacancies().get(1));
        assertEquals(getAllVacanciesResponse.getVacancies().get(3), getEmployeeVacanciesResponse4.getVacancies().get(2));
        assertEquals(getAllVacanciesResponse.getVacancies().get(4), getEmployeeVacanciesResponse4.getVacancies().get(3));
    }

    @Test
    public void testChangeActivityProfile(){
        String tokenEmployee = registerJuniorDeveloper();

        ChangeActivityProfileEmployeeDtoRequest changeActivityRequest =
                new ChangeActivityProfileEmployeeDtoRequest(tokenEmployee, false);
        String jsonActivityResponse = server.changeProfileActivity(gson.toJson(changeActivityRequest));
        SuccessDtoResponse successResponse = gson.fromJson(jsonActivityResponse, SuccessDtoResponse.class);
        assertEquals(jsonActivityResponse, gson.toJson(successResponse));

        String jsonResponseGetEmployee = server.getEmployee(gson.toJson(new GetUserDtoRequest(tokenEmployee)));
        GetEmployeeDtoResponse getEmployeeResponse = gson.fromJson(jsonResponseGetEmployee, GetEmployeeDtoResponse.class);
        assertFalse(getEmployeeResponse.isActive());

    }
}
