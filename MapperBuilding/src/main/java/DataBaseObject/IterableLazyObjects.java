package DataBaseObject;

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
	private Class<T> klass;

	public IterableLazyObjects(PreparedStatement cmd,ConnectionStrategy connStr,Class<T> klass, List<ColumnInfo> columnsInfo) {
		this.cmd = cmd;
		this.connStr = connStr;
		this.klass = klass;
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
						next = klass.newInstance();
						int auxIndex = 0;
						
						for(ColumnInfo c:columnsInfo){
							c.set(next, rs.getObject(columnsInfo.get(
									auxIndex++).getName()));
						}
						
						containsNext = true;
						return true;
					}
					return false;
				} catch (IllegalArgumentException
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
