import process.Program;
import system.GlobalSystem;
import system.Scheduler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

//    public static void main(String[] args) throws IOException {
//        GlobalSystem globalSystem = new GlobalSystem();
//        List<Program> programs = new ArrayList<>();
//        for (int i = 1; i <= 10; i++){
//            programs.add(globalSystem.readPrograms(String.format(GlobalSystem.PATH+"%02d.txt", i)));
//        }
//
////        Scheduler.QUANTUM = globalSystem.readQuantum(PATH+"quantum.txt");
////         System.out.println(Scheduler.QUANTUM);
//
//        Scheduler scheduler = new Scheduler();
//
//        List<Integer> prioritities = globalSystem.readPriority(GlobalSystem.PATH+"prioridades.txt");
//        System.out.println("Lista de Prioridades:");
//        System.out.println(prioritities.toString());
//
//        globalSystem.setPriorities(prioritities, programs);
//
//        for(Program p : programs){
//            System.out.println(p.getName());
//            System.out.println(p.getPriority());
//            System.out.println(p.getTextSegment().toString());
//            System.out.println();
//        }
//    }

    public static void main(String[] args) throws IOException {
        Scheduler scheduler = new Scheduler();
        scheduler.executeProcesses();
        System.exit(0);
    }
}