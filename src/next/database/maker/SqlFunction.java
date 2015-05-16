package next.database.maker;

import java.util.ArrayList;
import java.util.List;

import next.database.sql.SqlFieldNormal;

public class SqlFunction {
	List<SqlFieldNormal> items = new ArrayList<SqlFieldNormal>();

	public void add(SqlFieldNormal fm) {
		items.add(fm);
	}

	public String getFunctionString(String function) {
		if (items.size() == 0)
			return "";
		String result = "";
		for (int i = 0; i < items.size(); i++) {
			result += items.get(i).getWrappedColumnName() + ", ";
		}
		if (result.length() > 2)
			result = result.substring(0, result.length() - 2);
		return function + "(" + result + ")";

	}

	public boolean hasItem() {
		return items.size() != 0;
	}
}
