package Collecting;

import dto.Affectation;
import dto.Employe;
import dto.Mission;
import interfeces.Iaffectation;
import interfeces.Iemploye;
import interfeces.Imission;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CAffectation {

    public static void affectationManagement(Scanner scanner, Iaffectation affectationService, Iemploye employeService, Imission missionService) {
        while (true) {
            System.out.println("Affectation Management Menu:");
            System.out.println("1. Create New Affectation");
            System.out.println("2. Delete Affectation");
            System.out.println("3. Show All Affectations");
            System.out.println("4. Show Assignment History by Matricule");
            System.out.println("5. Back to Main Menu");

            System.out.print("Enter your choice (1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createNewAffectation(scanner, affectationService, employeService, missionService);
                case 2 -> deleteAffectation(scanner, affectationService, employeService, missionService);
                case 3 -> getAffectationStatistics(affectationService);
                case 4 -> showAssignmentHistoryByMatricule(scanner, affectationService);
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }
    }
    public static void getAffectationStatistics(Iaffectation affectationService) {
        HashMap<String, Integer> statisticsMap = affectationService.getAffectationStatistics();

        int totalAffectations = statisticsMap.get("TotalAffectations");
        int totalEmployees = statisticsMap.get("TotalEmployees");
        int totalMissions = statisticsMap.get("TotalMissions");

        System.out.println("Total Affectations: " + totalAffectations);
        System.out.println("Total Employees: " + totalEmployees);
        System.out.println("Total Missions: " + totalMissions);
    }


    private static void createNewAffectation(Scanner scanner, Iaffectation affectationService, Iemploye employeService, Imission missionService) {
        System.out.print("Enter Employee Matricule: ");
        String employeeMatricule = scanner.nextLine();

        Optional<List<Employe>> employeesOptional = employeService.SearchByMatricule(employeeMatricule);

        if (employeesOptional.isEmpty()) {
            System.out.println("Employee with Matricule '" + employeeMatricule + "' not found.");
            return;
        }

        List<Employe> employees = employeesOptional.get();
        if (employees.isEmpty()) {
            System.out.println("Employee with Matricule '" + employeeMatricule + "' not found.");
            return;
        }

        Employe employee = employees.get(0);

        System.out.print("Enter Mission Code: ");
        String missionCode = scanner.nextLine();

        Optional<Mission> missionOptional = missionService.getCodeMission(missionCode);

        if (missionOptional.isEmpty()) {
            System.out.println("Mission with Code '" + missionCode + "' not found.");
            return;
        }

        Mission mission = missionOptional.get();

        System.out.print("Enter Affectation Name: ");
        String affectationName = scanner.nextLine();

        System.out.print("Enter Affectation Description: ");
        String affectationDescription = scanner.nextLine();

        Affectation newAffectation = new Affectation(employee, mission, affectationName, affectationDescription);

        Optional<Affectation> createdAffectation = affectationService.createNewAffectation(newAffectation);

        if (createdAffectation.isPresent()) {
            System.out.println("Affectation created successfully!");
        } else {
            System.out.println("Failed to create affectation.");
        }
    }


    private static void deleteAffectation(Scanner scanner, Iaffectation affectationService, Iemploye employeService, Imission missionService) {
        System.out.print("Enter Employee Matricule: ");
        String employeeMatricule = scanner.nextLine();

        Optional<List<Employe>> employeesOptional = employeService.SearchByMatricule(employeeMatricule);

        if (employeesOptional.isEmpty()) {
            System.out.println("Employee with Matricule '" + employeeMatricule + "' not found.");
            return;
        }

        List<Employe> employees = employeesOptional.get();

        if (employees.isEmpty()) {
            System.out.println("Employee with Matricule '" + employeeMatricule + "' not found.");
            return;
        }

        System.out.print("Enter Mission Code: ");
        String missionCode = scanner.nextLine();

        Optional<Mission> missionOptional = missionService.getCodeMission(missionCode);

        if (missionOptional.isEmpty()) {
            System.out.println("Mission with Code '" + missionCode + "' not found.");
            return;
        }

        Mission mission = missionOptional.get();

        Affectation affectationToDelete = new Affectation(employees.get(0), mission, "", "");

        boolean isDeleted = affectationService.DeleteAffectation(affectationToDelete);

        if (isDeleted) {
            System.out.println("Affectation deleted successfully!");
        } else {
            System.out.println("Failed to delete affectation.");
        }
    }

    private static void showAssignmentHistoryByMatricule(Scanner scanner, Iaffectation affectationService) {
        System.out.print("Enter Employee Matricule: ");
        String employeeMatricule = scanner.nextLine();

        List<Affectation> assignmentHistory = affectationService.getAssignmentHistoryByMatricule(employeeMatricule);

        if (assignmentHistory.isEmpty()) {
            System.out.println("No assignment history found for employee with Matricule '" + employeeMatricule + "'.");
        } else {
            System.out.println("Assignment History for Employee with Matricule '" + employeeMatricule + "':");
            for (Affectation affectation : assignmentHistory) {
                Employe employee = affectation.getEmploye();
                Mission mission = affectation.getMission();
                System.out.println("Mission Code: " + mission.getCode());
                System.out.println("Mission Name: " + mission.getNome());
                System.out.println("Assignment Name: " + affectation.getNom());
                System.out.println("Assignment Description: " + affectation.getDescription());
                System.out.println("Employee Name: " + employee.getNom() + " " + employee.getPrenom());
                System.out.println("------------------------------------------------");
            }
        }
    }

}
