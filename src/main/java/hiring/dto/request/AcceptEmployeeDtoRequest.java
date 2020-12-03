package hiring.dto.request;

import hiring.model.Employee;
import hiring.model.Vacancy;

public class AcceptEmployeeDtoRequest {
    private String token;
    private Vacancy vacancy;
    private Employee employee;

    public AcceptEmployeeDtoRequest(String token, Vacancy vacancy, Employee employee) {
        this.token = token;
        this.vacancy = vacancy;
        this.employee = employee;
    }

    public String getToken() {
        return token;
    }

    public Vacancy getVacancy() {
        return vacancy;
    }

    public Employee getEmployee() {
        return employee;
    }
}
