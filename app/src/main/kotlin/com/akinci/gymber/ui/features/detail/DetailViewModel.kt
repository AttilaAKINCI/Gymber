package com.akinci.gymber.ui.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.akinci.gymber.R
import com.akinci.gymber.core.compose.reduce
import com.akinci.gymber.core.maps.MapsManager
import com.akinci.gymber.domain.Location
import com.akinci.gymber.ui.ds.components.snackbar.SnackBarState
import com.akinci.gymber.ui.features.detail.DetailViewContract.ScreenArgs
import com.akinci.gymber.ui.features.detail.DetailViewContract.State
import com.akinci.gymber.ui.features.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mapsManager: MapsManager,
) : ViewModel() {

    private val screenArgs by lazy { savedStateHandle.navArgs<ScreenArgs>() }

    private val _stateFlow = MutableStateFlow(State(gym = screenArgs.gym))
    val stateFlow = _stateFlow.asStateFlow()

    fun openGoogleMaps(gymName: String, location: Location) {
        mapsManager.open(
            latitude = location.latitude,
            longitude = location.longitude,
            name = gymName,
        ).onFailure {
            // send error message to UI layer.
            _stateFlow.reduce {
                copy(
                    snackBarState = SnackBarState(
                        messageId = R.string.detail_screen_open_map_error
                    )
                )
            }
        }
    }
}
