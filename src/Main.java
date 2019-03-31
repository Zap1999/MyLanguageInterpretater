import Operators.Interpretater;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // file
        if (/*args[0] == "-f"*/true) {
            String fileName = "D:\\PE\\Java\\parser2\\src\\light.machine";

            try {
                BufferedReader fileReader =
                        new BufferedReader(
                                new FileReader(fileName));

                Interpretater interpretater = new Interpretater();
                while (true) {
                    String line = fileReader.readLine();
                    if (line == null) break;
                    interpretater.parse(line);
                }
                interpretater.run();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // commands
        else if(/*args[0] == "-c"*/false) {

            Scanner s = new Scanner(System.in);
            Interpretater interpretater = new Interpretater();
            while(true) {
                String line = s.nextLine();
                interpretater.parse(line);
            }

        }
    }
}