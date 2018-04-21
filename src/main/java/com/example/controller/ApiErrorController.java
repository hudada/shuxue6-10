package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
@RequestMapping(value = "/api/error")
public class ApiErrorController {

	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private ErrorDao errorDao;
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public BaseBean<List<QuestionBean>> list(HttpServletRequest request) {
		String uid = request.getParameter("uid");
		List<ErrorBean> list = errorDao.findByUidList(Long.parseLong(uid));
		List<QuestionBean> bean = new ArrayList<>();
		for (ErrorBean errorBean : list) {
			bean.add(questionDao.findOne(errorBean.getQid()));
		}
		return ResultUtils.resultSucceed(bean);
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public BaseBean<ErrorBean> del(HttpServletRequest request) {
		String uid = request.getParameter("uid");
		String qid = request.getParameter("qid");
		errorDao.delete(errorDao.findByUid(Long.parseLong(uid), Long.parseLong(qid)));
		return ResultUtils.resultSucceed("");
	}
	

}
