import swiftbot.*;
import java.util.List;
import java.util.Scanner;

public class dance_project {
    static SwiftBotAPI swiftBot;
    static Saving swiftBotController = new Saving();
    static boolean firstAttempt = true;
    static List<String> lastHexValues = null;

    public static void main(String[] args) throws InterruptedException {
        try {
            swiftBot = new SwiftBotAPI();
        } catch (Exception e) {
            System.out.println("\nI2C disabled!");
            System.out.println("Run the following command:");
            System.out.println("sudo raspi-config nonint do_i2c 0\n");
            System.exit(5);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n***********************************************");
        System.out.println("*       WELCOME TO SWIFTBOT DANCE SYSTEM      *");
        System.out.println("***********************************************\n");

        while (true) {
            System.out.println("\n+----------------------------------+");
            System.out.println("|        SELECT AN OPTION         |");
            System.out.println("+----------------------------------+");
            System.out.println("| 1 - Scan QR Code                |");
            System.out.println("| 2 - Save Hex Values             |");
            System.out.println("| 3 - Exit                        |");
            System.out.println("+----------------------------------+");
            System.out.print("Enter a number: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    Robot();
                    break;
                case "2":
                    Saving.viewLogFile();
                    break;
                case "3":
                    System.out.println("\nShutting down SwiftBot...");
                    forceCloseAPI();
                    System.out.println("System shutdown complete.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void Robot() {
        QRText qrScanner = new QRText(swiftBot);
        Converter converter = new Converter();
        Speed speedController = new Speed(swiftBot);
        LED ledController = new LED(swiftBot);
        Dance danceController = new Dance(swiftBot);
        Scanner inputScanner = new Scanner(System.in);

        firstAttempt = true;

        while (true) {
            if (firstAttempt) {
                System.out.println("\n+----------------------------------+");
                System.out.println("|       Starting Scan Now          |");
                System.out.println("+----------------------------------+");
                firstAttempt = false;
            }

            List<String> hexValues = qrScanner.scanQRCode();

            if (hexValues == null || hexValues.isEmpty()) {
                firstAttempt = true;
                continue;
            }

            if (hexValues.size() > 5) {
                System.out.println("\n[ERROR] Too many values detected! Please scan another QR code with at most 5 values.");
                firstAttempt = true;
                continue;
            }

            System.out.println("\n+----------------------------------+");
            System.out.println("|       QR CODE DETECTED!         |");
            System.out.println("+----------------------------------+");

            lastHexValues = hexValues;

            performDanceSequence(lastHexValues, converter, speedController, ledController, danceController);

            System.out.print("\n[INFO] Press Enter to save...");
            inputScanner.nextLine();
            swiftBotController.saveToFile();

            // **Disable buttons before reassigning them**
            swiftBot.disableButton(Button.Y);
            swiftBot.disableButton(Button.X);
            swiftBot.disableButton(Button.A);

            // **Display user options**
            System.out.println("\nPress 'Y' to scan another QR code, 'X' to exit, or 'A' to repeat the last dance.");

            swiftBot.enableButton(Button.Y, () -> {
                System.out.println("\n[INFO] Scanning another QR code...");
                Robot();
            });

            swiftBot.enableButton(Button.X, () -> {
                System.out.println("\n+----------------------------------+");
                System.out.println("|    ALL SCANNED HEX VALUES       |");
                System.out.println("+----------------------------------+");

                List<String> allHexValues = swiftBotController.getScannedHexValues();
                for (String hex : allHexValues) {
                    System.out.println("|  " + hex);
                }

                System.out.println("+----------------------------------+");
                System.out.println("\n[INFO] Exiting...");

                swiftBotController.saveToFile();
                System.exit(0);
            });

            if (lastHexValues != null) {
                swiftBot.enableButton(Button.A, () -> {
                    System.out.println("\n[INFO] Repeating last dance...");
                    performDanceSequence(lastHexValues, converter, speedController, ledController, danceController);

                    System.out.print("\n[INFO] Press Enter to save...");
                    inputScanner.nextLine();
                    swiftBotController.saveToFile();

                    System.out.println("\nPress 'Y' to scan another QR code, 'X' to exit, or 'A' to repeat the last dance.");
                });
            }

            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    private static void performDanceSequence(List<String> hexValues, Converter converter, Speed speedController, LED ledController, Dance danceController) {
        for (String hexValue : hexValues) {
            swiftBotController.addHexValue(hexValue);
            int decimalValue = converter.hexToDecimal(hexValue);
            String binaryValue = converter.decimalToBinary(decimalValue);
            String octalValue = converter.decimalToOctal(decimalValue);
            int speed = speedController.calculateSpeed(Integer.parseInt(octalValue));
            int[] rgb = ledController.calculateLEDColor(decimalValue);

            System.out.println("\n***********************************");
            System.out.println("Hex: " + hexValue);
            System.out.println("Binary: " + binaryValue);
            System.out.println("Octal: " + octalValue);
            System.out.println("Decimal: " + decimalValue);
            System.out.println("Speed: " + speed);
            System.out.println("LED: (" + rgb[0] + ", " + rgb[1] + ", " + rgb[2] + ")");
            System.out.println("***********************************");

            ledController.setLEDColor(rgb);
            try {
                danceController.performDance(binaryValue, hexValue.length(), speed);
            } catch (InterruptedException e) {
                System.out.println("Error: Dance movement interrupted.");
            }

            ledController.turnOffLED();
        }
    }

    public static void forceCloseAPI() {
        Speed speedController = new Speed(swiftBot);
        LED ledController = new LED(swiftBot);

        speedController.stopWheels();
        ledController.turnOffLED();
    }
}
