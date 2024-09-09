import process.Program;
import system.GlobalSystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        GlobalSystem globalSystem = new GlobalSystem();
        List<Program> programs = new ArrayList<>();
        for (int i = 1; i <= 10; i++){
            programs.add(globalSystem.readPrograms(String.format("C:\\Projetos\\ep_so\\EP\\src\\system\\programs\\%02d.txt", i)));
        }

        int quantum = globalSystem.readQuantum("C:\\Projetos\\ep_so\\EP\\src\\system\\programs\\quantum.txt");
        System.out.println(quantum);

        List<Integer> prioritities = globalSystem.readPriority("C:\\Projetos\\ep_so\\EP\\src\\system\\programs\\prioridades.txt");
        System.out.println("Lista de Prioridades:");
        System.out.println(prioritities.toString());

        globalSystem.setPriorities(prioritities, programs);

        for(Program p : programs){
            System.out.println(p.getName());
            System.out.println(p.getPriority());
            System.out.println(p.getTextSegment().toString());
            System.out.println();
        }


    }
}