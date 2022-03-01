package m.derakhshan.done.feature_profile.presentation

import android.net.Uri

sealed class ProfileEvent {
    data class OnNameChanged(val name: String) : ProfileEvent()

    data class ImageSelected(val uri: Uri) : ProfileEvent()
    object ImageSelectionClose : ProfileEvent()
    object ImageSelectionOpen : ProfileEvent()

    object OnEditProfileClicked : ProfileEvent()
    object OnPasswordChangeClicked : ProfileEvent()
    object ApplyChanges : ProfileEvent()
    object Logout : ProfileEvent()
}