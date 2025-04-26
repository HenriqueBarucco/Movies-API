package com.henriquebarucco.movielie.shared.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class Logger {
    companion object {
        fun getLogger(): Logger {
            val className = Throwable().stackTrace[1].className
            return LoggerFactory.getLogger(Class.forName(className))
        }
    }
}
