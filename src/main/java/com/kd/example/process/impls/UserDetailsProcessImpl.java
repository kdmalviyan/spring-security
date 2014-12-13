/**
 * 
 */
package com.kd.example.process.impls;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.kd.example.entities.Role;
import com.kd.example.entities.UserInfo;
import com.kd.example.process.UserDetailsProcess;

/**
 * @author kd
 *
 */
public class UserDetailsProcessImpl implements UserDetailsProcess {
	private SessionFactory sessionFactory;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		UserInfo userInfo = getUser(username);
		if (userInfo != null) {
			List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
			for (Role role : userInfo.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			}
			User user = new User(username, userInfo.getPassword(), true, true,
					true, true, authorities);
			return user;
		} else {
			throw new UsernameNotFoundException(
					"Could not found user with provded credentials.....");
		}
	}

	public UserInfo getUser(String username) {
		Session session = sessionFactory.openSession();
		// Transaction transaction = session.beginTransaction();
		// transaction.begin();
		Criteria criteria = session.createCriteria(UserInfo.class);
		criteria.add(Restrictions.eq("username", username));
		UserInfo info = (UserInfo) criteria.uniqueResult();
		session.close();
		// transaction.commit();
		return info;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
