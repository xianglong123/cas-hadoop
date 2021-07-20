package com.cas.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author xiang_long
 * @version 1.0
 * @date 2021/7/20 4:15 下午
 * @desc
 */
public class HdfsClient {

    private FileSystem fs;

    private static final String path = "/com/cas";

    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        // 连接的集群nn地址
        URI uri = new URI("hdfs://hadoop100:8020");
        // 创建一个配置文件
        Configuration configuration = new Configuration();
        configuration.set("dfs.replication", "2");
        // 用户
        String user = "root";

        // 1、获取到了客户端对象
        fs = FileSystem.get(uri, configuration, user);
    }

    @After
    public void close() throws IOException {
        // 3、关闭资源
        fs.close();
    }

    /**
     * 创建文件夹
     * @throws IOException
     */
    @Test
    public void testMkdir() throws IOException {
        // 2、创建一个文件夹
        fs.mkdirs(new Path(path));
    }

    /**
     * 参数优先级
     * hdfs-default.xml => hdfs-site.xml => 在项目资源目录下的配置文件 => configuration
     *
     */
    @Test
    public void testPut() throws IOException {
        //是否删除数据源 delSrc, 是否允许覆盖 overwrite, 数据源 srcs, 上传位置 dst
        fs.copyFromLocalFile(false, true, new Path("/Users/xianglong/IdeaProjects/cas-hadoop/src/test/resources/static/hdfs.txt"), new Path(path));
    }

    /**
     * 测试下载
     */
    @Test
    public void testGet() throws IOException {
        // 源文件是否删除 delSrc, 源文件路径HDFS src, 目标地址路径 dst,是否开启本地校验 useRawLocalFileSystem
        fs.copyToLocalFile(false, new Path("hdfs://hadoop100/com/cas/hdfs.txt"), new Path("/Users/xianglong/IdeaProjects/cas-hadoop/src/test/resources/static/"), true);
    }

}
