package robert.paba.latihan11

data class Task(
    var nama : String,
    var tanggal : String,
    var kategori : String,
    var deskripsi : String,
    var status: String = "idle" // Status bisa "idle", "ongoing", atau "done"
)
