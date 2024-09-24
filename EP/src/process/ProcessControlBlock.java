package process;

import util.*;

public class ProcessControlBlock {
    private int programCounter;
    private ProcessState state;
    private int priority;
    private int creditCounter;
    private int[] generalRegisters;
    private int textSegmentRef;
    private int waitingTime;

    public ProcessControlBlock(int programCounter, ProcessState state, int priority, int creditCounter, int[] generalRegisters, int textSegmentRef) {
        this.programCounter = programCounter;
        this.state = state;
        this.priority = priority;
        this.creditCounter = creditCounter;
        this.generalRegisters = generalRegisters;
        this.textSegmentRef = textSegmentRef;
    }

    public ProcessControlBlock(int priority) {
        this.priority = priority;
        state = ProcessState.PRONTO;
        textSegmentRef = 0;
        creditCounter = priority;
        waitingTime = 0;
        generalRegisters = new int[2];
    }

    // Getters and Setters
    public int getProgramCounter() {
        return programCounter;
    }

    public void setProgramCounter(int programCounter) {
        this.programCounter = programCounter;
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

    public int getTextSegmentRef() {
        return textSegmentRef;
    }

    public void setTextSegmentRef(int textSegmentRef) {
        this.textSegmentRef = textSegmentRef;
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