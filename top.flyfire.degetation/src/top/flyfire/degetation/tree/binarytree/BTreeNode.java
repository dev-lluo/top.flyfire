package top.flyfire.degetation.tree.binarytree;

public class BTreeNode<O extends Comparable<O>> implements BinaryTreeNode<O> {
	
	protected O owner;
	
	protected BinaryTreeNode<O> parent;
	
	protected BinaryTreeNode<O> left;
	
	protected BinaryTreeNode<O> right;
	
	protected int count;

	@Override
	public O owner() {
		// TODO Auto-generated method stub
		return owner;
	}

	@Override
	public BinaryTreeNode<O> parent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public BinaryTreeNode<O> left() {
		// TODO Auto-generated method stub
		return left;
	}

	@Override
	public BinaryTreeNode<O> right() {
		// TODO Auto-generated method stub
		return right;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return count;
	}
	@Override
	public int countIncrement() {
		// TODO Auto-generated method stub
		return ++count;
	}

	@Override
	public void left(BinaryTreeNode<O> node) {
		// TODO Auto-generated method stub
		this.left = node;
	}

	@Override
	public void right(BinaryTreeNode<O> node) {
		// TODO Auto-generated method stub
		this.right = node;
	}

	@Override
	public void owner(O o) {
		// TODO Auto-generated method stub
		this.owner = o;
	}
	
	public BTreeNode(BinaryTreeNode<O> parent){
		this.parent = parent;
	}
	
	@Override
	public String toString(){
		return "["+this.owner+"("+this.count+")]";
	}
	
}
