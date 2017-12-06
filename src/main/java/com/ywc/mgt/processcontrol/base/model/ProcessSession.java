package com.ywc.mgt.processcontrol.base.model;

import java.util.Map;

/**
 * 流程Session
 *
 * @author yanwe
 * createTime 2017-11-2017/11/9 19:43
 */
public class ProcessSession {

    private ProcessBaseSession processBaseSession;

    public ProcessSession(ProcessBaseSession processBaseSession) {
        this.processBaseSession = processBaseSession;
    }

    public Map<String, Object> getSessionMap() {
        return processBaseSession.getSessionMap();
    }

    public void setAttribute(String name, Object value){
        processBaseSession.setAttribute(name,value);
    }

    public Object getAttribute(String name){
        return processBaseSession.getAttribute(name);
    }

    public <T> T getAttribute(String name, Class<T> clazz){
        return (T) processBaseSession.getAttribute(name);
    }

    public void removeAttribute(String name){
        processBaseSession.removeAttribute(name);
    }
}
