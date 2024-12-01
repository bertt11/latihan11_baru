package robert.paba.latihan11

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var rvTask: RecyclerView
    private lateinit var taskAdapter: taskAdapter
    private var arTask = arrayListOf<Task>()

    private var _namaTask : MutableList<String> = emptyList<String>().toMutableList()
    private var _tanggal : MutableList<String> = emptyList<String>().toMutableList()
    private var _kategori : MutableList<String> = emptyList<String>().toMutableList()
    private var _deskripsi : MutableList<String> = emptyList<String>().toMutableList()

    lateinit var sp : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //intent
        var _btnintent1 = findViewById<Button>(R.id.btnPesan)
        _btnintent1.setOnClickListener {
            val intent = Intent(this@MainActivity, halaman_tambah::class.java)
            startActivity(intent)
        }

        sp = getSharedPreferences("dataSP", MODE_PRIVATE)

        val gson = Gson()
        val isiSP = sp.getString("spTask",null)
        val type = object : TypeToken<ArrayList<Task>>(){}.type
        if (isiSP!=null)
                arTask = gson.fromJson(isiSP,type)


        if (arTask.size ==0){
            SiapkanData()
        }else {
            arTask.forEach{
                _namaTask.add(it.nama)
                _tanggal.add(it.tanggal)
                _kategori.add(it.kategori)
                _deskripsi.add(it.deskripsi)

            }
            arTask.clear()
        }
        TambahData()
        TampilkanData()

    }

    fun SiapkanData() {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)

        // Ambil data dari SharedPreferences
        val namaTask = sharedPreferences.getString("namaTask", null)
        val tanggal = sharedPreferences.getString("tanggal", null)
        val kategori = sharedPreferences.getString("kategori", null)
        val deskripsi = sharedPreferences.getString("deskripsi", null)

        // Jika data ada, tambahkan ke list
        if (namaTask != null && tanggal != null && kategori != null && deskripsi != null) {
            _namaTask.add(namaTask)
            _tanggal.add(tanggal)
            _kategori.add(kategori)
            _deskripsi.add(deskripsi)
        }
    }


    fun TambahData() {
        // Ambil data terbaru dari SharedPreferences
        val gson = Gson()
        val isiSP = sp.getString("spTask", null)
        val type = object : TypeToken<ArrayList<Task>>() {}.type

        arTask.clear() // Bersihkan daftar task

        // Jika ada data di SharedPreferences, tambahkan ke daftar
        if (isiSP != null) {
            arTask = gson.fromJson(isiSP, type)
        }

        // Reset daftar lokal
        _namaTask.clear()
        _tanggal.clear()
        _kategori.clear()
        _deskripsi.clear()

        // Sinkronkan daftar lokal dengan arTask
        arTask.forEach {
            _namaTask.add(it.nama)
            _tanggal.add(it.tanggal)
            _kategori.add(it.kategori)
            _deskripsi.add(it.deskripsi)
        }
    }




    fun TampilkanData() {
        TambahData()

        rvTask = findViewById(R.id.rvTask)
        rvTask.layoutManager = LinearLayoutManager(this)

        // Gabungkan data menjadi daftar Task
        val taskList = mutableListOf<Task>()
        for (i in _namaTask.indices) {
            taskList.add(Task(_namaTask[i], _tanggal[i], _kategori[i], _deskripsi[i]))
        }

        // Set Adapter
        taskAdapter = taskAdapter(taskList)
        rvTask.adapter = taskAdapter
        taskAdapter.notifyDataSetChanged()
        // Callback untuk menghapus data
        taskAdapter.setOnItemClickCallback(object : taskAdapter.OnItemClickCallback {
            override fun delData(pos: Int) {
                _namaTask.removeAt(pos)
                _tanggal.removeAt(pos)
                _kategori.removeAt(pos)
                _deskripsi.removeAt(pos)

                // Simpan perubahan ke SharedPreferences
                val gson = Gson()
                val editor = sp.edit()
                arTask.removeAt(pos)
                val json = gson.toJson(arTask)
                editor.putString("spTask", json)
                editor.apply()

                TambahData()
                TampilkanData()
            }

            override fun onUpdateData(pos: Int) {
                val intent = Intent(this@MainActivity, halaman_tambah::class.java)
                intent.putExtra("editMode", true)
                intent.putExtra("position", pos)
                intent.putExtra("namaTask", _namaTask[pos])
                intent.putExtra("tanggal", _tanggal[pos])
                intent.putExtra("kategori", _kategori[pos])
                intent.putExtra("deskripsi", _deskripsi[pos])
                startActivity(intent)
            }

            override fun onStartTask(pos: Int) {
                Log.d("MainActivity", "Mulai task di posisi $pos")
                arTask[pos].status = "ongoing"

                val gson = Gson()
                val editor = sp.edit()
                val json = gson.toJson(arTask)
                editor.putString("spTask", json)
                editor.apply()

                taskAdapter.notifyItemChanged(pos)

                TambahData()
                TampilkanData()
            }

            override fun onFinishTask(pos: Int) {
                Log.d("MainActivity", "Selesai task di posisi $pos")
                arTask.removeAt(pos)

                val gson = Gson()
                val editor = sp.edit()
                val json = gson.toJson(arTask)
                editor.putString("spTask", json)
                editor.apply()

                TambahData()
                TampilkanData()
            }
        })
    }



}