package ru.lab.fintechcourseproject.network.students


import com.google.gson.annotations.SerializedName
import ru.lab.fintechcourseproject.database.students.DBStudent

fun convertToDatabaseStudent(student: Student): DBStudent {
    return DBStudent(
        student.id,
        student.name,
        student.grades[student.grades.size - 1].mark
    )
}

class StudentsContainer(
    @SerializedName("grades")
    val students: ArrayList<Student>
)
