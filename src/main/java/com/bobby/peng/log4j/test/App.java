package com.bobby.peng.log4j.test;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>2017/9/11</p>
 *
 * @author <a href="mailto:peng2035@163.com">彭天浩</a>
 * @version 1.0
 */
public class App {

    Logger log = Logger.getLogger(App.class);

    public void log(String str) {
        log.warn(str);
    }


    public static void main(String[] args) throws Exception {
        String str = txt2String(new File("src/main/resources/BIPlatform.log"));
        PropertyConfigurator.configure(ClassLoader.getSystemResource("log4j.properties"));

        App app = new App();
        ExecutorService pool = Executors.newFixedThreadPool(100);

        long startTime = System.currentTimeMillis() + 1000 * 600;
        while(System.currentTimeMillis() < startTime) {
            pool.execute(() -> app.log(str));
        }
    }


    public static String txt2String(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
