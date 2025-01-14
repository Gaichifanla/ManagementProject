package com.yizhi.student.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yizhi.common.annotation.Log;
import com.yizhi.common.controller.BaseController;
import com.yizhi.common.utils.*;
import com.yizhi.student.domain.ClassDO;
import com.yizhi.student.domain.CollegeDO;
import com.yizhi.student.service.ClassService;
import com.yizhi.student.service.CollegeService;
import com.yizhi.student.service.MajorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.yizhi.student.domain.StudentInfoDO;
import com.yizhi.student.service.StudentInfoService;

/**
 * 生基础信息表
 */
 
@Controller
@RequestMapping("/student/studentInfo")
public class StudentInfoController {

	


	@Autowired
	private StudentInfoService studentInfoService;



	/**
	 * 可分页 查询
	 */
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("student:studentInfo:studentInfo")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
		if (params.get("sort")!=null) {
			params.put("sort",BeanHump.camelToUnderline(params.get("sort").toString()));
		}
		System.out.println(params+"---------------------------------------");
		//查询列表数据
		Query query = new Query(params);
		List<StudentInfoDO> studentInfoDOList = studentInfoService.list(query);
		System.out.println(studentInfoDOList+"----------------------------");
		int total = studentInfoService.count(query);
		PageUtils pageUtils = new PageUtils(studentInfoDOList, total,query.getCurrPage(),query.getPageSize());
		return pageUtils;
	}


	/**
	 * 修改
	 */
	@Log("学生基础信息表修改")
	@ResponseBody
	@PostMapping("/update")
	@RequiresPermissions("student:studentInfo:edit")
	public R update(StudentInfoDO studentInfo){

		studentInfoService.update(studentInfo);
		return R.ok();
	}

	/**
	 * 删除
	 */
	@Log("学生基础信息表删除")
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("student:studentInfo:remove")
	public R remove( Integer id){
		if (studentInfoService.remove(id)>0) {
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 批量删除
	 */
	@Log("学生基础信息表批量删除")
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("student:studentInfo:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		int batchRemove = studentInfoService.batchRemove(ids);
		if (batchRemove>0){
			return R.ok();
		}
		return R.error();
	}


	//前后端不分离 客户端 -> 控制器-> 定位视图
	/**
	 * 学生管理 点击Tab标签 forward页面
	 */
	@GetMapping()
	@RequiresPermissions("student:studentInfo:studentInfo")
	String StudentInfo(){
		return "student/studentInfo/studentInfo";
	}

	/**
	 * 更新功能 弹出View定位
	 */
	@GetMapping("/edit/{id}")
	@RequiresPermissions("student:studentInfo:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		StudentInfoDO studentInfo = studentInfoService.get(id);
		model.addAttribute("studentInfo", studentInfo);
		return "student/studentInfo/edit";
	}

	/**
	 * 学生管理 添加学生弹出 View
	 */
	@GetMapping("/add")
	@RequiresPermissions("student:studentInfo:add")
	String add(StudentInfoDO studentInfo){
//	    studentInfoService.save(studentInfo);
		return "student/studentInfo/add";
	}
	@PostMapping("/save")
	@ResponseBody

	R save(StudentInfoDO studentInfo){
		//1
		int save = studentInfoService.save(studentInfo);
		if (save >0){
			return R.ok();
		}
		return R.error();
	}
	
}//end class
