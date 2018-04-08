package com.jintao.example.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created with IDEA
 * author:jinjueYang
 * Date:2018/3/20
 * Time:15:54
 * 创建使用自定义的类加载器
 */
public class CompileClassLoader extends ClassLoader {

    /**
     * 读取一个文件的内容
     *
     * @param fileName 文件名
     * @return
     */
    private byte[] getBytes(String fileName) throws IOException {
        File file = new File(fileName);
        long len = file.length();
        byte[] raw = new byte[(int) len];
        try (FileInputStream fin = new FileInputStream(file)) {
            //一次读取class文件的二进制数据
            int r = fin.read(raw);
            if (r != len) {
                throw new IOException("无法读取全部文件: " + r + "!=" + len);
            }

            return raw;
        }

    }

    /**
     * 定义编译指定java文件的方法
     *
     * @param javaFile
     * @return
     */
    private boolean compile(String javaFile) throws IOException {
        System.out.println("正在编译 " + javaFile + "....");
        //调用系统的javac命令
        Process p = Runtime.getRuntime().exec("javac " + javaFile);
        try {
            //其它线程都等待这个线程完成
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //获取javac线程的退出值
        int ret = p.exitValue();
        return ret == 0;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clazz = null;
        //将包路径中的.替换成/
        String fileStub = name.replace(".","/");
        String javaFileName = fileStub + ".java";
        String classFileName = fileStub + ".class";
        File javaFile = new File(javaFileName);
        File classFile = new File(classFileName);
        //当指定源文件存在，且Class文件不存在，或者Java源文件的修改时间
        //比Class文件的修改时间更晚时，重新编译
        if(javaFile.exists() && (!classFile.exists() || javaFile.lastModified() > classFile.lastModified())){
            try {
                //如果编译失败，或者该class文件不存在
                if(!compile(javaFileName) || !classFile.exists()){
                    throw new ClassNotFoundException("ClassNotFoundException:" + javaFileName);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        //如果Class文件存在，系统负责将该文件转换成Class对象
        if(classFile.exists()){
            try {
                //将class文件的二进制数据读入数组
                byte[] raw = getBytes(classFileName);
                //调用ClassLoader的defineClass方法将二进制数据转换成Class对象
                clazz = defineClass(name,raw,0,raw.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //如果clazz为null,表明加载失败，则抛出异常
        if(clazz == null){
            throw new ClassNotFoundException(name);
        }

        return clazz;
    }
}
