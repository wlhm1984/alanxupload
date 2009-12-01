package cn.alanx.test;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class TestListener implements ServletContextAttributeListener,
		ServletContextListener, HttpSessionBindingListener,
		HttpSessionActivationListener, HttpSessionListener,
		HttpSessionAttributeListener, ServletRequestListener,
		ServletRequestAttributeListener {

	public void attributeAdded(ServletContextAttributeEvent arg0) {
		
		System.out.println("1");
	}

	public void attributeRemoved(ServletContextAttributeEvent arg0) {
		System.out.println("2");

	}

	public void attributeReplaced(ServletContextAttributeEvent arg0) {
		System.out.println("3");

	}

	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("4");

	}

	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("5");

	}

	public void valueBound(HttpSessionBindingEvent arg0) {
		System.out.println("6");

	}

	public void valueUnbound(HttpSessionBindingEvent arg0) {
		System.out.println("7");

	}

	public void sessionDidActivate(HttpSessionEvent arg0) {
		System.out.println("8");

	}

	public void sessionWillPassivate(HttpSessionEvent arg0) {
		System.out.println("9");

	}

	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("sessionCreatedRequestedSessionId:"
				+ arg0.getSession().getId());
		System.out.println("finished!");
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("sessionDestroyedRequestedSessionId:"
				+ arg0.getSession().getId());
		System.out.println("finished!");

	}

	public void attributeAdded(HttpSessionBindingEvent arg0) {
		System.out.println("12");

	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		System.out.println("13");

	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		System.out.println("14");

	}

	public void requestDestroyed(ServletRequestEvent arg0) {
		System.out.println("requestDestroyedRequestedSessionId:"
				+ ((HttpServletRequest) arg0.getServletRequest())
						.getRequestedSessionId());
		System.out.println("finished!");
	}

	public void requestInitialized(ServletRequestEvent arg0) {

		// System.out.println("ContextPath:"+((HttpServletRequest)arg0.getServletRequest()).getContextPath());
		// System.out.println("LocalAddr:"+((HttpServletRequest)arg0.getServletRequest()).getLocalAddr());
		// System.out.println("LocalName:"+((HttpServletRequest)arg0.getServletRequest()).getLocalName());
		// System.out.println("LocalPort:"+((HttpServletRequest)arg0.getServletRequest()).getLocalPort());
		// System.out.println("Method:"+((HttpServletRequest)arg0.getServletRequest()).getMethod());
		// System.out.println("Protocol:"+((HttpServletRequest)arg0.getServletRequest()).getProtocol());
		// System.out.println("ContentLength:"+((HttpServletRequest)arg0.getServletRequest()).getContentLength());
		// System.out.println("ContentType:"+((HttpServletRequest)arg0.getServletRequest()).getContentType());
		// System.out.println("PathTranslated:"+((HttpServletRequest)arg0.getServletRequest()).getPathTranslated());
		// System.out.println("QueryString:"+((HttpServletRequest)arg0.getServletRequest()).getQueryString());
		// System.out.println("RemoteAddr:"+((HttpServletRequest)arg0.getServletRequest()).getRemoteAddr());
		// System.out.println("RemotePort:"+((HttpServletRequest)arg0.getServletRequest()).getRemotePort());
		// System.out.println("RemoteHost:"+((HttpServletRequest)arg0.getServletRequest()).getRemoteHost());
		System.out.println("requestInitializedRequestedSessionId:"
				+ ((HttpServletRequest) arg0.getServletRequest())
						.getRequestedSessionId());
		// System.out.println("RequestURI:"+((HttpServletRequest)arg0.getServletRequest()).getRequestURI());
		// System.out.println("Scheme:"+((HttpServletRequest)arg0.getServletRequest()).getScheme());
		// System.out.println("ServerName:"+((HttpServletRequest)arg0.getServletRequest()).getServerName());
		// System.out.println("ServerPort:"+((HttpServletRequest)arg0.getServletRequest()).getServerPort());
		// System.out.println("ServletPath:"+((HttpServletRequest)arg0.getServletRequest()).getServletPath());
		// System.out.println("Locale:"+((HttpServletRequest)arg0.getServletRequest()).getLocale().toString());
		// System.out.println("RequestURL:"+((HttpServletRequest)arg0.getServletRequest()).getRequestURL().toString());

	}

	public void attributeAdded(ServletRequestAttributeEvent arg0) {
		System.out.println("Test_requestInitialized");

	}

	public void attributeRemoved(ServletRequestAttributeEvent arg0) {
		System.out.println("Test_requestInitialized");

	}

	public void attributeReplaced(ServletRequestAttributeEvent arg0) {
		System.out.println("Test_requestInitialized");

	}

}