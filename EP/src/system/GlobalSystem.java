package system;
import process.Program;
import util.Command;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Class System (Sistema)
public class GlobalSystem {

    public Program readPrograms(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String name;
        List<String> textSegments = new ArrayList<>();

        name = reader.readLine();

        String line;
        while ((line = reader.readLine()) != null) {
            textSegments.add(line);
        }

        reader.close();
        return new Program(name, textSegments);
    }

    public List<Integer> readPriority(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<Integer> priority = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            priority.add(Integer.valueOf(line));
        }

        reader.close();
        return priority;
    }

    public void setPriorities(List<Integer> priorities, List<Program> programs) {
        for (int i = 0; i < programs.size(); i++) {
            String title = String.format("TESTE-%d", priorities.get(i));

            Optional<Program> programOpt = programs.stream()
                    .filter(p -> p.getName().equals(title))
                    .findAny();

            if (programOpt.isPresent()) {
                Program program = programOpt.get();
                if(program.getPriority() == null) program.setPriority(i + 1);

            }
        }
    }

    public int readQuantum(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int quantum = Integer.parseInt(reader.readLine());
        reader.close();

        return quantum;
    }



    public Command readCommand() {
        // Lógica para ler um comando
        return Command.COM; // Retorno de exemplo
    }
}