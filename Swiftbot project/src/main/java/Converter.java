public class Converter {
    
    public int hexToDecimal(String hex) {
        int decimalValue = 0;
        int base = 1;

        
        for (int i = hex.length() - 1; i >= 0; i--) {
            char currentChar = hex.charAt(i);

            if (currentChar >= '0' && currentChar <= '9') {
                decimalValue += (currentChar - '0') * base;
            } else if (currentChar >= 'A' && currentChar <= 'F') {
                decimalValue += (currentChar - 'A' + 10) * base;
            }
            base *= 16;
        }
        return decimalValue;
    }

    public String decimalToBinary(int decimal) {
        
        if (decimal == 0) {
            return "0";
        }

        String binary = "";
        while (decimal > 0) {
            binary = (decimal % 2) + binary;
            decimal /= 2;
        }
        return binary;
    }

    public String decimalToOctal(int decimal) {
        
        if (decimal == 0) {
            return "0";
        }

        String octal = "";
        while (decimal > 0) {
            octal = (decimal % 8) + octal;
            decimal /= 8;
        }
        return octal;
    }
}