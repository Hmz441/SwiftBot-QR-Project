import swiftbot.SwiftBotAPI;

public class Dance {
    private SwiftBotAPI swiftBot;

    public Dance(SwiftBotAPI swiftBot) {
        this.swiftBot = swiftBot;
    }

    public void performDance(String binaryValue, int hexLength, int speed) throws InterruptedException {
        int forwardDuration = (hexLength == 1) ? 1000 : 500; 
        int spinDuration = 3600; // 

        
        if (binaryValue.isEmpty()) {
            binaryValue = "0";
        }

        
        speed = Math.max(speed, 50);

        
        for (int i = binaryValue.length() - 1; i >= 0; i--) {
            char bit = binaryValue.charAt(i);
            if (bit == '1') {
                swiftBot.move(speed, speed, forwardDuration); 
            } else {
                swiftBot.move(speed, 0, spinDuration); 
            }
            Thread.sleep(50); 
        }

        swiftBot.move(0, 0, 500); 
    }
}
