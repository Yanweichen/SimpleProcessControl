package com.ywc.test;

import com.ywc.base.ResultTo;
import com.ywc.mgt.processcontrol.base.model.ProcessHandler;
import com.ywc.mgt.processcontrol.base.model.ProcessOrder;
import com.ywc.mgt.processcontrol.base.service.impl.ProcessDispatcher;
import com.ywc.mgt.processcontrol.base.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author yanwe
 * createTime 2017-11-2017/11/6 19:40
 */
@RestController
@RequestMapping("/api/process")
public class ProcessTestController {

    @Autowired
    private ProcessService processService;

    @RequestMapping("/create/{applicationId}")
    public ResultTo create(@PathVariable("applicationId") String applicationId){
        List<ProcessOrder> processOrders = new ArrayList<>();
        processOrders.add(new ProcessOrder(TestOne.class,0));
        processOrders.add(new ProcessOrder(TestTwo.class,2));
        processOrders.add(new ProcessOrder(TestThree.class,1));
        String processId = processService.createProcess(HandlerGroup.GROUP_ONE,processOrders);
        ProcessDispatcher.getProcessSession(processId).setAttribute("applicationId",applicationId);
        return new ResultTo().setData(processId);
    }

    @RequestMapping("/start/{processId}")
    public ResultTo start(@PathVariable("processId") String processId){
        ProcessDispatcher.startProcess(processId);
        return new ResultTo();
    }

    @RequestMapping("/all")
    public ResultTo getAll(){
        List<ProcessHandler> allProcessHandler = processService.getAllProcessFunction(HandlerGroup.GROUP_ONE);
        return new ResultTo().setData(allProcessHandler);
    }

    @RequestMapping("/doNext/{processId}")
    public ResultTo doNext(@PathVariable("processId")String processId){
        ProcessDispatcher.doNextFunction(processId);
        return new ResultTo();
    }

    @RequestMapping("/clean")
    public ResultTo clean(){
        processService.cleanUnfinishedFunction(Collections.singletonList(TestTwo.class));
        return new ResultTo();
    }

}
