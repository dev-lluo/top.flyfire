package top.flyfire.degetation.net.tcp.c2c;


import java.util.List;

import top.flyfire.degetation.KVal;
import top.flyfire.degetation.buffer.BufferEngine;
import top.flyfire.degetation.tree.binarytree.HashBTreeGroup;

public class IdToBuffer implements KVal<String, BufferEngine<byte[]>> ,Comparable<IdToBuffer>{
	
	private String id;
	
	private BufferEngine<byte[]> engine;

	@Override
	public String key() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public BufferEngine<byte[]> val() {
		// TODO Auto-generated method stub
		return this.engine;
	}
	
	
	public IdToBuffer(String id,BufferEngine<byte[]> engine){
		this.id = id;
		this.engine = engine;
	}

	@Override
	public int compareTo(IdToBuffer o) {
		// TODO Auto-generated method stub
		return o.id.hashCode()-this.id.hashCode();
	}
	
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.id.hashCode();
	}



	public static class Store{
//		private final static Dictionary<String,IdToBuffer> INPUT = new Hashtable<String,IdToBuffer>();
		
//		private final static Dictionary<String,IdToBuffer> OUTPUT = new Hashtable<String,IdToBuffer>();
		
		private final static HashBTreeGroup<IdToBuffer> INPUT = new HashBTreeGroup<IdToBuffer>();
		
		private final static HashBTreeGroup<IdToBuffer> OUTPUT = new HashBTreeGroup<IdToBuffer>();
		
		public static void putInput(IdToBuffer idToBuffer){
			INPUT.put(idToBuffer);
		}
		
		public static IdToBuffer findInput(String id){
			//return INPUT.get(id);
			return INPUT.get(new IdToBuffer(id, null));
		}
		
		public static List<IdToBuffer> allInput(){
			return INPUT.all();
		}
		
		public static void putOutput(IdToBuffer idToBuffer){
			OUTPUT.put(idToBuffer);
		}
		
		public static IdToBuffer findOutput(String id){
			//return OUTPUT.get(id);
			return OUTPUT.get(new IdToBuffer(id, null));
			
		}
		
		public static List<IdToBuffer> allOutput(){
			return OUTPUT.all();
		}
	}

	


}
