package DataBaseObject;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SQLIterableImpl<T> implements SQLIterable<T> {
	
	private List<T> list;
	private int count;
	
	public SQLIterableImpl() {
		list = new LinkedList<T>();
		count=0;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SQLIterable<T> where(String clause) {
		return null;
	}

	@Override
	public int count() {
		return count;
	}
	
	public void add(T t){
		list.add(t);
		count++;
	}

}
