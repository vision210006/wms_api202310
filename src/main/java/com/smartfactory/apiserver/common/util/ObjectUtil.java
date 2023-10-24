package com.smartfactory.apiserver.common.util;

import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * @FileName : ObjectUtil.java
 * @Date : 2021. 2. 6.
 * @author : dooho
 * @Class 설명 : Object의 생성을 담당하는 Util 클래스
 */

public class ObjectUtil {
	
	private ObjectUtil() {
		throw new AssertionError();
	}
	/**
	 * @MethodName : newMapInstance
	 * @Date : 2021. 2. 6.
	 * @author : dooho
	 * @변경이력 :
	 * @Method설명 :
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> HashMap<K, V> newMapInstance(Class<K> keyType, Class<V> valueType){
		return new HashMap<K, V>();
	}
	
	/**
	 * @MethodName : newListInstance
	 * @Date : 2021. 2. 6.
	 * @author : dooho
	 * @변경이력 :
	 * @Method설명 :
	 * @param <T>
	 * @return
	 */
	public static <T> ArrayList<T> newListInstance(Class<T> type){
		return new ArrayList<T>();
	}
	
	/**
	 * @MethodName : LinkedMultiValueMap
	 * @Date : 2021. 2. 6.
	 * @author : dooho
	 * @변경이력 :
	 * @Method설명 :
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> LinkedMultiValueMap<K, V> newMultiValueMapInstance(Class<K> keyType, Class<V> valueType){
		return new LinkedMultiValueMap<K, V>();
	}

	public static LinkedMultiValueMap<String, String> newMultiValueMapInstanceForJsonHeader(){
		LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		return headers;
	}
}
