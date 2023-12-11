import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.konektraapplication.R
import com.dicoding.konektraapplication.data.HistoryItem
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(private val historyList: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewHistory: TextView = itemView.findViewById(R.id.textViewHistory)
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)

        fun bind(historyItem: HistoryItem) {
            textViewHistory.text = historyItem.text
            textViewDate.text = formatDate(historyItem.date)
        }
        private fun formatDate(date: Date): String {
            val formatter = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
            return formatter.format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val historyItem = historyList[position]
        holder.bind(historyItem)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }


}
