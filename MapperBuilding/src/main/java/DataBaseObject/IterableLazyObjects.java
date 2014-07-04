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

public class IterableLazyObjects<T> implements Iterable<T>{
	
	private PreparedStatement cmd;
	private ConnectionStrategy connStr;
	private List<ColumnInfo> columnsInfo;
	private Constructor<T> constr;

	public IterableLazyObjects(PreparedStatement cmd,ConnectionStrategy connStr,Constructor<T> constr, List<ColumnInfo> columnsInfo) {
		this.cmd = cmd;
		this.connStr = connStr;
		this.constr = constr;
		this.columnsInfo = columnsInfo;
	}
	
	@Override
	public Iterator<T> iterator() {
		ResultSet rs;
		try {
			connStr.beginTransaction(true);
			rs =  cmd.executeQuery();
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
						int auxIndex = 0, columnsSize = columnsInfo.size();
						Object[] objs = new Object[columnsSize];
						while (auxIndex < columnsSize-1) {
							objs[auxIndex] = rs.getObject(columnsInfo.get(
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

}
