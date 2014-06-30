package MapperBuilder;

import java.lang.reflect.Constructor;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import DataBaseObject.SQLIterableImpl;
import Strategy.Connections.ConnectionStrategy;

public class DataMapperSQL<T> implements DataMapper<T> {

	private ConnectionStrategy connStr;
	private String nameTable;
	private Class<T> klass;
	private List<ColumnInfo> columnsInfo;
	private ColumnInfo primaryKey;

	public DataMapperSQL(String nameTable, ConnectionStrategy conn,
			Class<T> klass, List<ColumnInfo> nameColumns, ColumnInfo primaryKey) {
		this.nameTable = nameTable;
		this.connStr = conn;
		this.klass = klass;
		this.columnsInfo = nameColumns;
		this.primaryKey = primaryKey;
	}

	@Override
	public SQLIterableImpl<T> getAll() {
		Stream<Constructor<?>> lambda = Arrays.stream(klass.getConstructors())
				.filter(x -> x.getParameterCount() > 0);

		if (lambda.count() > 1)
			throw new RuntimeException();

		Constructor<T> constr = (Constructor<T>) lambda.findFirst().get();
		return new SQLIterableImpl<T>("SELECT * FROM " + nameTable, connStr,
				constr, columnsInfo);
	}

	@Override
	public void update(T val) {
		try {
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

			fillPrepareStament(cmd,nameTable,primaryKey.getName(), primaryKey.get(val));

			cmd.executeUpdate();
			connStr.commit();
		} catch (SQLException e) {
			connStr.rollback();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(T val) {
		try {
			PreparedStatement cmd = connStr.getConnection().prepareStatement(
					"DELETE FROM ? WHERE ?=?");
			
			fillPrepareStament(cmd,nameTable,primaryKey.getName(), primaryKey.get(val));

			cmd.executeUpdate();
			connStr.commit();
		} catch (SQLException e) {
			connStr.rollback();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void insert(T val) {
		try {
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

			fillPrepareStament(cmd,nameTable);

			cmd.executeUpdate();
			connStr.commit();
		} catch (SQLException e) {
			connStr.rollback();
			throw new RuntimeException(e);
		}
	}
	
	private void fillPrepareStament(PreparedStatement prepareS,Object... argsToPrepareStament) {
		if (argsToPrepareStament != null || argsToPrepareStament.length > 0) {
			for (int i = 0; i < argsToPrepareStament.length; ++i) {
				try {
					prepareS.setObject(i+1, argsToPrepareStament[i]);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

}
