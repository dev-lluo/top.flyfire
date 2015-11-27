package top.flyfire.degetation.tree.binarytree;

import java.util.List;

import top.flyfire.degetation.tree.Tree;

/**
 * (01) ������ڵ�����������գ��������������н���ֵ��С����ĸ����ֵ��
 * (02) ����ڵ�����������գ��������������н���ֵ�������ĸ����ֵ��
 * (03) ����ڵ����������Ҳ�ֱ�Ϊ�����������
 * (04) û�м�ֵ��ȵĽڵ㣨no duplicate nodes����
 * @author h
 *
 * @param <N> �ڵ�����
 * @param <O> �ڵ�ʵ�ʶ�������
 */
@SuppressWarnings("rawtypes")
public interface BinaryTree<O extends Comparable<O>>  extends Tree<BinaryTreeNode,O> {
	BinaryTreeNode max();
	BinaryTreeNode min();
	void insert(O o);
	List<O> sortAsList();
	BinaryTreeNode buildTree(List<O> oArr);
}
