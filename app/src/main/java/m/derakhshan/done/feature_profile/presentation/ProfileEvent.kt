package m.derakhshan.done.feature_profile.presentation

sealed class ProfileEvent {
    object OnEditProfileClicked : ProfileEvent()
    data class OnNameChanged(val name: String) : ProfileEvent()
    object OnPasswordChangeClicked : ProfileEvent()
    object ApplyChanges : ProfileEvent()
    object Logout : ProfileEvent()
}