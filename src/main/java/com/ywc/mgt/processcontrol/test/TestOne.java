package com.ywc.mgt.processcontrol.test;

import com.ywc.mgt.processcontrol.base.annotation.FunctionHandlerClass;
import com.ywc.mgt.processcontrol.base.annotation.FunctionHandlerMethod;
import com.ywc.mgt.processcontrol.base.model.ProcessSession;
import com.ywc.mgt.processcontrol.base.service.impl.ProcessDispatcher;
import org.springframework.stereotype.Service;

/**
 * @author yanwe
 * createTime 2017-11-2017/11/6 19:31
 */
@Service
@FunctionHandlerClass(processName = "流程一",bmpIds = {HandlerGroup.GROUP_ONE})
public class TestOne {

    @FunctionHandlerMethod
    public void sayHello(String processId){
        System.out.println("流程一执行了！");
        ProcessSession processSession = ProcessDispatcher.getProcessSession(processId);
        System.out.println("进件ID:"+processSession.getAttribute("applicationId"));
        processSession.setAttribute("test1","test111111111");
        ProcessDispatcher.executeEnd(processId,true);
    }
}
