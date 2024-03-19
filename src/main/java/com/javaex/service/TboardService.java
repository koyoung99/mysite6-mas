package com.javaex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaex.dao.TboardDao;
import com.javaex.vo.TboardVo;

@Service
public class TboardService {

	@Autowired
	private TboardDao tboardDao;

	// 리스트(검색X, 페이징O)
	public Map<String, Object> exeList2(int crtPage) {
		System.out.println("TboardService.exeList2()");
		System.out.println(crtPage);

		//////////////////////////////////////////////////
		// 리스트가져오기
		//////////////////////////////////////////////////
		// 한페이지당 출력 글갯수
		int listCnt = 10;

		// crtPage
		crtPage = (crtPage > 0) ? crtPage : (crtPage = 1);
		/*
		 * if(crtPage > 0) { crtPage = crtPage; }else { crtPage = 1; }
		 */

		// startRowNo 구하기
		// 1->1 10, 2->11 20, 3->21,30
		// 1->0 10, 2->10 10, 3->20,10
		// (1-1)10 -> 0
		// (2-1)10 ->10
		// (crtPage-1)listCnt ->20
		int startRowNo = (crtPage - 1) * listCnt;

		// startRowNo, listCnt Map으로 묶는다
		Map<String, Integer> limitMap = new HashMap<String, Integer>();
		limitMap.put("startRowNo", startRowNo);
		limitMap.put("listCnt", listCnt);

		// dao에 전달해서 현재페이지의 리스트 10개를 받는다
		List<TboardVo> boardList = tboardDao.boardSelectList2(limitMap);

		//////////////////////////////////////////////////
		// 페이징 계산
		//////////////////////////////////////////////////
		// 페이지랑 버튼갯수
		int pageBtncount = 5;

		// 전ㅊ체 글갯수
		int totalCnt = tboardDao.selectTotalCnt();

		// 마지막 버튼 번호
		int endPageBtnNo = (int) Math.ceil(crtPage / (double) pageBtncount) * pageBtncount;

		// 시작 버튼 번호
		int startPageBtnNo = (endPageBtnNo - pageBtncount) + 1;

		// 다음 화살표 유무
		boolean next = false;
		if (listCnt * endPageBtnNo < totalCnt) {
			next = true;
		} else { // 다음화살표가 false일때 마지막 버튼번호 정확히 계산
			endPageBtnNo = (int) Math.ceil(totalCnt / (double) listCnt);
		}

		// 이전 화살표 유무
		boolean prev = false;
		if (startPageBtnNo != 1) {
			prev = true;
		}

		// 5개 map으로 묶어서 controller한테 보내고 리턴해준다
		Map<String, Object> pMap = new HashMap<String, Object>();
		pMap.put("boardList", boardList);
		pMap.put("startPageBtnNo", startPageBtnNo);
		pMap.put("endPageBtnNo", endPageBtnNo);
		pMap.put("prev", prev);
		pMap.put("next", next);

		System.out.println(pMap);

		return pMap;
	}

	// 리스트(검색X,페이징 X)
	public List<TboardVo> exeList() {
		System.out.println("TboardService.exeList()");

		List<TboardVo> boardList = tboardDao.boardSelectList();

		return boardList;

	}

}