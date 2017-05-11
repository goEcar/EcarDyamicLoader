package com.ecar.epark.edroidloaer.interfaces;

/**
 * 定义lib 对外提供的接口
 * 1.加载jar包 ：1.1 下载jar 1.2 classloader 加载 jar
 * 2.提供api调用方式
 */
public interface IPluginLoader {

    boolean initLoaderJar(String jarVersion,String downUrl);
}
