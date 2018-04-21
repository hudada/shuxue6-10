package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.ErrorBean;
import com.example.bean.UserBean;

public interface ErrorDao extends JpaRepository<ErrorBean, Long> {

    @Query("from ErrorBean b where b.uid=:uid and b.qid=:qid")
    ErrorBean findByUid(@Param("uid") Long uid,
    		@Param("qid") Long qid);
    
    @Query("from ErrorBean b where b.uid=:uid")
    List<ErrorBean> findByUidList(@Param("uid") Long uid);
    
}
