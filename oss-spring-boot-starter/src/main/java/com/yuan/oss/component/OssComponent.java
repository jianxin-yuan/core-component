package com.yuan.oss.component;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectRequest;
import com.yuan.oss.config.OssProperties;
import com.yuan.oss.entity.OssPolicy;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Date;

/**
 * oss组件
 */
public class OssComponent {

    private final Log logger = LogFactory.getLog(getClass());

    private static final Charset UTF8 = StandardCharsets.UTF_8;

    private OssProperties ossProperties;

    public OssComponent(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }


    /**
     * 获取一个ossClient
     *
     * @return
     */
    public OSS createOss() {
        return new OSSClientBuilder().build(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
    }

    /**
     * 创建一个临时策略，用于oss前端直传的使用,使用配置中默认的bucket
     *
     * @param dir
     * @return
     */
    public OssPolicy createOssPolicy(String dir) {
        return createOssPolicy(dir, ossProperties.getBucket());
    }

    /**
     * 创建一个临时策略，用于oss前端直传的使用,使用指定的bucket
     * <p>
     * ref: https://help.aliyun.com/document_detail/91868.html?spm=a2c4g.11186623.2.15.2b746e28Alo6ZI#concept-ahk-rfz-2fb
     *
     * @param dir
     * @param bucket
     * @return
     */
    public OssPolicy createOssPolicy(String dir, String bucket) {
        OSS oss = createOss();
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;

            String host = ossProperties.getHost(bucket);

            PolicyConditions policyConditions = new PolicyConditions();
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);
            String policy = oss.generatePostPolicy(new Date(expireEndTime), policyConditions);

            String signature = oss.calculatePostSignature(policy);
            policy = BinaryUtil.toBase64String(policy.getBytes(UTF8));
            return new OssPolicy(bucket, ossProperties.getAccessKeyId(), policy, signature, dir, host, expireEndTime);
        } finally {
            oss.shutdown();
        }
    }

    /**
     * 将文件上传至oss
     *
     * @param file 文件目录(虚拟目录)
     * @param path 文件
     * @return
     */
    public String putFileToOss(File file, String path) {
        return putFileToOss(file, ossProperties.getBucket(), path);
    }

    /**
     * 将文件上传至oss,使用指定bucket
     *
     * @param file   文件
     * @param path   文件目录
     * @param bucket
     * @return
     */
    public String putFileToOss(File file, String bucket, String path) {
        OSS oss = createOss();
        try {
            String fullFilePath = createFileKey(path, file.getName());
            PutObjectRequest objectRequest = new PutObjectRequest(ossProperties.getBucket(), fullFilePath, file);
            oss.putObject(objectRequest);
            return genericUrl(bucket, fullFilePath);
        } finally {
            oss.shutdown();
        }
    }

    /**
     * 将文件上传至oss并设置过期时间
     *
     * @param file
     * @param path
     * @param expiration
     * @return
     */
    public String putFileToOss(File file, String path, Duration expiration) {
        return putFileToOss(file, ossProperties.getBucket(), path, expiration);
    }

    /**
     * 将文件上传至oss并设置过期时间
     *
     * @param file
     * @param bucket
     * @param path
     * @param expiration
     * @return
     */
    public String putFileToOss(File file, String bucket, String path, Duration expiration) {
        OSS oss = createOss();
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setExpirationTime(new Date(System.currentTimeMillis() + expiration.toMillis()));
            String fullFilePath = createFileKey(path, file.getName());
            oss.putObject(bucket, fullFilePath, file, metadata);
            return genericUrl(bucket, fullFilePath);
        } finally {
            oss.shutdown();
        }
    }

    public String genericUrl(String bucket, String fullFilePath) {
        if (fullFilePath.startsWith("/")) {
            fullFilePath = fullFilePath.replaceFirst("/", "");
        }
        try {
            return String.format("https://%s.%s/%s", bucket, ossProperties.getEndpoint(), URLEncoder.encode(fullFilePath, UTF8.displayName()));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private String createFileKey(String path, String fileName) {
        if (path.startsWith("/")) {
            path = path.replaceFirst("/", "");
        }
        String fullPath = FilenameUtils.normalize(FilenameUtils.concat(path, fileName), true);
        logger.debug(path + "/" + fileName + "->" + fullPath);
        return fullPath;
    }

    /**
     * 删除一个oss对象
     *
     * @param path
     */
    public void deleteObject(String path) {
        deleteObject(path, ossProperties.getBucket());
    }

    /**
     * 删除一个oss对象（指定bucket）
     *
     * @param path 源文件路径: 不包含bucket和endpoint的全路径,如 aa/bb/c.txt
     * @param bucket
     */
    public void deleteObject(String path, String bucket) {
        OSS oss = createOss();
        try {
            oss.deleteObject(bucket, path);
        } finally {
            oss.shutdown();
        }
    }

    /**
     * 下载文件
     * @param sourceFile 源文件路径: 不包含bucket和endpoint的全路径,如 aa/bb/c.txt
     * @param targetFile 目标文件名
     * @throws Exception
     */
    public void download(String sourceFile, String targetFile) throws Exception {
        OSS oss = createOss();
        try {
            OSSObject ossObject = oss.getObject(ossProperties.getBucket(), sourceFile);
            InputStream content = ossObject.getObjectContent();
            if (content != null) {
                Path target = new File(targetFile).toPath();
                Files.copy(content, target);
                content.close();
            }
        } finally {
            oss.shutdown();
        }

    }
}
