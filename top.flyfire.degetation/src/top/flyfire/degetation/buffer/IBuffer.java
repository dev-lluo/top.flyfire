package top.flyfire.degetation.buffer;

public interface IBuffer<T> {
	T head();
	void head(T t);
	T unLoad();
	void load(T t);
	boolean isEmpty();
	void clear();
}
