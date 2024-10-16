package com.example.calculator1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow


class MainActivity : AppCompatActivity() , View.OnClickListener {

    lateinit var textResult: TextView
    var state: Int = 1
    var op  = 0 // toán tử
    var op1: Double = 0.0 // toán hạng 1
    var op2: Double = 0.0// toán hạng 2
    var isDecimal: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.text1)

        findViewById<Button>(R.id.button0).setOnClickListener(this)
        findViewById<Button>(R.id.button1).setOnClickListener(this)
        findViewById<Button>(R.id.button2).setOnClickListener(this)
        findViewById<Button>(R.id.button3).setOnClickListener(this)
        findViewById<Button>(R.id.button4).setOnClickListener(this)
        findViewById<Button>(R.id.button5).setOnClickListener(this)
        findViewById<Button>(R.id.button6).setOnClickListener(this)
        findViewById<Button>(R.id.button7).setOnClickListener(this)
        findViewById<Button>(R.id.button8).setOnClickListener(this)
        findViewById<Button>(R.id.button9).setOnClickListener(this)
        findViewById<Button>(R.id.buttonNhan).setOnClickListener(this)
        findViewById<Button>(R.id.buttonChia).setOnClickListener(this)
        findViewById<Button>(R.id.buttonCong).setOnClickListener(this)
        findViewById<Button>(R.id.buttonTru).setOnClickListener(this)
        findViewById<Button>(R.id.buttonCE).setOnClickListener(this)
        findViewById<Button>(R.id.buttonC).setOnClickListener(this)
        findViewById<Button>(R.id.buttonPhay).setOnClickListener(this)
        findViewById<Button>(R.id.buttonBang).setOnClickListener(this)

    }

    override fun onClick(p0: View?){
        val id = p0?.id
        if(id == R.id.button0){
            addDigit(0)
        } else if(id == R.id.button1){
            addDigit(1)
        } else if(id == R.id.button2){
            addDigit(2)
        } else if(id == R.id.button3){
            addDigit(3)
        } else if(id == R.id.button4){
            addDigit(4)
        }else if(id == R.id.button5){
            addDigit(5)
        }else if(id == R.id.button6){
            addDigit(6)
        }else if(id == R.id.button7){
            addDigit(7)
        }else if(id == R.id.button8){
            addDigit(8)
        }else if(id == R.id.button9){
            addDigit(9)
        } else if(id == R.id.buttonCong || id == R.id.buttonTru || id == R.id.buttonNhan || id == R.id.buttonChia){
            if(state ==2 ){ // nếu đang nhập số t2 và nhấn toán tử thì tính toán
                caculateResult()
            }
            // cập nhật toán tử mới
            when(id){
                R.id.buttonCong -> op =1
                R.id.buttonTru -> op = 2
                R.id.buttonNhan -> op = 3
                R.id.buttonChia -> op = 4
            }
            state = 2
            isDecimal = false
        } else if(id == R.id.buttonBang){
            caculateResult()
            state = 1
        } else if(id == R.id.buttonCE){
            clearEntry()
        } else if(id == R.id.buttonC){
            clearAll()
        } else if(id == R.id.buttonPhay){
            addDecimal()

        }

    }

    private fun caculateResult(){
        var result = 0.0
        when(op){
            1 -> result = op1 + op2
            2 -> result = op1 - op2
            3 -> result = op1*op2
            4 -> result = if(op2!= 0.0) op1/op2 else Double.NaN
        }
        op1 = result
        op2 = 0.0
        textResult.text = if (result == result.toInt().toDouble()) {
            result.toInt().toString() // Hiển thị số nguyên nếu không có phần thập phân
        } else {
            result.toString() // Hiển thị số thực nếu có phần thập phân
        }
    }

    var decimalPlace: Int = 0 // Thêm biến này để theo dõi vị trí thập phân

    private fun addDigit(c: Int) {
        if (state == 1) {
            if (isDecimal) {
                decimalPlace++
                op1 += c / 10.0.pow(decimalPlace) // Thêm số thập phân tại vị trí thích hợp
            } else {
                op1 = 10 * op1 + c
            }
            textResult.text = if (op1 == op1.toInt().toDouble()) {
                op1.toInt().toString()
            } else {
                op1.toString()
            }
        } else {
            if (isDecimal) {
                decimalPlace++
                op2 += c / 10.0.pow(decimalPlace) // Tương tự với toán hạng thứ hai
            } else {
                op2 = 10 * op2 + c
            }
            textResult.text = if (op2 == op2.toInt().toDouble()) {
                op2.toInt().toString()
            } else {
                op2.toString()
            }
        }
    }


    private fun clearEntry() {
        if (state == 1) {
            if (isDecimal) {
                // Nếu có số thập phân
                val op1Str = op1.toString()
                if (op1Str.contains(".")) {
                    // Tách phần nguyên và phần thập phân
                    val parts = op1Str.split(".")
                    val intPart = parts[0] // Phần nguyên
                    val decPart = parts[1] // Phần thập phân

                    if (decPart.isNotEmpty()) {
                        // Nếu phần thập phân còn ký tự, xóa ký tự cuối cùng
                        val newDecPart = decPart.dropLast(1)
                        op1 = if (newDecPart.isEmpty()) {
                            // Nếu xóa hết phần thập phân, chỉ còn lại phần nguyên
                            isDecimal = false
                            intPart.toDouble()
                        } else {
                            // Cập nhật lại số với phần thập phân mới
                            "$intPart.$newDecPart".toDouble()
                        }
                        textResult.text = op1.toString()
                    }
                }
            } else {
                // Xử lý xóa phần nguyên (khi không có số thập phân)
                op1 = (op1 / 10).toInt().toDouble()
                textResult.text = if (op1 == op1.toInt().toDouble()) {
                    op1.toInt().toString()
                } else {
                    op1.toString()
                }
            }
        } else {
            if (isDecimal) {
                // Tương tự cho toán hạng thứ hai (op2)
                val op2Str = op2.toString()
                if (op2Str.contains(".")) {
                    val parts = op2Str.split(".")
                    val intPart = parts[0]
                    val decPart = parts[1]

                    if (decPart.isNotEmpty()) {
                        val newDecPart = decPart.dropLast(1)
                        op2 = if (newDecPart.isEmpty()) {
                            isDecimal = false
                            intPart.toDouble()
                        } else {
                            "$intPart.$newDecPart".toDouble()
                        }
                        textResult.text = op2.toString()
                    }
                }
            } else {
                // Xử lý xóa phần nguyên của toán hạng thứ hai
                op2 = (op2 / 10).toInt().toDouble()
                textResult.text = if (op2 == op2.toInt().toDouble()) {
                    op2.toInt().toString()
                } else {
                    op2.toString()
                }
            }
        }
    }


    private  fun clearAll(){
        op1 = 0.0
        op2 = 0.0
        op = 0
        isDecimal = false
        state = 1
        textResult.text = "0"
    }

    private fun addDecimal(){
        if(!isDecimal){
            if(state ==1){
                textResult.text = "$op1."
            } else {
                textResult.text = "$op2."
            }
        }
        isDecimal = true
        decimalPlace = 0
    }

}

