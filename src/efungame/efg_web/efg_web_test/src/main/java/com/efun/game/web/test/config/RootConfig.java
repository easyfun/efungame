package com.efun.game.web.test.config;

import java.util.regex.Pattern;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import com.efun.game.web.test.config.RootConfig.WebPackage;

@Configuration
//@ImportResource("classpath:services/service-consumer.xml")
@ComponentScan(basePackages = {"com.efun.game"},
	excludeFilters = {
		@Filter(type = FilterType.CUSTOM, value = WebPackage.class)
	}
)
public class RootConfig {
	public static class WebPackage extends RegexPatternTypeFilter {
	    public WebPackage() {
	      super(Pattern.compile("com\\.efun\\.game\\.web\\.test"));
	    }
	  }
}
