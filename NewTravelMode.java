import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class NewTravelMode 
{
    public static void main(String[] args) 
    {
        HashMap<String, ArrayList<String>> PassportInfo = new HashMap<>();
        Scanner input = new Scanner(System.in);
        Console cnsl = System.console();
        int choice;
        do 
        {
            System.out.println("|      Welcome to Block Voyage!         |");
            System.out.println("=========================================");
            System.out.println("| 1: Display All Travelers (Encrypted)  |");
            System.out.println("| 2: Add New Traveler                   |");
            System.out.println("| 3: Remove Traveler                    |");
            System.out.println("| 4: Update Traveler Records            |");
            System.out.println("| 5: Exit                               |");
            System.out.println("=========================================");
            choice = input.nextInt();
            input.nextLine(); // Consume the newline character
            switch (choice) 
            {
                case 1:
                    Display_Users(PassportInfo);   
                    if (cnsl != null)
                    {
                        HashMap<String, ArrayList<String>> encryptedInfo = Mask_Personal_Info(PassportInfo);
                        Decrypt_Personal_Info(encryptedInfo, cnsl);
                    } 
                    else
                    {
                        System.out.println("Console not valid or password not accepted. Try again.. \n");
                    }
    
                    break;
                case 2:
                    Add_User(input, PassportInfo);
                    break;
                case 3:
                    Remove_User(input, PassportInfo);
                    break;    
                case 4:
                    Update_User(input, PassportInfo);
                    break;
                default:
                    System.out.println("Exiting. Thank you, come again! \n");
            }
        } while (choice != 5); // Fixing the loop condition
        input.close();
    }

    public static void Add_User(Scanner input, HashMap<String, ArrayList<String>> PassportInfo) {
        System.out.println("=========================================");
        System.out.println("|         Add New Traveler              |");
        System.out.println("=========================================");
        System.out.print("Enter traveler name: \n");
        String key = input.nextLine();
        ArrayList<String> values = new ArrayList<>();
        boolean Valid_Passport_Number;
        String Passport_Number;
        do
        {
            System.out.print("Enter passport number: \n");
            Passport_Number = input.nextLine();
            Valid_Passport_Number = Pattern.matches("C\\d{8}", Passport_Number);
            if (!Valid_Passport_Number)
            {
                System.out.println("Please enter a valid passport number! \n");
            }
        }while(!Valid_Passport_Number);
        values.add(Passport_Number);
        boolean Valid_DOB;
        String DOB;
        do
        {
            System.out.print("Enter Date of Birth (MM-DD-YYYY): \n");
            DOB = input.nextLine();
            Valid_DOB = Pattern.matches("\\d{2}-\\d{2}-\\d{4}", DOB);
            if (!Valid_DOB)
            {
                System.out.println("Invalid Date Format. Use MM-DD-YYYY format. \n");
            }

            
        }while(!Valid_DOB);
        values.add(DOB);
        System.out.print("Please state reason for travel: Business, Leisure, Vacation? \n");
        String Travel_Reason = input.nextLine();
        values.add(Travel_Reason);

        PassportInfo.put(key, values);
        System.out.println("=========================================");
        System.out.println("| This is your passport info:           |");
        System.out.println("| " + PassportInfo);
        System.out.println("=========================================");
    }

    public static void Remove_User(Scanner input, HashMap<String, ArrayList<String>> PassportInfo) {
        System.out.println("=========================================");
        System.out.println("|         Remove Traveler               |");
        System.out.println("=========================================");
        System.out.print("Enter the name of the user you wish to remove: ");
        String User = input.nextLine();
        PassportInfo.remove(User);
        System.out.println("=========================================");
        System.out.println("| User removed successfully.            |");
        System.out.println("=========================================");
    }

    public static void Update_User(Scanner input, HashMap<String, ArrayList<String>> PassportInfo) {
        System.out.println("=========================================");
        System.out.println("|       Update Traveler Records         |");
        System.out.println("=========================================");
        System.out.print("Enter the name of user you wish to update: ");
        String User = input.nextLine();
        if (PassportInfo.containsKey(User)) {
            ArrayList<String> values = PassportInfo.get(User);
            System.out.println("=========================================");
            System.out.println("| What parameter do you wish to adjust? |");
            System.out.println("| 1: Passport Number                    |");
            System.out.println("| 2: Date of Birth                      |");
            System.out.println("| 3: Travel Reason                      |");
            System.out.println("=========================================");
            System.out.print("Enter your choice: ");
            int param = input.nextInt();
            input.nextLine(); // Consume newline left-over
            switch (param) {
                // check to make sure data validation applies to the update in user!
                case 1:
                    System.out.print("Enter new passport number: ");
                    values.set(0, input.nextLine());
                    break;
                case 2:
                    System.out.print("Enter new Date of Birth (MM-DD-YYYY): ");
                    values.set(1, input.nextLine());
                    break;
                case 3:
                    System.out.print("Enter new reason for travel: ");
                    values.set(2, input.nextLine());
                    break;
                default:
                    System.out.println("=========================================");
                    System.out.println("| Invalid parameter.                   |");
                    System.out.println("=========================================");
            }
            PassportInfo.put(User, values);
            System.out.println("=========================================");
            System.out.println("| User updated successfully.            |");
            System.out.println("=========================================");
        } else {
            System.out.println("=========================================");
            System.out.println("| User not found.                       |");
            System.out.println("=========================================");
        }
    }

    public static void Display_Users(HashMap<String, ArrayList<String>> PassportInfo) 
    {
        System.out.println("=========================================");
        System.out.println("|       Display All Travelers           |");
        System.out.println("=========================================");
        for (String key : PassportInfo.keySet()) {
            System.out.println("| Traveler: " + key);
            System.out.println("| Details: " + PassportInfo.get(key));
            System.out.println("=========================================");
        }
    }
    // change up data type to return String? 
    public static HashMap<String, ArrayList<String>> Mask_Personal_Info(HashMap<String, ArrayList<String>> PassportInfo) 
    {
        HashMap<String, ArrayList<String>> encryptedInfo = new HashMap<>();
        for (String key: PassportInfo.keySet())
        {
            ArrayList<String> values = PassportInfo.get(key);
            ArrayList<String> encryptedValues = new ArrayList<>();

            for (String value : values)
            {
                StringBuilder encryptedValue = new StringBuilder();
                for (int i = 0; i < value.length(); i++)
                {
                    char c = value.charAt(i);
                    int asciiVal = (int) c;
                    int encryptedAscii = asciiVal * 5;
                    encryptedValue.append(encryptedAscii).append(" ");
                }
                encryptedValues.add(encryptedValue.toString());
            }
            encryptedInfo.put(key, encryptedValues);
        }

        System.out.println("=========================================");
        System.out.println("|       Encrypted Personal Info         |");
        System.out.println("=========================================");
        System.out.println("| Encrypted Info: " + encryptedInfo);
        System.out.println("=========================================");

        return encryptedInfo;
        
    }

    public static void Decrypt_Personal_Info(HashMap<String, ArrayList<String>> encryptedInfo, Console cnsl)
    {
        System.out.println("Enter the passcode to decrypt values: \n");
        char[] passcode_Array = cnsl.readPassword("Enter password: ");
        String Password = new String(passcode_Array);
        if (Password.equals("B0degaM@n"))
        {
            HashMap<String, ArrayList<String>> decryptedInfo = new HashMap<>();
            for (String key : encryptedInfo.keySet())
            {
                ArrayList<String> encryptedValues = encryptedInfo.get(key);
                ArrayList<String> decryptedValues = new ArrayList<>();

                for (String ev: encryptedValues)
                {
                    String[] encryptedChars = ev.split(" ");
                    StringBuilder decryptedValue = new StringBuilder();

                    for (String ec : encryptedChars)
                    {
                        if (!ec.isEmpty())
                        {
                            int encryptedAscii = Integer.parseInt(ec);
                            int asciiVal = encryptedAscii / 5;
                            decryptedValue.append((char) asciiVal);
                        }
                    }
                    decryptedValues.add(decryptedValue.toString());
                }
                decryptedInfo.put(key, decryptedValues);
            }
            System.out.println("=========================================");
            System.out.println("|       Decrypted Personal Info         |");
            System.out.println("=========================================");
            for (String key : decryptedInfo.keySet())
            {
                System.out.println("| Traveler: " + key);
                System.out.println("| Details: " + decryptedInfo.get(key));
                System.out.println("=========================================");
            }
        }
        else
        {
            System.out.println("=========================================");
            System.out.println("| Password is not correct. Try again. Bozo.. ");
            System.out.println("=========================================");
        
        }
    }
}
    // public static void Mask_Personal_Info(HashMap<String, ArrayList<String>> PassportInfo) {
    //     System.out.println("=========================================");
    //     System.out.println("|       Masked Personal Info            |");
    //     System.out.println("=========================================");
    //     for (String key : PassportInfo.keySet()) {
    //         ArrayList<String> values = PassportInfo.get(key);
    //         for (int i = 0; i < values.size(); i++) {
    //             String maskedValue = values.get(i).replaceAll(".", "*");
    //             values.set(i, maskedValue);
    //         }
    //         PassportInfo.put(key, values);
    //     }
    //     System.out.println("| Masked Personal Info: " + PassportInfo);
    //     System.out.println("=========================================");
    // }


