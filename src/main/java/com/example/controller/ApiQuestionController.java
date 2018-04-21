package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bean.BaseBean;
import com.example.bean.ErrorBean;
import com.example.bean.QuestionBean;
import com.example.bean.UserBean;
import com.example.dao.ErrorDao;
import com.example.dao.QuestionDao;
import com.example.dao.UserDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/question")
public class ApiQuestionController {

	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private ErrorDao errorDao;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public BaseBean<QuestionBean> list(HttpServletRequest request) {
		return rankOne(request);
	}
	
	@RequestMapping(value = "/listError", method = RequestMethod.GET)
	public BaseBean<QuestionBean> listError(HttpServletRequest request) {
		String qid = request.getParameter("qid");
		String uid = request.getParameter("uid");
		ErrorBean bean = new ErrorBean();
		bean.setUid(Long.parseLong(uid));
		bean.setQid(Long.parseLong(qid));
		errorDao.save(bean);
		return rankOne(request);
	}
	
	@RequestMapping(value = "/listRight", method = RequestMethod.GET)
	public BaseBean<QuestionBean> listRight(HttpServletRequest request) {
		String qid = request.getParameter("qid");
		String uid = request.getParameter("uid");
		ErrorBean bean = errorDao.findByUid(Long.parseLong(uid), 
				Long.parseLong(qid));
		if (bean != null) {
			errorDao.delete(bean);
		}
		return rankOne(request);
	}
	
	private BaseBean<QuestionBean> rankOne(HttpServletRequest request){
		String type = request.getParameter("type");
		List<QuestionBean> list = questionDao.findByType(Integer.parseInt(type));
		int count = list.size();
		if (count > 0) {
			Random random = new Random();
			int select = random.nextInt(count);
			return ResultUtils.resultSucceed(list.get(select));
		}else {
			return ResultUtils.resultSucceed("");
		}
	}

}
