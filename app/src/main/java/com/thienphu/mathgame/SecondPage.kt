package com.thienphu.mathgame


import android.os.CountDownTimer
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondPage(navController: NavController, category: String) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(colorResource(id = R.color.blue))
    val life = remember {
        mutableIntStateOf(3)
    }

    val score = remember {
        mutableIntStateOf(0)
    }

    val remainingTimeText = remember {
        mutableStateOf("30")
    }

    val myQuestion = remember {
        mutableStateOf("")
    }

    val myAnswer = remember {
        mutableStateOf("")
    }

    val isEnable = remember {
        mutableStateOf(true)
    }

    val correctAnswer = remember {
        mutableIntStateOf(0)
    }

    val totalTimeInMillis = remember {
        when(category){
            "multi"-> mutableLongStateOf(26000L)
            "div"-> mutableLongStateOf(26000L)
            else -> mutableLongStateOf(16000L)
        }
    }

    val timer = remember {
        mutableStateOf(
            object : CountDownTimer(totalTimeInMillis.longValue,1000){
                override fun onTick(millisUntilFinished: Long) {
                    remainingTimeText.value = String.format(Locale.getDefault(),"%02d",millisUntilFinished/1000)
                    if(!isEnable.value && myAnswer.value.isNotEmpty()){
                        isEnable.value = true
                    }
                }

                override fun onFinish() {
                    cancel()
                    myQuestion.value = "Sorry, Time is up!"
                    life.intValue -=1
                    isEnable.value = false
                }

            }.start()
        )
    }

    LaunchedEffect(key1 = "math") {
        val resultList = generateQuestion(category)
        myQuestion.value = resultList[0].toString()
        correctAnswer.intValue = resultList[1].toString().toInt()
    }

    val context = LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Rounded.ArrowBack, contentDescription = "Back")
                    }
                },
                title = {
                    Text(
                        text = when (category) {
                            "add" -> "Addition"
                            "sub" -> "Subtraction"
                            "multi" -> "Multiplication"
                            else -> "Division"
                        }, fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = colorResource(id = R.color.blue),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .paint(
                        painterResource(id = R.drawable.second),
                        contentScale = ContentScale.FillBounds
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Life: ", fontSize = 20.sp, color = Color.DarkGray)
                    Text(text = life.intValue.toString(), fontSize = 20.sp, color = Color.DarkGray)
                    Text(text = "Score: ", fontSize = 20.sp, color = Color.DarkGray)
                    Text(text = score.intValue.toString(), fontSize = 20.sp, color = Color.DarkGray)
                    Text(text = "Time Remaining: ", fontSize = 20.sp, color = Color.DarkGray)
                    Text(text = remainingTimeText.value, fontSize = 20.sp, color = Color.DarkGray)

                }
                Spacer(modifier = Modifier.height(70.dp))
                TextForQuestion(myQuestion.value)
                Spacer(modifier = Modifier.height(30.dp))
                TextFieldForAnswer(myAnswer)
                Spacer(modifier = Modifier.height(70.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ButtonOkNext(buttonText = "OK", myOnclick = {
                        isEnable.value = false
                        if(myAnswer.value.isEmpty()){
                            Toast.makeText(context,"Write an answer or click the next button",Toast.LENGTH_SHORT).show()
                        }else {
                            timer.value.cancel()

                            if(myAnswer.value.toInt() == correctAnswer.intValue){
                                score.intValue +=10
                                myQuestion.value = "Congratulations!!!"
                                myAnswer.value =""
                            }else {
                                life.intValue -=1
                                myQuestion.value = "Sorry, your answer is wrong."
                            }
                        }
                    }, isEnabled = isEnable.value)
                    ButtonOkNext(buttonText = "Next", myOnclick = {
                        timer.value.cancel()
                        timer.value.start()

                        if(life.intValue == 0){
                            Toast.makeText(context,"Game over!!!",Toast.LENGTH_SHORT).show()
                            navController.navigate("ResultPage/${score.intValue}"){
                                popUpTo("FirstPage"){
                                    inclusive = false
                                }
                            }
                        }else {
                            val newResultList = generateQuestion(category)
                            myQuestion.value = newResultList[0].toString()
                            correctAnswer.intValue = newResultList[1].toString().toInt()
                            myAnswer.value = ""
                            isEnable.value = true
                        }
                    }, isEnabled = true)
                }
            }
        }
    )
}