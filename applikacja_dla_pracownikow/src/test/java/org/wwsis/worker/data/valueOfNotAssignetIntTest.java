package org.wwsis.worker.data;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class valueOfNotAssignetIntTest {

	@Test
	public void test() {
		Worker nw = new Worker();
		assertEquals(nw.getNumOfFailedLogingAttempts(),0);
	}

}
