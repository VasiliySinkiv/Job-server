package hiring.server;

import com.google.gson.Gson;
import hiring.service.EmployeeService;
import hiring.service.EmployerService;

import java.io.*;

public class Server {
    private EmployeeService employeeService = new EmployeeService();
    private EmployerService employerService = new EmployerService();

    //1
    public void startServer(String savedDataFileName) {
        Gson gson = new Gson();

        if (savedDataFileName != null)
            try (BufferedReader br = new BufferedReader(new FileReader(savedDataFileName))) {
                //???
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    //2
    public void stopServer(String saveDataFileName) {
        Gson gson = new Gson();
        if (saveDataFileName != null)
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(saveDataFileName))) {
                //???
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    // Команды работника
    // Регистрация работника
    public String registerEmployee(String requestJsonString) {
        return employeeService.registerEmployee(requestJsonString);
    }

    public String getEmployee(String requestJsonString) {
        return employeeService.getEmployee(requestJsonString);
    }

    //Изменение данных работника
    public String changeEmployee(String requestJsonString) {
        return employeeService.changeEmployee(requestJsonString);
    }

    //Удаление работника
    public String deleteEmployee(String requestJsonString) {
        return employeeService.deleteEmployee(requestJsonString);
    }

    //Вход работника
    public String loginEmployee(String requestJsonString) {
        return employeeService.loginEmployee(requestJsonString);
    }

    //Выход работника
    public String logoutEmployee(String requestJsonString) {
        return employeeService.logoutEmployee(requestJsonString);
    }

    //Добавление навыка
    public String addSkill(String requestJsonString) {
        return employeeService.addEmployeeSkill(requestJsonString);
    }

    public String getSkills(String requestJsonString) {
        return employeeService.getSkills(requestJsonString);
    }

    //Изменение навыка
    public String changeSkill(String requestJsonString) {
        return employeeService.changeEmployeeSkill(requestJsonString);
    }

    //Удаление навыка
    public String deleteSkill(String requestJsonString) {
      return employeeService.deleteEmployeeSkill(requestJsonString);
    }

    //Получить список вакансий
    // Type 1 - все вакансии работодателей, для которых его набор умений соответствует
    // требованиям работодателя на уровне не ниже уровня, указанного в требовании
    // Type 2 - все вакансии работодателей, для которых его набор умений соответствует
    // обязательным требованиям работодателя на уровне не ниже уровня, указанного в требовании
    // Type 3 - все вакансии работодателей, для которых его набор умений соответствует
    // требованиям работодателя на любом уровне
    // Type 4 - список всех вакансий работодателей, для которых работник имеет хотя бы одно умение из списка
    // в требовании работодателя на уровне не ниже уровня, указанного в требовании.
    // В этом случае список выдается отсортированным по числу найденных умений, то есть в начале списка
    // приводятся те вакансии работодателей, для которых работник имеет большее число умений.
    public String getVacanciesByEmployee(String requestJsonString) {
        return employeeService.getVacanciesByEmployee(requestJsonString);
    }

    //Активировать профиль
    public String changeProfileActivity(String requestJsonString) {
        return employeeService.changeProfileActivity(requestJsonString);
    }

    // Команды работодателя
    //Регистрация работодателя
    public String registerEmployer(String requestJsonString) {
        return employerService.registerEmployer(requestJsonString);
    }

    public String getEmployer(String requestJsonString) {
        return employerService.getEmployer(requestJsonString);
    }

    //Изменение данных работодателя
    public String changeEmployer(String requestJsonString) {
        return employerService.changeEmployer(requestJsonString);
    }

    //Удаление работодателя
    public String deleteEmployer(String requestJsonString) {
        return employerService.deleteEmployer(requestJsonString);
    }

    //Вход работодателя
    public String loginEmployer(String requestJsonString) {
        return employerService.loginEmployer(requestJsonString);
    }

    //Выход работодателя
    public String logoutEmployer(String requestJsonString) {
        return employerService.logoutEmployer(requestJsonString);
    }

    //Добавление вакансии
    public String addVacancy(String requestJsonString) {
        return employerService.addVacancy(requestJsonString);
    }

    //Удаление вакансии
    public String deleteVacancy(String requestJsonString) {
        return employerService.deleteVacancy(requestJsonString);
    }

    //Добавление требования
    public String addRequirement(String requestJsonString) {
        return employerService.addRequirement(requestJsonString);
    }

    //Изменение требования
    public String changeRequirement(String requestJsonString) {
        return employerService.changeRequirement(requestJsonString);
    }

    //Удаление требования
    public String deleteRequirement(String requestJsonString) {
        return employerService.deleteRequirement(requestJsonString);
    }

    public String getVacancy(String requestJsonString) {
        return employerService.getVacancy(requestJsonString);
    }

    //Получить все свои вакансии
    //Type 1 - получить все вакансии
    //Type 2 - получить активные вакансии
    //Type 3 - получить неактивные вакансии
    public String getAllVacancies(String requestJsonString) {
        return employerService.getAllVacancies(requestJsonString);
    }

    //Получить список работников, удовлетворяющих требованиям вакансии
    // Type 1 - всех работников, имеющих все необходимые для этой вакансии умения на уровне не ниже уровня, указанного в требовании
    // Type 2 - всех работников, имеющих все обязательные для этой вакансии умения на уровне не ниже уровня, указанного в требовании
    // Type 3 - всех работников, имеющих все необходимые для этой вакансии умения на любом уровне
    // Type 4 - всех работников, имеющих хотя бы одно необходимое для этой вакансии умение на уровне не ниже уровня, указанного в требовании
    public String getEmployeesByVacancy(String requestJsonString) {
        return employerService.getEmployeesByVacancy(requestJsonString);
    }

    //Принять работника
    public String acceptEmployee(String requestJsonString) {
        return employerService.acceptEmployee(requestJsonString);
    }

    public String clearDataBase(){
        return employerService.clearDataBase();
    }
}
