package process;

import system.GlobalSystem;
import util.*;

import java.util.List;

public class Process {
    private int id;
    private Program program;
    private ProcessControlBlock BCP;

    public Process(int id, Program program, ProcessControlBlock BCP) {
        this.id = id;
        this.program = program;
        this.BCP = BCP;
    }

    public Process() {
        BCP = new ProcessControlBlock(-1);
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public ProcessControlBlock getBCP() {
        return BCP;
    }

    public void setBCP(ProcessControlBlock BCP) {
        this.BCP = BCP;
    }

    public void executeCommand() {
        BCP.setCreditCounter(BCP.getCreditCounter() - 1);
    }

    public List<String> getTextSegment() {
        return this.program.getTextSegment();
    }

    public Command getCommand() {
        if(BCP.getProgramCounter() >= getTextSegment().size())
            return Command.SAIDA;

        String current = getTextSegment().get(BCP.getProgramCounter());
        Command currentCommand;
        if (current.equals("COM"))
            currentCommand = Command.COM;
        else if (current.equals("E/S"))
            currentCommand = Command.ES;
        else if (current.charAt(0) == 'X') {
            GlobalSystem.X = getRegisterValue(current);
            BCP.setX(getRegisterValue(current));
            currentCommand = Command.REGISTERVALUE;
        } else if (current.charAt(0) == 'Y') {
            GlobalSystem.Y = getRegisterValue(current);
            BCP.setY(getRegisterValue(current));
            currentCommand = Command.REGISTERVALUE;
        } else {
            currentCommand = Command.SAIDA;
        }

        BCP.setProgramCounter(BCP.getProgramCounter() + 1);
        return currentCommand;
    }

    public int getCreditCounter(){
        return BCP.getCreditCounter();
    }

    public void setCreditCounter(int value){
        this.BCP.setCreditCounter(value);
    }

    public void block() {
        BCP.setState(ProcessState.BLOQUEADO);
        BCP.setWaitingTime(2);
    }

    public void setReady() {
        BCP.setState(ProcessState.PRONTO);
    }

    public void setExecuting() {
        BCP.setState(ProcessState.EXECUTANDO);
    }

    public void decrementWaitingTime() {
        if(BCP.getWaitingTime()>0)
            BCP.setWaitingTime(BCP.getWaitingTime() - 1);
    }

    private int getRegisterValue(String str) {
        // Divide a string pelo sinal de igual
        String[] partes = str.split("=");
        // Converte a parte que contém o valor para inteiro
        return Integer.parseInt(partes[1]);
    }
}

