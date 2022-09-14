package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BusinessCardApp()
                }
            }
        }
    }
}

@Composable
fun BusinessCardApp() {
    BusinessCardScreen(
        imagePainter = painterResource(R.drawable.portrait),
        name = stringResource(R.string.name),
        jobTitle = stringResource(R.string.job_title),
        phone = stringResource(R.string.phone),
        email = stringResource(R.string.email),
        linkedIn = stringResource(R.string.linked_in)
    )
}

@Composable
fun BusinessCardScreen(
    imagePainter: Painter,
    name: String,
    jobTitle: String,
    phone: String,
    email: String,
    linkedIn: String
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = imagePainter,
            contentDescription = "portrait of $name",
            modifier = Modifier
                .padding(start = 80.dp, end = 80.dp)
        )
        Text(
            text = name,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 24.dp, bottom = 8.dp)
        )
        Text(
            text = jobTitle,
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.height(100.dp))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 80.dp, bottom = 70.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row() {
            Icon(
                Icons.Rounded.Phone,
                contentDescription = null,
                modifier = Modifier.padding(end = 20.dp)
            )
            Text(
                text = phone,
                fontSize = 16.sp
            )
        }
        Row() {
            Icon(
                Icons.Rounded.Email,
                contentDescription = null,
                modifier = Modifier.padding(end = 20.dp)
            )
            Text(
                text = email,
                fontSize = 16.sp
            )
        }
        Row() {
            Icon(
                Icons.Rounded.Face,
                contentDescription = null,
                modifier = Modifier.padding(end = 20.dp)
            )
            HyperlinkText(text = "LinkedIn", link = linkedIn, 16.sp)
        }
    }
}

@Composable
fun HyperlinkText(text: String, link: String, fontSize: TextUnit) {
    // Style text
    val annotatedText = buildAnnotatedString {
        pushStringAnnotation(
            tag = "URL",
            annotation = link
        )
        withStyle(style = SpanStyle(color = Color.Blue, fontSize = fontSize)) {
            append(text)
        }
        pop()
    }

    // Handle action on click
    val uriHandler = LocalUriHandler.current
    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = "URL",
                start = offset,
                end = offset
            )
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BusinessCardTheme {
        BusinessCardApp()
    }
}