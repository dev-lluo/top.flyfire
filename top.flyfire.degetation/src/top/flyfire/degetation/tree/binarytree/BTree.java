package top.flyfire.degetation.tree.binarytree;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class BTree<O extends Comparable<O>> implements BinaryTree<O> {

	protected BinaryTreeNode<O> root = new BTreeNode<O>(null);
	
	@Override
	public BinaryTreeNode root() {
		// TODO Auto-generated method stub
		return root;
	}

	@Override
	public int deep() {
		// TODO Auto-generated method stub
		return deep(root);
	}
	
	protected int deep(BinaryTreeNode<O> node){
		if(node==null){
			return 0;
		}else{
			int l = deep(node.left());
			int r = deep(node.right());
			return (l>r)?(l+1):(r+1);
		}
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size(root);
	}
	
	protected int size(BinaryTreeNode<O> node) {
		if(node == null){
            return 0;
        }else{
            return 1+ size(node.left())+size(node.right());
        }
	}

	@Override
	public BinaryTreeNode find(O o) {
		// TODO Auto-generated method stub
			return find(o,root);
	}
	
	protected BinaryTreeNode find(O o,BinaryTreeNode<O> node) {
		if(o==null||root==null){
			return null;
		}if(o.compareTo(node.owner())==0){
			return node;
		}else if(o.compareTo(node.owner())>0){
			return find(o,node.right());
		}else if(o.compareTo(node.owner())<0){
			return find(o,node.left());
		}else{
			return null;
		}
	}

	@Override
	public BinaryTreeNode buildTree(O[] oArr) {
		// TODO Auto-generated method stub
		for(int i = 0;i<oArr.length;i++){
			insert(oArr[i]);
		}
		return root;
	}
	
	public void insert(O o){
		insert(o,root);
	}
	
	protected void insert(O o,BinaryTreeNode<O> node) {
		if(o==null&&node==null){
			return;
		}else if(node.owner()==null){
			node.owner(o);
		}else{
			if(o.compareTo(node.owner())==0){
				node.countIncrement();
			}else if(o.compareTo(node.owner())<0){
				if(node.left()==null)node.left(new BTreeNode<O>(node));
				insert(o,node.left());
			}else if(o.compareTo(node.owner())>0){
				if(node.right()==null)node.right(new BTreeNode<O>(node));
				insert(o,node.right());
			}
		}
	}

	@Override
	public BinaryTreeNode max() {
		// TODO Auto-generated method stub
		return max(root);
	}
	
	protected BinaryTreeNode max(BinaryTreeNode<O> node) {
		if(node==null)return null;
		else if(node.right()!=null){
			return max(node.right());
		}else{
			return node;
		}
	}

	@Override
	public BinaryTreeNode min() {
		// TODO Auto-generated method stub
		return min(root);
	}
	
	public List<O> sortAsList(){
		List<O> oArr = new ArrayList<O>();
		sortAsList(root, oArr);
		return oArr;
	}
	
	protected void sortAsList(BinaryTreeNode<O> node,List<O> oArr) {
	    if(node != null) {
	    	sortAsList(node.left(),oArr);
	        oArr.add(node.owner());
	        sortAsList(node.right(),oArr);
	    }
	}

	
	
	
	protected BinaryTreeNode min(BinaryTreeNode<O> node) {
		if(node==null)return null;
		else if(node.left()!=null){
			return min(node.left());
		}else{
			return node;
		}
	}
	
	
	@Override
	public String toString(){
		int deep = deep();
		return toString(deep,root);
	}
	
	protected String toString(int deep,BinaryTreeNode<O> node) {
		return null;
	}

	@Override
	public BinaryTreeNode buildTree(List<O> oArr) {
		// TODO Auto-generated method stub
		for(int i = 0,len=oArr.size();i<len;i++){
			insert(oArr.get(i));
		}
		return root;
	}
	

}
