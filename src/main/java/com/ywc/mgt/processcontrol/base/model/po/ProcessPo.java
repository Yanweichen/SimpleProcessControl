package com.ywc.mgt.processcontrol.base.model.po;

import javax.persistence.*;

@Table(name = "PROCESS")
public class ProcessPo{
    /**
     * 流程ID
     */
    @Id
    @Column(name = "PROCESS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String processId;

    /**
     * 流程图ID
     */
    @Column(name = "BPM_ID")
    private String bpmId;

    /**
     * 流程顺序
     */
    @Column(name = "PROCESS_ORDER")
    private String processOrder;

    /**
     * 流程进度
     */
    @Column(name = "PROCESS_SESSION")
    private String processSession;

    /**
     * 流程Session
     */
    @Column(name = "PROCESS_SCHEDULE")
    private String processSchedule;

    public String getProcessSession() {
        return processSession;
    }

    public void setProcessSession(String processSession) {
        this.processSession = processSession;
    }

    public String getProcessSchedule() {
        return processSchedule;
    }

    public void setProcessSchedule(String processSchedule) {
        this.processSchedule = processSchedule;
    }

    public String getProcessOrder() {
        return processOrder;
    }

    public void setProcessOrder(String processOrder) {
        this.processOrder = processOrder;
    }

    /**
     * 获取流程ID
     *
     * @return PROCESS_ID - 流程ID
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * 设置流程ID
     *
     * @param processId 流程ID
     */
    public void setProcessId(String processId) {
        this.processId = processId;
    }

    /**
     * 获取流程图ID
     *
     * @return BPM_ID - 流程图ID
     */
    public String getBpmId() {
        return bpmId;
    }

    /**
     * 设置流程图ID
     *
     * @param bpmId 流程图ID
     */
    public void setBpmId(String bpmId) {
        this.bpmId = bpmId;
    }

}