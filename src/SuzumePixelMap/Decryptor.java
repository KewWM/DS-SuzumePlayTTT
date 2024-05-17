package SuzumePixelMap;

public class Decryptor {

    public static void main(String[] args) {
        int secretKey = 7;
        int decryptedNumber = decrypt(17355, secretKey);
        System.out.println("Decrypted number: "+decryptedNumber);
    }

    public static int decrypt(int decryptedNumber, int secretKey) {
        String decryptedBinaryString = Integer.toBinaryString(decryptedNumber);
        StringBuilder encryptedBinaryStringBuilder = new StringBuilder();
    
        while (decryptedBinaryString.length() % 3 != 0) {
            decryptedBinaryString = "0" + decryptedBinaryString; // Padding with leading zeros if necessary
        }
    
        for (int i = decryptedBinaryString.length() - 3; i >= 0; i -= 3) {
            String block = decryptedBinaryString.substring(i, i + 3);
            int blockValue = Integer.parseInt(block, 2);
    
            int actualValue = blockValue - (secretKey % 2);
    
            String actualBinaryString = Integer.toBinaryString(actualValue);
            while (actualBinaryString.length() < 3) {
                actualBinaryString = "0" + actualBinaryString; // Padding actual binary string with leading zeros if necessary
            }
    
            secretKey = secretKey >> 1;
    
            encryptedBinaryStringBuilder.insert(0, actualBinaryString);
        }
    
        String encryptedBinaryString = encryptedBinaryStringBuilder.toString();
        int decryptedResult = Integer.parseInt(encryptedBinaryString, 2);
        return decryptedResult;
    }
    
}
