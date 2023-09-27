import dto.*;
import implementation.*;
import interfeces.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.time.LocalDate;


public class Main {

    public static void main(String[] args) {
        Iemploye employeService = new EmployeImpl();
        Iclient clientService = new ClientImpl();
        Imission missionService = new MissionImpl();
        Iaffectation affectationService = new AffectationImpl();
        Ioperation operationService = new OperationImpl();
        Icompte compteCourantService = new CompteCourantImpl();
        Icompte compteEpargneService = new CompteEpargneImpl();


        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Employee/Client/Mission/Affectation Management System Menu:");
            System.out.println("1. Employee Management");
            System.out.println("2. Client Management");
            System.out.println("3. Mission Management");
            System.out.println("4. Affectation Management");
            System.out.println("5. compte Courant Management");
            System.out.println("6. compte Epargne Management");

            System.out.println("7. Operation Management");
            System.out.println("8. Exit");


            System.out.print("Enter your choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    employeeManagement(scanner, employeService);
                    break;
                case 2:
                    clientManagement(scanner, clientService);
                    break;
                case 3:
                    missionManagement(scanner, missionService);
                    break;
                case 4:
                    affectationManagement(scanner, affectationService, employeService, missionService);
                    break;
                case 5:
                    compteCourantManagement(scanner, compteCourantService);
                    break;
                case 6:
                    compteEpargneManagement(scanner,compteEpargneService);
                case 7:
                    operationManagement(scanner, operationService);
                case 8:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }

    private static void employeeManagement(Scanner scanner, Iemploye employeService) {
        while (true) {
            System.out.println("Employee Management Menu:");
            System.out.println("1. Add Employee");
            System.out.println("2. Search Employee by Matricule");
            System.out.println("3. Delete Employee by Matricule");
            System.out.println("4. Show All Employees");
            System.out.println("5. Search Employees by Recruitment Date");
            System.out.println("6. Update Employee");
            System.out.println("7. Back to Main Menu");

            System.out.print("Enter your choice (1-7): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addEmployee(scanner, employeService);
                case 2 -> searchEmployeeByMatricule(scanner, employeService);
                case 3 -> deleteEmployeeByMatricule(scanner, employeService);
                case 4 -> showAllEmployees(employeService);
                case 5 -> searchEmployeesByRecruitmentDate(scanner, employeService);
                case 6 -> updateEmployee(scanner, employeService);
                case 7 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }

    private static void addEmployee(Scanner scanner, Iemploye employeService) {
        System.out.println("Enter employee details:");
        System.out.print("Matricule: ");
        String matricule = scanner.nextLine();

        Date recruitmentDate = null;
        boolean validRecruitmentDate = false;
        while (!validRecruitmentDate) {
            System.out.print("Recruitment Date (yyyy-MM-dd): ");
            String recruitmentDateStr = scanner.nextLine();
            try {
                recruitmentDate = new SimpleDateFormat("yyyy-MM-dd").parse(recruitmentDateStr);
                validRecruitmentDate = true;
            } catch (ParseException e) {
                System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
            }
        }

        System.out.print("Email Address: ");
        String email = scanner.nextLine();

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        Date dateOfBirth = null;
        boolean validDateOfBirth = false;
        while (!validDateOfBirth) {
            System.out.print("Date of Birth (yyyy-MM-dd): ");
            String dateOfBirthStr = scanner.nextLine();
            try {
                dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);
                validDateOfBirth = true;
            } catch (ParseException e) {
                System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
            }
        }

        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        Employe employe = new Employe(firstName, lastName, dateOfBirth, phone, address, matricule, recruitmentDate, email, null, null, null);

        employeService.Add(employe);

        System.out.println("Employee added successfully!");
    }

    private static void searchEmployeeByMadtricule(Scanner scanner, Iemploye employeService) {
        System.out.print("Enter Matricule to search: ");
        String matricule = scanner.nextLine();
        List<Employe> employees = employeService.SearchByMatricule(matricule);
        if (employees.isEmpty()) {
            System.out.println("No employees found with the specified Matricule.");
        } else {
            System.out.println("Employees with Matricule '" + matricule + "':");
            for (Employe employee : employees) {
                System.out.println(employee);
            }
        }
    }
    private static void searchEmployeeByMatricule(Scanner scanner, Iemploye employeService) {
        System.out.print("Enter Matricule to search: ");
        String matricule = scanner.nextLine();
        Optional<List<Employe>> optionalEmployees = Optional.ofNullable(employeService.SearchByMatricule(matricule));

        if (optionalEmployees.isPresent()) {
            List<Employe> employees = optionalEmployees.get();
            System.out.println("Employees with Matricule '" + matricule + "':");
            employees.forEach(System.out::println);
        } else {
            System.out.println("No employees found with the specified Matricule.");
        }
    }


    private static void deleteEmployeeByMatricule(Scanner scanner, Iemploye employeService) {
        System.out.print("Enter Matricule to delete: ");
        String matricule = scanner.nextLine();
        boolean deleted = employeService.Delete(matricule);
        if (deleted) {
            System.out.println("Employee with Matricule '" + matricule + "' deleted successfully.");
        } else {
            System.out.println("No employee found with the specified Matricule. Deletion failed.");
        }
    }

    private static void showAllEmployees(Iemploye employeService) {
        List<Employe> employees = employeService.ShowList();
        if (employees.isEmpty()) {
            System.out.println("No employees found in the database.");
        } else {
            System.out.println("List of all employees:");
            for (Employe employee : employees) {
                System.out.println(employee);
            }
        }
    }

    private static void searchEmployeesByRecruitmentDate(Scanner scanner, Iemploye employeService) {
        System.out.print("Enter Recruitment Date (yyyy-MM-dd) to search: ");
        String dateRecruitmentStr = scanner.nextLine();
        try {
            Date dateRecruitment = new SimpleDateFormat("yyyy-MM-dd").parse(dateRecruitmentStr);
            List<Employe> employees = employeService.SearchByDateR(dateRecruitment);
            if (employees.isEmpty()) {
                System.out.println("No employees found with the specified Recruitment Date.");
            } else {
                System.out.println("Employees recruited on '" + dateRecruitmentStr + "':");
                for (Employe employee : employees) {
                    System.out.println(employee);
                }
            }
        } catch (ParseException e) {
            System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
        }
    }

    private static void updateEmployee(Scanner scanner, Iemploye employeService) {
        System.out.print("Enter Matricule to update: ");
        String matricule = scanner.nextLine();
        List<Employe> employees = employeService.SearchByMatricule(matricule);
        if (employees.isEmpty()) {
            System.out.println("No employees found with the specified Matricule.");
        } else {
            Employe employeeToUpdate = employees.get(0);

            System.out.println("Update employee details:");
            System.out.print("Email Address: ");
            String email = scanner.nextLine();

            System.out.print("First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();

            Date dateOfBirth = null;
            boolean validDateOfBirth = false;
            while (!validDateOfBirth) {
                System.out.print("Date of Birth (yyyy-MM-dd): ");
                String dateOfBirthStr = scanner.nextLine();
                try {
                    dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);
                    validDateOfBirth = true;
                } catch (ParseException e) {
                    System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
                }
            }

            System.out.print("Phone Number: ");
            String phone = scanner.nextLine();

            System.out.print("Address: ");
            String address = scanner.nextLine();

            employeeToUpdate.setEmailAdresse(email);
            employeeToUpdate.setNom(firstName);
            employeeToUpdate.setPrenom(lastName);
            employeeToUpdate.setDateN(dateOfBirth);
            employeeToUpdate.setTel(phone);
            employeeToUpdate.setAdress(address);

            Employe updatedEmployee = employeService.Update(employeeToUpdate);

            if (updatedEmployee != null) {
                System.out.println("Employee with Matricule '" + matricule + "' updated successfully.");
            } else {
                System.out.println("Failed to update employee with Matricule '" + matricule + "'.");
            }
        }
    }

    private static void clientManagement(Scanner scanner, Iclient clientService) {
        while (true) {
            System.out.println("Client Management Menu:");
            System.out.println("1. Add Client");
            System.out.println("2. Search Client by Code");
            System.out.println("3. Delete Client by Code");
            System.out.println("4. Show All Clients");
            System.out.println("5. Search Clients by Last Name");
            System.out.println("6. Update Client");
            System.out.println("7. Back to Main Menu");

            System.out.print("Enter your choice (1-7): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addClient(scanner, clientService);
                case 2 -> searchClientByCode(scanner, clientService);
                case 3 -> deleteClientByCode(scanner, clientService);
                case 4 -> showAllClients(clientService);
                case 5 -> searchClientsByLastName(scanner, clientService);
                case 6 -> updateClient(scanner, clientService);
                case 7 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }

    private static void addClient(Scanner scanner, Iclient clientService) {
        System.out.println("Enter client details:");
        System.out.print("Code: ");
        String code = scanner.nextLine();

        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        Date dateOfBirth = null;
        boolean validDateOfBirth = false;
        while (!validDateOfBirth) {
            System.out.print("Date of Birth (yyyy-MM-dd): ");
            String dateOfBirthStr = scanner.nextLine();
            try {
                dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);
                validDateOfBirth = true;
            } catch (ParseException e) {
                System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
            }
        }

        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        Client client = new Client(code, lastName, firstName, dateOfBirth, phone, address, null);


        clientService.Add(client);

        System.out.println("Client added successfully!");
    }

    private static void searchClientByCode(Scanner scanner, Iclient clientService) {
        System.out.print("Enter Code to search: ");
        String code = scanner.nextLine();
        List<Client> clients = clientService.SearchByCode(code);
        if (clients.isEmpty()) {
            System.out.println("No clients found with the specified Code.");
        } else {
            System.out.println("Clients with Code '" + code + "':");
            for (Client client : clients) {
                System.out.println(client);
            }
        }
    }

    private static void deleteClientByCode(Scanner scanner, Iclient clientService) {
        System.out.print("Enter Code to delete: ");
        String code = scanner.nextLine();
        boolean deleted = clientService.Delete(code);
        if (deleted) {
            System.out.println("Client with Code '" + code + "' deleted successfully.");
        } else {
            System.out.println("No client found with the specified Code. Deletion failed.");
        }
    }

    private static void showAllClients(Iclient clientService) {
        List<Client> clients = clientService.Showlist();
        if (clients.isEmpty()) {
            System.out.println("No clients found in the database.");
        } else {
            System.out.println("List of all clients:");
            for (Client client : clients) {
                System.out.println(client);
            }
        }
    }

    private static void searchClientsByLastName(Scanner scanner, Iclient clientService) {
        System.out.print("Enter Last Name to search: ");
        String lasttName = scanner.nextLine();
        List<Client> clients = clientService.SearchByPrenom(lasttName);
        if (clients.isEmpty()) {
            System.out.println("No clients found with the specified Last Name.");
        } else {
            System.out.println("Clients with First Name '" + lasttName + "':");
            for (Client client : clients) {
                System.out.println(client);
            }
        }
    }

    private static void updateClient(Scanner scanner, Iclient clientService) {
        System.out.print("Enter Code to update: ");
        String code = scanner.nextLine();
        List<Client> clients = clientService.SearchByCode(code);
        if (clients.isEmpty()) {
            System.out.println("No clients found with the specified Code.");
        } else {
            Client clientToUpdate = clients.get(0);

            System.out.println("Update client details:");
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();

            Date dateOfBirth = null;
            boolean validDateOfBirth = false;
            while (!validDateOfBirth) {
                System.out.print("Date of Birth (yyyy-MM-dd): ");
                String dateOfBirthStr = scanner.nextLine();
                try {
                    dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirthStr);
                    validDateOfBirth = true;
                } catch (ParseException e) {
                    System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
                }
            }

            System.out.print("Phone Number: ");
            String phone = scanner.nextLine();

            System.out.print("Address: ");
            String address = scanner.nextLine();

            clientToUpdate.setNom(firstName);
            clientToUpdate.setPrenom(lastName);
            clientToUpdate.setDateN(dateOfBirth);
            clientToUpdate.setTel(phone);
            clientToUpdate.setAdress(address);

            Client updatedClient = clientService.Update(clientToUpdate);

            if (updatedClient != null) {
                System.out.println("Client with Code '" + code + "' updated successfully.");
            } else {
                System.out.println("Failed to update client with Code '" + code + "'.");
            }
        }
    }

    private static void missionManagement(Scanner scanner, Imission missionService) {
        while (true) {
            System.out.println("Mission Management Menu:");
            System.out.println("1. Add Mission");
            System.out.println("2. Delete Mission by Code");
            System.out.println("3. Show All Missions");
            System.out.println("4. Mission History");
            System.out.println("5. Mission Statistics");
            System.out.println("6. Back to Main Menu");

            System.out.print("Enter your choice (1-6): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addMission(scanner, missionService);
                case 2 -> deleteMissionByCode(scanner, missionService);
                case 3 -> showAllMissions(missionService);
                case 4 -> showMissionHistory(missionService);
                case 5 -> showMissionStatistics(missionService);
                case 6 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }

    private static void addMission(Scanner scanner, Imission missionService) {
        System.out.println("Enter mission details:");
        System.out.print("Code: ");
        String code = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        Mission mission = new Mission(code, name, description, null);

        missionService.Add(mission);

        System.out.println("Mission added successfully!");
    }

    private static void deleteMissionByCode(Scanner scanner, Imission missionService) {
        System.out.print("Enter Code to delete: ");
        String code = scanner.nextLine();
        boolean deleted = missionService.Delete(code);
        if (deleted) {
            System.out.println("Mission with Code '" + code + "' deleted successfully.");
        } else {
            System.out.println("No mission found with the specified Code. Deletion failed.");
        }
    }

    private static void showAllMissions(Imission missionService) {
        List<Mission> missions = missionService.ShowList();
        if (missions.isEmpty()) {
            System.out.println("No missions found in the database.");
        } else {
            System.out.println("List of all missions:");
            for (Mission mission : missions) {
                System.out.println(mission);
            }
        }
    }

    private static void showMissionHistory(Imission missionService) {
        List<Mission> missionHistory = missionService.MisiionHistory();
        if (missionHistory.isEmpty()) {
            System.out.println("No mission history found in the database.");
        } else {
            System.out.println("Mission history:");
            for (Mission mission : missionHistory) {
                System.out.println(mission);
            }
        }
    }

    private static void showMissionStatistics(Imission missionService) {
        List<Mission> missionStatistics = missionService.MissionStatistic();
        if (missionStatistics.isEmpty()) {
            System.out.println("No mission statistics found in the database.");
        } else {
            System.out.println("Mission statistics:");
            for (Mission mission : missionStatistics) {
                System.out.println(mission);
            }
        }
    }

    private static void affectationManagement(Scanner scanner, Iaffectation affectationService, Iemploye employeService, Imission missionService) {
        while (true) {
            System.out.println("Affectation Management Menu:");
            System.out.println("1. Create New Affectation");
            System.out.println("2. Delete Affectation");
            System.out.println("3. Show All Affectations");
            System.out.println("4. Back to Main Menu");

            System.out.print("Enter your choice (1-4): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createNewAffectation(scanner, affectationService, employeService, missionService);
                case 2 -> deleteAffectation(scanner, affectationService, employeService, missionService);
                case 3 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    private static void createNewAffectation(Scanner scanner, Iaffectation affectationService, Iemploye employeService, Imission missionService) {
        System.out.print("Enter Employee Matricule: ");
        String employeeMatricule = scanner.nextLine();

        List<Employe> employees = employeService.SearchByMatricule(employeeMatricule);

        if (employees.isEmpty()) {
            System.out.println("Employee with Matricule '" + employeeMatricule + "' not found.");
            return;
        }
        Employe employee = employees.get(0);


        System.out.print("Enter Mission Code: ");
        String missionCode = scanner.nextLine();

        Mission mission = missionService.getCodeMission(missionCode);

        if (mission == null) {
            System.out.println("Mission with Code '" + missionCode + "' not found.");
            return;
        }

        System.out.print("Enter Affectation Name: ");
        String affectationName = scanner.nextLine();

        System.out.print("Enter Affectation Description: ");
        String affectationDescription = scanner.nextLine();

        Affectation newAffectation = new Affectation(employee, mission, affectationName, affectationDescription);

        Affectation createdAffectation = affectationService.CreateNewAffectation(newAffectation);

        if (createdAffectation != null) {
            System.out.println("Affectation created successfully!");
        } else {
            System.out.println("Failed to create affectation.");
        }
    }


    private static void deleteAffectation(Scanner scanner, Iaffectation affectationService, Iemploye employeService, Imission missionService) {
        System.out.print("Enter Employee Matricule: ");
        String employeeMatricule = scanner.nextLine();

        List<Employe> employees = employeService.SearchByMatricule(employeeMatricule);

        if (employees.isEmpty()) {
            System.out.println("Employee with Matricule '" + employeeMatricule + "' not found.");
            return;
        }

        System.out.print("Enter Mission Code: ");
        String missionCode = scanner.nextLine();

        Mission mission = missionService.getCodeMission(missionCode);

        if (mission == null) {
            System.out.println("Mission with Code '" + missionCode + "' not found.");
            return;
        }

        Affectation affectationToDelete = new Affectation(employees.get(0), mission, "", "");

        boolean isDeleted = affectationService.DeleteAffectation(affectationToDelete);

        if (isDeleted) {
            System.out.println("Affectation deleted successfully!");
        } else {
            System.out.println("Failed to delete affectation.");
        }
    }

    private static void operationManagement(Scanner scanner, Ioperation operationService) {


        while (true) {
            System.out.println("Operation Management Menu:");
            System.out.println("1. Add Operation");
            System.out.println("2. Search Operation by Number");
            System.out.println("3. Delete Operation by Number");
            System.out.println("4. Back to Main Menu");

            System.out.print("Enter your choice (1-4): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addOperation(scanner, operationService);
                case 2 -> searchOperationByNumber(scanner, operationService);
                case 3 -> deleteOperationByNumber(scanner, operationService);
                case 4 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }
    private static void addOperation(Scanner scanner, Ioperation operationService) {
        System.out.println("Add Operation:");

        // Collect operation details from the user
        System.out.print("Enter Operation Number: ");
        String operationNumber = scanner.nextLine();

        LocalDate dateCreation = LocalDate.now(); // Get the current date

        System.out.print("Enter Operation Amount: ");
        double montant;
        try {
            montant = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount format. Please enter a valid number.");
            return;
        }

        System.out.print("Enter Operation Type (e.g., versement, retrait): ");
        String operationType = scanner.nextLine();

        System.out.print("Enter Employee Matricule: ");
        String employeeMatricule = scanner.nextLine();

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        OperationImpl operationImpl = new OperationImpl();

        Employe employe = operationImpl.getEmployeByMatricule(employeeMatricule);
        if (employe == null) {
            System.out.println("Employee with Matricule '" + employeeMatricule + "' not found.");
            return;
        }

        Compte compte = operationImpl.getCompteByNumero(accountNumber);
        if (compte == null) {
            System.out.println("Account with Number '" + accountNumber + "' not found.");
            return;
        }

        Operation newOperation = new Operation(
                operationNumber,
                java.sql.Date.valueOf(dateCreation), // Convert LocalDate to java.sql.Date
                montant,
                TypeOperation.valueOf(operationType),
                employe,
                compte
        );

        // Call the Add method to insert the operation into the database
        Operation addedOperation = operationService.Add(newOperation);

        if (addedOperation != null) {
            System.out.println("Operation added successfully.");
        } else {
            System.out.println("Failed to add the operation.");
        }
    }





    private static void searchOperationByNumber(Scanner scanner, Ioperation operationService) {
        System.out.print("Enter Operation Number to search: ");
        String operationNumber = scanner.nextLine();

        List<Operation> operations = operationService.SearchByNumber(operationNumber);

        if (operations.isEmpty()) {
            System.out.println("No operations found with the specified number.");
        } else {
            System.out.println("Operations with Number '" + operationNumber + "':");
            for (Operation operation : operations) {
                System.out.println(operation);
            }
        }
    }

    private static void deleteOperationByNumber(Scanner scanner, Ioperation operationService) {
        System.out.print("Enter Operation Number to delete: ");
        String operationNumber = scanner.nextLine();

        boolean isDeleted = operationService.Delete(operationNumber);

        if (isDeleted) {
            System.out.println("Operation with Number '" + operationNumber + "' deleted successfully.");
        } else {
            System.out.println("No operation found with the specified number. Deletion failed.");
        }
    }

    private static void compteCourantManagement(Scanner scanner, Icompte compteCourantService) {
        while (true) {
            System.out.println("Compte Management Menu:");
            System.out.println("1. Add Compte");
            System.out.println("2. Search Compte by Client Code");
            System.out.println("3. Delete Compte by Numero");
            System.out.println("4. Update Compte Status by Numero");
            System.out.println("5. Show All Comptes");
            System.out.println("6. Search Compte by Operation Type");
            System.out.println("7. Filter Comptes by Status");
            System.out.println("8. Filter Comptes by Date of Creation");
            System.out.println("9. Back to Main Menu");

            System.out.print("Enter your choice (1-9): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCompteCourant(scanner, compteCourantService);
                case 2 -> searchCompteCourantByClientCode(scanner, compteCourantService);
                case 3 -> deleteCompteByNumero(scanner, compteCourantService);
                case 4 -> updateCompteCourantStatusByNumero(scanner, compteCourantService);
                case 5 -> showAllComptesCourant(compteCourantService);
                case 6 -> searchCompteCourantByOperation(scanner, compteCourantService);
                case 7 -> filterComptesCourantByStatus(scanner, compteCourantService);
                case 8 -> filterComptesCourantByDateOfCreation(scanner, compteCourantService);
                case 9 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    private static void addCompteCourant(Scanner scanner, Icompte compteCourantService) {
        System.out.println("Enter Compte Courant details:");

        System.out.print("Numero: ");
        String numero = scanner.nextLine();

        System.out.print("Sold: ");
        double sold = scanner.nextDouble();
        scanner.nextLine();

        LocalDate currentDate = LocalDate.now();

        System.out.print("Etat: ");
        String etatStr = scanner.nextLine();
        EtatCompte etat = EtatCompte.valueOf(etatStr);

        System.out.print("Client Code: ");
        String clientCode = scanner.nextLine();
        Client client = new Client(clientCode, null, null, null, null, null, null);

        System.out.print("Employe Matricule: ");
        String employeMatricule = scanner.nextLine();
        Employe employe = new Employe(null, null, null, null, null, employeMatricule, null, null, null, null, null);

        System.out.print("Decouvert: ");
        double decouvert = scanner.nextDouble();
        scanner.nextLine();

        List<Operation> operations = new ArrayList<>();

        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate);

        Compte compte = new CompteCourant(numero, sold, sqlDate, etat, client, employe, operations, decouvert);

        Compte addedCompte = compteCourantService.Add(compte);

        if (addedCompte != null) {
            System.out.println("Compte added successfully!");
        } else {
            System.out.println("Failed to add Compte.");
        }
    }



    private static void searchCompteCourantByClientCode(Scanner scanner, Icompte compteCourantService) {
        System.out.print("Enter Client Code to search Comptes: ");
        String clientCode = scanner.nextLine();

        Client client = new Client(clientCode, null, null, null, null, null, null);

        List<Compte> comptes = compteCourantService.SearchByClient(client);

        if (comptes.isEmpty()) {
            System.out.println("No Comptes found for the specified Client Code.");
        } else {
            System.out.println("Comptes for Client Code '" + clientCode + "':");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }

    private static void deleteCompteByNumero(Scanner scanner, Icompte compteService) {
        System.out.print("Enter Compte Numero to delete: ");
        String numero = scanner.nextLine();

        boolean deleted = compteService.Delete(numero);

        if (deleted) {
            System.out.println("Compte with Numero '" + numero + "' deleted successfully.");
        } else {
            System.out.println("No Compte found with the specified Numero. Deletion failed.");
        }
    }

    private static void updateCompteCourantStatusByNumero(Scanner scanner, Icompte compteCourantService) {
        System.out.print("Enter Compte Numero to update status: ");
        String numero = scanner.nextLine();

        Compte existingCompte = CompteCourantImpl.GetByNumero(numero);

        if (existingCompte == null) {
            System.out.println("No Compte found with the specified Numero.");
            return;
        }

        System.out.print("Enter new status (ACTIVE or INACTIVE): ");
        String newStatusStr = scanner.nextLine();
        EtatCompte newStatus = EtatCompte.valueOf(newStatusStr);

        existingCompte.setEtat(newStatus);

        Compte updatedCompte = compteCourantService.UpdateStatus(existingCompte);

        if (updatedCompte != null) {
            System.out.println("Compte status updated successfully.");
        } else {
            System.out.println("Failed to update Compte status.");
        }
    }
    private static void showAllComptesCourant(Icompte compteCourantService) {
        List<Compte> comptes = compteCourantService.ShowList();
        if (comptes.isEmpty()) {
            System.out.println("No compte found in the database.");
        } else {
            System.out.println("List of all comptes:");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }
    private static void searchCompteCourantByOperation(Scanner scanner, Icompte compteCourantService) {
        System.out.print("Enter Operation Type (versement or retrait): ");
        String operationType = scanner.nextLine();

        TypeOperation typeOperation;
        try {
            typeOperation = TypeOperation.valueOf(operationType);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Operation Type. Please enter 'versement' or 'retrait'.");
            return;
        }

        Operation operation = new Operation(null,null,null,typeOperation,null,null);
        List<Compte> comptes = compteCourantService.SearchByOperation(operation);

        if (comptes.isEmpty()) {
            System.out.println("No Comptes found for the specified Operation Type.");
        } else {
            System.out.println("Comptes for Operation Type '" + operationType + "':");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }

    private static void filterComptesCourantByStatus(Scanner scanner, Icompte compteCourantService) {
        System.out.print("Enter Status (ACTIVE or INACTIVE): ");
        String status = scanner.nextLine();

        EtatCompte etat = EtatCompte.valueOf(status);
        List<Compte> comptes = compteCourantService.FilterByStatus(etat);

        if (comptes.isEmpty()) {
            System.out.println("No Comptes found with the specified status.");
        } else {
            System.out.println("Comptes with status '" + status + "':");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }

    private static void filterComptesCourantByDateOfCreation(Scanner scanner, Icompte compteCourantService) {
        System.out.print("Enter Date of Creation (yyyy-MM-dd): ");
        String dateCreationStr = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCreation;
        try {
            dateCreation = dateFormat.parse(dateCreationStr);
        } catch (ParseException e) {
            System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
            return;
        }

        List<Compte> comptes = compteCourantService.FilterByDCreation(dateCreation);

        if (comptes.isEmpty()) {
            System.out.println("No Comptes found with the specified Date of Creation.");
        } else {
            System.out.println("Comptes with Date of Creation '" + dateCreationStr + "':");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }

}

    private static void compteEpargneManagement(Scanner scanner, Icompte compteEpargneService) {

        while (true) {
            System.out.println("Compte Epargne Management Menu:");
            System.out.println("1. Add Compte Epargne");
            System.out.println("2. Search Compte Epargne by Client Code");
            System.out.println("3. Delete Compte Epargne by Numero");
            System.out.println("4. Update Compte Epargne Status by Numero");
            System.out.println("5. Show All Comptes Epargne");
            System.out.println("6. Search Compte Epargne by Operation Type");
            System.out.println("7. Filter Comptes Epargne by Status");
            System.out.println("8. Filter Comptes Epargne by Date of Creation");
            System.out.println("9. Back to Main Menu");

            System.out.print("Enter your choice (1-9): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCompteEpargne(scanner, compteEpargneService);
                    break;
                case 2:
                    searchCompteEpargneByClientCode(scanner, compteEpargneService);
                    break;
                case 3:
                    deleteCompteEpargneByNumero(scanner, compteEpargneService);
                    break;
                case 4:
                    updateCompteEpargneStatusByNumero(scanner, compteEpargneService);
                    break;
                case 5:
                    showAllComptesEpargne(compteEpargneService);
                    break;
                case 6:
                    searchCompteEpargneByOperation(scanner, compteEpargneService);
                    break;
                case 7:
                    filterComptesEpargneByStatus(scanner, compteEpargneService);
                    break;
                case 8:
                    filterComptesEpargneByDateOfCreation(scanner, compteEpargneService);
                    break;
                case 9:
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
            }
        }

    }
    private static void addCompteEpargne(Scanner scanner, Icompte compteEpargneService) {
        System.out.println("Enter Compte Epargne details:");

        System.out.print("Numero: ");
        String numero = scanner.nextLine();

        System.out.print("Sold: ");
        double sold = scanner.nextDouble();
        scanner.nextLine();

        LocalDate currentDate = LocalDate.now(); // Get the current date

        System.out.print("Etat: ");
        String etatStr = scanner.nextLine();
        EtatCompte etat = EtatCompte.valueOf(etatStr);

        System.out.print("Client Code: ");
        String clientCode = scanner.nextLine();
        Client client = new Client(clientCode, null, null, null, null, null, null);

        System.out.print("Employe Matricule: ");
        String employeMatricule = scanner.nextLine();
        Employe employe = new Employe(null, null, null, null, null, employeMatricule, null, null, null, null, null);

        System.out.print("Taux Interet: ");
        double tauxInteret = scanner.nextDouble();
        scanner.nextLine();

        List<Operation> operations = new ArrayList<>();

        java.sql.Date sqlDate = java.sql.Date.valueOf(currentDate); // Convert LocalDate to java.sql.Date

        Compte compte = new CompteEpargne(numero, sold, sqlDate, etat, client, employe, operations, tauxInteret);

        Compte addedCompte = compteEpargneService.Add(compte);

        if (addedCompte != null) {
            System.out.println("Compte Epargne added successfully!");
        } else {
            System.out.println("Failed to add Compte Epargne.");
        }
    }

    private static void searchCompteEpargneByClientCode(Scanner scanner, Icompte compteEpargneService) {
        System.out.print("Enter Client Code to search Comptes Epargne: ");
        String clientCode = scanner.nextLine();

        Client client = new Client(clientCode, null, null, null, null, null, null);

        List<Compte> comptes = compteEpargneService.SearchByClient(client);

        if (comptes.isEmpty()) {
            System.out.println("No Comptes Epargne found for the specified Client Code.");
        } else {
            System.out.println("Comptes Epargne for Client Code '" + clientCode + "':");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }

    private static void deleteCompteEpargneByNumero(Scanner scanner, Icompte compteEpargneService) {
        System.out.print("Enter Compte Epargne Numero to delete: ");
        String numero = scanner.nextLine();

        boolean deleted = compteEpargneService.Delete(numero);

        if (deleted) {
            System.out.println("Compte Epargne with Numero '" + numero + "' deleted successfully.");
        } else {
            System.out.println("No Compte Epargne found with the specified Numero. Deletion failed.");
        }
    }

    private static void updateCompteEpargneStatusByNumero(Scanner scanner, Icompte compteEpargneService) {
        System.out.print("Enter Compte Epargne Numero to update status: ");
        String numero = scanner.nextLine();

        Compte existingCompte = CompteEpargneImpl.GetByNumero(numero);

        if (existingCompte == null) {
            System.out.println("No Compte Epargne found with the specified Numero.");
            return;
        }

        System.out.print("Enter new status (ACTIVE or INACTIVE): ");
        String newStatusStr = scanner.nextLine();
        EtatCompte newStatus = EtatCompte.valueOf(newStatusStr);

        existingCompte.setEtat(newStatus);

        Compte updatedCompte = compteEpargneService.UpdateStatus(existingCompte);

        if (updatedCompte != null) {
            System.out.println("Compte Epargne status updated successfully.");
        } else {
            System.out.println("Failed to update Compte Epargne status.");
        }
    }

    private static void showAllComptesEpargne(Icompte compteEpargneService) {
        List<Compte> comptes = compteEpargneService.ShowList();
        if (comptes.isEmpty()) {
            System.out.println("No Compte Epargne found in the database.");
        } else {
            System.out.println("List of all Comptes Epargne:");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }
    private static void searchCompteEpargneByOperation(Scanner scanner, Icompte compteEpargneService) {
        System.out.print("Enter Operation Type (versement or retrait): ");
        String operationType = scanner.nextLine();

        TypeOperation typeOperation;
        try {
            typeOperation = TypeOperation.valueOf(operationType);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Operation Type. Please enter 'versement' or 'retrait'.");
            return;
        }

        Operation operation = new Operation(null, null, null, typeOperation, null, null);
        List<Compte> comptes = compteEpargneService.SearchByOperation(operation);

        if (comptes.isEmpty()) {
            System.out.println("No Comptes Epargne found for the specified Operation Type.");
        } else {
            System.out.println("Comptes Epargne for Operation Type '" + operationType + "':");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }

    private static void filterComptesEpargneByStatus(Scanner scanner, Icompte compteEpargneService) {
        System.out.print("Enter Status (ACTIVE or INACTIVE): ");
        String status = scanner.nextLine();

        EtatCompte etat = EtatCompte.valueOf(status);
        List<Compte> comptes = compteEpargneService.FilterByStatus(etat);

        if (comptes.isEmpty()) {
            System.out.println("No Comptes Epargne found with the specified status.");
        } else {
            System.out.println("Comptes Epargne with status '" + status + "':");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }

    private static void filterComptesEpargneByDateOfCreation(Scanner scanner, Icompte compteEpargneService) {
        System.out.print("Enter Date of Creation (yyyy-MM-dd): ");
        String dateCreationStr = scanner.nextLine();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCreation;
        try {
            dateCreation = dateFormat.parse(dateCreationStr);
        } catch (ParseException e) {
            System.err.println("Invalid date format. Please enter a date in yyyy-MM-dd format.");
            return;
        }

        List<Compte> comptes = compteEpargneService.FilterByDCreation(dateCreation);

        if (comptes.isEmpty()) {
            System.out.println("No Comptes Epargne found with the specified Date of Creation.");
        } else {
            System.out.println("Comptes Epargne with Date of Creation '" + dateCreationStr + "':");
            for (Compte compte : comptes) {
                System.out.println(compte);
            }
        }
    }

}