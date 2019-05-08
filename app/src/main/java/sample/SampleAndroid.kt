package sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text
import java.lang.Error

actual class Sample {
    actual fun checkMe() = 44
}

actual object Platform {
    actual val name: String = "Android"
}

class MainActivity : AppCompatActivity() {

    var equation : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("SampleAndroid", "onCreate"+ equation)
        hello()
        Sample().checkMe()
        setContentView(R.layout.activity_main)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        Log.e("SampleAndroid", "onSaveInstanceState" + equation)
        if (outState != null) {
            outState.putString("p", equation)
        }
        println("onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            Log.e("SampleAndroid", "onRestoreInstanceState "+  savedInstanceState.getString("p"))
            equation = savedInstanceState.getString("p")
            val textView: TextView = findViewById(R.id.equation_text_view)
            textView.text = equation
        }
        Log.e("SampleAndroid", "onRestoreInstanceState"+ equation)
    }
    fun addCharacter(view: View) {
        var button : Button  = view as Button
        equation += button.text.toString()

        val textView: TextView = findViewById(R.id.equation_text_view)
        textView.text = equation
    }

    fun addSqrt(view: View) {
        var button : Button  = view as Button
        equation += "v"

        val textView: TextView = findViewById(R.id.equation_text_view)
        textView.text = equation
    }

    fun clearEquation(view: View) {
        var button : Button  = view as Button
        equation = ""
        val textView: TextView = findViewById(R.id.equation_text_view)
        textView.text = equation
    }

    fun doEquation(view: View) {
        var button : Button  = view as Button
        var calculator = Calculator()

        if (calculator.isStringAnEquation(equation)) {
            var str = calculator.run(equation)
            var result = calculator.calculate(str)

            val textView: TextView = findViewById(R.id.equation_text_view)
            textView.text = result.toString()
        }
        else {
            throw Error("Introduced string is not an equation")
        }
    }


}