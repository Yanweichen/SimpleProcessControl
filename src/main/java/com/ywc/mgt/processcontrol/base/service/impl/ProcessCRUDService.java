package com.ywc.mgt.processcontrol.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ywc.mgt.processcontrol.base.model.ProcessOrder;
import com.ywc.mgt.processcontrol.base.model.ProcessBaseSession;
import com.ywc.mgt.processcontrol.base.model.ProcessSchedule;
import com.ywc.mgt.processcontrol.base.dao.ProcessMapper;
import com.ywc.mgt.processcontrol.base.model.Process;
import com.ywc.mgt.processcontrol.base.model.po.ProcessPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 流程CRUD Service
 * @author yanwe
 * createTime 2017-11-2017/11/6 18:49
 */
public abstract class ProcessCRUDService {

    @Autowired
    private ProcessMapper processMapper;

    /**
     * 保存流程对象
     *
     * @param process 流程
     * @return 流程ID
     */
    public String createProcess(Process process) {
        ProcessPo processPo = new ProcessPo();
        processPo.setBpmId(process.getBpmId());
        processPo.setProcessOrder(JSON.toJSONString(process.getProcessOrder()));
        processPo.setProcessSchedule(JSON.toJSONString(process.getProcessSchedule()));
        processPo.setProcessSession(getSessionStr(process.getProcessSession()));
        processMapper.insert(processPo);
        return processPo.getProcessId();
    }

    /**
     * 获得流程对象
     *
     * @param processId 流程ID
     * @return 流程与主体
     */
    public Process getProcess(String processId) {
        ProcessPo processPo = processMapper.selectByPrimaryKey(processId);
        Process process = new Process();
        process.setProcessId(processPo.getProcessId());
        process.setBpmId(processPo.getBpmId());
        process.setProcessOrder(getProcessOrders(processPo));
        process.setProcessSession(getProcessSession(processPo.getProcessSession()));
        process.setProcessSchedule(JSON.parseArray(processPo.getProcessSchedule(),ProcessSchedule.class));
        return process;
    }

    /**
     * 持久化流程Session
     *
     * @param processSession 流程Session
     */
    public void saveProcessSession(ProcessBaseSession processSession) {
        ProcessPo processPo = new ProcessPo();
        processPo.setProcessId(processSession.getProcessId());
        processPo.setProcessSession(getSessionStr(processSession));
        processMapper.updateByPrimaryKeySelective(processPo);
    }

    /**
     * 保存流程进度
     *
     * @param processId 流程id
     * @param processScheduleList 流程进度
     */
    @Transactional
    public void saveProcessSchedule(String processId ,List<ProcessSchedule> processScheduleList) {
        ProcessPo processPo = new ProcessPo();
        processPo.setProcessId(processId);
        processPo.setProcessSchedule(JSON.toJSONString(processScheduleList));
        processMapper.updateByPrimaryKeySelective(processPo);
    }

    /**
     * 流程结束对应操作
     */
    public abstract void processEndHandle(String processId);

    /**
     * 根据配置的需要清理的状态返回主体id列表
     *
     * @param status 状态列表
     * @return 主体id列表
     */
    public abstract List<String> getNeedCleanSubjects(List<Integer> status);

    private List<ProcessOrder> getProcessOrders(ProcessPo processPo) {
        JSONArray jsonArray = JSON.parseArray(processPo.getProcessOrder());
        List<ProcessOrder> processOrders = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            ProcessOrder processOrder = new ProcessOrder();
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            processOrder.setProcessOrder(jsonObject.getInteger("processOrder"));
            try {
                processOrder.setProcessClass(Class.forName(jsonObject.getString("processClazz")));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            processOrders.add(processOrder);
        }
        return processOrders;
    }

    private ProcessBaseSession getProcessSession(String processSession) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(processSession,ProcessBaseSession.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getSessionStr(ProcessBaseSession processBaseSession){
        try {
            return new ObjectMapper().writeValueAsString(processBaseSession);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
