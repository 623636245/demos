package cn.lannis.demo.codegenerater.utils;

import org.springframework.util.StringUtils;

public class TableConvert {
	public static String getNullAble(String nullable) {
		if (("YES".equals(nullable)) || ("yes".equals(nullable)) || ("y".equals(nullable)) || ("Y".equals(nullable)) || ("f".equals(nullable))) {
			return "Y";
		}
		if (("NO".equals(nullable)) || ("N".equals(nullable)) || ("no".equals(nullable)) || ("n".equals(nullable)) || ("t".equals(nullable))) {
			return "N";
		}
		return null;
	}

	public static String getNullString(String nullable) {
		if (StringUtils.isEmpty(nullable)) {
			return "";
		}
		return nullable;
	}

	public static String getV(String s) {
		return "'" + s + "'";
	}
}
