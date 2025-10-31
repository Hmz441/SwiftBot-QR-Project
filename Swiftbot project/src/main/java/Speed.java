import swiftbot.SwiftBotAPI;

public class Speed {
    private SwiftBotAPI swiftBot;

    public Speed(SwiftBotAPI swiftBot) {
        this.swiftBot = swiftBot;
    }

    public int calculateSpeed(int octalValue) {
        int speed;

        if (octalValue < 50) {
            speed = octalValue + 50; 
        } else if (octalValue > 100) {
            speed = 100; 
        } else {
            speed = octalValue; 
        }

        
        return (speed == 0) ? 50 : speed;
    }

    public void stopWheels() {
        swiftBot.move(0, 0, 0); 
    }
}