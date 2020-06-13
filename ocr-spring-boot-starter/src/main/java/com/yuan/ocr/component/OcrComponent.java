package com.yuan.ocr.component;


import com.aliyuncs.AcsResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.RpcAcsRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeAccountPageRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeAccountPageResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeBankCardRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeBankCardResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeBusinessCardRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeBusinessCardResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeBusinessLicenseRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeBusinessLicenseResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeCharacterRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeCharacterResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeDriverLicenseRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeDriverLicenseResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeDrivingLicenseRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeDrivingLicenseResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeIdentityCardRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeIdentityCardResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeLicensePlateRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeLicensePlateResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeStampRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeStampResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeTaxiInvoiceRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeTaxiInvoiceResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeTrainTicketRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeTrainTicketResponse;
import com.aliyuncs.ocr.model.v20191230.RecognizeVINCodeRequest;
import com.aliyuncs.ocr.model.v20191230.RecognizeVINCodeResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.yuan.ocr.config.OcrProperties;
import com.yuan.ocr.enums.SideTypeEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 图片文字识别的组件
 * <p>
 * https://help.aliyun.com/document_detail/146692.html?spm=a2c4g.11186623.6.608.2bf3510fPkQZgD
 * https://api.aliyun.com/#/?product=ocr&version=2019-12-30&api=GetAsyncJobResult&tab=DOC&lang=JAVA
 */
public class OcrComponent {

    private final Log log = LogFactory.getLog(getClass());

    private OcrProperties ocrProperties;
    private IAcsClient client;

    public OcrComponent(OcrProperties ocrProperties) {
        this.ocrProperties = ocrProperties;
        client = new DefaultAcsClient(
                DefaultProfile.getProfile(
                        ocrProperties.getRegionId(),
                        ocrProperties.getAccessKeyId(),
                        ocrProperties.getAccessKeySecret()
                )
        );
    }

    private <R extends RpcAcsRequest<T>, T extends AcsResponse> T getAcsResponse(R req) {
        try {
            return client.getAcsResponse(req);
        } catch (Exception e) {
            log.error("Exception:e={}", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 身份证识别
     *
     */
    public RecognizeIdentityCardResponse getIdentityCardResponse(String url, SideTypeEnum side) {
        RecognizeIdentityCardRequest req = new RecognizeIdentityCardRequest();
        req.setImageURL(url);
        req.setSide(side.getValue());
        return getAcsResponse(req);

    }


    /**
     * 驾驶证识别
     *
     */
    public RecognizeDriverLicenseResponse getDriverLicenseResponse(String url, SideTypeEnum side) {
        RecognizeDriverLicenseRequest req = new RecognizeDriverLicenseRequest();
        req.setSide(side.getValue());
        req.setImageURL(url);
        return getAcsResponse(req);
    }

    /**
     * 行驶证识别
     *
     */
    public RecognizeDrivingLicenseResponse getDrivingLicenseResponse(String url, SideTypeEnum side) {
        RecognizeDrivingLicenseRequest req = new RecognizeDrivingLicenseRequest();
        req.setSide(side.getValue());
        req.setImageURL(url);
        return getAcsResponse(req);
    }

    /**
     * 车牌识别
     */
    public RecognizeLicensePlateResponse getRecognizeLicensePlate(String url) {
        RecognizeLicensePlateRequest req = new RecognizeLicensePlateRequest();
        req.setImageURL(url);
        return getAcsResponse(req);
    }

    /**
     * 银行卡识别
     *
     */
    public RecognizeBankCardResponse getRecognizeBankCard(String url) {
        RecognizeBankCardRequest req = new RecognizeBankCardRequest();
        req.setImageURL(url);
        return getAcsResponse(req);
    }

    /**
     * 火车票识别
     */
    public RecognizeTrainTicketResponse getRecognizeTrainTicket(String url) {
        RecognizeTrainTicketRequest req = new RecognizeTrainTicketRequest();
        req.setImageURL(url);
        return getAcsResponse(req);
    }

    /**
     * 名片识别
     *
     */
    public RecognizeBusinessCardResponse getRecognizeBusinessCard(String url) {
        RecognizeBusinessCardRequest req = new RecognizeBusinessCardRequest();
        req.setImageURL(url);
        return getAcsResponse(req);
    }

    /**
     * 出租车发票识别
     */
    public RecognizeTaxiInvoiceResponse getRecognizeTaxiInvoice(String url) {
        RecognizeTaxiInvoiceRequest req = new RecognizeTaxiInvoiceRequest();
        req.setImageURL(url);
        return getAcsResponse(req);
    }

    /**
     * VIN码识别
     *
     */
    public RecognizeVINCodeResponse getRecognizeVINCode(String url) {
        RecognizeVINCodeRequest req = new RecognizeVINCodeRequest();
        req.setImageURL(url);
        return getAcsResponse(req);
    }

    /**
     * 公章识别
     *
     */
    public RecognizeStampResponse getRecognizeStamp(String url) {
        RecognizeStampRequest req = new RecognizeStampRequest();
        req.setImageURL(url);
        return getAcsResponse(req);
    }

    /**
     * 通用识别
     */
    public RecognizeCharacterResponse getRecognizeCharacter(String url) {
        RecognizeCharacterRequest req = new RecognizeCharacterRequest();
        req.setMinHeight(10);
        req.setOutputProbability(true);
        req.setImageURL(url);
        return getAcsResponse(req);
    }

    /**
     * 营业执照识别
     *
     */
    public RecognizeBusinessLicenseResponse getRecognizeBusinessLicense(String url) {
        RecognizeBusinessLicenseRequest req = new RecognizeBusinessLicenseRequest();
        req.setImageURL(url);
        return getAcsResponse(req);
    }

    /**
     * 户口页识别
     *
     */
    public RecognizeAccountPageResponse getRecognizeAccountPage(String url) {
        RecognizeAccountPageRequest req = new RecognizeAccountPageRequest();
        req.setImageURL(url);
        return getAcsResponse(req);
    }

}
