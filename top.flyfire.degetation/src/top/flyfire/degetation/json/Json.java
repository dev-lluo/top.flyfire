package top.flyfire.degetation.json;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SuppressWarnings("all")
public interface Json {
	/**
	 * 对象转json
	 * @author flyfire
	 *
	 */
	public class ObJ {
		
		private static final int defaultDeep = 3;
		
		private static final String defaultSkip = "$[skip]$";
		
		public static String convert(Object obj){
				return convert(obj,ObJ.defaultDeep);
		}
		
		public static String convert(Object obj,int deep){
			if(obj==null){
				return "null";
			}else if(deep==0){
				return ObJ.defaultSkip;
			}
			
			Class<?> type = obj.getClass();
			if(isVType(type)){
				return VJSON(obj,deep);
			}else if(isRBaseType(type)){
				return RBseJSON(obj,deep);
			}else if(obj.getClass().isArray()){
				return ArrayJSON(obj,deep);
			}else if(obj instanceof java.util.Collection){
				return ListJSON(obj,deep);
			}else if(obj instanceof java.util.Map){
				return MapJSON(obj,deep);
			}else{
				try {
					return ObjectJSON(obj,deep);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					throw new RuntimeException(e);
				}
			}
		}
		
		private static boolean isVType(Class<?> type){
			return type.isPrimitive()
					|Boolean.class.isAssignableFrom(type)
					|Number.class.isAssignableFrom(type);
		}
		
		private static boolean isRBaseType(Class<?> type){
			return Date.class.isAssignableFrom(type)
			|String.class.isAssignableFrom(type);
		}
		
		private static boolean isVArrType(Object obj){
			return obj instanceof int[]
					|obj instanceof double[]
					|obj instanceof float[]
					|obj instanceof byte[]
					|obj instanceof long[]
					|obj instanceof short[]
					|obj instanceof boolean[]
					|obj instanceof char[];
		}
		
		private static String VJSON(Object obj,int deep){
			deep--;
			return obj.toString();
		}
		
		private static String RBseJSON(Object obj,int deep){
			deep--;
			return "\""+obj.toString()+"\"";
		}
		
		private static String ObjectJSON(Object obj,int deep) throws IllegalArgumentException, IllegalAccessException {
			deep--;
			Class<?> clss =obj.getClass();
			StringBuffer sb = new StringBuffer("{");
			Field[] fieldArr = clss.getDeclaredFields();
			for(int i = 0;i<fieldArr.length;i++){
				Field field = fieldArr[i];
				field.setAccessible(true);
				String temp = ObJ.convert(field.get(obj),deep);
				if(temp==ObJ.defaultSkip)continue;
				sb.append("\"");
				sb.append(field.getName());
				sb.append("\"");
				sb.append(":");
				sb.append(temp);
				sb.append(",");
			}
			while(clss.getSuperclass()!=Object.class&&clss!=Object.class){
				clss = clss.getSuperclass();
				fieldArr = clss.getDeclaredFields();
				for(int i = 0;i<fieldArr.length;i++){
					Field field = fieldArr[i];
					field.setAccessible(true);
					String temp = ObJ.convert(field.get(obj),deep);
					if(temp==ObJ.defaultSkip)continue;
					sb.append("\"");
					sb.append(field.getName());
					sb.append("\"");
					sb.append(":");
					sb.append(temp);
					sb.append(",");
				}
			}
			int sbLen = sb.length();
			if(sbLen>1){
				sb.replace(sbLen-1, sbLen, "}");
			}else{
				sb.append("}");
			}
			return sb.toString();
		}
		private static String ArrayJSON(Object obj,int deep){
			deep--;
			if(ObJ.convert("",deep)==ObJ.defaultSkip)return ObJ.defaultSkip;
			StringBuffer sb = new StringBuffer("[");
			for(int i = 0,len = Array.getLength(obj);i<len;i++){
				sb.append(ObJ.convert(Array.get(obj, i),deep)+" ,");
			}
			int sbLen = sb.length();
			if(sbLen>1){
				sb.replace(sbLen-1, sbLen, "]");
			}else{
				sb.append("]");
			}
			return sb.toString();
		}
		private static String MapJSON(Object obj,int deep){
			deep--;
			StringBuffer sb = new StringBuffer("{");
			Map objs = (Map)obj;
			Set<Entry> entrySet = objs.entrySet();
			for(Map.Entry entry : entrySet){
				String temp = ObJ.convert(entry.getValue(),deep);
				if(temp==ObJ.defaultSkip)continue;
				sb.append("\"");
				sb.append(entry.getKey());
				sb.append("\"");
				sb.append(":");
				sb.append(temp);
				sb.append(",");
			}
			int sbLen = sb.length();
			if(sbLen>1){
				sb.replace(sbLen-1, sbLen, "}");
			}else{
				sb.append("}");
			}
			return sb.toString();
		}
		private static String ListJSON(Object obj,int deep){
			deep--;
			if(ObJ.convert("",deep)==ObJ.defaultSkip)return ObJ.defaultSkip;
			StringBuffer sb = new StringBuffer("[");
			Collection objs = (Collection)obj;
			for(Object o : objs){
				sb.append(ObJ.convert(o,deep)+" ,");
			}
			int sbLen = sb.length();
			if(sbLen>1){
				sb.replace(sbLen-1, sbLen, "]");
			}else{
				sb.append("]");
			}
			return sb.toString();
		}

	}
	/**
	 * json转对象
	 * @author flyfire
	 *
	 */
	public class JsO {

		private static  Pattern pattern = Pattern.compile("-?[0-9]+(.?[0-9]+)?");
		
		public enum JType {
			ARR, KVL, STR, NUM, NULL
		}

		@SuppressWarnings("unchecked")
		public static <T> T convert(String json) {
			JType type = JsO.dist(json);
			if(type==JType.KVL){
				Map<String,Object> result = JsO.kvlReadAdapter(json);
//				Set<Entry<String,Object>> set = result.entrySet();
//				for(Iterator<Entry<String,Object>> i = set.iterator();i.hasNext();){
//					Entry<String,Object> entry = i.next();
//					entry.setValue(JsO.convert(entry.getValue().toString()));
//				}
				Map<String,Object> temp = new HashMap<String, Object>();
				for(Iterator<String> i = result.keySet().iterator();i.hasNext();){
					String key  = i.next();
					Object val = result.get(key);
					val = JsO.convert(val==null?null:val.toString());
					temp.put(key, val);
					i.remove();
				}
				for(Iterator<String> i = temp.keySet().iterator();i.hasNext();){
					String key  = i.next();
					result.put(key, temp.get(key));
				}
				return (T) result;
			}else if(type==JType.ARR){
				List<Object> result = JsO.arrReadAdapter(json);
				for(int i = 0,len = result.size();i<len;i++){
					Object obj = result.get(i);
					result.set(i, JsO.convert(obj.toString()));
				}
				return (T) result;
			}else if(type==JType.STR){
				return (T) JsO.prevStrData(json);
			}else if(type==JType.NUM){
				return (T) new BigDecimal(json);
			}
			return null;
		}

		private static JType dist(String json) {
			if(json==null||"null".equals(json)){
				return JType.NULL;
			}else if (json.startsWith("{") && json.endsWith("}")) {
				return JType.KVL;
			} else if (json.startsWith("[") && json.endsWith("]")) {
				return JType.ARR;
			} else if (json.startsWith("\"")&&json.endsWith("\"")) {
				return JType.STR;
			} else {
				Matcher isNum = pattern.matcher(json);
				if (isNum.matches()){
				    return JType.NUM; 
				}else{
					throw new UnknowJTypeException(json);
				}
			}
		}
		//[1,2,3,4,[1,2,3,4,5,[1,2,3,4,5]]]
		private static List<Object> arrReadAdapter(String json){
			List<Object> arrTemp = new ArrayList<Object>();
			StringBuffer temp = new StringBuffer();
			List<Character> fruitage = JsO.husking(json);
			
			Character idf = null;
			for(int deep = 0;!fruitage.isEmpty();){
				
				char ch = fruitage.remove(0);
				
				if(ch=='"'&&idf==null){
					deep++;
					idf = ch;
				}else if((ch=='{'||ch=='[')&&idf==null){
					deep++;
				}else if(ch=='"'&&idf=='"'){
					deep--;
					idf = null;
				}else if((ch=='}'||ch==']')&&idf==null){
					deep--;
				}
				
				if(temp.length()==0&&ch==','){
					throw new UnknowJTypeException(json);
				}else if(ch!=','||deep!=0){
					temp.append(ch);
				}else{
					arrTemp.add(temp.toString());
					temp.delete(0, temp.length());
				}
				
				if(fruitage.size()==0){
					arrTemp.add(temp.toString());
					temp.delete(0, temp.length());
				}
			}
			
			return arrTemp;
		}
		
		private static Map<String,Object> kvlReadAdapter(String json){
			Map<String,Object> kvlTemp = new HashMap<String, Object>();
			StringBuffer temp = new StringBuffer();
			List<Character> fruitage = JsO.husking(json);
			char idf = '\0';
			char prev = '\0';
			for(int deep = 0;!fruitage.isEmpty();){
				
				char ch = fruitage.remove(0);
				if(ch=='"'&&idf=='\0'&&prev!='\\'){
					deep++;
					idf = ch;
				}else if((ch=='{'||ch=='[')&&idf=='\0'){
					deep++;
				}else if(ch=='"'&&idf=='"'&&prev!='\\'){
					deep--;
					idf = '\0';
				}else if((ch=='}'||ch==']')&&idf=='\0'){
					deep--;
				}
				
				if(temp.length()==0&&ch==','){
					throw new UnknowJTypeException(json);
				}else if(ch!=','||deep!=0){
					temp.append(ch);
				}else{
					String key = temp.substring(0,temp.indexOf(":"));
					if(JsO.dist(key)==JType.STR){
						key = JsO.prevStrData(key);
					}
					String value = temp.substring(temp.indexOf(":")+1);
					kvlTemp.put(key,value);
					temp.delete(0, temp.length());
				}
				
				if(fruitage.size()==0){
					String key = temp.substring(0,temp.indexOf(":"));
					if(JsO.dist(key)==JType.STR){
						key = JsO.prevStrData(key);
					}
					String value = temp.substring(temp.indexOf(":")+1);
					kvlTemp.put(key,value);
					temp.delete(0, temp.length());
				}
				prev = ch;
			}
			return kvlTemp;
		}
		
		private static String prevStrData(String data){
			return data.substring(1,data.length()-1);
		}
		
		private static List<Character> husking(String json){
			json = json.substring(1,json.length()-1);
			List<Character> rs = new ArrayList<Character>();
			for(int i = 0,len = json.length();i<len;i++){
				rs.add(json.charAt(i));
			}
			return rs;
		}
		
		
		
	}

}
