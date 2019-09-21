package vn.topica.itlab4.jdbc.execute;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import vn.topica.itlab4.jdbc.entity.ClassEntity;
import vn.topica.itlab4.jdbc.entity.StudentEntity;

/**
 * This class executes the Eager Loading.
 *
 * @author AnhLT14 (anhlt14@topica.edu.vn)
 */
public class ExecuteEagerLoad {

    /**
     * This main method uses the given query to get data from database.
     * Then load them to a list of ClassEntity
     * with the list of StudentEntity of each ClassEntity at the same time.
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            List<ClassEntity> listClassEntity = new ArrayList<ClassEntity>();
            List<Long> listClassId = new ArrayList<Long>();
            List<StudentEntity> listStudentEntity;

            Connection con = ExecuteUtils.mysqlGetConnection();
            String strQuery = "Select c.id as class_id,c.name as class_name,s.id as student_id, s.name as student_name from Class c, Student s where c.id = s.class_id";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(strQuery);

            while (rs.next()) {
                Long classId;
                classId = rs.getLong(1);

                ClassEntity classEntity = new ClassEntity();
                classEntity.setId(rs.getLong(1));
                classEntity.setName(rs.getString(2));

                StudentEntity studentEntity = new StudentEntity();
                studentEntity.setId(rs.getLong(3));
                studentEntity.setName(rs.getString(4));
                
                /**
                 * listClassId contains unique id loaded from database.
                 * Use this to check if a ClassEntity appears more than once.
                 * If a ClassEntity object has existed in listClassEntity, 
                 * the data of the StudentEntity in the same row stored 
                 * to the listStudent of the object ClassEntity that existed.
                 */
                if (listClassId.contains(classId) == false) {
                    listClassId.add(classId);
                    listStudentEntity = new ArrayList<StudentEntity>();
                    listStudentEntity.add(studentEntity);
                    classEntity.setListStudent(listStudentEntity);
                    listClassEntity.add(classEntity);
                } else {
                    for (ClassEntity ce : listClassEntity) {
                            if (ce.getId() == classId) {
                                ce.getListStudent().add(studentEntity);
                            }
                    }
                }
            }
            con.close();
            for (ClassEntity ce : listClassEntity) {
                System.out.println("Class ID: " + ce.getId());
                System.out.println("Class name: " + ce.getName());
                System.out.println("List students: " + ExecuteUtils.listStudentToString(ce.getListStudent()));
                System.out.println("-----------------------------------");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}