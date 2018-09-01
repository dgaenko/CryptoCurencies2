package ru.familion.cryptocurencies.ui.list

import ru.familion.cryptocurencies.model.Data

interface ItemClickListener {

    abstract fun OnItemClick(data: Data)

}