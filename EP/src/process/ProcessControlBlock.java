package process;

import util.*;

public class ProcessControlBlock {
    private ProcessState state;
    private int priority;
    private int creditCounter;
    private int[] generalRegisters;
    private int programCounter;
    private int waitingTime;

    public ProcessControlBlock(int programCounter, ProcessState state, int priority, int creditCounter, int[] generalRegisters) {
        this.programCounter = programCounter;
        this.state = state;
        this.priority = priority;
        this.creditCounter = creditCounter;
        this.generalRegisters = generalRegisters;
    }

    public ProcessControlBlock(int priority) {
        this.priority = priority;
        state = ProcessState.PRONTO;
        programCounter = 0;
        creditCounter = priority;
        waitingTime = 0;
        generalRegisters = new int[2];
    }

    public ProcessState getState() {
        return state;
    }

    public void setState(ProcessState state) {
        this.state = state;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getCreditCounter() {
        return creditCounter;
    }

    public void setCreditCounter(int creditCounter) {
        this.creditCounter = creditCounter;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setX(int value) {
        generalRegisters[0] = value;
    }

    public void setY(int value) {
        generalRegisters[1] = value;
    }

    public int getX() {
        return generalRegisters[0];
    }

    public int getY() {
        return generalRegisters[1];
    }
}