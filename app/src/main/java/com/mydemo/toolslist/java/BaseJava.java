package com.mydemo.toolslist.java;

/**
 * @author: Nixon
 * @ProjectName: ToolsList
 * @Package: com.mydemo.toolslist
 * @ClassName: BaseJava
 * @CreateDate: 2021/2/10 17:04
 * @Description: 本类作用描述：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class BaseJava {
    public static void main(String[] args) {
        String sourceData = "{\"password\":\"qwe123\",\"username\":\"95038066\"}";
        String apiKey = "B7J2pqOh9UCkWup1";
        String encryptParamsString = AESUtils.encrypt(sourceData, apiKey);
        System.out.println("encryptParamsString===" + encryptParamsString);
    }







}
