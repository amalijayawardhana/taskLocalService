package managedata;

import DBConnction.DBConnection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ManageData {
    public static void manageData() throws IOException {

        /*READ THE FILE PATHS INSIDE THE FOLDER*/

//        Path path1 = Paths.get("C:\\Users\\ASUS\\Desktop\\codezync\\task1\\resources");
        Path path1 = Paths.get("/resources");
        Stream<Path> subPaths = Files.walk(path1,1);
//        subPaths.forEach(System.out::println);
        List<Path> filePaths = subPaths.collect(Collectors.toList());

        /*READ THE DATA INSIDE THE FILES*/
        for (int i = 1; i <filePaths.size() ; i++) {
//            System.out.println(filePaths.get(i));
            String pathString = filePaths.get(i).toString();
            String fileName = pathString.substring(pathString.lastIndexOf("\\") + 1);
            String subject = fileName.substring(0, fileName.lastIndexOf("."));

            Path path2 = Paths.get(pathString);
            System.out.println(subject);
            List<String> lines = Files.readAllLines(path2);
            String content = "";
            String lineSeparator = "";
            for (String line : lines) {
                lineSeparator = line + System.lineSeparator();
                content += lineSeparator;
                String[] dataArray = lineSeparator.split(",");
                String stuName = dataArray[0];
                String mark = dataArray[1];
//                System.out.println(stuName);


                /*SAVE DATA INTO DATABASE*/
                Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement pstm1 = null;
                PreparedStatement pstm2 = null;
                PreparedStatement pstm3 = null;
                String studentId;
                String subjectId;

                try {
//                    pstm1 = connection.prepareStatement("SELECT * FROM student WHERE name=?");
//                    pstm2 = connection.prepareStatement("SELECT * FROM subject WHERE name=?");
                    pstm3 = connection.prepareStatement("INSERT INTO Student_Subject_new (student_name, subject_name,mark) VALUES (?,?,?) ");

                    /*pstm2.setString(1, subject);
                    pstm1.setString(1, stuName);
                    ResultSet rst2 = pstm1.executeQuery();
                    while (rst2.next()){
                        subjectId = rst2.getString("id");
                        System.out.println(subject+subjectId);
                        ResultSet rst = pstm1.executeQuery();
                        while (rst.next()){
                            studentId = rst.getString("id");
                            System.out.println(stuName+"- "+studentId+"-"+subject+"_"+subjectId+"-"+mark);
                        }
                    }*/
                    pstm3.setString(1, stuName);
                    pstm3.setString(2, subject);
                    pstm3.setString(3, mark);
                    int row = pstm3.executeUpdate();
                    if (row > 0) {
                        System.out.println("successfully added");
                    }else {
                        System.out.println("sorry,Please try again");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

    }

}
