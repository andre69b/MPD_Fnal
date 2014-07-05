package DataBaseObject;

public interface SQLExtensionMethods<T> extends SQLIterable<T> {
	SQLExtensionMethods<T> where(String clause);

	SQLExtensionMethods<T> bind(Object... args);
}
