package system;

import process.*;
import process.Process;
import util.Command;
import util.ProcessState;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

public class Scheduler {
    private int QUANTUM;
    private int executedInstructions = 0;
    private int swichProcesses = 0;
    private int countQuantum = 0;
    private int countExecutedInstructions = 0;

    private ArrayList<Process> readyProcesses = new ArrayList<>();
    private ArrayList<Process> blockedProcesses = new ArrayList<>();
    private ArrayList<ProcessControlBlock> tableProcess = new ArrayList<>();

    public Scheduler() throws IOException {
        QUANTUM = GlobalSystem.readQuantum(GlobalSystem.PATH + "quantum.txt");

        List<Program> programs = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            programs.add(GlobalSystem.readPrograms(String.format(GlobalSystem.PATH + "%02d.txt", i)));
        }

        List<Integer> prioritities = GlobalSystem.readPriority(GlobalSystem.PATH + "prioridades.txt");
        GlobalSystem.setPriorities(prioritities, programs);

        for (int i = 0; i < programs.size(); i++) {
            ProcessControlBlock processControlBlock = new ProcessControlBlock(programs.get(i).getPriority());
            Process process = new Process(i + 1, programs.get(i), processControlBlock);
            readyProcesses.add(process);
            tableProcess.add(processControlBlock);
        }

        orderProcesses(readyProcesses);

        for (Process process : readyProcesses) {
            GlobalSystem.writeFile(QUANTUM, "Carregando " + process.getProgram().getName());
        }
    }

    private void orderProcesses(ArrayList<Process> processes) {
        Collections.sort(processes, new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                return Integer.compare(p2.getBCP().getCreditCounter(), p1.getBCP().getCreditCounter());
            }
        });
    }

    private void restartCreditCounter() {
        for (Process process : readyProcesses) {
            if (process.getCreditCounter() > 0)
                return;
        }

        for (Process process : readyProcesses) {
            process.getBCP().setCreditCounter(process.getBCP().getPriority());
        }
    }

//    private int getPriority(ArrayList<Process> processes, int currentProcess) {
//        if (processes.isEmpty())
//            return -1;
//
//        Process mostPriorProcess = new Process();
//        Process aux = mostPriorProcess;
//
//        for (Process process : processes) {
//            if (process.getBCP().getCreditCounter() > aux.getBCP().getCreditCounter())
//                mostPriorProcess = process;
//            aux = process;
//        }
//
//        if (currentProcess != -1 && processes.size() > currentProcess &&
//                mostPriorProcess.getBCP().getCreditCounter() == processes.get(currentProcess).getBCP().getCreditCounter())
//            return currentProcess;
//
//        return processes.indexOf(mostPriorProcess);
//    }

    private void freeBlocked() {
        for (Process blocked : blockedProcesses)
            blocked.decrementWaitingTime();

        if (!blockedProcesses.isEmpty() && blockedProcesses.getFirst().getBCP().getWaitingTime() == 0) {
            Process unblocked = blockedProcesses.removeFirst();
            readyProcesses.add(unblocked);
        }
    }

    private void executeProcess(Process currentProcess) {
        if (currentProcess.getCreditCounter() == 0)
            return;

        GlobalSystem.writeFile(QUANTUM, "\nExecutando " + readyProcesses.getFirst().getProgram().getName());

        currentProcess.setExecuting();
        Command currentCommand;
        GlobalSystem.X = currentProcess.getBCP().getX();
        GlobalSystem.Y = currentProcess.getBCP().getY();

        int i = 0;
        for (i = 0; i < QUANTUM; i++) {

            currentCommand = currentProcess.getCommand();
            if (currentCommand == Command.COM) {
                currentProcess.executeCommand();
            } else if (currentCommand == Command.ES) {
                currentProcess.executeCommand();
                currentProcess.block();
                i += 1;
                GlobalSystem.writeFile(QUANTUM, "E/S iniciada em " + currentProcess.getProgram().getName() + " após " + i + " instruções.");
                readyProcesses.remove(currentProcess);
                blockedProcesses.add(currentProcess);
                break;
            } else if (currentCommand == Command.REGISTERVALUE) {

            } else if (currentCommand == Command.SAIDA) {
                readyProcesses.remove(currentProcess);
                currentProcess.setCreditCounter(0);
                GlobalSystem.writeFile(QUANTUM, currentProcess.getProgram().getName() + " terminado. X=" + GlobalSystem.X + " Y=" + GlobalSystem.Y);
                executedInstructions += 1;
                return;
            }
            executedInstructions += 1;
        }
        countQuantum += 1;

        swichProcesses += 1;
        GlobalSystem.writeFile(QUANTUM, "Interrompendo " + currentProcess.getProgram().getName() + " após " + i + " instruções.");

        currentProcess.setReady();
    }

    public void executeProcesses() {

        Process currentProcess = readyProcesses.getFirst();
        double countProcesses = (double) readyProcesses.size();

        while (!readyProcesses.isEmpty() || !blockedProcesses.isEmpty()) {
            do freeBlocked();
            while (readyProcesses.isEmpty() && !blockedProcesses.isEmpty());

            if (readyProcesses.getFirst().getCreditCounter() > currentProcess.getCreditCounter())
                currentProcess = readyProcesses.getFirst();

            executeProcess(currentProcess);
            restartCreditCounter();
            orderProcesses(readyProcesses);
        }

        double averageSwichProcesses = (double) swichProcesses / countProcesses;
        double averageInstructions = (double) executedInstructions / countQuantum;

        GlobalSystem.writeFile(QUANTUM, "Média de trocas " + averageSwichProcesses);
        GlobalSystem.writeFile(QUANTUM, "Média de instruções " + averageInstructions);
    }
}