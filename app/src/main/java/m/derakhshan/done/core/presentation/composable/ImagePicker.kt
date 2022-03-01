package m.derakhshan.done.core.presentation.composable

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyGridState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale


@Composable
fun ImagePicker(
    modifier: Modifier = Modifier,
    openSelection: Boolean,
    height: Dp,
    context: Context,
    onCloseListener: () -> Unit,
    onSelect: (Uri) -> Unit,
) {
    var isPermissionChanged by remember {
        mutableStateOf(false)
    }
    var imageUri by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    LaunchedEffect(imageUri, isPermissionChanged, block = {
        imageUri = getImagesURI(context)
    })

    val actualHeight by animateDpAsState(
        targetValue = if (openSelection) height else 0.dp,
        animationSpec = spring(0.5f, stiffness = 15f),
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(actualHeight)
    ) {
        IconButton(
            onClick = onCloseListener,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "close")
        }
        Box {
            ShowImages(imageUri, onSelect)
            CheckStoragePermission {
                isPermissionChanged = it
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ShowImages(uris: List<Uri>, onSelection: (Uri) -> Unit) {

    var longPressOnImage by remember {
        mutableStateOf(-1)
    }
    val lazyState = rememberLazyGridState()
    val lastItemsIndex =
        uris.lastIndex downTo (uris.lastIndex - when (uris.size % 3) {
            0 -> 2
            1 -> 0
            else -> 1
        })

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        cells = GridCells.Fixed(3),
        state = lazyState,
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, bottom = 10.dp)
    ) {
        items(uris) { item ->

            val scaleAnimation by animateFloatAsState(
                targetValue = if (uris.indexOf(item) == longPressOnImage) 2f else 1f,
            )

            val offsetXAnimation by animateDpAsState(
                targetValue = (
                        if (uris.indexOf(item) == longPressOnImage && longPressOnImage % 3 == 0)
                            29.dp
                        else if (uris.indexOf(item) == longPressOnImage && (longPressOnImage + 1) % 3 == 0)
                            (-29).dp
                        else
                            0.dp
                        )
            )
            val offsetYAnimation by animateDpAsState(
                targetValue = (
                        if (uris.indexOf(item) == longPressOnImage && longPressOnImage in lazyState.firstVisibleItemIndex..lazyState.firstVisibleItemIndex + 2)
                            (40 + lazyState.firstVisibleItemScrollOffset * 0.2).dp
                        else if (uris.indexOf(item) == longPressOnImage && longPressOnImage in lastItemsIndex)
                            (-40).dp
                        else
                            0.dp
                        )
            )
            Box(
                modifier = Modifier
                    .zIndex(if (uris.indexOf(item) == longPressOnImage) 1f else 0f)
                    .scale(scaleAnimation)
                    .offset(x = offsetXAnimation, y = offsetYAnimation)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { onSelection(item) },
                            onPress = {
                                try {
                                    awaitRelease()
                                } finally {
                                    longPressOnImage = -1
                                }
                            },
                            onLongPress = { longPressOnImage = uris.indexOf(item) },
                        )
                    }
            ) {
                Image(
                    painter = rememberImagePainter(item),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .border(1.dp, MaterialTheme.colors.onBackground)
                        .background(MaterialTheme.colors.background)
                        .padding(5.dp)

                )
            }

        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun CheckStoragePermission(permissionChanged: (Boolean) -> Unit) {
    val permission = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
    val permissionChanges by remember {
        mutableStateOf(permission.status.isGranted)
    }
    if (permissionChanges != permission.status.isGranted)
        permissionChanged(true)

    if (!permission.status.isGranted) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val textToShow = if (permission.status.shouldShowRationale) {
                "Access to Storage is needed for loading the images."
            } else {
                "Can't access the storage. permission denied."
            }
            Text(textToShow)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    permission.launchPermissionRequest()
                }) {
                Text("Grant Permissions")
            }
        }
    }
}

private fun getImagesURI(context: Context): List<Uri> {
    val galleryImageUri = mutableListOf<Uri>()
    val collection =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        else
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf(
        MediaStore.Images.Media.DATE_TAKEN,
        MediaStore.Images.Media._ID
    )
    val order = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
    val query = context.contentResolver.query(collection, projection, null, null, order)
    query?.use { cursor ->
        val columnID = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(columnID)
            val contentUri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                id
            )
            galleryImageUri.add(contentUri)
        }
    }
    return galleryImageUri
}