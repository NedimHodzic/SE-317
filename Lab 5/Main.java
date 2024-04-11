import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int humidity = 0;
        int temp = 0;

        System.out.println("Enter the current humidity (Range of 0 - 100): ");
        humidity = Integer.parseInt(scanner.next());
        System.out.println("Enter the current temperature (Range of 0 - 125): ");
        temp = Integer.parseInt(scanner.next());

        EmbeddedSystem es = new EmbeddedSystem(humidity, temp);
        System.out.println(es.display());

        while(true) {
            System.out.println("What would you like to do? (Read, Reset, or Stop)");
            String choice = scanner.next();

            if(choice.equalsIgnoreCase("STOP")) {
                break;
            }
            else if(choice.equalsIgnoreCase("RESET")) {
                es.reset();
                System.out.println(es.display());
            }
            else if(choice.equalsIgnoreCase("READ")) {
                System.out.println("Enter the current humidity (Range of 0 - 100): ");
                humidity = scanner.nextInt();
                System.out.println("Enter the current temperature (Range of 0 - 125): ");
                temp = scanner.nextInt();

                es.setHumidity(humidity);
                es.setTemp(temp);

                System.out.println(es.display());
            }
        }

        scanner.close();
    }
}