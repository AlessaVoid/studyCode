package com.boco.SYS.base;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.context.annotation.ScopedProxyMode;

public class ScopeResolver implements ScopeMetadataResolver {

	  @Override
	  public ScopeMetadata resolveScopeMetadata(BeanDefinition definition) {
	    ScopeMetadata result = new ScopeMetadata();
	    result.setScopedProxyMode(ScopedProxyMode.NO);
	    result.setScopeName("session");
	    return result;
	  }

}
