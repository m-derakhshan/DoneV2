package m.derakhshan.done.feature_profile.presentation

import m.derakhshan.done.core.utils.AppConstants


data class ProfileState(
    val name: String = "",
    val email: String = "",
    val uid: String = "",
    val profileImage: String = AppConstants.DEFAULT_PROFILE,
    val isImagePickerOpen: Boolean = false,
    val isApplyChangesExpanded: Boolean = true

)