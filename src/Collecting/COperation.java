package Collecting;

import dto.Compte;
import dto.Employe;
import dto.Operation;
import dto.TypeOperation;
import implementation.OperationImpl;
import interfeces.Ioperation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class COperation {

    public static void operationManagement(Scanner scanner, Ioperation operationService) {


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

        System.out.print("Enter Operation Number: ");
        String operationNumber = scanner.nextLine();

        LocalDate dateCreation = LocalDate.now();

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
                java.sql.Date.valueOf(dateCreation),
                montant,
                TypeOperation.valueOf(operationType),
                employe,
                compte
        );

        Optional<Operation> addedOperation = operationService.Add(newOperation);

        if (addedOperation.isPresent()) {
            System.out.println("Operation added successfully.");
        } else {
            System.out.println("Failed to add the operation.");
        }
    }





    private static void searchOperationByNumber(Scanner scanner, Ioperation operationService) {
        System.out.print("Enter Operation Number to search: ");
        String operationNumber = scanner.nextLine();

        Optional<Optional<List<Operation>>> operationsOptional = Optional.ofNullable(operationService.SearchByNumber(operationNumber));

        if (operationsOptional.isPresent()) {
            Optional<List<Operation>> innerOptional = operationsOptional.get();

            if (innerOptional.isPresent()) {
                List<Operation> operations = innerOptional.get();
                if (operations.isEmpty()) {
                    System.out.println("No operations found with the specified number.");
                } else {
                    System.out.println("Operations with Number '" + operationNumber + "':");
                    for (Operation operation : operations) {
                        System.out.println(operation);
                    }
                }
            } else {
                System.out.println("Failed to retrieve operations with the specified number.");
            }
        } else {
            System.out.println("Failed to retrieve operations with the specified number.");
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
}
