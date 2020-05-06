package org.gemography.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RepositoryDetails {

	private long id;
	private String name;
	private long watchers;
	
}
