package com.spring.finall.admindto;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.spring.finall.admindomain.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "user_code")
public class ReponseReserveinfoDTO {

	public Long reserveinfo_num;
	public Integer onedayclass_num;
	public String merchant_uid;
	public String openday;
	public Integer user_code;
	public String application_day;
	public String reservestatus;
	public Integer contsrow;
	public Integer buttoncount;
	public Integer startbutton;
	public Integer endbutton;
	public String startDate;
	public String endDate;
	public Integer nextbtn;
	public UserEntity userEntity;
	
	public Long nextvalue;
	

}
