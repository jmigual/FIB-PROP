package presentacio;

import domini.BoardCreator.CpuBoardCreator;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by arnau_000 on 08/11/2015.
 */
public class DriverBoardCreator {

    private static PrintStream out = System.out;
    private static Scanner in = new Scanner(System.in);

    public DriverBoardCreator() {
    }

    public static void main(String[] args) {

        while (true) {
            out.println("Benvingut al creador de taulells de Kenken!\n" +
                    "Seleciona una opci�:\n" +
                    "1) Crear un taulell manualment.\n" +
                    "2) Que la CPU em generi un taulell aleat�riament a partir de certs par�metres.\n" +
                    "3) Sortir\n");

            if (!in.hasNextInt()) break;

            switch (in.nextInt()) {
                case 1: {
                    runHBC();
                    break;
                }
                case 2: {
                    runCBC();
                    break;
                }
                case 3: {
                    return;
                }
            }
        }
    }

    protected static void runCBC() {
        out.println("Introdueix la mida del taulell:");
        int size = in.nextInt();
        CpuBoardCreator CBC = new CpuBoardCreator(size);

        while (true) {
            out.println("Selecciona una opci�:\n" +
                    "1) Canviar la mida m�xima de les regions\n" +
                    "2) Canviar pesos\n" +
                    "3) Generar un taulell\n" +
                    "4) Veure el taulell\n" +
                    "5) Guardar el taulell\n" +
                    "6) Sortir");

            if (!in.hasNextInt()) break;

            switch (in.nextInt()) {
                case 1: {
                    out.println("Introdueix la mida m�xima de les regions:");
                    int m = in.nextInt();
                    CBC.setMaxRegionSize(m);
                    out.println("Mida m�xima canviada a " + m + ".");
                    break;
                }
                case 2: {
                    editWeights(CBC);
                    break;
                }
                case 3: {
                    try {
                        CBC.createBoard();
                    } catch (Exception e) {
                        out.println(e.getMessage());
                    }
                    break;
                }
                case 4: {
                    DriverKKBoardPrinter.printBoard(CBC.getBoard(), out);
                    break;
                }
                case 5: {
                    out.println("Feature not available yet :P\n");
                    break;
                }
                case 6: {
                    return;
                }
            }
        }
    }

    protected static void editWeights(CpuBoardCreator CBC) {
        while (true) {
            out.println("Pesos de les mides de regions: (mida)-->(pes)");
            for (int i = 0; i < CBC.getMaxRegionSize(); ++i) {
                out.println(i + 1 + "-->" + CBC.getSizesWeights().get(i));
            }
            out.println("Total:" + CBC.getTotalSizesWeight() + "\n" +
                    "Pesos de les operacions en les regions:\n" +
                    "Divisi� (d): " + CBC.getDivWeight() + "\n" +
                    "Resta (r): " + CBC.getSubsWeight() + "\n" +
                    "Producte (p): " + CBC.getProdWeight() + "\n" +
                    "Suma (s): " + CBC.getAddWeight() + "\n" +
                    "Total: " + CBC.getTotalOpWeight() + "\n" +
                    "\n" +
                    "Vols modificar els pesos de les mides (m) o de les operacions (o), o b� tornar al men� " +
                    "anterior (t)?\n" +
                    "Nota: els pesos de les operacions estan condicionats als de les mides.\n");
            boolean b = true;
            while (b) {
                switch (in.next()) {
                    case "m":
                        out.println("Escriu la mida i despr�s el pes:");
//                        CBC.getSizesWeights().set(in.nextInt()-1,in.nextInt());
                        try {
                            CBC.setSizeWeight(in.nextInt(), in.nextInt());
                        } catch (Exception e) {
                            out.println(e.getMessage());
                            return;
                        }
                        b = false;
                        break;
                    case "o":
                        out.println("Escriu la lletra que identifica l'operaci� (d,r,p,s) i despr�s el seu pes:");
                        switch (in.next()) {
                            case "d":
                                CBC.setDivWeight(in.nextInt());
                                break;
                            case "r":
                                CBC.setSubsWeight(in.nextInt());
                                break;
                            case "p":
                                CBC.setProdWeight(in.nextInt());
                                break;
                            case "s":
                                CBC.setAddWeight(in.nextInt());
                                break;
                        }
                        b = false;
                        break;
                    case "t":
                        return;
                }
            }
        }
    }

    public static void runHBC() {

    }
}
