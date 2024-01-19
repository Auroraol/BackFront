package com.springboot101.repository;

import com.springboot101.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**

 * @Description:
 * @date 2021/10/29
 */
@Repository  //@Repository是标注在Dao层接口上，作用是将接口的一个实现类交给Spring管理。
public interface UserDao extends JpaRepository<User, Long> {

}
