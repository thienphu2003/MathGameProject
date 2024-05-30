package com.thienphu.mathgame

import kotlin.random.Random

fun generateQuestion(selectedCategory: String) : ArrayList<Any>{
    var number1 = Random.nextInt(0,100)
    var number2= Random.nextInt(0,100)

    val textQuestion : String
    val correctAnswer : Int

    when(selectedCategory){
        "add" ->{
            textQuestion = "$number1 + $number2"
            correctAnswer = number1 + number2
        }
        "sub" ->{
             if(number1 >= number2){
                textQuestion ="$number1 - $number2"
                correctAnswer = number1 -number2
            }else{
                textQuestion =  "$number2 - $number1"
                correctAnswer = number2- number1
            }
        }
        "multi" ->{
            number1 = Random.nextInt(0,20)
            number2 = Random.nextInt(0,20)
            textQuestion = "$number1 * $number2"
            correctAnswer = number1 * number2
        }
        else ->{
            if(number1 == 0 || number2 ==0){
                textQuestion = "0/1"
                correctAnswer = 0
            }else if(number1>=number2){
                val newNumber = number1 - (number1 % number2)
                textQuestion = "$newNumber / $number2"
                correctAnswer = newNumber/number2
            }else {
                val newNumber = number2 - (number2%number1)
                textQuestion = "$newNumber / $number1"
                correctAnswer = newNumber/number1
            }
        }
    }
    val gameResultList = ArrayList<Any>()
    gameResultList.add(textQuestion)
    gameResultList.add(correctAnswer)
    return gameResultList
}