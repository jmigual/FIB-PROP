package presentacio;

import dades.Table;
import domini.BoardCreator.BoardCreator;
import domini.BoardCreator.CpuBoardCreator;
import domini.BoardCreator.HumanBoardCreator;
import domini.KKBoard;
import javafx.scene.control.Tab;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Created by arnau_000 on 08/11/2015.
 */
public class DriverBoardCreator {

    private static PrintStream out = System.out;
    private static Scanner in = new Scanner(System.in);
    private static Table<KKBoard> mTableKKB;

    public DriverBoardCreator(){
        mTableKKB = new Table<>();
    }

    public static void main(String[] args){

        while (true) {
            out.println("Benvingut al creador de taulells de Kenken!\n" +
                    "Seleciona una opci�:\n" +
                    "1) Crear un taulell manualment.\n" +
                    "2) Que la CPU em generi un taulell aleat�riament a partir de certs par�metres.\n" +
                    "3) Sortir\n");

            if (!in.hasNextInt()) break;

            switch (in.nextInt()){
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

    protected static void runCBC(){
        out.println("Introdueix la mida del taulell:");
        int size = in.nextInt();
        CpuBoardCreator CBC = new CpuBoardCreator(size, mTableKKB);

        while (true){
            out.println("Selecciona una opció:\n" +
                    "1) Canviar la mida màxima de les regions\n" +
                    "2) Canviar pesos\n" +
                    "3) Generar un taulell\n" +
                    "4) Veure el taulell\n" +
                    "5) Guardar el taulell\n" +
                    "6) Sortir");

            if (!in.hasNextInt()) break;

            switch (in.nextInt()){
                case 1: {
                    out.println("Introdueix la mida màxima de les regions:");
                    int m = in.nextInt();
                    CBC.setMaxRegionSize(m);
                    out.println("Mida màxima canviada a " + m + ".");
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
                    out.print("Quin nom vols posar al taulell?");
                    String s = in.next();
                    CBC.saveBoard(s);
                    break;
                }
                case 6: {
                    return;
                }
            }
        }
    }

    protected static  void editWeights(CpuBoardCreator CBC){
        while (true) {
            out.println("Pesos de les mides de regions: (mida)-->(pes)");
            for (int i = 0; i < CBC.getMaxRegionSize(); ++i) {
                out.println(i+1 + "-->" + CBC.getSizesWeights().get(i));
            }
            out.println("Total:" + CBC.getTotalSizesWeight() + "\n" +
                    "Pesos de les operacions en les regions:\n" +
                    "Divisió (d): " + CBC.getDivWeight() + "\n" +
                    "Resta (r): " + CBC.getSubsWeight() + "\n" +
                    "Producte (p): " + CBC.getProdWeight() + "\n" +
                    "Suma (s): " + CBC.getAddWeight() + "\n" +
                    "Total: " + CBC.getTotalOpWeight() + "\n" +
                    "\n" +
                    "Vols modificar els pesos de les mides (m) o de les operacions (o), o bé tornar al menú " +
                    "anterior (t)?\n" +
                    "Nota: els pesos de les operacions estan condicionats als de les mides.\n");
            boolean b = true;
            while (b) {
                switch (in.next()) {
                    case "m":
                        out.println("Escriu la mida i després el pes:");
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
                        out.println("Escriu la lletra que identifica l'operació (d,r,p,s) i després el seu pes:");
                        switch (in.next()){
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

    public static void runHBC(){
        out.print("Inserta una mida pel taulell:");
        HumanBoardCreator.setBoardCreator(in.nextInt(), mTableKKB);

        boolean printMenu = true;
        while (true) {
            if (printMenu) {
                out.print("Selecciona una opció:\n" +
                        "0) Deixar d'imprimir el menú per pantalla\n" +
                        "1) Veure el taulell\n" +
                        "2) Omplir el taulell de nombres aleatoris\n" +
                        "3) Crear una regió\n" +
                        "4) Modificar una regió\n" +
                        "5) Comprovar que el taulell sigui resoluble\n" +
                        "6) Resoldre el taulell\n" +
                        "7) Guardar el taulell\n" +
                        "8) Sortir\n");
            }
            out.print(">> ");
            switch (in.nextInt()){
                case 0: {
                    printMenu = false;
                    break;
                }
                case 1: {
                    DriverKKBoardPrinter.printBoard(HumanBoardCreator.getBoard(),out);
                    break;
                }
                case 2: {
                    if (printMenu) {
                        out.print("Entra les coordenades de la cel·la (fila,columna) (començant per l'índex 1)" +
                                " i després entra el valor de la cel·la:\n" +
                                ">> ");
                    }
                    else {
                        out.print("(i,j)<-n >> ");
                    }
                    HumanBoardCreator.getBoard().getCell(in.nextInt()-1,in.nextInt()-1).setValue(in.nextInt());
                    break;
                }
                case 3: {
                    out.print("Número de cel·les: ");
                    int n = in.nextInt();
                    for (int i=1; i<=n; ++i){
                        out.print("Coordenades (fila, columna) de la cel·la " + i + ": ");
                    }
                    break;
                }
                case 4: {
                    break;
                }
                case 5: {
                    break;
                }
                case 6: {
                    break;
                }
                case 7: {
                    break;
                }
                case 8: {
                    return;
                }

            }
        }
    }
}
