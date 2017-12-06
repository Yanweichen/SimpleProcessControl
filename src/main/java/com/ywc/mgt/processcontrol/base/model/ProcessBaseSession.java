package com.ywc.mgt.processcontrol.base.model;

import com.ywc.mgt.processcontrol.base.service.impl.ProcessCRUDService;
import com.ywc.mgt.processcontrol.base.util.BeanFactoryUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 流程Session
 *
 * @author yanwe
 * createTime 2017-11-2017/11/6 17:22
 */
public class ProcessBaseSession implements Serializable {

    private transient String processId;

    private transient Map<String,Object> sessionMap = new HashMap<>();

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }

    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

    public void setAttribute(String name, Object value){
        sessionMap.put(name,value);
        BeanFactoryUtil.getBean(ProcessCRUDService.class).saveProcessSession(this);
    }

    public Object getAttribute(String name){
        return sessionMap.get(name);
    }

    public void removeAttribute(String name){
        sessionMap.remove(name);
    }
}
