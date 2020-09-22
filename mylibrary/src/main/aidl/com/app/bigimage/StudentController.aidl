// StudentController.aidl
package com.app.bigimage;
import com.app.bigimage.beans.StudentBean;
// Declare any non-default types here with import statements

/**
* 进程间调用的接口
*/
interface StudentController {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    List<StudentBean> getStudents();

    void addStudent(inout StudentBean student);

}
