package com.fisglobal.fsg.dip.core.entity.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.fisglobal.fsg.dip.entity.User_Master;

public interface UserRepo extends PagingAndSortingRepository<User_Master, Long> {

	@Query(value = "select * from USER_MASTER where EMAIL=?1", nativeQuery = true)
	User_Master findByUserID(String userID);

	Optional<User_Master> findByUserName(String username);
	
	@Query(value = "select * from USER_MASTER where USERNAME=? and CHANNEL=?", nativeQuery = true)
	Optional<User_Master> findByUserusingUserNameNChannel(String username, String channel);
	
	@Query(value = "select * from USER_MASTER where CHANNEL=?", nativeQuery = true)
	Optional<User_Master> findByUserusingOnlyChannel( String channel);
}
