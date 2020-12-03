package hiring.dto.request;

public class ChangeEmployerDtoRequest {
    private String token;
    private String lastName;
    private String firstName;
    private String middleName;
    private String password;
    private String company;
    private String address;
    private String email;

    public ChangeEmployerDtoRequest(String token, String lastName, String firstName, String middleName, String password,
                                    String company, String address, String email) {
        this.token = token;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.password = password;
        this.company = company;
        this.address = address;
        this.email = email;
    }

    public ChangeEmployerDtoRequest(String token, String lastName, String firstName, String password, String company,
                                    String address, String email) {
        this(token, lastName, firstName, null, password, company, address, email);
    }

    public String getToken() {
        return token;
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
}
