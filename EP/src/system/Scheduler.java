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

        Collections.sort(readyProcesses, new Comparator<Process>() {
            @Override
            public int compare(Process p1, Process p2) {
                return Integer.compare(p2.getBCP().getPriority(), p1.getBCP().getPriority());
            }
        });

        for (Process process : readyProcesses) {
            GlobalSystem.writeFile(QUANTUM, "Carregando " + process.getProgram().getName());
        }
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

    private int getPriority(ArrayList<Process> processes, int currentProcess) {
        if (processes.isEmpty())
            return -1;

        Process mostPriorProcess = new Process();
        Process aux = mostPriorProcess;

        for (Process process : processes) {
            if (process.getBCP().getCreditCounter() > aux.getBCP().getCreditCounter())
                mostPriorProcess = process;
            aux = process;
        }

        if (currentProcess != -1 && processes.size() > currentProcess &&
                mostPriorProcess.getBCP().getCreditCounter() == processes.get(currentProcess).getBCP().getCreditCounter())
            return currentProcess;

        return processes.indexOf(mostPriorProcess);
    }

    private void freeBlocked() {
        for (Process blocked : blockedProcesses)
            blocked.decrementWaitingTime();

        if (!blockedProcesses.isEmpty() && blockedProcesses.getFirst().getBCP().getWaitingTime() == 0) {
            Process unblocked = blockedProcesses.removeFirst();
            readyProcesses.add(unblocked);
        }
    }

    private void executeProcess(Process currentProcess) {
        currentProcess.setExecuting();
        Command currentCommand;
        GlobalSystem.X = currentProcess.getBCP().getX();
        GlobalSystem.Y = currentProcess.getBCP().getY();

        for (int i = 0; i < QUANTUM - 1; ) {
            if (currentProcess.getCreditCounter() == 0)
                break;

            currentCommand = currentProcess.getCommand();
            if (currentCommand == Command.COM) {
                currentProcess.executeCommand();
                executedInstructions += 1;
                i += 1;
            } else if (currentCommand == Command.ES) {
                executedInstructions += 1;
                currentProcess.block();
                GlobalSystem.writeFile(QUANTUM, "E/S iniciada em " + currentProcess.getProgram().getName() + " após " + executedInstructions + " instruções.");
                readyProcesses.remove(currentProcess);
                blockedProcesses.add(currentProcess);
                countQuantum += 1;
                return;
            } else if (currentCommand == Command.REGISTERVALUE) {
                executedInstructions += 1;
            } else if (currentCommand == Command.SAIDA) {
                executedInstructions += 1;
                readyProcesses.remove(currentProcess);
                currentProcess.getBCP().setState(ProcessState.BLOQUEADO);
                GlobalSystem.writeFile(QUANTUM, currentProcess.getProgram().getName() + " terminado. X=" + GlobalSystem.X + " Y=" + GlobalSystem.Y);
                countQuantum += 1;
                return;
            }
        }

        countQuantum += 1;
        currentProcess.setReady();
    }

    public void executeProcesses() {
        int index = getPriority(readyProcesses, -1);
        String lastName = "";
        Process lastProcess = null;
        Process currentProcess = null;
        GlobalSystem.writeFile(QUANTUM, "\nExecutando " + readyProcesses.get(index).getProgram().getName());

        while (!readyProcesses.isEmpty() || !blockedProcesses.isEmpty()) {
            currentProcess = readyProcesses.get(index);

            if (lastProcess != null && currentProcess != lastProcess && currentProcess.getCreditCounter() != 0) {
                countExecutedInstructions += executedInstructions;

                if (lastProcess.getBCP().getState() != ProcessState.BLOQUEADO)
                    GlobalSystem.writeFile(QUANTUM, "Interrompendo " + lastProcess.getProgram().getName() + " após " + executedInstructions + " instruções.");
                GlobalSystem.writeFile(QUANTUM, "\n");
                GlobalSystem.writeFile(QUANTUM, "Executando " + currentProcess.getProgram().getName());

                swichProcesses += 1;
                executedInstructions = 0;
            }

            executeProcess(currentProcess);
            restartCreditCounter();

            do freeBlocked();
            while (readyProcesses.isEmpty() && !blockedProcesses.isEmpty());

            lastProcess = currentProcess;
            index = getPriority(readyProcesses, index);
        }

        float averageInstructions = (float) countExecutedInstructions /countQuantum;

        GlobalSystem.writeFile(QUANTUM, "\nTrocas de processo: " + swichProcesses);
        GlobalSystem.writeFile(QUANTUM, "Média de instruções por quantum: " + averageInstructions);

    }
}