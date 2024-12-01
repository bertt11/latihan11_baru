package robert.paba.latihan11

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class taskAdapter(private val listTask: MutableList<Task>) : RecyclerView.Adapter<taskAdapter.ListViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(pos: Int)
        fun onUpdateData(pos: Int)
        fun onStartTask(pos: Int)
        fun onFinishTask(pos: Int)
    }

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _namaTask = itemView.findViewById<TextView>(R.id.tvNama)
        var _tanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        var _kategori = itemView.findViewById<TextView>(R.id.tvKategori)
        var _deskripsi = itemView.findViewById<TextView>(R.id.tvDeskripsi)

        var _btnHapus =itemView.findViewById<Button>(R.id.btnHapus)
        var _btnUbah = itemView.findViewById<Button>(R.id.btnUbah)
        var _btnKerjakan = itemView.findViewById<Button>(R.id.btnKerjakan)
        var _btnSelesai = itemView.findViewById<Button>(R.id.btnSelesai)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listTask.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var Task = listTask[position]

        // Atur visibilitas tombol berdasarkan status
        if (Task.status == "idle") {
            holder._btnUbah.isEnabled = true
            holder._btnKerjakan.visibility = View.VISIBLE
            holder._btnSelesai.visibility = View.GONE
        } else if (Task.status == "ongoing") {
            holder._btnUbah.isEnabled = false
            holder._btnKerjakan.visibility = View.GONE
            holder._btnSelesai.visibility = View.VISIBLE
        }

        holder._namaTask.setText(Task.nama)
        holder._tanggal.setText(Task.tanggal)
        holder._kategori.setText(Task.kategori)
        holder._deskripsi.setText(Task.deskripsi)


        holder._btnKerjakan.setOnClickListener {
            onItemClickCallback.onStartTask(position)
        }

        holder._btnSelesai.setOnClickListener {
            onItemClickCallback.onFinishTask(position)
        }

        holder._btnHapus.setOnClickListener {
            onItemClickCallback.delData(position)
            removeTask(position)
        }

        holder._btnUbah.setOnClickListener {
            onItemClickCallback.onUpdateData(position)
        }

    }
    fun removeTask(position: Int){
        listTask.removeAt(position) // Hapus item dari daftar
        notifyItemRemoved(position) // Beritahu RecyclerView untuk memperbarui tampilan
        notifyItemRangeChanged(position, listTask.size) // Sesuaikan posisi lainnya
    }

}