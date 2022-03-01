package m.derakhshan.done.core.data.data_source

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class Setting @Inject constructor(
    @ApplicationContext context: Context
) {

    private val share = context.getSharedPreferences("share", Context.MODE_PRIVATE)
    private val edit = share.edit()

    var isUserLoggedIn: Boolean
        set(value) {
            edit.putBoolean("isUserLoggedIn", value)
            edit.apply()
        }
        get() = share.getBoolean("isUserLoggedIn", false)

    var lastNoteId: Int
        set(value) {
            edit.putInt("lastNoteId", value)
            edit.apply()
        }
        get() {
            val id = share.getInt("lastNoteId", 0)
            edit.putInt("lastNoteId", id + 1)
            edit.apply()
            return id
        }

    var userProfileImage: String
        set(value) {
            edit.putString("userProfileImage", value)
            edit.apply()
        }
        get() = share.getString(
            "userProfileImage",
            "https://www.constrack.ng/uploads/icon-user-default.png"
        ) ?: "https://www.constrack.ng/uploads/icon-user-default.png"

}