package DataBaseObject;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import MapperBuilder.ColumnInfo;
import Strategy.Connections.ConnectionStrategy;

public class SQLIterableAfterImpl<T> extends SQLIterableImpl<T> implements SQLExtensionMethods<T>{

	public SQLIterableAfterImpl(String sqlStatement,
			ConnectionStrategy connStr, Constructor<T> constr,
			List<ColumnInfo> columnsInfo,List<Object> args) {
		super(sqlStatement, connStr, constr, columnsInfo);
		argsToBind = args;
	}
	
	@Override
	public SQLExtensionMethods<T> where(String clause) {
		StringBuilder str = new StringBuilder(sqlStatement.toString());
		str.append(" AND ");
		str.append(clause);
		return new SQLIterableAfterImpl<T>(str.toString(), connStr, constr, columnInfos,argsToBind);
	}


	@Override
	public SQLExtensionMethods<T> bind(Object... args) {
		List<Object> list = new ArrayList<Object>(argsToBind.size()+args.length);
		list.addAll(argsToBind);
		for(Object o:args)
			list.add(o);
		return new SQLIterableAfterImpl<T>(sqlStatement.toString(), connStr, constr, columnInfos,list);
	}
}
