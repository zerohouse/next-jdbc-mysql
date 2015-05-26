package next.jdbc.mysql.maker;

import java.util.ArrayList;
import java.util.List;

public class SqlFunction {
	List<CreateColumn> items = new ArrayList<CreateColumn>();

	public void add(CreateColumn fm) {
		items.add(fm);
	}

	public String getFunctionString(String function) {
		if (items.size() == 0)
			return "";
		String result = "";
		for (int i = 0; i < items.size(); i++) {
			result += items.get(i).getColumnName() + ", ";
		}
		if (result.length() > 2)
			result = result.substring(0, result.length() - 2);
		return function + "(" + result + ")";

	}

	public boolean hasItem() {
		return items.size() != 0;
	}
}
