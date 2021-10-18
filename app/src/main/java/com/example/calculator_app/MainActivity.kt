package com.example.calculator_app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.abs
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    //region Variable Declarations
    lateinit var tv: TextView

    lateinit var addButton: Button
    lateinit var subButton: Button
    lateinit var divButton: Button
    lateinit var multButton: Button

    lateinit var decimalButton: Button
    lateinit var equalButton: Button
    lateinit var posNegButton: Button
    lateinit var clearButton: Button
    lateinit var deleteButton: Button

    lateinit var num0Button: Button
    lateinit var num1Button: Button
    lateinit var num2Button: Button
    lateinit var num3Button: Button
    lateinit var num4Button: Button
    lateinit var num5Button: Button
    lateinit var num6Button: Button
    lateinit var num7Button: Button
    lateinit var num8Button: Button
    lateinit var num9Button: Button

    lateinit var numlist: ArrayList<Float>
    lateinit var oplist: ArrayList<Char>
    var counter = 0
    var isDecimal = false
    var isNegative = false
    var isFinished = false
    //endregion

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //region Declarations
        tv = tvMain
        addButton = add
        subButton = sub
        divButton = divide
        multButton = multiply
        decimalButton = decimal
        equalButton = equal
        posNegButton = posNeg
        clearButton = clear
        deleteButton = delete
        num0Button = num0
        num1Button = num1
        num2Button = num2
        num3Button = num3
        num4Button = num4
        num5Button = num5
        num6Button = num6
        num7Button = num7
        num8Button = num8
        num9Button = num9
        numlist = ArrayList()
        oplist = ArrayList()
        //endregions

        addButton.setOnClickListener { opButtonPressed('+') }
        subButton.setOnClickListener { opButtonPressed('-') }
        divButton.setOnClickListener { opButtonPressed('/') }
        multButton.setOnClickListener { opButtonPressed('*') }
        decimalButton.setOnClickListener {
            opButtonPressed('.')
            isDecimal = true
        }
        equalButton.setOnClickListener {
            opButtonPressed('=')
            //make all calculations
            var final = calculate()
            //clear the text view and update it + clear Arraylist but add last one
            tv.text = final.toString()
            numlist.removeAll(numlist)
            oplist.removeAll(oplist)
            isDecimal = false
            isNegative = false
            isFinished = true
        }
        posNegButton.setOnClickListener {
            var temp = tv.text.toString()
            tv.text = temp + '_'
            oplist.add('_')
            isNegative = true
        }
        clearButton.setOnClickListener {
            numlist.removeAll(numlist)
            oplist.removeAll(oplist)
            tv.text = ""
            isDecimal = false
            isNegative = false
        }
        deleteButton.setOnClickListener {
            if (tv.text.isNotEmpty()) {
                var temp = (tv.text.toString())
                var tempChar = temp[temp.length - 1]
                if (tempChar.isDigit()) {
                    var temp1 = numlist[numlist.size - 1]
                    numlist.removeLast()
                    //this deletes the whole last number and i want to delete only the last digit
                    var temps = temp1.toString()
                    temps = temps.substring(0, temps.length - 1)
                    numlist.add(temps.toFloat())
                } else {
                    var temp2 = oplist[oplist.size - 1]
                    oplist.removeLast()
                    if (temp2 == '_') isNegative = false
                    if (temp2 == '.') isDecimal = false
                }
                tv.text = temp.substring(0, temp.length - 1)
            } else {
                Toast.makeText(this, "You can't delete more!", Toast.LENGTH_LONG).show()
            }
        }


        //region numButtons
        num0Button.setOnClickListener { numButtonPressed('0') }
        num1Button.setOnClickListener { numButtonPressed('1') }
        num2Button.setOnClickListener { numButtonPressed('2') }
        num3Button.setOnClickListener { numButtonPressed('3') }
        num4Button.setOnClickListener { numButtonPressed('4') }
        num5Button.setOnClickListener { numButtonPressed('5') }
        num6Button.setOnClickListener { numButtonPressed('6') }
        num7Button.setOnClickListener { numButtonPressed('7') }
        num8Button.setOnClickListener { numButtonPressed('8') }
        num9Button.setOnClickListener { numButtonPressed('9') }
        //endregion

    }

    fun numButtonPressed(num: Char) {
        if (isFinished) {
            tv.text = ""
            isFinished = false
        }
        tv.text = tv.text.toString() + num
        counter++
    }

    fun opButtonPressed(op: Char) {
        if (counter == 0) {
            Toast.makeText(this, "please enter a number!", Toast.LENGTH_LONG).show()
        } else {
            var temp = tv.text.toString()
            tv.text = temp + op
            var num = temp.substring(temp.length - counter until temp.length).toFloat()
            counter = 0
            numlist.add(num)
            oplist.add(op)
        }
    }

    fun decimal() {
        // 11 + 22 . 33 =
        // 11 22 33
        // + . op
        var newnumlist: ArrayList<Float> = ArrayList()
        var newoplist: ArrayList<Char> = ArrayList()
        var i = 0
        while (i < oplist.size) {
            var temp: Float
            if (oplist[i] == '.') {
                temp = 10f.pow(numlist[i + 1].toInt().toString().length)
                temp = numlist[i + 1] / temp
                temp = numlist[i] + temp.toFloat()
                newnumlist.add(temp)
                newoplist.add(oplist[i + 1])
                i++
            } else {
                newnumlist.add(numlist[i])
                newoplist.add(oplist[i])
            }
            i++
        }
        oplist = newoplist
        numlist = newnumlist
    }

    fun negative() {
        // 11 + - 22 . 33 =
        // 11 22 33
        // + - . op

        //new
        //11 -22
        //+ .
        var newnumlist: ArrayList<Float> = ArrayList()
        var newoplist: ArrayList<Char> = ArrayList()
        var i = 0
        while (i < oplist.size) {
            var temp: Float
            if (oplist[i] == '_') {
                temp = numlist[i] * -1
                newnumlist.add(temp)
                newoplist.add(oplist[i + 1])
                oplist.remove('_')
            } else {
                newnumlist.add(numlist[i])
                newoplist.add(oplist[i])
            }
            i++
        }
        oplist = newoplist
        numlist = newnumlist
    }

    fun calculate(): Float {

        if (isDecimal) {
            decimal()
        }

        if (isNegative) {
            negative()
        }

        // 11 + 22 - 33 =
        // 11 22 33
        // + - op

        var num = numlist[0]
        numlist.remove(numlist[0])

        // 11 + 22 - 33 =
        // 22 33
        // + - op

        for (i in 0..oplist.size) {
            when (oplist[i]) {
                '+' -> num += numlist[i]
                '-' -> num -= numlist[i]
                '*' -> num *= numlist[i]
                '/' -> {
                    if (numlist[i] == 0f) {
                        Toast.makeText(this, "zero division", Toast.LENGTH_LONG).show()
                        return 0f
                    } else {
                        num /= numlist[i]
                    }
                }
                '=' -> break
            }
        }
        return num
    }

}

/*
Calculator App, LEGGO!!

- add layout in xml file
    - 4*5 buttons = 19 buttons
    - a text view
- calculations
    - read num 1 if fuond, if not then either old num in tv or (0 / toast message)\
    - read nums
    - when op is pressed, read nums with counter, store in ArrayList, then op
    - same with any op, even =
    - if = is pressed, read all ArrayList and cal everything


    - calculations consider text view as well todo
    - -*+/ calculations
    - a clear button
    - +/- button for negative or pos values
    - decimal point button
    - cannot divide be zero


- Bonus:
    - Allow user to perform additional arithmetic on results
    - Include a delete button to delete the last entered number
    - Allow larger equations with multiple operators
    - Preserve the state to allow users to rotate the device without resetting their operation
* */