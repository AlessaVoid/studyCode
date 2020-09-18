package com.boco.SYS.customdatasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	protected Object determineCurrentLookupKey() {
		// 从自定义的位置获取数据源标识
		//logger.info("current dadasource is "+DynamicDataSourceHolder.getDataSource());
        return DynamicDataSourceHolder.getDataSource();
	}
	
}
