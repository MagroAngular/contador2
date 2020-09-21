package com.example.contador

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
     var decrementarButton: Button? = null
     var incrementarButton: Button? = null
     var resetearButton: Button? = null
     var resultado: TextView? = null
     private var res = 0
     var reset = 0
     var resetNumb: TextView? = null
     var infoReset: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        resultado = findViewById<View>(R.id.resultado) as TextView
        infoReset = findViewById<View>(R.id.infoReset) as TextView
        resetNumb = findViewById<View>(R.id.resetNumb) as TextView
        decrementarButton = findViewById<View>(R.id.decrementarButton) as Button
        incrementarButton = findViewById<View>(R.id.incrementarButton) as Button
        resetearButton = findViewById<View>(R.id.resetearButton) as Button
        reset = resetNumb!!.text.toString().toInt()
        res = 0
        mostrarContador()
    }

    fun incrementar(view: View?) {
        val teclado = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        teclado.hideSoftInputFromWindow(resetNumb!!.windowToken, 0)
        res++
        mostrarContador()
    }

    fun decrementar(view: View?) {
        val teclado = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        teclado.hideSoftInputFromWindow(resetNumb!!.windowToken, 0)
        val negativo = findViewById<View>(R.id.checkBox) as CheckBox
        res--
        if (res < 0 && !negativo.isChecked) {
            res = 0
        }
        mostrarContador()
    }

    fun actualizar(view: View?) {
        var aux = resetNumb!!.text.toString()
        if (aux.isEmpty()) {
            aux = "0"
        }
        reset = aux.toInt()
        mostrarContador()
    }

    fun resetear(view: View?) {
        val teclado = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        teclado.hideSoftInputFromWindow(resetNumb!!.windowToken, 0)
        var aux = resetNumb!!.text.toString()
        if (aux.isEmpty()) {
            aux = "0"
        }
        reset = aux.toInt()
        res = reset
        mostrarContador()
    }

    fun mostrarContador() {
        resultado!!.text = res.toString()
        infoReset!!.text = "Resetear a $reset"
    }

    override fun onSaveInstanceState(state: Bundle) {
        state.putInt("valor", res)
        super.onSaveInstanceState(state)
    }

    override fun onRestoreInstanceState(state: Bundle) {
        super.onRestoreInstanceState(state)
        res = state.getInt("valor", 0)
        mostrarContador()
    }

    override fun onPause() {
        super.onPause()
        val datos = PreferenceManager.getDefaultSharedPreferences(this) as SharedPreferences
        val miEditor = datos.edit()
        miEditor.putInt("valor", res)
        miEditor.apply()
    }

    override fun onResume() {
        super.onResume()
        val datos = PreferenceManager.getDefaultSharedPreferences(this) as SharedPreferences
        res = datos.getInt("valor", 0)
        mostrarContador()
    }
}