import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Saving {
    private static final List<String> scannedHexValues = new ArrayList<>();
    private static final String FILE_NAME = "hex_log.txt"; 

    public void addHexValue(String hexValue) {
        if (!scannedHexValues.contains(hexValue)) scannedHexValues.add(hexValue);
    }

    public List<String> getScannedHexValues() {
        return new ArrayList<>(scannedHexValues);
    }

    public void saveToFile() {
        if (scannedHexValues.isEmpty()) {
            System.out.println("\n[WARNING] No hex values to save.");
            return;
        }

        try {
            ensureFileExists(); 

            try (FileWriter writer = new FileWriter(FILE_NAME, false)) { 
                System.out.println("\n Writing hex values to file:");
                writeHexValues(writer);
                System.out.println("\n[INFO] Hex values saved successfully to: " + new File(FILE_NAME).getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("\n[ERROR] Failed to save file: " + e.getMessage());
        }
    }

    private void ensureFileExists() throws IOException {
        File file = new File(FILE_NAME);

        
        if (!file.exists()) {
            if (file.createNewFile()) {
                System.out.println("\n[INFO] Log file created: " + file.getAbsolutePath());
            } else {
                System.out.println("\n[ERROR] Failed to create log file: " + file.getAbsolutePath());
            }
        }
    }

    private void writeHexValues(FileWriter writer) throws IOException {
        Collections.sort(scannedHexValues);
        for (String hex : scannedHexValues) {
            System.out.println("Saving: " + hex);
            writer.write(hex + "\n");
        }
        writer.flush();
    }

    public static void viewLogFile() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("\n[WARNING] No log file found. Scan QR codes first.");
        } else {
            System.out.println("\n[INFO] Hexadecimal log file saved at: " + file.getAbsolutePath());
        }
    }
}
