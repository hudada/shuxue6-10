package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.bean.AdminBean;
import com.example.bean.BaseBean;
import com.example.bean.QuestionBean;
import com.example.bean.UserBean;
import com.example.dao.AdminDao;
import com.example.dao.QuestionDao;
import com.example.dao.UserDao;
import com.example.utils.ResultUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.spring.web.json.Json;

import com.example.WebSecurityConfig;

@Controller
public class PageLoginController {

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private QuestionDao questionDao;


	// 返回登录页面
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(ModelMap map) {
		map.addAttribute("title","后台管理");
		return "newlogin";
	}

	// 登录接口
	@RequestMapping(value = "/dologin", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseBean<String> userLogin(@RequestBody AdminBean adminBean, HttpSession session) {
		AdminBean admin = adminDao.findByNameAndPwd(adminBean.getUserName(), adminBean.getPwd());
		if (admin != null) {
			session.setAttribute(WebSecurityConfig.SESSION_KEY, adminBean);
			return ResultUtils.resultSucceed("登陆成功");
		} else {
			return ResultUtils.resultError("账号或密码错误");
		}
	}

	// 退出登录接口
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String loginOut(HttpSession session) {
		session.removeAttribute(WebSecurityConfig.SESSION_KEY);
		return "redirect:/login";
	}

	private ModelMap getPub(ModelMap map, HttpSession session,int position) {
		AdminBean admin = (AdminBean) session.getAttribute(WebSecurityConfig.SESSION_KEY);
		map.addAttribute("name", admin.getUserName());
		map.addAttribute("title","企业后台管理");
		map.addAttribute("left",""
				+ "<li>"
					+ "<a href=\"#\">用户</a>"
				+ "</li>"
				+ "<li "+isActive(1,position)+">"
					+ "<a href=\"/userManager\">"
					+ "<i class=\"icon-chevron-right\"></i>用户列表</a>"
				+ "</li>"
				+ "<li>"
					+ "<a href=\"#\">题目</a>"	
				+ "</li>"
				+ "<li "+isActive(2,position)+">"
					+ "<a href=\"/questionAdd\">"
					+ "<i class=\"icon-chevron-right\"></i>新增题目</a>"
				+ "</li>"
				+ "<li "+isActive(6,position)+">"
					+ "<a href=\"/questionManager6\">"
					+ "<i class=\"icon-chevron-right\"></i>6岁题目列表</a>"
				+ "</li>"
				+ "<li "+isActive(7,position)+">"
					+ "<a href=\"/questionManager7\">"
					+ "<i class=\"icon-chevron-right\"></i>7岁题目列表</a>"
				+ "</li>"
				+ "<li "+isActive(8,position)+">"
					+ "<a href=\"/questionManager8\">"
					+ "<i class=\"icon-chevron-right\"></i>8岁题目列表</a>"
				+ "</li>"
				+ "<li "+isActive(9,position)+">"
					+ "<a href=\"/questionManager9\">"
					+ "<i class=\"icon-chevron-right\"></i>9岁题目列表</a>"
				+ "</li>"
				+ "<li "+isActive(10,position)+">"
					+ "<a href=\"/questionManager10\">"
					+ "<i class=\"icon-chevron-right\"></i>10岁题目列表</a>"
				+ "</li>");
		return map;
	}

	private String isActive(int curr,int position) {
		return position==curr?"class=\"active\"":"";
	}

	// 返回管理首页
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(ModelMap map, HttpSession session) {
		getPub(map, session,0);
		return "newindex";
	}

	// ----------------------------------------------------------------
	@RequestMapping(value = "/userManager", method = RequestMethod.GET)
	public String userManager(ModelMap map, HttpSession session) {
		getPub(map, session,1);
		List<UserBean> list = userDao.findAll();
		map.addAttribute("list", list);
		return "user/newtable";
	}
	
	@RequestMapping(value = "/questionAdd", method = RequestMethod.GET)
	public String questionAdd(ModelMap map, HttpSession session) {
		getPub(map, session,2);
		return "question/addform";
	}
	
	@RequestMapping(value = "/questionEdit/{id}/{type}", method = RequestMethod.GET)
	public String questionEdit(@PathVariable String id, 
			@PathVariable String type,
			ModelMap map, HttpSession session) {
		getPub(map, session,Integer.parseInt(type));
		map.addAttribute("bean", questionDao.findOne(Long.parseLong(id)));
		return "question/editform";
	}
	
	@RequestMapping(value = "/questionManager6", method = RequestMethod.GET)
	public String questionManager6(ModelMap map, HttpSession session) {
		getPub(map, session,6);
		List<QuestionBean> list = questionDao.findByType(6);
		map.addAttribute("list", list);
		return "question/newtable";
	}
	
	@RequestMapping(value = "/questionManager7", method = RequestMethod.GET)
	public String questionManager7(ModelMap map, HttpSession session) {
		getPub(map, session,7);
		List<QuestionBean> list = questionDao.findByType(7);
		map.addAttribute("list", list);
		return "question/newtable";
	}
	
	@RequestMapping(value = "/questionManager8", method = RequestMethod.GET)
	public String questionManager8(ModelMap map, HttpSession session) {
		getPub(map, session,8);
		List<QuestionBean> list = questionDao.findByType(8);
		map.addAttribute("list", list);
		return "question/newtable";
	}
	
	@RequestMapping(value = "/questionManager9", method = RequestMethod.GET)
	public String questionManager9(ModelMap map, HttpSession session) {
		getPub(map, session,9);
		List<QuestionBean> list = questionDao.findByType(9);
		map.addAttribute("list", list);
		return "question/newtable";
	}
	
	@RequestMapping(value = "/questionManager10", method = RequestMethod.GET)
	public String questionManager10(ModelMap map, HttpSession session) {
		getPub(map, session,10);
		List<QuestionBean> list = questionDao.findByType(10);
		map.addAttribute("list", list);
		return "question/newtable";
	}
}
