package com.gmiller.roomtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.gmiller.roomtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = MainDb.getDb(this)
        db.getDao().getAllItem().asLiveData().observe(this){list ->
            binding.tvList.text = ""
            list.forEach{

                val text = "${it.idn} ${it.name}, Price: ${it.price} RUB\n"
                binding.tvList.append(text)
            }
        }
        binding.button.setOnClickListener{

            val item = Item(null,
                binding.edName.text.toString(),
                binding.edPrice.text.toString()
            )

            Thread{

                db.getDao().insertItem(item)

            }.start()


        }
    }
}