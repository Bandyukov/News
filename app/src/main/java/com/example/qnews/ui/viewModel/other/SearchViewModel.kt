package com.example.qnews.ui.viewModel.other

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.qnews.R
import com.example.qnews.core.DI.resources.ResourceProvider
import com.example.qnews.core.mapping.toSearches
import com.example.qnews.core.models.base.ListItem
import com.example.qnews.core.models.suggestion.Search
import com.example.qnews.core.models.suggestion.SuggestionHorizontalItem
import com.example.qnews.core.repo.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val repository: MainRepository,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _topics = MutableLiveData<List<ListItem>>()
    val topics: LiveData<List<ListItem>> get() = _topics

    private val headlines: Array<String> = resourceProvider.array(R.array.headlines)
    private val world: Array<String> = resourceProvider.array(R.array.world)

    init {
        getDefaultTopics()
    }

    private fun getDefaultTopics() {
        val suggestions = mutableListOf(
            SuggestionHorizontalItem(
                resourceProvider.string(R.string.headlines),
                headlines.toSearches()
            ),
            SuggestionHorizontalItem(
                resourceProvider.string(R.string.world),
                world.toSearches()
            )
        )
        _topics.value = suggestions
    }

    fun getRecentSearches() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_topics.value != null) {
                val suggestions = _topics.value as MutableList
                val latest = repository.getAllRecentSearches()

                if (latest.isNotEmpty()) {
                    if (suggestions.size < 3) {
                        suggestions.add(
                            SuggestionHorizontalItem(
                                resourceProvider.string(R.string.recent_searches),
                                latest
                            )
                        )
                    } else {
                        suggestions[suggestions.lastIndex] = SuggestionHorizontalItem(
                            resourceProvider.string(R.string.recent_searches),
                            latest
                        )
                    }
                }
                _topics.postValue(suggestions)
            }
        }
    }

    fun updateRecentSearches(topic: String) {
        viewModelScope.launch(Dispatchers.IO) {
            topics.value?.let { topics ->
                val topicLast = topics[topics.lastIndex] as SuggestionHorizontalItem
                for (suggestion in topicLast.suggestions) {
                    val curSuggestion = suggestion as Search
                    if (curSuggestion.suggestion == topic) {
                        repository.deleteSearch(curSuggestion)
                    }
                }
            }
            repository.updateSearches(Search(topic))
        }
    }
}