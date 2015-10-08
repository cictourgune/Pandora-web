package org.tourgune.pandora.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tourgune.pandora.dao.DeveloperDao;

@Service("userServiceImpl")
@Transactional 
public class DeveloperServiceImpl implements UserDetailsService {
	
	@Resource
	private DeveloperDao developerDao;
	 
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
 
    	
    	List<GrantedAuthority> AUTHORITIES = new ArrayList<GrantedAuthority>();
    	
    	String password = developerDao.getDeveloperPassword(username);
    	
    	if (password!=null) { 
        	AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_REGISTERED")); 
        	return new User(username,password, AUTHORITIES);
        } else {
        	throw new UsernameNotFoundException("User not found: " + username);
        }
    }
}
