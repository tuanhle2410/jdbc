package vn.topica.itlab4.jdbc.execute;

import vn.topica.itlab4.jdbc.entity.ClassEntity;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class executes the Lazy Loading.
 *
 * @author AnhLT14 (anhlt14@topica.edu.vn)
 */
public class ExecuteLazyLoad {

    /**
     * This main method uses methods in ExecuteUtils to lazily load a list of ClassEntity.
     * 
     * @param args
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        ClassEntity classEntity = new ClassEntity();
        List<ClassEntity> listClassEntity = new ArrayList<ClassEntity>();
        listClassEntity = ExecuteUtils.lazyLoad(classEntity, ClassEntity.class);

        for (ClassEntity ce : listClassEntity) {
            System.out.println("Class ID: " + ce.getId());
            System.out.println("Class name: " + ce.getName());
            System.out.println("List students: " + ExecuteUtils.listStudentToString(ExecuteUtils.lazyLoadListStudent(ce.getId(), ce)));
            System.out.println("-----------------------------------");
        }
    }
}
