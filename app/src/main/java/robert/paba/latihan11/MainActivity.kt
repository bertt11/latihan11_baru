package robert.paba.latihan11

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvTask: RecyclerView
    private lateinit var taskAdapter: taskAdapter

    private var _namaTask : MutableList<String> = emptyList<String>().toMutableList()
    private var _tanggal : MutableList<String> = emptyList<String>().toMutableList()
    private var _kategori : MutableList<String> = emptyList<String>().toMutableList()
    private var _deskripsi : MutableList<String> = emptyList<String>().toMutableList()

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

        // Inisialisasi RecyclerView
        rvTask = findViewById(R.id.rvTask)

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

        // Callback untuk hapus data
        taskAdapter.setOnItemClickCallback(object : taskAdapter.OnItemClickCallback {
            override fun delData(pos: Int) {
                namaTask.removeAt(pos)
                tanggal.removeAt(pos)
                kategori.removeAt(pos)
                deskripsi.removeAt(pos_)
            }
        })

    }

}