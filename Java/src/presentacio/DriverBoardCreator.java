package presentacio;

import domini.BoardCreator.BoardCreator;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by arnau_000 on 08/11/2015.
 */
public class DriverBoardCreator {

    private BoardCreator mBC;
    private static PrintStream out = System.out;
    private static Scanner in = new Scanner(System.in);


    public DriverBoardCreator(){

    }

    public static void main(String[] args){

        while (true) {
            out.println("Benvingut al creador de taulells de Kenken!\n" +
                    "Seleciona una opció:\n" +
                    "1) Crear un taulell manualment." +
                    "2) Que la CPU em generi un taulell aleatòriament a partir de certs paràmetres.\n" +
                    "3) Sortir\n");

            if (!in.hasNextInt()) break;

            switch (in.nextInt()){
                case 1: {
                    runCBC();

                    break;
                }
                case 2: {
                    runHBC();

                    break;
                }
                case 3: {
                    return;
                }
            }
        }
    }

    public static void runCBC(){
        out.println();
    }

    public static void runHBC(){

    }
}
