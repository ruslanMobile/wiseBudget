package com.finance.presentation.ui.categories.create_category

import com.finance.presentation.R
import com.finance.presentation.model.ChooseIconCategory
import com.finance.presentation.model.IconCategoriesEnum

val chooseCategoryIconList = listOf(
    ChooseIconCategory(
        iconCategory = IconCategoriesEnum.COMMON,
        iconsList = listOf(
            R.drawable.ic_magic_wand,
            R.drawable.ic_calendar_day,
            R.drawable.ic_clip_paperclip,
        )
    ),
    ChooseIconCategory(
        iconCategory = IconCategoriesEnum.FOOD_AND_DRINK,
        iconsList = listOf(
            R.drawable.ic_burger,
            R.drawable.ic_fish,
            R.drawable.ic_coffee,
        )
    ),
    ChooseIconCategory(
        iconCategory = IconCategoriesEnum.HEALTH,
        iconsList = listOf(
            R.drawable.ic_pill,
            R.drawable.ic_heart
        )
    ),
    ChooseIconCategory(
        iconCategory = IconCategoriesEnum.SHOP,
        iconsList = listOf(
            R.drawable.ic_clothes,
            R.drawable.ic_diamond
        )
    )
)