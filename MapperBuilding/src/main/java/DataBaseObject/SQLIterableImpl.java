package DataBaseObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Exception.MyRuntimeException;
import MapperBuilder.ColumnInfo;
import Strategy.Connections.ConnectionStrategy;

public class SQLIterableImpl<T> implements SQLIterable<T> {

	protected StringBuilder sqlStatement;
	protected ConnectionStrategy connStr;
	protected Class<T> klass;
	protected List<ColumnInfo> columnInfos;
	private PreparedStatement cmd;
	private boolean iteratorIsValid;
	protected List<Object> argsToBind;

	public SQLIterableImpl(String sqlStatement, ConnectionStrategy connStr,
			Class<T> klass, List<ColumnInfo> columnsInfo) {
		this.sqlStatement = new StringBuilder(sqlStatement);
		this.connStr = connStr;
		this.klass = klass;
		this.columnInfos = columnsInfo;
		iteratorIsValid = true;
		argsToBind = new ArrayList<Object>();
	}

	@Override
	public SQLExtensionMethods<T> where(String clause) {

		StringBuilder str = new StringBuilder(sqlStatement.toString());
		str.append(" WHERE ");
		str.append(clause);
		return new SQLIterableAfterImpl<T>(str.toString(), connStr, klass,
				columnInfos, argsToBind);
	}

	@Override
	public int count() {
		iteratorIsValid = false;
		int returnElements = -1;
		try {
			connStr.beginTransaction(true);
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
		try {
			cmd = connStr.getConnection().prepareStatement(
					sqlStatement.toString());
		} catch (SQLException e) {
			throw new MyRuntimeException(e);
		}
		fillArgsToBind(cmd);
		return new IterableLazyObjects<T>(cmd, connStr, klass, columnInfos)
				.iterator();
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