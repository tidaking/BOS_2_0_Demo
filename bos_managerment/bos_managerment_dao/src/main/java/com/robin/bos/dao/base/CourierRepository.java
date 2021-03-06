package com.robin.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.robin.bos.domain.base.Courier;

/**  
 * ClassName:CourierRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午10:35:35 <br/>       
 */

//JpaSpecificationExecutor:
//这个接口是spring-data-jpa,提供出来做复杂查询用的,里面有如下方法接口如:Page<T> findAll(Specification<T> spec, Pageable pageable);
//里面有个关键对象(实际上是接口):Specification,我将其理解为复杂查询条件的集合
public interface CourierRepository extends JpaRepository<Courier, Long>,JpaSpecificationExecutor<Courier> {
    Courier findById(Long id);
    
    @Query("from Courier where nvl(deltag,0) <> 1")
    List<Courier> findAvalible();

}
  
