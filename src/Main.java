import java.io.*;
import java.util.Scanner;

/*
  @author   ${USER}
  @project   ${PROJECT_NAME}
  @class  ${NAME}
  @version  1.0.0 
  @since ${DATE} - ${HOUR}.${MINUTE}
*/public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String inputFile = "inputFile";
        String outputFile = "outputFile";

        System.out.print("Введіть параметр 'a' (ціле число, НСД(a, 26) = 1): ");
        int a = scanner.nextInt();
        System.out.print("Введіть параметр 'b' (ціле число): ");
        int b = scanner.nextInt();
        int m = 26;

        String plainText = readFromFile(inputFile);

        String cipherText = encrypt(plainText, a, b, m);

        writeToFile(outputFile, cipherText);

        System.out.println("Шифрування завершено");
    }

    private static String readFromFile(String fileName) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    private static String encrypt(String text, int a, int b, int m) {
        StringBuilder result = new StringBuilder();

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                int x = character - base;
                int encryptedChar = (a * x + b) % m;
                if (encryptedChar < 0) {
                    encryptedChar += m;
                }
                result.append((char) (encryptedChar + base));
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    private static void writeToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}