package com.mydemo.toolslist.sms;

/**
 * @author: Nixon
 * @ProjectName: ToolsList
 * @Package: com.mydemo.toolslist.sms
 * @ClassName: SmsMessageInfo
 * @CreateDate: 2020/8/25 17:30
 * @Description: 本类作用描述：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class SmsMessageInfo {
    private String messageBody;
    private String originatingAddress;
    private String displayMessageBody;
    private String displayOriginatingAddress;
    private String timestampMillis;
    private String emailBody;
    private String emailFrom;
    private String pseudoSubject;
    private String serviceCenterAddress;

    @Override
    public String toString() {
        return "SmsMessageInfo{" +
                "messageBody='" + messageBody + '\'' +
                ", originatingAddress='" + originatingAddress + '\'' +
                ", displayMessageBody='" + displayMessageBody + '\'' +
                ", displayOriginatingAddress='" + displayOriginatingAddress + '\'' +
                ", timestampMillis='" + timestampMillis + '\'' +
                ", emailBody='" + emailBody + '\'' +
                ", emailFrom='" + emailFrom + '\'' +
                ", pseudoSubject='" + pseudoSubject + '\'' +
                ", serviceCenterAddress='" + serviceCenterAddress + '\'' +
                '}';
    }

    public SmsMessageInfo(String messageBody, String originatingAddress, String displayMessageBody, String displayOriginatingAddress, String timestampMillis, String emailBody, String emailFrom, String pseudoSubject, String serviceCenterAddress) {
        this.messageBody = messageBody;
        this.originatingAddress = originatingAddress;
        this.displayMessageBody = displayMessageBody;
        this.displayOriginatingAddress = displayOriginatingAddress;
        this.timestampMillis = timestampMillis;
        this.emailBody = emailBody;
        this.emailFrom = emailFrom;
        this.pseudoSubject = pseudoSubject;
        this.serviceCenterAddress = serviceCenterAddress;
    }

    public String getMessageBody() {
        return messageBody == null ? "" : messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getOriginatingAddress() {
        return originatingAddress == null ? "" : originatingAddress;
    }

    public void setOriginatingAddress(String originatingAddress) {
        this.originatingAddress = originatingAddress;
    }

    public String getDisplayMessageBody() {
        return displayMessageBody == null ? "" : displayMessageBody;
    }

    public void setDisplayMessageBody(String displayMessageBody) {
        this.displayMessageBody = displayMessageBody;
    }

    public String getDisplayOriginatingAddress() {
        return displayOriginatingAddress == null ? "" : displayOriginatingAddress;
    }

    public void setDisplayOriginatingAddress(String displayOriginatingAddress) {
        this.displayOriginatingAddress = displayOriginatingAddress;
    }

    public String getTimestampMillis() {
        return timestampMillis == null ? "" : timestampMillis;
    }

    public void setTimestampMillis(String timestampMillis) {
        this.timestampMillis = timestampMillis;
    }

    public String getEmailBody() {
        return emailBody == null ? "" : emailBody;
    }

    public void setEmailBody(String emailBody) {
        this.emailBody = emailBody;
    }

    public String getEmailFrom() {
        return emailFrom == null ? "" : emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getPseudoSubject() {
        return pseudoSubject == null ? "" : pseudoSubject;
    }

    public void setPseudoSubject(String pseudoSubject) {
        this.pseudoSubject = pseudoSubject;
    }

    public String getServiceCenterAddress() {
        return serviceCenterAddress == null ? "" : serviceCenterAddress;
    }

    public void setServiceCenterAddress(String serviceCenterAddress) {
        this.serviceCenterAddress = serviceCenterAddress;
    }
}
