package com.stonewu.blog.core.entity.auth;

import com.stonewu.blog.core.entity.SysUser;

import java.io.Serializable;
import java.util.Date;

/**
 * 可序列化的rememberMeToken
 * 
 * @author StoneWu
 *
 */
public class AdminUserLoginToken implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;
	private String series;
	private SysUser user;
	private Date date;
	// Session Host
	private String host;

	public AdminUserLoginToken() {
	}


	public AdminUserLoginToken(String username, String series, SysUser user, Date date, String host) {
		super();
		this.username = username;
		this.series = series;
		this.user = user;
		this.date = date;
		this.host = host;
	}


	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public String getSeries() {
		return series;
	}

	public Date getDate() {
		return date;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
