# SimpleProcessControl
##简易流程控制

####自定义方法链执行,超级简易版Activiti

例如 TestOne TestTwo TestThree 三个Service

``` java
   List<ProcessOrder> processOrders = new ArrayList<>();
    processOrders.add(new ProcessOrder(TestOne.class,0));
    processOrders.add(new ProcessOrder(TestTwo.class,2));
    processOrders.add(new ProcessOrder(TestThree.class,1));
    //创建一个流程
    String processId = processService.createProcess(HandlerGroup.GROUP_ONE,processOrders);
    //启动流程
    ProcessDispatcher.startProcess(processId);
```
>
 上述流程启动则会自动调用TestOne TestTwo TestThree中指定的方法
 
 
####运行方法
 
 Application类中方法直接运行Main方法

