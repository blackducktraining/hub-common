/*******************************************************************************
 * Black Duck Software Suite SDK
 * Copyright (C) 2016 Black Duck Software, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *******************************************************************************/
package com.blackducksoftware.integration.hub.policy.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.joda.time.DateTime;
import org.junit.Test;

import com.blackducksoftware.integration.hub.meta.MetaInformation;
import com.blackducksoftware.integration.hub.meta.MetaLink;

public class PolicyStatusTest {

	@Test
	public void testPolicyStatus() {
		final String overallStatus1 = "status1";
		final String updatedAt1 = "time1";
		final String name1 = "name1";
		final int value1 = 3214;
		final ComponentVersionStatusCount statusCount1 = new ComponentVersionStatusCount(name1, value1);
		final List<ComponentVersionStatusCount> counts1 = new ArrayList<ComponentVersionStatusCount>();
		counts1.add(statusCount1);
		final String allow1 = "allow1";
		final List<String> allows1 = new ArrayList<String>();
		allows1.add(allow1);
		final String href1 = "href1";
		final MetaLink link1 = new MetaLink("rel1", "link1");
		final List<MetaLink> links1 = new ArrayList<MetaLink>();
		links1.add(link1);
		final MetaInformation _meta1 = new MetaInformation(allows1, href1, links1);

		final String overallStatus2 = PolicyStatusEnum.IN_VIOLATION.name();
		final String updatedAt2 = new DateTime().toString();
		final String name2 = PolicyStatusEnum.IN_VIOLATION_OVERRIDDEN.name();
		final int value2 = 0;
		final ComponentVersionStatusCount statusCount2 = new ComponentVersionStatusCount(name2, value2);
		final List<ComponentVersionStatusCount> counts2 = new ArrayList<ComponentVersionStatusCount>();
		counts2.add(statusCount2);
		final String allow2 = "allow2";
		final List<String> allows2 = new ArrayList<String>();
		allows2.add(allow2);
		final String href2 = "href2";
		final MetaLink link2 = new MetaLink("rel2", "link2");
		final List<MetaLink> links2 = new ArrayList<MetaLink>();
		links2.add(link2);
		final MetaInformation _meta2 = new MetaInformation(allows2, href2, links2);

		final PolicyStatus item1 = new PolicyStatus(overallStatus1, updatedAt1, counts1, _meta1);
		final PolicyStatus item2 = new PolicyStatus(overallStatus2, updatedAt2, counts2, _meta2);
		final PolicyStatus item3 = new PolicyStatus(overallStatus1, updatedAt1, counts1, _meta1);
		final PolicyStatus item4 = new PolicyStatus("", null, null, null);

		assertEquals(overallStatus1, item1.getOverallStatus());
		assertEquals(PolicyStatusEnum.UNKNOWN, item1.getOverallStatusEnum());
		assertEquals(updatedAt1, item1.getUpdatedAt());
		assertNull(item1.getUpdatedAtTime());
		assertEquals(counts1, item1.getComponentVersionStatusCounts());
		assertEquals(_meta1, item1.get_meta());

		assertEquals(overallStatus2, item2.getOverallStatus());
		assertEquals(PolicyStatusEnum.IN_VIOLATION, item2.getOverallStatusEnum());
		assertEquals(updatedAt2, item2.getUpdatedAt());
		assertEquals(updatedAt2, item2.getUpdatedAtTime().toString());
		assertEquals(counts2, item2.getComponentVersionStatusCounts());
		assertEquals(_meta2, item2.get_meta());

		assertEquals(PolicyStatusEnum.UNKNOWN, item4.getOverallStatusEnum());
		assertNull(item4.getUpdatedAtTime());

		assertTrue(!item1.equals(item2));
		assertTrue(item1.equals(item3));

		EqualsVerifier.forClass(PolicyStatus.class).suppress(Warning.STRICT_INHERITANCE).verify();

		assertTrue(item1.hashCode() != item2.hashCode());
		assertEquals(item1.hashCode(), item3.hashCode());

		final StringBuilder builder = new StringBuilder();
		builder.append("PolicyStatus [overallStatus=");
		builder.append(item1.getOverallStatus());
		builder.append(", updatedAt=");
		builder.append(item1.getUpdatedAt());
		builder.append(", componentVersionStatusCounts=");
		builder.append(item1.getComponentVersionStatusCounts());
		builder.append(", _meta=");
		builder.append(item1.get_meta());
		builder.append("]");

		assertEquals(builder.toString(), item1.toString());
	}

	@Test
	public void testGetCountInViolation() {
		PolicyStatus status = new PolicyStatus(null, null, null, null);

		assertNull(status.getCountInViolation());

		String name = "name";
		int value = 346;
		ComponentVersionStatusCount statusCount = new ComponentVersionStatusCount(name, value);
		List<ComponentVersionStatusCount> counts = new ArrayList<ComponentVersionStatusCount>();
		counts.add(statusCount);

		status = new PolicyStatus(null, null, counts, null);

		assertNull(status.getCountInViolation());

		name = PolicyStatusEnum.NOT_IN_VIOLATION.name();
		value = 435;
		statusCount = new ComponentVersionStatusCount(name, value);
		counts = new ArrayList<ComponentVersionStatusCount>();
		counts.add(statusCount);

		status = new PolicyStatus(null, null, counts, null);

		assertNull(status.getCountInViolation());

		name = PolicyStatusEnum.IN_VIOLATION.name();
		value = 435;
		statusCount = new ComponentVersionStatusCount(name, value);
		counts = new ArrayList<ComponentVersionStatusCount>();
		counts.add(statusCount);

		status = new PolicyStatus(null, null, counts, null);

		assertEquals(statusCount, status.getCountInViolation());

	}

	@Test
	public void testGetCountNotInViolation() {
		PolicyStatus status = new PolicyStatus(null, null, null, null);

		assertNull(status.getCountNotInViolation());

		String name = "name";
		int value = 346;
		ComponentVersionStatusCount statusCount = new ComponentVersionStatusCount(name, value);
		List<ComponentVersionStatusCount> counts = new ArrayList<ComponentVersionStatusCount>();
		counts.add(statusCount);

		status = new PolicyStatus(null, null, counts, null);

		assertNull(status.getCountNotInViolation());

		name = PolicyStatusEnum.IN_VIOLATION.name();
		value = 435;
		statusCount = new ComponentVersionStatusCount(name, value);
		counts = new ArrayList<ComponentVersionStatusCount>();
		counts.add(statusCount);

		status = new PolicyStatus(null, null, counts, null);

		assertNull(status.getCountNotInViolation());

		name = PolicyStatusEnum.NOT_IN_VIOLATION.name();
		value = 435;
		statusCount = new ComponentVersionStatusCount(name, value);
		counts = new ArrayList<ComponentVersionStatusCount>();
		counts.add(statusCount);

		status = new PolicyStatus(null, null, counts, null);

		assertEquals(statusCount, status.getCountNotInViolation());

	}

	@Test
	public void testGetCountInViolationOveridden() {
		PolicyStatus status = new PolicyStatus(null, null, null, null);

		assertNull(status.getCountInViolationOverridden());

		String name = "name";
		int value = 346;
		ComponentVersionStatusCount statusCount = new ComponentVersionStatusCount(name, value);
		List<ComponentVersionStatusCount> counts = new ArrayList<ComponentVersionStatusCount>();
		counts.add(statusCount);

		status = new PolicyStatus(null, null, counts, null);

		assertNull(status.getCountInViolationOverridden());

		name = PolicyStatusEnum.NOT_IN_VIOLATION.name();
		value = 435;
		statusCount = new ComponentVersionStatusCount(name, value);
		counts = new ArrayList<ComponentVersionStatusCount>();
		counts.add(statusCount);

		status = new PolicyStatus(null, null, counts, null);

		assertNull(status.getCountInViolationOverridden());

		name = PolicyStatusEnum.IN_VIOLATION_OVERRIDDEN.name();
		value = 435;
		statusCount = new ComponentVersionStatusCount(name, value);
		counts = new ArrayList<ComponentVersionStatusCount>();
		counts.add(statusCount);

		status = new PolicyStatus(null, null, counts, null);

		assertEquals(statusCount, status.getCountInViolationOverridden());

	}

}
