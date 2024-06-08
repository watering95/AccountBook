package com.example.accountbook.screen.categorysetscreen

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.accountbook.data.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategorySetScreenViewModel(private val repository: CategorySetScreenRepositoryImpl) : ViewModel() {
    private val _title = mutableStateOf("Set Category")
    private val _initItem = Category(name = "", pid = -1, depth = 0)

    private var _listOfItems: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    private val _aCardIsLongPressed = mutableStateOf(false)
    private val _aCardIsTaped = mutableStateOf(false)
    private val _checkedCards: MutableStateFlow<MutableList<Boolean>> = MutableStateFlow(mutableListOf())
    private val _selectedItem = mutableStateOf(_initItem)
    private val _preSelected0 = mutableStateOf("")
    private val _preSelected1 = mutableStateOf("")
    private var _categoriesDepth0: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())
    private var _categoriesDepth1: MutableStateFlow<List<Category>> = MutableStateFlow(emptyList())

    val title : State<String> = _title
    val listOfItems = _listOfItems
    val checkedCards = _checkedCards
    val aCardIsLongPressed = _aCardIsLongPressed
    val aCardIsTaped = _aCardIsTaped
    val selectedItem = _selectedItem
    val preSelected0 = _preSelected0
    val preSelected1 = _preSelected1
    val categoriesDepth0 = _categoriesDepth0
    val categoriesDepth1 = _categoriesDepth1


    init {
        getAllItems()
        getCategories(0)
        getCategories(1)
    }

    private fun getAllItems() {
        viewModelScope.launch {
            repository.allCategories.collectLatest {
                _listOfItems.value = it
                _checkedCards.value = MutableList(it.size) {false}
            }
        }
    }

    private fun getPreSelectedParent(category: Category) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val parentCategory = repository.getCategory(category.pid)
                preSelected0.value = when (category.depth) {
                    1 -> parentCategory.name
                    2 -> if (parentCategory.pid != -1L) repository.getCategory(parentCategory.pid).name else ""
                    else -> ""
                }
                preSelected1.value = when (category.depth) {
                    1 -> ""
                    2 -> parentCategory.name
                    else -> ""
                }
            }
        }
    }

    private fun getCategories(depth: Int) {
        viewModelScope.launch {
            repository.getCategories(depth).collectLatest {
                when(depth) {
                    0 -> _categoriesDepth0.value = it
                    1 -> _categoriesDepth1.value = it
                }
            }
        }
    }

    fun aCardIsTaped(category: Category = _selectedItem.value) {
        _selectedItem.value = category
        if(category.pid != -1L) getPreSelectedParent(category)
        (!_aCardIsTaped.value).also { _aCardIsTaped.value = it }
    }

    fun aCardIsLongPressed() {
        _checkedCards.value = MutableList(_listOfItems.value.size) {false}
        _aCardIsLongPressed.value = !_aCardIsLongPressed.value
    }

    fun checkedACard(index: Int, checked: Boolean) {
        _checkedCards.value[index] = checked
    }

    fun clearSelectedCards() {
        viewModelScope.launch {
            _selectedItem.value = _initItem
            _preSelected0.value = ""
            _preSelected1.value = ""
            delay(100)
        }
    }

    fun insert(category: Category) = viewModelScope.launch {
        repository.insert(category)
    }
    fun update(category: Category) = viewModelScope.launch {
        repository.update(category)
    }
    fun delete(category: Category) = viewModelScope.launch {
        repository.delete(category)
    }
    fun delete(array: Array<Category>) = viewModelScope.launch {
        repository.delete(array)
    }
}