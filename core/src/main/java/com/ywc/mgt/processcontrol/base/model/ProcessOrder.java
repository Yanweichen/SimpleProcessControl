package com.ywc.mgt.processcontrol.base.model;

/**
 * 主体对应方法
 *
 * @author yanwe
 * createTime 2017-11-2017/11/6 15:59
 */
public class ProcessOrder {

    /**
     * 方法类
     */
    private Class processClass;

    /**
     * 方法顺序
     */
    private Integer processOrder;

    public ProcessOrder() {
    }

    public ProcessOrder(Class processClass, Integer processOrder) {
        this.processClass = processClass;
        this.processOrder = processOrder;
    }

    public String getProcessClass() {
        return processClass.getSimpleName();
    }

    public Class getProcessClazz() {
        return processClass;
    }

    public void setProcessClass(Class processClass) {
        this.processClass = processClass;
    }

    public Integer getProcessOrder() {
        return processOrder;
    }

    public void setProcessOrder(Integer processOrder) {
        this.processOrder = processOrder;
    }

}
