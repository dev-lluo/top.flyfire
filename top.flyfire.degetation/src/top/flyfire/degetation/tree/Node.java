package top.flyfire.degetation.tree;

@SuppressWarnings("rawtypes")
public interface Node<O,P extends Node> {
	O owner();
	void owner(O o);
	P parent();
}
