package ru.lab.fintechcourseproject.network.lectures

import com.google.gson.annotations.SerializedName
import ru.lab.fintechcourseproject.database.homeworks.DBHomework
import ru.lab.fintechcourseproject.database.lectures.DBLecture


fun convertLecture(l: Lecture): DBLecture {
    return DBLecture(l.id, l.lecTitle)
}

fun getHomeworksOfLecture(lecture: Lecture): List<DBHomework> {
    val databaseHomeworks = arrayListOf<DBHomework>()
    for (homework in lecture.tasks) {
        databaseHomeworks.add(
            DBHomework(
                homework.task.title,
                homework.status,
                homework.mark,
                homework.task.maxScore,
                homework.task.deadline,
                lecture.id
            )
        )
    }
    return databaseHomeworks
}

class HomeworksContainer(
    @SerializedName("homeworks")
    val lectures: ArrayList<Lecture>
)
