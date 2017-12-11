package com.ywc.mgt.processcontrol.base.model;

import java.util.List;

/**
 * 流程对应主体
 *
 * @author yanwe
 * createTime 2017-11-2017/11/9 16:12
 */
public class Process {

    private String processId;

    private String bpmId;

    private List<ProcessOrder> processOrder;

    private ProcessBaseSession processSession;

    private List<ProcessSchedule> processSchedule;

    public ProcessBaseSession getProcessSession() {
        return processSession;
    }

    public void setProcessSession(ProcessBaseSession processSession) {
        this.processSession = processSession;
    }

    public List<ProcessSchedule> getProcessSchedule() {
        return processSchedule;
    }

    public void setProcessSchedule(List<ProcessSchedule> processSchedule) {
        this.processSchedule = processSchedule;
    }

    public List<ProcessOrder> getProcessOrder() {
        return processOrder;
    }

    public void setProcessOrder(List<ProcessOrder> processOrder) {
        this.processOrder = processOrder;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getBpmId() {
        return bpmId;
    }

    public void setBpmId(String bpmId) {
        this.bpmId = bpmId;
    }


    public static final class ProcessBuilder {
        private String processId;
        private String bpmId;
        private List<ProcessOrder> processOrder;
        private ProcessBaseSession processSession;
        private List<ProcessSchedule> processSchedule;

        private ProcessBuilder() {
        }

        public static ProcessBuilder aProcess() {
            return new ProcessBuilder();
        }

        public ProcessBuilder withProcessId(String processId) {
            this.processId = processId;
            return this;
        }

        public ProcessBuilder withBpmId(String bpmId) {
            this.bpmId = bpmId;
            return this;
        }

        public ProcessBuilder withProcessOrder(List<ProcessOrder> processOrder) {
            this.processOrder = processOrder;
            return this;
        }

        public ProcessBuilder withProcessSession(ProcessBaseSession processSession) {
            this.processSession = processSession;
            return this;
        }

        public ProcessBuilder withProcessSchedule(List<ProcessSchedule> processSchedule) {
            this.processSchedule = processSchedule;
            return this;
        }

        public Process build() {
            Process process = new Process();
            process.setProcessId(processId);
            process.setBpmId(bpmId);
            process.setProcessOrder(processOrder);
            process.setProcessSession(processSession);
            process.setProcessSchedule(processSchedule);
            return process;
        }
    }
}
