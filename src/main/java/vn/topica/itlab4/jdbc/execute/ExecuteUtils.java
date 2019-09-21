package vn.topica.itlab4.jdbc.execute;

import vn.topica.itlab4.jdbc.annotation.Column;
import vn.topica.itlab4.jdbc.annotation.Table;
import vn.topica.itlab4.jdbc.entity.ClassEntity;
import vn.topica.itlab4.jdbc.entity.StudentEntity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains methods
 * - to get connection to a MySQL Database
 * - to load a list of ClassEntity as LazyLoad
 * - to load a list of StudentEntity
 * - to convert a list of Student into a specific String
 *
 * @author AnhLT14 (anhlt14@topica.edu.vn)
 */
public class ExecuteUtils {

    /**
     * This method gets connection to my database named "loading_db"
     * 
     * @return a connection
     * 
     * @throws ClassNotFoundException no definition for the specified class name could be found
     * @throws SQLException error during an interaction with a data source
     */
    public static Connection mysqlGetConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/loading_db", "root", "");
        return con;
    }

    /**
     * This method loads lazily data from database to a list of any object
     * using java reflection
     * 
     * @param <T> any Class
     * @param object any object of the Class you want to lazily load
     * @param tclass the Class you want to load
     * 
     * @return a list of object which lazily loaded
     */
    public static <T> List<T> lazyLoad(T object, Class<T> tclass) {
        List<T> list = new ArrayList<T>();
        try {
            Class<?> clazz = object.getClass();
            Table annClass = clazz.getDeclaredAnnotation(Table.class);

            Field[] fieldArray = clazz.getDeclaredFields();
            List<String> column = new ArrayList<String>();
            List<Field> annFieldList = new ArrayList<Field>();

            for (Field element : fieldArray) {
                element.setAccessible(true);
                Column annField = element.getDeclaredAnnotation(Column.class);
                if (annField != null) {
                    column.add(annField.name());
                    annFieldList.add(element);
                }
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < column.size(); i++) {
                sb.append(column.get(i)).append(",");
            }
            String strToAddToQuery = sb.toString().substring(0, sb.length() - 1);
            String strQuery = "select " + strToAddToQuery + " from " + annClass.name();

            Connection con = mysqlGetConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(strQuery);

            while (rs.next()) {
                T obj = tclass.newInstance();
                for (int i = 0; i < annFieldList.size(); i++) {
                    String fieldName = annFieldList.get(i).getName();
                    String strMethodSetter = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Class fieldType = annFieldList.get(i).getType();
                    Method methodSetter = obj.getClass().getMethod(strMethodSetter, fieldType);
                    methodSetter.invoke(obj, rs.getObject(i + 1, fieldType));
                }
                list.add(obj);
            }
            con.close();
            return list;
        } catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException
                | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {

        }
        return list;
    }

    /**
     * This method loads a list of StudentEntity.
     * 
     * @param id the class_id
     * @param ce the object ClassEntity you want to add this list
     * 
     * @return a list of StudentEntity
     * 
     * @throws ClassNotFoundException no definition for the specified class name could be found
     * @throws SQLException error during an interaction with a data source
     */
    public static List<StudentEntity> lazyLoadListStudent(long id, ClassEntity ce)
            throws SQLException, ClassNotFoundException {
        List<StudentEntity> studentEntityList = new ArrayList<StudentEntity>();
        Connection con = mysqlGetConnection();

        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Student where class_id = " + id);
        while (rs.next()) {
            studentEntityList.add(new StudentEntity((long) rs.getInt(1), rs.getString(2)));
        }
        ce.setListStudent(studentEntityList);
        con.close();
        return studentEntityList;
    }

    /**
     * This method converts a list of StudentEntity into a specific string to show.
     * 
     * @param studentEntityList the input list
     * 
     * @return the specific string
     */
    public static String listStudentToString(List<StudentEntity> studentEntityList) {
        StringBuilder sb = new StringBuilder();
        for (StudentEntity se : studentEntityList) {
            sb.append(se.getName()).append("\t");
        }
        String str = sb.toString().trim();
        return str;
    }
}