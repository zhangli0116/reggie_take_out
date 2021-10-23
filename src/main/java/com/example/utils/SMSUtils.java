package com.example.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 短信发送工具类
 */
public class SMSUtils {

	public static void main(String[] args) {
		sendSMS("15071312410","20211018");
	}

	public static void sendSMS(String phone,String code){
		String key = "LTAI5tMZnBX8f27xipYTEG1s";
		String pass = "ALwO0IVOhidR7LCZhyu2dCt3Eq3v0F";
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", key, pass);//自己账号的AccessKey信息
		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		request.setSysMethod(MethodType.POST);
		request.setSysDomain("dysmsapi.aliyuncs.com");//短信服务的服务接入地址
		request.setSysVersion("2017-05-25");//API的版本号
		request.setSysAction("SendSms");//API的名称
		request.putQueryParameter("PhoneNumbers", phone);//接收短信的手机号码
		request.putQueryParameter("SignName", "传智健康");//短信签名名称
		request.putQueryParameter("TemplateCode", "SMS_195226089");//短信模板ID
		//request.putQueryParameter("TemplateParam", "{\"code\":\"1111\"}");//短信模板变量对应的实际值
		request.putQueryParameter("TemplateParam", "{\"code\":\""+ code +"\"}");//短信模板变量对应的实际值
		try {
			CommonResponse response = client.getCommonResponse(request);
			System.out.println(response.getData());
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 发送短信
	 * @param signName 签名
	 * @param templateCode 模板
	 * @param phoneNumbers 手机号
	 * @param param 参数
	 */
	/*public static void sendMessage(String signName, String templateCode,String phoneNumbers,String param){
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "", "");
		IAcsClient client = new DefaultAcsClient(profile);

		SendSmsRequest request = new SendSmsRequest();
		request.setSysRegionId("cn-hangzhou");
		request.setPhoneNumbers(phoneNumbers);
		request.setSignName(signName);
		request.setTemplateCode(templateCode);
		request.setTemplateParam("{\"code\":\""+param+"\"}");
		try {
			SendSmsResponse response = client.getAcsResponse(request);
			System.out.println("短信发送成功");
		}catch (ClientException e) {
			e.printStackTrace();
		}
	}*/

}
