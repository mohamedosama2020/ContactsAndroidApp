package com.moham.contacts.features.contacts.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.moham.contacts.R
import com.moham.contacts.features.contactinfo.ContactInfoActivity
import com.moham.contacts.features.contactinfo.ContactInfoActivity.Companion.INTENT_EXTRA
import com.moham.contacts.features.contacts.adapter.SwipeAction.*
import com.moham.contacts.model.entities.Contact

class ContactsAdapter(
    private val context: Activity,
    val callback: (contact: Contact, action: SwipeAction) -> Unit
) :
    RecyclerView.Adapter<ContactsAdapter.ImageViewHolder>() {

    private var contacts = listOf<Contact>()

    fun setContacts(list: List<Contact>) {
        contacts = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val contact = contacts[position]
        holder.tvName.text = contact.name
        holder.tvPhone.text = contact.phone
        holder.tvContactLetter.text = contact.name.first().toString()

        holder.container.setOnClickListener {
            val intent = Intent(context, ContactInfoActivity::class.java)
            intent.putExtra(INTENT_EXTRA, contact)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context,
                holder.ivContactImage,
                "image"
            )
            context.startActivity(intent, options.toBundle())
        }
        holder.ivCall.setOnClickListener {
            callback(contact, Call)
        }
        holder.ivDelete.setOnClickListener {
            callback(contact, Delete)
        }

    }


    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val container: ConstraintLayout = itemView.findViewById(R.id.container)
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val ivContactImage: ImageView = itemView.findViewById(R.id.ivContactImage)
        val ivCall: ImageView = itemView.findViewById(R.id.ivCall)
        val ivDelete: ImageView = itemView.findViewById(R.id.ivDelete)
        val tvPhone: TextView = itemView.findViewById(R.id.tvPhone)
        val tvContactLetter: TextView = itemView.findViewById(R.id.tvContactLetter)

    }
}