package com.hcl.got.ui.home

import android.graphics.Color
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hcl.got.databinding.ListItemBookBinding
import com.hcl.got.data.model.BooksData
import com.hcl.got.data.model.CharactersData
import com.hcl.got.databinding.ListItemCharactersBinding
import com.hcl.got.ui.utils.getListFirstItem

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.MyViewHolder>() {

    private var listOfCharacter: List<CharactersData> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ListItemCharactersBinding.inflate(
                android.view.LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfCharacter.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindUI(position)
    }

    fun setListItems(listOfCharacters :  List<CharactersData>) {
        val preSize = this.listOfCharacter.size
        this.listOfCharacter = listOfCharacters
        notifyItemRangeChanged(preSize, listOfCharacter.size-1)
    }

    inner class MyViewHolder(val binding: ListItemCharactersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val bookNameTextView: TextView = binding.bookNameTextView


        fun bindUI(position: Int) {
            binding.apply {
                listOfCharacter[position].let { characterData->
                    charName = characterData.name
                    gender = characterData.gender
                    titles = getListFirstItem(characterData.titles)
                    aliases = getListFirstItem(characterData.aliases)
                    playedBy = getListFirstItem(characterData.playedBy)
                }
            }
        }
    }

}