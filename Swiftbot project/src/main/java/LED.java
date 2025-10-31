import swiftbot.*;

public class LED {
    private SwiftBotAPI swiftBot;

    public LED(SwiftBotAPI swiftBot) {
        this.swiftBot = swiftBot;
    }

    public int[] calculateLEDColor(int decimalValue) {
        int red = decimalValue;
        int green = (decimalValue % 80) * 3;
        int blue = Math.max(red, green);
        return new int[]{red, green, blue};
    }

    public void setLEDColor(int[] rgb) {
        swiftBot.fillUnderlights(rgb);
    }

    public void fadeOutLED() {
        for (int brightness = 255; brightness >= 0; brightness -= 10) {
            int[] dimColor = {brightness, brightness, brightness};
            setLEDColor(dimColor);
            try { Thread.sleep(50); } catch (InterruptedException ignored) {}
        }
        turnOffLED();
    }

    public void turnOffLED() {
        swiftBot.disableUnderlights();
    }
}