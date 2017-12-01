/**
 * This is the Client Interface to the Radon Processing Application. It
 * presents a menu to the user, reads in any required values from the user,
 * and processes the choice that the user makes.
 */

import java.util.*;

public class RadonClientApplication
{

    public void run()
    {
        Scanner input = new Scanner(System.in);
        RadonProcessor rp = new RadonProcessor();
        System.out.println("Welcome to the Radon Level Analysis Application");
        System.out.println("Enter the file to be loaded");
        String fileName = input.nextLine();
        if (!rp.loadData(fileName))
        {
            System.out.println("Error in loading data from file " + fileName);
            return;
        }
        while (true)
        {
            this.showMenu();
            int selection = input.nextInt();
            input.nextLine();
            switch (selection) {
                case 1:
                    rp.printBlockData();
                    break;
                case 2:
                    rp.printHouseDetails();
                    break;
                case 3:
                    System.out.println("Enter the house number to be demolished:");
                    int houseNumber = input.nextInt();
                    input.nextLine();
                    rp.demolishHouse(houseNumber);
                    break;
                case 4:
                    System.out.println("Enter the lot to create an infill:");
                    int lotNumber = input.nextInt();
                    input.nextLine();
                    rp.putInfill(lotNumber);
                    break;
                case 5:
                    rp.printReport();
                    break;
                case 6:
                    System.out.println("Enter the output file name:");
                    String outputFileName = input.nextLine();
                    rp.exit(outputFileName);
                    break;
                default:
                    System.out.println("Invalid selection");
                    break;
            }
        }
        

        // Request any additional details about the choice.

        // Process the user's choice by calling on RadonProcessor.

        // Write the data to the files when done processing.

        // Generate a witty and/or sarcastic message when the user stops the program.
    }


    /**
     * Displays the menu for the user.
     */
    private void showMenu()
    {
        System.out.println ("Radon Analysis Menu");
        System.out.println ("1 - Print Block data");
        System.out.println ("2 - Print the information about a house");
        System.out.println ("3 - Demolish a house");
        System.out.println ("4 - Build an infill");
        System.out.println ("5 - Print remediation report");
        System.out.println ("6 - Quit the application");
        System.out.println ("\nPlease make your choice");
    }

}
