package com.example.week16

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week16.Adapter.Mainadapter
import com.example.week16.FullAPI.APIClient
import com.example.week16.FullAPI.ListResponse
import com.example.week16.Model.Person
import com.example.week16.databinding.ActivityMainBinding
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
   lateinit var mainadapter: Mainadapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getdata()
        Floatingbutton()
    }


    fun getdata(){
        APIClient.APIEndpoint().getallperson().enqueue(object : Callback<ListResponse<Person>> {
            override fun onFailure(call: retrofit2.Call<ListResponse<Person>>, t: Throwable) {
                println(t.message)
            }

            override fun onResponse(call: retrofit2.Call<ListResponse<Person>>, response: Response<ListResponse<Person>>) {
                if(response.isSuccessful){
                    val body = response.body()
                    if (body != null){
                        showrcy(body.data)
                        Toast.makeText(applicationContext,"Data Berhasil Didapatkan ", Toast.LENGTH_SHORT).show()
                    }
                }
            }



        })
    }
    private fun showrcy(Person : List<Person>){
        mainadapter = Mainadapter(Person,object : Mainadapter.Click{
            override fun onClick(post: Person) {
                startActivity(Intent(this@MainActivity,activity_detail_data::class.java).apply {
                    putExtra("id",post.id)
                })
            }

            override fun EditClick(post: Person) {
                startActivity(Intent(this@MainActivity,activity_post::class.java).apply {
                    putExtra("id",post.id)
                    putExtra("first_name",post.first_name)
                    putExtra("last_name",post.last_name)
                    putExtra("email",post.email)

                })
            }

        })
        binding.Rcyview.apply{
            adapter = mainadapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
    fun Floatingbutton(){
        binding.ButtonAdd.setOnClickListener {
            startActivity(Intent(this,activity_post::class.java))
        }
    }

}