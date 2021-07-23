package com.atguigu.meinian.test;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

// 使用七牛云 完成图片上传的测试
public class TestQiniu {

    /**
     * 文件上传
     */
//    @Test
//    public void test01(){
//        //构造一个带指定 Region 对象的配置类
//        Configuration cfg = new Configuration(Zone.zone2());
//        //...其他参数参考类注释
//        UploadManager uploadManager = new UploadManager(cfg);
//        //...生成上传凭证，然后准备上传
//        String accessKey = "vth5hVepvBGwDG7RWhO1O1orS9VNZYZ-iHNrkGf6";
//        String secretKey = "l9iJ87DwW4lMHV7407VW-f3__4klubhu0USWWEm5";
//        // 存储空间
//        String bucket = "java0323meinian";
//        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
//        String localFilePath = "C:\\Users\\buube\\Pictures\\Saved Pictures\\bg.jpg";
//        // 上传成功 地址：http://qvlfthpgh.hn-bkt.clouddn.com/FjWs9kdxsEzItJt_fNlNYFAh132y
//        //默认不指定key的情况下，以文件内容的hash值作为文件名
//        String key = null;
//        Auth auth = Auth.create(accessKey, secretKey);
//        String upToken = auth.uploadToken(bucket);
//        try {
//            Response response = uploadManager.put(localFilePath, key, upToken);
//            //解析上传成功的结果
//            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//            System.out.println(putRet.key);
//            System.out.println(putRet.hash);
//        } catch (Exception ex2) {
//            //ignore
//        }
//    }
//
//
//    /**
//     * 删除文件
//     */
//    @Test
//    public void test02(){
//        //构造一个带指定 Region 对象的配置类
//        Configuration cfg = new Configuration(Zone.zone2());
//        //...其他参数参考类注释
//        String accessKey = "vth5hVepvBGwDG7RWhO1O1orS9VNZYZ-iHNrkGf6";
//        String secretKey = "l9iJ87DwW4lMHV7407VW-f3__4klubhu0USWWEm5";
//        String bucket = "java0323meinian";
//        // 文件的名字
//        String key = "FjWs9kdxsEzItJt_fNlNYFAh132y";
//        Auth auth = Auth.create(accessKey, secretKey);
//        BucketManager bucketManager = new BucketManager(auth, cfg);
//        try {
//            bucketManager.delete(bucket, key);
//        } catch (Exception ex) {
//            //ignore
//        }
//    }
}
