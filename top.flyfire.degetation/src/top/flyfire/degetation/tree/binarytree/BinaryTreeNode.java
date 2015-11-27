package top.flyfire.degetation.tree.binarytree;

import top.flyfire.degetation.tree.Node;

@SuppressWarnings("rawtypes")
public interface BinaryTreeNode<O extends Comparable<O>> extends Node<O, BinaryTreeNode>{
	BinaryTreeNode<O> left();
	void left(BinaryTreeNode<O> node);
	BinaryTreeNode<O> right();
	void right(BinaryTreeNode<O> node);
	int count();
	int countIncrement();
}
