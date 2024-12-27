import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

//class TaskAdapter(private val tasks: List<String>) :
//    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_task, parent, false)
//        return TaskViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
//        holder.taskText.text = tasks[position]
//    }
//
//    override fun getItemCount(): Int = tasks.size
//
//    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val taskText: TextView = itemView.findViewById(R.id.taskText)
//    }
//}
class TaskAdapter(private val tasks: List<String>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // ViewHolder class
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskText: TextView = itemView.findViewById(R.id.taskText) // Assuming tvTask is the TextView for task name
        val taskIndex: TextView = itemView.findViewById(R.id.tvTaskIndex) // TextView for index (new)
    }

    // Create a new ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(itemView)
    }

    // Bind the data to the ViewHolder
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        // Set task index and task name
        holder.taskIndex.text = (position + 1).toString()+"." // Display position as index (starting from 1)
        holder.taskText.text = task
    }

    // Return the size of the list
    override fun getItemCount(): Int {
        return tasks.size
    }
}

