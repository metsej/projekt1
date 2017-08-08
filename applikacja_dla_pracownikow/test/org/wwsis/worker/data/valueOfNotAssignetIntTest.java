package org.wwsis.worker.data;

import static org.junit.Assert.*;

import org.junit.Test;
import org.wwsis.worker.data.Worker;

public class valueOfNotAssignetIntTest {

	@Test
	public void test() {
		Worker nw = new Worker();
		assertEquals(nw.getNumOfFailedLogingAttempts(),0);
	}

}
