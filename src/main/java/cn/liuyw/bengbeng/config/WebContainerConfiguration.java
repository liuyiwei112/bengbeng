package cn.liuyw.bengbeng.config;

import cn.liuyw.bengbeng.bean.Result;
import cn.liuyw.bengbeng.filter.RequestLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class WebContainerConfiguration {

	@Bean
	public FilterRegistrationBean requestLoggingFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new RequestLoggingFilter());
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean characterEncodingFilter() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setForceEncoding(true);
		characterEncodingFilter.setEncoding("UTF-8");
		registrationBean.setFilter(characterEncodingFilter);
		registrationBean.addUrlPatterns("/*");
		return registrationBean;
	}

	@Bean(name = "localeResolver")
	public LocaleResolver localeResolverBean() {
		SessionLocaleResolver local=new SessionLocaleResolver();
		local.setDefaultLocale(Locale.CHINA);
		return new SessionLocaleResolver();
	}

	@Bean(name = "messageSource")
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("i18n/message");
		Result.setMessageSource(source);
		return source;
	}

	
}
