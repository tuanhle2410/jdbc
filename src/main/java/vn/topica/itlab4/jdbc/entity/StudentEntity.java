package vn.topica.itlab4.jdbc.entity;

import vn.topica.itlab4.jdbc.annotation.Column;
import vn.topica.itlab4.jdbc.annotation.Table;

/**
 * This class represents an object StudentEntity.
 * It provides attributes of StudentEntity and their get and set methods.
 *
 * @author AnhLT14 (anhlt14@topica.edu.vn)
 */
@Table(name = "student")
public class StudentEntity {
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name")
	private String name;

	public StudentEntity(Long id, String name){
	    this.id = id;
	    this.name = name;
	}
	
	public StudentEntity(){

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
