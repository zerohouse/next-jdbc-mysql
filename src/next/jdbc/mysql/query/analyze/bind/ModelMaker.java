package next.jdbc.mysql.query.analyze.bind;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import next.jdbc.mysql.join.JoinSet;
import next.jdbc.mysql.query.analyze.Analyzer;
import next.jdbc.mysql.query.analyze.JoinAnalyzer;
import next.jdbc.mysql.query.analyze.TypeAnalyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelMaker {

	private static final Logger logger = LoggerFactory.getLogger(ModelMaker.class);

	public static <T> T getByMap(Class<T> type, Map<String, Object> recordMap) {
		if (recordMap == null)
			return null;
		Analyzer analyzer = new TypeAnalyzer(type);
		return getObjectByMap(type, recordMap, analyzer);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getObjectByMap(Class<T> type, Map<String, Object> recordMap, Analyzer analyzer) {
		if (recordMap == null)
			return null;
		T object = newInstance(type);
		return (T) setByMap(object, recordMap, analyzer);
	}

	public static Object setByMap(Object object, Map<String, Object> recordMap, Analyzer analyzer) {
		if (recordMap == null)
			return null;
		if (analyzer.getClass().equals(JoinAnalyzer.class))
			return getJoinSet(recordMap, (JoinAnalyzer) analyzer);
		analyzer.getAllFields().forEach(fieldObject -> {
			Object obj = recordMap.get(fieldObject.getColumnName().toLowerCase());
			if (obj == null)
				return;
			try {
				Field field = fieldObject.getField();
				field.setAccessible(true);
				field.set(object, obj);
			} catch (Exception e) {
				logger.warn(e.getMessage());
			}
		});
		return object;
	}

	@SuppressWarnings("unchecked")
	private static <T> T newInstance(Class<T> type) {
		List<Object> params = new ArrayList<Object>();
		Constructor<?>[] constructors = type.getConstructors();
		if (constructors.length == 0)
			return null;
		for (int i = 0; i < constructors.length; i++)
			if (constructors[i].getParameterTypes().length == 0) {
				try {
					return (T) constructors[i].newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		Class<?>[] paramTypes = constructors[0].getParameterTypes();
		for (int i = 0; i < paramTypes.length; i++) {
			params.add(getDefaultValue(paramTypes[i]));
		}
		try {
			return (T) type.getConstructors()[0].newInstance(params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Object getDefaultValue(Class<?> paramType) {
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

	@SuppressWarnings("unchecked")
	public static <LEFT, RIGHT> JoinSet<LEFT, RIGHT> getJoinSet(Map<String, Object> recordMap, JoinAnalyzer analyzer) {
		JoinSet<LEFT, RIGHT> result = new JoinSet<LEFT, RIGHT>(null, null);
		result.setLeft((LEFT) setByMap(analyzer.getLeft(), recordMap, analyzer.getLeftAnalyzer()));
		result.setRight((RIGHT) setByMap(analyzer.getRight(), recordMap, analyzer.getRightAnalyzer()));
		return result;
	}
}
