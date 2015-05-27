package edu.jiangnan.dm.util;

public interface Callback {
	void onBefore();

	boolean onRun();

	void onAfter(boolean b);
}
