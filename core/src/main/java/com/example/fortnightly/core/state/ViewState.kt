package com.example.fortnightly.core.state

import com.example.fortnightly.core.util.StringWrapper

sealed class ViewState {
    object NoState: ViewState()
    object LoadingState: ViewState()
    object EmptyState: ViewState()
    class ErrorState(val message: StringWrapper): ViewState()
}