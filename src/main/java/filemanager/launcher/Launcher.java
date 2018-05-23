package filemanager.launcher;

import filemanager.controller.Application;

import java.util.Scanner;

public class Launcher {

    public static void main(String[] args) {
        Application application = new Application("qa");
        boolean flag = true;

        menuPrint();
        Scanner sc = new Scanner(System.in);
        while (flag) {
            int option = sc.nextInt();
            if (option == 1) {
                application.changeEnvironment();
            } else if (option == 2) {
                application.runProgram();
            } else if (option == 3) {
                flag = false;
            }
        }
    }

    private static void menuPrint() {
        System.out.println("1. Change environment.");
        System.out.println("2. Run program.");
        System.out.println("3. Exit.");
    }
}