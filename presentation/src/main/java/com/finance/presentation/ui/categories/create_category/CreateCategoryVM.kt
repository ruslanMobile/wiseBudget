package com.finance.presentation.ui.categories.create_category

import androidx.lifecycle.ViewModel
import com.finance.presentation.R
import com.finance.presentation.model.ChooseIconCategory
import com.finance.presentation.model.IconCategoriesEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateCategoryVM @Inject constructor(

) : ViewModel() {


    var newCategoryModel = CreatedCategoryEntity

}

