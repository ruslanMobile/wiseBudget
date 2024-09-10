package com.finance.presentation.model

import com.finance.presentation.R

data class ChooseIconCategory(
    var iconCategory: IconCategoriesEnum,
    var iconsList: List<Int>
)

enum class IconCategoriesEnum(val textOfCategory: Int) {
    COMMON(R.string.label_common), FOOD_AND_DRINK(R.string.label_food_and_drink),
    HEALTH(R.string.label_health), SHOP(R.string.label_shop)
}