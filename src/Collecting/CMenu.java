package Collecting;

import implementation.*;
import interfeces.*;

import java.util.Scanner;


public class CMenu {
    private static final Iemploye employeService = new EmployeImpl();

    private static final Iclient clientService = new ClientImpl();
    private static final Imission missionService = new MissionImpl();
    private static final Iaffectation affectationService = new AffectationImpl();
    private static final Ioperation operationService = new OperationImpl();
    private static final Icompte compteCourantService = new CompteCourantImpl();
    private static final Icompte compteEpargneService = new CompteEpargneImpl();

    private static final Scanner scanner = new Scanner(System.in);

    public static void startMenu() {
        while (true) {
            System.out.println("EasyBank Management System Menu:");
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
                    CEmploye.employeeManagement(scanner, employeService);
                    break;
                case 2:
                    CClient.clientManagement(scanner, clientService);
                    break;
                case 3:
                    CMission.missionManagement(scanner, missionService);
                    break;
                case 4:
                    CAffectation.affectationManagement(scanner, affectationService, employeService, missionService);
                    break;
                case 5:
                    CCOmpteC.compteCourantManagement(scanner, compteCourantService);
                    break;
                case 6:
                    CCOmpteE.compteEpargneManagement(scanner, compteEpargneService);
                    break;
                case 7:
                    COperation.operationManagement(scanner, operationService);
                    break;
                case 8:
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }
        }
    }
}