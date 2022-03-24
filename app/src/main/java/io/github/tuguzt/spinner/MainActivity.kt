package io.github.tuguzt.spinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import io.github.tuguzt.spinner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            val adapter = kotlin.run {
                val spinnerValues = resources.getStringArray(R.array.spinner_values)
                val resource = android.R.layout.simple_spinner_item
                ArrayAdapter(this@MainActivity, resource, spinnerValues)
            }
            spinner.adapter = adapter

            viewModel.text.observe(this@MainActivity) {
                solution.text = it
            }

            getSolution.setOnClickListener {
                try {
                    val a = sideA.text.toString().toInt()
                    val b = sideB.text.toString().toInt()
                    val c = sideC.text.toString().toInt()
                    val answer = { answer: Int -> "Ответ: $answer" }
                    val text = when (spinner.selectedItemPosition) {
                        0 -> answer(4 * (a + b + c))
                        1 -> answer(2 * (a * b + b * c + a * c))
                        2 -> answer(a * b * c)
                        else -> "Элемент выпадающего списка не был выбран"
                    }
                    viewModel.setText(text)
                } catch (e: NumberFormatException) {
                    viewModel.setText("Ошибка ввода")
                }
            }
        }
    }
}
