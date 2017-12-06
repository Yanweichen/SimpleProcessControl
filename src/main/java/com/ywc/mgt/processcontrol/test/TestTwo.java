package com.ywc.mgt.processcontrol.test;

import com.ywc.mgt.processcontrol.base.annotation.FunctionHandlerClass;
import com.ywc.mgt.processcontrol.base.annotation.FunctionHandlerMethod;
import com.ywc.mgt.processcontrol.base.service.impl.ProcessDispatcher;
import org.springframework.stereotype.Service;

/**
 * @author yanwe
 * createTime 2017-11-2017/11/6 19:31
 */
@Service
@FunctionHandlerClass(processName = "流程二",bmpIds = {HandlerGroup.GROUP_ONE},needCleanStatus = {1})
public class TestTwo {

    @FunctionHandlerMethod
    public void sayHello(String processId){
        System.out.println("流程二执行了！");
        System.out.println("进件ID:"+ ProcessDispatcher.getProcessSession(processId)
                .getAttribute("applicationId"));
        ProcessDispatcher.executeEnd(processId,true);
    }
}
