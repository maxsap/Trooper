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
package org.trpr.platform.batch.spi.spring.admin;

import java.util.Date;

import org.quartz.impl.SchedulerRepository;
import org.trpr.platform.runtime.spi.component.ComponentContainer;

/**
 * <code>JobService</code> interface is an extension of {@link JobService} that holds {@link SchedulerRepository} 
 * which will have access to the trigger information
 * 
 * @author devashishshankar
 * @version 1.0, 10 Jan 2013
 */
public interface JobService extends org.springframework.batch.admin.service.JobService {

	/**
	 * Returns the CronExpression based on the jobName
	 * @param jobName The name of the job
	 * @return CronExpression in a String
	 */
	public String getCronExpression(String jobName);

	/**
	 * Returns the NextFireDate based on the jobName
	 * @param jobName The name of the job
	 * @return NextFireDate in a Date
	 */
	public Date getNextFireDate(String jobName);

	/** 	
	 * Gets the {@link ComponentContainer} that loaded this JobService
	 * @return the ComponentContainer that loaded this JobService
	 */
	public ComponentContainer getComponentContainer();

	/**
	 * Sets the {@link ComponentContainer} that loaded this JobService
	 * @param componentContainer the ComponentContainer that loaded this JobService
	 */
	public void setComponentContainer(ComponentContainer componentContainer);

	/**
	 * Returns whether the job is in jobService
	 */
	public boolean contains(String jobName);
}
