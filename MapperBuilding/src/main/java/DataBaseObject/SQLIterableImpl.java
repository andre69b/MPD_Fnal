package DataBaseObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import MapperBuilder.ColumnInfo;
import Strategy.Connections.ConnectionStrategy;

public class SQLIterableImpl<T> implements SQLIterable<T> {

	private StringBuilder sqlStatement;
	private ConnectionStrategy connStr;
	private Constructor<T> constr;
	private List<ColumnInfo> columnInfos;
	private boolean firstWhereDone;
	private PreparedStatement cmd;
	private boolean iteratorIsValid;
	private Object[] argsToBind;

	public SQLIterableImpl(String sqlStatement, ConnectionStrategy connStr,
			Constructor<T> constr, List<ColumnInfo> columnsInfo) {
		this.sqlStatement = new StringBuilder(sqlStatement);
		this.connStr = connStr;
		this.constr = constr;
		this.columnInfos = columnsInfo;
		iteratorIsValid = true;
	}
	
	private SQLIterableImpl(String sqlStatement, ConnectionStrategy connStr,
			Constructor<T> constr, List<ColumnInfo> columnsInfo,Object[] argsToBind,boolean interValid) {
		this.sqlStatement = new StringBuilder(sqlStatement);
		this.connStr = connStr;
		this.constr = constr;
		this.columnInfos = columnsInfo;
		iteratorIsValid = interValid;
		this.argsToBind = argsToBind;
		
	}

	@Override
	public SQLIterable<T> where(String clause) {
		if (firstWhereDone)
			sqlStatement.append(" AND ");
		else
			sqlStatement.append(" WHERE ");
			
		sqlStatement.append(clause);
		return new SQLIterableImpl<T>(sqlStatement.toString(), connStr, constr, columnInfos,argsToBind,iteratorIsValid);
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
			throw new RuntimeException(e);
		}
		return returnElements;
	}

	@Override
	public SQLIterable<T> bind(Object... args) {
		argsToBind = args;
		return new SQLIterableImpl<T>(sqlStatement.toString(), connStr, constr, columnInfos,argsToBind,iteratorIsValid);
	}

	@Override
	public void close() throws Exception {
		cmd.close();
	}

	@Override
	public Iterator<T> iterator() {
		if (!iteratorIsValid)
			throw new RuntimeException(new IllegalAccessException());
		ResultSet rs;
		try {
			connStr.beginTransaction(true);
			cmd = connStr.getConnection().prepareStatement(
					sqlStatement.toString());
			fillArgsToBind(cmd);
			rs = cmd.executeQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return new Iterator<T>() {
			T next;
			boolean containsNext = false;
			

			@Override
			public boolean hasNext() {
				try {
					if (containsNext)
						return true;
					if (rs.next()) {
						int auxIndex = 0, columnsSize = columnInfos.size();
						Object[] objs = new Object[columnsSize];
						while (auxIndex < columnsSize) {
							objs[auxIndex] = rs.getObject(columnInfos.get(
									auxIndex).getName());
							auxIndex++;
						}
						next = constr.newInstance(objs);
						containsNext = true;
						return true;
					}
					return false;
				} catch (InvocationTargetException | IllegalArgumentException
						| IllegalAccessException | SQLException
						| InstantiationException e) {
					throw new RuntimeException(e);
				}
			}

			@Override
			public T next() {
				if (containsNext || hasNext()) {
					containsNext = false;
					return next;
				}
				throw new NoSuchElementException();
			}

		};
	}

	private void fillArgsToBind(PreparedStatement prepareS) {
		if (argsToBind != null && argsToBind.length > 0) {
			for (int i = 0; i < argsToBind.length; ++i) {
				try {
					prepareS.setObject(i+1, argsToBind[i]);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}