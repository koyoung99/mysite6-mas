package com.javaex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.GuestDao;
import com.javaex.vo.GuestVo;

@Service
public class GuestService {

	@Autowired
	private GuestDao guestDao;

	// 리스트
	public List<GuestVo> exeList() {

		List<GuestVo> guestList = guestDao.guestSelect();

		return guestList;
	}

	// 등록
	public int exeInsert(GuestVo guestVo) {

		int count = guestDao.guestInsert(guestVo);

		return count;

	}

	// 삭제
	public int exeDelete(GuestVo guestVo) {
		System.out.println("exeDelete");

		int count = guestDao.guestDelete(guestVo);

		return count;
	}

	// ajax등록 저장
	public GuestVo exeAddandGuest(GuestVo guestVo) {
		System.out.println("GuestbookService.exeAddandGuest()");

		// 저장
		guestDao.insertSelectKey(guestVo);

		// 1명데이터 데이터 가져오기
		GuestVo gVo = guestDao.guestSelectOne(guestVo.getNo());
		return gVo;
	}
}
