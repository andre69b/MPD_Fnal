package MapperBuilder;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import DataBaseObject.SQLIterableImpl;
import Exception.MyRuntimeException;
import Strategy.Connections.ConnectionStrategy;

public class DataMapperSQL<T> implements DataMapper<T> {

	private ConnectionStrategy connStr;
	private String nameTable;
	private Class<T> klass;
	private List<ColumnInfo> columnsInfo;
	private Map<String, List<ColumnInfo>> mapColumns;
	private ColumnInfo primaryKey;

	public DataMapperSQL(String nameTable, ConnectionStrategy conn,
			Class<T> klass, Map<String, List<ColumnInfo>> mapColumns) {
		this.nameTable = nameTable;
		this.connStr = conn;
		this.klass = klass;
		this.columnsInfo = mapColumns.get("ColumnInfo");
		this.mapColumns = mapColumns;
		this.primaryKey = mapColumns.get("primaryKey").get(0);
	}

	@Override
	public SQLIterableImpl<T> getAll() {
		return new SQLIterableImpl<T>("SELECT * FROM " + nameTable, connStr,
				klass, mapColumns);
	}

	@Override
	public T getById(Object value, String attributeName) {
		return new SQLIterableImpl<T>("SELECT * FROM " + nameTable, connStr,
				klass, mapColumns).where(attributeName + "= ?").bind(value)
				.iterator().next();
	}

	@Override
	public void update(T val) {
		try {
			connStr.beginConnection(false);
			StringBuilder prepareStamentString = new StringBuilder("UPDATE "
					+ nameTable + " SET ");
			int indexAux = 1;
			try {
				while (indexAux < columnsInfo.size()) {
					prepareStamentString.append(columnsInfo.get(indexAux)
							.getName()
							+ " = "
							+ klass.getField(columnsInfo.get(indexAux)
									.getName()).get(val)
							+ " ");
					if (indexAux != columnsInfo.size() - 1)
						prepareStamentString.append(",");
					indexAux++;
				}

				prepareStamentString.append("WHERE " + primaryKey.getName()
						+ "=" + klass.getField(primaryKey.getName()).get(val));
			} catch (IllegalArgumentException | IllegalAccessException
					| NoSuchFieldException | SecurityException e) {
				throw new MyRuntimeException(e);
			}
			PreparedStatement cmd = connStr.getConnection().prepareStatement(
					prepareStamentString.toString());

			cmd.executeUpdate();
			connStr.commit();
		} catch (SQLException e) {
			connStr.rollback();
			throw new MyRuntimeException(e);
		}
	}

	@Override
	public void delete(T val) {
		try {
			connStr.beginConnection(false);
			PreparedStatement cmd = null;
			try {
				cmd = connStr.getConnection()
						.prepareStatement(
								"DELETE FROM "
										+ nameTable
										+ " WHERE "
										+ primaryKey.getName()
										+ "="
										+ klass.getField(primaryKey.getName())
												.get(val));
			} catch (IllegalArgumentException | IllegalAccessException
					| NoSuchFieldException | SecurityException e) {
				throw new MyRuntimeException(e);
			}

			cmd.executeUpdate();
			connStr.commit();
		} catch (SQLException e) {
			connStr.rollback();
			throw new MyRuntimeException(e);
		}
	}

	@Override
	public int insert(T val) {
		try {
			connStr.beginConnection(false);
			StringBuilder prepareStamentString = new StringBuilder(
					"INSERT INTO " + nameTable + " VALUES(");
			int indexAux = 1;
			while (indexAux < columnsInfo.size()) {
				try {
					prepareStamentString.append(klass.getField(
							columnsInfo.get(indexAux).getName()).get(val));
				} catch (IllegalArgumentException | IllegalAccessException
						| NoSuchFieldException | SecurityException e) {
					throw new MyRuntimeException(e);
				}
				if (indexAux != columnsInfo.size() - 1)
					prepareStamentString.append(",");
				indexAux++;
			}
			prepareStamentString.append(")");
			PreparedStatement cmd = connStr.getConnection().prepareStatement(
					prepareStamentString.toString());

			cmd.executeUpdate();

			String idSQL = "SELECT MAX(" + primaryKey.getName()
					+ ") as idInserted FROM " + nameTable;

			cmd = connStr.getConnection().prepareStatement(idSQL);

			ResultSet rs = cmd.executeQuery();
			int idInserted = 0;
			if (rs.next())
				idInserted = rs.getInt("idInserted");

			connStr.commit();
			return idInserted;
		} catch (SQLException e) {
			connStr.rollback();
			throw new MyRuntimeException(e);
		}
	}
}
