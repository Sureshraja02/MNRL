package com.fisglobal.fsg.dip.core.comm.beans;

import java.util.Map;

public class RouterGroupMap {
	private static Map<String,RouterGroup_VO> routGrpMap;
	public static Map<String,RouterGroup_VO>getRoutGrpMap(){return routGrpMap;}
	public static void setRoutGrpMap(Map<String,RouterGroup_VO>routGrpMap){RouterGroupMap.routGrpMap=routGrpMap;}
}
