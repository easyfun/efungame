package com.efun.game.performance.id;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.efun.game.common.id.IdUtils;

@Service("idServiceImpl")
public class IdServiceImpl implements IdService {
	private static final Logger logger = LoggerFactory.getLogger(IdServiceImpl.class);

	private static Map<String, Long> mapUid = new HashMap<>();
	private static long count = 0;

	@Override
	public void insert() {
		long id = IdUtils.getInstance().createFlowId("01");
//		long id = IdUtils.getInstance().createUid();
		checkId(id);
	}
	
	public void show() {
		logger.info("thread id: {}, uidSize={}, count={}", Thread.currentThread().getName(), mapUid.size(), count);
	}

	private static synchronized void checkId(long id) {
		count++;
		// System.out.printf("id=%d\n", id);
		Long oldId = mapUid.get(String.valueOf(id));
		if (null != oldId) {
			logger.error("duplicate id, id={}, oldId={}", id, oldId);
			System.exit(-1);
		}
		mapUid.put(String.valueOf(id), id);

	}
}
