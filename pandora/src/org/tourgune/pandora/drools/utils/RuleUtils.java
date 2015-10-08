package org.tourgune.pandora.drools.utils;
 
import java.util.concurrent.BlockingQueue;

import org.tourgune.pandora.bean.Situation;

public class RuleUtils {
	
    private static BlockingQueue sharedQueue;
 
	public RuleUtils(BlockingQueue sharedQueue){
		this.sharedQueue = sharedQueue; 
	}
	
	
	public static void putInQueue(Situation s){ 
		 try { 
			sharedQueue.put(s);
		} catch (InterruptedException e) { 
			e.printStackTrace();
		} 
	}
 

}
