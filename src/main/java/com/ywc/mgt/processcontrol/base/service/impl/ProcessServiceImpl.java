package com.ywc.mgt.processcontrol.base.service.impl;

import com.ywc.mgt.processcontrol.base.annotation.FunctionHandlerClass;
import com.ywc.mgt.processcontrol.base.model.ProcessOrder;
import com.ywc.mgt.processcontrol.base.model.ProcessBaseSession;
import com.ywc.mgt.processcontrol.base.model.ProcessHandler;
import com.ywc.mgt.processcontrol.base.model.ProcessSchedule;
import com.ywc.mgt.processcontrol.base.model.Process;
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

import static com.ywc.mgt.processcontrol.base.enums.ProcessConstant.DEFAULT_NEED_CLEAN_VALUE;


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
    public List<ProcessHandler> getAllProcess(String bmpId){
        return BeanFactoryUtil.getBeansWithAnnotation(FunctionHandlerClass.class).entrySet().stream()
                .map(entry -> getProcessHandler(entry.getValue()))
                .filter(processFunction -> processFunction.getBmpIds().contains(bmpId))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProcessHandler> getCustomProcess(String processId) {
        Process process = processCRUDService.getProcess(processId);
        List<ProcessOrder> processOrders = processCRUDService.getProcess(processId).getProcessOrder();
        return sortHandler(process.getBpmId(), processOrders);
    }

    @Override
    @Transactional
    public String createProcess(String bmpId, List<ProcessOrder> processOrders){
        if (bmpId == null || processOrders == null || processOrders.isEmpty()){
            throw new RuntimeException("bmpId或流程顺序不能为空");
        }
        //获得主体所对应的方法
        List<ProcessHandler> processHandlers = sortHandler(bmpId,processOrders);
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
    public void cleanUnfinishedFunction() {
        List<ProcessHandler> needCleanProcessHandlers = BeanFactoryUtil
                .getBeansWithAnnotation(FunctionHandlerClass.class).values()
                .stream().map(this::getProcessHandler)
                .filter(processFunction -> Arrays.stream(processFunction.getNeedCleanStatus())
                        .noneMatch(status -> Objects.equals(DEFAULT_NEED_CLEAN_VALUE,status)))
                .collect(Collectors.toList());
        List<String> needCleanSubjects = processCRUDService.getNeedCleanSubjects(needCleanProcessHandlers
                .stream().flatMap(function -> Arrays.stream(function.getNeedCleanStatus()).boxed())
                .collect(Collectors.toList()));
        needCleanProcessHandlers.forEach(function -> needCleanSubjects
                .forEach(processId -> ProcessDispatcher.findProcessHandler(function.getProcessClass()
                            , processId, ProcessDispatcher::executeFunction)));
    }

    private ProcessHandler getProcessHandler(Object object){
        Object target = AopTargetUtils.getTarget(object).orElseThrow(() -> { logger.error("流程类不存在:"+object);
        return new RuntimeException("流程类不存在:"+object); });
        FunctionHandlerClass functionHandlerClass = target.getClass().getAnnotation(FunctionHandlerClass.class);
        return ProcessHandler.ProcessFunctionBuilder.aProcessFunction()
                .withNeedCleanStatus(functionHandlerClass.needCleanStatus())
                .withBmpIds(Stream.of(functionHandlerClass.bmpIds()).collect(Collectors.toList()))
                .withProcessHandler(target)
                .withProcessName(functionHandlerClass.processName()).build();
    }

    private List<ProcessHandler> sortHandler(String bmpId, List<ProcessOrder> processOrders) {
        //排序
        processOrders.sort(Comparator.comparing(ProcessOrder::getProcessOrder));
        List<ProcessHandler> allProcessHandler = getAllProcess(bmpId);
        List<ProcessHandler> sortRealProcessHandler = new ArrayList<>();
        for (ProcessOrder processOrder : processOrders) {
            for (ProcessHandler processHandler : allProcessHandler) {
                if (processHandler.getProcessClass()
                        .equalsIgnoreCase(processOrder.getProcessClass())){
                    sortRealProcessHandler.add(processHandler);
                }
            }
        }
        return sortRealProcessHandler;
    }

}
