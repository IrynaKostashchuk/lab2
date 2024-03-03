/*
  @author   rakel
  @project   lab2
  @class  Decrypted
  @version  1.0.0 
  @since 03.03.2024 - 22.18
*/

import java.io.*;
import java.util.Scanner;

public class Decrypted {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String inputFile = "outputFile";


        System.out.print("Введіть параметр 'a' (ціле число, НСД(a, 26) = 1): ");
        int a = scanner.nextInt();
        System.out.print("Введіть параметр 'b' (ціле число): ");
        int b = scanner.nextInt();
        int m = 26;

        String cipherText = readFromFile(inputFile);

        String decryptedText = decrypt(cipherText, a, b, m);

        String outputFile ="decryptedFile";

        writeToFile(outputFile, decryptedText);

        System.out.println("Розшифрування завершено");
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

    private static String decrypt(String text, int a, int b, int m) {
        StringBuilder result = new StringBuilder();

        int aInverse = findInverse(a, m);

        for (char character : text.toCharArray()) {
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                int x = character - base;
                int decryptedChar = (aInverse * (x - b)) % m;
                if (decryptedChar < 0) {
                    decryptedChar += m;
                }
                result.append((char) (decryptedChar + base));
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    private static int findInverse(int a, int m) {
        int m0 = m, t, q;
        int x0 = 0, x1 = 1;

        if (m == 1) {
            return 0;
        }

        // Застосовуємо розширений алгоритм Евкліда для знаходження оберненого за модулем числа
        while (a > 1) {
            q = a / m;
            t = m;

            m = a % m;
            a = t;
            t = x0;

            x0 = x1 - q * x0;
            x1 = t;
        }

        // Забезпечення того, що x1 є позитивним числом
        if (x1 < 0) {
            x1 += m0;
        }

        return x1;
    }

    private static void writeToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
