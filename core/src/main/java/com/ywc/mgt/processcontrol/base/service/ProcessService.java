package com.ywc.mgt.processcontrol.base.service;

import com.ywc.mgt.processcontrol.base.model.ProcessHandler;
import com.ywc.mgt.processcontrol.base.model.ProcessOrder;

import java.util.List;

/**
 * 产品后续功能Service
 *
 * @author yanwe
 *         createTime 2017-02-2017/2/14 9:31
 */
public interface ProcessService {

    /**
     * 获得所有流程方法
     *
     * @return 所有流程方法
     */
    List<ProcessHandler> getAllProcessFunction(String bpmId);

    /**
     * 根据进件Id获得产品后续功能列表
     *
     * @param processId 流程ID
     * @return 后续功能列表
     */
    List<ProcessHandler> getCustomProcess(String processId);

    /**
     * 创建流程
     *
     * @param bmpId 流程图Id
     * @param processOrders 执行顺序
     */
    String createProcess(String bmpId, List<ProcessOrder> processOrders);

    /**
     * 清理异常进件
     */
    void cleanUnfinishedFunction(List<Class> classList);
}
