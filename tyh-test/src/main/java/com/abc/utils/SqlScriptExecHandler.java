package com.abc.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Description: SqlScriptExecUtils
 * @Author: 青衣醉
 * @Date: 2022/12/21 3:14 下午
 */
@Component
@Slf4j
public class SqlScriptExecHandler {

    @Value("${sql.script.path}")
    private  String path;

    @Autowired
    private DataSource dataSource;

    public ExecutorService executor = null;

    private SqlScriptExecHandler(){
        this.executor = Executors.newFixedThreadPool(4,new ThreadFactory () {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "执行SQL脚本：");
                thread.setDaemon(true);

                return thread;
            }
        });
    }
    public  void runScript(String... fileNames) {
        this.runScriptRet (fileNames);

    }
    public  Future<?> runScriptRet(String... fileNames) {

        List<File> files = getFiles (path).stream ()
                .filter (file -> Arrays.stream (fileNames)
                        .filter (fileName->fileName.endsWith (".sql"))
                        .anyMatch (fileName -> file.getName ().equals (fileName)))
                .sorted ()
                .collect (Collectors.toList ());
        System.out.println ("44444");
        System.getProperties ();
        return executor.submit (new ExecTask (files));

    }
    //脚本执行任务
    class ExecTask implements Runnable {
        List<File> files;
        public ExecTask(List<File> files){
            this.files=files;
        }
        @SneakyThrows
        @Override
        public void run() {
            Connection connection = null;
            try {
                connection = dataSource.getConnection ();
                ScriptRunner scriptRunner = getScriptRunner (connection);
                for (File file:files){
                    InputStream inputStream = new FileInputStream (file);
                    InputStreamReader reader = new InputStreamReader (inputStream,"GBK");
                    scriptRunner.runScript(reader);
                }
                scriptRunner.closeConnection();
                connection.close();
                Thread.sleep (3000);
                System.out.println ("55555");
            } catch (Exception throwables) {
                throw new Exception("ff");
            }

        }
    }

    private ScriptRunner getScriptRunner(Connection connection) {
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        scriptRunner.setEscapeProcessing(false);
        scriptRunner.setRemoveCRs(true);
        //逐行执行
        scriptRunner.setSendFullScript(false);
        //支持报错终止
        scriptRunner.setStopOnError (true);
        //支持自动提交
        scriptRunner.setAutoCommit(true);
        //支持长语句换行
        scriptRunner.setFullLineDelimiter(false);
        //每条命令间的分隔符
        scriptRunner.setDelimiter("$$");
        return scriptRunner;
    }


    public  List<File> getFiles(String path) {
        return Arrays.stream ((new File(path)).listFiles ())
                .filter (temp->temp.isFile ())
                .collect (Collectors.toList ());
    }
}
