package com.mexc.sun.admin.config;

import com.mexc.sun.admin.config.RootConfig.WebPackage;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import java.util.regex.Pattern;

@Configuration
@ImportResource("classpath:dubbo/dubbo_consumer.xml")
@ComponentScan(basePackages = {"com.mexc.sun"},
		excludeFilters = {
				@Filter(type = FilterType.CUSTOM, value = WebPackage.class)
		}
)
public class RootConfig {
	public static class WebPackage extends RegexPatternTypeFilter {
	    public WebPackage() {
	      super(Pattern.compile("com\\.mexc\\.sun\\.admin"));
	    }
	  }
}
