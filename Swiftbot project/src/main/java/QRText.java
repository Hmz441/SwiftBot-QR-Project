import swiftbot.SwiftBotAPI;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class QRText {
    private SwiftBotAPI swiftBot;

    public QRText(SwiftBotAPI swiftBot) {
        this.swiftBot = swiftBot;
    }

    public List<String> scanQRCode() {
        List<String> hexValues = new ArrayList<>();
        boolean waitingMessageShown = false; 

        while (true) {
            if (!waitingMessageShown) {
                System.out.println("\n+----------------------------------+");
                System.out.println("|       Waiting for QR Code...    |");
                System.out.println("+----------------------------------+");
                waitingMessageShown = true;
            }

            try {
                BufferedImage img = swiftBot.getQRImage(); 

                
                if (img == null) {
                   
                    Thread.sleep(1000); 
                    continue;
                }

                String qrData = swiftBot.decodeQRImage(img); 

                
                if (qrData == null || qrData.trim().isEmpty()) {
                    
                    Thread.sleep(1000);
                    continue;
                }

                System.out.println("\nQR Code Detected: " + qrData);
                waitingMessageShown = false;

                
                String[] rawHexValues = qrData.split(":");
                hexValues.clear();

                for (String value : rawHexValues) {
                    value = value.trim().toUpperCase();

                    
                    if (value.matches("^[0-9A-F]{1,2}$")) {
                        hexValues.add(value);
                    } else {
                        System.out.println("[WARNING] Invalid hex value ignored: " + value);
                    }
                }

                
                if (hexValues.size() > 5) {
                    System.out.println("\n[ERROR] Too many values detected! Please scan another QR code with at most 5 values.");
                    Thread.sleep(2000); 
                    continue; 
                }

                return hexValues; 
            } catch (Exception e) {
                System.out.println("[ERROR] Failed to scan QR code: " + e.getMessage());
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException ignored) {}
            }
        }
    }
}