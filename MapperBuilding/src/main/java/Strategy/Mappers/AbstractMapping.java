package Strategy.Mappers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import DataBaseObject.EDTable;
import DataBaseObject.ForeignKey;
import DataBaseObject.PrimaryKey;
import Exception.MyRuntimeException;
import MapperBuilder.ColumnInfo;
import MapperBuilder.DataMapper;
import MapperBuilder.DataMapperSQL;
import MapperBuilder.ForeignkeyObject;
import Strategy.Connections.ConnectionStrategy;

public abstract class AbstractMapping implements MappingStrategy {
	private final int LEVEL = 1;
	private int currentLevel = 0;
	private ConnectionStrategy connStr;

	@Override
	public <T> DataMapper<T> build(Class<T> klass, ConnectionStrategy connStr) {

		this.connStr = connStr;
		EDTable Table = (EDTable) klass.getAnnotation(EDTable.class);
		if (Table == null)
			throw new MyRuntimeException(new UnsupportedOperationException(
					"All ED must have an EDTableAttribute with its TableName!"));

		Map<String, List<ColumnInfo>> mapColumns = getColumnInfoAndFillPrimaryKey(klass);

		return new DataMapperSQL<T>(Table.TableName(), connStr, klass,
				mapColumns);
	}

	protected abstract <T> Member[] getMembers(Class<T> klass);

	protected abstract ColumnInfo createColumnInfo(Member member);

	protected abstract Annotation[] getMemberAnnotations(Member member);

	protected <T> Map<String, List<ColumnInfo>> getColumnInfoAndFillPrimaryKey(
			Class<T> klass) {
		Map<String, List<ColumnInfo>> map = new HashMap<String, List<ColumnInfo>>();
		Member[] members = getMembers(klass);
		List<ColumnInfo> ret = new ArrayList<ColumnInfo>(members.length);
		List<ColumnInfo> ret2 = new LinkedList<ColumnInfo>();
		ColumnInfo ci = null;
		for (Member member : members) {
			Annotation[] annotations = getMemberAnnotations(member);
			if (annotations.length > 1)
				throw new MyRuntimeException(
						"Not supported more than one annotation per member");

			ci = createColumnInfo(member);
			if (ci == null)
				continue;

			if (annotations.length == 0) {
				ret.add(ci);
				continue;
			}
			Annotation a = annotations[0];
			if ((map.get("primaryKey")) == null && a instanceof PrimaryKey) {
				ret.add(ci);
				map.put("primaryKey", Arrays.asList(ci));
				// primaryKey = ci;

				continue;
			}
			if (currentLevel < LEVEL && a instanceof ForeignKey) {
				ForeignKey v = (ForeignKey) a;
				Class<?> k = v.Type();
				++currentLevel;
				ret2.add(new ForeignkeyObject<>(k, ((EDTable) k
						.getAnnotation(EDTable.class)).TableName(),v.KeyName(),
						getColumnInfoAndFillPrimaryKey(k), v.Association(), v
								.AttributeName(), ci, connStr));
				--currentLevel;
			}
		}

		map.put("ColumnInfo", ret);
		map.put("ForeignKeyObject", ret2);

		return map;
	}
}
