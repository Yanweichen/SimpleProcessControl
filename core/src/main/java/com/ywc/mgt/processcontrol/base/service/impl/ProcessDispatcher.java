package com.ywc.mgt.processcontrol.base.service.impl;

import com.ywc.base.functioninterface.Try;
import com.ywc.mgt.processcontrol.base.annotation.FunctionHandlerMethod;
import com.ywc.mgt.processcontrol.base.model.ProcessBaseSession;
import com.ywc.mgt.processcontrol.base.model.ProcessHandler;
import com.ywc.mgt.processcontrol.base.model.ProcessSchedule;
import com.ywc.mgt.processcontrol.base.model.ProcessSession;
import com.ywc.mgt.processcontrol.base.service.ProcessService;
import com.ywc.mgt.processcontrol.base.util.AopTargetUtils;
import com.ywc.mgt.processcontrol.base.util.BeanFactoryUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * 方法链
 *
 * @author yanwe
 * createTime 2017-02-2017/2/14 14:08
 */
public class ProcessDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(ProcessDispatcher.class);

    private static ProcessService processService =
            BeanFactoryUtil.getBean(ProcessService.class);

    private static ProcessCRUDService processCRUDService =
            BeanFactoryUtil.getBean(ProcessCRUDService.class);

    public static void startProcess(String processId){
        //执行首个方法
        ProcessDispatcher.executeFunction(processId,
                ProcessDispatcher.getProcessFunctionList(processId).get(0).getProcessHandler());
    }

    /**
     * 是否有下一个未执行的节点
     *
     * @param processId 流程ID
     * @return 是/否
     */
    public static boolean isHaveNext(String processId) {
        return !getCurrentProcess(processId).isExecute();
    }

    /**
     * 查询流程是否有指定方法
     *
     * @param processId 流程id
     * @param classes 类
     * @return true/false
     */
    public static boolean isHaveFunction(String processId, Class... classes){
        List<ProcessHandler> functions = processService.getCustomProcess(processId);
        return Arrays.stream(classes).allMatch(clazz -> functions.stream()
                .anyMatch(function -> clazz.getSimpleName()
                        .equalsIgnoreCase(function.getProcessClass())));
    }

    /**
     * 方法执行结束
     *
     * @param processId 流程ID
     * @param goOn 是否继续流程
     */
    public static void executeEnd(String processId,boolean goOn) {
        saveCurrentProgress(processId);//保存当前进度
        if (goOn && isHaveNext(processId)){
            executeNextFunction(processId);
        } else if (goOn){
            processCRUDService.processEndHandle(processId);
        }
    }

    /**
     * 执行下一个方法
     *
     * @param processId 流程id
     */
    public static void doNextFunction(String processId) {
        boolean haveNext = isHaveNext(processId);
        if (haveNext) {
            executeNextFunction(processId);
        } else {
            processCRUDService.processEndHandle(processId);
            logger.info("流程:"+processId+"没有后续方法,执行结束！");
        }
    }

    /**
     * 重新执行当前方法
     *
     * @param processId 流程id
     */
    public static void redoCurrentFunction(String processId){
        executeCurrentFunction(processId);
    }

    /**
     * 获得Session
     *
     * @param processId 流程ID
     * @return session
     */
    public static ProcessSession getProcessSession(String processId){
        ProcessBaseSession processSession = processCRUDService.getProcess(processId).getProcessSession();
        processSession.setProcessId(processId);
        return new ProcessSession(processSession);
    }

    /**
     * 获得执行进度
     *
     * @param processId 流程ID
     * @return 执行进度
     */
    public static List<ProcessSchedule> getProcessSchedule(String processId) {
        return processCRUDService.getProcess(processId).getProcessSchedule();
    }

    /**
     * 获得下一个未执行的方法也就是当前进度所在位置（全部执行完毕则返回最后一步）
     *
     * @param processId 流程ID
     * @return 进度
     */
    public static ProcessSchedule getCurrentProcess(String processId){
        List<ProcessSchedule> processScheduleList = getProcessSchedule(processId);
        return processScheduleList.stream().filter(processSchedule -> !processSchedule.isExecute())
                .findFirst().orElse(processScheduleList.get(processScheduleList.size() -1));
    }

    /**
     * 储存当前进度
     *
     * @param processId 流程ID
     */
    private static void saveCurrentProgress(String processId) {
        ProcessSchedule currentProcess = getCurrentProcess(processId);
        saveProgress(processId,currentProcess.getProcessClass());
    }

    /**
     * 储存进度
     *
     * @param processId 流程id
     * @param processClass 需要储存进度的类名称
     */
    private static void saveProgress(String processId, String processClass) {
        List<ProcessSchedule> applicationProgress = getProcessSchedule(processId);
        applicationProgress.stream()
                .filter(productFunction -> processClass.equals(productFunction.getProcessClass()))
                .forEach(productFunction -> productFunction.setExecute(true));
        processCRUDService.saveProcessSchedule(processId ,applicationProgress);
    }

    /**
     * 根据名称查询执行者
     *
     * @param functionClassName 根据名称查询执行者
     * @param processId 流程id
     * @param biConsumer 结果处理方法
     */
    public static void findProcessHandler(String functionClassName, String processId
            , BiConsumer<String,Object> biConsumer){
        getProcessFunctionList(processId).stream()
                .filter(handler -> handler.getProcessClass().equals(functionClassName))
                .forEach(handler -> biConsumer.accept(processId,handler.getProcessHandler()));
    }

    /**
     * 执行方法
     *
     * @param processId   主体id
     * @param functionHandler 执行者
     */
    public static void executeFunction(String processId, Object functionHandler) {
        if(functionHandler == null){
            throw new RuntimeException("functionHandler为空!流程id："+processId);
        }
        Method[] methods = getTargetHandler(functionHandler).getClass().getMethods();
        Arrays.stream(methods)
                .filter(method -> method.isAnnotationPresent(FunctionHandlerMethod.class))
                .forEach(Try.of(method -> {method.invoke(functionHandler,processId);}));
    }

    /**
     * 重新执已经执行过的最后一个方法
     *
     * @param processId 流程id
     */
    private static void executeCurrentFunction(String processId) {
        List<ProcessSchedule> processSchedule = getProcessSchedule(processId);
        ProcessSchedule currentProcess = null;
        for (int i = 0; i < processSchedule.size(); i++) {
            if (!processSchedule.get(i).isExecute()){
                currentProcess = processSchedule.get(i==0 ? i : i-1);
            }
        }
        if (currentProcess == null){
            currentProcess = processSchedule.get(processSchedule.size() - 1);
        }
        findProcessHandler(currentProcess.getProcessClass(),processId
                ,(sub,functionHandler) -> executeFunction(processId,functionHandler));
    }

    /**
     * 执行下一个方法
     *
     * @param processId 流程id
     */
    private static void executeNextFunction(String processId) {
        ProcessSchedule currentProcessSchedule = getCurrentProcess(processId);
        if (!currentProcessSchedule.isExecute()){
            findProcessHandler(currentProcessSchedule.getProcessClass(),processId
                    ,(sub,functionHandler) -> executeFunction(processId,functionHandler));
        }
    }

    /**
     * 获得排序后的执行链,包含方法
     *
     * @param processId 流程id
     * @return 原始执行链(可能含有代理类)
     */
    private static List<ProcessHandler> getProcessFunctionList(String processId) {
        List<ProcessHandler> processHandlers =
                processService.getCustomProcess(processId);
        if(processHandlers.isEmpty()){
            RuntimeException exception = new RuntimeException("流程:" + processId + "没有后续方法");
            logger.error("流程:" + processId + "没有后续方法",exception);
            throw exception;
        }
        return processHandlers;
    }

    private static Object getTargetHandler(Object handler){
        return AopTargetUtils.getTarget(handler).orElseThrow(() -> new RuntimeException("未找到对应方法执行类"));
    }

}
