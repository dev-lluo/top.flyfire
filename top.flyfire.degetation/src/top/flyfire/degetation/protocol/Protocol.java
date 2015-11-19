package top.flyfire.degetation.protocol;

public interface Protocol {
	int DEFAULT_BEWRITE_START = 0;
	int bewriteStart();
	int DEFAULT_BEWRITE_STEP = 6;
	int bewriteStep();
	byte[] protocol(byte[] by);
	byte[] content(byte[] by);
	byte protocol(byte[] by,int index);
	
}
