package com.lyamtalbot.backlogbuster2.backlogbuster2.ui

import backlogbuster2.composeapp.generated.resources.Res
import backlogbuster2.composeapp.generated.resources.date_added_sort
import backlogbuster2.composeapp.generated.resources.date_completed_sort
import backlogbuster2.composeapp.generated.resources.rating_sort
import backlogbuster2.composeapp.generated.resources.time_to_finish_sort
import backlogbuster2.composeapp.generated.resources.title_sort
import org.jetbrains.compose.resources.StringResource

//enum class SortType(val sortString: String){
//    Title("Title"),
//    Rating("Rating"),
//    DateAdded("Date Added"),
//    DateCompleted("Date Completed"),
//    TimeToFinish("Time to Finish")
//}

enum class SortType(val sortString: StringResource){
    Title(Res.string.title_sort),
    Rating(Res.string.rating_sort),
    DateAdded(Res.string.date_added_sort),
    DateCompleted(Res.string.date_completed_sort),
    TimeToFinish(Res.string.time_to_finish_sort),
}