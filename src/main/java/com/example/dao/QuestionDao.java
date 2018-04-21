package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.QuestionBean;
import com.example.bean.UserBean;

public interface QuestionDao extends JpaRepository<QuestionBean, Long> {
	@Query("from QuestionBean b where b.type=:type")
	List<QuestionBean> findByType(@Param("type") int type);
}
