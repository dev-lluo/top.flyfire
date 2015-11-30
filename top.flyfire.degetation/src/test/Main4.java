package test;

import java.util.Map;

import top.flyfire.degetation.json.Json.JsO;
import top.flyfire.degetation.json.Json.ObJ;

public class Main4 {
	public static void main(String[] args) {
		String str = ObJ.convert(new ClassA());
		System.out.println(str);
		Map map = JsO.convert(str);
		System.out.println(map.get("s"));
		System.out.println(map.get("s1"));
	}
}
