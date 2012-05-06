/*
 * Copyright 2012 Athens Team
 *
 * This file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package net.rothlee.athens.olympus.data;

/**
 * @author roth2520@gmail.com
 */
public class Range {
	
	public static Range createRange(Integer afterId, Integer beforeId, Integer limit) {
		Integer rangeAfterId = Integer.MAX_VALUE;
		Integer rangeBeforeId = 0;
		Integer rangeLimit = Integer.MAX_VALUE;
		if(afterId != null) {
			rangeAfterId = afterId;
		}
		if(beforeId != null) {
			rangeBeforeId = beforeId;
		}
		if(limit != null) {
			rangeLimit = limit;
		}
		return new Range(rangeAfterId, rangeBeforeId, rangeLimit);
	}

	private Integer afterId;
	
	private Integer beforeId;
	
	private Integer limit;

	private Range(Integer afterId, Integer beforeId, Integer limit) {
		this.afterId = afterId;
		this.beforeId = beforeId;
		this.limit = limit;
	}
	public Integer getAfterId() {
		return afterId;
	}

	public Integer getBeforeId() {
		return beforeId;
	}

	public Integer getLimit() {
		return limit;
	}
}
