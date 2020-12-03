package hiring.dto.response;

import hiring.model.Employee;

import java.util.List;

public class GetEmployeesDtoResponse {
    private List<Employee> employees;

    public GetEmployeesDtoResponse(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
