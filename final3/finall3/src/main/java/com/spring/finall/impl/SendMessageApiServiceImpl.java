package com.spring.finall.impl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.finall.AuthVO;
import com.spring.finall.service.SendMessageApiService;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;


@Service("SendMessage")
public class SendMessageApiServiceImpl implements SendMessageApiService {

	@Autowired
	private SendMessageApiDAObatis sdao;
	
    
	
	public boolean sendMessage(HttpServletRequest request, String randomNumber,AuthVO vo) {
	
		
	 String api_key="NCSY4BTX5OIPQLWB";
		 
	String api_secret="MUKUA17KEOWARC69L8WJRTJ5VY3RWWKA";
		
	    //Message 가 coolsms 
	    // 거 뭐냐, 토큰 도 받아주고 apiurl 까지 다 만ㄷ릉어주고 연결해줌 까보니
	    // urlconnection하고 있음
		Message coolsms = new Message(api_key, api_secret);
		
		HashMap<String, String> params = new HashMap<String,String>();
		String mTel = request.getParameter("mTel"); 
		params.put("to", "01093130686"); 
		params.put("from", mTel);
		params.put("type", "SMS"); // 문자 타입
		params.put("text", "[Ka Ac Tueail] 본인확인 인증번호 (" + randomNumber + ") 를 입력하세요."); // 입력할 내용
		params.put("app_version", "JAVA SDK v2.2");
		
		try {
			JSONObject obj = (JSONObject) coolsms.send(params);
			System.out.println("obj->>>"+obj);
		
			
			
		//사용자가 인증번호를 받지 못하더라도, 실제 인증번호는 발송이되어 데이터베이스 들어가니 그걸 처리하는 if문임
		if(obj.get("error_list")==null) {
			insertAuthnum(vo);
			return false;
			
		} else if(obj.get("error_list").toString()!=null) {
			return true;
		}
		/*
		 * String error_list= obj.get("error_list").toString(); if(error_list!=null) {
		 * 
		 * 
		 * return true; }else { insertAuthnum(vo); return false; }
		 */
			
		} catch (CoolsmsException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCode());
		}
		//예상치 못한 에러
		return false;
		
		
	
		                         
		
	}
	

	
	
	
	
	
	
	public void insertAuthnum(AuthVO vo) {
		sdao.insertAuthnum(vo);
		
	}
	
	public boolean deleteAuthnum(AuthVO vo) {
		int check =sdao.deleteAuthnum(vo);
		
		if(check==0 || check>1) {
			return false;
		}else if(check==1) {
			return true;
		}
		return false;//이건 오류처리
		
	}


	
	

}
