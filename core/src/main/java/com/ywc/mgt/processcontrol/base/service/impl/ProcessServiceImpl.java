package com.ywc.mgt.processcontrol.base.service.impl;

import com.ywc.mgt.processcontrol.base.annotation.FunctionHandlerClass;
import com.ywc.mgt.processcontrol.base.model.Process;
import com.ywc.mgt.processcontrol.base.model.*;
import com.ywc.mgt.processcontrol.base.service.ProcessService;
import com.ywc.mgt.processcontrol.base.util.AopTargetUtils;
import com.ywc.mgt.processcontrol.base.util.BeanFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 产品后续功能Service实现类
 *
 * @author yanwe
 *         createTime 2017-02-2017/2/14 9:33
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);

    @Autowired
    private ProcessCRUDService processCRUDService;

    @Override
    public List<ProcessHandler> getAllProcessFunction(String bmpId){
        return BeanFactoryUtil.getBeansWithAnnotation(FunctionHandlerClass.class).entrySet().stream()
                .map(entry -> getProcessHandler(entry.getValue()))
                .filter(processFunction -> processFunction.getBmpIds().contains(bmpId))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessHandler> getCustomProcess(String processId) {
        List<ProcessSchedule> processSchedule = processCRUDService.getProcess(processId).getProcessSchedule();
        return processSchedule.stream()
                .map(schedule -> getProcessHandler(BeanFactoryUtil.getBean(schedule.getProcessClass())))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String createProcess(String bmpId, List<ProcessOrder> processOrders){
        if (bmpId == null || processOrders == null || processOrders.isEmpty()){
            throw new RuntimeException("bmpId或流程顺序不能为空");
        }
        //获得主体所对应的方法
        List<ProcessHandler> processHandlers = sortHandler(getAllProcessFunction(bmpId),processOrders);
        List<ProcessSchedule> processSchedule = processHandlers.stream()
                .map(function -> ProcessSchedule.ProcessScheduleBuilder.aProcessSchedule()
                        .withProcessClass(function.getProcessClass()).withProcessName(function.getProcessName())
                        .withExecute(false).build())
                .collect(Collectors.toList());
        //生成流程
        return processCRUDService.createProcess(Process.ProcessBuilder
                .aProcess()
                .withBpmId(bmpId)
                .withProcessOrder(processOrders)
                .withProcessSchedule(processSchedule)
                .withProcessSession(new ProcessBaseSession())
                .build());
    }

    @Override
    public void cleanUnfinishedFunction(List<Class> classList) {
        List<Process> allProcess = processCRUDService.getAllProcess();
        classList.forEach(function -> allProcess
                .forEach(process -> ProcessDispatcher.findProcessHandler(function.getSimpleName()
                            , process.getProcessId(), ProcessDispatcher::executeFunction)));
    }

    private ProcessHandler getProcessHandler(Object object){
        Object target = AopTargetUtils.getTarget(object).orElseThrow(() -> { logger.error("流程类不存在:"+object);
        return new RuntimeException("流程类不存在:"+object); });
        FunctionHandlerClass functionHandlerClass = target.getClass().getAnnotation(FunctionHandlerClass.class);
        return ProcessHandler.ProcessFunctionBuilder.aProcessFunction()
                .withBmpIds(Stream.of(functionHandlerClass.bmpIds()).collect(Collectors.toList()))
                .withProcessHandler(target)
                .withProcessName(functionHandlerClass.processName()).build();
    }

    private List<ProcessHandler> sortHandler(List<ProcessHandler> allProcessHandler, List<ProcessOrder> processOrders) {
        return processOrders.stream()
                //排序
                .sorted(Comparator.comparing(ProcessOrder::getProcessOrder))
                .map(processOrder -> allProcessHandler.stream()
                .filter(processHandler -> processHandler.getProcessClass()
                        .equalsIgnoreCase(processOrder.getProcessClass())).findFirst())
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
