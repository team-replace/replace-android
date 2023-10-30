package com.replace.data.common

sealed interface FetchState {
    object BadInternet : FetchState
    object ParseError : FetchState
    object WrongConnection : FetchState

    object Fail : FetchState
}
