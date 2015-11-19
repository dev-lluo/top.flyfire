package top.flyfire.degetation.level;

public interface Level {
	int HADOOP = Integer.MAX_VALUE;
	int FLYFIRE = 0;
	int MOUSE = Integer.MIN_VALUE;
	int DEFAULT = FLYFIRE;
	
	int HIGHER = 1;
	int EQUALLY = 0;
	int LOWER = -1;
	
	int level();
	
	public static class Compare{
		public static int to(Level a,Level b){
			int temp = a.level() - b.level();
			return ( temp > 0 ? HIGHER : (temp < 0 ? LOWER : EQUALLY));
		}
	}
}
