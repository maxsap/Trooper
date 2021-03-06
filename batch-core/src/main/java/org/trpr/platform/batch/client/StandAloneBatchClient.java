/*
 * Copyright 2012-2015, the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.trpr.platform.batch.client;

import org.trpr.platform.batch.impl.spring.jmx.JMXJobUtils;
import org.trpr.platform.core.PlatformException;
import org.trpr.platform.core.impl.logging.LogFactory;
import org.trpr.platform.core.spi.logging.Logger;
import org.trpr.platform.runtime.impl.bootstrap.spring.Bootstrap;

/**
 * The <code> StandAloneBatchClient </code> may be used for testing batch jobs. It expects the following arguments:
 * <pre>
 * 1. Bootstrap config location  
 * 2. Batch Job Name e.g. greetingJob
 * <pre>
 * 
 * Note that this client executes the mentioned job only once and also ignores the trigger settings. Also initiates Trooper runtime shutdown after execution, 
 * irrespective of status of outcome - success (or) failure.
 * 
 * @author Regunath B
 * @version 1.0, 04/10/2012
 * 
 */
public class StandAloneBatchClient {

	/** The Log instance for this class */
	private static final Logger LOGGER = LogFactory.getLogger(StandAloneBatchClient.class);
	
	/**
	 * Main method test the service in standalone nature. It expects the following arguments
     * 
	 * @param args args[0] - Bootstrap configuration path 
	 *             args[1] - Batch Job Name
	 *             
	 * @throws PlatformException 
	 */
	public static void main(String[] args) throws PlatformException {

		//validate the batch information
		if(args.length < 2) {
			LOGGER.error("Batch information is not sufficient. bootstrap config path, batch job name are required parameters");
			throw new PlatformException("Batch Job information is not sufficient");
		}
	
		//	get Job information
		String bootstrapConfigPath = args[0];
		String jobName = args[1];
		
		// boot the Trooper runtime
		Bootstrap bootstrap = new Bootstrap();
		
		// we use System.out as the logging would not have been configured until the end of bootstrap
		System.out.println(("Bootstrap Config Path: " + bootstrapConfigPath));
		bootstrap.init(bootstrapConfigPath);
		
		try { 
			JMXJobUtils jmxJobUtils = new JMXJobUtils();
			LOGGER.info("Invoking Trooper Job execution for : " + jobName);
			jmxJobUtils.runJob(jobName);
			String status = jmxJobUtils.waitForJobExecution(jobName, 1000);
			LOGGER.info("Going to terminate Trooper as Job execution has completed with status : " + status);
		} catch(Throwable e) {
			LOGGER.error("Exception running the job", e);
			throw new PlatformException(e);
		} finally {
			try {
				bootstrap.destroy();
			} catch (Exception e) {
				LOGGER.error("Exception thrown while destroying Platform ", e);
				throw new PlatformException(e);
			}
		}
	}
	
}
