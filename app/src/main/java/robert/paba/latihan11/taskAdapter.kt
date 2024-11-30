package robert.paba.latihan11

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class taskAdapter(private val listTask: List<Task>) : RecyclerView.Adapter<taskAdapter.ListViewHolder>(){

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _namaTask = itemView.findViewById<TextView>(R.id.tvNama)
        var _tanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        var _kategori = itemView.findViewById<TextView>(R.id.tvKategori)
        var _deskripsi = itemView.findViewById<TextView>(R.id.tvDeskripsi)
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

        holder._namaTask.setText(Task.nama)
        holder._tanggal.setText(Task.nama)
        holder._kategori.setText(Task.kategori)
        holder._deskripsi.setText(Task.deskripsi)

    }

}