package com.ywc.mgt.processcontrol.base.model;

import java.util.List;

/**
 * @author yanwe
 * createTime 2017-11-2017/11/6 14:38
 */
public class ProcessHandler {

    private List<String> bmpIds;

    private String processName;

    private String processClass;

    private int [] needCleanStatus;

    private Object processHandler;

    public Object getProcessHandler() {
        return processHandler;
    }

    public void setProcessHandler(Object processHandler) {
        this.processHandler = processHandler;
    }

    public List<String> getBmpIds() {
        return bmpIds;
    }

    public void setBmpIds(List<String> bmpIds) {
        this.bmpIds = bmpIds;
    }

    public String getProcessClass() {
        if (processHandler != null){
            return processHandler.getClass().getSimpleName();
        } else {
            return null;
        }
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public int [] getNeedCleanStatus() {
        return needCleanStatus;
    }

    public void setNeedCleanStatus(int [] needCleanStatus) {
        this.needCleanStatus = needCleanStatus;
    }

    public static final class ProcessFunctionBuilder {
        private List<String> bmpIds;
        private String processName;
        private String processClass;
        private int [] needCleanStatus;
        private Object processHandler;

        private ProcessFunctionBuilder() {
        }

        public static ProcessFunctionBuilder aProcessFunction() {
            return new ProcessFunctionBuilder();
        }

        public ProcessFunctionBuilder withBmpIds(List<String> bmpId) {
            this.bmpIds = bmpId;
            return this;
        }

        public ProcessFunctionBuilder withProcessName(String processName) {
            this.processName = processName;
            return this;
        }

        public ProcessFunctionBuilder withNeedCleanStatus(int[] needCleanStatus) {
            this.needCleanStatus = needCleanStatus;
            return this;
        }

        public ProcessFunctionBuilder withProcessHandler(Object processHandler) {
            this.processHandler = processHandler;
            return this;
        }

        public ProcessHandler build() {
            ProcessHandler processHandler = new ProcessHandler();
            processHandler.setBmpIds(bmpIds);
            processHandler.setProcessName(processName);
            processHandler.setNeedCleanStatus(needCleanStatus);
            processHandler.setProcessHandler(this.processHandler);
            return processHandler;
        }
    }
}
