package com.ywc.mgt.processcontrol.base.model;

import java.io.Serializable;

/**
 * 流程进度
 *
 * @author yanwe
 *         createTime 2017-02-2017/2/15 16:48
 */
public class ProcessSchedule implements Serializable{

    /**
     * 方法名称
     */
    private String processName;

    /**
     * 方法类名
     */
    private String processClass;

    /**
     * 是否已经执行
     */
    private boolean execute;

    public String getProcessClass() {
        return processClass;
    }

    public void setProcessClass(String processClass) {
        this.processClass = processClass;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }


    public static final class ProcessScheduleBuilder {
        private String processName;
        private String processClass;
        private boolean execute;

        private ProcessScheduleBuilder() {
        }

        public static ProcessScheduleBuilder aProcessSchedule() {
            return new ProcessScheduleBuilder();
        }

        public ProcessScheduleBuilder withProcessName(String processName) {
            this.processName = processName;
            return this;
        }

        public ProcessScheduleBuilder withProcessClass(String processClass) {
            this.processClass = processClass;
            return this;
        }

        public ProcessScheduleBuilder withExecute(boolean execute) {
            this.execute = execute;
            return this;
        }

        public ProcessSchedule build() {
            ProcessSchedule processSchedule = new ProcessSchedule();
            processSchedule.setProcessName(processName);
            processSchedule.setProcessClass(processClass);
            processSchedule.setExecute(execute);
            return processSchedule;
        }
    }
}
