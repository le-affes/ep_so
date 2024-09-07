package system;
import util.*;
import java.util.ArrayList;

class Scheduler {
    public static final int MAX_COM = 100;
    public static int X;
    public static int Y;
    public static ArrayList<Process> readyProcesses = new ArrayList<>();
    public static ArrayList<Process> blockedProcesses = new ArrayList<>();
    public static ArrayList<ProcessControlBlock> tableProcess = new ArrayList<>();

    public static void executeProcesses() {
        // Lógica para executar processos
    }
}