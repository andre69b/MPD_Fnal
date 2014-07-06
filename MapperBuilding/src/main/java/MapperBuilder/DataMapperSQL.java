package MapperBuilder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import DataBaseObject.SQLExtensionMethods;
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
			Class<T> klass, Map<String, List<ColumnInfo>> mapColumns,
			ColumnInfo primaryKey) {
		this.nameTable = nameTable;
		this.connStr = conn;
		this.klass = klass;
		this.columnsInfo = mapColumns.get("ColumnInfo");
		this.mapColumns = mapColumns;
		this.primaryKey = primaryKey;
	}

	@Override
	public SQLIterableImpl<T> getAll() {
		return new SQLIterableImpl<T>("SELECT * FROM " + nameTable, connStr,
				klass, mapColumns);
	}

	@Override
	public T getById(Object value,String attributeName) {
		return new SQLIterableImpl<T>("SELECT * FROM " + nameTable, connStr,
						klass, mapColumns).where(attributeName + "= ?")
						.bind(value).iterator().next();
	}

	@Override
	public void update(T val) {
		try {
			connStr.beginTransaction(false);
			StringBuilder prepareStamentString = new StringBuilder(
					"UPDATE ? SET ");
			int indexAux = 0;
			while (indexAux < columnsInfo.size()) {
				prepareStamentString.append(columnsInfo.get(indexAux).getName()
						+ " = " + columnsInfo.get(indexAux).get(val) + " ");
			}
			prepareStamentString.append("WHERE ?=?");
			PreparedStatement cmd = connStr.getConnection().prepareStatement(
					prepareStamentString.toString());

			fillPrepareStament(cmd, nameTable, primaryKey.getName(),
					primaryKey.get(val));

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
			connStr.beginTransaction(false);
			PreparedStatement cmd = connStr.getConnection().prepareStatement(
					"DELETE FROM ? WHERE ?=?");
			fillPrepareStament(cmd, nameTable, primaryKey.getName(),
					primaryKey.get(val));

			cmd.executeUpdate();
			connStr.commit();
		} catch (SQLException e) {
			connStr.rollback();
			throw new MyRuntimeException(e);
		}
	}

	@Override
	public void insert(T val) {
		try {
			connStr.beginTransaction(false);
			StringBuilder prepareStamentString = new StringBuilder(
					"INSERT INTO ? VALUES(");
			int indexAux = 0;
			while (indexAux < columnsInfo.size()) {
				prepareStamentString.append(columnsInfo.get(indexAux).get(val));
				if (indexAux != columnsInfo.size() - 1)
					prepareStamentString.append(",");
			}
			prepareStamentString.append(")");
			PreparedStatement cmd;

			cmd = connStr.getConnection().prepareStatement(
					prepareStamentString.toString());

			fillPrepareStament(cmd, nameTable);

			cmd.executeUpdate();
			connStr.commit();
		} catch (SQLException e) {
			connStr.rollback();
			throw new MyRuntimeException(e);
		}
	}

	private void fillPrepareStament(PreparedStatement prepareS,
			Object... argsToPrepareStament) {
		if (argsToPrepareStament != null && argsToPrepareStament.length > 0) {
			for (int i = 0; i < argsToPrepareStament.length; ++i) {
				try {
					prepareS.setObject(i + 1, argsToPrepareStament[i]);
				} catch (SQLException e) {
					throw new MyRuntimeException(e);
				}
			}
		}
	}
}
