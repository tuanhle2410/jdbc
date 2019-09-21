package vn.topica.itlab4.jdbc.entity;

import java.util.List;

import vn.topica.itlab4.jdbc.annotation.Column;
import vn.topica.itlab4.jdbc.annotation.OneToMany;
import vn.topica.itlab4.jdbc.annotation.Table;

/**
 * This class represents an object ClassEntity.
 * It provides attributes of ClassEntity and their get and set methods.
 *
 * @author AnhLT14 (anhlt14@topica.edu.vn)
 */
@Table(name = "Class")
public class ClassEntity {
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "student")
	private List<StudentEntity> listStudent;

	public ClassEntity(){

	}

	public ClassEntity(Long id, String name){
        this.id = id;
        this.name = name;
        this.listStudent = null;
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

	public List<StudentEntity> getListStudent() {
		return listStudent;
	}

	public void setListStudent(List<StudentEntity> listStudent) {
		this.listStudent = listStudent;
	}
}
