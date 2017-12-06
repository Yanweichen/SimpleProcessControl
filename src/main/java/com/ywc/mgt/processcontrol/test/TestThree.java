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
@FunctionHandlerClass(processName = "流程三",bmpIds = {HandlerGroup.GROUP_ONE})
public class TestThree {

    @FunctionHandlerMethod
    public void sayHello(String processId){
        ProcessSession processSession = ProcessDispatcher.getProcessSession(processId);
        Object test1 = processSession.getAttribute("test1");
        System.out.println("流程三执行了！"+test1);
        ProcessDispatcher.executeEnd(processId,true);
        System.out.println("进件ID:"+processSession.getAttribute("applicationId"));
    }
}
