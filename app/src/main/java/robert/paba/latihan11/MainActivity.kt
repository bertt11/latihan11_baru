package robert.paba.latihan11

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
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


//        // Inisialisasi RecyclerView
//        rvTask = findViewById(R.id.rvTask)

//        // Ambil data dari SharedPreferences
//        val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
//        val namaTask = sharedPreferences.getString("namaTask", "Tidak ada data") ?: "Tidak ada data"
//        val tanggal = sharedPreferences.getString("tanggal", "Tidak ada data") ?: "Tidak ada data"
//        val kategori = sharedPreferences.getString("kategori", "Tidak ada data") ?: "Tidak ada data"
//        val deskripsi = sharedPreferences.getString("deskripsi", "Tidak ada data") ?: "Tidak ada data"

//        // Buat daftar task dengan MutableList
//        val taskList = mutableListOf(
//            Task(namaTask, tanggal, kategori, deskripsi)
//        )

//        // Atur Adapter dan LayoutManager untuk RecyclerView
//        taskAdapter = taskAdapter(taskList)
//        rvTask.layoutManager = LinearLayoutManager(this)
//        rvTask.adapter = taskAdapter

//        // Callback untuk hapus data
//        taskAdapter.setOnItemClickCallback(object : taskAdapter.OnItemClickCallback {
//            override fun delData(pos: Int) {
//                namaTask.removeAt(pos)
//                tanggal.removeAt(pos)
//                kategori.removeAt(pos)
//                deskripsi.removeAt(pos_)
//            }
//        })

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
        // Bersihkan daftar task untuk menyimpan data baru
        arTask.clear()

        // Tambahkan semua data dari list lokal ke daftar task
        for (position in _namaTask.indices) {
            val data = Task(
                _namaTask[position],
                _tanggal[position],
                _kategori[position],
                _deskripsi[position]
            )
            arTask.add(data)
        }

        // Simpan daftar task ke SharedPreferences
        val gson = Gson()
        val editor = sp.edit()
        val json = gson.toJson(arTask)
        editor.putString("spTask", json)
        editor.apply()
    }



    fun TampilkanData() {
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

        // Callback untuk menghapus data
        taskAdapter.setOnItemClickCallback(object : taskAdapter.OnItemClickCallback {
            override fun delData(pos: Int) {
                _namaTask.removeAt(pos)
                _tanggal.removeAt(pos)
                _kategori.removeAt(pos)
                _deskripsi.removeAt(pos)

                // Perbarui RecyclerView
                taskAdapter.notifyDataSetChanged()
            }
        })
    }


}