### 简介

---

主要是对阿里云短信服务(sms),存储服务(oss),图片识别服务(ocr)的简单封装.方便使用

### oss:对象存储服务

主要封装了文件上传,下载,删除等几个方法,同时封装了一个获取临时访问政策对象的方法,用于前端直传时的需要

### sms:短信服务

主要封装了短信发送和查询发送结果2个方法

### ocr:图片识别服务

主要封装了对常用证件(身份证,行驶证,驾驶证,护照,营业执照,车牌等)的识别



### 使用方式

---

1. 依赖引入: 根据实际需要引入对应的依赖即可

   ```xml
     <dependency>
               <!--sms依赖-->
               <groupId>com.yuan</groupId>
               <artifactId>sms-spring-boot-starter</artifactId>
               <version>1.0-SNAPSHOT</version>
           </dependency>
           <dependency>
               <!--oss依赖-->
               <groupId>com.yuan</groupId>
               <artifactId>oss-spring-boot-starter</artifactId>
               <version>1.0-SNAPSHOT</version>
           </dependency>
           <dependency>
               <!--ocr依赖-->
               <groupId>com.yuan</groupId>
               <artifactId>ocr-spring-boot-starter</artifactId>
               <version>1.0-SNAPSHOT</version>
           </dependency>
   ```

   > 中央仓库中可能没有相关依赖,可自行打包

2. 配置:阿里云服务的相关配置

   ```yaml
   sms:
     access-key-id: 
     access-key-secret: 
     sign-name: 
   
   oss:
     access-key-id: 
     access-key-secret: 
     bucket: #bucket
     endpoint: #域名:如oss-cn-beijing.aliyuncs.com
   
   ocr:
     access-key-id: 
     access-key-secret: 
     region-id: #:如cn-shanghai
   ```

3. 使用

   ```java
   package com.yuan;
   
   import com.aliyuncs.ocr.model.v20191230.RecognizeLicensePlateResponse;
   import com.aliyuncs.utils.MapUtils;
   import com.google.gson.Gson;
   import com.yuan.ocr.component.OcrComponent;
   import com.yuan.oss.component.OssComponent;
   import com.yuan.oss.entity.OssPolicy;
   import com.yuan.sms.component.SmsComponent;
   import com.yuan.sms.entity.QueryParams;
   import com.yuan.sms.response.QueryDetailResponse;
   import com.yuan.sms.response.SmsResponse;
   import org.junit.Test;
   import org.junit.runner.RunWith;
   import org.springframework.beans.factory.annotation.Autowired;
   import org.springframework.boot.test.context.SpringBootTest;
   import org.springframework.test.context.junit4.SpringRunner;
   
   import java.io.File;
   import java.time.LocalDate;
   import java.util.Collections;
   import java.util.List;
   
   @SpringBootTest
   @RunWith(SpringRunner.class)
   public class DemoTest {
   
       private Gson gson  = new Gson();
   
       @Autowired
       private SmsComponent smsComponent;
       @Autowired
       private OssComponent ossComponent;
       @Autowired
       private OcrComponent ocrComponent;
   
       @Test
       public void testOcr() {
           String url = "https://viapi-demo.oss-cn-shanghai.aliyuncs.com/viapi-demo/images/RecognizeLicensePlate/licensePlate.jpg";//阿里云oss的文件路径(目前只支持上海节点的oss路径)
           RecognizeLicensePlateResponse recognizeLicensePlate = ocrComponent.getRecognizeLicensePlate(url);
           System.out.println(gson.toJson(recognizeLicensePlate));
       }
   
   
       @Test
       public void testSms() {
           String templateCode = ""; //短信模板code
           List<String> phoneList = Collections.singletonList("");//电话
           String params = MapUtils.getMapString(
                   Collections.singletonMap("code", "999999")
           );
   
           SmsResponse smsResponse = smsComponent.sendSms(templateCode, phoneList, params);
           System.out.println(gson.toJson(smsResponse));
       }
   
       @Test
       public void testQueryDetail() {
           QueryParams searchParams = new QueryParams("电话", LocalDate.of(2020, 6, 13),10,1);//查询参数: 电话,日期(只能查询最近30天的记录),分页参数
           QueryDetailResponse response = smsComponent.querySmsRecord(searchParams);
           System.out.println(gson.toJson(response));
       }
   
   
       @Test
       public void testUpload() {
           File file = new File("/Users/yuan/Downloads/xx.txt");
           String url = ossComponent.putFileToOss(file, "yuan/test");
           System.out.println(url);
       }
   
       @Test
       public void testDownLoad() throws Exception{
           ossComponent.download("test/test1588924000084hYamBepjSz.jpeg","/Users/yuan/Downloads/a.jpeg");
       }
   
       @Test
       public void testDelete() {
           ossComponent.deleteObject("test/NewFile.txt");
       }
       @Test
       public void testGetPolicy() {
           OssPolicy ossPolicy = ossComponent.createOssPolicy("test/NewFile.txt");
           System.out.println(gson.toJson(ossPolicy));
       }
   }
   
   ```

   

**注意事项: 目前ocr只支持上海的OSS节点,如果是其他节点需要转换URL,可参考 [生成URL](https://help.aliyun.com/document_detail/155645.html?spm=a2c4g.11186623.6.554.678249d8Cfhjbe) 来生成合法的URL**

