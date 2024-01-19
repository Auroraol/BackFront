package com.example.flowable.controller;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * TestFlowable
 *
 * @Author
 * @Date: 2021/10/17 23:35
 * @Version 1.0
 */
@Slf4j
public class TestFlowable {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private org.flowable.engine.TaskService taskService;

    @Autowired
    private org.flowable.engine.IdentityService identityService;

    public void createDeploymentZip() {

        /*
         * @Date: 2021/10/17 23:38
         * Step 1: 部署xml（压缩到zip形式，直接xml需要配置相对路径，麻烦，暂不用）
         */
        try {
            File zipTemp = new File("f:/leave_approval.bpmn20.zip");
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipTemp));
            Deployment deployment = repositoryService
                    .createDeployment()
                    .addZipInputStream(zipInputStream)
                    .deploy();
            log.info("部署成功:{}", deployment.getId());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*
         * @Date: 2021/10/17 23:40
         * Step 2: 查询部署的流程定义
         */
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leave_approval").list();
        List<ProcessDefinition> pages = repositoryService.createProcessDefinitionQuery().processDefinitionKey("leave_approval").listPage(1, 30);

        /*
         * @Date: 2021/10/17 23:40
         * Step 3: 启动流程，创建实例
         */
        String processDefinitionKey = "leave_approval";//流程定义的key,对应请假的流程图
        String businessKey = "schoolleave";//业务代码，根据自己的业务用
        Map<String, Object> variablesDefinition = new HashMap<>();//流程变量，可以自定义扩充
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variablesDefinition);
        log.info("启动成功:{}", processInstance.getId());

        /*
         * @Date: 2021/10/17 23:40
         * Step 4: 查询指定流程所有启动的实例列表
         * 列表，或 分页 删除
         */
        List<Execution> executions = runtimeService.createExecutionQuery().processDefinitionKey("leave_approval").list();
        List<Execution> executionPages = runtimeService.createExecutionQuery().processDefinitionKey("leave_approval").listPage(1, 30);
//        runtimeService.deleteProcessInstance(processInstanceId, deleteReason); //删除实例

        /*
         * @Date: 2021/10/17 23:40
         * Step 5: 学生查询可以操作的任务,并完成任务
         */
        String candidateGroup = "stu_group"; //候选组 xml文件里面的 flowable:candidateGroups="stu_group"
        List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup(candidateGroup).orderByTaskCreateTime().desc().list();
        for (Task task : taskList) {
            // 申领任务
            taskService.claim(task.getId(), "my");
            // 完成
            taskService.complete(task.getId());
        }

        /*
         * @Date: 2021/10/17 23:40
         * Step 6: 老师查询可以操作的任务,并完成任务
         */
        String candidateGroupTe = "te_group"; //候选组 xml文件里面的 flowable:candidateGroups="te_group"
        List<Task> taskListTe = taskService.createTaskQuery().taskCandidateGroup(candidateGroupTe).orderByTaskCreateTime().desc().list();
        for (Task task : taskListTe) {
            // 申领任务
            taskService.claim(task.getId(), "myte");
            // 完成
            Map<String, Object> variables = new HashMap<>();
            variables.put("command", "agree"); //携带变量，用于网关流程的条件判定，这里的条件是同意
            taskService.complete(task.getId(), variables);
        }

        /*
         * @Date: 2021/10/18 0:17
         * Step 7: 历史查询，因为一旦流程执行完毕，活动的数据都会被清空，上面查询的接口都查不到数据，但是提供历史查询接口
         */
        // 历史流程实例
        List<HistoricProcessInstance> historicProcessList = historyService.createHistoricProcessInstanceQuery().processDefinitionKey("leave_approval").list();
        // 历史任务
        List<HistoricTaskInstance> historicTaskList = historyService.createHistoricTaskInstanceQuery().processDefinitionKey("leave_approval").list();
        // 实例历史变量 , 任务历史变量
        // historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId);
        // historyService.createHistoricVariableInstanceQuery().taskId(taskId);

        // *****************************************************分隔符********************************************************************
        // *****************************************************分隔符********************************************************************
        // 可能还需要的API
        // 移动任务，人为跳转任务
        // runtimeService.createChangeActivityStateBuilder().processInstanceId(processInstanceId)
        //       .moveActivityIdTo(currentActivityTaskId, newActivityTaskId).changeState();

        // 如果在数据库配置了分组和用户，还会用到
        List<User> users = identityService.createUserQuery().list();    //用户查询，用户id对应xml 里面配置的用户
        List<Group> groups = identityService.createGroupQuery().list(); //分组查询，分组id对应xml 里面配置的分组 如 stu_group，te_group 在表里是id的值

        // 另外，每个查询后面都可以拼条件，内置恁多查询，包括模糊查询，大小比较都有
    }
}