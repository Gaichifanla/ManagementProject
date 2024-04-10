package com.yizhi.student.service.impl;

import com.yizhi.common.redis.shiro.RedisCache;
import com.yizhi.common.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yizhi.student.dao.StudentInfoDao;
import com.yizhi.student.domain.StudentInfoDO;
import com.yizhi.student.service.StudentInfoService;



@Service
public class StudentInfoServiceImpl implements StudentInfoService {



	@Autowired
	private StudentInfoDao studentInfoDao;



	@Override
	public StudentInfoDO get(Integer id){
		System.out.println("======service层中传递过来的id参数是：" + id + "======");
		return studentInfoDao.get(id);
	}


	@Override
	public List<StudentInfoDO> list(Map<String, Object> map){

		return studentInfoDao.list(map);
	}

	//"===================================================================================="


	@Override
	public int count(Map<String, Object> map){
		return studentInfoDao.count(map);
	}
	
	@Override
	public int save(StudentInfoDO studentInfo){
		Integer userId = Math.toIntExact(ShiroUtils.getUserId());
		studentInfo.setAddTime(new Date());
		studentInfo.setAddUserid(userId);
		studentInfo.setEditTime(new Date());
		studentInfo.setEditUserid(userId);
		return studentInfoDao.save(studentInfo);
	}
	
	@Override
	public int update(StudentInfoDO studentInfo){
		Integer userId = Math.toIntExact(ShiroUtils.getUserId());
		studentInfo.setEditTime(new Date());
		studentInfo.setEditUserid(userId);
		return studentInfoDao.update(studentInfo);
	}
	
	@Override
	public int remove(Integer id){
		return studentInfoDao.remove(id);
	}
	
	@Override
	public int batchRemove(Integer[] ids){
		return studentInfoDao.batchRemove(ids);
	}
	
}
