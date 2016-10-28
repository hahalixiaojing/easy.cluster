package easy.rpc.http.support;

import java.lang.reflect.Array;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import easy.rpc.http.IReturnConvert;

public class DefaultReturnConvert implements IReturnConvert {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T convert(String data, Class<T> clzz) {
		if (data == null || data.trim() == "") {
			return null;
		}
		if (clzz.isArray()) {

			Class<?> arrayType = clzz.getComponentType();

			JSONArray jsonArray = JSON.parseArray(data);
			Object array = Array.newInstance(arrayType, jsonArray.size());

			for (int i = 0; i < jsonArray.size(); i++) {

				Object value = jsonArray.getObject(i, arrayType);
				Array.set(array, i, value);
			}
			return (T) array;
		}
		return JSON.parseObject(data, clzz);
	}

}
