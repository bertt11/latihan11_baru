package robert.paba.latihan11

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class halaman_tambah : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_halaman_tambah)

        // Referensi ke EditText dan Button
        val _etNamaTask = findViewById<EditText>(R.id.etNamaTask)
        val _etTanggal = findViewById<EditText>(R.id.etTanggal)
        val _etKategori = findViewById<EditText>(R.id.etKategori)
        val _etDeskripsi = findViewById<EditText>(R.id.etDeskripsi)
        val _btnSimpan = findViewById<Button>(R.id.btnSimpan)

        // Listener untuk tombol Simpan
        _btnSimpan.setOnClickListener {
            // Ambil data input dari user
            val namaTask = _etNamaTask.text.toString()
            val tanggal = _etTanggal.text.toString()
            val kategori = _etKategori.text.toString()
            val deskripsi = _etDeskripsi.text.toString()

            // Validasi input
            if (namaTask.isEmpty() || tanggal.isEmpty() || kategori.isEmpty() || deskripsi.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Ambil data lama dari SharedPreferences
            val sharedPreferences = getSharedPreferences("dataSP", Context.MODE_PRIVATE)
            val gson = Gson()
            val isiSP = sharedPreferences.getString("spTask", null)
            val type = object : TypeToken<ArrayList<Task>>() {}.type
            val taskList: ArrayList<Task> = if (isiSP != null) {
                gson.fromJson(isiSP, type)
            } else {
                arrayListOf()
            }

            // Tambahkan data baru ke daftar
            taskList.add(Task(namaTask, tanggal, kategori, deskripsi))

            // Simpan kembali ke SharedPreferences
            val editor = sharedPreferences.edit()
            val json = gson.toJson(taskList)
            editor.putString("spTask", json)
            editor.apply()

            Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()

            // Kembali ke MainActivity
            val intent = Intent(this@halaman_tambah, MainActivity::class.java)
            startActivity(intent)
            finish()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}