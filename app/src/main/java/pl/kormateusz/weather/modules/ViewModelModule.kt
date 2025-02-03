package pl.kormateusz.weather.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.kormateusz.weather.ui.screens.details.DetailsViewModel
import pl.kormateusz.weather.ui.screens.search.SearchViewModel

val viewModelModule = module {
    viewModel { SearchViewModel(mainRouting = get()) }
    viewModel { (value: String) -> DetailsViewModel(value, mainRouting = get()) }
}