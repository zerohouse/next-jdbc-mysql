package next.jdbc.mysql.setting;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import next.jdbc.mysql.annotation.Exclude;

public class Parser {
	public static <T> T getObject(Class<T> cLass, Map<String, Object> record) {
		if (record == null)
			return null;
		T result = null;
		result = newInstance(cLass);
		setObject(result, record);
		return result;
	}

	public static <T> T setObject(T record, Map<String, Object> recordMap) {
		if (recordMap == null)
			return null;
		Class<?> cLass = record.getClass();
		Field[] fields = cLass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Exclude.class))
				continue;
			Object obj = recordMap.get(Setting.getSqlSupports().getSqlField(fields[i]).getColumnName());
			if (obj == null)
				continue;
			if (obj.getClass().equals(Timestamp.class)) {
				obj = new Date(((Timestamp) obj).getTime());
			}
			fields[i].setAccessible(true);
			try {
				fields[i].set(record, obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return record;
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> type) {
		List<Object> params = new ArrayList<Object>();
		if (type.getConstructors().length == 0)
			return null;
		Class<?>[] paramTypes = type.getConstructors()[0].getParameterTypes();
		if (paramTypes.length == 0)
			try {
				return type.getConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		Object obj = null;
		for (int i = 0; i < paramTypes.length; i++) {
			obj = getDefaultValue(paramTypes[i]);
			params.add(obj);
		}
		try {
			return (T) type.getConstructors()[0].newInstance(params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	static Object getDefaultValue(Class<?> paramType) {
		if (paramType.equals(byte.class)) {
			return 0;
		}
		if (paramType.equals(short.class)) {
			return 0;
		}
		if (paramType.equals(int.class)) {
			return 0;
		}
		if (paramType.equals(long.class)) {
			return 0L;
		}
		if (paramType.equals(float.class)) {
			return 0.0f;
		}
		if (paramType.equals(double.class)) {
			return 0.0d;
		}
		if (paramType.equals(char.class)) {
			return '\u0000';
		}
		if (paramType.equals(boolean.class)) {
			return false;
		}
		if (paramType.equals(byte[].class)) {
			return new byte[] { 0 };
		}
		if (paramType.equals(short[].class)) {
			return new short[] { 0 };
		}
		if (paramType.equals(int[].class)) {
			return new int[] { 0 };
		}
		if (paramType.equals(long[].class)) {
			return new long[] { 0L };
		}
		if (paramType.equals(float[].class)) {
			return new float[] { 0.0f };
		}
		if (paramType.equals(double[].class)) {
			return new double[] { 0.0d };
		}
		if (paramType.equals(char[].class)) {
			return new char[] { '\u0000' };
		}
		if (paramType.equals(boolean[].class)) {
			return new boolean[] { false };
		}
		return null;
	}
}
