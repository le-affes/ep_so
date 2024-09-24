package system;

import process.Program;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

// Class System (Sistema)
public class GlobalSystem {
    public static String PATH = "src\\system\\programs\\";
    public static String FILE_PATH = "src\\system\\";
    public static int X;
    public static int Y;

    public static Program readPrograms(String filePath) throws IOException {
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

    public static List<Integer> readPriority(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<Integer> priority = new ArrayList<>();

        String line;
        while ((line = reader.readLine()) != null) {
            priority.add(Integer.valueOf(line));
        }

        reader.close();
        return priority;
    }

    public static void setPriorities(List<Integer> priorities, List<Program> programs) {
        for (int i = 0; i < programs.size(); i++) {
            programs.get(i).setPriority(priorities.get(i));
        }
    }

    public static int readQuantum(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int quantum = Integer.parseInt(reader.readLine());
        reader.close();

        return quantum;
    }

    public static void writeFile(int quantum, String conteudo) {
        System.out.println(conteudo);
        String caminhoArquivo = FILE_PATH + "\\output\\" + String.format("%02d", quantum) + ".txt";
        File file = new File(caminhoArquivo);

        boolean appendMode = file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo, appendMode))) {
            writer.write(conteudo);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}