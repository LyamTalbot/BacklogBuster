package com.lyamtalbot.backlogbuster2.backlogbuster2

import androidx.lifecycle.SavedStateHandle
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.GameDao
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.GameDatabase
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.DetailsViewModel
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.GameListViewModel
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screenmodels.AddScreenModel
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screenmodels.DetailsScreenModel
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screenmodels.HomescreenModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun commonModule(): Module = module {
    single<GameDao> {get<GameDatabase>().getGameDao()}
    singleOf(::GameListViewModel)
    factory {
        DetailsScreenModel(gameDao = get())
    }
    factory {
        HomescreenModel(gameDao = get())
    }
    factory {
        AddScreenModel(gameDao = get())
    }
}