package com.mexc.sun.web.config;

import org.springframework.context.annotation.Configuration;
import java.util.regex.Pattern;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.ComponentScan.Filter;

import com.mexc.sun.web.config.RootConfig.WebPackage;

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
	      super(Pattern.compile("com\\.mexc\\.sun\\.web"));
	    }
	  }
}
