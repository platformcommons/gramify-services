package com.platformcommons.platform.service.report.application.utility;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.token.PlatformAppToken;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
@Component
public final class ParamUtils {
	static final Logger LOGGER = LoggerFactory.getLogger(ParamUtils.class);
	public static IGXSecurityUtility igxUserService;
	// Reserved Params
	public static final String PARAM_EFFECTIVE_TENANT_ID = "EFFECTIVE_TENANT_ID";
	public static final String PARAM_CURRENT_TENANT_ID = "CURRENT_TENANT_ID";
	public static final String PARAM_IGX_CURRENT_TENANT_ID = "IGX_CURRENT_TENANT_ID";
	public static final String PARAM_CURRENT_TENANT_LOGINNAME = "CURRENT_TENANT_LOGIN_NAME";
	public static final String PARAM_CURRENT_DOMAIN = "CURRENT_DOMAIN";
	public static final String PARAM_CURRENT_USER_ID = "CURRENT_USER_ID";
	public static final String PARAM_IGX_CURRENT_USER_ID = "IGX_CURRENT_USER_ID";
	public static final String PARAM_CURRENT_USER_LOGIN_NAME = "CURRENT_USER_LOGIN_NAME";

	public static final String IS_TENANT_ADMIN = "IS_TENANT_ADMIN";
	public static final String IS_PLATFORM_ADMIN = "IS_PLATFORM_ADMIN";
	public static final String PARAM_LIMIT = "<::LIMIT::>";
	public static final String LIMIT = "LIMIT";
	public static final String PARAM_OFFSET = "<::OFFSET::>";
	public static final String OFFSET = "OFFSET";
	public static final String PARAM_CURRENT_ROLES = "CURRENT_ROLES";


	public static final String SORT_BY = "SORT_BY";
	public static final String SORT_ORDER = "SORT_ORDER";

	public static final String LIMIT_OFFSET = "LIMIT_OFFSET";

	public static final String LIMIT_ROW_COUNT = "LIMIT_ROW_COUNT";

	public static final String START_DATE = "START_DATE_TIME";
	public static final String END_DATE = "END_DATE_TIME";


	public static final String reservedParams[] = new String[] {  PARAM_CURRENT_TENANT_ID,
			PARAM_CURRENT_TENANT_LOGINNAME,  PARAM_CURRENT_USER_ID,
			PARAM_CURRENT_USER_LOGIN_NAME,IS_TENANT_ADMIN,IS_PLATFORM_ADMIN,
			PARAM_IGX_CURRENT_TENANT_ID,PARAM_IGX_CURRENT_USER_ID};

	public ParamUtils(@Autowired IGXSecurityUtility igxUserService){
		ParamUtils.igxUserService = igxUserService;
	}

	public static Map<String, Object> parseMap(String input) {
		final Map<String, Object> map = new HashMap<String, Object>();
		if(null == input || 0 == input.length()) {
			return map;
		}
		for (String pair : input.split("--")) {
			String[] kv = pair.split("=");
			if (kv.length != 2) {
				kv = new String[] { pair.split("=")[0], null };
			}
			LOGGER.info("param in the json>>>>" + kv[0] + " value " + kv[1]);
			if(kv[0].startsWith("SEARCH_TEXT")){
				kv[1] = "%"+kv[1]+"%";
			}

			if(kv[0].startsWith("IN_PARAM") && kv[1]!=null){
				map.put(kv[0], Arrays.asList( kv[1].split(",")));
			}else {
				map.put(kv[0], kv[1]);
			}
		}
		return map;
	}


	public static String replaceReservedParams(String sql) {
		Matcher matcher;
		Set<String> paramNames;
		String s;
		for (String rParam : reservedParams) {
			switch (rParam) {
				case PARAM_CURRENT_TENANT_ID:
					s = "<::" + PARAM_CURRENT_TENANT_ID + "::>";
					if(!sql.contains(s)) break;
					sql = sql.replace(s,
								Long.toString(PlatformSecurityUtil.getCurrentTenantId()));
					break;

				case PARAM_CURRENT_TENANT_LOGINNAME:
					s = "<::" + PARAM_CURRENT_TENANT_LOGINNAME + "::>";
					if(!sql.contains(s)) break;
					sql = sql.replace(s,
								PlatformSecurityUtil.getCurrentTenantLogin());
					break;


				case PARAM_CURRENT_USER_ID:
					s = "<::" + PARAM_CURRENT_USER_ID + "::>";
					if(!sql.contains(s)) break;
					sql = sql.replace(s, Long.toString(PlatformSecurityUtil.getCurrentUserId()));
					break;

				case PARAM_CURRENT_USER_LOGIN_NAME:
					s = "<::" + PARAM_CURRENT_USER_LOGIN_NAME + "::>";
					if(!sql.contains(s)) break;
					sql = sql.replace(s, PlatformSecurityUtil.getCurrentUserLogin());
					break;

				case IS_TENANT_ADMIN:
					s = "<::" + IS_TENANT_ADMIN + "::>";
					if(!sql.contains(s)) break;
					sql = sql.replace(s, PlatformSecurityUtil.isTenantAdmin()?" 1 ":" 0 ");
					break;
				case IS_PLATFORM_ADMIN:
					s = "<::" + IS_PLATFORM_ADMIN + "::>";
					if(!sql.contains(s)) break;
					sql = sql.replace(s, PlatformSecurityUtil.isPlatformAdmin()?" 1 ":" 0 ");
					break;
				case PARAM_IGX_CURRENT_TENANT_ID:

					matcher= Pattern.compile("<::("+PARAM_IGX_CURRENT_TENANT_ID+")::>").matcher(sql);
					paramNames=new HashSet<>();
					while(matcher.find()) paramNames.add(matcher.group(1));
					if(!paramNames.isEmpty()) {
						sql = sql.replace("<::" + PARAM_IGX_CURRENT_TENANT_ID + "::>", igxUserService.getCurrentTenantId().toString());
					}
					break;
				case PARAM_IGX_CURRENT_USER_ID:
					matcher= Pattern.compile("<::("+PARAM_IGX_CURRENT_USER_ID+")::>").matcher(sql);
					paramNames=new HashSet<>();
					while(matcher.find()) paramNames.add(matcher.group(1));
					if(!paramNames.isEmpty()) {
						sql = sql.replace("<::" + PARAM_IGX_CURRENT_USER_ID + "::>", igxUserService.getCurrentUserId().toString());
					}
					break;
			}
		}

		return sql;
	}

	public  static String  replacePagingParameters(String query , Map<String,Object> params,boolean checkDefaultRowCount,
												   int defaultRowCount){
		if(params.containsKey(SORT_BY)){
			query = query.replace(":"+SORT_BY,(String)params.get(SORT_BY));
		}
		if(params.containsKey(SORT_ORDER) ){
			validateSortOrder((String)params.get(SORT_ORDER));
			query= query.replace(":"+SORT_ORDER,(String)params.get(SORT_ORDER));
		}
		if(params.containsKey(LIMIT_OFFSET)){
			validateNumber(LIMIT_OFFSET,(String)params.get(LIMIT_OFFSET));
			query= query.replace(":"+LIMIT_OFFSET,(String)params.get(LIMIT_OFFSET));
		}
		if(params.containsKey(LIMIT_ROW_COUNT)){
			validateNumber(LIMIT_ROW_COUNT,(String)params.get(LIMIT_ROW_COUNT));
			if(checkDefaultRowCount){
				if(Long.parseLong((String) params.get(LIMIT_ROW_COUNT))> 100L){
					query= query.replace(":"+LIMIT_ROW_COUNT,String.valueOf(defaultRowCount));
				}
				else {
					query= query.replace(":"+LIMIT_ROW_COUNT,(String)params.get(LIMIT_ROW_COUNT));
				}
			}
			else {
				query= query.replace(":"+LIMIT_ROW_COUNT,(String)params.get(LIMIT_ROW_COUNT));
			}

		}
		return  query;
	}

	public static String replaceSearchText(String query,Map<String,String> params) {
		if(params.containsKey("SEARCH_TEXT")){
			query=query.replace(":SEARCH_TEXT",params.get("SEARCH_TEXT"));
		}
		return query;
	}

	private static void  validateNumber(String key ,String number){
		try{
			Long.parseLong(number);
		}catch (NumberFormatException ex){
			throw  new InvalidInputException(String.format("%s should b a number",number));
		}
	}

	private static  void  validateSortOrder(String order){
		boolean isValid= Boolean.FALSE;
		if(( order.equalsIgnoreCase("ASC")  || order.equalsIgnoreCase("DESC"))){
			isValid = Boolean.TRUE;
		}
		if(!isValid){
			throw  new InvalidInputException("Invalid Sort Order");
		}
	}

	public static String replaceReservedParamsV2(String sql) {
		if(!(SecurityContextHolder.getContext().getAuthentication() instanceof PlatformAppToken)){
			return replaceReservedParams(sql);
		}

		for (String rParam : reservedParams) {
			switch (rParam) {
				case PARAM_CURRENT_TENANT_ID:
					sql = sql.replace("<::" + PARAM_CURRENT_TENANT_ID + "::>", "-1");
					break;
				case PARAM_CURRENT_TENANT_LOGINNAME:
					sql = sql.replace("<::" + PARAM_CURRENT_TENANT_LOGINNAME + "::>","-1");
					break;
				case PARAM_CURRENT_USER_ID:
					sql = sql.replace("<::" + PARAM_CURRENT_USER_ID + "::>", "-1");
					break;

				case PARAM_CURRENT_USER_LOGIN_NAME:
					sql = sql.replace("<::" + PARAM_CURRENT_USER_LOGIN_NAME + "::>", "-1");
					break;

				case IS_TENANT_ADMIN:
					sql = sql.replace("<::" + IS_TENANT_ADMIN + "::>", " 0 ");
					break;
				case IS_PLATFORM_ADMIN:
					sql = sql.replace("<::" + IS_PLATFORM_ADMIN + "::>", " 0 ");
					break;
				case PARAM_IGX_CURRENT_TENANT_ID:
					sql = sql.replace("<::" + PARAM_IGX_CURRENT_TENANT_ID + "::>", "-1");
					break;
				case PARAM_IGX_CURRENT_USER_ID:
					sql = sql.replace("<::" + PARAM_IGX_CURRENT_USER_ID + "::>", "-1");
					break;
			}
		}
		return sql;
	}

	public static boolean usingReservedParamsCheck(String params) {
		if(params==null || params.isEmpty()) {
			return false;
		}
		Set<String> paramNames=Arrays.stream(params.split("--"))
									 .map(param->param.split("=")[0]).collect(Collectors.toSet());

		for(String reservedParam:reservedParams) {
			if(paramNames.contains(reservedParam)) {
				return true;
			}
		}
		return false;

	}
}
