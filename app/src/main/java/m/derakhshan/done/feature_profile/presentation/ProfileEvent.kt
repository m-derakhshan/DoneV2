package m.derakhshan.done.feature_profile.presentation

sealed class ProfileEvent() {
    object OnEditProfileClicked : ProfileEvent()
}