package com.jsh.filter.common;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;

public class OpenSessionInViewFilterExtend extends OpenSessionInViewFilter
{
	@Override
	protected Session getSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException
	{
		this.setFlushMode(FlushMode.AUTO);
		return super.getSession(sessionFactory);
	}
	@Override
	protected void closeSession(Session session, SessionFactory sessionFactory)
	{
		session.flush();
		super.closeSession(session, sessionFactory);
	}
}
