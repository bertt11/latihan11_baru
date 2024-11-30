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
            val sharedPreferences = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            // Ambil input dari user
            val namaTask = _etNamaTask.text.toString()
            val tanggal = _etTanggal.text.toString()
            val kategori = _etKategori.text.toString()
            val deskripsi = _etDeskripsi.text.toString()

            // Simpan data ke SharedPreferences
            editor.putString("namaTask", namaTask)
            editor.putString("tanggal", tanggal)
            editor.putString("kategori", kategori)
            editor.putString("deskripsi", deskripsi)
            editor.apply()

            Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()

            //pindah halaman
            val intent = Intent(this@halaman_tambah, MainActivity::class.java)
            startActivity(intent)


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }
}