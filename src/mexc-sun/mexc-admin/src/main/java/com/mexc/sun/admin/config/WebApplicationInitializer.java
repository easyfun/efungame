package com.mexc.sun.admin.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Web应用初始化类
 * @author dennis
 *
 */
public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/**
	 * 使用Class<?> [] 中的Configuration注解类来配置ContextLoaderListener创建的应用上下文
	 * @return
	 */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?> [] { RootConfig.class };
	}

	/**
	 * 
	 * 使用Class<?> [] 中的Configuration注解类来配置DispatcherServlet应用上下文中的bean
	 * @return
	 */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?> [] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();  
        characterEncodingFilter.setEncoding("UTF-8");  
        characterEncodingFilter.setForceEncoding(true);  
		return new Filter[]{characterEncodingFilter};
	}

}
