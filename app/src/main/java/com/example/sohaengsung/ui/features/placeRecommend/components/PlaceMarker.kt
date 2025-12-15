import androidx.compose.runtime.Composable
import com.example.sohaengsung.data.model.Place
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState

@Composable
fun PlaceMarker(
    place: Place,
    onClick: (Place) -> Unit
) {
    Marker(
        state = MarkerState(position = LatLng(place.latitude, place.longitude)),
        title = place.name,
        snippet = place.address,
        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED),
        onClick = {
            onClick(place)
            false // true일 경우 기본 동작(카메라 이동, 정보창 표시)이 무시됨
        }
    )
}