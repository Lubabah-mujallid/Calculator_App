package com.example.calculator_app

class Calculations (var initNum : Float = 0f){

    fun addition(num : Float, num2 :Float = 0f) : Float { return initNum + num + num2 }
    fun subtraction(num : Float) : Float  {return initNum - num}
    fun subtraction(num : Float, num2 :Float) : Float  {return num + num2}
    fun multiplication(num : Float) : Float  {return initNum * num }
    fun multiplication(num : Float, num2 :Float) : Float  {return num * num2}
    fun division(num : Float) : Float  {return initNum / num}
    fun division(num : Float, num2 :Float) : Float  {return num / num2}



}