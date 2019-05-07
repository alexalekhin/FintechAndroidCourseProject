package ru.lab.fintechcourseproject.database.user

import android.content.SharedPreferences
import ru.lab.fintechcourseproject.network.users.User


class UserRepository(private val prefs: SharedPreferences) {
    fun loadUserFromMemory(): User {
        return User(
            prefs.getString("lastName", STD_STRING),
            prefs.getString("firstName", STD_STRING),
            prefs.getString("patronymic", STD_STRING),
            prefs.getString("avatarUrl", STD_STRING),
            prefs.getString("bDay", STD_STRING),
            prefs.getString("email", STD_STRING),
            prefs.getString("phone", STD_STRING),
            prefs.getString("tShirtSize", STD_STRING),
            prefs.getBoolean("isClient", STD_BOOLEAN),
            prefs.getString("skype", STD_STRING),
            prefs.getString("description",
                STD_STRING
            ),
            prefs.getString("region", STD_STRING),
            prefs.getString("school", STD_STRING),
            prefs.getLong("schoolGradYear", STD_LONG),
            prefs.getString("university", STD_STRING),
            prefs.getLong("universityGradYear",
                STD_LONG
            ),
            prefs.getString("faculty", STD_STRING),
            prefs.getString("grade", STD_STRING),
            prefs.getString("department", STD_STRING),
            prefs.getString("currentWork",
                STD_STRING
            ),
            prefs.getString("resume", STD_STRING)
        )

    }

    fun saveUserToMemory(user: User) {
        with(prefs.edit()) {
            putString("lastName", user.lastName)
            putString("firstName", user.firstName)
            putString("patronymic", user.patronymic)
            putString("avatarUrl", user.avatarUrl)
            putString("bDay", user.bDay)
            putString("email", user.email)
            putString("phone", user.phone)
            putString("tShirtSize", user.tShirtSize)
            putBoolean("isClient", user.isClient)
            putString("skype", user.skype)
            putString("description", user.description)
            putString("region", user.region)
            putString("school", user.school)
            putLong("schoolGradYear", user.schoolGradYear ?: STD_LONG)
            putString("university", user.university)
            putLong("universityGradYear", user.universityGrad ?: STD_LONG)
            putString("faculty", user.faculty)
            putString("grade", user.grade)
            putString("department", user.department)
            putString("currentWork", user.currentWork)
            putString("resume", user.resume)
            apply()
        }
    }

    companion object {
        const val STD_STRING = ""
        const val STD_BOOLEAN = false
        const val STD_LONG = Long.MAX_VALUE
        val STD_AVATAR_URL = null
    }
}
