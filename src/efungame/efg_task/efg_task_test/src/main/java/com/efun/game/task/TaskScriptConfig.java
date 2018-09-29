package com.efun.game.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class TaskScriptConfig {

	@Bean(name="produceTaskScript")
	public RedisScript<String> produceTaskScript() {
		DefaultRedisScript<String> script = new DefaultRedisScript<String>();
		script.setScriptSource(new ResourceScriptSource(new ClassPathResource("/script/produceTask.lua")));
		script.setResultType(String.class);
		return script;
	}
}
