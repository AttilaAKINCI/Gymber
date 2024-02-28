package com.akinci.gymber.ui.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akinci.gymber.R
import com.akinci.gymber.core.maps.MapsManager
import com.akinci.gymber.core.mvi.MVI
import com.akinci.gymber.core.mvi.mvi
import com.akinci.gymber.domain.data.Location
import com.akinci.gymber.ui.features.detail.DetailViewContract.Action
import com.akinci.gymber.ui.features.detail.DetailViewContract.Effect
import com.akinci.gymber.ui.features.detail.DetailViewContract.ScreenArgs
import com.akinci.gymber.ui.features.detail.DetailViewContract.State
import com.akinci.gymber.ui.features.navArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val mapsManager: MapsManager,
) : ViewModel(),
    MVI<State, Action, Effect> by mvi(State(savedStateHandle.navArgs<ScreenArgs>().gym)) {

    override fun onAction(action: Action) {
        when (action) {
            Action.OnBackPressed -> viewModelScope.sendEffect(Effect.Close)
            is Action.OpenMaps -> openGoogleMaps(
                location = action.location,
                gymName = action.gymName
            )
        }
    }

    fun openGoogleMaps(gymName: String, location: Location) {
        mapsManager.open(
            latitude = location.latitude,
            longitude = location.longitude,
            name = gymName,
        ).onFailure {
            // send error message to UI layer.
            viewModelScope.sendEffect(
                Effect.ShowToastMessage(messageId = R.string.detail_screen_open_map_error)
            )
        }
    }
}
