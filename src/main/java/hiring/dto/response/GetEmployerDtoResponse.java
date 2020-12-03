package hiring.dto.response;

import hiring.model.Vacancy;

import java.util.List;

public class GetEmployerDtoResponse {
    private int id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String login;
    private String password;
    private String company;
    private String address;
    private String email;
    private List<Vacancy> vacancies;

    public GetEmployerDtoResponse(int id, String lastName, String firstName, String middleName, String login, String password,
                                  String company, String address, String email, List<Vacancy> vacancies) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.login = login;
        this.password = password;
        this.company = company;
        this.address = address;
        this.email = email;
        this.vacancies = vacancies;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getCompany() {
        return company;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public List<Vacancy> getVacancies() {
        return vacancies;
    }
}
