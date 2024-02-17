package com.example.myriadmirror.ui.view


import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.myriadmirror.R

@Composable
fun AvatarImage(model: Any?, size: Dp = 56.dp) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(model).decoderFactory(
            ImageDecoderDecoder.Factory()
        ).build(),
        contentDescription = stringResource(R.string.avatar_image),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.defualt_avatar),
        error = painterResource(R.drawable.defualt_avatar),
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
    )
}

@Preview
@Composable
fun AvatarImagePreview() {
    AvatarImage(
        model = "https://pub-45c0b529c25a4d388dfa7cf57f35f8f0.r2.dev/avatar/xhs.webp",
        size = 56.dp
    )
}

