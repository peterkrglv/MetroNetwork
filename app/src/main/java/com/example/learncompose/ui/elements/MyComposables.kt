package com.example.learncompose.ui.elements

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.learncompose.R

@Composable
fun MyTitle(
    text: String
) {
    Text(
        modifier = Modifier.padding(8.dp),
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    )
}

@Composable
fun MyTextField(
    value: String,
    label: String,
    isError: Boolean,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = Modifier.padding(8.dp),
        value = value,
        label = { Text(label) },
        isError = isError,
        onValueChange = onValueChange
    )
}

@Composable
fun MyPasswordField(
    value: String,
    label: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    onIconClick: () -> Unit,
    passwordVisibility: Boolean
) {
    TextField(
        modifier = Modifier.padding(8.dp),
        value = value,
        label = { Text(label) },
        isError = isError,
        onValueChange = onValueChange,
        trailingIcon = {
            IconButton(onClick = onIconClick) {
                Icon(
                    painterResource(id = if (passwordVisibility) R.drawable.visibility else R.drawable.visibility_off),
                    contentDescription = null
                )
            }
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun LoadingBlock() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun LoadingScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun PostCard(username: String, date: String, text: String, image: String, color: Color) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = username, color = color)
            Text(text = date)
            //TODO: change image
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.example2),
                contentDescription = null
            )
            Text(text = text)
        }

    }
}

@Composable
fun EditPost(username: String, text: String, image: Uri, color: Color, onTextValueChange: (String) -> Unit, onButtonClick: () -> Unit, onImageClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = username, color = color)
            Image(
                modifier = Modifier.fillMaxWidth().clickable {
                    onImageClick()
                },
                painter = rememberAsyncImagePainter(image), //rememberimagePainter(image),
                contentDescription = null
            )
            TextField(
                value = text,
                onValueChange = onTextValueChange,
                label = { Text("You can write here") }
            )

            Button(
                onClick = { onButtonClick() },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = color)
            ) {
                Text(text = "Опубликовать")
            }
        }

    }
}

@Composable
fun CircularButton(onClick: () -> Unit, color: Color) {
    FloatingActionButton(
        onClick = onClick,
        shape = CircleShape,
        containerColor = color,
        modifier = Modifier.size(40.dp).padding(4.dp) // Adjust the size as needed
    ) {
        // No content inside the FAB
    }
}