package top.flyfire.degetation.tree;

public interface Tree<N,O> {
	N root();
	int deep();
	int size();
	N find(O o);
	N buildTree(O[] oArr);
}
