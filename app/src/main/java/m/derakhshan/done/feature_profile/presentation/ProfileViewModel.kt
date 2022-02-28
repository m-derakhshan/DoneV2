package m.derakhshan.done.feature_profile.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import m.derakhshan.done.feature_profile.domain.use_case.ProfileUseCases
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    useCases: ProfileUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    init {
        viewModelScope.launch {
            useCases.getUserInfo().collectLatest {
                _state.value = _state.value.copy(
                    name = it.name,
                    email = it.email,
                )
            }
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnEditProfileClicked -> {}
            is ProfileEvent.OnNameChanged -> {
                _state.value = _state.value.copy(
                    name = event.name.replace("\n", "")
                )
            }
            is ProfileEvent.OnPasswordChangeClicked -> {}
            is ProfileEvent.ApplyChanges -> {}
            is ProfileEvent.Logout -> {}
        }
    }
}