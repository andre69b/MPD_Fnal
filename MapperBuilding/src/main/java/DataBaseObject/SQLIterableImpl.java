package DataBaseObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import Exception.MyRuntimeException;
import MapperBuilder.ColumnInfo;
import MapperBuilder.ForeignkeyObject;
import Strategy.Connections.ConnectionStrategy;

public class SQLIterableImpl<T> implements SQLIterable<T> {

	protected StringBuilder sqlStatement;
	protected ConnectionStrategy connStr;
	protected Class<T> klass;
	protected Map<String, List<ColumnInfo>> mapColumns;
	private PreparedStatement cmd;
	private boolean iteratorIsValid;
	protected List<Object> argsToBind;

	public SQLIterableImpl(String sqlStatement, ConnectionStrategy connStr,
			Class<T> klass, Map<String, List<ColumnInfo>> columnsInfo) {
		this.sqlStatement = new StringBuilder(sqlStatement);
		this.connStr = connStr;
		this.klass = klass;
		this.mapColumns = columnsInfo;
		iteratorIsValid = true;
		argsToBind = new ArrayList<Object>();
	}

	@Override
	public SQLExtensionMethods<T> where(String clause) {

		StringBuilder str = new StringBuilder(sqlStatement.toString());
		str.append(" WHERE ");
		str.append(clause);
		return new SQLIterableAfterImpl<T>(str.toString(), connStr, klass,
				mapColumns, argsToBind);
	}

	@Override
	public int count() {
		iteratorIsValid = false;
		int returnElements = -1;
		try {
			connStr.beginConnection(true);
			String statementWithCount = sqlStatement.toString().replace("*",
					"COUNT(*) as count");
			cmd = connStr.getConnection().prepareStatement(statementWithCount);
			fillArgsToBind(cmd);
			ResultSet rs = cmd.executeQuery();
			if (rs.next())
				returnElements = rs.getInt("count");
		} catch (Exception e) {
			throw new MyRuntimeException(e);
		}
		return returnElements;
	}

	@Override
	public void close() throws Exception {
		cmd.close();
	}

	@Override
	public Iterator<T> iterator() {
		if (!iteratorIsValid)
			throw new MyRuntimeException(new IllegalAccessException());

		ResultSet rs;
		try {
			connStr.beginConnection(true);
			PreparedStatement cmd = connStr.getConnection().prepareStatement(
					sqlStatement.toString());
			fillArgsToBind(cmd);
			rs = cmd.executeQuery();
		} catch (SQLException e) {
			throw new MyRuntimeException(e);
		}
		return new Iterator<T>() {
			T next;
			boolean containsNext = false;
			List<ColumnInfo> foreignKeyObjects;
			List<ColumnInfo> columnsInfo;

			@Override
			public boolean hasNext() {
				try {
					if (containsNext)
						return true;
					if (rs.next()) {
						columnsInfo = mapColumns.get("ColumnInfo");
						foreignKeyObjects = mapColumns.get("ForeignKeyObject");
						next = klass.newInstance();
						int auxIndex = 0;

						for (ColumnInfo c : columnsInfo) {
							String s = columnsInfo.get(auxIndex++).getName();
							Object x = rs.getObject(s);
							if (x != null)
								c.set(next, x);
						}

						if (foreignKeyObjects != null) {
							for (ColumnInfo c : foreignKeyObjects) {
								@SuppressWarnings("rawtypes")
								ForeignkeyObject fO = (ForeignkeyObject) c;
								Object o = columnsInfo
										.stream()
										.filter(x -> x.getName().equals(
												fO.attributeName)).findFirst()
										.get().get(next);
								if (o != null)
									c.set(next, o);
							}
						}
						containsNext = true;
						return true;
					}
					return false;
				} catch (IllegalArgumentException | IllegalAccessException
						| SQLException | InstantiationException e) {
					throw new MyRuntimeException(e);
				}
			}

			@Override
			public T next() {
				if (containsNext || hasNext()) {
					containsNext = false;
					return next;
				}
				throw new MyRuntimeException(new NoSuchElementException());
			}

		};
	}

	private void fillArgsToBind(PreparedStatement prepareS) {
		if (argsToBind != null && argsToBind.size() > 0) {
			for (int i = 0; i < argsToBind.size(); ++i) {
				try {
					prepareS.setObject(i + 1, argsToBind.get(i));
				} catch (SQLException e) {
					throw new MyRuntimeException(e);
				}
			}
		}
	}
}