package com.boco.SYS.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private String[] encryptPropNames = { "jdbc.test.password","jdbc.test.url","jdbc.para.password","jdbc.para.url","jdbc.query.password","jdbc.query.url" };

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {

		// ����ڼ������������з��ָ�����
		if (isEncryptProp(propertyName)) {
			String decryptValue = ThreeDes.decryptString(propertyValue, ThreeDes.KEY);
			return decryptValue;
		} else {
			return propertyValue;
		}

	}

	private boolean isEncryptProp(String propertyName) {
		for (String encryptName : encryptPropNames) {
			if (encryptName.equals(propertyName)) {
				return true;
			}
		}
		return false;
	}
}
