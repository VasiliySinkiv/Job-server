package hiring.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Employer extends User {
    private String company;
    private String address;
    private String email;
    private List<Vacancy> vacancies = new ArrayList<>();

    public Employer(int id, String lastName, String firstName, String middleName, String login, String password,
                    String company, String address, String email) {
        super(id, lastName, firstName, middleName, login, password);
        this.company = company;
        this.address = address;
        this.email = email;
    }

    public Employer(String lastName, String firstName, String middleName, String login, String password, String company,
                    String address, String email) {
        this(0, lastName, firstName, middleName, login, password, company, address, email);
    }

    public Employer(String lastName, String firstName, String login, String password, String company, String address, String email) {
        this(lastName, firstName, null, login, password, company, address, email);
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addVacancy(Vacancy vacancy){
        vacancies.add(vacancy);
    }

    public void removeVacancy(int index){
        vacancies.remove(index);
    }

    public List<Vacancy> getVacancies() {
        return Collections.unmodifiableList(vacancies);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employer)) return false;
        if (!super.equals(o)) return false;
        Employer employer = (Employer) o;
        return Objects.equals(getCompany(), employer.getCompany()) &&
                Objects.equals(getAddress(), employer.getAddress()) &&
                Objects.equals(getEmail(), employer.getEmail()) &&
                Objects.equals(getVacancies(), employer.getVacancies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCompany(), getAddress(), getEmail(), getVacancies());
    }
}
